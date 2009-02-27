package org.pgist.funding;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.pgist.users.User;


/**
 * A funding source suite consists of a set of funding source references.
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_funding_suite" lazy="true"
 */
public class FundingSourceSuite {
    
    
    private Long id;
    
    private Set<FundingSourceRef> references = new HashSet<FundingSourceRef>();
    
    private Map<User, UserCommute> userCommutes = new HashMap<User, UserCommute>();
    
    
    /**
     * @return
     * 
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
     * @hibernate.set lazy="true" cascade="all-delete-orphan"
     * @hibernate.collection-key column="suite_id"
     * @hibernate.collection-one-to-many class="org.pgist.funding.FundingSourceRef"
     */
    public Set<FundingSourceRef> getReferences() {
        return references;
    }


    public void setReferences(Set<FundingSourceRef> references) {
        this.references = references;
    }
    
    
    /**
     * @return
     * 
     * @hibernate.map table="pgist_funding_user_commute_map" cascade="all"
     * @hibernate.collection-key column="fundsuite_id"
     * @hibernate.index-many-to-many column="user_id" class="org.pgist.users.User"
     * @hibernate.collection-many-to-many class="org.pgist.funding.UserCommute" column="usercommute_id"
     */
    public Map<User, UserCommute> getUserCommutes() {
        return userCommutes;
    }


    public void setUserCommutes(Map<User, UserCommute> userCommutes) {
        this.userCommutes = userCommutes;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    /**
     * Check if the suite contains a reference to the given fundingSource alternative.
     * 
     * @param alt a given fundingSource alternative
     * @return
     */
    public boolean containsAlts(FundingSourceAlternative alt) {
    	return containsAlts(alt.getId());
    }//contains()
    
    
    /**
     * Check if the suite contains a reference to the given fundingSource alternative based
     * on the ID of the alternative
     * 
     * @param alt a given fundingSource alternative
     * @return	True if the FundingSourceAlternative is reference by a fundingSourceAltRef in a FundingSourceRef in the FundingSourceSuite
     */
    public boolean containsAlts(long fundingSourceAltId) {
    	
    	for (FundingSourceRef ref : getReferences()) {
        	for (FundingSourceAltRef altRef : ref.getAltRefs()) {
    			if(altRef.getAlternative().getId().equals(fundingSourceAltId)) {
    				return true;
    			}
        	}    		
    	}
    	return false;
    	
    }//contains()        
    
    
    /**
     * Returns the fundingSource reference that is used with the specified fundingSource
     * 
     * @param	fundingSource		The fundingSource to look for
     * @return	The fundingSource reference in this suite that references the provided fundingSource.  Or null if 
     * 			none was found
     */
    public FundingSourceRef getFundingSourceReference(FundingSource fundingSource) {
    	if(fundingSource == null) return null;
    	for (FundingSourceRef ref : getReferences()) {    		
    		if(ref.getSource().getId().equals(fundingSource.getId())) {
    			return ref;
    		}
    	}    	
    	return null;
    } //getFundingSourceReference
    
    
    /**
     * Returns the fundingSource reference that has a reference to the alternative provided
     * 
     * @param	fundingSource		The fundingSource to look for
     * @return	The fundingSource reference that contains the reference.  Or null if 
     * 			none was found
     */
    public FundingSourceRef getFundingSourceReference(FundingSourceAltRef altRef) {
    	if(altRef == null) return null;
    	for (FundingSourceRef ref : getReferences()) {
        	for (FundingSourceAltRef tempAltRef : ref.getAltRefs()) {
    			if(tempAltRef.getAlternative().getId().equals(altRef.getAlternative().getId())) {
    				return ref;
    			}
        	}
    	}    	
    	return null;
    } //getFundingSourceReference
    
    
    /**
     * Returns the project reference that has a reference to the alternative provided
     * 
     * @param	project		The project to look for
     * @return	The project reference that contains the reference.  Or null if 
     * 			none was found
     */
    public FundingSourceRef getFundingSourceReference(FundingSourceAlternative fundingAlt) {
    	if(fundingAlt == null) return null;
    	for (FundingSourceRef ref : getReferences()) {
        	for (FundingSourceAltRef tempAltRef : ref.getAltRefs()) {        		
    			if(tempAltRef.getAlternative().getId().equals(fundingAlt.getId())) {
    				return ref;
    			}
        	}
    	}
    	return null;
    } //getFundingSourceReference
    
    
}//class FundingSourceSuite
