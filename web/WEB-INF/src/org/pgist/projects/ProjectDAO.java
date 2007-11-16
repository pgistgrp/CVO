/**
 * 
 */
package org.pgist.projects;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.pgist.system.BaseDAO;

/**
 * @author Guirong
 *
 */
public interface ProjectDAO extends BaseDAO {

	public void save(Project p) throws Exception;

	public void save(ProjectAlternative a) throws Exception;
	
	public void save(Package p) throws Exception;
	
	public Project getProject(long pid) throws Exception;
	
	public List getAllProjects() throws Exception;
	
	public List getProjects(String criteria) throws Exception;
	
	public double[][] getFootprint(Connection conn, long fpid) throws Exception;
	
	public Map getFootprints(Connection conn, String fpids) throws Exception;
	
	public void saveFootprint(Connection conn, Project p, double[][] coords, int[]parts, String type) throws Exception;
	
	public Package getPackage(Long pid) throws Exception;
	
	
}
