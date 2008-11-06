package org.pgist.sarp.bct;

import java.util.Set;

import org.pgist.sarp.GenericComment;


/**
 * 
 * @author kenny
 *
 * @hibernate.joined-subclass name="ConcernComment" table="sarp_concern_comment" lazy="true"
 * @hibernate.joined-subclass-key column="id"
 */
public class ConcernComment extends GenericComment {
    
    
    private Concern concern;
    
    private Object object;

    
    /**
     * @return
     * @hibernate.many-to-one column="concern_id" lazy="true"
     */
    public Concern getConcern() {
        return concern;
    }


    public void setConcern(Concern concern) {
        this.concern = concern;
    }


    /**
     * @return
     * 
     * @hibernate.set lazy="true" table="sarp_comment_tag_link" cascade="all"
     * @hibernate.collection-key column="comment_id"
     * @hibernate.collection-many-to-many column="tag_id" class="org.pgist.tagging.Tag"
     */
    public Set getTags() {
        return tags;
    }


    public void setTags(Set tags) {
        this.tags = tags;
    }


    /*
     * ------------------------------------------------------------------------
     */


    public Object getObject() {
        return object;
    }


    public void setObject(Object object) {
        this.object = object;
    }


}//class ConcernComment
