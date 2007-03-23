package org.pgist.funding;

import java.util.HashSet;
import java.util.Set;

/**
 * FundingSourceRef refers to a FundingSource object.
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_funding_source_ref" lazy="true"
 */
public class FundingSourceRef {
    
    
    private Long id;
    
    private FundingSourceSuite suite;
    
    private FundingSource source;
    
    private Set<FundingSourceAltRef> altRefs = new HashSet<FundingSourceAltRef>();
    
    
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
     * @hibernate.many-to-one column="suite_id" cascade="none" lazy="true"
     */
    public FundingSourceSuite getSuite() {
        return suite;
    }


    public void setSuite(FundingSourceSuite suite) {
        this.suite = suite;
    }


    /**
     * @return
     * 
     * @hibernate.many-to-one column="source_id" cascade="none" lazy="true"
     */
    public FundingSource getSource() {
        return source;
    }


    public void setSource(FundingSource source) {
        this.source = source;
    }


    /**
     * @return
     * 
     * @hibernate.set lazy="false" cascade="all-delete-orphan"
     * @hibernate.collection-key column="sourceref_id"
     * @hibernate.collection-one-to-many class="org.pgist.funding.FundingSourceAltRef"
     */
    public Set<FundingSourceAltRef> getAltRefs() {
        return altRefs;
    }


    public void setAltRefs(Set<FundingSourceAltRef> altRefs) {
        this.altRefs = altRefs;
    }

    /**
     * Removes the project alternative reference from this project reference
     * 
     * @param altRef	The reference to remove
     */
	public void removeAltRef(FundingSourceAltRef altRef) {
		//NOTE We do this manually because hibernate requires a specialized equals so to make sure
		//this is accessed by ID alone we first search for the altRef with this id, then remove that
		//alt ref
		FundingSourceAltRef foundRef = null;
		for(FundingSourceAltRef tempAltRef : getAltRefs()) {
			//If it has a null ID then use the ID of the alternative
			if(tempAltRef.getId() == null || altRef.getId() == null) {
				if(tempAltRef.getAlternative().getId().equals(altRef.getAlternative().getId())) {
					foundRef = tempAltRef;
					break;					
				}
			} else {
				if(tempAltRef.getId().equals(altRef.getId())) {
					foundRef = tempAltRef;
					break;
				}
			}
		}
		if(foundRef != null) {
			this.getAltRefs().remove(foundRef);
		}
	}
	
	/**
	 * Returns the number of alternative reference in this project referece
	 * 
	 * @return	The number of alternative references in this project reference
	 */
	public int getNumAltRefs() {
		return this.altRefs.size();
	}
    
	/**
	 * Returns true if the specified alternative is inside this project ref
	 * 
	 * @param	alt		The alternative to search for
	 */
	public boolean containsAlternative(FundingSourceAlternative alt) {
    	for (FundingSourceAltRef altRef : getAltRefs()) {
			if(altRef.getAlternative().getId().equals(alt.getId())) {
				return true;
			}
    	}
    	return false;
	}
}//class FundingSourceRef
