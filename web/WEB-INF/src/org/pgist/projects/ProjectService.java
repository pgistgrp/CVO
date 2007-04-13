package org.pgist.projects;

import java.util.Collection;
import java.util.Map;

import org.pgist.discussion.InfoStructure;


/**
 * @author Guirong
 *
 */
public interface ProjectService {
    
	ProjectSuite getProjectSuite(Long suiteId) throws Exception;
    
    Project getProjectById(Long pid) throws Exception;
    
    Collection getProjects() throws Exception;
    
    Project createProject(String name, String description, int transMode, boolean inclusive) throws Exception;    
    
    void editProject(Long id, String name, String description, int transMode, boolean inclusive) throws Exception;    
    
    void deleteProject(Long id) throws Exception;
                
    String setGrading(Long altRefId, Long critId, Long objId, int value) throws Exception, UnknownCriteriaException, UnknownObjectiveException;

    void setupProjectsForCCT(Long cctId, String[] ids) throws Exception;

    double[][][] getFootprint(Long fpid) throws Exception;
    
    Map getFootprints(String fpids) throws Exception;
    
    Long saveFootprint(Long altId, double[][][] coords, String shape) throws Exception;
    Long saveFootprint(Long altId, String fpids) throws Exception;
    
    void deleteFootPrint(Long fpid);
    
    void relateProjectAlt(Long suiteId, Long altId) throws Exception, UnknownProjectSuite;    

    ProjectAltRef getProjectAltRefById(Long altrefId) throws Exception;

	ProjectAlternative createProjectAlt(Long id, String name, String shortDescription, String detailedDescription, Float cost, String links, String sponsor, String statementFor, String statementAgainst, String county) throws Exception;
    
	ProjectAlternative getProjectAlternativeById(Long altId) throws Exception;
    
	void derelateProjectAlt(Long suiteId, Long altId) throws Exception, UnknownProjectSuite;
    
	void deleteProjectAlt(Long id) throws Exception;

	void editProjectAlt(Long id, String name, String shortDescription, String detailedDescription, Float cost, String links, String sponsor, String statementFor, String statementAgainst, String county) throws Exception;
    	
	public void updateProjectSuiteCriteria(ProjectSuite projSuite) throws Exception;
	
    ProjectSuite createProjectSuite() throws Exception;

    InfoStructure publish(Long cctId, Long suiteId) throws Exception;
    
    
}//interface ProjectService
