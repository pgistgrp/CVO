package org.pgist.criteria;

import java.util.HashMap;
import java.util.Map;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.directwebremoting.WebContextFactory;


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
        
        String name = (String) params.get("name");
    	String low = (String) params.get("low");
    	String medium = (String) params.get("medium");
    	String high = (String) params.get("high");
    	String na = (String) params.get("na");
    	
    	if(name==null || "".equals(name.trim())){
    		map.put("reason", "Criterion name cannot be empty.");
    		return map;
    	}
    	if(low==null || "".equals(low.trim())){
    		map.put("reason", "Criterion low cannot be empty.");
    		return map;
    	}
    	if(medium==null || "".equals(medium.trim())){
    		map.put("reason", "Criterion medium cannot be empty.");
    		return map;
    	}
    	if(high==null || "".equals(high.trim())){
    		map.put("reason", "Criterion high cannot be empty.");
    		return map;
    	}
    	
        try {
        	Criteria criteria = criteriaService.addCriterion(name, low, medium, high, na);
        	map.put("id", criteria.getId());
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
     *     <li>id - String, the id of the criterion to be deleted</li>
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
        
        String strId = (String)params.get("id");
        
        if(strId==null || "".equals(strId.trim())){
        	map.put("reason", "Criterion id cannot be null.");
    		return map;	
        }
        
        Long id = Long.parseLong(strId); 
        
        try {
        	criteriaService.deleteCriterion(id);
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
    }//deleteCriterion()
    
    
    /**
     * Edit an existing criterion.
     * 
     * @param params a Map contains:
     *   <ul>
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
        
        String strId = (String) params.get("id");
        String name = (String) params.get("name");
    	String low = (String) params.get("low");
    	String medium = (String) params.get("medium");
    	String high = (String) params.get("high");
    	String na = (String) params.get("na");
    	
    	if(strId==null || "".equals(strId.trim())){
        	map.put("reason", "Criterion id cannot be null.");
    		return map;	
        }
    	if(name==null || "".equals(name.trim())){
    		map.put("reason", "Criterion name cannot be empty.");
    		return map;
    	}
    	if(low==null || "".equals(low.trim())){
    		map.put("reason", "Criterion low cannot be empty.");
    		return map;
    	}
    	if(medium==null || "".equals(medium.trim())){
    		map.put("reason", "Criterion medium cannot be empty.");
    		return map;
    	}
    	if(high==null || "".equals(high.trim())){
    		map.put("reason", "Criterion high cannot be empty.");
    		return map;
    	}
        
        Long id = Long.parseLong(strId); 
       
        try {
        	Criteria c = criteriaService.getCriterionById(id);
        	criteriaService.editCriterion(c, name, low, medium, high, na);
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
     * Get all unrelated criteria to the given theme.
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
    public Map getAvailableCriteria(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
    }//getAvailableCriteria()
    
    
    /**
     * Get a Criteria object with the given id.
     * 
     * @param params a Map contains:
     *   <ul>
     *     <li>id - int, id of the Criteia object</li>
     *   </ul>
     * @return a Map contains:
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *     <li>criterion - if successful the criteria object</li>
     *   </ul>
     */
    public Map getCriterionById(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        String strId = (String)params.get("id");
        
        if(strId==null || "".equals(strId.trim())){
        	map.put("reason", "Criterion id cannot be null.");
    		return map;	
        }
        
        Long id = Long.parseLong(strId);
        
        try {
        	Criteria c = criteriaService.getCriterionById(id);
        	map.put("criterion", c);
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
    
    
    /**
     * Choose one Criteria to work on for the current participant.
     * 
     * @param params a Map contains:
     *   <ul>
     *     <li>isid - int, the id of an InfoStructure object</li>
     *     <li>critId - int, the id of an Criteria object</li>
     *   </ul>
     * @return a Map contains:
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
    public Map addCriterionSlider(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
    }//addCriterionSlider()
    
    
    /**
     * Delete one Criteria from the current participant
     * 
     * @param params a Map contains:
     *   <ul>
     *     <li>isid - int, the id of an InfoStructure object</li>
     *     <li>critId - int, the id of an Criteria object</li>
     *   </ul>
     * @return a Map contains:
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
    public Map deleteCriterionSlider(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
    }//deleteCriterionSlider()
    
    
    /**
     * Get all the weights of the current participant to the given InfoStructure
     * 
     * @param params a Map contains:
     *   <ul>
     *     <li>isid - int, the id of an InfoStructure object</li>
     *   </ul>
     * @return a Map contains:
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *     <li>weights - a list of CriteriaWeight object</li>
     *   </ul>
     */
    public Map getWeights(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
    }//getWeights()
    
    
    /**
     * Get all the weights of the current participant to the given InfoStructure
     * 
     * @param params a Map contains:
     *   <ul>
     *     <li>isid - int, the id of an InfoStructure object</li>
     *     <li>critId - int, the id of an Criteria object</li>
     *     <li>weight - int, weight value</li>
     *   </ul>
     * @return a Map contains:
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
    public Map setWeight(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
    }//setWeight()
    
    
    /**
     * Get all the Criterion Available
     * @param params An empty map.
     * @return a Map contains:
     *   <ul>
     *     <li>criteria - A list of Criteria objects</li>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *     <li>html- </li>
     *   </ul>
     */
    public Map getAllCriterion(HttpServletRequest request, Map params) {
    	
    	Map map = new HashMap();
        map.put("successful", false);
        
        try {
        	Collection criteria = criteriaService.getAllCriterion();
        	request.setAttribute("criteria", criteria);  
        	map.put("criteria", criteria);
        	map.put("html", WebContextFactory.get().forwardToString("/WEB-INF/jsp/criteria/criteria.jsp"));
        	map.put("successful", true);
        	
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
    } //getAllCriterion()
    
    
}//class CriteriaAgent
