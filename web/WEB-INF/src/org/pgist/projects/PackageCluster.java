package org.pgist.projects;

import java.util.HashSet;
import java.util.Set;


/**
 * @author  Mike and Guirong
 * @hibernate.class  table="pgist_ag_packcluster" lazy="true"
 */
public class PackageCluster {
    
    
    private Long id;
    
    private String info;
    
    private int numPeople;
    
    private Set<Package> packages = new HashSet<Package>();
    
    
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
     * @hibernate.property
     * @uml.property  name="info"
     */
    public String getInfo() {
        return info;
    }
    
    
    /**
     * @param info  the info to set
     * @uml.property  name="info"
     */
    public void setInfo(String info) {
        this.info = info;
    }
    
    
    /**
     * @hibernate.property
     * @uml.property  name="numPeople"
     */
    public int getNumPeople() {
        return numPeople;
    }
    
    
    /**
     * @param numPeople  the numPeople to set
     * @uml.property  name="numPeople"
     */
    public void setNumPeople(int numPeople) {
        this.numPeople = numPeople;
    }
    
    
    /**
     * @hibernate.set  lazy="true" table="pgist_ag_cluster_pack" cascade="none"
     * @hibernate.collection-key  column="cluster_id"
     * @hibernate.collection-many-to-many  column="package_id" class="org.pgist.projects.Package"
     * @uml.property  name="packages"
     */
    public Set<Package> getPackages() {
        return packages;
    }
    
    
    /**
     * @param packages  the packages to set
     * @uml.property  name="packages"
     */
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
