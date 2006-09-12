package org.pgist.projects;

/**
 * @author Mike and Guirong
 * @hibernate.class table="pgist_ag_funding" lazy="true"
 */
class FundingSource {
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

    /**
     * @hibernate.property not-null="true"
     */
    public String getName() {
        return name;
    }

    /**
     * @hibernate.property not-null="true"
     */
    public Float getTotalRevenue() {

        return totalRevenue;
    }

    /**
     * @hibernate.property not-null="true"
     */
    public Float getAvgPersonalTax() {
        return avgPersonalTax;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTotalRevenue(Float totalRevenue) {

        this.totalRevenue = totalRevenue;
    }

    public void setAvgPersonalTax(Float avgPersonalTax) {
        this.avgPersonalTax = avgPersonalTax;
    }
}
