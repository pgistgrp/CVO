package org.pgist.packages;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


/**
 * @author Mike and Guirong
 * 
 * @hibernate.class table="pgist_ag_packcluster" lazy="true"
 */
public class PackageCluster implements Serializable {
    
    
    private Long id;
    
    private String info;
    
    private int numPeople;
    
    private Set<Package> packages = new HashSet<Package>();
    
    
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
     * @hibernate.property
     */
    public String getInfo() {
        return info;
    }
    
    
    public void setInfo(String info) {
        this.info = info;
    }
    
    
    /**
     * @hibernate.property
     */
    public int getNumPeople() {
        return numPeople;
    }
    
    
    public void setNumPeople(int numPeople) {
        this.numPeople = numPeople;
    }
    
    
    /**
     * @hibernate.set lazy="true" table="pgist_ag_cluster_pack" cascade="none"
     * @hibernate.collection-key column="cluster_id"
     * @hibernate.collection-many-to-many column="package_id" class="org.pgist.packages.Package"
     */
    public Set<Package> getPackages() {
        return packages;
    }
    
    
    public void setPackages(Set<Package> packages) {
        this.packages = packages;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    public void addPackage(Package p){
        packages.add(p);
    }
    
    
    public void removePackage(Package p){
        packages.remove(p);
    }
    
    
}//class PackageCluster
