package org.pgist.sarp.bct;

import java.io.Serializable;
import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;

import org.pgist.users.User;


/**
 * 
 * @author kenny
 *
 * @hibernate.class table="sarp_concerns" lazy="true"
 */
public class Concern implements Serializable {
    
    
    private Long id;
    
    private String content = "";
    
    private User author;
    
    private Date createTime;
    
    private boolean deleted = false;
    
    private BCT bct;
    
    private SortedSet tags = new TreeSet(new TagReferenceComparator());
    
    private int sortOrder = 0;
    
    private int numAgree = 0;
    
    private int numVote = 0;
    
    private int views = 0;
    
    private int replies = 0;
    
    private Date replyTime;
    
    private boolean emailNotify = false;
    
    private Object object = null;
    
    
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
     * @hibernate.many-to-one column="author_id" lazy="true" cascade="all"
     */
    public User getAuthor() {
        return author;
    }


    public void setAuthor(User author) {
        this.author = author;
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
     * @hibernate.property not-null="true"
     */
    public boolean isDeleted() {
        return deleted;
    }


    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }


    /**
     * @return
     * @hibernate.many-to-one column="bct_id" lazy="true" class="org.pgist.sarp.bct.BCT" cascade="all"
     */
    public BCT getBct() {
        return bct;
    }


    public void setBct(BCT bct) {
        this.bct = bct;
    }


    /**
     * @return
     * 
     * @hibernate.set lazy="true" table="sarp_concern_tag_link" cascade="all" sort="org.pgist.sarp.bct.TagReferenceComparator"
     * @hibernate.collection-key column="concern_id"
     * @hibernate.collection-many-to-many column="tagref_id" class="org.pgist.sarp.bct.TagReference"
     */
    public SortedSet getTags() {
        return tags;
    }


    public void setTags(SortedSet tags) {
        this.tags = tags;
    }
    
    
    /**
     * @return
     * @hibernate.property not-null="true" column="sort_order"
     */
    public int getSortOrder() {
        return sortOrder;
    }


    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
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
    public int getReplies() {
        return replies;
    }


    public void setReplies(int replies) {
        this.replies = replies;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public int getViews() {
        return views;
    }


    public void setViews(int views) {
        this.views = views;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public Date getReplyTime() {
        return replyTime;
    }


    public void setReplyTime(Date replyTime) {
        this.replyTime = replyTime;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public boolean isEmailNotify() {
        return emailNotify;
    }


    public void setEmailNotify(boolean emailNotify) {
        this.emailNotify = emailNotify;
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


}//class Concern
