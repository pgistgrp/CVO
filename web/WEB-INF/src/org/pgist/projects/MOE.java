package org.pgist.projects;

/**
 * @author Mike and Guirong
 * @hibernate.class table="pgist_ag_moe" lazy="true"
 */
class MOE {
    private Long id;
    private String name;
    private String units;

    /**
     * @hibernate.id generator-class="native"
     */
    public Long getId() {
        return id;
    }

    /**
     * @hibernate.property not-null="true"
     */
    public String getName() {
        return name;
    }

    /**
     * @hibernate.property not-null="true"
     */
    public String getUnits() {
        return units;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUnits(String units) {
        this.units = units;
    }
}
