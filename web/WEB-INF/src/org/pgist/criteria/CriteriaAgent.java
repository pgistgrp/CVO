package org.pgist.criteria;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.directwebremoting.WebContextFactory;
import org.pgist.cvo.CCTService;
import org.pgist.cvo.Theme;
import org.pgist.discussion.InfoObject;


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
     * Add one new criterion to the system. All associates now
     * 
     * @param params a Map contains:
     *   <ul>
     *     <li>name - string, name of the criteia</li>
     *     <li>critSuiteId - long, id of crit suite</li>
     *     <li>infoObjectIds - string, name of the themeid's separated by commas - Optional</li>
     *     <li>objectiveIds - string, list of Object Id's - Optional</li>	
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
        boolean bool_infoObjects = true;
        boolean bool_objectives = true;
        
        String name = (String) params.get("name");
        String strCritSuiteId = (String) params.get("critSuiteId");
    	String infoObjectIds = (String) params.get("infoObjectIds");
    	String objectiveIds = (String) params.get("objectiveIds");
    	String na = (String) params.get("na");
    	
    	if(name==null || "".equals(name.trim())){
    		map.put("reason", "Criterion name cannot be empty.");
    		return map;
    	}
    	
    	if(strCritSuiteId==null || "".equals(strCritSuiteId.trim())){
    		map.put("reason", "critSuiteId name cannot be empty.");
    		return map;
    	}
    	
    	if(infoObjectIds==null || "".equals(infoObjectIds.trim())){
    		bool_infoObjects = false;
    	}
    	
    	if(objectiveIds==null || "".equals(objectiveIds.trim())){
    		bool_objectives = false;
    	}
    	if(na==null || "".equals(na.trim())){
    		na = "NONE";
    	}
    	
        try {
        	Long critSuiteId = Long.parseLong(strCritSuiteId);
        	
        	String[] infoObjectsIdList;
        	String[] objectiveIdList;
        	Set<InfoObject> infoObjects = new HashSet();
        	SortedSet objectives = new TreeSet();
        	
        	if(bool_infoObjects) {
        		infoObjectsIdList =infoObjectIds.split(",");
        		infoObjects = criteriaService.getInfoObjects(infoObjectsIdList);
        	}
        	if(bool_objectives) {
	        	objectiveIdList = objectiveIds.split(",");
	        	objectives = criteriaService.getObjectiveObjects(objectiveIdList);
        	}
        	
        	Criteria c = criteriaService.addCriterion(bool_infoObjects, bool_objectives, name, infoObjects, objectives, na);
        	criteriaService.addAssocCriterion(c.getId(), critSuiteId, true);
        	
        	map.put("id", c.getId());
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
    }//addCriterion()
    
    
    /**
     * Associate a criterion to a suite
     * 
     * @param params a Map contains:
     *   <ul>
     *     <li>critSuiteId - long (string), critSuite Id 
     *     <li>critId - long (string), critId</li>
     *     <li>checked - boolean (string), if true create association, if false remove association</li>
     *   </ul>
     * @return a Map contains:
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
    public Map addAssocCriterion(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        boolean checked = false;
    	String strCritId = (String) params.get("critId");
    	String strCritSuiteId = (String) params.get("critSuiteId");
    	String strChecked = (String) params.get("checked");
    	
    	if(strCritId==null || "".equals(strCritId.trim())){
    		map.put("reason", "critId cannot be empty.");
    		return map;
    	}
    	if(strCritSuiteId==null || "".equals(strCritSuiteId.trim())){
    		map.put("reason", "critSuite cannot be empty.");
    		return map;
    	}
    	if(strChecked==null || "".equals(strChecked.trim())){
    		map.put("reason", "checked cannot be empty.");
    		return map;
    	}
    	

        try {
        	Long critId = new Long(strCritId);
        	Long critSuiteId = new Long(strCritSuiteId);
        	
        	if(strChecked.equals("true")) {
        		checked = true;
        	}
        	criteriaService.addAssocCriterion(critId, critSuiteId, checked);

            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
    }//addAssocCriterion()
    
    
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
     * Check to see if a criterion is contained within a suite
     * 
     * @param params a Map contains:
     *   <ul>
     *     <li>critSuiteId - long (string), critSuite Id 
     *     <li>critId - long (string), critId</li>
     *   </ul>
     * @return a Map contains:
     *   <ul>
     *     <li>contains - a boolean value, the suite contains the criterion, true or false</li>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
    public Map getContainsCriteria(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        boolean checked = false;
    	String strCritId = (String) params.get("critId");
    	String strCritSuiteId = (String) params.get("critSuiteId");
    	
    	if(strCritId==null || "".equals(strCritId.trim())){
    		map.put("reason", "critId cannot be empty.");
    		return map;
    	}
    	if(strCritSuiteId==null || "".equals(strCritSuiteId.trim())){
    		map.put("reason", "critSuite cannot be empty.");
    		return map;
    	}
    	

        try {
        	Long critId = new Long(strCritId);
        	Long critSuiteId = new Long(strCritSuiteId);

        	boolean contains = criteriaService.getContainsCriteria(critId, critSuiteId);

        	map.put("contains", contains);
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
    }//getContainsCriteria()
    
    
    /**
     * Edit an existing criterion.
     * 
     * @param params a Map contains:
     *   <ul>
     *     <li>critId - string, id of the criterion</li>
     *     <li>name - string, name of the criteia - Optional</li>
     *     <li>infoObjectIds - string, name of the themeid's separated by commas - Optional</li>
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
        boolean bool_name = true;
        boolean bool_infoObjects = true;
        boolean bool_objectives = true;
        
        String strId = (String) params.get("critId");
        String name = (String) params.get("name");
    	String infoObjectIds = (String) params.get("infoObjectId");
    	String objectiveIds = (String) params.get("objectiveIds");
    	String na = (String) params.get("na");
    	
    	if(strId==null || "".equals(strId.trim())){
    		map.put("reason", "Criterion id cannot be empty.");
    		return map;
    	}
    	if(name==null || "".equals(name.trim())){
    		bool_name = false;
    	}
    	if(infoObjectIds==null || "".equals(infoObjectIds.trim())){
    		bool_infoObjects = false;
    	}
    	if(objectiveIds==null || "".equals(objectiveIds.trim())){
    		bool_objectives = false;
    	}
    	if(na==null){
    		na = "NONE";
    	}
    	Long id = new Long(strId);
       
        try {
        	Criteria c = criteriaService.getCriterionById(id);
        	
        	String[] infoObjectList;
        	String[] objectiveIdList;
        	Set infoObjects = new HashSet();
        	SortedSet objectives = new TreeSet();
        	
        	if(bool_infoObjects) {
        		infoObjectList = infoObjectIds.split(",");
        		infoObjects = criteriaService.getInfoObjects(infoObjectList);
        	}
        	if(bool_objectives) {
	        	objectiveIdList = objectiveIds.split(",");
	        	objectives = criteriaService.getObjectiveObjects(objectiveIdList);
        	}
        	
        	criteriaService.editCriterion(bool_name, bool_infoObjects, bool_objectives, c, name, infoObjects, objectives, na);
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
    }//editCriterion()
    
    
    /**
     * Get the infoObjects list for the given isid.
     * 
     * @param params a Map contains:
     *   <ul>
     *     <li>isid - int, the id of an infoStructure object</li>
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
     *           <li>isid - A infostructure Id</li>
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
    public Map getInfoObjects(HttpServletRequest request, Map params) {
        Map map = new HashMap();
        map.put("successful", false);    
        
        
        Long isid = new Long((String) params.get("isid"));
  
        try {        	
            Set infoObjects = criteriaService.getInfoObjects(isid);
            
            request.setAttribute("infoObjects", infoObjects); 
            map.put("infoObjects", infoObjects);
        	map.put("html", WebContextFactory.get().forwardToString("/WEB-INF/jsp/criteria/criteriaAssoc_themes.jsp"));
            map.put("successful", true);   
            
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//getInfoObjects()
    
    
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
    public Map getWeight(HttpServletRequest request, Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        String strCritSuiteId = (String) params.get("critSuiteId");
        
        if(strCritSuiteId==null || "".equals(strCritSuiteId.trim())){
        	map.put("reason", "critSuiteId cannot be null.");
    		return map;	
        }
        
        String strCritId = (String) params.get("critId");
        
        if(strCritId==null || "".equals(strCritId.trim())){
        	map.put("reason", "strCritId cannot be null.");
    		return map;	
        }
        
        Long critSuiteId = new Long(strCritSuiteId);
        Long critId = new Long(strCritId);
        
        try {
        	int weight = criteriaService.getWeight(critSuiteId, critId);
            
        	request.setAttribute("weight", weight);
            map.put("weight", weight);
            
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
     *     <li>html - a HTML source segment. (Generated by /WEB-INF/jsp/criteria/criteriaAssoc_weights.jsp)<br>
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
        	
        	map.put("critSuite", cs);
        	request.setAttribute("critSuite", cs);
        	map.put("html", WebContextFactory.get().forwardToString("/WEB-INF/jsp/criteria/criteriaAssoc_weights.jsp"));
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
        	map.put("html", WebContextFactory.get().forwardToString("/WEB-INF/jsp/criteria/criteria.jsp"));
        	map.put("successful", true);
        	
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
    } //getAllCriterion()
    
    
    /**
     * Get all the Criterion Available For the Manager
     * @param params An empty map.
     * <ul>
     *     <li>critSuiteId - string, critSuiteId</li>
     *     <li>isid - string, isid</li>
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
    public Map getAllCriterionForMgr(HttpServletRequest request, Map params) {
    	
    	Map map = new HashMap();
        map.put("successful", false);
        boolean useIsid= true;
       
        String strCritSuiteId = (String) params.get("critSuiteId");
        String strIsid = (String) params.get("isid");
        
        if(strIsid==null || "".equals(strIsid.trim())){
        	useIsid = false;
    	}

        if(strCritSuiteId==null || "".equals(strCritSuiteId.trim())){
        	map.put("reason", "critSuiteId cannot be null.");
    		return map;	
    	}

        
        try {
        	Collection infoObjects;
        	Long critSuiteId = Long.parseLong(strCritSuiteId);
        	
        	Collection criteria = criteriaService.getAllCriterion(critSuiteId);
        	
        	if(useIsid){
        		Long isid = new Long(strIsid);
        		infoObjects = criteriaService.getInfoObjects(isid);
        		request.setAttribute("infoObjects", infoObjects); 
        		map.put("infoObjects", infoObjects);
        		request.setAttribute("isid", isid); 
        	} 
        	
        	request.setAttribute("criteria", criteria); 
        	map.put("criteria", criteria);
        	map.put("html", WebContextFactory.get().forwardToString("/WEB-INF/jsp/criteria/criteriaForMgr.jsp"));
        	map.put("successful", true);
        	
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
    } //getAllCriterionForMgr()
    
    
    /**
	  * add Objective to criterion
	 * @param params a Map contains:
	 *   <ul>
	 *     <li>critId - Long, criterion id</li> 
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
		  String strCritId = (String) params.get("critId");
		  String description = (String) params.get("description");

		  if(strCritId==null || "".equals(strCritId.trim())){
			  map.put("reason", "critId cannot be empty.");
			  return map;
		  }
		  
		  if(description==null || "".equals(description.trim())){
			  map.put("reason", "Objective description cannot be empty.");
			  return map;
		  }

		  try {  	
			  Long critId = Long.parseLong(strCritId);
			  Objective o = criteriaService.addObjective(critId, description);
			  
			  map.put("id", o.getId());
			  map.put("successful", true);	
		  } catch (Exception e) {
			  e.printStackTrace();
			  map.put("reason", e.getMessage());
		  }
	    
		  return map;    	 
	  } //addObjective()

	  
	/**
	 * edit Objective 
	 * @param params a Map contains:
	 *   <ul>
	 *     <li>objectiveId - Long, objective id</li>  
	 *     <li>description - string, name</li>  
	 *   </ul>
	 * @return a Map contains:
	 *   <ul>
	 *     <li>id - id of the added objective</li>
	 *     <li>successful - a boolean value denoting if the operation succeeds</li>
	 *     <li>reason - reason why operation failed (valid when successful==false)</li>
	 *   </ul>
	  */
	  public Map editObjective(Map params) {
		  Map map = new HashMap();
		  map.put("successful", false);
		  String description = (String) params.get("description");
		  String strObjectiveId = (String) params.get("objectiveId");
		  
		  if(description==null || "".equals(description.trim())){
			  map.put("reason", "Objective description cannot be empty.");
			  return map;
		  }

		  if(strObjectiveId==null || "".equals(strObjectiveId.trim())){
			  map.put("reason", "objectiveId  cannot be empty.");
			  return map;
		  }
		  
		  try {  	
			  Long objectiveId = Long.parseLong(strObjectiveId);
			  criteriaService.editObjective(objectiveId, description);
			  map.put("successful", true);	
		  } catch (Exception e) {
			  e.printStackTrace();
			  map.put("reason", e.getMessage());
		  }
	    
		  return map;    	 
	  } //editObjective()
		  
		  
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
   
    
    /**
     * Manually Create CriteriaSuite for Acceptance test
     * 
     * @return a Map contains:
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
    public Map createCriteriaSuite(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {        	
        	CriteriaSuite cs = criteriaService.createCriteriaSuite();
        	
        	map.put("critSuiteId", cs.getId());
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
    }//createCriteriaSuite()
    
    
    /**
     * Get orphaned themes
     * 
     * @param params a Map contains:
     *   <ul>
     *     <li>critSuiteId - long, id of a CriteriaSuite object</li>
     *     <li>isid - long, isid</li>
     *   </ul>
     * @return a Map contains:
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
    public Map getOrphanInfoObjects(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        String strId = (String)params.get("critSuiteId");
        String strIsid = (String)params.get("isid");
        
        if(strId==null || "".equals(strId.trim())){
        		map.put("reason", "critSuiteId cannot be null.");
        		return map;	
        }

        
        if(strIsid==null || "".equals(strIsid.trim())){
        		map.put("reason", "isid cannot be null.");
        		return map;	
        }
        
        Long suiteId = Long.parseLong(strId); 
        Long isid = Long.parseLong(strIsid); 
        
        try {        	
        	Collection infoObjects = criteriaService.getOrphanInfoObjects(suiteId, isid);
        	
        	map.put("infoObjects", infoObjects);
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
    }//createCriteriaSuite()
    
    
}//class CriteriaAgent
