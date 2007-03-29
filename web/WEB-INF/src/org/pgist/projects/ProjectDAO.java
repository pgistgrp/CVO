package org.pgist.projects;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.pgist.packages.Package;
import org.pgist.system.BaseDAO;


/**
 * @author Guirong
 *
 */
public interface ProjectDAO extends BaseDAO {
    
	void save(GradedCriteria p) throws Exception;
	void save(GradedObjective p) throws Exception;
    
	void save(Project p) throws Exception;
	
        
	Project getProject(long pid) throws Exception;
	
    
	List getProjects(String criteria) throws Exception;
	
    
	double[][][] getFootprint(long fpid) throws Exception;
	
    
	Map getFootprints(String fpids) throws Exception;
	
    
	void saveFootprint(ProjectAlternative pa, double[][][] coords, String type) throws Exception;
	
	
    Collection getProjects() throws Exception;
    
    
    void delete(Project p) throws Exception;    

	ProjectAlternative getProjectAlternative(Long pid) throws Exception;
    
    void delete(ProjectAlternative a) throws Exception;    

	void save(ProjectAlternative a) throws Exception;   
	
	ProjectSuite getProjectSuite(Long suiteID) throws Exception;

	ProjectAltRef getProjectAlternativeReference(Long altId);
	
	void save(ProjectAltRef altRef) throws Exception;	
    void delete(ProjectAltRef altRef) throws Exception;    
	
	void save(ProjectRef projectRef) throws Exception;	
    void delete(ProjectRef projectRef) throws Exception;    
	
	void save(ProjectSuite suite) throws Exception;	
	
}//interface ProjectDAO
