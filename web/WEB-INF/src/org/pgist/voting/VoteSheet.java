package org.pgist.voting;

import java.util.ArrayList;
import java.util.List;

import org.pgist.users.User;


/**
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_vote_sheet"
 */
public class VoteSheet {
    
    
    private Long id;
    
    private int status = 0;
    
    private List items = new ArrayList();
    
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
     * @hibernate.property not-null="true"
     */
    public int getStatus() {
        return status;
    }


    public void setStatus(int status) {
        this.status = status;
    }


    /**
     * @return
     * 
     * @hibernate.list table="pgist_vote_item" lazy="true" cascade="all"
     * @hibernate.collection-key column="item_id"
     * @hibernate.collection-index column="item_order"
     * @hibernate.collection-one-to-many class="org.pgist.voting.VoteItem"
     * 
     */
    public List getItems() {
        return items;
    }


    public void setItems(List items) {
        this.items = items;
    }


    /**
     * @return
     * 
     * @hibernate.many-to-one column="owner_id" class="org.pgist.users.User" cascade="all"
     */
    public User getOwner() {
        return owner;
    }


    public void setOwner(User owner) {
        this.owner = owner;
    }
    
    
}//class VoteSheet
