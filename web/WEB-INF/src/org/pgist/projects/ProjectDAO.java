package org.pgist.projects;

import java.util.List;
import java.util.Map;

import org.pgist.system.BaseDAO;


/**
 * @author Guirong
 *
 */
public interface ProjectDAO extends BaseDAO {
    
    
	public void save(Project p) throws Exception;
	
    
	public void save(Project p, ProjectAlternative a) throws Exception;
	
    
	public void save(Package p) throws Exception;
	
    
	public Project getProject(long pid) throws Exception;
	
    
	public List getProjects(String criteria) throws Exception;
	
    
	public double[][][] getFootprint(long fpid) throws Exception;
	
    
	public Map getFootprints(String fpids) throws Exception;
	
    
	public void saveFootprint(Project p, double[][][] coords, String type) throws Exception;
	
    
	public Package getPackage(Long pid) throws Exception;
	
    
}//interface ProjectDAO
