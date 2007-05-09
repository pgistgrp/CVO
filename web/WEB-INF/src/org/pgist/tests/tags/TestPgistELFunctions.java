package org.pgist.tests.tags;

import org.pgist.projects.Project;
import org.pgist.projects.ProjectAltRef;
import org.pgist.projects.ProjectAlternative;
import org.pgist.projects.ProjectRef;
import org.pgist.projects.ProjectSuite;
import org.pgist.tags.PgistELFunctions;

import static org.junit.Assert.*;

import org.junit.Test;

import common.Assert;

import static org.junit.Assert.*;

/**
 * Used to test the functionality of the ProjectSuite
 * 
 * @author Matt Paulin
 */
public class TestPgistELFunctions {
	
	/**
	 * Test that an alt Ref is added properly to the project suite
	 */
	@Test
	public void testGettingRefereces() {
		String in;
		String out;
		
		in = "B+";
		out = PgistELFunctions.gradeSwitch(in);
		assertEquals("BPlus", out);

		in = "B";
		out = PgistELFunctions.gradeSwitch(in);
		assertEquals("B", out);

		in = "B-";
		out = PgistELFunctions.gradeSwitch(in);
		assertEquals("BMinus", out);

	}
}
