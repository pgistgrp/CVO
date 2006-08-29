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
		return projectDAO.getProjects(criteria);
	}
	public void saveProject(Project project) throws Exception{
		projectDAO.save(project);
	}
	public void saveProject(Project project, ProjectAlternative alternative) throws Exception{
		projectDAO.save(project, alternative);
	}
	public void saveFootprint(Project project, double[][][] coords, String geoType) throws Exception{
		projectDAO.saveFootprint(project, coords, geoType);
	}
	public Map getFootprints(String fpids) throws Exception{
		return projectDAO.getFootprints(fpids);
	}
	public double[][][] getFootprint(Long fpid) throws Exception{
		return projectDAO.getFootprint(fpid);
	}

}
