package org.pgist.users;

import java.io.Serializable;


/**
 * PGIST Assoc class.
 * @author michalis
 * @hibernate.class table="pgist_assoc"
 */
public class Assoc implements Serializable {

    
    private Long id;
    
    private String assocName;
    
    private String assocDescription;
    
    private boolean internal = false;
    
    private boolean deleted = false;
    
    
    /**
     * @return
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
     * @hibernate.property not-null="true"
     */
    public String getName() {
        return assocName;
    }
    
    
    public void setName(String name) {
        this.assocName = name;
    }


    /**
     * @return
     * @hibernate.property
     */
    public String getDescription() {
        return assocDescription;
    }


    public void setDescription(String description) {
        this.assocDescription = description;
    }


    /**
     * @return
     * @hibernate.property
     */
    
    public boolean isInternal() {
        return internal;
    }

    public void setInternal(boolean internal) {
        this.internal = internal;
    }


    /**
     * @return
     * @hibernate.property
     */
    
    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    
    
}
