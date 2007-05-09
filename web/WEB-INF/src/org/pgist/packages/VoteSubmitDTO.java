package org.pgist.packages;

import java.util.HashMap;

/**
 * Special DTO used when submitting a vote
 * 
 * @author Matt Paulin
 */
public class VoteSubmitDTO {

	
	private HashMap votes = new HashMap();
	
	private Long packageSuiteId;

	/**
	 * @return the packageSuiteId
	 */
	public Long getPackageSuiteId() {
		return packageSuiteId;
	}

	/**
	 * @param packageSuiteId the packageSuiteId to set
	 */
	public void setPackageSuiteId(Long packageSuiteId) {
		this.packageSuiteId = packageSuiteId;
	}

	/**
	 * @return the votes
	 */
	public HashMap getVotes() {
		return votes;
	}

	/**
	 * @param votes the votes to set
	 */
	public void setVotes(HashMap votes) {
		this.votes = votes;
	}
	
	
}
