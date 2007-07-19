package org.pgist.projects;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.map.HashedMap;
import org.directwebremoting.WebContextFactory;


/**
 * @author Guirong
 */
public class ProjectAgent {
    
    
	private ProjectService projectService = null;
	
    
	/**
     * This is not an AJAX method
     * @param ps
     */
	public void setProjectService(ProjectService ps){
        this.projectService = ps;
    }


	/*
     * ------------------------------------------------------------------------
	 */
    
    
    /**
     * Get a Project object by id
     *
     * @param  A map contains:
     *     <ul>
     *       <li>id - int, id of the Project object</li>
     *     </ul>
     * 
     * @return A map contains:
     *     <ul>
     *       <li>successful - a boolean value denoting if the operation succeeds</li>
     *       <li>reason - reason why operation failed (valid when successful==false)</li>
     *       <li>project - a Project object</li>
     *     </ul>
     */
    public Map getProjectById(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            Long id = new Long((String) params.get("id"));
            Project project = projectService.getProjectById(id);
            map.put("project", project);
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
    }//getProjectById()
    
    
    /**
     * Get a ProjectAlternative object by id
     *
     * @param  A map contains:
     *     <ul>
     *       <li>id - int, id of the ProjectAlternative object</li>
     *     </ul>
     * 
     * @return A map contains:
     *     <ul>
     *       <li>successful - a boolean value denoting if the operation succeeds</li>
     *       <li>reason - reason why operation failed (valid when successful==false)</li>
     *       <li>alternative - a ProjectAlternative object</li>
     *     </ul>
     */
    public Map getProjectAltById(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            Long id = new Long((String) params.get("id"));
            ProjectAlternative alternative = projectService.getProjectAlternativeById(id);
            map.put("alternative", alternative);
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
    }//getProjectAltById()
    
    
    /**
     * Get a ProjectAlternative Ref object by id
     *
     * @param  A map contains:
     *     <ul>
     *       <li>id - int, id of the ProjectAlternative Ref object</li>
     *     </ul>
     * 
     * @return A map contains:
     *     <ul>
     *       <li>successful - a boolean value denoting if the operation succeeds</li>
     *       <li>reason - reason why operation failed (valid when successful==false)</li>
     *       <li>altRef - a ProjectAlternative Ref object</li>
     *     </ul>
     */
    public Map getProjectAltRefById(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            Long id = new Long((String) params.get("id"));
            ProjectAltRef altRef = projectService.getProjectAltRefById(id);
            map.put("altRef", altRef);
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
    }//getProjectAltRefById()

    /**
     * Get the grades of a ProjectAlternative Ref object by id
     *
     * @param  A map contains:
     *     <ul>
     *       <li>id - int, id of the ProjectAlternative Ref object</li>
     *     </ul>
     * 
     * @return A map contains:
     *     <ul>
     *       <li>successful - a boolean value denoting if the operation succeeds</li>
     *       <li>reason - reason why operation failed (valid when successful==false)</li>
     *       <li>html - HTML rendition of the project grades, ready for edit</li>
     *     </ul>
     */
    public Map getGradesByAltRefId(HttpServletRequest request, Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            Long id = new Long((String) params.get("id"));
            ProjectAltRef altRef = projectService.getProjectAltRefById(id);
            request.setAttribute("altRef", altRef);
            map.put("html", WebContextFactory.get().forwardToString("/WEB-INF/jsp/projects/projectGrades_pane.jsp"));
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
    }//getGradesByAltRefId()
    
