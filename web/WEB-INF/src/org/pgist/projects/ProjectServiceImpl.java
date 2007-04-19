package org.pgist.projects;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import org.pgist.criteria.Criteria;
import org.pgist.criteria.CriteriaDAO;
import org.pgist.criteria.Objective;
import org.pgist.cvo.CCT;
import org.pgist.cvo.CCTDAO;
import org.pgist.discussion.DiscussionDAO;
import org.pgist.discussion.InfoObject;
import org.pgist.discussion.InfoStructure;


/**
 * @author  Guirong
 */
public class ProjectServiceImpl implements ProjectService{
        
	private ProjectDAO projectDAO = null;
    
	private CriteriaDAO criteriaDAO = null;
    
    private CCTDAO cctDAO;
    
    private DiscussionDAO discussionDAO;
    
    
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
	
    
    public void setCctDAO(CCTDAO cctDAO) {
        this.cctDAO = cctDAO;
    }


    public void setDiscussionDAO(DiscussionDAO discussionDAO) {
        this.discussionDAO = discussionDAO;
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
			String shortDescription, String detailedDescription, Float cost, String links, String sponsor,
			String statementFor, String statementAgainst, String county) throws Exception {

    	//Get the project
    	Project project = projectDAO.getProject(id);
    	
    	//Create the alternative
    	ProjectAlternative alternative = new ProjectAlternative();
    	alternative.setName(name);
    	alternative.setShortDesc(shortDescription);
    	alternative.setDetailedDesc(detailedDescription);
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

    
    public void editProjectAlt(Long id, String name, String shortDescription, String detailedDescription,
			Float cost, String links, String sponsor, String statementFor,
			String statementAgainst, String county) throws Exception {

    	//Get the project alternative
    	ProjectAlternative alt = projectDAO.getProjectAlternative(id);
    	
    	//load it
    	alt.setName(name);
    	alt.setShortDesc(shortDescription);
    	alt.setDetailedDesc(detailedDescription);
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
     * @param altRefId - int, id of a ProjectAlt object</li>
     * @param critId - int, id of a Criteria object</li>
     * @param objId - int, id of a Objective object</li>
     * @param value - int, grading value, [-3, 3]</li>
     * @return	The grade of the criteria relative to this alternative
     * @throws Exception
     */
    public String setGrading(Long altRefId, Long critId, Long objId, int value) throws Exception, UnknownCriteriaException, UnknownObjectiveException {

    	ProjectAltRef altRef = projectDAO.getProjectAlternativeReference(altRefId);

    	Iterator<GradedCriteria> crits = altRef.getGradedCriteria().iterator();
    	GradedCriteria gradedCrit;
    	while(crits.hasNext()) {
    		gradedCrit = crits.next();
    		if(gradedCrit.getCriteria().getId().equals(critId)) {
    			return setGrading(gradedCrit, objId, value);
    		}
    	}
    	
    	throw new UnknownCriteriaException("Could not find criteria [" + critId + "]");
    }//setGrading()

    /**
     * Sets the grading on a specific objective within the criteria
     * 
     * @param gradedCriteria	The criteria with the objective in it
     * @param objId - int, id of a Objective object</li>
     * @param value - int, grading value, [-3, 3]</li>
     * @return	The grade of the criteria relative to this alternative
     * @throws Exception
     */
    public String setGrading(GradedCriteria gradedCriteria, Long objId, int value) throws Exception, UnknownObjectiveException {
    	Iterator<GradedObjective> objs = gradedCriteria.getObjectives().iterator();
    	GradedObjective tempObj;
    	while(objs.hasNext()) {
    		tempObj = objs.next();
    		if(tempObj.getObjective().getId().equals(objId)) {
    			tempObj.setGrade(value);
    			projectDAO.save(tempObj);
    			
    			//Update the final grade on the criteria
    			gradedCriteria.recalcGrade();
    			projectDAO.save(gradedCriteria);
    			return gradedCriteria.getGrade();
    		}
    	}
    	throw new UnknownObjectiveException("Could not find objective [" + objId + "]");
    }    
    
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
    	try{
    		ProjectAlternative alt = projectDAO.getProjectAlternative(altId);
    		projectDAO.saveFootprint(alt, coords, shape);
    		return alt.getId();
    	}catch(Exception e){
    		e.printStackTrace();
    		return null;
    	}
    }//saveFootprint()

    public Long saveFootprint(Long altId, String fpids) throws Exception {
    	try{
    		ProjectAlternative alt = projectDAO.getProjectAlternative(altId);
    		projectDAO.saveFootprint(alt, fpids);
    		return alt.getId();
    	}catch(Exception e){
    		e.printStackTrace();
    		return null;
    	}
    }//saveFootprint()
    
    public void deleteFootPrint(Long fpid) {
        /*
         * TODO
         */
    }//deleteFootPrint()
    

   /**
     * Relate the given ProjectAlternative object to ProjectSuite object
     */
    public void relateProjectAlt(Long suiteId, Long altId) throws Exception, UnknownProjectSuite {
    	ProjectSuite suite = projectDAO.getProjectSuite(suiteId);
    	    	
    	if(suite == null) throw new UnknownProjectSuite("Unknown Project Suite [" + suiteId +"]");
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
        	ProjectRef projectReference = suite.getProjectReference(project);
        	
        	//If the reference doesn't exist then create it and add the project
        	if(projectReference == null) {
        		projectReference = new ProjectRef();
        		projectReference.setSuite(suite);
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
    public void derelateProjectAlt(Long suiteId, Long altId) throws Exception, UnknownProjectSuite {
    	//Get a reference to the suite
    	ProjectSuite suite = projectDAO.getProjectSuite(suiteId);
    	
    	if(suite == null) throw new UnknownProjectSuite("Unknown Project Suite [" + suiteId +"]");
    	    	
    	ProjectAlternative projectAlt = projectDAO.getProjectAlternative(altId);

    	//Get the project reference that has this alternative reference in it
    	ProjectRef projectRef = suite.getProjectReference(projectAlt);    	
    	ProjectAltRef altRef = projectRef.getProjectAltRef(projectAlt);
    	
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
    	return projectDAO.getProjectAlternativeReference(altrefId);
    }//getProjectAltRefById()    


    /**
     * Returns the requested project suite
     */
	public ProjectSuite getProjectSuite(Long suiteId) throws Exception {
		ProjectSuite suite = projectDAO.getProjectSuite(suiteId);
		return suite;
	}
    
	/**
	 * Attaches the criteria to the suite
	 * 
	 * @param	suite	The suite to attaches criteria 
	 */
	public void updateProjectSuiteCriteria(ProjectSuite projSuite) throws Exception {
		Collection criterias = this.criteriaDAO.getAllCriterion();
		
//		Collection crits = criteriaDAO.getAllCriterion();		
//		Collection objectives = criteriaDAO.getObjectives();
//		Objective obj;
//		
//		Iterator iCrit = crits.iterator();
//		Criteria crit;
//		while(iCrit.hasNext()) {
//			crit = (Criteria)iCrit.next();
//			GradedCriteria gCrit = new GradedCriteria();
//			gCrit.setCriteria(crit);
//			Iterator i = objectives.iterator();
//			while(i.hasNext()) {
//				obj = (Objective)i.next();
//				GradedObjective gObj = new GradedObjective();
//				gObj.setObjective(obj);
//				projectDAO.save(gObj);
//				gCrit.getObjectives().add(gObj);
//			}
//			projectDAO.save(gCrit);
//		}
		
		
System.out.println("MATT: Found " + criterias.size() + " criteria");		
		Criteria tempCrit;
		ProjectAltRef tempAltRef;
		Iterator<ProjectRef> projRefs = projSuite.getReferences().iterator();
		while(projRefs.hasNext()) {
			Iterator<ProjectAltRef> altRefs = projRefs.next().getAltRefs().iterator();
			while(altRefs.hasNext()) {
				tempAltRef = altRefs.next();
				Iterator cIter = criterias.iterator();
				while(cIter.hasNext()) {
					tempCrit = (Criteria)cIter.next();
					addCriteria(tempAltRef,tempCrit);					
				}
			}
		}
	}
	
	
	/**
	 * Adds the new criteria to this project alt ref
	 * 
	 * @param	altRef	The new alt ref to add the criteria to
	 * @param newCrit	The new criteria to add
	 */
	private void addCriteria(ProjectAltRef altRef, Criteria newCrit) throws Exception {
System.out.println("Matt 1");		
		GradedCriteria tempCrit;
		GradedCriteria gradedCrit = null;
		
		
		//Check to see if the criteria already exist
		Iterator<GradedCriteria> crits = altRef.getGradedCriteria().iterator();
		while(crits.hasNext()) {
			tempCrit = crits.next();
			if(tempCrit.getCriteria().getId().equals(newCrit.getId())) {
				gradedCrit = tempCrit;
				break;
			}
		}
		
		//If not add it
		if(gradedCrit == null) {
			gradedCrit = new GradedCriteria();
			gradedCrit.setCriteria(newCrit);
			projectDAO.save(gradedCrit);
			altRef.getGradedCriteria().add(gradedCrit);
			projectDAO.save(altRef);
		}
		
		//Load all the objectives	
		Iterator objectives = newCrit.getObjectives().iterator();
		Objective tempObj;
		
		while(objectives.hasNext()) {
			tempObj = (Objective)objectives.next();
			addObjective(gradedCrit, tempObj);
		}		
		System.out.println("Matt 3");		
		
	}
	
	/**
	 * Adds the objective to the provided criteria
	 * 
	 * @param	crit	The criteria to add if it isn't already added
	 * @param	obj	The objective to add
	 */
	private void addObjective(GradedCriteria gradedCrit, Objective newObj) throws Exception {
		GradedObjective tempObj;
		GradedObjective gradedObj = null;
		
		//See if this objective is already in the graded criteria
		Iterator<GradedObjective> gradedObjs = gradedCrit.getObjectives().iterator();
		while(gradedObjs.hasNext()) {
			tempObj = gradedObjs.next();
			if(tempObj.getObjective().getId().equals(newObj.getId())) {
				gradedObj = tempObj;
			}
		}
		//If it isn't already in the graded criteria then add it
		if(gradedObj == null) {
			gradedObj = new GradedObjective();
			gradedObj.setObjective(newObj);
			gradedObj.setGrade(null);
			projectDAO.save(gradedObj);
			
			gradedCrit.getObjectives().add(gradedObj);
			projectDAO.save(gradedCrit);
		}
	}
    
    
    public ProjectSuite createProjectSuite() throws Exception {
        ProjectSuite suite = new ProjectSuite();
        
        projectDAO.save(suite);
        
        return suite;
    }//createProjectSuite()
    
    
    public InfoStructure publish(Long cctId, Long suiteId) throws Exception {
        CCT cct = cctDAO.getCCTById(cctId);
        
        ProjectSuite suite = projectDAO.getProjectSuite(suiteId);
        
        Date date = new Date();
        
        InfoStructure structure = new InfoStructure();
        structure.setType("sdcrit");
        structure.setTitle("Step 2b: Review Criteria");
        structure.setRespTime(date);
        structure.setCctId(cct.getId());
        discussionDAO.save(structure);
        
        for (ProjectRef ref : suite.getReferences()) {
            ref.getId();
            ref.getAltRefs();
            ref.getNumAltRefs();
            ref.getProject();
            ref.getSuite();
            
            InfoObject obj = new InfoObject();
            obj.setObject(ref);
            obj.setRespTime(date);
            discussionDAO.save(obj);
            
            structure.getInfoObjects().add(obj);
        }//for ref
        
        discussionDAO.save(structure);
        
        return structure;
    }//publish()
    
    
}//class ProjectServiceImpl
