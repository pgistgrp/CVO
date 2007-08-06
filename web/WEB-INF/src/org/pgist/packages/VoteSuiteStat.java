package org.pgist.packages;

import java.util.Comparator;

import org.pgist.criteria.CriteriaRef;

/**
 * Represents a vote for one clustered package
 * 
 * @author Matt Paulin
 * @hibernate.class table="pgist_pkg_vote_suite_stats" lazy="true"
 */
public class VoteSuiteStat implements Comparator {

	private Long id;
	private Integer totalVotes = 0;
	private ClusteredPackage clusteredPackage;
	private Integer highVotes = 0;
	private Integer mediumVotes = 0;
	private Integer lowVotes = 0;
	private PackageVoteSuite packageVoteSuite;
	
	
	
	/**
	 * @return the packageVoteSuite
     * @hibernate.many-to-one column="votesuite_id" cascade="all" class="org.pgist.packages.PackageVoteSuite"
	 */
    public PackageVoteSuite getPackageVoteSuite() {
		return packageVoteSuite;
	}


	public void setPackageVoteSuite(PackageVoteSuite packageVoteSuite) {
		this.packageVoteSuite = packageVoteSuite;
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
	 * @return the clusteredPackage
     * @hibernate.many-to-one column="clustered_pkg_id" cascade="all" class="org.pgist.packages.ClusteredPackage"
	 */
	public ClusteredPackage getClusteredPackage() {
		return clusteredPackage;
	}


	/**
	 * @param clusteredPackage the clusteredPackage to set
	 */
	public void setClusteredPackage(ClusteredPackage clusteredPackage) {
		this.clusteredPackage = clusteredPackage;
	}


	/**
	 * @return the totalVotes
     * @hibernate.property not-null="true"
	 */
	public Integer getTotalVotes() {
		return totalVotes;
	}


	/**
	 * @param totalVotes the totalVotes to set
	 */
	public void setTotalVotes(Integer totalVotes) {
		this.totalVotes = totalVotes;
	}

	/**
	 * @return the highVotePercent
     * @hibernate.property not-null="true"
	 */
	public Integer getHighVotes() {
		return highVotes;
	}


	public void setHighVotes(Integer highVotes) {
		this.highVotes = highVotes;
	}

	/**
	 * @param lowVotePercent the lowVotePercent to set
	 * @hibernate.property not-null="true"
	 */
	public Integer getLowVotes() {
		return lowVotes;
	}


	public void setLowVotes(Integer lowVotes) {
		this.lowVotes = lowVotes;
	}

	/**
	 * @return the mediumVotePercent
     * @hibernate.property not-null="true"
	 */
	public Integer getMediumVotes() {
		return mediumVotes;
	}


	public void setMediumVotes(Integer mediumVotes) {
		this.mediumVotes = mediumVotes;
	}	    
	
	
	
	public int compare(Object obj1, Object obj2) {
		VoteSuiteStat o1 = (VoteSuiteStat) obj1;
    	VoteSuiteStat o2 = (VoteSuiteStat) obj2;
        
    	int o1Pos = o1.getHighVotes() + o1.getMediumVotes();
    	int o2Pos = o2.getHighVotes() + o2.getMediumVotes();
    	
    	int result = 0;
    	
    	if(o1Pos > o2Pos ) {
    		result = 1;
    	} else if(o1Pos == o2Pos){
    		if(o1.getHighVotes() > o2.getHighVotes()) {
				result = 1;
    		} else {
    		result = -1;
    		}
		} else {
			result = -1;
		}		
    	
    	//-1 less, 0 same, 1 greater
        
    	return result*-1; //I want to reverse it
	}
}
