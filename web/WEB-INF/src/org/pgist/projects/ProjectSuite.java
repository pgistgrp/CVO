package org.pgist.projects;

import java.util.HashSet;
import java.util.Set;


/**
 * A project suite consists of a set of project references for a specific decision situation.
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_project_suite" lazy="true"
 */
public class ProjectSuite {
    
    
    private Long id;
    
    private Set<ProjectRef> references = new HashSet<ProjectRef>();
    
    
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
     * @hibernate.set inverse="true" lazy="true"
     * @hibernate.collection-key column="suite_id"
     * @hibernate.collection-one-to-many class="org.pgist.projects.ProjectRef"
     */
    public Set<ProjectRef> getReferences() {
        return references;
    }


    public void setReferences(Set<ProjectRef> references) {
        this.references = references;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    /**
     * TODO: Check if the suite contains a reference to the given project alternative.
     * 
     * @param alt a given project alternative
     * @return
     */
    public boolean containsAlts(ProjectAlternative alt) {
        return false;
    }//contains()
    
    
}//class ProjectSuite
