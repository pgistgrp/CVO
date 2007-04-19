package org.pgist.system;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.pgist.criteria.CriteriaRef;

/**
 * @author John
 * 
 * @hibernate.class table="pgist_county" lazy="true"
 */
public class County implements Serializable {

	private Long id;
	
	private String name;
	
	private int quotaLimit;
	
	private Set<String> zipCodes = new HashSet<String>();
	
	private int tempQuotaNumber;
	
	
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
     * @hibernate.property not-null="true"
     */
    public String getName() {
        return name;
    }
    
    
    public void setName(String name) {
        this.name = name;
    }
 
    
    /**
     * @hibernate.property not-null="true"
     */
    public int getQuotaLimit() {
        return quotaLimit;
    }
    
    
    public void setQuotaLimit(int quotaLimit) {
        this.quotaLimit = quotaLimit;
    }
    
    
    /**
     * @hibernate.property not-null="false"
     */
    public int getTempQuotaNumber() {
        return tempQuotaNumber;
    }
    
    
    public void setTempQuotaNumber(int tempQuotaNumber) {
        this.tempQuotaNumber = tempQuotaNumber;
    }
    
    
    /**
     * @return
     * 
     * @hibernate.set table="pgist_county_zipcodes_link"
     * @hibernate.collection-key column="id"
     * @hibernate.collection-element type="string" column="zipCodes"
     */
    public Set<String> getZipCodes() {
    	
        return zipCodes;
    }


    public void setZipCodes(Set<String> zipCodes ) {
        this.zipCodes = zipCodes;
    }
    
} //class County
