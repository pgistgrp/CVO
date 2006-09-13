package org.pgist.system;

import java.util.HashMap;
import java.util.Map;


/**
 * DWR AJAX Agent class.<br>
 * Provide AJAX services to client programs.<br>
 * In this document, all the NON-AJAX methods are marked out. So all methods
 * <span style="color:red;">without</span> such a description
 * <span style="color:red;">ARE</span> AJAX service methods.<br>
 *
 * @author kenny
 *
 */
public class SystemAgent {
    
    
    private SystemService systemService;
    
    
    /**
     * This is not an AJAX service method.
     *
     * @param systemService
     */
    public void setSystemService(SystemService systemService) {
        this.systemService = systemService;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    /**
     * Create a new Feedback for the current user.
     * 
     * @param a map contains:
     *   <ul>
     *     <li>feedback - string, the feedback from the user</li>
     *     <li>action - string, the action where the feedback is created</li>
     *   </ul>
     * 
     * @return a map contains:
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
    public Map createFeedback(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            String s = (String) params.get("feedback");
            String action = (String) params.get("action");
            
            if (s==null || "".equals(s.trim())) {
                map.put("reason", "The content of feedback can't be empty!");
                return map;
            }
            
            if (action==null || "".equals(action.trim())) {
                map.put("reason", "Unknown Action!");
                return map;
            }
            
            systemService.createFeedback(action, s);
            
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
    }//createFeedback()
    
    
}//class SystemAgent
