package org.pgist.funding;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.directwebremoting.WebContextFactory;
import org.pgist.users.User;
import org.pgist.util.PageSetting;
import org.pgist.util.WebUtils;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * 
 * @author kenny
 *
 */
public class FundingAgent {
    
    
    private FundingService fundingService;
    
    
    public void setFundingService(FundingService fundingService) {
        this.fundingService = fundingService;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
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
    public Map getVehicles(HttpServletRequest request, Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
        	User user = this.fundingService.getUser(WebUtils.currentUser());
        	UserTaxInfoDTO taxinfo = this.fundingService.createUserTaxInfoDTO(user.getId());
            
            request.setAttribute("vehicles", taxinfo.getVehicles());
            map.put("html", WebContextFactory.get().forwardToString("/WEB-INF/jsp/funding/fundingCalc_vehicles.jsp"));
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//addVehicle()
    
    
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
            Long userId = WebUtils.currentUserId();
            float mpg = new Float((String) params.get("milesPerGallon"));
            float value = new Float((String) params.get("value"));
            float mpy = new Float((String) params.get("milesPerYear"));
            
            map.put("user", this.fundingService.addVehicle(userId, mpg, value, mpy));
            
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//addVehicle()

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
            Long userId = WebUtils.currentUserId();
            Long vehicleId = new Long((String) params.get("vehicleId"));
            
            map.put("user", this.fundingService.deleteVehicle(userId, vehicleId));
            
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//removeVehicle()
        
    /**
     * Get a FundingSource object by id
     * 
     * @param params a Map contains:<br>
     *   <ul>
     *     <li>id - int, id of a FundingSource object</li>
     *   </ul>
     * 
     * @return a Map contains:<br>
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *     <li>source - a FundingSource object</li>
     *   </ul>
     */
    public Map getFundingSourceById(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            Long id = new Long((String) params.get("id"));
            
            FundingSource source = fundingService.getFundingSourceById(id);
            
            map.put("source", source);
            
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//getFundingSourceById()
    
    
    /**
     * Get a FundingSourceAlternative object by id
     * 
     * @param params a Map contains:<br>
     *   <ul>
     *     <li>id - int, id of a FundingSourceAlternative object</li>
     *   </ul>
     *   
     * @return a Map contains:<br>
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *     <li>alternative - a FundingSourceAlternative object</li>
     *   </ul>
     */
    public Map getFundingSourceAltById(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            Long id = new Long((String) params.get("id"));
            
            FundingSourceAlternative alternative = fundingService.getFundingSourceAltById(id);
            
            map.put("alternative", alternative);
            
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//getFundingSourceAltById()
    
    
    /**
     * Get funding sources
     * 
     * @param params a Map contains:<br>
     *   <ul>
     *     <li>page - int, the current page</li>
     *     <li>count - int, number of records shown on one page</li>
     *   </ul>
     *   
     * @return a Map contains:<br>
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *     <li>
     *       html - string, html source segment generated by "/WEB-INF/jsp/funding/fundingMgr_sources.jsp". In this page the following variables are avaiable:<br>
     *         <ul>
     *           <li>fundings - a list of FundingSource objects</li>
     *           <li>setting - a PageSetting object</li>
     *         </ul>
     *     </li>
     *   </ul>
     */
    public Map getFundingSources(HttpServletRequest request, Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            PageSetting setting = new PageSetting();
            
            setting.setPage((String) request.getParameter("page"));
            setting.setRowOfPage((String) request.getParameter("count"));

            //This puts the variable into the bit of jsp from above.  If you put it in the params map, then it shows up in the javascript
            Collection fundings = fundingService.getFundingSources();
            
            request.setAttribute("fundings", fundings);
            map.put("html", WebContextFactory.get().forwardToString("/WEB-INF/jsp/funding/fundingMgr_sources.jsp"));
            
            map.put("fundings", fundings);        
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//getFundingSources()
    
    
    /**
     * Create a new funding source
     * 
     * @param params a Map contains:<br>
     *   <ul>
     *     <li>name - string, name of the funding source</li>
     *     <li>location - string, location of the funding source</li>
     *     <li>type - int, Type of calculation from the FundingSource</li>
     *   </ul>
     *   
     * @return a Map contains:<br>
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *     <li>id - the id of the newly created funding source</li>
     *   </ul>
     */
    public Map createFundingSource(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            String name = (String) params.get("name");
            if (name==null || name.trim().length()==0) {
                map.put("reason", "name is required.");
            }
            String location = (String) params.get("location");
            int type = Integer.parseInt((String) params.get("type"));
            
            FundingSource funding = fundingService.createFundingSource(name, type, location);
            
            map.put("id", funding.getId());
            
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//createFundingSource()
    
    
    /**
     * Edit a given funding source
     * 
     * @param params a Map contains:<br>
     *   <ul>
     *     <li>id - int, id of the FundingSource object</li>
     *     <li>name - string, name of the funding source</li>
     *     <li>location - string, location of the funding source</li>
     *     <li>type - int, Type of calculation from the FundingSource</li>
     *   </ul>
     *   
     * @return a Map contains:<br>
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
    public Map editFundingSource(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            Long id = new Long((String) params.get("id"));
            
            String name = (String) params.get("name");
            if (name==null || name.trim().length()==0) {
                map.put("reason", "name is required.");
            }
            String location = (String) params.get("location");
            int type = Integer.parseInt((String) params.get("type"));
            
            fundingService.editFundingSource(id, name, type, location);
            
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//editFundingSource()
    
    
    /**
     * Delete a given funding source
     * 
     * @param params a Map contains:<br>
     *   <ul>
     *     <li>id - int, id of the FundingSource object</li>
     *   </ul>
     *   
     * @return a Map contains:<br>
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
    public Map deleteFundingSource(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            Long id = new Long((String) params.get("id"));
            
            fundingService.deleteFundingSource(id);
            
            map.put("successful", true);
        } catch (DataIntegrityViolationException dive) {
            map.put("reason", "This funding source is already used, you can't delete it.");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//deleteFundingSource()
    
    
    /**
     * Create a new funding source alt
     * 
     * @param params a Map contains:<br>
     *   <ul>
     *     <li>id - int, the id of the funding source this alt associated</li>
     *     <li>name - string, name of the funding source alt</li>
     *     <li>revenue - float, revenue of the funding source alt</li>
     *     <li>taxRate - float, tax rate of the funding source alt</li>
     *     <li>source  - String, name or link to a source</li>
     *     <li>avgCost - float, The average cost to each resident</li>
     *     <li>toll    - boolean, "true" if the alternative is a toll</li>
     *     <li>peakHourTrips - float, The cost for peak hour trips</li>
     *     <li>offPeakTrips  - float, The cost for off peak hour trips</li>
     *   </ul>
     *   
     * @return a Map contains:<br>
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *     <li>id - the id of the newly created funding source alt</li>
     *   </ul>
     */
    public Map createFundingSourceAlt(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            String name = (String) params.get("name");
            if (name==null || name.trim().length()==0) {
                map.put("reason", "name is required.");
                return map;
            }
            Long id = new Long((String) params.get("id"));            
            float revenue = new Float((String) params.get("revenue"));
            float taxRate = new Float((String) params.get("taxRate"));
            String source = (String) params.get("source");
            float avgCost = new Float((String) params.get("avgCost"));
            boolean toll = Boolean.parseBoolean((String) params.get("toll"));
            float peakHourTrips = 0;
            float offPeakTrips = 0;
            if(toll) {
                peakHourTrips = new Float((String) params.get("peakHourTrips"));
                offPeakTrips = new Float((String) params.get("offPeakTrips"));            	
            }
            
            FundingSourceAlternative alt = fundingService.createFundingSourceAlt(id, name, revenue, taxRate, source, avgCost, toll, peakHourTrips, offPeakTrips);
            
            map.put("id", alt.getId());
            
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//createFundingSourceAlt()
    
    
    /**
     * Edit a given funding source alt
     * 
     * @param params a Map contains:<br>
     *   <ul>
     *     <li>id - int, id of the FundingSourceAlternative object</li>
     *     <li>name - string, name of the funding source alt</li>
     *     <li>revenue - float, revenue of the funding source alt</li>
     *     <li>taxRate - float, tax rate of the funding source alt</li>
     *     <li>source  - String, name or link to a source</li>
     *     <li>avgCost - float, The average cost to each resident</li>
     *     <li>toll    - boolean, "true" if the alternative is a toll</li>
     *     <li>peakHourTrips - float, The cost for peak hour trips</li>
     *     <li>offPeakTrips  - float, The cost for off peak hour trips</li>
     *   </ul>
     *   
     * @return a Map contains:<br>
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
    public Map editFundingSourceAlt(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            Long id = new Long((String) params.get("id"));
            
            String name = (String) params.get("name");
            if (name==null || name.trim().length()==0) {
                map.put("reason", "name is required.");
            }
            
            float revenue = new Float((String) params.get("revenue"));
            float taxRate = new Float((String) params.get("taxRate"));
            String source = (String) params.get("source");
            float avgCost = new Float((String) params.get("avgCost"));
            boolean toll = Boolean.parseBoolean((String) params.get("toll"));
            float peakHourTrips = 0;
            float offPeakTrips = 0;
            if (toll) {
                peakHourTrips = Float.parseFloat((String) params.get("peakHourTrips"));
                offPeakTrips = Float.parseFloat((String) params.get("offPeakTrips"));            	
            }
            
            fundingService.editFundingSourceAlt(id, name, revenue, taxRate, source, avgCost, toll, peakHourTrips, offPeakTrips);
            
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//editFundingSourceAlt()
    
    
    /**
     * Delete a given funding source alt
     * 
     * @param params a Map contains:<br>
     *   <ul>
     *     <li>id - int, id of the FundingSourceAlternative object</li>
     *   </ul>
     *   
     * @return a Map contains:<br>
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
    public Map deleteFundingSourceAlt(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            Long id = new Long((String) params.get("id"));
            
            fundingService.deleteFundingSourceAlt(id);
            
            map.put("successful", true);
        } catch (DataIntegrityViolationException dive) {
            map.put("reason", "This funding source alternative is already used, you can't delete it.");
        } catch (Exception e) {
            System.out.println("-----> "+e.getClass().getName());
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//deleteFundingSourceAlt()
    
    /**
     * Set the definition of funding in a decision situation. According the operation code,
     * the given FundingAlternative will be associated or unassociated with the give suite.
     * 
     * @param params a Map contains:
     *         <ul>
     *           <li>suiteId - int, id for a FundingSuite object</li>
     *           <li>altId - int, id for a FundingAlternative object</li>
     *           <li>operation - string, "add" | "remove"</li>
     *         </ul>
     * @return a Map contains:
     *         <ul>
     *           <li>successful - a boolean value denoting if the operation succeeds</li>
     *           <li>reason - reason why operation failed (valid when successful==false)</li>
     *         </ul>
     */
    public Map setFundingDef(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            Long suiteId = new Long((String) params.get("suiteId"));
            Long altId = new Long((String) params.get("altId"));
            String operation = (String) params.get("operation");
            
            if ("add".equals(operation)) {
                fundingService.relateFundingAlt(suiteId, altId);
            } else if ("remove".equals(operation)) {
                fundingService.derelateFundingAlt(suiteId, altId);
            } else {
                map.put("reason", "unknown operation: "+operation);
                return map;
            }
            
            map.put("successful", true);
        } catch (UnknownFundingSuiteException e) {
            map.put("reason", "Could not find the funding suite specified");             
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
    }//setFundingDef()
    

    /**
     * ????? Serious Security Issue
     * 
     * Returns the user you are looking for with the provided ID
     * 
     * @param params a Map contains:<br>
     *   <ul>
     *     <li>userId - The ID of the user</li>	 
     *   </ul>
     *   
     * @return a Map contains:<br>
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *     <li>
     *       html - string, html source segment generated by "/WEB-INF/jsp/funding/fundingCalc_estimates.jsp". In this page the following variables are avaiable:<br>
     *         <ul>
     *           <li>user - The current User object to load into the page</li>
     *         </ul>
     *     </li>
     *   </ul>
     */
    public Map lookupUserById(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            Long userId = new Long((String) params.get("userId"));
        	map.put("user", this.fundingService.createUserTaxInfoDTO(userId));
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//getUserById
    
    
    /**
     * Updates the user with all of the specified values.  You can only use this to update the parameters
     * in the user object.  You cannot use this to update the number of vehicles.  The vehicles associated
     * with the user passed in will be ignored.  For that you must use the add/remove Vehicle functions
     * 
     * @param params a Map contains:<br>
     *   <ul>
     *     <li>user - UserTaxInfoDTO used to move information</li>
     *   </ul>
     * 
     * @return a Map contains:<br>
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *     <li>user - The User object with all the users information</li>
     *   </ul>
     */
    public Map updateUser(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            UserTaxInfoDTO user = (UserTaxInfoDTO) params.get("user");

            if (!WebUtils.checkUser(user.getUserId())) {
                throw new Exception("You are not the owner of this UserTaxInfo!");
            }
            
            this.fundingService.updateUserTaxInfo(user);
            
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//update User()    
    
    /**
     * Set the estimate for user commute in tax calculator.
     * 
     * @param user - UserTaxInfoDTO, DTO used to hold the tax information from the page</li>	 
     * @return a Map contains:<br>
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
	 *     <li>user - The UserTaxInfoDTO object with the commuting information updated from the tables</li>
     *   </ul>
     */
    public Map calcCommute(UserTaxInfoDTO user) {
    	
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            if (!WebUtils.checkUser(user.getUserId())) {
                throw new Exception("You are not the owner of this UserTaxInfo!");
            }
            
        	map.put("reason", "Bike Days = " + user.getBikeDays() + " ID = " + user.getUserId());
			
        	//Find the commute object
        	map.put("user", fundingService.createCommute(user));
            
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//setEstimates()
    
    
    /**
     * Set the User Annual Costs Report
     * 
     * @param user				The UserTaxInfoDTO that you want a report generated for
     * @param fundingSuiteID	The funding suite used to generate the report
     * @return a Map contains:<br>
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *     <li>
     *       html - string, html source segment generated by "/WEB-INF/jsp/funding/fundingCalc_report.jsp". In this page the following variables are avaiable:<br>
     *         <ul>
     *           <li>user - The UserTaxInfoDTO object with the commuting information updated from the tables</li>
     *         </ul>
     *     </li>
     *   </ul>
     */
    public Map calcCostReport(HttpServletRequest request, UserTaxInfoDTO user, long fundingSuiteId) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            if (!WebUtils.checkUser(user.getUserId())) {
                throw new Exception("You are not the owner of this UserTaxInfo!");
            }
            
            request.setAttribute("user", this.fundingService.createCostReport(user, fundingSuiteId));
            
            map.put("html", WebContextFactory.get().forwardToString("/WEB-INF/jsp/funding/fundingCalc_report.jsp"));            
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//setEstimates()
    
    
    /**
     * Get Funding Suite
     * 
     * @param id - funding suite Id
     * @param fundingSuiteID	The funding suite used to generate the report
     * @return a Map contains:<br>
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *     <li>fundSuite - funding suite</li>
     *   </ul>
     */
    public Map getFundingSuite(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            
        	Long id = new Long((String) params.get("id"));
            FundingSourceSuite fundSuite = fundingService.getFundingSuite(id);
            map.put("fundSuite", fundSuite);
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//getFundingSuite()
    
    
}//class FundingAgent
