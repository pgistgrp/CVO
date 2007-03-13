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

    /**
     * Removes the project alternative reference from this project reference
     * 
     * @param altRef	The reference to remove
     */
	public void removeAltRef(ProjectAltRef altRef) {
		//NOTE We do this manually because hibernate requires a specialized equals so to make sure
		//this is accessed by ID alone we first search for the altRef with this id, then remove that
		//alt ref
		ProjectAltRef foundRef = null;
		for(ProjectAltRef tempAltRef : getAltRefs()) {
			if(tempAltRef.getId().equals(altRef.getId())) {
				foundRef = tempAltRef;
			}
		}
		if(foundRef != null) {
			this.getAltRefs().remove(foundRef);
		}
	}
	
	/**
	 * Returns the number of alternative reference in this project referece
	 * 
	 * @return	The number of alternative references in this project reference
	 */
	public int getNumAltRefs() {
		return this.altRefs.size();
	}
    
    
}//class ProjectRef
