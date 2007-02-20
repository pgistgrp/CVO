package org.pgist.packages;


/**
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_clustered_packages" lazy="true"
 */
public class ClusteredPackage extends Package {
    
    
    private boolean manual;
    
    private float totalCost;
    
    private float totalFunding;
    
    
    /**
     * @hibernate.property
     */
    public boolean isManual() {
        return manual;
    }


    public void setManual(boolean manual) {
        this.manual = manual;
    }


    /**
     * @hibernate.property
     */
    public float getTotalCost() {
        return totalCost;
    }


    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }


    /**
     * @hibernate.property
     */
    public float getTotalFunding() {
        return totalFunding;
    }


    public void setTotalFunding(float totalFunding) {
        this.totalFunding = totalFunding;
    }


    /*
     * ------------------------------------------------------------------------
     */
    
    
    public float getBalance() {
        return totalFunding - totalCost;
    }
    
    
}//class ClusteredPackage
