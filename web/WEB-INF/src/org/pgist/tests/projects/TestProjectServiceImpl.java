package org.pgist.tests.projects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.pgist.projects.Project;
import org.pgist.projects.ProjectAltRef;
import org.pgist.projects.ProjectAlternative;
import org.pgist.projects.ProjectRef;
import org.pgist.projects.ProjectServiceImpl;
import org.pgist.projects.ProjectSuite;

/**
 * Used to test the functionality ProjectServiceImpl
 * 
 */
public class TestProjectServiceImpl {

	private ProjectServiceImpl projectService = new ProjectServiceImpl();
	private MockProjectDAO projectDAO = null;
	private MockCriteriaDAO criteriaDAO = null;
	
	@Before
	public void setUp() {
		projectDAO = new MockProjectDAO();
		projectService.setProjectDAO(projectDAO);		

		criteriaDAO = new MockCriteriaDAO();
		projectService.setCriteriaDAO(criteriaDAO);			
	}
	
	/**
	 * Test adding a reference to the project service
	 */
	/**
	 * @throws Exception
	 */
	@Test
	public void testRelate() throws Exception {
		Long suiteId = new Long(1);
		ProjectSuite suite = new ProjectSuite();
		suite.setId(suiteId);
		
		Long altId = new Long(42);
		ProjectAlternative alt = new ProjectAlternative();
		alt.setId(altId);
		Project project = new Project();
		project.setId(new Long(21));
		alt.setProject(project);
		
		ProjectAltRef altRef = new ProjectAltRef();
		
		//Set all the preformed information
		projectDAO.setProjectSuite(suite);
		projectDAO.setProjectAlternative(alt);
		projectDAO.clearSaved();
				
		projectService.relateProjectAlt(suiteId, altId);
		
		ArrayList saved = projectDAO.getSaved();
		assertEquals(4, saved.size());
		Object obj;
		obj = saved.get(0);
		if(!(obj instanceof ProjectAltRef)) {
			fail("First should have saved the AltRef");
		}
		obj = saved.get(1);
		if(!(obj instanceof ProjectRef)) {
			fail("should have saved the ProjectRef");
		}
		obj = saved.get(2);		
		if(!(obj instanceof ProjectSuite)) {
			fail("should have saved the ProjectSuite");
		} else {
			suite = (ProjectSuite)obj;
		}
		obj = saved.get(3);
		if(!(obj instanceof ProjectRef)) {
			fail("should have saved the ProjectRef");
		}
		Set references = suite.getReferences();
		assertEquals(1, references.size());
		
		ProjectRef ref = (ProjectRef)references.toArray()[0];
		assertEquals(new Long(21), ref.getProject().getId());
		assertEquals(1, ref.getAltRefs().size());
		
		ProjectAltRef tempAltRef = (ProjectAltRef)ref.getAltRefs().toArray()[0];
		assertEquals(new Long(42), tempAltRef.getAlternative().getId());
		
		alt = new ProjectAlternative();
		altId = new Long(231);
		alt.setId(altId);
		project = new Project();
		project.setId(new Long(21));
		alt.setProject(project);		

		//Set all the preformed information
		projectDAO.setProjectSuite(suite);
		projectDAO.setProjectAlternative(alt);
		projectDAO.clearSaved();
				
		projectService.relateProjectAlt(suiteId, altId);
		
		saved = projectDAO.getSaved();
		assertEquals(2, saved.size());
		obj = saved.get(0);
		if(!(obj instanceof ProjectAltRef)) {
			fail("First should have saved the AltRef");
		}
		obj = saved.get(1);
		if(!(obj instanceof ProjectRef)) {
			fail("should have saved the ProjectRef");
		}
		references = suite.getReferences();
		assertEquals(1, references.size());
		
		ref = (ProjectRef)references.toArray()[0];
		assertEquals(new Long(21), ref.getProject().getId());
		assertEquals(2, ref.getAltRefs().size());
		
		tempAltRef = (ProjectAltRef)ref.getAltRefs().toArray()[1];
		if(tempAltRef.getAlternative().getId().equals(new Long(42))) {
			tempAltRef = (ProjectAltRef)ref.getAltRefs().toArray()[0];			
		}
		assertEquals(new Long(231), tempAltRef.getAlternative().getId());
		
		//Now to dereference
		
		//Set all the preformed information
		projectDAO.setProjectSuite(suite);
		projectDAO.setProjectAlternative(alt);
		
		projectDAO.clearSaved();
		projectDAO.clearDeleted();

		Set<ProjectRef> altRefs = suite.getReferences();				
		assertEquals(2, ((ProjectRef)(altRefs.toArray())[0]).getAltRefs().size());
		
		Set<ProjectAltRef> projectAltRefs = ((ProjectRef)(altRefs.toArray())[0]).getAltRefs();
		ProjectAltRef altRef1 = (ProjectAltRef)projectAltRefs.toArray()[0];
		ProjectAltRef altRef2 = (ProjectAltRef)projectAltRefs.toArray()[1];
		
		projectDAO.setProjectAlternativeReference(altRef1);
		
		projectService.derelateProjectAlt(suiteId, altId);		
		assertEquals(1, projectDAO.getSaved().size());
		assertEquals(1, projectDAO.getDeleted().size());
		assertEquals(1, ((ProjectRef)(suite.getReferences().toArray())[0]).getAltRefs().size());
		
		projectDAO.clearSaved();
		projectDAO.clearDeleted();
		
		projectDAO.setProjectAlternativeReference(altRef2);
		projectService.derelateProjectAlt(suiteId, new Long(42));		
		assertEquals(0, ((ProjectRef)(suite.getReferences().toArray())[0]).getAltRefs().size());
		assertEquals(2, projectDAO.getDeleted().size());
		assertEquals(0, projectDAO.getSaved().size());
		
	}
	

//	@Test
//	public void testDerelate() throws Exception {
//		Long suiteId = new Long(1);
//		ProjectSuite suite = new ProjectSuite();
//		suite.setId(suiteId);
//		
//		Long altId = new Long(42);
//		ProjectAlternative alt = new ProjectAlternative();
//		alt.setId(altId);
//
//		criteriaDAO.expects(once()).method("getAppointments").will(
//				returnValue(results));
//		projectDAO.expects(once()).method("getProjectSuite").will(
//				returnValue(results));		
//		
//		projectService.derelateProjectAlt(suiteId, altId);
//	}
	

}

