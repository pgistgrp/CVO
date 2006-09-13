package org.pgist.system;

import java.util.Date;

import org.pgist.users.User;


/**
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_feedbacks" lazy="true"
 */
public class Feedback {
    
    
    private Long id;
    
    private User user;
    
    private Date createTime;
    
    private String action;
    
    private String content;
    
    
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
     * @hibernate.many-to-one column="user_id" lazy="true"
     */
    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
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
     * @hibernate.property not-null="true"
     */
    public String getAction() {
        return action;
    }


    public void setAction(String action) {
        this.action = action;
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


}//class Feedback
