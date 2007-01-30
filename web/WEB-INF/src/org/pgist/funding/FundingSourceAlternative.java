package org.pgist.funding;

import java.io.Serializable;


/**
 * @author Mike and Guirong, kenny
 * 
 * @hibernate.class table="pgist_funding_source_alter" lazy="true"
 */
public class FundingSourceAlternative implements Serializable {
    
    
    private Long id;
    
    private String name;
    
    private Float revenue;
    
    private Float taxRate;
    
    private FundingSource source;
    
    private float avgCost;
    
    private boolean toll;
    
    
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
    public Float getRevenue() {
        return revenue;
    }


    public void setRevenue(Float revenue) {
        this.revenue = revenue;
    }


    /**
     * @hibernate.property not-null="true"
     */
    public Float getTaxRate() {
        return taxRate;
    }


    public void setTaxRate(Float taxRate) {
        this.taxRate = taxRate;
    }


    /**
     * @return
     * 
     * @hibernate.many-to-one column="source_id" cascade="none"
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
     * @hibernate.property
     */
    public float getAvgCost() {
        return avgCost;
    }


    public void setAvgCost(float avgCost) {
        this.avgCost = avgCost;
    }


    /**
     * @return
     * 
     * @hibernate.property
     */
    public boolean isToll() {
        return toll;
    }


    public void setToll(boolean toll) {
        this.toll = toll;
    }
    
    
}//class FundingSourceAlternative
