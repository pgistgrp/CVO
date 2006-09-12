package org.pgist.projects;

/**
 * @author Mike and Guirong
 * @hibernate.class table="pgist_ag_pack_crit" lazy="true"
 */
class PackageCriteria {
    private Long id;
    private Package thePackage;
    private Criteria criterion;
    private Double value;

    /**
     * @hibernate.id generator-class="native"
     */
    public Long getId() {
        return id;
    }

    /**
     * @hibernate.property not-null="true"
     */
    public Package getThePackage() {
        return thePackage;
    }

    /**
     * @hibernate.property not-null="true"
     */
    public Criteria getCriterion() {
        return criterion;
    }

    /**
     * @hibernate.property
     */
    public Double getValue() {
        return value;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setThePackage(Package thePackage) {
        this.thePackage = thePackage;
    }

    public void setCriterion(Criteria criterion) {
        this.criterion = criterion;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
