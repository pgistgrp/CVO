package org.pgist.sarp.vtt;

import java.io.Serializable;
import java.util.Set;

import org.pgist.sarp.GenericComment;
import org.pgist.users.User;


/**
 * CHT Comment.
 * 
 * @author kenny
 *
 * @hibernate.joined-subclass name="VTTComment" table="sarp_vtt_comment" lazy="true"
 * @hibernate.joined-subclass-key column="id"
 */
public class VTTComment extends GenericComment implements Serializable {
    
    
	private static final long serialVersionUID = -2561599748380339322L;
	
	
	private VTT vtt;
	
    private VTTComment parent;
    
    private User owner;
    
    
    /**
     * @return
     * 
     * @hibernate.many-to-one column="vtt_id" lazy="true"
     */
    public VTT getVtt() {
        return vtt;
    }


    public void setVtt(VTT vtt) {
        this.vtt = vtt;
    }


    /**
     * @return
     * 
     * @hibernate.set lazy="true" table="sarp_cht_comment_tag_link" cascade="all"
     * @hibernate.collection-key column="comment_id"
     * @hibernate.collection-many-to-many column="tag_id" class="org.pgist.tagging.Tag"
     */
    public Set getTags() {
        return tags;
    }


    public void setTags(Set tags) {
        this.tags = tags;
    }


    /**
     * @return
     * 
     * @hibernate.many-to-one column="parent_id" lazy="true"
     */
    public VTTComment getParent() {
		return parent;
	}


	public void setParent(VTTComment parent) {
		this.parent = parent;
	}


    /**
     * @return
     *
     * @hibernate.many-to-one column="owner_id" lazy="true"
     */
    public User getOwner() {
        return owner;
    }


    public void setOwner(User owner) {
        this.owner = owner;
    }


}//class VTTComment
