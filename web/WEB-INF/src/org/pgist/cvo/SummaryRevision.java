package org.pgist.cvo;

import java.util.Date;

import org.pgist.users.User;


/**
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_cvo_summary_revision" lazy="true"
 */
public class SummaryRevision {
    
    
    private Long id;
    
    private String content;
    
    private Date createTime;
    
    private User creator;
    
    private SummaryRevision origin;
    
    
    /**
     * @return
     * 
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
     * @hibernate.property not-null="true"
     */
    public String getContent() {
        return content;
    }


    public void setContent(String content) {
        this.content = content;
    }


    /**
     * @return
     * 
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
     * @hibernate.many-to-one column="creator_id" lazy="true" class="org.pgist.users.User" cascade="all" not-null="true"
     */
    public User getCreator() {
        return creator;
    }


    public void setCreator(User creator) {
        this.creator = creator;
    }


    /**
     * @return
     * 
     * @hibernate.many-to-one column="origin_id" lazy="true" class="org.pgist.cvo.SummaryRevision" cascade="all"
     */
    public SummaryRevision getOrigin() {
        return origin;
    }


    public void setOrigin(SummaryRevision origin) {
        this.origin = origin;
    }
    
    
}//class SummaryRevision
