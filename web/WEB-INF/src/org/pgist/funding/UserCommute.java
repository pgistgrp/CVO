package org.pgist.funding;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.pgist.users.User;


/**
 * A UserCommute object hold the commute information for one user.
 * 
 * @author Kenny
 * 
 * @hibernate.class table="pgist_funding_user_commute" lazy="true"
 */
public class UserCommute implements Serializable {
    
    
    private Long id;
    
    private FundingSourceSuite fundingSuite;
        
    private float annualConsume;
    
    private float costPerGallon;
    
    private SortedSet<UserFundingSourceToll> tolls = new TreeSet<UserFundingSourceToll>();
    
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
	 * @return the tolls
     * @hibernate.set lazy="true" cascade="all" sort="org.pgist.funding.UserFundingSourceTollComparator"
     * @hibernate.collection-key column="commute_id"
     * @hibernate.collection-one-to-many class="org.pgist.funding.UserFundingSourceToll"
	 */
	public SortedSet<UserFundingSourceToll> getTolls() {
		return tolls;
	}


	/**
	 * @param tolls the tolls to set
	 */
	public void setTolls(SortedSet<UserFundingSourceToll> tolls) {
		this.tolls = tolls;
	}


	/**
     * @return
     * 
     * @hibernate.many-to-one column="suite_id" cascade="all"
     */
    public FundingSourceSuite getFundingSuite() {
        return fundingSuite;
    }


    public void setFundingSuite(FundingSourceSuite fundingSuite) {
        this.fundingSuite = fundingSuite;
    }

    /**
     * @return
     * 
     * @hibernate.property not-null="true"
     */
    public float getAnnualConsume() {
        return annualConsume;
    }


    public void setAnnualConsume(float annualConsume) {
        this.annualConsume = annualConsume;
    }


    /**
     * @return
     * 
     * @hibernate.property not-null="true"
     */
	public float getCostPerGallon() {
		return costPerGallon;
	}


	public void setCostPerGallon(float costPerGallon) {
		this.costPerGallon = costPerGallon;
	}


}//class UserCommute
