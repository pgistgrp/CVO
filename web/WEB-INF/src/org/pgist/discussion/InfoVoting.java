package org.pgist.discussion;

import java.io.Serializable;

import org.pgist.users.User;


/**
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_info_voting" lazy="true"
 */
public class InfoVoting implements Serializable {
    
    
    private Long id;
    
    private InfoStructure structure;
    
    private InfoObject object;
    
    private boolean voting;
    
    private User owner;
    
    
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
     * @hibernate.many-to-one column="structure_id" lazy="true"
     */
    public InfoStructure getStructure() {
        return structure;
    }


    public void setStructure(InfoStructure structure) {
        this.structure = structure;
    }


    /**
     * @return
     * @hibernate.many-to-one column="object_id" lazy="true"
     */
    public InfoObject getObject() {
        return object;
    }


    public void setObject(InfoObject object) {
        this.object = object;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public boolean isVoting() {
        return voting;
    }


    public void setVoting(boolean voting) {
        this.voting = voting;
    }


    /**
     * @return
     * @hibernate.many-to-one column="owner_id" lazy="true"
     */
    public User getOwner() {
        return owner;
    }


    public void setOwner(User owner) {
        this.owner = owner;
    }
    
    
}//class InfoVoting
