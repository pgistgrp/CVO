package org.pgist.packages;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Used to pass information about the project
 * 
 * @author Matt Paulin
 */
public class ProjectDTO implements Comparable<ProjectDTO> {

	private String name;
	private Long projectId;
	
	private SortedSet<ProjectAlternativeDTO> projectAlternatives = new TreeSet<ProjectAlternativeDTO>();

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the projectAlternatives
	 */
	public SortedSet<ProjectAlternativeDTO> getProjectAlternatives() {
		return projectAlternatives;
	}

	/**
	 * @param projectAlternatives the projectAlternatives to set
	 */
	public void setProjectAlternatives(
			SortedSet<ProjectAlternativeDTO> projectAlternatives) {
		this.projectAlternatives = projectAlternatives;
	}

	/**
	 * @return the projectId
	 */
	public Long getProjectId() {
		return projectId;
	}

	/**
	 * @param projectId the projectId to set
	 */
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(ProjectDTO o) {
		return this.getName().compareTo(o.getName());
	}	
}
