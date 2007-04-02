package org.pgist.criteria;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.pgist.cvo.CCT;



/**
 * @author John
 * 
 * @hibernate.class table="pgist_crit" lazy="true"
 */
public class Criteria implements Serializable {
    
    
    private Long id;
    
    private String name;
    
    private String na;
    
    private CriteriaRef critRef;
    
    private Set themes = new HashSet();
    
    private Set objectives = new HashSet();
    
    private Object object;

    
    private boolean deleted = false;
    
    
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
     * @return
     * 
     * @hibernate.many-to-one column="criteriaref_id" cascade="none"
     */
    public CriteriaRef getCritRef() {
        return critRef;
    }
    
    
    public void setCritRef(CriteriaRef critRef) {
        this.critRef = critRef;
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
    
    
    public void setThemes(Set themes) {
        this.themes = themes;
    }
    
    /**
     * @hibernate.set lazy="false" table="pgist_criteria_theme_link" cascade="none"
     * @hibernate.collection-key column="criterion_id"
     * @hibernate.collection-many-to-many column="theme_id" class="org.pgist.cvo.Theme"
     */   
    public Set getThemes() {
        return themes;
    }
    
    
    public void setObjectives(Set objectives) {
        this.objectives = objectives;
    }
    
    /**
     * @hibernate.set lazy="false" table="pgist_criteria_objective_link" cascade="none"
     * @hibernate.collection-key column="criterion_id"
     * @hibernate.collection-many-to-many column="objective_id" class="org.pgist.criteria.Objective"
     */   
    public Set getObjectives() {
        return objectives;
    }
    
    
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    
    /**
     * @hibernate.property not-null="true"
     */
    public boolean getDeleted() {
        return deleted;
    }

    
    /*
     * ------------------------------------------------------------------------
     */
    

    public Object getObject() {
        return object;
    }


    public void setObject(Object object) {
        this.object = object;
    }
    
    
}//class Criteria
