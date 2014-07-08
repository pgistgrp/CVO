package org.pgist.sarp.drt;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.pgist.sarp.GenericComment;


/**
 * DRT Comment.
 * 
 * @author kenny
 *
 * @hibernate.joined-subclass name="Comment" table="sarp_drt_comment" lazy="true"
 * @hibernate.joined-subclass-key column="id"
 */
public class Comment extends GenericComment implements Serializable {
    
    
	private static final long serialVersionUID = -2561599748380339322L;
	
	
	private InfoObject target;
    
    private Set tags = new HashSet();
    
    private Comment parent;
    
    protected boolean voted;
    
    protected boolean agree;
    
    
    /**
     * @return
     * 
     * @hibernate.many-to-one column="target_id" cascade="all" lazy="true"
     */
	public InfoObject getTarget() {
		return target;
	}


	public void setTarget(InfoObject target) {
		this.target = target;
	}
	
	
    /**
     * @return
     * 
     * @hibernate.set lazy="true" table="sarp_drt_comment_tag_link" cascade="all"
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
    public Comment getParent() {
		return parent;
	}


	public void setParent(Comment parent) {
		this.parent = parent;
	}


	public void setVoted(boolean voted) {
		this.voted = voted;
	}


	public boolean isVoted() {
		return voted;
	}


	public void setAgree(boolean agree) {
		this.agree = agree;
	}


	public boolean isAgree() {
		return agree;
	}


}//class Comment
