package org.pgist.projects;


/**
 * @author Mike and Guirong
 * 
 * @hibernate.class table="pgist_ag_funding_source" lazy="true"
 */
public class FundingSource {
    
    
    private Long id;
    
    private String name;
    
    private Float totalRevenue;
    
    private Float avgPersonalTax;
    
    
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
    public Float getTotalRevenue() {
        return totalRevenue;
    }
    
    
    public void setTotalRevenue(Float totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
    
    
    /**
     * @hibernate.property not-null="true"
     */
    public Float getAvgPersonalTax() {
        return avgPersonalTax;
    }
    
    
    public void setAvgPersonalTax(Float avgPersonalTax) {
        this.avgPersonalTax = avgPersonalTax;
    }
    
    
}//class FundingSource
