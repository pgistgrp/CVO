package org.pgist.packages;

import org.pgist.packages.knapsack.KSItem;
import org.pgist.projects.ProjectAltRef;

/**
 * A project that can be used in the knap sack algorithms
 * 
 * @author Matt Paulin
 */
public class ProjectKSItem extends KSItem {

	private ProjectAltRef projectAltRef;
	
	/**
	 * @param projectAltRef the projectAltRef to set
	 */
	public void setProjectAltRef(ProjectAltRef projectAltRef) {
		this.projectAltRef = projectAltRef;
	}

	public ProjectAltRef getProjectAltRef() {
		return this.projectAltRef;
	}	
}
