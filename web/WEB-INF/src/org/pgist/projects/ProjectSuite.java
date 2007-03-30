package org.pgist.projects;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;


/**
 * A project suite consists of a set of project references for a specific decision situation.
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_project_suite" lazy="true"
 */
public class ProjectSuite {
    
    
    private Long id;
    
    private SortedSet<ProjectRef> references = new TreeSet<ProjectRef>();
    
    
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
     * @hibernate.set inverse="true" lazy="true" sort="org.pgist.projects.ProjectRefComparator"
     * @hibernate.collection-key column="suite_id"
     * @hibernate.collection-one-to-many class="org.pgist.projects.ProjectRef"
     */
    public Set<ProjectRef> getReferences() {
        return references;
    }


    public void setReferences(SortedSet<ProjectRef> references) {
        this.references = references;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    /**
     * Check if the suite contains a reference to the given project alternative.
     * 
     * @param alt a given project alternative
     * @return
     */
    public boolean containsAlts(ProjectAlternative alt) {
    	return containsAlts(alt.getId());
    }//contains()
    
    
    /**
     * Check if the suite contains a reference to the given project alternative based
     * on the ID of the alternative
     * 
     * @param alt a given project alternative
     * @return	True if the ProjectAlternative is reference by a projectAltRef in a ProjectRef in the ProjectSuite
     */
    public boolean containsAlts(long projectAltId) {
    	
    	for (ProjectRef ref : getReferences()) {
        	for (ProjectAltRef altRef : ref.getAltRefs()) {
    			if(altRef.getAlternative().getId().equals(projectAltId)) {
    				return true;
    			}
        	}    		
    	}
    	return false;
    	
    }//contains()        
    
    
    /**
     * Returns the project reference that is used with the specified project
     * 
     * @param	project		The project to look for
     * @return	The project reference in this suite that references the provided project.  Or null if 
     * 			none was found
     */
    public ProjectRef getProjectReference(Project project) {
    	if(project == null) return null;
    	for (ProjectRef ref : getReferences()) {    		
    		if(ref.getProject().getId().equals(project.getId())) {
    			return ref;
    		}
    	}    	
    	return null;
    } //getProjectReference
    
    /**
     * Returns the project reference that has a reference to the alternative provided
     * 
     * @param	project		The project to look for
     * @return	The project reference that contains the reference.  Or null if 
     * 			none was found
     */
    public ProjectRef getProjectReference(ProjectAlternative projectAlt) {
    	if(projectAlt == null) return null;
    	for (ProjectRef ref : getReferences()) {
        	for (ProjectAltRef tempAltRef : ref.getAltRefs()) {        		
    			if(tempAltRef.getAlternative().getId().equals(projectAlt.getId())) {
    				return ref;
    			}
        	}
    	}    	
    	return null;
    } //getProjectReference        
}//class ProjectSuite
