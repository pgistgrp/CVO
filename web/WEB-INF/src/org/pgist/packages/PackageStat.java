package org.pgist.packages;

import org.pgist.cvo.CCT;


/**
 * 
 * @author kenny
 * @hibernate.class table="pgist_package_stat" lazy="true"
 */
public class PackageStat {
    
    
    private Long id;
    
    private CCT cct;
    
    private float avgCost;
    
    
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
     * @return
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
     * @hibernate.many-to-one column="cct_id" cascade="none"
     */
    public CCT getCct() {
        return cct;
    }


    public void setCct(CCT cct) {
        this.cct = cct;
    }
    
    
}//class PackageStat
