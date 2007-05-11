package org.pgist.packages;
/**
 * Represents a vote for one clustered package
 * 
 * @author Matt Paulin
 * @hibernate.class table="pgist_pkg_vote_suite_stats" lazy="true"
 */
public class VoteSuiteStat {

	private Long id;
	private Integer totalVotes;
	private ClusteredPackage clusteredPackage;
	private Integer highVotePercent;
	private Integer mediumVotePercent;
	private Integer lowVotePercent;
	
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
     * @hibernate.many-to-one column="clustered_pkg_id" cascade="none" class="org.pgist.packages.ClusteredPackage"
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
	 * @return the highVotePercent
     * @hibernate.property not-null="true"
	 */
	public Integer getHighVotePercent() {
		return highVotePercent;
	}


	/**
	 * @param highVotePercent the highVotePercent to set
	 */
	public void setHighVotePercent(Integer highNum) {
		this.highVotePercent = highNum;
	}


	/**
	 * @return the lowVotePercent
     * @hibernate.property not-null="true"
	 */
	public Integer getLowVotePercent() {
		return lowVotePercent;
	}


	/**
	 * @param lowVotePercent the lowVotePercent to set
	 */
	public void setLowVotePercent(Integer lowNum) {
		this.lowVotePercent = lowNum;
	}


	/**
	 * @return the mediumVotePercent
     * @hibernate.property not-null="true"
	 */
	public Integer getMediumVotePercent() {
		return mediumVotePercent;
	}


	/**
	 * @param mediumVotePercent the mediumVotePercent to set
	 */
	public void setMediumVotePercent(Integer mediumNum) {
		this.mediumVotePercent = mediumNum;
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
}
