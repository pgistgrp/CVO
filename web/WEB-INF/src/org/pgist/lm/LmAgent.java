package org.pgist.lm;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.pgist.projects.ProjectAlternative;
import org.pgist.projects.ProjectService;

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

public class LmAgent {

	private ProjectService projectService;
	
	private LmService lmService;


	public void setLmService(LmService lmService) {
		this.lmService = lmService;
	}


	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}
	
	
	/**
     * Get all the projects, (Agent for testing)
     * 
     * @return a Map contains:
     *   <ul>
     *     <li>projects - array of projects</li>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
    public Map getProjects(Map params) {
    	Map map = new HashMap();
        map.put("successful", false);
        
        try {

        	Collection projects = lmService.getProjects();
        	map.put("projects", projects);
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        return map;
    } //getProjects()
    
    
	/**
     * Get all the projects, (Agent for testing)
     * @param a Map contains:
     *   <ul>
     *     <li>altId - Long, id of an alternative</li>
     *   </ul>
     * @return a Map contains:
     *   <ul>
     *     <li>projects - array of projects</li>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
    public Map getAlt(Map params) {
    	Map map = new HashMap();
        map.put("successful", false);
        
        String strId = (String) params.get("altId");
        
        if (strId==null || "".equals(strId.trim())) {
            map.put("reason", "altId can't be empty!");
            return map;
        }
        
        try {
        	Long altId = Long.parseLong(strId);
  
        	ProjectAlternative alt = lmService.getAlt(altId);
        	map.put("alt", alt);
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        return map;
    } //getProjects()
	
}
