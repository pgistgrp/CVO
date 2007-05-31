package org.pgist.packages;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.pgist.funding.FundingSourceAltRef;
import org.pgist.funding.FundingSourceAlternative;
import org.pgist.projects.ProjectAltRef;


/**
 * Clustered packages.<br>
 * 
 * May be created through two ways:
 * <ul>
 *   <li>Automatically created by the system</li>
 *   <li>Manually created by moderator</li>
 * </ul>
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_clustered_packages" lazy="true"
 */
public class ClusteredPackage extends Package {
    
    
    private boolean manual;
    
    private float avgResidentCost;
    
    private float totalCost;
    
    private float totalFunding;
    
    protected Set<ProjectAltRef> projAltRefs = new HashSet<ProjectAltRef>();
    
    protected Set<FundingSourceAltRef> fundAltRefs = new HashSet<FundingSourceAltRef>();    

    /**
     * This is a list of all the user pkgs that were combined into this package
     */
    protected Set<UserPackage> userPkgs = new HashSet<UserPackage>();    


    /**
     * @return
     * 
     * @hibernate.set lazy="true" table="pgist_clustered_user_package" cascade="none" 
     * @hibernate.collection-key column="clustered_pkg_id"
     * @hibernate.collection-many-to-many column="user_pkg_id" class="org.pgist.packages.UserPackage"
     */
    public Set<UserPackage> getUserPkgs() {
        return userPkgs;
    }
    
    
    public void setUserPkgs(Set<UserPackage> userPkgs) {
        this.userPkgs = userPkgs;
    }
    
    
    /**
     * @hibernate.property
	 * @return the avgResidentCost
	 */
	public float getAvgResidentCost() {
		return avgResidentCost;
	}


	/**
	 * @param avgResidentCost the avgResidentCost to set
	 */
	public void setAvgResidentCost(float totalCostForAvgResident) {
		this.avgResidentCost = totalCostForAvgResident;
	}


	/**
     * @hibernate.property
     */
    public boolean isManual() {
        return manual;
    }


    public void setManual(boolean manual) {
        this.manual = manual;
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


    /*
     * ------------------------------------------------------------------------
     */
    
    
    public float getBalance() {
        return totalFunding - totalCost;
    }

    /**
     * @return
     * 
     * @hibernate.set lazy="false" table="pgist_clustered_package_funding_link" cascade="all"
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
     * @hibernate.set lazy="false" table="pgist_clustered_package_project_link" cascade="none"
     * @hibernate.collection-key column="pkg_id"
     * @hibernate.collection-many-to-many column="project_id" class="org.pgist.projects.ProjectAltRef"
     */
    public Set<ProjectAltRef> getProjAltRefs() {
        return projAltRefs;
    }
    
    
    public void setProjAltRefs(Set<ProjectAltRef> projAltRefs) {
        this.projAltRefs = projAltRefs;
    }
    
	/**
     * Recalculates all of the information about this user package
     */
    public void updateCalculations() {
    	System.out.println("MATT: Updating cost for the [" + this.projAltRefs.size() + "] projects and [" + this.fundAltRefs.size() + "] funding sources");
    	this.totalCost = 0;
    	this.totalFunding = 0;
    	this.avgResidentCost = 0;
  
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
    	}    	    	
    }
    
}//class ClusteredPackage
