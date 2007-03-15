package org.pgist.criteria;

import java.util.HashMap;
import java.util.Map;


/**
 * A CriteriaRef refers to a Criteria object.
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_crit_ref" lazy="true"
 */
public class CriteriaRef {
    
    
    private Long id;
    
    private CriteriaSuite suite;
    
    private Criteria criterion;
    
    private int grade;
    
    private Map<Objective, Integer> objectiveGrades = new HashMap<Objective, Integer>();
    
    
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
    public int getGrade() {
        return grade;
    }


    public void setGrade(int grade) {
        this.grade = grade;
    }


    /**
     * @return
     * 
     * @hibernate.map table="pgist_crit_ref_obj_grade_map"
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
    
    
}//class CriteriaRef
