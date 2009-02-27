package org.pgist.packages.cluster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.pgist.funding.FundingSource;
import org.pgist.funding.FundingSourceAltRef;
import org.pgist.funding.FundingSourceAlternative;
import org.pgist.funding.FundingSourceRef;
import org.pgist.funding.FundingSourceSuite;
import org.pgist.packages.UserPackage;
import org.pgist.projects.Project;
import org.pgist.projects.ProjectAltRef;
import org.pgist.projects.ProjectAlternative;
import org.pgist.projects.ProjectRef;
import org.pgist.projects.ProjectSuite;

/**
 * Used to create project items
 * 
 * @author paulin
 */
public class ProjectItemFactory {

	/**
	 * Holds all the PackageItemFormers
	 */
	private List<PackageItemFormer> formers = new ArrayList<PackageItemFormer>();
	
	/**
	 * Preps the factory with the funding source suite and the project suite
	 */
	public void prepareFactory(ProjectSuite projSuite, FundingSourceSuite fundSuite) {
		formers.clear();
		
		Iterator<ProjectRef> iProjRef = projSuite.getReferences().iterator();
		ProjectRef tempProjRef;
		Project tempProject;
		Iterator<ProjectAlternative> iProjAlt;
		ProjectAlternative tempProjAlt;
		PackageItemFormer tempFormer;
		while(iProjRef.hasNext()) {
			tempProjRef = iProjRef.next();
			tempProject = tempProjRef.getProject();
			iProjAlt = tempProject.getAlternatives().iterator();
			while(iProjAlt.hasNext()) {
				tempProjAlt = iProjAlt.next();
				tempFormer = new PackageItemFormer();
				tempFormer.setId(tempProjAlt.getId());
				tempFormer.setSelected(false);
				tempFormer.setProj(true);
				formers.add(tempFormer);
			}
		}		
		
		Iterator<FundingSourceRef> iFundRef = fundSuite.getReferences().iterator();
		FundingSourceRef tempFundRef;
		FundingSource tempFundingSource;
		Iterator<FundingSourceAlternative> iFundAlt;
		FundingSourceAlternative tempFundAlt;
		while(iFundRef.hasNext()) {
			tempFundRef = iFundRef.next();
			tempFundingSource = tempFundRef.getSource();
			iFundAlt = tempFundingSource.getAlternatives().iterator();
			while(iFundAlt.hasNext()) {
				tempFundAlt = iFundAlt.next();
				tempFormer = new PackageItemFormer();
				tempFormer.setId(tempFundAlt.getId());
				tempFormer.setSelected(false);
				tempFormer.setProj(false);
				formers.add(tempFormer);
			}
		}
		
		this.sort();
	}
		
	/**
	 * Creates the PackageItem when given a user package
	 * 
	 * @param	userPkg	The user package to turn into a packageItem
	 * @return	A package Item to use in clustering
	 */
	public PackageItem createPackageItem(UserPackage userPackage) {

		this.resetChoices();
		
		PackageItemFormer tempFormer;
		
		Iterator<ProjectAltRef> iProjAltRefs = userPackage.getProjAltRefs().iterator();
		ProjectAlternative tempAlt;
		while(iProjAltRefs.hasNext()) {
			tempAlt = iProjAltRefs.next().getAlternative();
			tempFormer = this.getFormer(tempAlt.getId());
			tempFormer.setSelected(true);
		}
		
		Iterator<FundingSourceAltRef> iFundAltRefs = userPackage.getFundAltRefs().iterator();
		FundingSourceAlternative tempFundAlt;
		while(iFundAltRefs.hasNext()) {
			tempFundAlt = iFundAltRefs.next().getAlternative();
			tempFormer = this.getFormer(tempFundAlt.getId());			
			tempFormer.setSelected(true);			
		}
		
		this.sort();
		
        List<Boolean> choices;
        choices = new ArrayList<Boolean>();
        Iterator<PackageItemFormer> iFormer = this.formers.iterator();
        while(iFormer.hasNext()) {
        	if(iFormer.next().isSelected()) {
        		choices.add(true);
        	} else {
        		choices.add(false);
        	}
        }
        PackageItem packItem = new PackageItem(userPackage.getId(), choices);
System.out.println("Created :::" + packItem);                
		return packItem;
	}
	
	/**
	 * Returns the package item former that has the specified ID
	 */
	public PackageItemFormer getFormer(Long id) {
		Iterator<PackageItemFormer> i = this.formers.iterator();
		PackageItemFormer tempFormer;
		while(i.hasNext()) {
			tempFormer = i.next();
			if(tempFormer.getId().equals(id)) return tempFormer;
		}
		return null;
	}
	
	/**
	 * Resets all of the choices back to false
	 */
	public void resetChoices() {
		Iterator<PackageItemFormer> i = this.formers.iterator();
		while(i.hasNext()) {
			i.next().reset();
		}
	}
	
	/**
	 * Sorts the collection of formers
	 */
	private void sort() {
		Collections.sort(this.formers);
	}
	
	/**
	 * Temporary class used when forming the packages
	 * @author paulin
	 */
	public class PackageItemFormer implements Comparable<PackageItemFormer>{
		/**
		 * Set to true if its a project
		 */
		private boolean proj;
		
		/**
		 * The ID of the project or funding source
		 */
		private Long id;
		
		/**
		 * True if the user selected this
		 */
		private boolean selected;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public boolean isProj() {
			return proj;
		}

		public void setProj(boolean proj) {
			this.proj = proj;
		}

		public boolean isSelected() {
			return selected;
		}

		public void setSelected(boolean selected) {
			this.selected = selected;
		}

		public void reset() {
			this.selected = false;
		}
		
		public int compareTo(PackageItemFormer o) {
			if(o.isProj() && !this.isProj()) {
				return 1;
			}
			if(!o.isProj() && this.isProj()) {
				return -1;
			}
			if(o.isProj() && this.isProj()) {
				return this.getId().compareTo(o.getId());
			}
			if(!o.isProj() && !this.isProj()) {
				return this.getId().compareTo(o.getId());
			}
			return 0;
		}
	}
}
