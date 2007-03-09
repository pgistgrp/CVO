package org.pgist.packages;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;


/**
 * A package suite consists of a set of user packages and a set of clustered packages.
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_pkg_suite" lazy="true"
 */
public class PackageSuite {
    
    
    private Long id;
    
    private Set<UserPackage> userPkgs = new HashSet<UserPackage>();
    
    private Set<ClusteredPackage> clusteredPkgs = new HashSet<ClusteredPackage>();
    
    private SortedSet<PackageVoteSuite> voteSuites = new TreeSet<PackageVoteSuite>();
    
    
    /**
     * @return
     * 
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
     * 
     * @hibernate.set lazy="true" cascade="all" order-by="id"
     * @hibernate.collection-key column="suite_id"
     * @hibernate.collection-one-to-many class="org.pgist.packages.UserPackage"
     */
    public Set<UserPackage> getUserPkgs() {
        return userPkgs;
    }


    public void setUserPkgs(Set<UserPackage> userPkgs) {
        this.userPkgs = userPkgs;
    }


    /**
     * @return
     * 
     * @hibernate.set lazy="true" cascade="all" order-by="id"
     * @hibernate.collection-key column="suite_id"
     * @hibernate.collection-one-to-many class="org.pgist.packages.ClusteredPackage"
     */
    public Set<ClusteredPackage> getClusteredPkgs() {
        return clusteredPkgs;
    }


    public void setClusteredPkgs(Set<ClusteredPackage> clusteredPkgs) {
        this.clusteredPkgs = clusteredPkgs;
    }


    /**
     * @return
     * 
     * @hibernate.set lazy="true" cascade="all" order-by="id"
     * @hibernate.collection-key column="pkgsuite_id"
     * @hibernate.collection-one-to-many class="org.pgist.packages.PackageVoteSuite"
     */
    public SortedSet<PackageVoteSuite> getVoteSuites() {
        return voteSuites;
    }


    public void setVoteSuites(SortedSet<PackageVoteSuite> voteSuites) {
        this.voteSuites = voteSuites;
    }
    
    
}//class PackageSuite
