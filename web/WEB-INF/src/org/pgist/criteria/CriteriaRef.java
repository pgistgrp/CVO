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
    
    private Map<String, Integer> objGrades = new HashMap<String, Integer>();
    
    
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
     * @hibernate.map table="pgist_crit_ref_obj_grade_map"
     * @hibernate.collection-key column="critref_id"
     * @hibernate.collection-index column="objective" type="string"
     * @hibernate.collection-element type="integer" column="grade"
     */
    public Map<String, Integer> getObjGrades() {
        return objGrades;
    }


    public void setObjGrades(Map<String, Integer> objGrades) {
        this.objGrades = objGrades;
    }
    
    
}//class CriteriaRef
