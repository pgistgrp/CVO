package org.pgist.system;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.pgist.exceptions.UserExistException;
import org.pgist.users.BaseUser;
import org.pgist.users.Role;
import org.pgist.users.User;
import org.pgist.users.UserInfo;
import org.pgist.users.Vehicle;
import org.pgist.util.PageSetting;
import org.pgist.util.WebUtils;
import org.pgist.web.DelegatingHttpServletRequestWrapper;
import org.pgist.system.SystemService;
import org.pgist.funding.FundingDAO;
import org.pgist.funding.UserTaxInfoDTO;

/**
 * 
 * @author kenny
 *
 */
public class RegisterServiceImpl implements RegisterService {

	private RegisterDAO registerDAO;
    
    private UserDAO userDAO;
    
    private SystemService systemService;


    public void setRegisterDAO(RegisterDAO registerDAO) {
        this.registerDAO = registerDAO;
    }
    
    
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}


    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    
    
    public Long addUser(String firstname, String lastname, String email1,  String address1, String address2, String city, String state, String zipcode, String username, String password1) throws Exception {
    	return registerDAO.addUser(firstname, lastname, email1, address1, address2, city, state, zipcode, username, password1); 
    }
    
    
    public boolean createQuotaQualify(Long id) throws Exception {
    	systemService.createQuotaStats();
    	return registerDAO.createQuotaQualify(id);
    }
    
    
    public void login(HttpServletRequest request, Long id) throws Exception {
    	HttpSession session = request.getSession(true);
    	User user = systemService.getUserById(id);
    	UserInfo userInfo = new UserInfo(user);
    	session.setAttribute("user", userInfo);
        WebUtils.setCurrentUser(userInfo);
    }
    
    public void addQuotaInfo(String user_interview, String user_observation) throws Exception {
    	Long id = WebUtils.currentUserId();
    	registerDAO.addQuotaInfo(user_interview, user_observation, id);
    }
    
    
    public void addConsent() throws Exception {
    	Long id = WebUtils.currentUserId();
    	registerDAO.addConsent(id);
    }
    
    
    public void deleteUser() throws Exception {
    	Long id = WebUtils.currentUserId();
    	registerDAO.deleteUser(id);
    }
    
    
}
