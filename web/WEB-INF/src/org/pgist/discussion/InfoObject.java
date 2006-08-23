package org.pgist.discussion;


/**
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_info_object" lazy="true"
 */
public class InfoObject {
    
    
    private Long id;
    
    private Object object;
    
    private int numDiscussion;
    
    private int numAgree;
    
    private int numVote;
    
    private DiscussionPost lastPost;
    
    
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
     * @return object
     * 
     * @hibernate.any id-type="long" cascade="all" meta-type="string"
     * @hibernate.any-column name="class_name"
     * @hibernate.any-column name="class_id"
     */
    public Object getObject() {
        return object;
    }
    
    
    public void setObject(Object object) {
        this.object = object;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public int getNumAgree() {
        return numAgree;
    }


    public void setNumAgree(int numAgree) {
        this.numAgree = numAgree;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public int getNumDiscussion() {
        return numDiscussion;
    }


    public void setNumDiscussion(int numDiscussion) {
        this.numDiscussion = numDiscussion;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public int getNumVote() {
        return numVote;
    }


    public void setNumVote(int numVote) {
        this.numVote = numVote;
    }


    /**
     * @return
     * 
     * @hibernate.many-to-one column="last_post"
     */
    public DiscussionPost getLastPost() {
        return lastPost;
    }


    public void setLastPost(DiscussionPost lastPost) {
        this.lastPost = lastPost;
    }
    
    
}//class InfoObject
