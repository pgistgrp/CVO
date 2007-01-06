package org.pgist.projects;

import java.io.Serializable;

import org.pgist.criteria.Criteria;
import org.pgist.cvo.CCT;


/**
 * @author Mike and Guirong
 * 
 * @hibernate.class table="pgist_ag_alternative_crit" lazy="true"
 */
public class ProjectAlternativeCriteria implements Serializable {
    
    
    private Long id;
    
    private CCT cct;
    
    private ProjectAlternative alternative;
    
    private Criteria criterion;
    
    private int grade;
    
    
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
     * @hibernate.many-to-one column="cct_id" cascade="none" lazy="true"
     */
    public CCT getCct() {
        return cct;
    }


    public void setCct(CCT cct) {
        this.cct = cct;
    }


    /**
     * @hibernate.many-to-one column="alternative_id" cascade="none" lazy="true"
     */
    public ProjectAlternative getAlternative() {
        return alternative;
    }


    public void setAlternative(ProjectAlternative alternative) {
        this.alternative = alternative;
    }


    /**
     * @hibernate.many-to-one column="criterion_id" cascade="none" lazy="true"
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
    public int getGrade() {
        return grade;
    }
    
    
    public void setGrade(int grade) {
        this.grade = grade;
    }
    
    
}//class ProjectAlternativeCriteria
