package org.pgist.criteria;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Collection;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import org.directwebremoting.WebContextFactory;
import org.pgist.cvo.CCTService;


/**
 * 
 * @author John
 *
 */
public class CriteriaAgent {
    
    
    private CriteriaService criteriaService;
    
    private CCTService cctService = null;

	public CCTService getCctService() {
		return cctService;
	}


	public void setCctService(CCTService cctService) {
		this.cctService = cctService;
	}


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
     *     <li>themeIds - string, name of the themeid's separated by commas - Optional</li>
     *     <li>objectiveIds - string, list of Object Id's - Optional</li>	
     *     <li>critSuiteId - long (string), critSuite Id 
     *     <li>na - string, description. - Optional</li>
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
        boolean bool_themes = true;
        boolean bool_objectives = true;
        
        String name = (String) params.get("name");
    	String themeIds = (String) params.get("themeIds");
    	String objectiveIds = (String) params.get("objectiveIds");
    	String strCritSuiteId = (String) params.get("critSuiteId");
    	String na = (String) params.get("na");
    	
    	if(name==null || "".equals(name.trim())){
    		map.put("reason", "Criterion name cannot be empty.");
    		return map;
    	}
    	
    	if(themeIds==null || "".equals(themeIds.trim())){
    		bool_themes = false;
    	}
    	
    	if(strCritSuiteId==null || "".equals(strCritSuiteId.trim())){
    		map.put("reason", "critSuite cannot be empty.");
    		return map;
    	}
    	if(objectiveIds==null || "".equals(objectiveIds.trim())){
    		bool_objectives = false;
    	}
    	if(na==null || "".equals(na.trim())){
    		na = "NONE";
    	}
    	Long critSuiteId = new Long(strCritSuiteId);
    	
