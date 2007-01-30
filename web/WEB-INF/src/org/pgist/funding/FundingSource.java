package org.pgist.funding;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


/**
 * @author Mike and Guirong
 * 
 * @hibernate.class table="pgist_funding_source" lazy="true"
 */
public class FundingSource implements Serializable {
    
    
    /**
     * annual cost = (tax rate) / (miles per galon) * (miles driven per year)
     */
    private static final int TYPE_GAS_TAX = 1;
    
    /**
     * annual cost = (tax rate) * (estimated annual consumption)
     */
    private static final int TYPE_SALES_TAX = 2;
    
    /**
     * For bridge tolls:<br>
     * annual cost = (toll rate) * (trips per year)
     */
    private static final int TYPE_BRIDGE_TOLL = 3;
    
    /**
     * For road tolls:<br>
     * annual cost = (toll rate) * (trips per year)
     */
    private static final int TYPE_ROAD_TOLL = 4;
    
    
    private Long id;
    
    private String name;
    
    private Set alternatives = new HashSet();
    
    /**
     * type of the funding source
     */
    private int type;
    
    private FundingSourceAlternative selected = null;
    
    
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
     * @return
     * 
     * @hibernate.set lazy="true" cascade="all"
     * @hibernate.collection-key column="source_id"
     * @hibernate.collection-one-to-many class="org.pgist.funding.FundingSourceAlternative"
     */
    public Set getAlternatives() {
        return alternatives;
    }


    public void setAlternatives(Set alternatives) {
        this.alternatives = alternatives;
    }


    /**
     * @hibernate.property not-null="true"
     */
    public int getType() {
        return type;
    }


    public void setType(int type) {
        this.type = type;
    }
    
    
    /**
     * @return
     * 
     * @hibernate.many-to-one column="alternative_id" cascade="none"
     */
    public FundingSourceAlternative getSelected() {
        return selected;
    }


    public void setSelected(FundingSourceAlternative selected) {
        this.selected = selected;
    }


}//class FundingSource
