package org.pgist.system;

import java.util.Collection;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.List;

import org.pgist.funding.UserCommute;
import org.pgist.funding.UserFundingSourceToll;
import org.pgist.users.User;
import org.pgist.users.Role;


/**
 * 
 * @author kenny
 *
 */
public class RegisterDAOImpl extends BaseDAOImpl implements RegisterDAO {

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
    }
    
    
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
    }
    
    
    public void addQuotaInfo(String user_interview, String user_observation, Long id) throws Exception {
    	User user = (User) load(User.class, id);
    	user.setInterview(user_interview);
    	user.setRecording(user_observation);
    	user.setConsented("Quota");
    	user.setQuota(true);
    }
    
    
    public void addConsent(Long id) throws Exception {
    	User user = (User) load(User.class, id);
    	user.setConsented("Non-Quota");
    }
    
    
    public void deleteUser(Long id) throws Exception {
    	User user = (User) load(User.class, id);
    	user.setDeleted(true);
    	user.setEnabled(false);
    }
    
    
    private static final String hql_getTolls = "from FundingSource fs order by fs.name";
    
	public Collection getTolls() throws Exception {
		
		return getHibernateTemplate().find(hql_getTolls);
	}
	

	public void addQuestionnaire(Long id, String incomeRange, int householdsize, int drive, int carpool, int carpoolpeople, int bus, int bike) throws Exception {
		User user = (User) load(User.class, id);
		user.setIncomeRange(incomeRange);
		user.setFamilyCount(householdsize);
		user.setDriveDays(drive);
		user.setCarpoolDays(carpool);
		user.setCarpoolPeople(carpoolpeople);
		user.setBusDays(bus);
		user.setBikeDays(bike);
		user.setEnabled(true);
		save(user);
	}
	
	
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
    }
	
	
	private static final String hql_checkUsername = "from User u where u.loginname=?";
	
	public boolean checkUsername(String username) throws Exception {
		
		List list = getHibernateTemplate().find(hql_checkUsername, new Object[] {
				username,
    	});
		
		if(list.size() > 0) {
			
			return false;
		}
		
		return true;
	}
}
