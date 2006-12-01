package org.pgist.system;

import java.util.Collection;
import java.util.Date;

import org.pgist.exceptions.UserExistException;
import org.pgist.users.BaseUser;
import org.pgist.users.Role;
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
    
    
    public void setSystemDAO(SystemDAO systemDAO) {
        this.systemDAO = systemDAO;
    }


    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }


    /*
     * ------------------------------------------------------------------------
     */
    
    
    public User getUserByName(String loginname, boolean enabled, boolean deleted) throws Exception {
        return userDAO.getUserByName(loginname, enabled, deleted);
    }//getUserByName()
    
    
    public Collection getUsersByRole(String role) throws Exception {
        return userDAO.getUsersByRole(role);
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
		return userDAO.getUserById(WebUtils.currentUserId(), true, false);
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


    public void editCurrentUser(String cpassword, String password, String email) throws Exception {
        User user = userDAO.getUserById(WebUtils.currentUserId(), true, false);
        
        if (!user.checkPassword(cpassword)) throw new Exception("Current Password is Incorrect." + cpassword);
        
        //if password field left blank then don't update password
        if (password!=null || !("".equals(password))) {
            user.setPassword(password);
            user.encodePassword();
        }
        
        //if email field left blank then don't update email
        if (email!=null && !"".equals(email)) {
            user.setEmail(email);
        }
        
        userDAO.save(user);
    }//editCurrentUser()


}//class SystemServiceImpl
