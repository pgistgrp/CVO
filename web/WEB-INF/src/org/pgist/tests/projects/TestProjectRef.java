package org.pgist.tests.projects;

import org.pgist.projects.ProjectAltRef;
import org.pgist.projects.ProjectRef;
import org.pgist.tests.PGISTTest;

/**
 * Used to test the functionality of the ProjectSuite
 * 
 * @author Matt Paulin
 */
public class TestProjectRef extends PGISTTest {
	
	/**
	 * Test that an alt Ref can be removed from the project alt ref
	 */
	public void testRemovingProjectAltRef() {
		

		ProjectRef pRef = new ProjectRef();
		ProjectAltRef altRef1 = new ProjectAltRef();
		altRef1.setId(new Long(1));
		ProjectAltRef altRef2 = new ProjectAltRef();
		altRef2.setId(new Long(2));
		ProjectAltRef altRef3 = new ProjectAltRef();
		altRef3.setId(new Long(2));
		
		assertEquals(0, pRef.getNumAltRefs());
		
		pRef.getAltRefs().add(altRef1);
		assertEquals(1, pRef.getNumAltRefs());

		pRef.getAltRefs().add(altRef2);
		assertEquals(2, pRef.getNumAltRefs());
		
		pRef.removeAltRef(altRef1);
		assertEquals(1, pRef.getNumAltRefs());
		
		//Test removing with a different object with the same id
		pRef.removeAltRef(altRef3);
		assertEquals(0, pRef.getNumAltRefs());
		
	}
}
