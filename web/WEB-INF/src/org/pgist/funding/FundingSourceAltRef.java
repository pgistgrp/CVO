package org.pgist.funding;


/**
 * FundingSourceAltRef refers to a FundingSourceAlternative object.
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_funding_source_alt_ref" lazy="true"
 */
public class FundingSourceAltRef {
    
    
    private Long id;
    
    private FundingSourceAlternative alternative;
    
    private FundingSourceRef sourceRef;
    
    
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
     * @hibernate.many-to-one column="alt_id" cascade="all" lazy="true"
     */
    public FundingSourceAlternative getAlternative() {
        return alternative;
    }


    public void setAlternative(FundingSourceAlternative alternative) {
        this.alternative = alternative;
    }


    /**
     * @return
     * 
     * @hibernate.many-to-one column="sourceref_id" cascade="all" lazy="true"
     */
    public FundingSourceRef getSourceRef() {
        return sourceRef;
    }


    public void setSourceRef(FundingSourceRef sourceRef) {
        this.sourceRef = sourceRef;
    }


}//class FundingSourceAltRef
