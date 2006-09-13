package org.pgist.projects;


/**
 * @author  Mike and Guirong
 * @hibernate.class  table="pgist_ag_funding_source" lazy="true"
 */
public class FundingSource {
    
    
    private Long id;
    
    private String name;
    
    private Float totalRevenue;
    
    private Float avgPersonalTax;
    
    
    /**
     * @hibernate.id  generator-class="native"
     * @uml.property  name="id"
     */
    public Long getId() {
        return id;
    }
    
    
    /**
     * @param id  the id to set
     * @uml.property  name="id"
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    
    /**
     * @hibernate.property  not-null="true"
     * @uml.property  name="name"
     */
    public String getName() {
        return name;
    }
    
    
    /**
     * @param name  the name to set
     * @uml.property  name="name"
     */
    public void setName(String name) {
        this.name = name;
    }
    
    
    /**
     * @hibernate.property  not-null="true"
     * @uml.property  name="totalRevenue"
     */
    public Float getTotalRevenue() {
        return totalRevenue;
    }
    
    
    /**
     * @param totalRevenue  the totalRevenue to set
     * @uml.property  name="totalRevenue"
     */
    public void setTotalRevenue(Float totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
    
    
    /**
     * @hibernate.property  not-null="true"
     * @uml.property  name="avgPersonalTax"
     */
    public Float getAvgPersonalTax() {
        return avgPersonalTax;
    }
    
    
    /**
     * @param avgPersonalTax  the avgPersonalTax to set
     * @uml.property  name="avgPersonalTax"
     */
    public void setAvgPersonalTax(Float avgPersonalTax) {
        this.avgPersonalTax = avgPersonalTax;
    }
    
    
}//class FundingSource