    /**
     * Get a ProjectAlternative object
     *
     * @param  A map contains:
     *     <ul>
     *       <li>id - int, id of the ProjectSuite object</li>
     *     </ul>
     * 
     * @return A map contains:
     *     <ul>
     *       <li>successful - a boolean value denoting if the operation succeeds</li>
     *       <li>reason - reason why operation failed (valid when successful==false)</li>
     *       <li>projSuite - ProjectSuite object</li>
     *     </ul>
     */
    public Map getProjectSuite(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            Long id = new Long((String) params.get("id"));
            ProjectSuite projSuite = projectService.getProjectSuite(id);
            map.put("projSuite", projSuite);
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
    }//getProjectSuite()
    
    
    /**
     * Get a page segment of projects list for project management.<br>
     * 
     * @param params A empty map
     * 
     * @return A map contains:
     *     <ul>
     *       <li>successful - a boolean value denoting if the operation succeeds</li>
     *       <li>reason - reason why operation failed (valid when successful==false)</li>
     *       <li>
     *         html - string, html source segment generated by "/WEB-INF/jsp/projects/projectMgr_projects.jsp". In this page the following variables are avaiable:<br>
     *           <ul>
     *             <li>projects - a list of Project objects</li>
     *           </ul>
     *       </li>
     *     </ul>
     */
    public Map getProjectsForMgr(HttpServletRequest request, Map params){
        Map map = new HashedMap();
        map.put("successful", false);
        
        try{
            /*
             * Get all the projects
             */
            Collection projects = projectService.getProjects();
            
            request.setAttribute("projects", projects);
            
            map.put("html", WebContextFactory.get().forwardToString("/WEB-INF/jsp/projects/projectMgr_projects.jsp"));
            
            map.put("successful", true);
        }catch(Exception e){
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
    }//getProjectsForMgr()
    
    
    /**
     * Create a new Project with the given information.
     * 
     * @param params A map contains:
     *     <ul>
     *       <li>name - string, name of the Project object</li>
     *       <li>description - string, description of the project</li>
     *       <li>transMode - int, 1 for "road", 2 for "transit"</li>
     *       <li>inclusive - string, "true" for inclusive, "false" for exclusive</li>
     *     </ul>
     * 
     * @return A map contains:
     *     <ul>
     *       <li>successful - a boolean value denoting if the operation succeeds</li>
     *       <li>reason - reason why operation failed (valid when successful==false)</li>
     *       <li>id - the id of the new Project object</li>
     *     </ul>
     */
    public Map createProject(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            String name = (String) params.get("name");
            String description = (String) params.get("description");
            int transMode = Integer.parseInt((String) params.get("transMode"));
            boolean inclusive = "true".equals((String) params.get("inclusive"));
            
            Project project = projectService.createProject(name, description, transMode, inclusive);
            
            map.put("id", project.getId());
            
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//createProject()
    
    
    /**
     * Manually Create ProjectSuite, For Acceptance Test
     * 
     * 
     * @return A map contains:
     *     <ul>
     *       <li>successful - a boolean value denoting if the operation succeeds</li>
     *       <li>reason - reason why operation failed (valid when successful==false)</li>
     *       <li>id - the id of the new Project object</li>
     *     </ul>
     */
    public Map createProjectSuite(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {

            ProjectSuite projectSuite = projectService.createProjectSuite();
            
            map.put("id", projectSuite.getId());
            
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//createProject()
    
    
    /**
     * Create a new Project Alt with the given information.
     * 
     * @param params A map contains:
     *     <ul>
     *       <li>id - int, id of a Project object</li>
     *       <li>name - string, name for ProjectAlternative</li>
     *       <li>shortDescription - string, A small description of the ProjectAlternative</li>
     *       <li>detailedDescription - string, A longer description fo the ProjectAlternative</li>
     *       <li>cost - float, cost</li>
     *       <li>links - string, links</li>
     *       <li>sponsor - string, name of sponsor</li>
     *       <li>statementFor - string</li>
     *       <li>statementAgainst - string</li>
     *       <li>county - string</li>
     *     </ul>
     * 
     * @return A map contains:
     *     <ul>
     *       <li>successful - a boolean value denoting if the operation succeeds</li>
     *       <li>reason - reason why operation failed (valid when successful==false)</li>
     *       <li>id - the id of the new ProjectAlternative object</li>
     *     </ul>
     */
    public Map createProjectAlt(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            Long id = new Long((String) params.get("id"));
            
            String name = (String) params.get("name");
            String shortDescription = (String) params.get("shortDescription");
            String detailedDescription = (String) params.get("detailedDescription");
            Float cost = Float.parseFloat((String)params.get("cost"));
            String links = (String) params.get("links");
            String sponsor = (String) params.get("sponsor");
            String statementFor = (String) params.get("statementFor");
            String statementAgainst = (String) params.get("statementAgainst");
            String county = (String) params.get("county");
                        
            ProjectAlternative projectAlt = projectService.createProjectAlt(id,
					name, shortDescription, detailedDescription, cost, links, sponsor, statementFor,
					statementAgainst, county);
                                   
            map.put("id", projectAlt.getId());            
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
    }//createProjectAlt()
    
    
    /**
     * Edit a Project object with the given information.
     * 
     * @param params A map contains:
     *     <ul>
     *       <li>id - int, id of the Project object</li>
     *       <li>name - string, name of the Project object</li>
     *       <li>description - string, description of the project</li>
     *       <li>transMode - int, 1 for "road", 2 for "transit"</li>
     *       <li>inclusive - string, "true" for inclusive, "false" for exclusive</li>
     *     </ul>
     * 
     * @return A map contains:
     *     <ul>
     *       <li>successful - a boolean value denoting if the operation succeeds</li>
     *       <li>reason - reason why operation failed (valid when successful==false)</li>
     *     </ul>
     */
    public Map editProject(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            Long id = new Long((String) params.get("id"));
            String name = (String) params.get("name");
            String description = (String) params.get("description");
            int transMode = Integer.parseInt((String) params.get("transMode"));
            boolean inclusive = "true".equals((String) params.get("inclusive"));
            
            projectService.editProject(id, name, description, transMode, inclusive);
            
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//editProject()
    
    
    /**
     * Edit a Project Alt with the given information.
     * 
     * @param params A map contains:
     *     <ul>
     *       <li>id - int, id of a ProjectAlternative object</li>
     *       <li>name - string, name for ProjectAlternative</li>
     *       <li>shortDescription - string, A small description of the ProjectAlternative</li>
     *       <li>detailedDescription - string, A longer description fo the ProjectAlternative</li>
     *       <li>cost - float, cost</li>
     *       <li>links - string, links</li>
     *       <li>sponsor - string, name of sponsor</li>
     *       <li>statementFor - string</li>
     *       <li>statementAgainst - string</li>
     *       <li>county - string</li>
     *     </ul>
     * 
     * @return A map contains:
     *     <ul>
     *       <li>successful - a boolean value denoting if the operation succeeds</li>
     *       <li>reason - reason why operation failed (valid when successful==false)</li>
     *     </ul>
     */
    public Map editProjectAlt(Map params, double[][][] footprint) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            Long id = new Long((String) params.get("id"));

            String name = (String) params.get("name");
            String shortDescription = (String) params.get("shortDescription");
            String detailedDescription = (String) params.get("detailedDescription");
            Float cost = Float.parseFloat((String)params.get("cost"));
            String links = (String) params.get("links");
            String sponsor = (String) params.get("sponsor");
            String statementFor = (String) params.get("statementFor");
            String statementAgainst = (String) params.get("statementAgainst");
            String county = (String) params.get("county");
            
            
            projectService.editProjectAlt(id,
					name, shortDescription, detailedDescription, cost, links, sponsor, statementFor,
					statementAgainst, county);
            
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
    }//editProjectAlt()
    
    
    /**
     * Delete a Project.
     * 
     * @param params A map contains:
     *     <ul>
     *       <li>id - int, id of a Project object</li>
     *     </ul>
     * 
     * @return A map contains:
     *     <ul>
     *       <li>successful - a boolean value denoting if the operation succeeds</li>
     *       <li>reason - reason why operation failed (valid when successful==false)</li>
     *     </ul>
     */
    public Map deleteProject(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            Long id = new Long((String) params.get("id"));
            
            projectService.deleteProject(id);
            
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//deleteProjectAlt()
    
    
    /**
     * Delete a Project Alt.
     * 
     * @param params A map contains:
     *     <ul>
     *       <li>id - int, id of a ProjectAlt object</li>
     *     </ul>
     * 
     * @return A map contains:
     *     <ul>
     *       <li>successful - a boolean value denoting if the operation succeeds</li>
     *       <li>reason - reason why operation failed (valid when successful==false)</li>
     *     </ul>
     */
    public Map deleteProjectAlt(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            Long id = new Long((String) params.get("id"));
            
            projectService.deleteProjectAlt(id);
            
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//deleteProjectAlt()
    
    
    /**
     * Set the definition of projects in a decision situation. According the operation code,
     * the given ProjectAlternative will be associated or unassociated with the give suite.
     * 
     * @param params a Map contains:
     *         <ul>
     *           <li>suiteId - int, id for a ProjectSuite object</li>
     *           <li>altId - int, id for a ProjectAlternative object</li>
     *           <li>operation - string, "add" | "remove"</li>
     *         </ul>
     * @return a Map contains:
     *         <ul>
     *           <li>successful - a boolean value denoting if the operation succeeds</li>
     *           <li>reason - reason why operation failed (valid when successful==false)</li>
     *         </ul>
     */
    public Map setProjectDefine(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            Long suiteId = new Long((String) params.get("suiteId"));
            Long altId = new Long((String) params.get("altId"));
            String operation = (String) params.get("operation");
            
            if ("add".equals(operation)) {
                projectService.relateProjectAlt(suiteId, altId);
            } else if ("remove".equals(operation)) {
                projectService.derelateProjectAlt(suiteId, altId);
            } else {
                map.put("reason", "unknown operation: "+operation);
                return map;
            }
            
            map.put("successful", true);
        } catch (UnknownProjectSuite e) {
            map.put("reason", "Could not find the project suite specified");        	
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        return map;
    }//setProjectDefine()
    
    
    /**
     * Set the project grading information on criteria objective
     * 
     * @param params A map contains:
     *     <ul>
     *       <li>altRefId - int, id of a ProjectAltRef object</li>
     *       <li>critId - int, id of a Criteria object</li>
     *       <li>objId - int, id of a Objective object</li>
     *       <li>value - int, grading value, [-3, 3]</li>
     *     </ul>
     * 
     * @return A map contains:
     *     <ul>
     *       <li>successful - a boolean value denoting if the operation succeeds</li>
     *       <li>reason - reason why operation failed (valid when successful==false)</li>
     *       <li>critGrade - the new calculated grading for the criteria</li>
     *     </ul>
     */
    public Map setGrading(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            Long altRefId = new Long((String) params.get("altRefId"));
            Long critId = new Long((String) params.get("critId"));
            Long objId = new Long((String) params.get("objId"));
            String valueStr = (String) params.get("value");
            Float value = (valueStr==null) ? null : new Float(valueStr);
            
            String critGrade = projectService.setGrading(altRefId, critId, objId, value);
            map.put("critGrade", critGrade);
            map.put("successful", true);
        } catch (UnknownCriteriaException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            map.put("reason", "Error: this objective could not be assigned the specified grade");
            return map;
        	
        } catch (UnknownObjectiveException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            map.put("reason", "Error: this objective could not be assigned the specified grade");
            return map;
        	
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//setGrading()
    
    
    /**
     * get footprints for a given list of footprint IDs.<br>
     * 
     * @param params A map contains:
     *     <ul>
     *       <li>fpids - string, comma separated id list of the project alternative footprints</li>
     *     </ul>
     * 
     * @param 
     * 
     * @return A map contains:
     *     <ul>
     *       <li>successful - a boolean value denoting if the operation succeeds</li>
     *       <li>reason - reason why operation failed (valid when successful==false)</li>
     *       <li>footprints - a map contains pair of (id, coordinates)</li>
     *     </ul>
     */
    public Map getFootprints(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            String fpids = (String) params.get("fpids");
            System.out.println(">>get footprints: " + fpids);
            
            Map footprints = projectService.getFootprints(fpids);
            
            map.put("footprints", footprints);
            
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
    }//getFootPrints()

    /**
     * get footprints for a project alternative.<br>
     * 
     * @param params A map contains:
     *     <ul>
     *       <li>altid - long, the ID of an project alternative
     *     </ul>
     * 
     * @param 
     * 
     * @return A map contains:
     *     <ul>
     *       <li>successful - a boolean value denoting if the operation succeeds</li>
     *       <li>reason - reason why operation failed (valid when successful==false)</li>
     *       <li>footprints - a map contains pair of (id, coordinates)</li>
     *     </ul>
     */
    public Map getFootprintsByAltId(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            Long id = new Long((String) params.get("altid"));
            System.out.print(">>altid=" + id.toString());
            ProjectAlternative alternative = projectService.getProjectAlternativeById(id);
            
            String fpids = alternative.getFpids();
            System.out.println("  fpids=" + fpids);
            
            if(fpids != null){
            	Map footprints = projectService.getFootprints(fpids);
            
            	map.put("footprints", footprints);
            }else{
            	map.put("footprints", null);
            }
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
    }//getFootprintsByAltId()
    
    /**
     * Save the given footprint for a project alternative.<br>
     * 
     * @param params A map contains:
     *     <ul>
     *       <li>altId - int, id of the project alternative object</li>
     *       <li>shape - string, shape of the project alternative object,
     *                   "POINT"| "LINE" | "POLYGON"
     *       </li>
     *     </ul>
     * 
     * @param coordinates - double[][][], the footprint coordinates
     * 
     * @return A map contains:
     *     <ul>
     *       <li>successful - a boolean value denoting if the operation succeeds</li>
     *       <li>reason - reason why operation failed (valid when successful==false)</li>
     *       <li>fpid - int, id of the new created footprint</li>
     *     </ul>
     */
    public Map saveFootprint(Long altId, String shape, double[][][] coordinates) {
        Map map = new HashMap();
        map.put("successful", false);
        System.out.println(">>try to create new geometry");
        try {            
            Long id = projectService.saveFootprint(altId, coordinates, shape);
            
            map.put("altId", id);
            
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//saveFootPrint()
    
    /**
     * Save the given footprint for a project alternative.<br>
     * 
     * @param params A map contains:
     *     <ul>
     *       <li>altId - int, id of the project alternative object</li>
     *       <li>fpids: comma-delimited footprint id(s). These are ids of the three counties
     *       </li>
     *     </ul>
     * 
     * @return A map contains:
     *     <ul>
     *       <li>successful - a boolean value denoting if the operation succeeds</li>
     *       <li>reason - reason why operation failed (valid when successful==false)</li>
     *       <li>fpid - int, id of the new created footprint</li>
     *     </ul>
     */
    public Map useFootprint(Long altId, String fpids) {
        Map map = new HashMap();
        map.put("successful", false);
        
        System.out.println(">>try to use existing geometry");
        try {            
            Long id = projectService.saveFootprint(altId, fpids);
            
            map.put("altId", id);
            
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//saveFootPrint()v    
    
    /**
     * Delete the given footprint.<br>
     * 
     * @param params A map contains:
     *     <ul>
     *       <li>fpid - int, id of the new created footprint</li>
     *     </ul>
     * 
     * @return A map contains:
     *     <ul>
     *       <li>successful - a boolean value denoting if the operation succeeds</li>
     *       <li>reason - reason why operation failed (valid when successful==false)</li>
     *     </ul>
     */
    public Map deleteFootprint(Map params, double[][][] coordinates) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            Long fpid = new Long((String) params.get("fpid"));
            
            projectService.deleteFootPrint(fpid);
            
            map.put("fpid", fpid);
            
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//deleteFootPrint()
    
    
}//class ProjectAgent
