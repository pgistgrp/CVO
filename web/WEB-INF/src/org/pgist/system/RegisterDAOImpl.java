package org.pgist.system;

import java.text.DateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;
import java.util.List;
import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;


import org.pgist.funding.UserCommute;
import org.pgist.funding.UserFundingSourceToll;
import org.pgist.users.User;
import org.pgist.users.Role;
import org.pgist.util.WebUtils;


/**
 * 
 * @author John
 *
 */
public class RegisterDAOImpl extends BaseDAOImpl implements RegisterDAO {

	
	private EmailSender emailSender;
	
	public void setEmailSender(EmailSender emailSender) {
		this.emailSender = emailSender;
	}
	
	
	private static final String hql_addUser = "from County c where ?= some elements(c.zipCodes)";
	
	private static final String hql_addUser2 = "from Role r where r.name=?";
	
    public Long addUser(String firstname, String lastname, String email1,  String address1, String address2, String city, String state, String zipcode, String username, String password1) throws Exception {
    	User u = new User();
    	
    	u.setFirstname(firstname);
    	u.setLastname(lastname);
    	u.setEmail(email1);
    	u.setHomeAddr(address1);
    	if(address2.length()>0) {
    		u.setHomeAddr2(address2);
    	} 
    	u.setCity(city);
    	u.setState(state);
    	u.setZipcode(zipcode);
    	u.setLoginname(username);
    	u.setPassword(password1);
    	u.setEnabled(false);
    	u.encodePassword();
    	
    	Collection counties = getHibernateTemplate().find(hql_addUser, new Object[] {
    			zipcode,
    	});
    	
    	if(counties.size()>0){
	    	Iterator it = counties.iterator();
	    	County c = (County) it.next();
	    	u.setCountyId(c.getId());
    	}
    	save(u);
    	
    	Collection roles = getHibernateTemplate().find(hql_addUser2, new Object[] {
    			"participant",
    	});
    	Iterator itRoles = roles.iterator();
    	while(itRoles.hasNext()) {
    		Role role = (Role) itRoles.next();
    		String roleName = role.getName();
    		if(roleName.equals("participant")) {
    			u.addRole(role);
    		}
    	}
    	
    	save(u);
    	
    	return u.getId();
    } //addUser()
    
    
    public boolean createQuotaQualify(Long id) throws Exception {
    	User u = (User) load(User.class, id);
    	Long cid = u.getCountyId();
    	if(cid==null) {
    		return false;
    	}
    	County c = (County) load(County.class, cid);
    	int limit = c.getQuotaLimit();
    	int current = c.getTempQuotaNumber();
    	
    	if(current < limit) {
    		u.setQuota(true);
    		return true;
    	}
    	return false;
    } //createQuotaQualify()
    
    
    public void addQuotaInfo(String user_interview, String user_observation, Long id) throws Exception {
    	User user = (User) load(User.class, id);
    	user.setInterview(user_interview);
    	user.setRecording(user_observation);
    	user.setConsented("Quota");
    	user.setQuota(true);
    } //addQuotaInfo()
    
    
    public void addConsent(Long id) throws Exception {
    	User user = (User) load(User.class, id);
    	user.setConsented("Non-Quota");
    } //addConsent()
    
    
    public void deleteUser(Long id) throws Exception {
    	User user = (User) load(User.class, id);
    	user.setDeleted(true);
    	user.setEnabled(false);
    } //deleteUser()
    
    
    private static final String hql_getTolls = "from FundingSource fs order by fs.name";
    
	public Collection getTolls() throws Exception {
		
		return getHibernateTemplate().find(hql_getTolls);
	} //getTolls()
	

	public void addQuestionnaire(Long id, String incomeRange, int householdsize, int drive, int carpool, int carpoolpeople, int bus, int bike,  int walk) throws Exception {
		User user = (User) load(User.class, id);
		user.setIncomeRange(incomeRange);
		user.setFamilyCount(householdsize);
		user.setDriveDays(drive);
		user.setCarpoolDays(carpool);
		user.setCarpoolPeople(carpoolpeople);
		user.setBusDays(bus);
		user.setBikeDays(bike);
		user.setWalkDays(walk);
		user.setEnabled(true);
		save(user);
	} //addQuestionnaire()
	
	
	public void setToll(Long id, Long myTollId, boolean boolchecked) throws Exception {
		User user = (User) load(User.class, id);
		UserCommute uc = user.getUserCommute();
		SortedSet<UserFundingSourceToll> tolls = uc.getTolls();
		Iterator it = tolls.iterator();
		while(it.hasNext()) {
			UserFundingSourceToll toll = (UserFundingSourceToll) it.next();
			Long tollId = toll.getId();
			if(tollId == myTollId) {
				toll.setUsed(boolchecked);
			}
		}
    } //setToll()
	
	
	private static final String hql_checkUsername = "from User u where u.loginname=?";
	
