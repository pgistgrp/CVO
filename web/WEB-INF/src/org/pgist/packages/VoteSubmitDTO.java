package org.pgist.packages;

import java.util.HashMap;
import java.util.Map;

/**
 * Special DTO used when submitting a vote
 * 
 * @author Matt Paulin
 */
public class VoteSubmitDTO {

	
	private Map<Long, Integer> votes = new HashMap<Long, Integer>();
	
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
	public Map<Long, Integer> getVotes() {
		return votes;
	}

	/**
	 * @param votes the votes to set
	 */
	public void setVotes(Map<Long, Integer> votes) {
		this.votes = votes;
	}
	
	
}
