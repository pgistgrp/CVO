package org.pgist.projects;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;


/**
 * @author Guirong
 */
public class ProjectAgent {
    
    
	private Connection connection = null;
    
	private ProjectService projectService = null;
	
    
	/**
     * This is not an AJAX method
     * @return
     */
	public ProjectService getProjectService(){
        return this.projectService;
    }
	
    
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
	 *
	 * @param pId
	 * @return
	 * A map containing the project object and the coordinates
	 */
	public Project getProject(long pId){
		try {
			return (Project)projectService.getProject(pId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
    
    
    /**
     * Get a Project object by id
     *
     * @param id, int
     * 
     * @return a Project object
     */
    public Project getProjectById(long id){
        try {
            return (Project) projectService.getProjectById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }//getProjectById()
    
    
    /**
     * Create a new Project with the given information.
     * 
     * @param params A map contains:
     *     <ul>
     *       <li>name - string, name of the Project object</li>
     *       <li>description - string, description of the project</li>
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
            
            Project project = projectService.createProject(name, description);
            
            map.put("id", project.getId());
            
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//createProject()
   
   
	public Map getProjects(String criteria){
		Map result = new HashedMap();
		if(criteria == null)criteria = "";

		try{
			List projects = projectService.getProjects(criteria);
			result.put("projects", projects);
			for(int i=0; i<projects.size(); i++){
				System.out.println("-->get project: " + ((Project)projects.get(i)).getName());
			}
			result.put("successful", true);
		}catch(Exception e){
			e.printStackTrace();
			result.put("successful", false);
			result.put("reason", e.getMessage());
		}
		return result;
	}
	
	public Map saveProject(Map prjparams){
		Map result = new HashMap();
		if(prjparams == null){
			result.put("successful", false);
			result.put("reason", "No project attributes given.");
		}

		Project p = new Project();
		p.setName((String)prjparams.get("name"));
		p.setDescription((String)prjparams.get("description"));
		//p.setCost(Double.valueOf((String)prjparams.get("cost")));
		if(prjparams.get("fpids") != null)
			;//p.setFpids( (String)prjparams.get("fpids") );

		try{
			projectService.saveProject(p);
			result.put("successful", true);
			result.put("pid", p.getId());
		}catch (Exception e){
			result.put("successful", false);
			result.put("reason", e.getMessage());
		}

		return result;
	}
	
    
	public Map saveAlternative(long pid, Map altparams){
		Map result = new HashMap();
		try {
			System.out.println("--pid=" + pid);
			Project p = (Project) projectService.getProject(pid);
			if(p == null){
				result.put("successful", false);
				result.put("reason", "Can't find this project.");
			}else{
				ProjectAlternative a = new ProjectAlternative();
				a.setName((String)altparams.get("name"));
				a.setDescription((String)altparams.get("description"));
				a.setCost(Double.valueOf((String)altparams.get("cost")));
				projectService.saveProject(p, a);

				result.put("successful", true);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.put("successful", false);
			result.put("reason", e.getMessage());
		}
		return result;
	}
	
    
	public Map saveFootprint(long pid, double[][][] coords, String geoType, String fpids){
		Map result = new HashMap();
		try {
			System.out.println("--pid=" + pid);
			Project p = (Project) projectService.getProject(pid);
			if(p == null){
				result.put("successful", false);
				result.put("reason", "Can't find this project.");
			}else{
				if(geoType.compareToIgnoreCase("POLYGON")==0){	//this handles known footprint ids
					//p.setFpids(fpids);
					//p.setGeoType(Geometry.MULTIPOLYGON);
					projectService.saveProject(p);
				}else{
					//projectService.saveFootprint(p , coords, geoType);
				}

				result.put("successful", true);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.put("successful", false);
			result.put("reason", e.getMessage());
		}
		return result;
	}
	
    
	public double[][][] getFootprint(long fpid){
		return null;
	}
	
    
	public Map getFootprints(String fpids){
		Map result = new HashMap();
		try {
			System.out.println("--fpids=" + fpids);
			Map footprints = projectService.getFootprints(fpids);
			result.put("footprints", footprints);
			result.put("successful", true);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.put("successful", false);
			result.put("reason", e.getMessage());
		}
		return result;
	}
	
    
    public Map enableProjectDiscussion(){
        Map result = new HashMap();
        try {
            projectService.publishProjects("");
            result.put("successful", true);
        } catch (Exception ex) {
            result.put("successful", false);
            ex.printStackTrace();
            result.put("reason", ex.getMessage());
        }

        return result;
    }
    
    
}//class ProjectAgent
