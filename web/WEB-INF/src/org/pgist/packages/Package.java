package org.pgist.packages;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.pgist.funding.FundingSourceAltRef;
import org.pgist.projects.ProjectAltRef;


/**
 * The base class for packages: UserPackage and ClusteredPackage.<br>
 * A package contains a set of project alternative references and a set of funding source alternative references.
 * 
 * @author Guirong
 * 
 */
public abstract class Package implements Serializable {
    
    
    protected Long id;
    
    protected PackageSuite suite;
    
    protected String description;
    
    protected Date createDate;
    
    protected Set<ProjectAltRef> projAltRefs = new HashSet<ProjectAltRef>();
    
    protected Set<FundingSourceAltRef> fundAltRefs = new HashSet<FundingSourceAltRef>();
    
    
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
     * @hibernate.many-to-one column="suite_id" cascade="none" lazy="true"
     */
    public PackageSuite getSuite() {
        return suite;
    }


    public void setSuite(PackageSuite suite) {
        this.suite = suite;
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
     * @hibernate.set lazy="true" cascade="all" order-by="id"
     * @hibernate.collection-key column="pkg_id"
     * @hibernate.collection-one-to-many class="org.pgist.funding.FundingSourceAltRef"
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
     * @hibernate.set lazy="true" cascade="all" table="pgist_pkg_projaltref_link" order-by="id"
     * @hibernate.collection-key column="pkg_id"
     * @hibernate.collection-one-to-many class="org.pgist.projects.ProjectAltRef"
     */
    public Set<ProjectAltRef> getProjAltRefs() {
        return projAltRefs;
    }
    
    
    public void setProjAltRefs(Set<ProjectAltRef> projAltRefs) {
        this.projAltRefs = projAltRefs;
    }


}//class Package
