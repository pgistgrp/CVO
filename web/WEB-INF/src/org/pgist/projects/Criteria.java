package org.pgist.projects;

import java.util.*;


/**
 * @author Mike and Guirong
 * 
 * @hibernate.class table="pgist_criteria" lazy="true"
 */
public class Criteria {
    
    
    private Long id;
    
    private String name;
    
    private String na;
    
    private String high;
    
    private String medium;
    
    private String low;
    
    private Set<MOE> moes = new HashSet<MOE>();
    
    
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
     * @hibernate.property not-null="true"
     */
    public String getName() {
        return name;
    }
    
    
    public void setName(String name) {
        this.name = name;
    }
    
    
    /**
     * @hibernate.property not-null="true" comment="if a score for this project and criterion is not applicapable"
     */
    public String getNa() {
        return na;
    }
    
    
    public void setNa(String na) {
        this.na = na;
    }
    
    
    /**
     * @hibernate.property not-null="true"
     */
    public String getHigh() {
        return high;
    }
    
    
    public void setHigh(String high) {
        this.high = high;
    }
    
    
    /**
     * @hibernate.property not-null="true"
     */
    public String getMedium() {
        return medium;
    }
    
    
    public void setMedium(String medium) {
        this.medium = medium;
    }
    
    
    /**
     * @hibernate.property not-null="true"
     */
    public String getLow() {
        return low;
    }
    
    
    public void setLow(String low) {
        this.low = low;
    }
    
    
    /**
     * @hibernate.set lazy="false" table="pgist_ag_moe_link" cascade="none"
     * @hibernate.collection-key column="criterion_id"
     * @hibernate.collection-many-to-many column="moe_id" class="org.pgist.projects.MOE"
     */
    public Set<MOE> getMoes() {
        return moes;
    }
    
    
    public void setMoes(Set<MOE> moes) {
        this.moes = moes;
    }
    
    
}//class Criteria
