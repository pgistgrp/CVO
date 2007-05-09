package org.pgist.packages;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
    
    private Map<ClusteredPackage, Integer> voters = new HashMap<ClusteredPackage, Integer>();
    
    private Map<ClusteredPackage, Integer> highs = new HashMap<ClusteredPackage, Integer>();
    
    private Map<ClusteredPackage, Integer> mediums = new HashMap<ClusteredPackage, Integer>();
    
    private Map<ClusteredPackage, Integer> lows = new HashMap<ClusteredPackage, Integer>();
    
    
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
        		if(!puv.getVotes().containsKey(user)) return false;    			
    		}
    	}
    	
    	return true;
    }
    
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
     * @hibernate.many-to-one column="pkgsuite_id" cascade="none"
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


    /**
     * @return
     * 
     * @hibernate.map table="pgist_pkg_vote_suite_stat_map"
     * @hibernate.collection-key column="votesuite_id"
     * @hibernate.index-many-to-many column="pkg_id" class="org.pgist.packages.ClusteredPackage"
     * @hibernate.collection-element type="integer" column="voters_num"
     */
    public Map<ClusteredPackage, Integer> getVoters() {
        return voters;
    }


    public void setVoters(Map<ClusteredPackage, Integer> voters) {
        this.voters = voters;
    }


    /**
     * @return
     * 
     * @hibernate.map table="pgist_pkg_vote_suite_stat_map"
     * @hibernate.collection-key column="votesuite_id"
     * @hibernate.index-many-to-many column="pkg_id" class="org.pgist.packages.ClusteredPackage"
     * @hibernate.collection-element type="integer" column="high_num"
     */
    public Map<ClusteredPackage, Integer> getHighs() {
        return highs;
    }


    public void setHighs(Map<ClusteredPackage, Integer> high) {
        this.highs = high;
    }


    /**
     * @return
     * 
     * @hibernate.map table="pgist_pkg_vote_suite_stat_map"
     * @hibernate.collection-key column="votesuite_id"
     * @hibernate.index-many-to-many column="pkg_id" class="org.pgist.packages.ClusteredPackage"
     * @hibernate.collection-element type="integer" column="medium_num"
     */
    public Map<ClusteredPackage, Integer> getMediums() {
        return mediums;
    }


    public void setMediums(Map<ClusteredPackage, Integer> medium) {
        this.mediums = medium;
    }
    
    
    /**
     * @return
     * 
     * @hibernate.map table="pgist_pkg_vote_suite_stat_map"
     * @hibernate.collection-key column="votesuite_id"
     * @hibernate.index-many-to-many column="pkg_id" class="org.pgist.packages.ClusteredPackage"
     * @hibernate.collection-element type="integer" column="low_num"
     */
    public Map<ClusteredPackage, Integer> getLows() {
        return lows;
    }


    public void setLows(Map<ClusteredPackage, Integer> low) {
        this.lows = low;
    }


}//class PackageVoteSuite
