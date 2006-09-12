package org.pgist.projects;

import java.util.*;

/**
 * @author Mike and Guirong
 * @hibernate.class table="pgist_ag_packcluster" lazy="true"
 */
class PackageCluster {
    private Long id;
    private String info;
    private int numPeople;
    private Set packages = new HashSet();

    /**
     * @hibernate.id generator-class="native"
     */
    public Long getId() {
        return id;
    }

    /**
     * @hibernate.property
     */
    public String getInfo() {
        return info;
    }

    /**
     * @hibernate.property
     */
    public int getNumPeople() {
        return numPeople;
    }

    /**
     * @hibernate.set lazy="true" table="pgist_ag_cluster_pack" cascade="none"
     * @hibernate.collection-key column="cluster_id"
     * @hibernate.collection-many-to-many column="package_id" class="org.pgist.projects.Package"
     */
    public Set getPackages() {

        return packages;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setNumPeople(int numPeople) {
        this.numPeople = numPeople;
    }

    public void setPackages(Set packages) {

        this.packages = packages;
    }

    public void addPackage(Package p){
        this.packages.add(p);
    }

    public void removePackage(Package p){
        this.packages.remove(p);
    }
}
