package org.pgist.projects;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.pgist.system.BaseDAO;


/**
 * @author Guirong
 *
 */
public interface ProjectDAO extends BaseDAO {
    
    
	void save(Project p) throws Exception;
	
    
	void save(Project p, ProjectAlternative a) throws Exception;
	
    
	void save(Package p) throws Exception;
	
    
	Project getProject(long pid) throws Exception;
	
    
	List getProjects(String criteria) throws Exception;
	
    
	double[][][] getFootprint(long fpid) throws Exception;
	
    
	Map getFootprints(String fpids) throws Exception;
	
    
	void saveFootprint(ProjectAlternative pa, double[][][] coords, String type) throws Exception;
	
    
	Package getPackage(Long pid) throws Exception;


    Collection getProjects() throws Exception;
	
    
}//interface ProjectDAO
