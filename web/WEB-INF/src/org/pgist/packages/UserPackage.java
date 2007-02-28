package org.pgist.packages;

import org.pgist.users.User;


/**
 * User defined package.
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_user_packages" lazy="true"
 */
public class UserPackage extends Package {
    
    
    private User author;
    
    private float totalCost;
    
    private float totalFunding;
    
    private float yourCost;
    
    
    /**
     * @hibernate.many-to-one column="user_id" cascade="none" lazy="true"
     */
    public User getAuthor() {
        return author;
    }
    
    
    public void setAuthor(User author) {
        this.author = author;
    }
    
    
    /**
     * @hibernate.property
     */
    public float getTotalCost() {
        return totalCost;
    }


    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }


    /**
     * @hibernate.property
     */
    public float getTotalFunding() {
        return totalFunding;
    }


    public void setTotalFunding(float totalFunding) {
        this.totalFunding = totalFunding;
    }


    /**
     * meaning: the annual cost of the current user based on the choosen funding source alternatives<br>
     * calculation: sum( (annual user cost) )
     * 
     * @hibernate.property
     */
    public float getYourCost() {
        return yourCost;
    }


    public void setYourCost(float yourCost) {
        this.yourCost = yourCost;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    public float getBalance() {
        return totalFunding - totalCost;
    }
    
    
}//class UserPackage
