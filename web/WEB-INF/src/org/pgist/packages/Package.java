package org.pgist.packages;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.pgist.cvo.CCT;
import org.pgist.funding.FundingSourceAlternative;
import org.pgist.projects.ProjectAlternative;


/**
 * 
 * @author Guirong
 * 
 */
public abstract class Package implements Serializable {
    
    
    protected Long id;
    
    private CCT cct;
    
    protected String description;
    
    protected Date createDate;
    
    protected Set<ProjectAlternative> projAlts = new HashSet<ProjectAlternative>();
    
    protected Set<FundingSourceAlternative> fundAlts = new HashSet<FundingSourceAlternative>();
    
    
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
     * @hibernate.property
     */
    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    /**
     * @return
     * @hibernate.property
     */
    public Date getCreateDate() {
        return createDate;
    }
    
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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
     * @hibernate.collection-many-to-many column="proj_alt_id" class="org.pgist.projects.ProjectAlternative"
     * @hibernate.collection-key column="upkg_id"
     */
    public Set<ProjectAlternative> getProjAlts() {
        return projAlts;
    }
    
    
    public void setProjAlts(Set<ProjectAlternative> projAlts) {
        this.projAlts = projAlts;
    }


}//class Package
