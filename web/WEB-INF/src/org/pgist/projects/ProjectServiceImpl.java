package org.pgist.projects;

import java.util.Collection;
import java.util.Map;

import org.pgist.criteria.Criteria;
import org.pgist.criteria.CriteriaDAO;


/**
 * @author  Guirong
 */
public class ProjectServiceImpl implements ProjectService{
    
    
	private ProjectDAO projectDAO = null;
	private CriteriaDAO criteriaDAO = null;
    
    
	



	public void setCriteriaDAO(CriteriaDAO criteriaDAO) {
		this.criteriaDAO = criteriaDAO;
	}


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
    public void editProject(Long id, String name, String description, int transMode, boolean inclusive) throws Exception {

    	//Retrieve the project, NOTE that if this id doesn't corrispond to a project then an exception is
    	//thrown and it is delt with from the Project Agent
    	Project project = projectDAO.getProject(id);
    	
    	//load the values
        project.setName(name);
        project.setDescription(description);
        project.setTransMode(transMode);
        project.setInclusive(inclusive);
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
			String statementFor, String statementAgainst, String county) throws Exception {

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
    	alternative.setCounty(county);
    	
    	//Save the alternative, then links the project and saves the project
    	projectDAO.save(alternative);
    	project.addAlternative(alternative);
    	projectDAO.save(project);
    	    	
		return alternative;
    }//createProjectAlt()

    
    public void editProjectAlt(Long id, String name, String description,
			Float cost, String links, String sponsor, String statementFor,
			String statementAgainst, String county) throws Exception {

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
    	alt.setCounty(county);
    	
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
     * @param altId - int, id of a ProjectAlt object</li>
     * @param critId - int, id of a Criteria object</li>
     * @param objId - int, id of a Objective object</li>
     * @param value - int, grading value, [-3, 3]</li>
     * @throws Exception
     */
    public void setGrading(Long altId, Long critId, Long objId, int value) throws Exception {

    	ProjectAltRef altRef = projectDAO.getProjectAlternativeReferece(altId);
    	
    	Criteria criteria = criteriaDAO.getCriterionById(critId);
    	
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
     * Relate the given ProjectAlternative object to ProjectSuite object
     */
    public void relateProjectAlt(Long suiteId, Long altId) throws Exception {
    	ProjectSuite suite = projectDAO.getProjectSuite(suiteId);
    	    	
    	//Check in the suite to see if if there is a Project the alternative is already related
    	if(!suite.containsAlts(altId)) {
    		
        	//If not then, load the alternative
        	ProjectAlternative alternative = projectDAO.getProjectAlternative(altId);
    		
        	//Relate it back to the Alt Ref 
        	ProjectAltRef altRef = new ProjectAltRef();
        	altRef.setAlternative(alternative);
        	projectDAO.save(altRef);
    		
           	//Pull the project ID from the alternative
        	Project project = alternative.getProject();
        	
        	//Get the project reference
        	ProjectRef projectReference = suite.getProjectReferece(project);
        	
        	//If the reference doesn't exist then create it and add the project
        	if(projectReference == null) {
        		projectReference = new ProjectRef();
        		projectReference.setProject(project);
        		
            	//Save the project reference
            	projectDAO.save(projectReference);
            	
            	//Add it to the suite
            	suite.getReferences().add(projectReference);
            	
            	//Save the suite (thus connecting the suite and project ref together
            	projectDAO.save(suite);       		
        	}
        	
        	//Now put the altRef into the project ref
        	projectReference.getAltRefs().add(altRef);
        	
        	//Save the project reference
        	projectDAO.save(projectReference);
        	    		
    	}    	
    }//relateProjectAlt()


    /**
     * Derelates the given ProjectAlternative object to ProjectSuite object
     * 
     * @param	suiteId	The id of the suite to remove the reference from
     * @param	altId	The alternative ID that tells of the project alternative to remove
     */
    public void derelateProjectAlt(Long suiteId, Long altId) throws Exception {
    	//Get a reference to the suite
    	ProjectSuite suite = projectDAO.getProjectSuite(suiteId);
    	ProjectAltRef altRef = projectDAO.getProjectAlternativeReferece(altId);
    	
    	//Get the project reference that has this alternative reference in it
    	ProjectRef projectRef = suite.getProjectReferece(altRef);    	
    	
    	if(projectRef != null) {
    		//Remove the reference to the alt ref
    		projectRef.removeAltRef(altRef);
    		
        	//Delete the project alterative reference provided
    		projectDAO.delete(altRef);
        	
        	//If that was the last alternative in that project reference then delete the project reference
    		if(projectRef.getNumAltRefs() <= 0) {
    			projectDAO.delete(projectRef);
    		} else {
    			projectDAO.save(projectRef);
    		}
    	}    	
    }//derelateProjectAlt()


    /**
     * Returns the ProjectAltRef object by id
     * 
     * @param	altrefId	The alternative reference ID interested in
     * @return	The project alternative reference
     */
    public ProjectAltRef getProjectAltRefById(Long altrefId) throws Exception {
    	return projectDAO.getProjectAlternativeReferece(altrefId);
    }//getProjectAltRefById()    


    /**
     * Returns the requested project suite
     */
	public ProjectSuite getProjectSuite(Long suiteId) throws Exception {
		return projectDAO.getProjectSuite(suiteId);
	}
    
    
    
}//class ProjectServiceImpl
