package org.pgist.system;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.pgist.users.TravelTrip;
import org.pgist.users.User;



/**
 * 
 * @author John
 *
 */
public interface RegisterService {

	Long addUser(String firstname, String lastname, String email1,  String address1, String address2, String city, String state, String zipcode, String username, String password1) throws Exception;
	
	boolean createQuotaQualify(Long id) throws Exception;
	
	void login(HttpServletRequest request, Long id) throws Exception;
	
	void logout(HttpServletRequest request) throws Exception;
	
	void addQuotaInfo(String user_interview, String user_observation) throws Exception;
	
	void addConsent() throws Exception;
	
	void deleteUser() throws Exception;
	
	Collection getTolls() throws Exception;
	
	void addQuestionnaire(String incomeRange, int householdsize, int drive, int carpool, int carpoolpeople, int bus, int bike, int walk) throws Exception;
	
	void setToll(Long myTollId, boolean boolchecked) throws Exception;
	
	boolean checkUsername(String username) throws Exception;
	
	User getCurrentUser() throws Exception;
	
	boolean createPasswordRecovery(String email) throws Exception;
	
	boolean checkEmail(String email) throws Exception;
	
	boolean validatePasswordRecoveryCode(String code) throws Exception;
	
	boolean changePassword(String code, String password)throws Exception;
	
	void deleteRecoverPassword(String code) throws Exception;
	
	void deleteAllExpired() throws Exception;
    
	Long saveUserTravelTrip(Long uid, TravelTrip trip) throws Exception;
    
	ArrayList<TravelTrip> getUserTravelTrips (Long uid) throws Exception;
	
	void deleteTravelTrip(long tripId) throws Exception;
	
	Collection getRegisterObjectByType(String type) throws Exception;
	
	void createRegisterObjects(String type, String[] valuelist) throws Exception;
	
	Collection getTransTypes() throws Exception;

}
