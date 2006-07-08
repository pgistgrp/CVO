package org.pgist.discussion;


/**
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_dicussion" lazy="true"
 */
public class Discussion {

    
    public static enum TargetType { term, tag, concern };
    
    
    protected Long id;
    
    protected Long targetId;
    
    protected String targetType;
    
    protected DiscussionPost root;
    
    
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
    public String getTargetType() {
        return targetType;
    }


    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }


    /**
     * @return
     * @hibernate.many-to-one column="root_id" lazy="true" class="org.pgist.discussion.DiscussionPost"
     */
    public DiscussionPost getRoot() {
        return root;
    }


    public void setRoot(DiscussionPost root) {
        this.root = root;
    }
    
    
}//class Discussion
