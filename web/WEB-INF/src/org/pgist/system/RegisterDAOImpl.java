package org.pgist.system;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.Iterator;
import java.util.SortedSet;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;
import org.pgist.criteria.Criteria;
import org.pgist.funding.FundingSource;
import org.pgist.funding.UserCommute;
import org.pgist.funding.UserTaxInfoDTO;
import org.pgist.funding.UserFundingSourceToll;
import org.pgist.users.User;
import org.pgist.users.Vehicle;
import org.pgist.users.Role;
import org.pgist.system.SystemService;
import org.pgist.util.PageSetting;
import org.pgist.util.WebUtils;
import org.pgist.web.DelegatingHttpServletRequestWrapper;



/**
 * 
 * @author kenny
 *
 */
public class RegisterDAOImpl extends BaseDAOImpl implements RegisterDAO {

	private static final String hql_addUser = "from County c where ?= some elements(c.zipCodes)";
	
    public Long addUser(String firstname, String lastname, String email1,  String address1, String address2, String city, String state, String zipcode, String username, String password1) throws Exception {
    	User u = new User();
    	Set<Role> roles = u.getRoles();
    	
    	Iterator itRoles = roles.iterator();
    	while(itRoles.hasNext()) {
    		Role role = (Role) itRoles.next();
    		String roleName = role.getName();
    		if(roleName.equals("participant")) {
    			u.addRole(role);
    		}
    	}
    	
    	u.setFirstname(firstname);
    	u.setLastname(lastname);
    	u.setEmail(email1);
    	if(address2.length()>0) {
    	u.setHomeAddr(address1 + ", " + address2);
    	} else {
    		u.setHomeAddr(address1);
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
}
