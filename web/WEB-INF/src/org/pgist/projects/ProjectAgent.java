/**
 * 
 */
package org.pgist.projects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.apache.commons.collections.map.HashedMap;
import org.postgresql.jdbc3.Jdbc3Connection;

/**
 * @author Guirong
 *
 */
public class ProjectAgent {
	private Connection connection = null;
	private ProjectService projectService = null;	//need injection
	
	/**
	 * This is not an AJAX method
	 * @return
	 */
	public ProjectService getProjectService(){
		return this.projectService;
	}
	 
	/**
	 * This is not an AJAX method 
	 * @param ps
	 */
	public void setProjectService(ProjectService ps){
		this.projectService = ps;
	}
	
	
	//-------------------------------
	public ProjectAgent(){
		getDBConn();	//is it possible to inject a connection to this agent?
	}
	
	private void getDBConn(){
		try{
			Class.forName("org.postgresql.Driver");
			String url = "jdbc:postgresql://localhost:5432/gisdata";
			connection = DriverManager.getConnection(url, "pgist", "ppgis.2005");
			((Jdbc3Connection)connection).addDataType("geometry","org.postgis.PGgeometry");
		    ((Jdbc3Connection)connection).addDataType("box3d","org.postgis.PGbox3d");
		}catch(Exception e){
			System.out.println("==!!" + e.getMessage());
			return;
		}
	}
	
	/**
	 * 
	 * @param pId
	 * @return
	 * A map containing the project object and the coordinates
	 */
	public Project getProject(long pId){
		try {
			return (Project)projectService.getProject(pId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public Map getProjects(String criteria){
		Map result = new HashedMap();
		//projectDAO.getAllProjects();
		return result;
	}
	
	public Map saveProject(Map prjparams){
		Map result = new HashMap();
		if(prjparams == null){
			result.put("successful", false);
			result.put("reason", "No project attributes given.");
		}
		
		Project p = new Project();
		p.setName((String)prjparams.get("name"));
		p.setDescrption((String)prjparams.get("description"));
		p.setCost(Double.valueOf((String)prjparams.get("cost")));
		try{
			projectService.saveProject(p);
			result.put("successful", true);
			result.put("pid", p.getId());
		}catch (Exception e){
			result.put("successful", false);
			result.put("reason", e.getMessage());
		}
		
		
		return result;
	}
	
	public void saveAlternative(long pid, Map altparams){
		
	}
	
	public Map saveFootprint(long pid, double[][] coords){
		Map result = new HashMap();
		try {
			//long pid, double[][] coords, int[] parts, String geoType
			//Long pid = new Long((String)(fpparams.get("pid")));
			System.out.println("--pid=" + pid);
			System.out.println("--array len=" + coords.length);
			Project p = (Project) projectService.getProject(pid);
			int[] parts = {0};
			projectService.saveFootprint(connection, p , coords, parts, "LINE");
			result.put("successful", true);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.put("successful", false);
			result.put("reason", e.getMessage());
		}		
		return result;
	}

	public double[][] getFootprint(long fpid){
		return null;
	}
	
	public Map getFootprints(String criteria){
		return null;
	}
	

}
