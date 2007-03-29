package org.pgist.projects;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.pgist.packages.Package;
import org.pgist.system.BaseDAOImpl;
import org.pgist.util.WKT;
import org.postgis.Geometry;
import org.postgis.PGgeometry;


public class ProjectDAOImpl extends BaseDAOImpl implements ProjectDAO {
    
    
	private static final String pfTableName = "pgist_data_project_footprint";
	private static final String pfTableName2 = "pgist_data_county";
	private static final String pfSequenceName = "gis_proj_footprint_id";
	
    
	/**
	 * Deletes the provided project
	 * 	
	 * @param p		The project to delete
	 * @throws Exception
	 */
    public void delete(Project p) throws Exception {
        getHibernateTemplate().delete(p);
    }//delete()	
	
    /*
	 * this is to be handled by BaseDAOImpl 
	 * @see org.pgist.projects.ProjectDAO#save(org.pgist.projects.Project)
	 */
	public void save(Project p) throws Exception{
		getHibernateTemplate().saveOrUpdate(p);
	}
	
    /*
	 * this is to be handled by BaseDAOImpl 
	 * @see org.pgist.projects.ProjectDAO#save(org.pgist.projects.Project)
	 */
	public void save(ProjectAlternative a) throws Exception{
		getHibernateTemplate().save(a);
	}	
	
	/**
	 * Deletes the project alternative
	 * 	
	 * @param p		The project alternative to delete
	 * @throws Exception
	 */
    public void delete(ProjectAlternative a) throws Exception {
        getHibernateTemplate().delete(a);
    }//delete()	
	
	/**
	 * Save a project, create a new footprint for it
	 * @param p: a project object
	 * @param coordinates: 2-d array, as line strings
	 * @param geoType: "POINT", "LINE", "POLYGON"
	 * @param parts: used only when a multi-part polygon is created as the foorprint,
	 * then find out how many lines are needed for each part.
	 * @throws Exception
	 */
	public void saveFootprint(ProjectAlternative pa, double[][][] coordinates, String geoType) throws Exception{
		Connection conn = getSession().connection();
		
		String wkt = null;
		int geometrytype = Geometry.MULTIPOINT;	//set to point by default
		
		if(geoType.compareToIgnoreCase("LINE") == 0){
			wkt = WKT.lineWKT(coordinates[0]);
			geometrytype = Geometry.MULTILINESTRING;
		}else if(geoType.compareToIgnoreCase("POINT") == 0){
			wkt = WKT.pointWKT(coordinates[0][0]);
		}
		
		System.out.println("-->new geom: " + wkt);
		synchronized(conn){
			Statement s = conn.createStatement();
			s.executeUpdate("insert into " + pfTableName + " (the_geom) "
					+ "values (GeomFromText('" + wkt + "'))");
			
			//use this to retrieve the last inserted footprint id
			ResultSet r = s.executeQuery("select currval('" + pfSequenceName + "')");
			if(r.next()){
				long newfpid = r.getLong(1);
				System.out.println("==>>inserted new geometry: id=" + newfpid + ", wkt=" + wkt);
				
				//put this into the project and save it
				pa.setFpids("" + newfpid);
				pa.setGeoType(geometrytype);
				this.save(pa);
			}
		}
					
	}//saveFootprint()
    

	public ProjectAlternative getProjectAlternative(long pid){
		return (ProjectAlternative)getHibernateTemplate().load(ProjectAlternative.class, pid);
	}	
	
	public ProjectAlternative getProjectAlternative(Long pid) throws Exception {
		return (ProjectAlternative)getHibernateTemplate().load(ProjectAlternative.class, pid);		
	}
	
	public ProjectAltRef getProjectAlternativeReference(Long altId) {
		return (ProjectAltRef)getHibernateTemplate().load(ProjectAltRef.class, altId);		
	}

	public void save(ProjectAltRef altRef) throws Exception{
		getHibernateTemplate().saveOrUpdate(altRef);		
	}
	
	public void save(GradedCriteria p) throws Exception {
		getHibernateTemplate().saveOrUpdate(p);		
	}
	public void save(GradedObjective p) throws Exception {
		getHibernateTemplate().saveOrUpdate(p);				
	}
	
    public void delete(ProjectAltRef a) throws Exception {
        getHibernateTemplate().delete(a);
    }//delete()		
	
	public void save(ProjectRef projectRef) throws Exception{
		getHibernateTemplate().saveOrUpdate(projectRef);		
	}	

    public void delete(ProjectRef a) throws Exception {
        getHibernateTemplate().delete(a);
    }//delete()	
    
	public void save(ProjectSuite suite) throws Exception{
		getHibernateTemplate().saveOrUpdate(suite);		
	}
	
	public Project getProject(long pid) throws Exception {
		return (Project)getHibernateTemplate().load(Project.class, pid);
	}
	
	private static String hql_getProjects_1 = "from Project as project";
	
	/**
	 * Given the criteria, return a map containing a set of projects, 
	 * and a set(as a hashmap) of footprints indexed by footprint IDs.
	 * @param String criteria: takes the form of "project.id = 15", "" will return all projects
	 */
	public List getProjects(String criteria){
		Query query = null;
		if(criteria == null || criteria.length()==0)
			query = getSession().createQuery(hql_getProjects_1);
		else
			query = getSession().createQuery(hql_getProjects_1 + " where " + criteria);
		
		return query.list();
	}
	
	public double[][][] getFootprint(long fpid){

		Connection conn = getSession().connection();
		
		Statement s;
		ResultSet r;
		try {
			s = conn.createStatement();
			r = s.executeQuery("select the_geom from " + pfTableName +
					" where id=" + fpid);
			
			if(!r.next())return null;
			
			PGgeometry geom = (PGgeometry)r.getObject(1);
			return WKT.geomToArray(geom);
			
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
	public Map getFootprints(String fpids){
		Connection conn = getSession().connection();
		Map footprints = new HashMap();
		
		String whereclause = (fpids.length()==0)?"":" where id in (" + fpids + ")";
		
		try {
			Statement s = conn.createStatement();
			ResultSet r = s.executeQuery("select id, the_geom from " +
					"(select id, the_geom from " + pfTableName + 
					" union select id, the_geom from " + pfTableName2 + ") as allfps " +
					whereclause);
			
			while(r.next()){
				Long id = r.getLong(1);
				PGgeometry geom = (PGgeometry)r.getObject(2);
				int[] parts = {};
				
				double[][][] coords = new double[1][][]; 
				coords = WKT.geomToArray(geom);
				System.out.println("==>>id=" + id + ";coords length: " + coords.length);
				footprints.put(id, coords);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		
		return footprints;
	}
	
    
    private static String hql_getProjects_2 = "from Project p order by p.name asc";
    
    
    public Collection getProjects() throws Exception {
        return getHibernateTemplate().find(hql_getProjects_2);
    }//getProjects()

    
    
    // ************* Project Suite ******************************** 
    
	public ProjectSuite getProjectSuite(Long suiteID) throws Exception {
		return (ProjectSuite)getHibernateTemplate().load(ProjectSuite.class, suiteID);
	}

    
}//class ProjectDAOImpl
