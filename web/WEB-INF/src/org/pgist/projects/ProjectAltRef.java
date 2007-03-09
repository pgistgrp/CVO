package org.pgist.projects;

import java.util.HashMap;
import java.util.Map;

import org.pgist.criteria.CriteriaRef;


/**
 * A ProjectAltRef refers to a ProjectAlternative.
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_project_alt_ref" lazy="true"
 */
public class ProjectAltRef {
    
    
    private Long id;
    
    private ProjectRef projectRef;
    
    private ProjectAlternative alternative;
    
    private Map<CriteriaRef, Integer> grades = new HashMap<CriteriaRef, Integer>();
    
    
    /**
     * @return
     * 
     * @hibernate.id generator-class="native"
     */
    public Long getId() {
        return id;
    }
    
    
    public void setId(Long id) {
        this.id = id;
    }


    /**
     * @return
     * 
     * @hibernate.many-to-one column="projref_id" cascade="none" lazy="true"
     */
    public ProjectRef getProjectRef() {
        return projectRef;
    }


    public void setProjectRef(ProjectRef projectRef) {
        this.projectRef = projectRef;
    }


    /**
     * @return
     * 
     * @hibernate.many-to-one column="alternative_id" cascade="none" lazy="true"
     */
    public ProjectAlternative getAlternative() {
        return alternative;
    }


    public void setAlternative(ProjectAlternative alternative) {
        this.alternative = alternative;
    }
    
    
    /**
     * The Criteria-Grade map for one project alternative
     * 
     * @return
     * 
     * @hibernate.map table="pgist_projalt_grade_map"
     * @hibernate.collection-key column="pac_id"
     * @hibernate.index-many-to-many column="critref_id" class="org.pgist.criteria.CriteriaRef"
     * @hibernate.collection-element type="integer" column="grade"
     */
    public Map<CriteriaRef, Integer> getGrades() {
        return grades;
    }


    public void setGrades(Map<CriteriaRef, Integer> grades) {
        this.grades = grades;
    }
    
    
}//class ProjectAltRef
