package org.pgist.projects;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.sql.*;

import org.hibernate.Query;

import org.pgist.system.BaseDAOImpl;
import org.pgist.util.WKT;

import org.apache.commons.collections.map.HashedMap;
import org.pgist.system.BaseDAOImpl;
import org.postgis.*;
import org.postgresql.jdbc3.Jdbc3Connection;

public class ProjectDAOImpl extends BaseDAOImpl implements ProjectDAO {
	
	/*
	 * this is to be handled by BaseDAOImpl 
	 * @see org.pgist.projects.ProjectDAO#save(org.pgist.projects.Project)
	 */
	public void save(Project p) throws Exception{
		getHibernateTemplate().saveOrUpdate(p);
	}
	
	public void save(ProjectAlternative a) throws Exception{
		getHibernateTemplate().saveOrUpdate(a);
	}

	/**
	 * Save a project, create a new footprint for it
	 * @param p: a project object
	 * @param coordinates: 2-d array, as line strings
	 * @param geoType: "POINT", "LINE", "POLYGON"
	 * @param parts: used only when a multi-part polygon is created as the foorprint,
	 * then find out how many lines are needed for each part.
	 * @throws Exception
	 */
	public void saveFootprint(Connection conn, Project p, double[][] coordinates, int[] parts, String geoType) throws Exception{
		
		//create a footprint record and remember the id
		String wkt = null;
		if(geoType.compareToIgnoreCase("LINE") == 0){
			wkt = WKT.lineWKT(coordinates);
		}else if(geoType.compareToIgnoreCase("POINT") == 0){
			wkt = WKT.pointWKT(coordinates[0]);
		}
		
		synchronized(conn){
			Statement s = conn.createStatement();
			s.executeUpdate("insert into gis_project_footprint (the_geom) "
					+ "values (GeomFromText('" + wkt + "'))");
			
			//use this to retrieve the last inserted footprint id
			ResultSet r = s.executeQuery("select currval('gis_proj_footprint_id')");
			if(r.next()){
				long newfpid = r.getLong(1);			
				System.out.println("==inserted new geometry: id=" + newfpid + ", wkt=" + wkt);
				
				//put this into the project and save it
				p.setFpids("" + newfpid);
				this.save(p);
			}
		}
					
	}

	public void save(Package p) throws Exception{
		getHibernateTemplate().saveOrUpdate(p);		
	}
	
	public Project getProject(long pid){
		return (Project)getHibernateTemplate().load(Project.class, pid);
	}
	
	private static String hql_getProjects = "from Project as project";
	public List getAllProjects(){
        Query query = getSession().createQuery(hql_getProjects);
		
		return query.list();
	}
	
	/**
	 * Given the criteria, return a map containing a set of porejects, 
	 * and a set(as a hashmap) of footprints indexed by footprint IDs.
	 * @param String criteria: takes the form of "project.id = 15"
	 */
	public List getProjects(String criteria){
        Query query = getSession().createQuery(hql_getProjects + " where " + criteria);
		
		return query.list();
	}
	
	public double[][] getFootprint(Connection conn, long fpid){
		//do query on project table
		Statement s;
		ResultSet r;
		try {
			s = conn.createStatement();
			r = s.executeQuery("select the_geom from gis_project_footprint" +
					" where id=" + fpid);
			
			if(!r.next())return null;
			
			PGgeometry geom = (PGgeometry)r.getObject(1);
			int[] parts = {};
			return WKT.geomToArray(geom, parts);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * return a map, containing a list of footprint ids and
	 * coordinates 
	 */
	public Map getFootprints(Connection conn, String fpids){
		Map footprints = new HashMap();
		
		try {
			Statement s = conn.createStatement();
			ResultSet r = s.executeQuery("select id, the_geom from gis_project_footprint" +
					" where id in (" + fpids + ")");
			
			while(r.next()){
				Long id = r.getLong(1);
				PGgeometry geom = (PGgeometry)r.getObject(2);
				int[] parts = {};
				
				footprints.put(id, 
						WKT.geomToArray(geom, parts));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		
		return footprints;
	}
	
	public Package getPackage(Long pid){
		return null;
	}
	
}
