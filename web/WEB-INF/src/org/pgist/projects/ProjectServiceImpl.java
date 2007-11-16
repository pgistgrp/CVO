/**
 * 
 */
package org.pgist.projects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Map;

import org.postgresql.jdbc3.Jdbc3Connection;

/**
 * @author Guirong
 *
 */
public class ProjectServiceImpl implements ProjectService{
	private ProjectDAO projectDAO = null;
	private Connection connection = null;
		
	public ProjectDAO getProjectDAO(){
		return this.projectDAO;
	}

	public void setProjectDAO(ProjectDAO p){
		this.projectDAO = p;
	}
	
	public Connection getConnection(){
		return this.connection;
	}
	
	public void setConnection(Connection c){
		this.connection = c;
	}

	//-----------------------------------------------
	public Project getProject(Long pId) throws Exception{
		return projectDAO.getProject(pId);
	}
	public List getProjects(String criteria) throws Exception{
		return projectDAO.getAllProjects();
	}
	public void saveProject(Project project) throws Exception{
		projectDAO.save(project);
	}
	public void saveProject(Project project, ProjectAlternative alternative) throws Exception{
		projectDAO.save(alternative);
	}
	public void saveFootprint(Connection conn, Project project, double[][] coords, int[] parts, String geoType) throws Exception{
		projectDAO.saveFootprint(conn, project, coords, parts, geoType);
	}
	public Map getFootprints(String criteria){
		return null;
	}


}