	public boolean checkUsername(String username) throws Exception {
		
		List list = getHibernateTemplate().find(hql_checkUsername, new Object[] {
				username,
    	});
		
		if(list.size() > 0) {
			
			return false;
		}
		
		return true;
	} //checkUsername()
	

	private static final String hql_checkEmail = "from User u where u.email=?";
	
	public boolean checkEmail(String email) throws Exception {
		
		List list = getHibernateTemplate().find(hql_checkEmail, new Object[] {
				email,
    	});
		
		if(list.size() > 0) {
			return false;
		}
		
		return true;
	} //checkEmail()
	
	
	public User getCurrentUser(Long id) throws Exception {
		User u = (User)load(User.class, id);
		return u;
	} //getCurrentUser()
	
	
	private static final String hql_recoverPassword = "from User u where u.email=?";
	
	public boolean createPasswordRecovery(String email) throws Exception {
		
		List list = getHibernateTemplate().find(hql_recoverPassword, new Object[] {
				email,
    	});
		
		if(list.size() != 1) {
			return false;
		}
		
		User user = (User) list.get(0);
		if(user.getRoleString().equals("participant")) {
			String code = Math.random() + user.getEmail() + Math.random() + user.getPassword();
			RecoverPassword rp = new RecoverPassword();
			rp.setCode(code);
			rp.encode();
			Date date = new Date();
			int minutes = date.getMinutes();
			date.setMinutes(minutes+30);
			rp.setDate(date);
			rp.setUserId(user.getId());
			save(rp);
			
			String recoveryCode = rp.getCode();
			
	    	Map values = new HashMap();
	    	String loginname = user.getLoginname();
	        values.put("code", recoveryCode);
	    	emailSender.send(user, "recoverpassword", values);
	    	
			return true;
		}
		return false;
	} //createPasswordRecovery()
	
	
	public boolean validatePasswordRecoveryCode(String code) throws Exception {
		RecoverPassword rp = getRecoverPassword(code);		
		if(rp != null) {
			Date sDate = rp.getDate();
			Date cDate = new Date();
			// make sure its still valid
			if(sDate.compareTo(cDate) > 0) {
				return true;
			}
			deleteRecoverPassword(code);
			return false;
		}
		return false;
	} //validatePasswordRecoveryCode()
	
	
	public boolean changePassword(String code, String password)throws Exception {
		RecoverPassword rp = getRecoverPassword(code);	
		
		if(rp != null) {			
			Long id = rp.getUserId();
			User user = (User) load(User.class, id);
			user.setPassword(password);
			user.encodePassword();
			return true;
		}
		return false;
	} //changePassword()
	
	
	public void deleteRecoverPassword(String code) throws Exception {
		RecoverPassword rp = getRecoverPassword(code);		
		if(rp != null) {
			getHibernateTemplate().delete(rp);
		}
	} //deleteRecoverPassword()
	

	private static final String hql_getRecoverPassword = "from RecoverPassword rc where rc.code=?";
	
	public RecoverPassword getRecoverPassword(String code) throws Exception {
		List list = getHibernateTemplate().find(hql_getRecoverPassword, new Object[] {
				code,
    	});
		
		if(list.size() > 0) {
			RecoverPassword rp = (RecoverPassword) list.get(0);
			return rp;
		}
		return null;
	} //getRecoverPassword()
	
	
	private static final String hql_deleteAllExpired = "from RecoverPassword rc order by rc.date";
	
	public void deleteAllExpired() throws Exception {
		List list = getHibernateTemplate().find(hql_deleteAllExpired);
		
		if(list.size() > 0) {
			Iterator it = list.iterator();
			while(it.hasNext()) {
				RecoverPassword rp = (RecoverPassword) it.next();
				Date sDate = rp.getDate();
				Date cDate = new Date();
				
				if(sDate.compareTo(cDate) < 0) {			
					this.deleteRecoverPassword(rp.getCode());
				} else {
					break;
				}
			}
		}
	} //deleteAllExpired()
	
	
}
