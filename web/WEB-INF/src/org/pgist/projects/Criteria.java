package org.pgist.projects;

import java.util.*;

/**
 * @author Mike and Guirong
 * @hibernate.class table="pgist_ag_criteria" lazy="true"
 */
class Criteria {
    private Long id;
    private String name;
    private String na;
    private String high;
    private String medium;
    private String low;
    private Set moes;


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
     * @hibernate.property not-null="true" comment="if a score for this project and criterion is not applicapable"
     */
    public String getNa() {
        return na;
    }

    /**
     * @hibernate.property not-null="true"
     */
    public String getHigh() {
        return high;
    }

    /**
     * @hibernate.property not-null="true"
     */
    public String getMedium() {
        return medium;
    }

    /**
     * @hibernate.property not-null="true"
     */
    public String getLow() {
        return low;
    }

    /**
     *
     * @hibernate.set lazy="false" table="pgist_ag_moe" cascade="none"
     * @hibernate.collection-key column="criterion_id"
     * @hibernate.collection-many-to-many column="moe_id" class="org.pgist.projects.MOE"
     */
    public Set getMoes() {
        return moes;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNa(String na) {
        this.na = na;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public void setMoes(Set moes) {
        this.moes = moes;
    }
}
