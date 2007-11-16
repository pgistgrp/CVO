package org.pgist.criteria;


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
    
    
}//class CriteriaRef
