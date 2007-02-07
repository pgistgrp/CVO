package org.pgist.packages;

import java.util.HashSet;
import java.util.Set;

import org.pgist.cvo.CCT;
import org.pgist.funding.FundingSourceAlternative;
import org.pgist.projects.ProjectAlternative;
import org.pgist.users.User;


/**
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_user_packages" lazy="true"
 */
public class UserPackage {
    
    
    private Long id;
    
    private User author;
    
    private CCT cct;
    
    private Set<ProjectAlternative> projAlts = new HashSet<ProjectAlternative>();
    
    private Set<FundingSourceAlternative> fundAlts = new HashSet<FundingSourceAlternative>();
    
    private float totalCost;
    
    private float totalFunding;
    
    private float yourCost;
    
    
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
     * @hibernate.many-to-one column="user_id" cascade="none" lazy="true"
     */
    public User getAuthor() {
        return author;
    }
    
    
    public void setAuthor(User author) {
        this.author = author;
    }
    
    
    /**
     * @hibernate.many-to-one column="cct_id" cascade="none" lazy="true"
     */
    public CCT getCct() {
        return cct;
    }
    
    
    public void setCct(CCT cct) {
        this.cct = cct;
    }
    
    
    /**
     * @return
     * 
     * @hibernate.set lazy="true" cascade="all" table="pgist_upkg_fundalt_link" order-by="id"
     * @hibernate.collection-many-to-many column="fund_alt_id" class="org.pgist.funding.FundingSourceAlternative"
     * @hibernate.collection-key column="upkg_id"
     */
    public Set<FundingSourceAlternative> getFundAlts() {
        return fundAlts;
    }
    
    
    public void setFundAlts(Set<FundingSourceAlternative> fundAlts) {
        this.fundAlts = fundAlts;
    }
    
    
    /**
     * @return
     * 
     * @hibernate.set lazy="true" cascade="all" table="pgist_upkg_projalt_link" order-by="id"
     * @hibernate.collection-many-to-many column="fund_alt_id" class="org.pgist.funding.ProjectAlternative"
     * @hibernate.collection-key column="upkg_id"
     */
    public Set<ProjectAlternative> getProjAlts() {
        return projAlts;
    }
    
    
    public void setProjAlts(Set<ProjectAlternative> projAlts) {
        this.projAlts = projAlts;
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
