package org.pgist.projects;

import java.util.Collection;
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
        return projectDAO.getProjectAlternative(altId);
    }//getProjectAlternativeById()


    public Collection getProjects() throws Exception {
        return projectDAO.getProjects();
    }//getProjects()


    public Project createProject(String name, String description, int transMode, boolean inclusive) throws Exception {
        //TODO verify that this is done?
    	Project project = new Project();
        
        project.setName(name);
        project.setDescription(description);
        project.setTransMode(transMode);
        project.setInclusive(inclusive);
        
        projectDAO.save(project);
        
        return project;
    }//createProject()


    /**
     * Allows the user to update the project with the new information
     * 
     * @throws	Exception	If the project cannot be found
     */
    public void editProject(Long id, String name, String description, int transMode) throws Exception {

    	//Retrieve the project, NOTE that if this id doesn't corrispond to a project then an exception is
    	//thrown and it is delt with from the Project Agent
    	Project project = projectDAO.getProject(id);
    	
    	//load the values
        project.setName(name);
        project.setDescription(description);
        project.setTransMode(transMode);
    	//save
        
        projectDAO.save(project);
        
    }//editProject()

    
    public void deleteProject(Long id) throws Exception {
    	//Get the project
    	Project project = projectDAO.getProject(id);
    	
    	//Delete it
    	projectDAO.delete(project);
    }//deleteProject()


    
    public ProjectAlternative createProjectAlt(Long id, String name,
			String description, Float cost, String links, String sponsor,
			String statementFor, String statementAgainst) throws Exception {

    	//Get the project
    	Project project = projectDAO.getProject(id);
    	
    	//Create the alternative
    	ProjectAlternative alternative = new ProjectAlternative();
    	alternative.setName(name);
    	alternative.setDetailedDesc(description);
    	alternative.setCost(cost);
    	alternative.setLinks(links);
    	alternative.setSponsor(sponsor);
    	alternative.setStatementFor(statementFor);
    	alternative.setStatementAgainst(statementAgainst);
    	
    	//Save the alternative, then links the project and saves the project
    	projectDAO.save(project, alternative);
    	    	
		return alternative;
    }//createProjectAlt()

    
    public void editProjectAlt(Long id, String name, String description,
			Float cost, String links, String sponsor, String statementFor,
			String statementAgainst) throws Exception {

    	//Get the project alternative
    	ProjectAlternative alt = projectDAO.getProjectAlternative(id);
    	
    	//load it
    	alt.setName(name);
    	alt.setDetailedDesc(description);
    	alt.setCost(cost);
    	alt.setLinks(links);
    	alt.setSponsor(sponsor);
    	alt.setStatementFor(statementFor);
    	alt.setStatementAgainst(statementAgainst);
    	    	
    	//save it
    	projectDAO.save(alt);
    	
	}//editProjectAlt()


    public void deleteProjectAlt(Long id) throws Exception {
    	//Get the alternative
    	ProjectAlternative alt = projectDAO.getProjectAlternative(id);
    	
    	//Delete it
    	projectDAO.delete(alt);
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
     * TODO: relate the given ProjectAlternative object to ProjectSuite object
     */
    public void relateProjectAlt(Long suiteId, Long altId) throws Exception {
    }//relateProjectAlt()


    /**
     * TODO: derelate the given ProjectAlternative object to ProjectSuite object
     */
    public void derelateProjectAlt(Long suiteId, Long altId) throws Exception {
    }//derelateProjectAlt()


    /**
     * TODO: get the ProjectAltRef object by id
     */
    public ProjectAltRef getProjectAltRefById(Long altrefId) throws Exception {
        return null;
    }//getProjectAltRefById()
    
    
}//class ProjectServiceImpl
