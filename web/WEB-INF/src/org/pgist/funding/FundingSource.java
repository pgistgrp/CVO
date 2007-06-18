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
     * meaning: Sales Tax<br>
     * value: 1<br>
     * calculation: annual cost = (tax rate) * (estimated annual consumption)
     */
    public static final int TYPE_SALES_TAX = 1;
    
    /**
     * meaning: Annual Vehicle License Fee<br>
     * value: 2<br>
     * calculation: annual cost = (tax rate) * (number of vehicles)
     */
    public static final int TYPE_LICENSE = 2;
    
    /**
     * meaning: Annual Motor Vehicle Excise Tax<br>
     * value: 3<br>
     * calculation: annual cost = sum( (tax rate) * (vehicle value) )
     */
    public static final int TYPE_MOTOR_TAX = 3;
    
    /**
     * meaning: Gas Tax<br>
     * value: 4<br>
     * calculation: annual cost = sum( (tax rate) / (miles per galon) * (miles driven per year) )
     */
    public static final int TYPE_GAS_TAX = 4;
    
    /**
     * meaning: Sales Tax on Gas<br>
     * value: 5<br>
     * calculation: annual cost = sum( (tax rate) / (miles per galon) * (miles driven per year) )
     */
    public static final int TYPE_SALES_GAS_TAX = 5;
    
    /**
     * meaning: Employer Excise Tax<br>
     * value: 6<br>
     * calculation: No direct cost calculated
     */
    public static final int TYPE_EMPLOYER_EXCISE_TAX = 6;
    
    /**
     * meaning: Commercial Parking Tax<br>
     * value: 7<br>
     * calculation: annual cost = (tax rate) * (parkings per year)
     */
    public static final int TYPE_PARKING_TAX = 7;
    
    /**
     * meaning: 1.Tolls<br>
     * value: 8<br>
     * calculation: annual cost = (tax rate) * (trips per year)
     */
    public static final int TYPE_TOLLS = 8;
    
    
    private Long id;
    
    private String name;
    
    private Set<FundingSourceAlternative> alternatives = new HashSet<FundingSourceAlternative>();
    
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
     * @hibernate.set lazy="true" cascade="all" order-by="id"
     * @hibernate.collection-key column="source_id"
     * @hibernate.collection-one-to-many class="org.pgist.funding.FundingSourceAlternative"
     */
    public Set<FundingSourceAlternative> getAlternatives() {
        return alternatives;
    }


    public void setAlternatives(Set<FundingSourceAlternative> alternatives) {
        this.alternatives = alternatives;
    }


    /**
     * Types of this funding source, accepted values are constants start with "TYPE_".
     * 
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
     * @hibernate.many-to-one column="alternative_id" cascade="all"
     */
    public FundingSourceAlternative getSelected() {
        return selected;
    }


    public void setSelected(FundingSourceAlternative selected) {
        this.selected = selected;
    }


}//class FundingSource
