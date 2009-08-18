package org.pgist.system;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.pgist.exceptions.UserExistException;
import org.pgist.search.SearchHelper;
import org.pgist.users.BaseUser;
import org.pgist.users.Role;
import org.pgist.users.Assoc;
import org.pgist.users.User;
import org.pgist.util.PageSetting;
import org.pgist.util.WebUtils;
import org.pgist.web.DelegatingHttpServletRequestWrapper;


/**
 * 
 * @author kenny
 *
 */
public class SystemServiceImpl implements SystemService {
    
    
    private SystemDAO systemDAO;
    
    private UserDAO userDAO;
    
    private SearchHelper searchHelper;
    
    
    public void setSystemDAO(SystemDAO systemDAO) {
        this.systemDAO = systemDAO;
    }


    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }


    public void setSearchHelper(SearchHelper searchHelper) {
        this.searchHelper = searchHelper;
    }


    /*
     * ------------------------------------------------------------------------
     */
    
    
    public Object getObject(String entityName, Long id) throws Exception {
        return systemDAO.getObject(entityName, id);
    }//getObject()
    
    
    public User getUserByName(String loginname, boolean deleted) throws Exception {
        return userDAO.getUserByName(loginname, deleted);
    }//getUserByName()
    
    
    public User getUserByName(String loginname, boolean enabled, boolean deleted) throws Exception {
        return userDAO.getUserByName(loginname, enabled, deleted);
    }//getUserByName()
    
    
    public Collection getUsersByRole(String role) throws Exception {
        return userDAO.getUsersByRole(role);
    }//getUsersByRole()
    
    
    public Collection getUsersByAssoc(String assoc) throws Exception {
        return userDAO.getUsersByAssoc(assoc);
    }//getUsersByRole()


    public void createUser(User user) throws Exception {
        // check if user already exists
        User other = userDAO.getUserByName(user.getLoginname(), false);
        if (other!=null) throw new UserExistException("The ID already exist. Please pick a differnt ID.");
        
        Role role = userDAO.getRoleByName("participant");
        user.addRole(role);
        
        userDAO.save(user);
    }//createUser()


    public Feedback createFeedback(String action, String s) throws Exception {
        Feedback feedback = new Feedback();
        
        feedback.setAction(action);
        feedback.setContent(s);
        feedback.setCreateTime(new Date());
        
        User user = userDAO.getUserById(WebUtils.currentUserId(), true, false);
        feedback.setUser(user);
        
        BaseUser baseuser = new BaseUser();
        String email = baseuser.getEmail();
        feedback.setEmail(email);
        
        systemDAO.save(feedback);
        
        return feedback;
    }//createFeedback()


    public Collection getFeedbacks(PageSetting setting) throws Exception {
        return systemDAO.getFeedbacks(setting);
    }//getFeedbacks()


	public User getCurrentUser() throws Exception {
		Long id = WebUtils.currentUserId();
		System.out.println("***SystemServiceImpl CurrentUser id: " + id);
		if(id!=null){
			return userDAO.getUserById(id, true, false);
		}
		return null;
		
	}
    
    
    public YesNoVoting getVoting(int targetType, Long targetId) throws Exception {
        return systemDAO.getVoting(targetType, targetId);
    }//getVoting()


    public void logRequest(DelegatingHttpServletRequestWrapper request) throws Exception {
        String method = request.getMethod();
        if ("GET".equals(method)) {
            systemDAO.logGetting(request);
        } else {
            systemDAO.logPosting(request);
        }
    }//logRequest()


    public void editCurrentUser(String address1, String address2, String state, String homeCity, String homeZipcode, String workCity, String workZipcode, String vocation, String primaryTransport, String profileDesc) throws Exception {
        User user = userDAO.getUserById(WebUtils.currentUserId(), true, false);
        
        user.setHomeAddr(address1);
        user.setState(state);
        user.setCity(homeCity);
        user.setZipcode(homeZipcode);

        if(!("".equals(address2.trim()))) {
        	user.setHomeAddr(address2);
        }
        if(!("".equals(workCity.trim()))) {
        	user.setWorkCity(workCity);
        }
        if(!("".equals(workZipcode.trim()))) {
        	user.setWorkZipcode(workZipcode);
        }
        if(!("".equals(vocation.trim()))) {
        	user.setVocation(vocation);
        }
        if(!("".equals(primaryTransport.trim()))) {
        	Long transId = Long.parseLong(primaryTransport);
        	RegisterObject transtype = (RegisterObject) systemDAO.load(RegisterObject.class, transId);
        	user.setPrimaryTransport(transtype);
        }
        if(!("".equals(profileDesc.trim()))) {
        	user.setProfileDesc(profileDesc);
        }

        userDAO.updateProfile(user);
        
        /*
         * Index the user profile with Lucene
         */
        IndexWriter writer = null;
        try {
            /*
             * First delete the user profile from Lucene
             */
            IndexReader reader = null;
            try {
                reader = searchHelper.getIndexReader();
                reader.deleteDocuments(new Term("userprofileid", user.getId().toString()));
            } finally {
                if (reader!=null) reader.close();
            }
            
            /*
             * Then index the user profile again
             */
            writer = searchHelper.getIndexWriter();
            
            String contents = "<p>" + user.getFirstname() + " " + user.getLastname() + " is a "
                            + user.getVocation() + " from " + user.getCity() + ".<p>"
                            + "<p>Reason why " + user.getFirstname() + " is here: "
                            + user.getProfileDesc() + "</p>";
            
            Document doc = new Document();
            
            doc.add( new Field("type", "userprofile", Field.Store.YES, Field.Index.UN_TOKENIZED) );
            doc.add( new Field("body", contents, Field.Store.YES, Field.Index.UN_TOKENIZED) );
            doc.add( new Field("contents", user.getLoginname() + " " + user.getVocation() + " " + user.getProfileDesc(), Field.Store.NO, Field.Index.TOKENIZED) );
            doc.add( new Field("userid", user.getId().toString(), Field.Store.YES, Field.Index.UN_TOKENIZED) );
            doc.add( new Field("loginname", user.getLoginname().toString(), Field.Store.YES, Field.Index.UN_TOKENIZED) );
            doc.add( new Field("userprofileid", user.getId().toString(), Field.Store.YES, Field.Index.UN_TOKENIZED) );
            
            writer.addDocument(doc);
            
            writer.optimize();
        } catch (Exception e) {
            /*
             * catch and ignore any exception here, we don't want lucene interfere with the execution
             */
            e.printStackTrace();
        } finally {
            if (writer!=null) writer.close();
        }
    }//editCurrentUser()

    
    public boolean editUserSettings(String cpassword, String password1, String email, boolean emailNotify, boolean emailNotifyDisc) throws Exception {
    	User user = userDAO.getUserById(WebUtils.currentUserId(), true, false);
    	
    	if((cpassword==null || "".equals(cpassword)) && (password1==null || "".equals(password1))) {
    		System.out.println("SystemServiceImpl: 1" + emailNotify + emailNotifyDisc);
    		user.setEmail(email);
        	user.setEmailNotify(emailNotify);
        	user.setEmailNotifyDisc(emailNotifyDisc);
        	systemDAO.save(user);
        	return true;
    	} else {
	    	if (!user.checkPassword(cpassword)) {
	    		return false;
	    	}
	    	
	    	user.setPassword(password1);
	    	user.encodePassword();
	    	user.setEmail(email);
        	user.setEmailNotify(emailNotify);
        	user.setEmailNotifyDisc(emailNotifyDisc);
        	System.out.println("SystemServiceImpl: 12" + emailNotify + emailNotifyDisc);
        	systemDAO.save(user);
	    	return true;
    	}
    }
    
    
    public Collection getAllUsers() throws Exception {    	
    	
    	return systemDAO.getAllUsers();
    } //getAllUsers();

    
    public User getUserById(Long id) throws Exception { 
    	
    	return systemDAO.getUserById(id);
    }
    
    
    public void disableUsers(String[] ids, boolean enable) throws Exception {
    	systemDAO.disableUsers(ids, enable);
    }
    
    
    public Collection getEnabledUsers() throws Exception {
    	return systemDAO.getEnabledUsers();
    }
    
    
    public Collection getDisabledUsers() throws Exception {
    	return systemDAO.getDisabledUsers();
    }
    
    
    public String getEmailList(boolean quota, boolean nonquota) throws Exception {
    	Collection userslist = getAllUsers();
    	String emaillist = "";
    	
    	Iterator ul = userslist.iterator();
    	
		if(quota == nonquota) {
			while(ul.hasNext()) {
	    		User user = (User)ul.next();
	    		if(user.isEnabled()) {
	    			emaillist += user.getEmail() + ", ";
	    		}
			}
		} else if (quota) {
			while(ul.hasNext()) {
	    		User user = (User)ul.next();
	    		if(user.getQuota() && user.isEnabled()) {
	    			emaillist += user.getEmail() + ", ";
	    		}
			}
		} else if (nonquota) {
			while(ul.hasNext()) {
	    		User user = (User)ul.next();
	    		if(!user.getQuota() && user.isEnabled()) {
	    			emaillist += user.getEmail() + ", ";
	    		}
			}
		}

    	return emaillist;
    }
    
    
    public void resetPassword(String[] ids, String password) throws Exception {
    	systemDAO.resetPassword(ids, password);
    }
    
    
    public void setQuota(Long id, boolean quota) throws Exception {
    	systemDAO.setQuota(id, quota);
    }
    
    
    public void setQuotaLimit(Long countyId, int limit) throws Exception {
    	systemDAO.setQuotaLimit(countyId, limit);
    }
    
    
    public Long addCounty(String name) throws Exception {
    	return systemDAO.addCounty(name);
    }
    
    
    public void editCountyName(Long countyId, String name) throws Exception {
    	systemDAO.editCountyName(countyId, name);
    }
    
    
    public Collection createQuotaStats() throws Exception {
    	return systemDAO.createQuotaStats();
    }
    
    
    public void addZipCodes(Long countyId, String[] zipCodes) throws Exception {
    	systemDAO.addZipCodes(countyId, zipCodes);
    }
    
    
    public void deleteZipCodes(Long countyId, String[] zipCodes) throws Exception {
    	systemDAO.deleteZipCodes(countyId, zipCodes);
    }
    
    
    public Collection getAllCounties() throws Exception {
    	return systemDAO.getAllCounties();
    }
    
    
    public void deleteCounty(Long countyId) throws Exception {
    	systemDAO.deleteCounty(countyId);
    }
    
    
    public void addAnnouncement(Long workflowId, String message) throws Exception {
    	systemDAO.addAnnouncement(workflowId, message);
    }
    
    
    public void editAnnouncement(Long id, String message) throws Exception {
    	systemDAO.editAnnouncement(id, message);
    }
    
    
    public void deleteAnnouncement(Long id) throws Exception {
    	systemDAO.deleteAnnouncement(id);
    }
    
    
    public Collection getAnnouncements(Long workflowId) throws Exception {
    	return systemDAO.getAnnouncements(workflowId);
    }
    
    public Collection getTransTypes() throws Exception {
    	return systemDAO.getTransTypes();
    }
    
    public void deleteUser(Long id) throws Exception {
    	if(WebUtils.checkRole("moderator")){
    		systemDAO.deleteUser(id);
    	}
    }
    
}//class SystemServiceImpl
