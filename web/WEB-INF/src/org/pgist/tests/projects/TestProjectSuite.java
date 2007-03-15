package org.pgist.tests.projects;

import org.pgist.projects.Project;
import org.pgist.projects.ProjectAltRef;
import org.pgist.projects.ProjectAlternative;
import org.pgist.projects.ProjectRef;
import org.pgist.projects.ProjectSuite;

import static org.junit.Assert.*;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Used to test the functionality of the ProjectSuite
 * 
 * @author Matt Paulin
 */
public class TestProjectSuite {
	
	/**
	 * Test that an alt Ref is added properly to the project suite
	 */
	@Test
	public void testGettingRefereces() {
		
		//Create the project suite
		ProjectSuite suite = new ProjectSuite();
		
		Project project1 = new Project();
		project1.setId(new Long(1));
		
		Project project2 = new Project();
		project2.setId(new Long(2));		
		
		ProjectAlternative pAlt1 = new ProjectAlternative();
		pAlt1.setId(new Long(1));
		pAlt1.setProject(project1);
		ProjectAlternative pAlt2 = new ProjectAlternative();
		pAlt2.setId(new Long(2));
		pAlt2.setProject(project1);
		ProjectAlternative pAlt3 = new ProjectAlternative();
		pAlt3.setId(new Long(3));
		pAlt3.setProject(project2);
		
		ProjectAltRef altRef1 = new ProjectAltRef();
		altRef1.setId(new Long(1));
		altRef1.setAlternative(pAlt1);
		ProjectAltRef altRef2 = new ProjectAltRef();
		altRef2.setId(new Long(2));
		altRef2.setAlternative(pAlt2);
		ProjectAltRef altRef3 = new ProjectAltRef();
		altRef3.setId(new Long(3));		
		altRef3.setAlternative(pAlt3);
		
		ProjectRef pRef1 = new ProjectRef();		
		pRef1.setProject(project1);
		pRef1.getAltRefs().add(altRef1);
		pRef1.getAltRefs().add(altRef2);
		
		ProjectRef pRef2 = new ProjectRef();
		pRef2.setProject(project2);
		pRef2.getAltRefs().add(altRef3);
		
		assertNull(suite.getProjectReferece(project1));
		assertNull(suite.getProjectReferece(project2));
		
		assertNull(suite.getProjectReferece(altRef1));
		assertNull(suite.getProjectReferece(altRef2));
		assertNull(suite.getProjectReferece(altRef3));
		
		assertFalse(suite.containsAlts(new Long(1)));
		assertFalse(suite.containsAlts(new Long(2)));
		assertFalse(suite.containsAlts(new Long(3)));

		//Add a project
		suite.getReferences().add(pRef1);
		assertEquals(new Long(1), suite.getProjectReferece(project1).getProject().getId());
		assertNull(suite.getProjectReferece(project2));

		assertEquals(new Long(1), suite.getProjectReferece(altRef1).getProject().getId());
		assertEquals(new Long(1), suite.getProjectReferece(altRef2).getProject().getId());
		assertNull(suite.getProjectReferece(altRef3));
		
		assertTrue(suite.containsAlts(new Long(1)));
		assertTrue(suite.containsAlts(new Long(2)));
		assertFalse(suite.containsAlts(new Long(3)));
		
		//Add the next project
		suite.getReferences().add(pRef2);
		assertEquals(new Long(1), suite.getProjectReferece(project1).getProject().getId());
		assertEquals(new Long(2), suite.getProjectReferece(project2).getProject().getId());
		
		assertEquals(new Long(1), suite.getProjectReferece(altRef1).getProject().getId());
		assertEquals(new Long(1), suite.getProjectReferece(altRef2).getProject().getId());
		assertEquals(new Long(2), suite.getProjectReferece(altRef3).getProject().getId());

		assertTrue(suite.containsAlts(new Long(1)));
		assertTrue(suite.containsAlts(new Long(2)));
		assertTrue(suite.containsAlts(new Long(3)));
	}
}
