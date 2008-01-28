package org.pgist.sarp.drt;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.pgist.users.User;


/**
 * DRT Comment.
 * 
 * @author kenny
 *
 * @hibernate.class table="sarp_drt_comment" lazy="true"
 */
public class Comment implements Serializable {
    
    
	private static final long serialVersionUID = -2561599748380339322L;
	
	
	private Long id;
	
	private InfoObject target;
    
    private String title;
    
    private String content;
    
    private User author;
    
    private Date createTime;
    
    private Set tags = new HashSet();
    
    private int numAgree;
    
    private int numVote;
    
    private boolean deleted;
    
    private Comment parent;
    
    private boolean emailNotify = false;
    
    
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




}//class Comment
