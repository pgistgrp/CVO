package org.pgist.projects;

import java.util.*;


/**
 * @author  Mike and Guirong
 * 
 * @hibernate.class  table="pgist_criteria" lazy="true"
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
     * @uml.property  name="name"
     */
    public String getName() {
        return name;
    }
    
    
    /**
     * @param name  the name to set
     * @uml.property  name="name"
     */
    public void setName(String name) {
        this.name = name;
    }
    
    
    /**
     * @hibernate.property  not-null="true" comment="if a score for this project and criterion is not applicapable"
     * @uml.property  name="na"
     */
    public String getNa() {
        return na;
    }
    
    
    /**
     * @param na  the na to set
     * @uml.property  name="na"
     */
    public void setNa(String na) {
        this.na = na;
    }
    
    
    /**
     * @hibernate.property  not-null="true"
     * @uml.property  name="high"
     */
    public String getHigh() {
        return high;
    }
    
    
    /**
     * @param high  the high to set
     * @uml.property  name="high"
     */
    public void setHigh(String high) {
        this.high = high;
    }
    
    
    /**
     * @hibernate.property  not-null="true"
     * @uml.property  name="medium"
     */
    public String getMedium() {
        return medium;
    }
    
    
    /**
     * @param medium  the medium to set
     * @uml.property  name="medium"
     */
    public void setMedium(String medium) {
        this.medium = medium;
    }
    
    
    /**
     * @hibernate.property  not-null="true"
     * @uml.property  name="low"
     */
    public String getLow() {
        return low;
    }
    
    
    /**
     * @param low  the low to set
     * @uml.property  name="low"
     */
    public void setLow(String low) {
        this.low = low;
    }
    
    
    /**
     * @hibernate.set  lazy="false" table="pgist_ag_moe" cascade="none"
     * @hibernate.collection-key  column="criterion_id"
     * @hibernate.collection-many-to-many  column="moe_id" class="org.pgist.projects.MOE"
     * @uml.property  name="moes"
     */
    public Set<MOE> getMoes() {
        return moes;
    }
    
    
    /**
     * @param moes  the moes to set
     * @uml.property  name="moes"
     */
    public void setMoes(Set<MOE> moes) {
        this.moes = moes;
    }
    
    
}//class Criteria
