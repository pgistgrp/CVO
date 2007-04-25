package org.pgist.packages;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.pgist.funding.FundingSourceAltRef;
import org.pgist.funding.FundingSourceAlternative;
import org.pgist.projects.ProjectAltRef;
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
    
    private float avgResidentCost;
        
    protected Set<ProjectAltRef> projAltRefs = new HashSet<ProjectAltRef>();
    
    protected Set<FundingSourceAltRef> fundAltRefs = new HashSet<FundingSourceAltRef>();
    
    /**
     * Non persisted values that represent the users cost in regards to a funding source alternative
     */
    protected HashMap<Long, Float> personalCost = new HashMap<Long, Float>();
    
    /**
     * Returns a map of the personal cost for a person in relation to a FundingSourceAlternative.
     * The key is the FundingSourceAlternative ID and the value is a float describing how much 
     * it would cost this user.
     * 
	 * @return the personalCost
	 */
	public HashMap<Long, Float> getPersonalCost() {
		return personalCost;
	}

	/**
	 * @param personalCost the personalCost to set
	 */
	public void setPersonalCost(HashMap<Long, Float> personalCost) {
		this.personalCost = personalCost;
	}
	
	/**
	 * Utility method that retrieves the value and deals with any situation where the 
	 * alternative ID provided doesn't exist
	 * 
	 * @param	fundingSourceAlternativeID	The id of the alternative
	 * @return	The personal cost	
	 */
	public float getPersonalCost(Long fundingSourceAlternativeID) {
		if(this.personalCost.containsKey(fundingSourceAlternativeID)) {
			return this.personalCost.get(fundingSourceAlternativeID).floatValue();
		}
		return 0;
	}
	

	/**
     * Recalculates all of the information about this user package
     */
    public void updateCalculations() {
    	this.totalCost = 0;
    	this.totalFunding = 0;
    	this.avgResidentCost = 0;
    	this.yourCost = 0;
  
    	Iterator<ProjectAltRef> projects = this.projAltRefs.iterator();
    	while(projects.hasNext()) {
    		totalCost = totalCost + (float)projects.next().getAlternative().getCost();
    	}
    	
    	Iterator<FundingSourceAltRef> funding = this.fundAltRefs.iterator();
    	FundingSourceAlternative fAlt;
    	
    	while(funding.hasNext()) {
    		fAlt = funding.next().getAlternative();
    		totalFunding = totalFunding + (float)fAlt.getRevenue();
    		avgResidentCost = avgResidentCost + fAlt.getAvgCost();
    		yourCost = yourCost + this.getPersonalCost(fAlt.getId());
    	}    	    	
    }
    
    
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
     * meaning: the annual cost of the avg user based on the choosen funding source alternatives<br>
     * calculation: sum( (annual user cost) )
     * 
     * @hibernate.property
     */
    public float getAvgResidentCost() {
        return avgResidentCost;
    }


    public void setAvgResidentCost(float yourCost) {
        this.avgResidentCost = yourCost;
    }
    
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    /**
	 * @return the yourCost
     * @hibernate.property
   	 */
	public float getYourCost() {
		return yourCost;
	}


	/**
	 * @param yourCost the yourCost to set
	 */
	public void setYourCost(float yourCost) {
		this.yourCost = yourCost;
	}


	public float getBalance() {
        return totalFunding - totalCost;
    }
    
    /**
     * @return
     * 
     * @hibernate.set lazy="false" table="pgist_user_package_funding_link" cascade="all"
     * @hibernate.collection-key column="pkg_id"
     * @hibernate.collection-many-to-many column="fundalt_id" class="org.pgist.funding.FundingSourceAltRef"
     * 
     */
    public Set<FundingSourceAltRef> getFundAltRefs() {
        return fundAltRefs;
    }
    
    
    public void setFundAltRefs(Set<FundingSourceAltRef> fundAltRefs) {
        this.fundAltRefs = fundAltRefs;
    }
    
    
    /**
     * @return
     * 
     * @hibernate.set lazy="false" table="pgist_user_package_project_link" cascade="none"
     * @hibernate.collection-key column="pkg_id"
     * @hibernate.collection-many-to-many column="project_id" class="org.pgist.projects.ProjectAltRef"
     */
    public Set<ProjectAltRef> getProjAltRefs() {
        return projAltRefs;
    }
    
    
    public void setProjAltRefs(Set<ProjectAltRef> projAltRefs) {
        this.projAltRefs = projAltRefs;
    }        
}//class UserPackage
