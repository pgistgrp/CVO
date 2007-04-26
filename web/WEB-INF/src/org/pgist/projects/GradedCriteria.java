package org.pgist.projects;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import org.pgist.criteria.Criteria;


/**
 * A criteria that has been assigned a grade
 * 
 * @author Matt Paulin
 *
 * @hibernate.class table="pgist_project_graded_criteria" lazy="true"
 */
public class GradedCriteria {

	private SortedSet<GradedObjective> objectives = new TreeSet<GradedObjective>(new GradedObjectiveComparator());
    private Long id;
	private Criteria criteria;	
	private String grade ="NA";
	private float value;

		
	/**
     * @hibernate.id generator-class="native"
     */
    public Long getId(){
        return this.id;
    }
    
    
    public void setId(Long id){
        this.id = id;
    }	
	
    
    /**
	 * @return the value
     * @hibernate.property not-null="true"
	 */
	public float getValue() {
		return value;
	}


	/**
	 * @param value the value to set
	 */
	public void setValue(float avgScore) {
		this.value = avgScore;
	}

	/**
     * Returns the grade
     * 
     * @return	The grade
     * @hibernate.property not-null="true"
     */
    public String getGrade() {
    	return this.grade;
    }
    
    public void setGrade(String grade) {
    	this.grade = grade;
    }
    
    /**
     * @return
     * 
     * @hibernate.many-to-one column="criteria_id" cascade="none" lazy="true"
     */
	public Criteria getCriteria() {
		return criteria;
	}

	public void setCriteria(Criteria criteria) {
		this.criteria = criteria;
	}

    /**
     * @return
     * 
     * @hibernate.set lazy="false" cascade="all-delete-orphan" sort="org.pgist.projects.GradedObjectiveComparator"
     * @hibernate.collection-key column="graded_criteria_id"
     * @hibernate.collection-one-to-many class="org.pgist.projects.GradedObjective"
     */
    public SortedSet<GradedObjective> getObjectives() {
		return objectives;
	}

	public void setObjectives(SortedSet<GradedObjective> objectives) {
		this.objectives = objectives;
	}
	
	/**
	 * Recalculates the grade of the criteria based on the objectives
	 * 
	 * @return	The grade for this objective
	 */
	public void recalcGrade() {		
		this.value = calcGrade(objectives);
		this.grade = convertGrade(this.value);
	}

	/**
	 * Static method for calculating a grade
	 * 
	 * @param	objectives	A list of objectives to get the grade from
	 * @return	A string version of the grade
	 */
	public static float calcGrade(SortedSet<GradedObjective> objectives) {
		float totalScore = 0;
		float avgScore = 0;
		
		//Average the different objectives together
		Iterator<GradedObjective> i = objectives.iterator();
		GradedObjective tempObj;
		while(i.hasNext()) {
			tempObj = i.next();
			if(tempObj.getGrade() != null) {
				//Add 3 to equalize the score
				totalScore = totalScore + (float)tempObj.getGrade() + 3;				
			}
		}
		
		avgScore = totalScore/objectives.size();
		
		//Now bring it back to a percentage
		avgScore = avgScore/6 * 100;

		return avgScore;
	}
	
	/**
	 * Takes a value and turns it into a letter grade
	 * 
	 * @param	value	The score to convert to a letter
	 * @return	The letter grade
	 */
	private static String convertGrade(float avgScore) {
		String letter = "NA";
		
		//choose the letter grade based off the final result
		if(avgScore > 93) {
			letter = "A";
		} else if (avgScore > 90 ){
			letter = "A-";			
		} else if (avgScore > 87 ){
			letter = "B+";			
		} else if (avgScore > 83 ){
			letter = "B";			
		} else if (avgScore > 80 ){
			letter = "B-";
		} else if (avgScore > 77 ){
			letter = "C+";
		} else if (avgScore > 73 ){
			letter = "C";
		} else if (avgScore > 70 ){
			letter = "C-";
		} else if (avgScore > 67 ){
			letter = "D+";
		} else if (avgScore > 63 ){
			letter = "D";
		} else if (avgScore > 60 ){
			letter = "D-";			
		} else if (avgScore > 57 ){
			letter = "F+";			
		} else {
			letter = "F";			
		}
		return letter;
	}
}
