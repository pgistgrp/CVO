package org.pgist.system;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.pgist.users.User;
import org.pgist.util.PageSetting;
import org.pgist.util.WebUtils;
import org.pgist.web.DelegatingHttpServletRequestWrapper;


/**
 * 
 * @author kenny
 *
 */
public class SystemDAOImpl extends BaseDAOImpl implements SystemDAO {
    
    
    private EmailSender emailSender;
    
    public void setEmailSender(EmailSender emailSender) {
        this.emailSender = emailSender;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */

    
    public Object getObject(String entityName, Long id) throws Exception {
        return getHibernateTemplate().get(entityName, id);
    }//getObject()


    private static final String hql_getFeedbacks_1 = "select count(fb.id) from Feedback fb";
    
    private static final String hql_getFeedbacks_2 = "from Feedback fb order by fb.id desc";
    
    public Collection getFeedbacks(PageSetting setting) throws Exception {
        Query query = null;
        
        //get count
        query = getSession().createQuery(hql_getFeedbacks_1);
        
        query.setMaxResults(1);
        
        int count = ((Number) query.uniqueResult()).intValue();
        
        setting.setRowOfPage(count);
        
        if (count==0) return new ArrayList();
        
        //get records
        query = getSession().createQuery(hql_getFeedbacks_2);
        
        query.setFirstResult(setting.getFirstRow());
        query.setMaxResults(count);
        
        return query.list();
    }//getFeedbacks();

    
    private static final String hql_getVoting = "from YesNoVoting v where v.targetType=? and v.targetId=? and v.owner=?";
    
    
    public YesNoVoting getVoting(int targetType, Long targetId) throws Exception {
        Query query = getSession().createQuery(hql_getVoting);
        
        query.setInteger(0, targetType);
        query.setLong(1, targetId);
        query.setLong(2, WebUtils.currentUserId());
        query.setMaxResults(1);
        
        return (YesNoVoting) query.uniqueResult();
    }//getVoting();


    public boolean setVoting(int targetType, Long targetId, boolean agree) throws Exception {
        YesNoVoting voting = getVoting(targetType, targetId);
        if (voting!=null) return false;
        
        User user = (User) load(User.class, WebUtils.currentUserId());
        
        voting = new YesNoVoting();
        
        voting.setTargetType(targetType);
        voting.setTargetId(targetId);
        voting.setOwner(user);
        voting.setVoting(agree);
        
        save(voting);
        
        return true;
    }//setVoting();


    public void logGetting(DelegatingHttpServletRequestWrapper request) throws Exception {
        SystemLog log = new SystemLog();
        
        log.setTime(new Date());
        log.setUserId(WebUtils.currentUserId());
        log.setMethod("GET");
        log.setUrl(request.getRequestURL().toString());
        log.setQuery(request.getQueryString());
        log.setPostData("");
        log.setReferer(request.getHeader("referer"));
        log.setSuccessful((new Boolean(true).equals(request.getAttribute("PGIST_SERVICE_SUCCESSFUL"))));
        
        save(log);
    }//logGetting();
    
    
    public void logPosting(DelegatingHttpServletRequestWrapper request) throws Exception {
        SystemLog log = new SystemLog();
        
        log.setTime(new Date());
        log.setUserId(WebUtils.currentUserId());
        log.setMethod("POST");
        log.setUrl(request.getRequestURL().toString());
        log.setQuery(request.getQueryString());
        log.setPostData(request.getLogging());
        log.setReferer(request.getHeader("referer"));
        log.setSuccessful((new Boolean(true).equals(request.getAttribute("PGIST_SERVICE_SUCCESSFUL"))));
        
        save(log);
    }//logPosting();
    
    
    private static final String hql_getAllUsers = "from User u where u.deleted=? order by u.id";
    
    public Collection getAllUsers() throws Exception {    	
    	
    	return getHibernateTemplate().find(hql_getAllUsers, false);
    } //getAllUsers();
    
    
    public User getUserById(Long id) throws Exception {
    	return (User) getHibernateTemplate().load(User.class, id);
    }//getUserById();
    
    
    public void disableUsers(String[] ids, boolean enable) throws Exception {
    	
    	for(String u: ids) {
    		Long userId = Long.parseLong(u);
    		User user = (User) getHibernateTemplate().load(User.class, userId);
    		user.setEnabled(enable);
    	} //for each
    	
    }//disableUsers();
    
    
    private static final String hql_getDisabledUsers = "from User u where u.enabled=? order by u.id";
    
    public Collection getDisabledUsers() throws Exception {
    	Collection users = getHibernateTemplate().find(hql_getDisabledUsers, false);
    	return users;
    }//getDisableUsers();
    
    
    public Collection getEnabledUsers() throws Exception {
    	Collection users = getHibernateTemplate().find(hql_getDisabledUsers, true);
    	return users;
    }//getEnableUsers();
    
    
    public void resetPassword(String[] ids, String password) throws Exception {
    	
    	for(String u: ids) {
    		Long userId = Long.parseLong(u);
    		User user = (User) getHibernateTemplate().load(User.class, userId);
    		user.setPassword(password);
    		user.encodePassword();   	
    		Map values = new HashMap();
    		values.put("newpassword", password);
        	emailSender.send(user, "modpasswordreset", values);
    	} //for each
    	
    } //resetPassword();
    
    
    public void setQuota(Long id, boolean quota) throws Exception {

    	User user = getUserById(id);
    	user.setQuota(quota);
    	save(user);
    } //setQuota();
    
    
    public void setQuotaLimit(Long countyId, int limit) throws Exception {
    	County c = (County) load(County.class, countyId);
    	c.setQuotaLimit(limit);
    	save(c);
    } //setQuotaLimit();
    
    
    public Long addCounty(String name) throws Exception {    	
    	County c = new County();
    	c.setName(name);
    	save(c);
    	return c.getId();
    } //addCounty();
    
    
    public void deleteCounty(Long countyId) throws Exception {
    	County c = (County) getHibernateTemplate().load(County.class, countyId);
    	if (c != null) getHibernateTemplate().delete(c);
    } //deleteCounty();
    
    
    private static final String hql_getQuotaStats1 = "from County c order by c.name";
    
    private static final String hql_getQuotaStats2 = "from User u where u.countyId=? and u.quota=?";
    
    public Collection createQuotaStats() throws Exception {
    	
    	Collection<County> collection = getHibernateTemplate().find(hql_getQuotaStats1);
    	for(County c : collection) {
    		Long id = c.getId();
    		List list = getHibernateTemplate().find(hql_getQuotaStats2, new Object[] {
        			id, new Boolean(true)
            });
    		int size = list.size();
    		c.setTempQuotaNumber(size);
    		save(c);
    	}
    	
    	return collection;
    }
    
    
    public void addZipCodes(Long countyId, String[] zipCodes) throws Exception {
    	County county = (County) load(County.class, countyId);
    	Set<String> myZipCodes = county.getZipCodes();
    	for(String zip : zipCodes) {
    		zip = zip.trim();
    		myZipCodes.add(zip);
    	}
    	county.setZipCodes(myZipCodes);
    	save(county);
    }
    
    
    public void editCountyName(Long countyId, String name) throws Exception {
    	County c = (County) load(County.class, countyId);
    	c.setName(name);
    	save(c);
    }
    
    
    public void deleteZipCodes(Long countyId, String[] zipCodes) throws Exception {
    	County county = (County) load(County.class, countyId);
    	Set<String> myZipCodes = county.getZipCodes();
    	for(String zip : zipCodes) {
    		myZipCodes.remove(zip);
    	}
    	county.setZipCodes(myZipCodes);
    	save(county);
    }
    
    
    private static final String hql_getAllCounties = "from County c order by c.name";
    
    public Collection getAllCounties() throws Exception {
    	
    	Collection<County> county = getHibernateTemplate().find(hql_getAllCounties);
    	
    	return county;
    }
    
    
    public void addAnnouncement(Long workflowId, String message) throws Exception {
    	Date date = new Date();
    	Announcement announcement = new Announcement();
    	announcement.setMessage(message);
    	announcement.setWorkflowId(workflowId);
    	announcement.setDate(date);
    	save(announcement);
    }
    
    
    public void editAnnouncement(Long id, String message) throws Exception {
    	Announcement announcement = (Announcement) load(Announcement.class, id);
    	announcement.setMessage(message);
    	save(announcement);
    }
    
    
    public void deleteAnnouncement(Long id) throws Exception {
    	Announcement a = (Announcement) getHibernateTemplate().load(Announcement.class, id);
    	if (a != null) getHibernateTemplate().delete(a);
    }
    
    
    private static final String hql_getAnnouncements = "from Announcement a where a.workflowId=? order by a.date desc";
    
    public Collection getAnnouncements(Long workflowId) throws Exception {
    	return getHibernateTemplate().find(hql_getAnnouncements, workflowId);
    }
    
    
    private static final String hql_getTransTypes = "from RegisterObject ro where ro.type=? order by ro.value desc";
    
    public Collection getTransTypes() throws Exception {
    	return getHibernateTemplate().find(hql_getTransTypes, "transport");
    }
    
    
    public void deleteUser(Long id) throws Exception {
		User u = (User)load(User.class, id);
		Set empty = new HashSet();
		u.setRoles(empty);
		u.setVehicles(empty);
		u.setDeleted(true);
		u.setEnabled(false);
		u.setQuota(false);
		Date d = new Date();
		u.setLoginname(u.getLoginname() + "deleted" + d);
		u.setHomeAddr("");
		u.setZipcode("");
		u.setCountyId(null);
		u.setEmail("");
		
		save(u);
    }
    
    
}//class SystemDAOImpl
