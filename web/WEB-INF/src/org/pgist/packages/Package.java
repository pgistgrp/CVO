package org.pgist.packages;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
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
     * @hibernate.many-to-one column="suite_id" cascade="all" lazy="true"
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
