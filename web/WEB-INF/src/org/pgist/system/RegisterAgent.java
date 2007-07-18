package org.pgist.system;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.directwebremoting.WebContextFactory;
import org.pgist.funding.FundingService;
import org.pgist.funding.UserTaxInfoDTO;
import org.pgist.users.User;
import org.pgist.util.WebUtils;


/**
 * DWR AJAX Agent class.<br>
 * Provide AJAX services to client programs.<br>
 * In this document, all the NON-AJAX methods are marked out. So all methods
 * <span style="color:red;">without</span> such a description
 * <span style="color:red;">ARE</span> AJAX service methods.<br>
 *
 * @author John
 *
 */
public class RegisterAgent {

	private RegisterService registerService;
	
	private FundingService fundingService;
	
	private EmailSender emailSender;
	
	
	public void setEmailSender(EmailSender emailSender) {
		this.emailSender = emailSender;
	}

	public void setRegisterService(RegisterService registerService) {
        this.registerService = registerService;
    }
	
	public void setFundingService(FundingService fundingService) {
		this.fundingService = fundingService;
	}
    
	/**
     * Add user stage one, basic registration information
     * 
     * @param params a Map contains:
     *   <ul>
     *     <li>firstname - string, user's first name</li>
     *     <li>lastname - string, user's last name</li>
     *     <li>email1 - string, user's email address</li>
     *     <li>email2 - string, user's email address to confirm</li>
     *     <li>address1 - string, user's home address line 1</li>
     *     <li>address2 - string, user's home address line 2 (optional)</li>
     *     <li>city - string, user's city</li>
     *     <li>state - string, user's state</li>
     *     <li>zipcode - String, user's zipcode</li>
     *     <li>username - String, user's desired username</li>
     *     <li>password1 - string, password</li>
     *     <li>password2 - string, confirm password</li>
     *   </ul>
     * @return a Map contains:
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *     <li>id - int, the id for the new user object</li>
     *   </ul>
     */
    public Map addUser(HttpServletRequest request, Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        String firstname = (String) params.get("firstname");
        String lastname = (String) params.get("lastname");
        String email1 = (String) params.get("email1");
        String email2 = (String) params.get("email2");
        String address1 = (String) params.get("address1");
        String address2 = (String) params.get("address2");
        String city = (String) params.get("city");
        String state = (String) params.get("state");
        String zipcode = (String) params.get("zipcode");
        String username = (String) params.get("username");
        String password1 = (String) params.get("password1");
        String password2 = (String) params.get("password2");
    	
    	if(firstname==null || "".equals(firstname.trim())){
    		map.put("reason", "first name cannot be blank.");
    		return map;
    	}
    	if(lastname==null || "".equals(lastname.trim())){
    		map.put("reason", "last name cannot be blank.");
    		return map;
    	}
    	if((email1==null || email2==null)|| ("".equals(email1.trim()) || "".equals(email2.trim()) )){
    		map.put("reason", "email fields cannot be blank.");
    		return map;
    	}
    	if(!email1.equals(email2)) {
    		map.put("reason", "email addresses must match.");
    		return map;
    	}
    	if(address1==null || "".equals(address1.trim())){
    		map.put("reason", "address line 1 cannot be blank.");
    		return map;
    	}
    	if(city==null || "".equals(city.trim())){
    		map.put("reason", "city cannot be blank.");
    		return map;
    	}
    	if(state==null || "".equals(state.trim())){
    		map.put("reason", "state cannot be blank.");
    		return map;
    	}
    	if(zipcode==null || "".equals(zipcode.trim())){
    		map.put("reason", "zipcode cannot be blank.");
    		return map;
    	} 
    	if(username==null || "".equals(username.trim())){
    		map.put("reason", "username cannot be blank.");
    		return map;
    	}
    	if((password1==null || password2==null)|| ("".equals(password1.trim()) || "".equals(password2.trim()) )){
    		map.put("reason", "password fields cannot be blank.");
    		return map;
    	}
    	if(!password1.equals(password2)) {
    		map.put("reason", "passwords must match.");
    		return map;
    	}
    	if(password1.length()<6) {
    		map.put("reason", "password must be at least six characters long.");
    		return map;
    	}
    	
        try {
        	
        	if(registerService.checkEmail(email1) && registerService.checkUsername(username)) {
        		Long id = registerService.addUser(firstname, lastname, email1, address1, address2, city, state, zipcode, username, password1);
        		registerService.login(request, id);
            	map.put("id", id);
        	}
        	
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
    }//addUser()
	
    
	/**
     * Add user stage one, basic registration information
     * 
     * @param params a Map contains:
     *   <ul>
     *     <li>income - string, user's income</li>
     *     <li>householdsize - string, user's household size</li>
     *     <li>drive - string, user's drive days</li>
     *     <li>carpool - string, user's carpool days</li>
     *     <li>carpoolpeople - string, user's carpool people</li>
     *     <li>bus - string, user's bus days</li>
     *     <li>bike - string, user's bike days</li>
     *     <li>walk - string, user's walk days</li>
     *   </ul>
     * @return a Map contains:
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
    public Map addQuestionnaire(HttpServletRequest request, Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        String income = (String) params.get("income");
        String householdsize = (String) params.get("householdsize");
        String drive = (String) params.get("drive");
        String carpool = (String) params.get("carpool");
        String carpoolpeople = (String) params.get("carpoolpeople");
        String bus = (String) params.get("bus");
        String bike = (String) params.get("bike");
        String walk = (String) params.get("walk");
    	
    	if(income==null || "".equals(income.trim())){
    		map.put("reason", "income cannot be blank.");
    		return map;
    	}
    	if(householdsize==null || "".equals(householdsize.trim())){
    		map.put("reason", "householdsize cannot be blank.");
    		return map;
    	}
    	if(drive==null || "".equals(drive.trim())){
    		map.put("reason", "drive cannot be blank.");
    		return map;
    	}
    	if(carpool==null || "".equals(carpool.trim())){
    		map.put("reason", "carpool cannot be blank.");
    		return map;
    	}
    	if(carpoolpeople==null || "".equals(carpoolpeople.trim())){
    		map.put("reason", "carpoolpeople cannot be blank.");
    		return map;
    	}
    	if(bus==null || "".equals(bus.trim())){
    		map.put("reason", "bus cannot be blank.");
    		return map;
    	} 
    	if(bike==null || "".equals(bike.trim())){
    		map.put("reason", "bike cannot be blank.");
    		return map;
    	}
    	if(walk==null || "".equals(walk.trim())){
    		map.put("reason", "walk cannot be blank.");
    		return map;
    	}
    	
        try {
        	
        	int myHouseholdsize = Integer.parseInt(householdsize);
        	int myDrive = Integer.parseInt(drive);
        	int myCarpool = Integer.parseInt(carpool);
        	int myCarpoolpeople = Integer.parseInt(carpoolpeople);
        	int myBus = Integer.parseInt(bus);
        	int myBike = Integer.parseInt(bike);
        	int myWalk = Integer.parseInt(walk);
        	
        	registerService.addQuestionnaire(income, myHouseholdsize, myDrive, myCarpool, myCarpoolpeople, myBus, myBike, myWalk);
        	
        	User user = registerService.getCurrentUser();
        	
        	Map values = new HashMap();
        	String loginname = user.getLoginname();
            values.put("loginname", loginname);
            values.put("participant", user);
        	emailSender.send(user, "registration", values);
        	
        	registerService.logout(request);
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
    }//addQuestionnaire()
    
    
	/**
     * Determine if the user qualifies for quota
     * 
     * @param params a Map contains:
     *   <ul>
     *     <li>id - string, user's id</li>
     *   </ul>
     * @return a Map contains:
     *   <ul>
     *     <li>qualify - a boolean value denoting if the operation succeeds</li>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
    public Map createQuotaQualify(Map params) {
    	Map map = new HashMap();
        map.put("successful", false);
        
        String strId = (String) params.get("id");
        
        if(strId==null || "".equals(strId.trim())){
    		map.put("reason", "id cannot be blank.");
    		return map;
    	}
        
        try {
        	Long id = Long.parseLong(strId);
        	
        	boolean qualify = registerService.createQuotaQualify(id);
        	map.put("qualify", qualify);
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        return map;
    }
    
    
	/**
     * Set the users quota information - uesr must be logged in
     * 
     * @param params a Map contains:
     *   <ul>
     *     <li>interview - boolean, agree to interview</li>
     *     <li>observation - boolean, agree to observation</li>
     *   </ul>
     * @return a Map contains:
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
    public Map addQuotaInfo(Map params) {
    	Map map = new HashMap();
        map.put("successful", false);
        
        String user_interview = "non-eligible";
        String user_observation = "non-eligible";
        
        String interview = (String) params.get("interview");
        String observation = (String) params.get("observation");
        
        if(interview==null || "".equals(interview.trim())){
    		map.put("reason", "interview cannot be blank.");
    		return map;
    	}
        if(observation==null || "".equals(observation.trim())){
    		map.put("reason", "observation cannot be blank.");
    		return map;
    	}
        
        if(interview.equals("true")) {
        	user_interview = "Eligible";
        }
        if(observation.equals("true")) {
        	user_observation = "Eligible";
        }
        
        try {
        	registerService.addQuotaInfo(user_interview, user_observation);
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        return map;
    }
    
    
	/**
     * Set the users consent for non-quota only
     * 
     * @return a Map contains:
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
    public Map addConsent(Map params) {
    	Map map = new HashMap();
        map.put("successful", false);
        
        
        try {
        	registerService.addConsent();
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        return map;
    }
    

	/**
     * Delete the current user from the system
     * 
     * @return a Map contains:
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
    public Map deleteUser(Map params) {
    	Map map = new HashMap();
        map.put("successful", false);
        
        
        try {
        	registerService.deleteUser();
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        return map;
    }
    
    
    /**
     * Adds a new vehicle to the user account
     * 
     * @param params a Map contains:<br>
     *   <ul>
     *     <li>milesPerGallon - float, The miles per gallon for the new vehicle</li>
     *     <li>value - float, The approximate value of the vehicle</li>
     *     <li>milesPerYear - float, The miles per year used with this vehicle</li>
     *   </ul>
     * 
     * @return a Map contains:<br>
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *     <li>user - The UserTaxInfoDTO with all the users information</li>
     *   </ul>
     */
    public Map addVehicle(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
        	float mpg = new Float((String) params.get("milesPerGallon"));
            float value = new Float((String) params.get("value"));
            float mpy = new Float((String) params.get("milesPerYear"));
        
            Long userId = WebUtils.currentUserId();
            fundingService.addVehicle(userId, mpg, value, mpy);
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//addVehicle()
    
    
    /**
     * Returns a list of all the vehicles for this user
     * 
     * @return a Map contains:<br>
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *     <li>vehicles - The list of all the vehicles for this user</li>
     *   </ul>
     */
    public Map createGetVehicles(HttpServletRequest request, Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
        	User user = fundingService.getUser(WebUtils.currentUser());
        	UserTaxInfoDTO taxinfo = fundingService.createUserTaxInfoDTO(user.getId());
            
            request.setAttribute("vehicles", taxinfo.getVehicles());
            map.put("html", WebContextFactory.get().forwardToString("/WEB-INF/jsp/system/register_vehicles.jsp"));              
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//getVehicle()
    
    
    /**
     * Removes a vehicle from the users account
     * 
     * @param params a Map contains:<br>
     *   <ul>
     *     <li>vehicleId - int, The ID of the vehicle to remove</li>
     *   </ul>
     * 
     * @return a Map contains:<br>
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *     <li>user - The User object with all the users information</li>
     *   </ul>
     */
    public Map deleteVehicle(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            Long vehicleId = new Long((String) params.get("vehicleId"));
            
            Long userId = WebUtils.currentUserId();
            fundingService.deleteVehicle(userId, vehicleId);
            
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//removeVehicle()
    
    
    /**
     * Updates information about the vehicle
     * 
     * @param params a Map contains:<br>
     *   <ul>
     *     <li>vehicleId - int, id of the vehicle to update</li>
     *     <li>milesPerGallon - float, The miles per gallon for the new vehicle</li>
     *     <li>value - float, The approximate value of the vehicle</li>
     *     <li>milesPerYear - float, The miles per year used with this vehicle</li>
     *   </ul>
     * 
     * @return a Map contains:<br>
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
    public Map updateVehicle(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            Long vehicleId = new Long((String) params.get("vehicleId"));

            float mpg = new Float((String) params.get("milesPerGallon"));
            float value = new Float((String) params.get("value"));
            float mpy = new Float((String) params.get("milesPerYear"));
            
            this.fundingService.updateVehicle(vehicleId, mpg, value, mpy);
            
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//updateVehicle()    
    
    
    /**
     * Get tolls
     * 
     * @return a Map contains:<br>
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
    public Map getTolls(HttpServletRequest request, Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
           
        	Collection fs = registerService.getTolls();
        	
        	request.setAttribute("tolls", fs);;
        	map.put("tolls", fs);
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//getTolls()   
    
    
    /**
     * set tolls
     * @param params a Map contains:<br>
     *   <ul>
     *     <li>tollid - int, id of the toll</li>
     *     <li>boolvalue - boolean, check or unchecked</li>
     *   </ul>
     *   
     * @return a Map contains:<br>
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
    public Map setToll(HttpServletRequest request, Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        boolean boolchecked = false;
        
        String tollId = (String) params.get("tollid");
        String boolvalue = (String) params.get("boolvalue");
    	
    	if(tollId==null || "".equals(tollId.trim())){
    		map.put("reason", "tollId cannot be blank.");
    		return map;
    	}
    	if(boolvalue==null || "".equals(boolvalue.trim())){
    		map.put("reason", "boolvalue cannot be blank.");
    		return map;
    	}
        
    	if(boolvalue.equals("true")) {
    		boolchecked = true;
    	}
        
        try {
        	Long myTollId = Long.parseLong(tollId);
        	
        	registerService.setToll(myTollId, boolchecked);
        	
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//setToll()
    
    
    /**
     * check if the username is okay to use, (not taken)
     * @param params a Map contains:<br>
     *   <ul>
     *     <li>username - string, desired username</li>
     *   </ul>
     *   
     * @return a Map contains:<br>
     *   <ul>
     *     <li>available - a boolean value, the user name is available, true or false</li>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
    public Map checkUsername(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
       
        String username = (String) params.get("username");
       
    	if(username==null || "".equals(username.trim())){
    		map.put("reason", "username cannot be blank.");
    		return map;
    	}
  
        try {
        	
        	boolean available = registerService.checkUsername(username);
        	
        	map.put("available", available);
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }

        return map;
    }//checkUsername()
    
    
    /**
     * check if the email address is okay to use, (not taken)
     * @param params a Map contains:<br>
     *   <ul>
     *     <li>email - string, desired email</li>
     *   </ul>
     *   
     * @return a Map contains:<br>
     *   <ul>
     *     <li>available - a boolean value, the user name is available, true or false</li>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
    public Map checkEmail(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
       
        String email = (String) params.get("email");
       
    	if(email==null || "".equals(email.trim())){
    		map.put("reason", "email cannot be blank.");
    		return map;
    	}
  
        try {
        	
        	boolean available = registerService.checkEmail(email);
        	
        	map.put("available", available);
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }

        return map;
    }//checkEmail()
    
    
} //RegisterAgent()