        try {
        	String[] themeIdList;
        	String[] objectiveIdList;
        	Set themes = new HashSet();
        	Set objectives = new HashSet();
        	
        	if(bool_themes) {
		    	themeIdList = themeIds.split(",");
		    	themes = criteriaService.getThemeObjects(themeIdList);
        	}
        	if(bool_objectives) {
	        	objectiveIdList = objectiveIds.split(",");
	        	objectives = criteriaService.getObjectiveObjects(objectiveIdList);
        	}
        	
        	Criteria c = c = criteriaService.addCriterion(bool_themes, bool_objectives, name, critSuiteId, themes, objectives, na);
        	
        	map.put("id", c.getId());
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
     *     <li>id - string, id of the criterion</li>
     *     <li>name - string, name of the criteia</li>
     *     <li>themeIds - string, name of the themeid's separated by commas - Optional</li>
     *     <li>objectiveIds - string, list of Object Id's - Optional</li>	
     *     <li>na - string, descript. - Optional</li>
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
        boolean bool_themes = true;
        boolean bool_objectives = true;
        
        String strId = (String) params.get("id");
        String name = (String) params.get("name");
    	String themeIds = (String) params.get("themeIds");
    	String objectiveIds = (String) params.get("objectiveIds");
    	String na = (String) params.get("na");
    	
    	if(strId==null || "".equals(strId.trim())){
    		map.put("reason", "Criterion id cannot be empty.");
    		return map;
    	}
    	if(name==null || "".equals(name.trim())){
    		map.put("reason", "Criterion name cannot be empty.");
    		return map;
    	}
    	if(themeIds==null || "".equals(themeIds.trim())){
    		bool_themes = false;
    	}
    	if(objectiveIds==null || "".equals(objectiveIds.trim())){
    		bool_objectives = false;
    	}
    	if(na==null || "".equals(na.trim())){
    		na = "NONE";
    	}
    	Long id = new Long(strId);
       
        try {
        	Criteria c = criteriaService.getCriterionById(id);
        	
        	String[] themeIdList;
        	String[] objectiveIdList;
        	Set themes = new HashSet();
        	Set objectives = new HashSet();
        	
        	if(bool_themes) {
		    	themeIdList = themeIds.split(",");
		    	themes = criteriaService.getThemeObjects(themeIdList);
        	}
        	if(bool_objectives) {
	        	objectiveIdList = objectiveIds.split(",");
	        	objectives = criteriaService.getObjectiveObjects(objectiveIdList);
        	}
        	
        	criteriaService.editCriterion(bool_themes, bool_objectives, c, name, themes, objectives, na);
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
    }//editCriterion()
    
    
    /**
     * Get the theme list for the given cct.
     * 
     * @param params a Map contains:
     *   <ul>
     *     <li>cctId - int, the id of an CriteriaRefId object</li>
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
     *           <li>cct - A CCT Id</li>
     *           <li>themes - A list of theme objects</li>
     *     		 <li>html - a HTML source segment. (Generated by /WEB-INF/jsp/criteria/criteriaAssoc_themes.jsp)<br>
     *           The following variables are available for use in the jsp:
     *                  <ul>
     *                  <li>themes - theme objects</li>
     *                  </ul>
     *           </li>
     *         </ul>
     *     </li>
     *   </ul>
     */
    public Map getThemes(HttpServletRequest request, Map params) {
        Map map = new HashMap();
        map.put("successful", false);    
        
        
        Long cctId = new Long((String) params.get("cctId"));
  
        try {        	
            List themes = criteriaService.getThemes(cctId);
            
            request.setAttribute("themes", themes); 
            map.put("themes", themes);
        	map.put("html", WebContextFactory.get().forwardToString("/WEB-INF/jsp/criteria/criteriaAssoc_themes.jsp"));
            map.put("successful", true);   
            
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//getThemes()
    
    
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
            Long cctId = new Long((String) params.get("cctId"));
            
            criteriaService.publish(cctId);
           
            map.put("successful", true);
        } catch(Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//publish()
    
    
    /**
     * Get all the weights of the current participant to the given CriteriaSuite
     * 
     * @param params a Map contains:
     *   <ul>
     *     <li>critSuiteId - string, the critSuiteId</li>
     *   </ul>
     * @return a Map contains:
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *     <li>weights - a list of CriteriaWeight object</li>
     *     <li>html - a HTML source segment. (Generated by /WEB-INF/jsp/criteria/criteriaAssoc_weights.jsp)<br>
     *         The following variables are available for use in the jsp:
     *         <ul>
     *          <li>weights- set of CriteriaUserWeight</li>
     *         </ul>
     *     </li>
     *   </ul>
     */
    public Map getWeights(HttpServletRequest request, Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        String strCritSuiteId = (String) params.get("critSuiteId");
        
        if(strCritSuiteId==null || "".equals(strCritSuiteId.trim())){
        	map.put("reason", "critSuiteId cannot be null.");
    		return map;	
        }
        
        Long critSuiteId = new Long(strCritSuiteId);
        
        try {
        	Set weights = criteriaService.getWeights(critSuiteId);
            
        	request.setAttribute("weights", weights);
            
        	map.put("html", WebContextFactory.get().forwardToString("/WEB-INF/jsp/criteria/criteriaAssoc_weights.jsp"));
            
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
    }//getWeights()
    
    
    /**
     * Get the criteriaSuite Object
     * @param params An empty map.
     * <ul>
     *     <li>critSuiteId - string, critSuiteId - Optional</li>
     * </ul>
     * @return a Map contains:
     *   <ul>
     *     <li>criteria - A list of Criteria objects</li>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *     <li>html - a HTML source segment. (Generated by /WEB-INF/jsp/criteria/criteria.jsp)<br>
     *                  The following variables are available for use in the jsp:
     *                  <ul>
     *                    <li>criteria - criteria objects/li>
     *                  </ul>
     *           </li>
     *   </ul>
     */
    public Map getCriteriaSuiteById(HttpServletRequest request, Map params) {
    	
    	Map map = new HashMap();
        map.put("successful", false);
        boolean useCritSuiteId= true;
       
        String strCritSuiteId = (String) params.get("critSuiteId");
        if(strCritSuiteId==null || "".equals(strCritSuiteId.trim())){
    		useCritSuiteId = false;
    	}
        
        try {
        	Long critSuiteId = new Long(strCritSuiteId);
        	CriteriaSuite cs = criteriaService.getCriteriaSuiteById(critSuiteId);
        	
        	request.setAttribute("critSuite", cs); 
        	map.put("html", WebContextFactory.get().forwardToString("/WEB-INF/jsp/criteria/criteriaAssoc_weights.jsp"));
        	map.put("critSuite", cs);
        	map.put("successful", true);
        	
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
    } //getCriteriaSuiteById()
    
    
    /**
     * Get all the Criterion Available
     * @param params An empty map.
     * <ul>
     *     <li>critSuiteId - string, critSuiteId - Optional</li>
     * </ul>
     * @return a Map contains:
     *   <ul>
     *     <li>criteria - A list of Criteria objects</li>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *     <li>html - a HTML source segment. (Generated by /WEB-INF/jsp/criteria/criteria.jsp)<br>
     *                  The following variables are available for use in the jsp:
     *                  <ul>
     *                    <li>criteria - criteria objects/li>
     *                  </ul>
     *           </li>
     *   </ul>
     */
    public Map getAllCriterion(HttpServletRequest request, Map params) {
    	
    	Map map = new HashMap();
        map.put("successful", false);
        boolean useCritSuiteId= true;
       
        String strCritSuiteId = (String) params.get("critSuiteId");
        if(strCritSuiteId==null || "".equals(strCritSuiteId.trim())){
    		useCritSuiteId = false;
    	}
        
        try {
        	Collection criteria;
        	if(useCritSuiteId){
        		Long critSuiteId = new Long(strCritSuiteId);
        		criteria = criteriaService.getAllCriterion(critSuiteId);
        	} else {
        		criteria = criteriaService.getAllCriterion();
        	}
        	request.setAttribute("criteria", criteria); 
        	map.put("criteria", criteria);
        	//map.put("html", WebContextFactory.get().forwardToString("/WEB-INF/jsp/criteria/criteria.jsp"));
        	map.put("successful", true);
        	
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
    } //getAllCriterion()
    
    
    /**
	  * add Objective 
	 * @param params a Map contains:
	 *   <ul>
	 *     <li>description - string, name</li>
	 *   </ul>
	 * @return a Map contains:
	 *   <ul>
	 *     <li>id - id of the added objective</li>
	 *     <li>successful - a boolean value denoting if the operation succeeds</li>
	 *     <li>reason - reason why operation failed (valid when successful==false)</li>
	 *   </ul>
	  */
	  public Map addObjective(Map params) {
		  Map map = new HashMap();
		  map.put("successful", false);
		  String description = (String) params.get("description");
	    
		  if(description==null || "".equals(description.trim())){
			  map.put("reason", "Objective name cannot be empty.");
			  return map;
		  }
		  
		  try {  	
			  Objective o = criteriaService.addObjective(description);
			  map.put("id", o.getId());
			  map.put("successful", true);	
		  } catch (Exception e) {
			  e.printStackTrace();
			  map.put("reason", e.getMessage());
		  }
	    
		  return map;    	 
	  } //addObjective()

	  
	/**
     * Get all objectives
     * @param params An empty map.
     * @return a Map contains:
     *   <ul>
     *     <li>objectives - A set of Objective objects</li>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *     <li>html - a HTML source segment. (Generated by /WEB-INF/jsp/criteria/criteriaAssoc_objectives.jsp)<br>
     *         The following variables are available for use in the jsp:
     *         <ul>
     *         		<li>objectives- objective objects</li>
     *         </ul>
     *      </li>
     *   </ul>
     */
    public Map getObjectives(HttpServletRequest request, Map params) {
     	Map map = new HashMap();
        map.put("successful", false);
        
        try {
        	Collection objectives = criteriaService.getObjectives();
        	
        	request.setAttribute("objectives", objectives); 
        	map.put("objectives", objectives);
        	map.put("html", WebContextFactory.get().forwardToString("/WEB-INF/jsp/criteria/criteriaAssoc_objectives.jsp"));
        	map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        return map;    	 
    } //getObjectives()
     
     
     /**
     * deleteObjective
     * @param params a Map contains:
     *   <ul>
     *     <li>id - id object</li>
     *   </ul>
     *   
     * @return a Map contains:
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
    public Map deleteObjective(Map params) {
	   Map map = new HashMap();
       map.put("successful", false);
       
       String strId = (String)params.get("id");
       
       if(strId==null || "".equals(strId.trim())){
       		map.put("reason", "Objective id cannot be null.");
       		return map;	
       }
       
       Long id = Long.parseLong(strId); 
       
       try {
       		criteriaService.deleteObjective(id);
       		map.put("successful", true);
       } catch (Exception e) {
           	e.printStackTrace();
           	map.put("reason", e.getMessage());
       } 
       
       return map;
    } //deleteObjective()
     
     
    /**
     * Set the criteria weight of the current user.
     * 
     * @param params a Map contains:
     *   <ul>
     *     <li>critSuiteId - int, id of a CriteriaSuite object</li>
     *     <li>critId - int, id of a CriteriaRef object</li>
     *     <li>weight - int, weight value</li>
     *   </ul>
     *   
     * @return a Map contains:
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
    public Map setCriteriaWeight(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        String strSuiteId = (String) params.get("critSuiteId");
        String strCritId= (String) params.get("critId");
    	String strWeight = (String) params.get("weight");
    	
    	if(strSuiteId==null || "".equals(strSuiteId.trim())){
    		map.put("reason", "Crit Suite id cannot be empty.");
    		return map;
    	}
    	if(strCritId==null || "".equals(strCritId.trim())){
    		map.put("reason", "Criterion id cannot be empty.");
    		return map;
    	}
    	if(strWeight==null || "".equals(strWeight.trim())){
    		map.put("reason", "Weight id cannot be empty.");
    		return map;
    	}
    	
    	Long suiteId = Long.parseLong(strSuiteId);
    	Long critId = Long.parseLong(strCritId);
    	int weight = Integer.parseInt(strWeight);
    	
        try {
        	
            criteriaService.setWeight(suiteId, critId, weight);
        	
            
            map.put("successful", true);
        } catch (Exception e) {
           e.printStackTrace();
           map.put("reason", e.getMessage());
        } 
        
        return map;
    }//setCriteriaWeight()
   
       
}//class CriteriaAgent
