package org.pgist.packages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.pgist.projects.Project;

/**
 * Used to pass information about the project
 * 
 * @author Matt Paulin
 */
public class ProjectDTO implements Comparable<ProjectDTO> {

	private String name;
	private Long projectId;
	
	private List<ProjectAlternativeDTO> projectAlternatives = new ArrayList<ProjectAlternativeDTO>();

	public ProjectDTO(Project tempProject) {
		this.name = tempProject.getName();
		this.projectId = tempProject.getId();
	}

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
	public List<ProjectAlternativeDTO> getProjectAlternatives() {
		return projectAlternatives;
	}

	/**
	 * @param projectAlternatives the projectAlternatives to set
	 */
	public void setProjectAlternatives(
			List<ProjectAlternativeDTO> projectAlternatives) {
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
	
	/**
	 * Sorts the package
	 */
	public void sort() {
		Collections.sort(this.projectAlternatives);
	}
}
