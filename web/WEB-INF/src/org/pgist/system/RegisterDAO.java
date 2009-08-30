package org.pgist.system;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.pgist.users.TravelTrip;
import org.pgist.users.User;


/**
 * 
 * @author John
 *
 */
public interface RegisterDAO extends BaseDAO {

	Long addUser(String firstname, String lastname, String email1,  String address1, String address2, String city, String state, String zipcode, String username, String password1) throws Exception;
	
	boolean createQuotaQualify(Long id) throws Exception;
	
	void addQuotaInfo(String user_interview, String user_observation, Long id) throws Exception;
	
	void addConsent(Long id) throws Exception;
	
	void deleteUser(Long id) throws Exception;
	
	Collection getTolls() throws Exception;
	
	void addQuestionnaire(Long id, String incomeRange, int householdsize, int drive, int carpool, int carpoolpeople, int bus, int bike, int walk) throws Exception;
	
	void setToll(Long id, Long myTollId, boolean boolchecked) throws Exception;
	
	boolean checkUsername(String username) throws Exception;
	
	User getCurrentUser(Long id) throws Exception;
	
	boolean createPasswordRecovery(String email) throws Exception;
	
	boolean checkEmail(String email) throws Exception;
	
	boolean validatePasswordRecoveryCode(String code) throws Exception;
	
	boolean createChangePassword(String code, String password)throws Exception;
	
	void deleteRecoverPassword(String code) throws Exception;
	
	void deleteAllExpired() throws Exception;

    Long saveUserTravelTrip(User user, TravelTrip trip) throws Exception;
    
    ArrayList<TravelTrip> getUserTravelTrips (Long uid) throws Exception;

    void deleteTravelTrip(long tripId) throws Exception;
    
    Collection getRegisterObjectByType(String type) throws Exception;
    
    void createRegisterObjects(String type, String[] valuelist) throws Exception;
    
    Collection getTransTypes() throws Exception;
    
    void createCancel(Long id) throws Exception;

    Long addSarpUser(String firstname, String lastname, String email1,
            String age, String gender, String income, String education,
            String zipcode, String username, String password1) throws Exception;
    
}
