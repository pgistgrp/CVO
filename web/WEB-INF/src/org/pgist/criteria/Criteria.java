package org.pgist.criteria;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.pgist.cvo.CCT;



/**
 * @author John
 * 
 * @hibernate.class table="pgist_criteria" lazy="true"
 */
public class Criteria implements Serializable {
    
    
    private Long id;
    
    private CCT cct;
    
    private String name;
    
    private String na;
    
    private Set<MOE> moes = new HashSet<MOE>();
    
    private Set themes = new HashSet();
    
    private Set objectives = new HashSet();
   
    
    
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
     * @hibernate.set lazy="false" table="pgist_ag_moe_link" cascade="none"
     * @hibernate.collection-key column="criterion_id"
     * @hibernate.collection-many-to-many column="moe_id" class="org.pgist.criteria.MOE"
     */
    public Set<MOE> getMoes() {
        return moes;
    }
    
    
    public void setMoes(Set<MOE> moes) {
        this.moes = moes;
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

	/**
     * @return
     * 
     * @hibernate.many-to-one column="cct_id" cascade="none" lazy="true"
     */
	public CCT getCct() {
		return cct;
	}


	public void setCct(CCT cct) {
		this.cct = cct;
	}
    
    
}//class Criteria
