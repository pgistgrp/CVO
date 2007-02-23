package org.pgist.packages;

import java.io.Serializable;

import org.pgist.criteria.Criteria;


/**
 * @author Mike and Guirong
 * 
 * @hibernate.class table="pgist_ag_pack_crit" lazy="true"
 */
public class PackageCriteria implements Serializable {
    
    
    private Long id;
    
    private Package pkg;
    
    private Criteria criterion;
    
    private Double value;
    
    
    /**
     * @hibernate.id generator-class="native"
     */
    public Long getId() {
        return id;
    }
    
    
    public void setId(Long id) {
        this.id = id;
    }
    
    
    /**
     * hibernate.many-to-one column="pkg_id" cascade="all" lazy="true"
     */
    public Package getPkg() {
        return pkg;
    }
    
    
    public void setPkg(Package thePackage) {
        this.pkg = thePackage;
    }
    
    
    /**
     * @hibernate.many-to-one column="criterion_id" cascade="all" lazy="true"
     */
    public Criteria getCriterion() {
        return criterion;
    }
    
    
    public void setCriterion(Criteria criterion) {
        this.criterion = criterion;
    }
    
    
    /**
     * @hibernate.property
     */
    public Double getValue() {
        return value;
    }
    
    
    public void setValue(Double value) {
        this.value = value;
    }
    
    
}//class PackageCriteria
