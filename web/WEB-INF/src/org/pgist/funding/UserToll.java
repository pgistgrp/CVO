package org.pgist.funding;

import java.io.Serializable;

import org.pgist.users.User;


/**
 * @author Kenny
 * 
 * @hibernate.class table="pgist_user_toll" lazy="true"
 */
public class UserToll implements Serializable {
    
    
    private Long id;
    
    private User user;
    
    private FundingSourceAlternative toll;
    
    private float value;
    
    private UserCommute commute;
    
    
    /**
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
     * @hibernate.many-to-one column="user_id" cascade="none"
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
     * @hibernate.many-to-one column="toll_id" cascade="none"
     */
    public FundingSourceAlternative getToll() {
        return toll;
    }


    public void setToll(FundingSourceAlternative toll) {
        this.toll = toll;
    }


}//class UserToll
