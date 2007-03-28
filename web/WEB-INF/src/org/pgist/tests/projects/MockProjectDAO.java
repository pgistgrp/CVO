package org.pgist.tests.projects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.pgist.packages.Package;
import org.pgist.projects.GradedCriteria;
import org.pgist.projects.GradedObjective;
import org.pgist.projects.Project;
import org.pgist.projects.ProjectAltRef;
import org.pgist.projects.ProjectAlternative;
import org.pgist.projects.ProjectDAO;
import org.pgist.projects.ProjectRef;
import org.pgist.projects.ProjectSuite;
import org.pgist.users.User;

/**
 * Use this class to create a mock interface to be used with
 * @author Matt Paulin
 *
 */
/**
 * @author Matt Paulin
 *
 */
public class MockProjectDAO implements ProjectDAO {

	//A collection of the objects deleted
	private ArrayList deleted = new ArrayList();
	
	public ArrayList getDeleted() {
		return deleted;
	}
	/**
	 * Resets the deleted
	 */
	public void clearDeleted() {
		this.deleted.clear();
	}
	
	
	//A collection of all that was saved;
	private ArrayList saved = new ArrayList();
	
	public ArrayList getSaved() {
		return saved;		
	}
	
	/**
	 * Resets the saved collection
	 */
	public void clearSaved() {
		saved.clear();
	}
	
	
	public void delete(Project p) throws Exception {
		this.deleted.add(p);

	}

	public void delete(ProjectAlternative a) throws Exception {
		this.deleted.add(a);
	}

	public void delete(ProjectAltRef altRef) throws Exception {
		this.deleted.add(altRef);
	}

	public void delete(ProjectRef projectRef) throws Exception {
		this.deleted.add(projectRef);
	}

	public double[][][] getFootprint(long fpid) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public Map getFootprints(String fpids) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public Package getPackage(Long pid) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public Project getProject(long pid) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	private ProjectAlternative projectAlternative;
	
	public void setProjectAlternative(ProjectAlternative projectAlternative) {
		this.projectAlternative = projectAlternative;
	}
	
	public ProjectAlternative getProjectAlternative(Long pid) throws Exception {		
		return this.projectAlternative;
	}
	private ProjectAltRef projectAltRef;
	public void setProjectAlternativeReference(ProjectAltRef projectAltRef) {
		this.projectAltRef = projectAltRef;
	}
	
	public ProjectAltRef getProjectAlternativeReference(Long altId) {
		return this.projectAltRef;
	}

	private ProjectSuite suite;
	public void setProjectSuite(ProjectSuite suite) {
		this.suite = suite;
	}
	
	public ProjectSuite getProjectSuite(Long suiteID) throws Exception {
		return suite;
	}

	public List getProjects(String criteria) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection getProjects() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public void save(Project p) throws Exception {
		// TODO Auto-generated method stub

	}

	public void save(GradedCriteria p) throws Exception {
		this.saved.add(p);		
	}
	public void save(GradedObjective p) throws Exception {
		this.saved.add(p);		
	}
	public void save(Package p) throws Exception {
		this.saved.add(p);
	}

	public void save(ProjectAlternative a) throws Exception {
		this.saved.add(a);
	}

	public void save(ProjectAltRef altRef) throws Exception {
		this.saved.add(altRef);
	}

	public void save(ProjectRef projectRef) throws Exception {
		this.saved.add(projectRef);
	}

	public void save(ProjectSuite suite) throws Exception {
		this.saved.add(suite);
	}

	public void saveFootprint(ProjectAlternative pa, double[][][] coords,
			String type) throws Exception {
		// TODO Auto-generated method stub

	}

	public User getUserById(Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public Object load(Class klass, Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public void save(Object object) throws Exception {
		this.saved.add(object);
	}

}
