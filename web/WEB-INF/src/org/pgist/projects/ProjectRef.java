package org.pgist.projects;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;


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
    
    private Set<ProjectAltRef> altRefs = new TreeSet<ProjectAltRef>();
    
    
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
     * @hibernate.many-to-one column="suite_id" cascade="all" lazy="true"
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
     * @hibernate.many-to-one column="project_id" cascade="all" lazy="true"
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
     * @hibernate.set lazy="false" cascade="all-delete-orphan" sort="org.pgist.projects.ProjectAltRefComparator"
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
			//If it has a null ID then use the ID of the alternative
			if(tempAltRef.getId() != null && altRef.getId() != null) {
				if(tempAltRef.getAlternative().getId().equals(altRef.getAlternative().getId())) {
					foundRef = tempAltRef;
					break;					
				}
			} else {
				if(tempAltRef.getId().equals(altRef.getId())) {
					foundRef = tempAltRef;
					break;
				}
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
    
	/**
	 * Returns true if the specified alternative is inside this project ref
	 * 
	 * @param	alt		The alternative to search for
	 */
	public boolean containsAlternative(ProjectAlternative alt) {
    	for (ProjectAltRef altRef : getAltRefs()) {
			if(altRef.getAlternative().getId().equals(alt.getId())) {
				return true;
			}
    	}
    	return false;
	}


	/**
	 * Returns the project alt ref that has this projectAlt in it
	 * 
	 * @param projectAlt	The project alternative to look for
	 * @return	The project alt ref with the project alternative in it, null if nothing found
	 */
	public ProjectAltRef getProjectAltRef(ProjectAlternative projectAlt) {
    	for (ProjectAltRef altRef : getAltRefs()) {
			if(altRef.getAlternative().getId().equals(projectAlt.getId())) {
				return altRef;
			}
    	}
    	return null;
	}
    
}//class ProjectRef
