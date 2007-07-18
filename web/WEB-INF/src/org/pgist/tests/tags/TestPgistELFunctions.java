package org.pgist.tests.tags;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.pgist.tags.PgistELFunctions;

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
