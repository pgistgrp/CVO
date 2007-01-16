package org.pgist.projects;

import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 * @author Guirong
 *
 */
public interface ProjectService {
    
    
    Project getProjectById(Long pid) throws Exception;
    
    ProjectAlternative getProjectAlternativeById(Long altId) throws Exception;
    
    Collection getProjects() throws Exception;
    
    double[][][] getFootprint(Long fpid) throws Exception;
    
    void deleteFootPrint(Long fpid);
    
    
    
    // to be checked
    
    
	List getProjects(String criteria) throws Exception;
    
	Map getFootprints(String criteria) throws Exception;
    
	void saveProject(Project project) throws Exception;
    
	void saveProject(Project project, ProjectAlternative alternative) throws Exception;
    
    Long saveFootprint(Long altId, double[][][] coords, String shape) throws Exception;
    
    void publishProjects(String criteria) throws Exception;
    
    
    Project createProject(String name, String description, int transMode) throws Exception;
    
    ProjectAlternative createProjectAlt(Long projectId, Map params) throws Exception;
    
    void editProject(Long id, String name, String description, int transMode) throws Exception;
    
    void deleteProject(Long id) throws Exception;
    
    void deleteProjectAlt(Long id) throws Exception;
    
    void editProjectAlt(Long id, Map params) throws Exception;
    
    Collection getProjectAlternativeGrades(Long cctId, Long altId) throws Exception;

    
    void setupProjectsForCCT(Long cctId, String[] ids) throws Exception;

    void setGrading(Long altId, Long critId, int value) throws Exception;

    
}//interface ProjectService
