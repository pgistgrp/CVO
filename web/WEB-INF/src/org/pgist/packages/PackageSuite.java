package org.pgist.packages;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.pgist.users.User;


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
    
    private Set<PackageVoteSuite> voteSuites = new HashSet<PackageVoteSuite>();
    
    private VoteSuiteStat prefPkgVoteSuiteStat;
    

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
     * @hibernate.collection-key column="suite_id"
     * @hibernate.collection-one-to-many class="org.pgist.packages.PackageVoteSuite"
     */
    public Set<PackageVoteSuite> getVoteSuites() {
        return voteSuites;
    }


    public void setVoteSuites(Set<PackageVoteSuite> voteSuites) {
        this.voteSuites = voteSuites;
    }

    
    /**
     * @return
     * 
     * @hibernate.many-to-one column="suite_id" cascade="all"
     */
    public VoteSuiteStat getPrefPkgVoteSuiteStat() {
		return prefPkgVoteSuiteStat;
	}


	public void setPrefPkgVoteSuiteStat(VoteSuiteStat prefPkgVoteSuiteStat) {
		this.prefPkgVoteSuiteStat = prefPkgVoteSuiteStat;
	}
    
    
    /**
     * Returns the ID of the clustered package that this users package ended up in
     * 
     * @param user 	The user 
     * @return The ID of the clustered package, null if that doesn't exist
     */
	public Long getUsersClusteredPackage(User user) {
		//Get the Users Package
		Iterator<UserPackage> iUserPkgs;
		UserPackage tempPackage;
System.out.println("MATT Getting the users cluster");		
		Iterator<ClusteredPackage> iClusteredPkgs = this.clusteredPkgs.iterator();
		ClusteredPackage tempCluster = null;
		while(iClusteredPkgs.hasNext()) {
			tempCluster = iClusteredPkgs.next();
System.out.println("In cluster " + tempCluster.getId());			
			iUserPkgs = tempCluster.getUserPkgs().iterator();
			while(iUserPkgs.hasNext()) {
				tempPackage = iUserPkgs.next();
System.out.println("Got a user package of ID " + tempPackage.getId() + " with an author of " + tempPackage.getAuthor().getId() + " Users ID is " + user.getId());
				if(tempPackage.getAuthor().getId() == user.getId()) {
					return tempCluster.getId();
				}
			}
		}
		return null;
	}
    
    
}//class PackageSuite
