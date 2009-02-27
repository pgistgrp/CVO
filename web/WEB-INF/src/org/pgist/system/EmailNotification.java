package org.pgist.system;

import java.io.Serializable;

import org.pgist.users.User;

/**
 * @author kenny
 *
 * @hibernate.class table="pgist_email_notify" lazy="true"
 */
public class EmailNotification implements Serializable {
    
    
    private Long id;
    
    private User user;
    
    private String link;
    
    private boolean flag;

    
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
     * @hibernate.property not-null="true" length="256"
     */
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    
    /**
     * @return
     * 
     * @hibernate.property not-null="true"
     */
    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
    
    
}
