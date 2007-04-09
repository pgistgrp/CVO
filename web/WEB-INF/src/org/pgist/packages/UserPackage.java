package org.pgist.packages;

import java.util.HashSet;
import java.util.Set;

import org.pgist.funding.FundingSourceAltRef;
import org.pgist.funding.FundingSourceRef;
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
    
    private float willingAnnualCost;
    
    private float willingResidentCost;
    
    private boolean brandnew;
    
    private Set<FundingSourceRef> willingSources = new HashSet<FundingSourceRef>();
    
    protected Set<ProjectAltRef> projAltRefs = new HashSet<ProjectAltRef>();
    
    protected Set<FundingSourceAltRef> fundAltRefs = new HashSet<FundingSourceAltRef>();
    
    
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
