package org.pgist.sarp.vtt;

import java.util.Set;

import org.pgist.sarp.GenericComment;
import org.pgist.users.User;

/**
 * @author kenny
 *
 * @hibernate.joined-subclass name="VTTSpecialistComment" table="sarp_vtt_specialist_comment" lazy="true"
 * @hibernate.joined-subclass-key column="id"
 */
public class VTTSpecialistComment extends GenericComment {
    
    
    private VTT vtt;
    
    protected User owner;
    
    private VTTSpecialistComment parent;
    
    
    /**
     * @return
     * @hibernate.many-to-one column="vtt_id" lazy="true" cascade="all"
     */
    public VTT getVtt() {
        return vtt;
    }
    public void setVtt(VTT vtt) {
        this.vtt = vtt;
    }
    
    
    /**
     * @return
     * @hibernate.many-to-one column="owner_id" lazy="true" cascade="all"
     */
    public User getOwner() {
        return owner;
    }
    public void setOwner(User owner) {
        this.owner = owner;
    }
    
    
    /**
     * @return
     * 
     * @hibernate.set lazy="true" table="sarp_cht_spec_comment_tag_link" cascade="all"
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
     * @hibernate.many-to-one column="parent_id" lazy="true"
     */
    public VTTSpecialistComment getParent() {
        return parent;
    }
    public void setParent(VTTSpecialistComment parent) {
        this.parent = parent;
    }


} //class VTTSpecialistComment
