package org.pgist.packages;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.pgist.users.User;


/**
 * A PackageVoteSuite is an entire set of data in one voting.<br>
 * It consists of a map from ClusteredPackage to PackageUserVote;<br>
 * it consists of a map from ClusteredPackage to voter number;<br>
 * it consists of a map from ClusteredPackage to high number;<br>
 * it consists of a map from ClusteredPackage to medium number;<br>
 * it consists of a map from ClusteredPackage to low number.
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_pkg_vote_suite" lazy="true"
 */
public class PackageVoteSuite {
    
    
    private Long id;
    
    private PackageSuite pkgSuite;
    
    private Map<ClusteredPackage, PackageUserVote> userVotes = new HashMap<ClusteredPackage, PackageUserVote>();

    private SortedSet<VoteSuiteStat> stats = new TreeSet<VoteSuiteStat>(new VoteSuiteStatComparator());
    
    private int numVoters;
    
    private boolean finalVote = false;
    
	

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
     * @hibernate.property not-null="true"
     */
    public boolean isFinalVote() {
		return finalVote;
	}


	public void setFinalVote(boolean finalVote) {
		this.finalVote = finalVote;
	}
	
	
	/**
     * @hibernate.property not-null="false"
     */
    public int getNumVoters() {
		return numVoters;
	}


	public void setNumVoters(int numVoters) {
		this.numVoters = numVoters;
	}
	
	
    /**
     * @return
     * 
     * @hibernate.many-to-one column="suite_id" cascade="all"
     */
    public PackageSuite getPkgSuite() {
        return pkgSuite;
    }
    
    
    public void setPkgSuite(PackageSuite pkgSuite) {
        this.pkgSuite = pkgSuite;
    }


    /**
     * @return
     * 
     * @hibernate.map table="pgist_pkg_vote_suite_vote_map"
     * @hibernate.collection-key column="votesuite_id"
     * @hibernate.index-many-to-many column="pkg_id" class="org.pgist.packages.ClusteredPackage"
     * @hibernate.collection-many-to-many class="org.pgist.packages.PackageUserVote" column="uservote_id"
     */
    public Map<ClusteredPackage, PackageUserVote> getUserVotes() {
        return userVotes;
    }


    public void setUserVotes(Map<ClusteredPackage, PackageUserVote> userVotes) {
        this.userVotes = userVotes;
    }

    
    
    /*
     * ------------------------------------------------------------------------
     */
    
   
    /**
     * @return
     * 
     * @hibernate.set inverse="true" lazy="true" cascade="all" sort="org.pgist.packages.VoteSuiteStatComparator"
     * @hibernate.collection-key column="votesuite_id"
     * @hibernate.collection-one-to-many class="org.pgist.packages.VoteSuiteStat"
     */
    public SortedSet<VoteSuiteStat> getStats() {
		return stats;
	}


	public void setStats(SortedSet<VoteSuiteStat> stats) {
		this.stats = stats;
	}


	/**
     * Returns true if the user has voted on all of the clustered packages
     */
    public boolean userVoted(User user) {
        boolean voted = false;
        
        Iterator<ClusteredPackage> iCPackage = pkgSuite.getClusteredPkgs().iterator();
        ClusteredPackage tempCPackage;
        PackageUserVote puv;
        //Check that the user has voted on each clustered package
        while(iCPackage.hasNext()) {
            tempCPackage = iCPackage.next();
            puv = userVotes.get(tempCPackage);
            if(puv != null) {
                if(!puv.getVotes().containsKey(user)) {
                    return false;               
                }
            } else {
                return false;
            }
        }
        return true;
    }
    
    
    
}//class PackageVoteSuite
