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


}//class FundingSourceRef
