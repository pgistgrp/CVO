package org.pgist.system;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.pgist.users.TravelTrip;
import org.pgist.users.User;
import org.pgist.users.UserInfo;
import org.pgist.util.WebUtils;


/**
 * 
 * @author John
 *
 */
public class RegisterServiceImpl implements RegisterService {

	private RegisterDAO registerDAO;
    
    private SystemService systemService;


    public void setRegisterDAO(RegisterDAO registerDAO) {
        this.registerDAO = registerDAO;
    }
    
    
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
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
    
    
    public void logout(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession(false);
        
        if (session!=null) {
            session.setAttribute("user", null);
            session.invalidate();
        }
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
    
    
    public Collection getTolls() throws Exception {
    	return registerDAO.getTolls();
    }
    
    
    public void addQuestionnaire(String incomeRange, int householdsize, int drive, int carpool, int carpoolpeople, int bus, int bike,  int walk) throws Exception {
    	Long id = WebUtils.currentUserId();
    	registerDAO.addQuestionnaire(id, incomeRange, householdsize, drive, carpool, carpoolpeople, bus, bike, walk);  	
    }
    
    
    public void setToll(Long myTollId, boolean boolchecked) throws Exception {
    	Long id = WebUtils.currentUserId();
    	registerDAO.setToll(id, myTollId, boolchecked);
    }
    
    
    public boolean checkUsername(String username) throws Exception {
    	
    	return registerDAO.checkUsername(username);
    }
    
    public User getCurrentUser() throws Exception {
    	Long id = WebUtils.currentUserId();
    	return registerDAO.getCurrentUser(id);
    }
    
    
	public boolean createPasswordRecovery(String email) throws Exception {
		return registerDAO.createPasswordRecovery(email);
	}
	
	
	public boolean checkEmail(String email) throws Exception {
		return registerDAO.checkEmail(email);
	}
	
	
	public boolean validatePasswordRecoveryCode(String code) throws Exception {
		return registerDAO.validatePasswordRecoveryCode(code);
	}
	
	
	public boolean changePassword(String code, String password)throws Exception {
		return registerDAO.changePassword(code, password);
	}
	
	
	public void deleteRecoverPassword(String code) throws Exception {
		registerDAO.deleteRecoverPassword(code);
	}
	
	
	public void deleteAllExpired() throws Exception {
		registerDAO.deleteAllExpired();
	}
	
    public Long saveUserTravelTrip(Long uid, TravelTrip trip) throws Exception{
    	User user = registerDAO.getCurrentUser(uid);
    	return registerDAO.saveUserTravelTrip(user, trip);
    }
    
    public ArrayList<TravelTrip> getUserTravelTrips (Long uid) throws Exception{
    	return registerDAO.getUserTravelTrips(uid);
    }

}
