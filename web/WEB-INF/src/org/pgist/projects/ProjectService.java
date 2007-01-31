package org.pgist.projects;

import java.util.Collection;
import java.util.Map;


/**
 * @author Guirong
 *
 */
public interface ProjectService {
    
    
    Project getProjectById(Long pid) throws Exception;
    
    ProjectAlternative getProjectAlternativeById(Long altId) throws Exception;
    
    Collection getProjects() throws Exception;
    
    Project createProject(String name, String description, int transMode, boolean inclusive) throws Exception;
    
    void editProject(Long id, String name, String description, int transMode) throws Exception;
    
    void deleteProject(Long id) throws Exception;
    
    ProjectAlternative createProjectAlt(Long projectId, Map params) throws Exception;
    
    void editProjectAlt(Long id, Map params) throws Exception;
    
    void deleteProjectAlt(Long id) throws Exception;
    
    void setGrading(Long altId, Long critId, Long objId, int value) throws Exception;

    void setupProjectsForCCT(Long cctId, String[] ids) throws Exception;

    double[][][] getFootprint(Long fpid) throws Exception;
    
    Map getFootprints(String fpids) throws Exception;
    
    Long saveFootprint(Long altId, double[][][] coords, String shape) throws Exception;
    
    void deleteFootPrint(Long fpid);
    
    
    
    // to be checked
    
    Collection getProjectAlternativeGrades(Long cctId, Long altId) throws Exception;

    
}//interface ProjectService
