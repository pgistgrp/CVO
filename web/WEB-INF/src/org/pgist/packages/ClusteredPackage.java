package org.pgist.packages;

import java.util.HashSet;
import java.util.Set;

import org.pgist.funding.FundingSourceAltRef;
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
    
    private float totalCostForAvgResident;
    
    private float totalCost;
    
    private float totalFunding;
    
    protected Set<ProjectAltRef> projAltRefs = new HashSet<ProjectAltRef>();
    
    protected Set<FundingSourceAltRef> fundAltRefs = new HashSet<FundingSourceAltRef>();

    
    
    /**
     * @hibernate.property
	 * @return the totalCostForAvgResident
	 */
	public float getTotalCostForAvgResident() {
		return totalCostForAvgResident;
	}


	/**
	 * @param totalCostForAvgResident the totalCostForAvgResident to set
	 */
	public void setTotalCostForAvgResident(float totalCostForAvgResident) {
		this.totalCostForAvgResident = totalCostForAvgResident;
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
    
}//class ClusteredPackage
