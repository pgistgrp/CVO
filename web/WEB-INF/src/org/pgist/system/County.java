package org.pgist.system;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * <span style="color:red;">POJO</span>: PGIST Criteria Class<br>
 * <span style="color:red;">TABLE</span>: pgist_crit
 * 
 * <p>County class contains information about each county the system
 * 
 * @author John
 * 
 * @hibernate.class table="pgist_county" lazy="true"
 */
public class County implements Serializable {

	 /**
     * <span style="color:blue;">(Column.)</span>
     * id of the county. Unique id number used to identify each county.
     */
	private Long id;
	
	 /**
     * <span style="color:blue;">(Column.)</span>
     * Name of the county. Example: King County
     */
	private String name;
	
	 /**
     * <span style="color:blue;">(Column.)</span>
     * quota limit. The maximum number of users that can qualify to get paid (quota) in this county
     */
	private int quotaLimit;
	
	 /**
     * <span style="color:blue;">(Column.)</span>
     * zipcodes. Set of zipcodes associated with this county
     */
	private Set<String> zipCodes = new HashSet<String>();
	
	 /**
     * <span style="color:blue;">(Column.)</span>
     * temporary quota number. Used to temporarily hold the number of users currently signed up for this county's quota.
     */
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
