package org.pgist.system;

import java.util.Collection;
import java.util.Date;

import org.pgist.users.BaseUser;
import org.pgist.users.User;
import org.pgist.util.PageSetting;
import org.pgist.util.WebUtils;


/**
 * 
 * @author kenny
 *
 */
public class SystemServiceImpl implements SystemService {
    
    
    private SystemDAO systemDAO;
    
    private UserDAO userDAO;
    
    
    public void setSystemDAO(SystemDAO systemDAO) {
        this.systemDAO = systemDAO;
    }


    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }


    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void createFeedback(String action, String s) throws Exception {
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
    }//createFeedback()


    public Collection getFeedbacks(PageSetting setting) throws Exception {
        return systemDAO.getFeedbacks(setting);
    }//getFeedbacks()


	public User getCurrentUser() throws Exception {
		return userDAO.getUserById(WebUtils.currentUserId(), true, false);
	}
    
    
    public YesNoVoting getVoting(int targetType, Long targetId) throws Exception {
        return systemDAO.getVoting(targetType, targetId);
    }//getVoting()


}//class SystemServiceImpl
