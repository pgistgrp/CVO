package org.pgist.projects;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Comparator;

import org.pgist.criteria.Criteria;
import org.pgist.criteria.CriteriaRef;


/**
 * A ProjectAltRef refers to a ProjectAlternative.
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_project_alt_ref" lazy="true"
 */
public class ProjectAltRef implements Comparator {
    
    
    private Long id;
    
    private ProjectRef projectRef;
    
    private ProjectAlternative alternative;
    
	private SortedSet<GradedCriteria> gradedCriteria = new TreeSet<GradedCriteria>();
    
    
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
     * 
     * @return	A set of all the criteria associated with this alt ref
     * 
     * @hibernate.set lazy="false" cascade="all-delete-orphan" sort="org.pgist.projects.GradedCriteriaComparator"
     * @hibernate.collection-key column="project_alt_ref_id"
     * @hibernate.collection-one-to-many class="org.pgist.projects.GradedCriteria"
     */
    public SortedSet<GradedCriteria> getGradedCriteria() {
		return gradedCriteria;
	}

	public void setGradedCriteria(SortedSet<GradedCriteria> criteria) {
		this.gradedCriteria = criteria;
	}    
	
	public boolean equals(Object obj) {
		if(obj != null && obj instanceof ProjectAlternative) {
			ProjectAlternative temp = (ProjectAlternative)obj;
			if(this.getId().equals(temp.getId())) return true;
		}
		return false;
	}
	
	public int compare(Object o1, Object o2) {
		
		return ((ProjectAltRef)o1).getAlternative().getName().compareToIgnoreCase(((ProjectAltRef)o2).getAlternative().getName());
	}
	
}//class ProjectAltRef
