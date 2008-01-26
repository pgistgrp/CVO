package org.pgist.criteria;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.pgist.projects.UnknownObjectiveException;


/**
 * A CriteriaRef refers to a Criteria object.
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_crit_ref" lazy="true"
 */
public class CriteriaRef implements Comparator {
    
    
    private Long id;
    
    private CriteriaSuite suite;
    
    private Criteria criterion;
    
    private float grade; //compute average grade for this criterion based on objective scores
    
    private Map<Objective, Integer> objectiveGrades = new HashMap<Objective, Integer>(); //objective object, grade
    
    /**
     * @return
     * 
     * @hibernate.id generator-class="native"
     */
    public Long getId() {
        return id;
    }
    
    
    public void setId(Long id) {
        this.id = id;
    }


    /**
     * @return
     * 
     * @hibernate.many-to-one column="suite_id" cascade="none"
     */
    public CriteriaSuite getSuite() {
        return suite;
    }


    public void setSuite(CriteriaSuite suite) {
        this.suite = suite;
    }


    /**
     * @return
     * 
     * @hibernate.many-to-one column="crit_id" cascade="none"
     */
    public Criteria getCriterion() {
        return criterion;
    }


    public void setCriterion(Criteria criterion) {
        this.criterion = criterion;
    }


    /**
     * @return
     * 
     * @hibernate.property
     */
    public float getGrade() {
        return grade;
    }


    public void setGrade(float grade) {
        this.grade = grade;
    }


    /**
     * @return
     * 
     * @hibernate.map table="pgist_crit_ref_obj_grade_map" cascade="all"
     * @hibernate.collection-key column="critref_id"
     * @hibernate.index-many-to-many column="obj_id" class="org.pgist.criteria.Objective"
     * @hibernate.collection-element type="integer" column="grade"
     */
    public Map<Objective, Integer> getObjectiveGrades() {
        return objectiveGrades;
    }


    public void setObjectiveGrades(Map<Objective, Integer> objectiveGrades) {
        this.objectiveGrades = objectiveGrades;
    }

    
    /**
     * Helper method that sets a specific grade in the Criteria Reference
     * 
     * @param objId		The objective
     * @param value		The new grade
     * @return	The new overall grade for the objective
     * @throws	An unknown objective exception if you can't find the objective
     */
	public float setObjectiveGrade(Long objId, int value) throws UnknownObjectiveException {
		Iterator i = this.objectiveGrades.keySet().iterator();
		Objective tempObj;
		while(i.hasNext()) {
			tempObj = (Objective)i.next();
			if(tempObj.getId().equals(objId)) {
				this.objectiveGrades.put(tempObj, value);
				recalcGrade();
				return this.getGrade();
			}
		}
		throw new UnknownObjectiveException("Could not find the Objective [" + objId + "]");
	}
    
	
	/**
	 * Recalculates the grade
	 */
	public void recalcGrade() {
		 Collection<Integer> values = objectiveGrades.values();
		 int size = values.size();
		 int total = 0;
		 for(Integer i : values) {
			 total += i.intValue();
		 }
		 grade = total/size;
	}    
	
	
	public int compare(Object o1, Object o2) {
		
		return ((CriteriaRef)o1).getCriterion().getName().compareToIgnoreCase(((CriteriaRef)o2).getCriterion().getName());
	}
	
	
}//class CriteriaRef
