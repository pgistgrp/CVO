/**
 *
 */
package org.pgist.projects;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * @author Guirong
 *
 */
public interface ProjectService {
	public Project getProject(Long pId) throws Exception;
	public List getProjects(String criteria) throws Exception;
	public double[][][] getFootprint(Long fpid) throws Exception;
	public Map getFootprints(String criteria) throws Exception;
	public void saveProject(Project project) throws Exception;
	public void saveProject(Project project, ProjectAlternative alternative) throws Exception;
	public void saveFootprint(Project project, double[][][] coords,String geoType) throws Exception;
        public void makeProjectsDiscussable(String criteria) throws Exception;
}
