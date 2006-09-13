package org.pgist.projects;


/**
 * @author  Mike and Guirong
 * @hibernate.class  table="pgist_ag_pack_crit" lazy="true"
 */
public class PackageCriteria {
    
    
    private Long id;
    
    private Package pkg;
    
    private Criteria criterion;
    
    private Double value;
    
    
    /**
     * @hibernate.id  generator-class="native"
     * @uml.property  name="id"
     */
    public Long getId() {
        return id;
    }
    
    
    /**
     * @param id  the id to set
     * @uml.property  name="id"
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    
    /**
     * @hibernate.property  not-null="true"
     * @uml.property  name="pkg"
     */
    public Package getPkg() {
        return pkg;
    }
    
    
    /**
     * @param pkg  the pkg to set
     * @uml.property  name="pkg"
     */
    public void setPkg(Package thePackage) {
        this.pkg = thePackage;
    }
    
    
    /**
     * @hibernate.property  not-null="true"
     * @uml.property  name="criterion"
     */
    public Criteria getCriterion() {
        return criterion;
    }
    
    
    /**
     * @param criterion  the criterion to set
     * @uml.property  name="criterion"
     */
    public void setCriterion(Criteria criterion) {
        this.criterion = criterion;
    }
    
    
    /**
     * @hibernate.property
     * @uml.property  name="value"
     */
    public Double getValue() {
        return value;
    }
    
    
    /**
     * @param value  the value to set
     * @uml.property  name="value"
     */
    public void setValue(Double value) {
        this.value = value;
    }
    
    
}//class PackageCriteria
