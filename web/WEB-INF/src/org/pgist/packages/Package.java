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
}//class Package
