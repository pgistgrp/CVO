package org.pgist.system;

import java.io.Serializable;

import org.pgist.users.User;


/**
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_yes_no_voting" lazy="true"
 */
public class YesNoVoting implements Serializable {
    
    
    public static final int TYPE_INFO_STRUCTURE = 0;
    
    public static final int TYPE_INFO_OBJECT = 1;
    
    public static final int TYPE_DISCUSSION_POST = 2;
    
    public static final int TYPE_DISCUSSION_REPLY = 3;
    
    public static final int TYPE_CONCERN = 4;
    
    public static final int TYPE_COMMENT = 5;

	public static final int TYPE_SARP_DRT_COMMENT = 6;

	public static final int TYPE_SARP_DRT_INFOOBJ = 7;
    
    public static final int TYPE_SARP_CST_COMMENT = 8;

    public static final int TYPE_SARP_CHT_COMMENT = 9;

    public static final int TYPE_SARP_VTT_COMMENT = 10;

    public static final int TYPE_SARP_DRT_ANNOUNCEMENT = 11;

    public static final int TYPE_SARP_CHT_PATH = 12;
    
    
    private Long id;
    
    private int targetType;
    
    private Long targetId;
    
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
     * @hibernate.property not-null="true"
     */
    public Long getTargetId() {
        return targetId;
    }


    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public int getTargetType() {
        return targetType;
    }


    public void setTargetType(int targetType) {
        this.targetType = targetType;
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
    
    
}//class PostVoting
