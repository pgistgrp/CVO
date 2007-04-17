package org.pgist.packages;

import java.util.HashMap;

/**
 * Special class for encapsulating the information from the user for figuring out 
 * their optimal package
 * 
 * @author Matt Paulin
 */
public class TunerConfig {

	/**
	 * Static value for the user to identify that they must have this option
	 */
	public static final int MUST_HAVE = 2;
	
	/**
	 * Static value for they might want this option
	 */
	public static final int MAYBE = 1;
	
	/**
	 * Static value for the user never wanting this option
	 */
	public static final int NEVER = 0;
	
		
	/**
	 * The project suite involved
	 */
	private Long projSuiteId;
	
	/**
	 * The funding suite involved
	 */
	private Long fundSuiteId; 
	
	/**
	 * The hashmap of all the projectAltRef ID's (keys) and choices (values)
	 */
	private HashMap projectChoices = new HashMap();
	
	/**
	 * The hashmap of all the fundingSourceAltRef ID's (keys) and choices (values)
	 */
	private HashMap fundingChoices = new HashMap();

	
	/**
	 * @return the fundSuiteId
	 */
	public Long getFundSuiteId() {
		return fundSuiteId;
	}

	/**
	 * @param fundSuiteId the fundSuiteId to set
	 */
	public void setFundSuiteId(Long fundSuiteId) {
		this.fundSuiteId = fundSuiteId;
	}

	/**
	 * @return the projSuiteId
	 */
	public Long getProjSuiteId() {
		return projSuiteId;
	}

	/**
	 * @param projSuiteId the projSuiteId to set
	 */
	public void setProjSuiteId(Long projSuiteId) {
		this.projSuiteId = projSuiteId;
	}

	/**
	 * @return the fundingChoices
	 */
	public HashMap getFundingChoices() {
		return fundingChoices;
	}

	/**
	 * @param fundingChoices the fundingChoices to set
	 */
	public void setFundingChoices(HashMap fundingChoices) {
		this.fundingChoices = fundingChoices;
	}

	/**
	 * @return the projectChoices
	 */
	public HashMap getProjectChoices() {
		return projectChoices;
	}

	/**
	 * @param projectChoices the projectChoices to set
	 */
	public void setProjectChoices(HashMap projectChoices) {
		this.projectChoices = projectChoices;
	}	
}
