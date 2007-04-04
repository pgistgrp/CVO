package org.pgist.funding;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
    
    private User user;
    
    private Set<FundingSourceAltRef> tolls = new HashSet<FundingSourceAltRef>();
    
    private Map<FundingSourceAltRef, Integer> peekHourTrips = new HashMap<FundingSourceAltRef, Integer>();
    
    private Map<FundingSourceAltRef, Integer> offPeekTrips = new HashMap<FundingSourceAltRef, Integer>();
    
    private Map<FundingSourceAltRef, Float> costs = new HashMap<FundingSourceAltRef, Float>();
    
    private float annualConsume;
    
    private float costPerGallon;
    
    
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
     * @hibernate.many-to-one column="suite_id" cascade="none"
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
     * @hibernate.set lazy="true" table="pgist_user_commute_toll_link" cascade="all"
     * @hibernate.collection-key column="commute_id"
     * @hibernate.collection-many-to-many column="altref_id" class="org.pgist.funding.FundingSourceAltRef"
     */
    public Set<FundingSourceAltRef> getTolls() {
        return tolls;
    }


    public void setTolls(Set<FundingSourceAltRef> tolls) {
        this.tolls = tolls;
    }


    /**
     * @return
     * 
     * @hibernate.map table="pgist_funding_user_commute_value"
     * @hibernate.collection-key column="commute_id"
     * @hibernate.index-many-to-many column="altref_id" class="org.pgist.funding.FundingSourceAltRef"
     * @hibernate.collection-element type="integer" column="peekhourtrips"
     */
    public Map<FundingSourceAltRef, Integer> getPeekHourTrips() {
        return peekHourTrips;
    }


    public void setPeekHourTrips(Map<FundingSourceAltRef, Integer> peekHourTrips) {
        this.peekHourTrips = peekHourTrips;
    }


    /**
     * @return
     * 
     * @hibernate.map table="pgist_funding_user_commute_value"
     * @hibernate.collection-key column="commute_id"
     * @hibernate.index-many-to-many column="altref_id" class="org.pgist.funding.FundingSourceAltRef"
     * @hibernate.collection-element type="integer" column="offpeektrips"
     */
    public Map<FundingSourceAltRef, Integer> getOffPeekTrips() {
        return offPeekTrips;
    }


    public void setOffPeekTrips(Map<FundingSourceAltRef, Integer> offPeekTrips) {
        this.offPeekTrips = offPeekTrips;
    }


    /**
     * @return
     * 
     * @hibernate.map table="pgist_funding_user_commute_costs"
     * @hibernate.collection-key column="commute_id"
     * @hibernate.index-many-to-many column="altref_id" class="org.pgist.funding.FundingSourceAltRef"
     * @hibernate.collection-element type="float" column="cost"
     */
    public Map<FundingSourceAltRef, Float> getCosts() {
        return costs;
    }


    public void setCosts(Map<FundingSourceAltRef, Float> costs) {
        this.costs = costs;
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
