package org.pgist.tests.projects;

import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Test;
import org.pgist.criteria.Objective;
import org.pgist.projects.GradedCriteria;
import org.pgist.projects.GradedObjective;
import org.pgist.projects.GradedObjectiveComparator;

import com.mchange.util.AssertException;

import static org.junit.Assert.*;

/**
 * Used to test the functionality of the ProjectSuite
 * 
 * @author Matt Paulin
 */
public class TestGradedCriteria {
	
	/**
	 * Test that grades are created properly
	 */
	@Test
	public void testGradingObjectives() {
	
		SortedSet<GradedObjective> objectives = new TreeSet<GradedObjective>(new GradedObjectiveComparator());
		
		//Works out that with 7 options, 14 objectives means I can change one one point
		//and alter the percentage by 1... roughly
		objectives.add(createGradedObjective(3));
		objectives.add(createGradedObjective(3));
		objectives.add(createGradedObjective(3));
		objectives.add(createGradedObjective(3));
		objectives.add(createGradedObjective(3));
		objectives.add(createGradedObjective(3));
		objectives.add(createGradedObjective(3));
		objectives.add(createGradedObjective(3));
		objectives.add(createGradedObjective(3));
		objectives.add(createGradedObjective(3));
		objectives.add(createGradedObjective(3));
		objectives.add(createGradedObjective(3));
		objectives.add(createGradedObjective(3));
		objectives.add(createGradedObjective(3));
		
		assertEquals("A", GradedCriteria.calcGrade(objectives));

		objectives = new TreeSet<GradedObjective>(new GradedObjectiveComparator());		
		objectives.add(createGradedObjective(-3));
		objectives.add(createGradedObjective(3));
		objectives.add(createGradedObjective(3));
		objectives.add(createGradedObjective(3));
		objectives.add(createGradedObjective(3));
		objectives.add(createGradedObjective(3));
		objectives.add(createGradedObjective(3));
		objectives.add(createGradedObjective(3));
		objectives.add(createGradedObjective(3));
		objectives.add(createGradedObjective(3));
		objectives.add(createGradedObjective(3));
		objectives.add(createGradedObjective(3));
		objectives.add(createGradedObjective(3));
		objectives.add(createGradedObjective(3));
		
		assertEquals("A-", GradedCriteria.calcGrade(objectives));

		
		objectives = new TreeSet<GradedObjective>(new GradedObjectiveComparator());		
		objectives.add(createGradedObjective(-3));
		objectives.add(createGradedObjective(0));
		objectives.add(createGradedObjective(3));
		objectives.add(createGradedObjective(3));
		objectives.add(createGradedObjective(3));
		objectives.add(createGradedObjective(3));
		objectives.add(createGradedObjective(3));
		objectives.add(createGradedObjective(3));
		objectives.add(createGradedObjective(3));
		objectives.add(createGradedObjective(3));
		objectives.add(createGradedObjective(3));
		objectives.add(createGradedObjective(3));
		objectives.add(createGradedObjective(3));
		objectives.add(createGradedObjective(3));
		
		assertEquals("B+", GradedCriteria.calcGrade(objectives));
		
		objectives = new TreeSet<GradedObjective>(new GradedObjectiveComparator());		
		objectives.add(createGradedObjective(-3));
		objectives.add(createGradedObjective(-3));
		objectives.add(createGradedObjective(3));
		objectives.add(createGradedObjective(3));
		objectives.add(createGradedObjective(3));
		objectives.add(createGradedObjective(3));
		objectives.add(createGradedObjective(3));
		objectives.add(createGradedObjective(3));
		objectives.add(createGradedObjective(3));
		objectives.add(createGradedObjective(3));
		objectives.add(createGradedObjective(3));
		objectives.add(createGradedObjective(3));
		objectives.add(createGradedObjective(3));
		objectives.add(createGradedObjective(3));
		
		assertEquals("B", GradedCriteria.calcGrade(objectives));		
	}
	
	private static long objCounter = 0;
	private GradedObjective createGradedObjective(int grade) {
		GradedObjective gradedObj = new GradedObjective();
		Objective obj = new Objective();
		obj.setId(new Long(objCounter++));
		obj.setDescription("Obj" + objCounter);
		gradedObj.setObjective(obj);
		gradedObj.setGrade(grade);
		return gradedObj;		
	}
	
}
