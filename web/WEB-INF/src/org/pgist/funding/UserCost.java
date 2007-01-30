package org.pgist.funding;

import java.io.Serializable;

import org.pgist.users.User;


/**
 * @author Kenny
 * 
 * @hibernate.class table="pgist_user_cost" lazy="true"
 */
public class UserCost implements Serializable {
    
    
    private Long id;
    
    private User user;
    
    private FundingSourceAlternative alternative;
    
    private float cost;
    
    private UserCommute commute;
    
    private FundingSourceAlternative toll;
    
    
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
     * @hibernate.many-to-one column="funding_alt_id" cascade="none"
     */
    public FundingSourceAlternative getAlternative() {
        return alternative;
    }


    public void setAlternative(FundingSourceAlternative alternative) {
        this.alternative = alternative;
    }


    /**
     * @hibernate.property not-null="true"
     * @return
     */
    public float getCost() {
        return cost;
    }


    public void setCost(float cost) {
        this.cost = cost;
    }


    /**
     * @return
     * 
     * @hibernate.many-to-one column="commute_id" cascade="none"
     */
    public UserCommute getCommute() {
        return commute;
    }


    public void setCommute(UserCommute commute) {
        this.commute = commute;
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


}//class UserCost
