package org.pgist.cvo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.pgist.users.User;


/**
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_cvo_concern_comments" lazy="true"
 */
public class Comment {
    
    
    private Long id;
    
    private String title;
    
    private String content;
    
    private User owner;
    
    private Date createTime;
    
    private Concern concern;
    
    private Set tags = new HashSet();
    
    private int numAgree;
    
    private int numVote;
    
    private boolean deleted;
    
    private Object object;

    
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
     * @hibernate.property
     */
    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    /**
     * @return
     * @hibernate.property type="text"
     */
    public String getContent() {
        return content;
    }


    public void setContent(String content) {
        this.content = content;
    }


    /**
     * @return
     * @hibernate.many-to-one column="owner_id" lazy="true" class="org.pgist.users.User" cascade="all"
     */
    public User getOwner() {
        return owner;
    }


    public void setOwner(User owner) {
        this.owner = owner;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public Date getCreateTime() {
        return createTime;
    }


    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    
    /**
     * @return
     * @hibernate.many-to-one column="concern_id" lazy="true" class="org.pgist.cvo.Concern" cascade="all"
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
     * @hibernate.set lazy="true" table="pgist_cvo_comment_tag_link" cascade="all"
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
    public int getNumVote() {
        return numVote;
    }


    public void setNumVote(int numVote) {
        this.numVote = numVote;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public boolean isDeleted() {
        return deleted;
    }


    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
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


}//class Comment
