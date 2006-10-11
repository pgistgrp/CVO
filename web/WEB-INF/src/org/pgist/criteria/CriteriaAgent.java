package org.pgist.criteria;

import java.util.HashMap;
import java.util.Map;


/**
 * 
 * @author kenny
 *
 */
public class CriteriaAgent {
    
    
    private CriteriaService criteriaService;
    
    
    public void setCriteriaService(CriteriaService criteriaService) {
        this.criteriaService = criteriaService;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    /**
     * Add one new criterion to the system.
     * 
     * @param params a Map contains:
     *   <ul>
     *     <li>cctId - int, the id of an CCT object</li>
     *     <li>name - string, name of the criteia</li>
     *     <li>low - string, descript</li>
     *     <li>medium - string, descript</li>
     *     <li>high - string, descript</li>
     *     <li>na - string, descript. Optional.</li>
     *   </ul>
     * @return a Map contains:
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *     <li>id - int, the id for the new Criteria object.</li>
     *   </ul>
     */
    public Map addCriterion(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
    }//addCriterion()
    
    
    /**
     * Delete a criterion from the system.
     * 
     * @param params a Map contains:
     *   <ul>
     *     <li>cctId - int, the id of an CCT object</li>
     *     <li>id - int, the id of the criterion to be deleted</li>
     *   </ul>
     * @return a Map contains:
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
    public Map deleteCriterion(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
    }//deleteCriterion()
    
    
    /**
     * Add one new criterion to the system.
     * 
     * @param params a Map contains:
     *   <ul>
     *     <li>cctId - int, the id of an CCT object</li>
     *     <li>id - int, the id of the Criteria to be edited</li>
     *     <li>name - string, name of the criteia</li>
     *     <li>low - string, descript</li>
     *     <li>medium - string, descript</li>
     *     <li>high - string, descript</li>
     *     <li>na - string, descript. Optional.</li>
     *   </ul>
     * @return a Map contains:
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
    public Map editCriterion(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
    }//editCriterion()
    
    
    /**
     * Relate several criteria to one theme.
     * 
     * @param params a Map contains:
     *   <ul>
     *     <li>cctId - int, the id of an CCT object</li>
     *     <li>themeId - int, id of the theme</li>
     *     <li>ids - string, comma separated id list for criteria to be associated</li>
     *   </ul>
     * @return a Map contains:
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
    public Map relateCriteria(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
    }//relateCriteria()
    
    
    /**
     * Derelate some criteria from a theme.
     * 
     * @param params a Map contains:
     *   <ul>
     *     <li>cctId - int, the id of an CCT object</li>
     *     <li>themeId - int, id of the theme</li>
     *     <li>ids - string, comma separated id list for criteria to be derelated</li>
     *   </ul>
     * @return a Map contains:
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
    public Map derelateCriteria(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
    }//derelateCriteria()
    
    
    /**
     * Get the theme list for the given cct.
     * 
     * @param params a Map contains:
     *   <ul>
     *     <li>cctId - int, the id of an CCT object</li>
     *   </ul>
     * 
     * @return a Map contains:
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *     <li>
     *       html - a HTML source segment. (Generated by /WEB-INF/jsp/criteria/criteriaAssoc_themes.jsp)<br>
     *       The following variables are available for use in the jsp:
     *         <ul>
     *           <li>cct - A CCT objects</li>
     *           <li>criteria - A list of Criteria objects</li>
     *         </ul>
     *     </li>
     *   </ul>
     */
    public Map getThemes(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
    }//getThemes()
    
    
    /**
     * Add one all or unrelated criteria to the given theme.
     * 
     * @param params a Map contains:
     *   <ul>
     *     <li>cctId - int, the id of an CCT object</li>
     *     <li>themeId - int, id of the theme. Optional, if omitted, all criteria will be returned</li>
     *   </ul>
     * @return a Map contains:
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *     <li>
     *       html - a HTML source segment. (for themeId!=null Generated by /WEB-INF/jsp/criteria/criteriaAssoc_criteria.jsp,
     *              for themeId==null Generated by /WEB-INF/jsp/criteria/criteriaMgr_criteria.jsp)<br>
     *       The following variables are available for use in the jsp:
     *         <ul>
     *           <li>cct - A CCT objects</li>
     *           <li>criteria - A list of Criteria objects</li>
     *         </ul>
     *     </li>
     *   </ul>
     */
    public Map getCriteria(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
    }//getCriteria()
    
    
    /**
     * Get a Criteria object with the given id.
     * 
     * @param params a Map contains:
     *   <ul>
     *     <li>cctId - int, the id of an CCT object</li>
     *     <li>id - int, id of the Criteia object</li>
     *   </ul>
     * @return a Map contains:
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *     <li>criterion - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
    public Map getCriterionById(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
    }//getCriterionById()
    
    
    /**
     * Publish the current association of the given cct.
     * 
     * @param params a Map contains:
     *   <ul>
     *     <li>cctId - int, the id of an CCT object</li>
     *   </ul>
     * @return a Map contains:
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
    public Map publish(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
    }//publish()
    
    
}//class CriteriaAgent
