package org.pgist.discussion;

import java.util.Set;


/**
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_discussion_reply" lazy="true"
 */
public class DiscussionReply extends GenericPost {
    
    
    protected DiscussionPost parent;
    
    
    /**
     * @return
     * @hibernate.many-to-one column="parent_id" lazy="true"
     */
    public DiscussionPost getParent() {
        return parent;
    }


    public void setParent(DiscussionPost parent) {
        this.parent = parent;
    }


    /**
     * @return
     * 
     * @hibernate.set lazy="true" table="pgist_dreply_tag_link" order-by="tag_id"
     * @hibernate.collection-key column="dreply_id"
     * @hibernate.collection-many-to-many column="tag_id" class="org.pgist.tagging.Tag"
     */
    public Set getTags() {
        return tags;
    }


}//class DiscussionReply
