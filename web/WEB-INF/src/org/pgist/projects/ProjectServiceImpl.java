package org.pgist.projects;

import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 * @author  Guirong
 */
public class ProjectServiceImpl implements ProjectService{
    
    
	private ProjectDAO projectDAO = null;
    
    
	/**
     * @param projectDAO  the projectDAO to set
     * @uml.property  name="projectDAO"
     */
	public void setProjectDAO(ProjectDAO p){
		this.projectDAO = p;
	}
	
    
    /*
     * ------------------------------------------------------------------------
     */
	
    
    public Project getProjectById(Long pid) throws Exception {
        return projectDAO.getProject(pid);
    }//getProjectById()


    public ProjectAlternative getProjectAlternativeById(Long altId) throws Exception {
        /**
         * TODO
         */
        return null;
    }//getProjectAlternativeById()


    public Collection getProjects() throws Exception {
        return projectDAO.getProjects();
    }//getProjects()


    public Project createProject(String name, String description, int transMode) throws Exception {
        Project project = new Project();
        
        project.setName(name);
        project.setDescription(description);
        project.setTransMode(transMode);
        
        projectDAO.save(project);
        
        return project;
    }//createProject()


    public void editProject(Long id, String name, String description, int transMode) throws Exception {
        /*
         * TODO
         */
    }//editProject()

    
    public void deleteProject(Long id) throws Exception {
        /*
         * TODO
         */
    }//deleteProject()


    public ProjectAlternative createProjectAlt(Long projectId, Map params) throws Exception {
        /*
         * TODO
         */
        return null;
    }//createProjectAlt()


    public void editProjectAlt(Long id, Map params) throws Exception {
        /*
         * TODO
         */
    }//editProjectAlt()


    public void deleteProjectAlt(Long id) throws Exception {
        // TODO Auto-generated method stub
    }//deleteProjectAlt()


    /**
     * Set grading to a specific ProjectAlternative and Criteria Objective of a specific User
     * 
     * @param altId
     * @param critId
     * @param objId
     * @param value
     * @throws Exception
     */
    public void setGrading(Long altId, Long critId, Long objId, int value) throws Exception {
        /**
         * TODO:
         *   
         */
    }//setGrading()


    /**
     * Setup the association between projects and CCT.
     * 
     * @param cctId id of the CCT object to be associated
     * @param ids ids of projects to be assosicated
     * @throws Exception
     */
    public void setupProjectsForCCT(Long cctId, String[] ids) throws Exception {
        /*
         * TODO:
         *   Load CCT object by cctId, throw exception if failed
         *   Load each ProjectAlternative object by id, throw exception if any failed
         *   put each ProjectAlternative object to CCT.projects
         *   persist objects
         */
    }//setupProjectsForCCT()


    public double[][][] getFootprint(Long fpid) throws Exception{
        return projectDAO.getFootprint(fpid);
    }//getFootprint()
    
    
    public Map getFootprints(String fpids) throws Exception{
        return projectDAO.getFootprints(fpids);
    }//getFootprints()
    
    
    public Long saveFootprint(Long altId, double[][][] coords, String shape) throws Exception {
        /*
         * TODO
         */
        return null;
    }//saveFootprint()
    
    
    public void deleteFootPrint(Long fpid) {
        /*
         * TODO
         */
    }//deleteFootPrint()



    
    
    
    
    

    /**
     * TODO
     */
    public Collection getProjectAlternativeGrades(Long cctId, Long altId) throws Exception {
        return null;
    }//getProjectAlternativeGrades()
    
    
}//class ProjectServiceImpl
