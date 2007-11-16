package org.pgist.projects;

import java.util.HashSet;
import java.util.Set;

/**
 * A ProjectRef refers to a project object.
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_project_ref" lazy="true"
 */
public class ProjectRef {
    
    
    private Long id;
    
    private ProjectSuite suite;
    
    private Project project;
    
    private Set<ProjectAltRef> altRefs = new HashSet<ProjectAltRef>();
    
    
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
     * @hibernate.many-to-one column="suite_id" cascade="none" lazy="true"
     */
    public ProjectSuite getSuite() {
        return suite;
    }


    public void setSuite(ProjectSuite suite) {
        this.suite = suite;
    }


    /**
     * @return
     * 
     * @hibernate.many-to-one column="project_id" cascade="none" lazy="true"
     */
    public Project getProject() {
        return project;
    }


    public void setProject(Project project) {
        this.project = project;
    }


    /**
     * @return
     * 
     * @hibernate.set lazy="false" cascade="all-delete-orphan"
     * @hibernate.collection-key column="projref_id"
     * @hibernate.collection-one-to-many class="org.pgist.projects.ProjectAltRef"
     */
    public Set<ProjectAltRef> getAltRefs() {
        return altRefs;
    }


    public void setAltRefs(Set<ProjectAltRef> altRefs) {
        this.altRefs = altRefs;
    }
    
    
}//class ProjectRef
