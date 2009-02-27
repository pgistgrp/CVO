package org.pgist.packages;

import org.pgist.packages.knapsack.KSChoices;
import org.pgist.packages.knapsack.KSItem;
import org.pgist.projects.ProjectAltRef;

/**
 * A project that can be used in the knap sack algorithms
 * 
 * @author Matt Paulin
 */
public class ProjectKSItem extends KSItem {
    
    
	private ProjectAltRef projectAltRef;
	
	
	public ProjectKSItem(KSChoices choices, ProjectAltRef projectAltRef, double profit) {
	    super(choices, projectAltRef.getAlternative().getCost(), profit);
	    this.projectAltRef = projectAltRef;
	}
	
	
	public ProjectAltRef getProjectAltRef() {
		return this.projectAltRef;
	}
	
	
}//class ProjectKSItem
