package org.pgist.packages;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.pgist.criteria.Criteria;
import org.pgist.criteria.CriteriaDAO;
import org.pgist.criteria.CriteriaRef;
import org.pgist.criteria.CriteriaSuite;
import org.pgist.criteria.CriteriaUserWeight;
import org.pgist.cvo.CCT;
import org.pgist.cvo.CCTDAO;
import org.pgist.discussion.DiscussionDAO;
import org.pgist.discussion.InfoObject;
import org.pgist.discussion.InfoStructure;
import org.pgist.funding.FundingDAO;
import org.pgist.funding.FundingService;
import org.pgist.funding.FundingSource;
import org.pgist.funding.FundingSourceAltRef;
import org.pgist.funding.FundingSourceAlternative;
import org.pgist.funding.FundingSourceRef;
import org.pgist.funding.FundingSourceSuite;
import org.pgist.funding.TaxCalcUtils;
import org.pgist.funding.UserCommute;
import org.pgist.packages.knapsack.KSChoices;
import org.pgist.packages.knapsack.KSEngine;
import org.pgist.packages.knapsack.KSItem;
import org.pgist.projects.GradedCriteria;
import org.pgist.projects.Project;
import org.pgist.projects.ProjectAltRef;
import org.pgist.projects.ProjectAlternative;
import org.pgist.projects.ProjectDAO;
import org.pgist.projects.ProjectRef;
import org.pgist.projects.ProjectSuite;
import org.pgist.users.User;
import org.pgist.users.UserInfo;
import org.pgist.users.Vehicle;


/**
 * Implements all of the package service functionality
 * 
 * @author kenny
 *
 */
public class PackageServiceImpl implements PackageService {

	FundingService fundingService;
	
	/**
	 * @return the fundingService
	 */
	public FundingService getFundingService() {
		return fundingService;
	}


	/**
	 * @param fundingService the fundingService to set
	 */
	public void setFundingService(FundingService fundingService) {
		this.fundingService = fundingService;
	}
	
    CriteriaDAO criteriaDAO;
    
    
    public void setCriteriaDAO(CriteriaDAO criteriaDAO) {
        this.criteriaDAO = criteriaDAO;
    }		
	
    FundingDAO fundingDAO;
    
    
    public void setFundingDAO(FundingDAO fundingDAO) {
        this.fundingDAO = fundingDAO;
    }	
    
    
    PackageDAO packageDAO;
    
    
    public void setPackageDAO(PackageDAO packageDAO) {
        this.packageDAO = packageDAO;
    }

    ProjectDAO projectDAO;
    
    
    public void setProjectDAO(ProjectDAO projectDAO) {
        this.projectDAO = projectDAO;
    }
    
    
    private CCTDAO cctDAO;
    
    private DiscussionDAO discussionDAO;
    
    
    public void setCctDAO(CCTDAO cctDAO) {
        this.cctDAO = cctDAO;
    }


    public void setDiscussionDAO(DiscussionDAO discussionDAO) {
        this.discussionDAO = discussionDAO;
    }


    /*
     * ------------------------------------------------------------------------
     */
    
	public PackageSuite getPackageSuite(Long pkgId) throws Exception {
		return packageDAO.getPackageSuite(pkgId);
	}

	/* (non-Javadoc)
	 * @see org.pgist.packages.PackageService#createClusteredPackage(java.lang.Long, java.lang.String)
	 */
	public ClusteredPackage createClusteredPackage(Long suiteId, String description) throws Exception {
		ClusteredPackage newPack = new ClusteredPackage();
		newPack.setManual(true);
		newPack.setDescription(description);
		this.packageDAO.save(newPack);
		
		PackageSuite packSuite = this.packageDAO.getPackageSuite(suiteId);
		packSuite.getClusteredPkgs().add(newPack);
		this.packageDAO.save(packSuite);
		return newPack;
	}


	/* (non-Javadoc)
	 * @see org.pgist.packages.PackageService#deleteClusteredPackage(java.lang.Long, java.lang.Long)
	 */
	public void deleteClusteredPackage(Long suiteId, Long pkgId) throws Exception {
		Package pkg = this.packageDAO.getClusteredPackage(pkgId);
		if(pkg != null) {
			PackageSuite suite = this.getPackageSuite(suiteId);
			Iterator i = suite.getClusteredPkgs().iterator();
			ClusteredPackage temp;
			ClusteredPackage found = null;
			while(i.hasNext()) {
				temp = (ClusteredPackage)i.next();
				if(temp.getId().equals(pkg.getId())) {
					found = temp;
					break;
				}
			}
			if(found != null) {
				suite.getClusteredPkgs().remove(found);
				this.packageDAO.save(suite);
			}
		}		
	}


	/* (non-Javadoc)
	 * @see org.pgist.packages.PackageService#getClusteredPackage(java.lang.Long)
	 */
	public Package getPackage(Long pkgId) throws Exception {
		return this.packageDAO.getPackage(pkgId);
	}


	/**
	 * Adds the provided alternative project to the package
	 */
	public Package addProjectAlternative(Long usrPkgId, Long altId, boolean userPkg) throws Exception {
		//System.out.println("MATT##################: Adding project alternative [" + altId + "] to user package [" + usrPkgId + "]");

		if(userPkg) {
			//Get the user package
			UserPackage uPack = this.packageDAO.getUserPackage(usrPkgId);
			
			//get the alternative
			ProjectAltRef altRef = this.projectDAO.getProjectAlternativeReference(altId); 
			//Add the reference
			uPack.getProjAltRefs().add(altRef);
			
			uPack.updateCalculations();
			this.packageDAO.save(uPack);
			return uPack;			
		} else {
			//Get the clustered package
			ClusteredPackage cPack = this.packageDAO.getClusteredPackage(usrPkgId);
			
			//get the alternative
			ProjectAltRef altRef = this.projectDAO.getProjectAlternativeReference(altId); 
			//Add the reference
			cPack.getProjAltRefs().add(altRef);
			
			cPack.updateCalculations();
			this.packageDAO.save(cPack);
			return cPack;						
		}
	}

	public Package deleteProjectAlternative(Long pkgId, Long altId, boolean userPkg) throws Exception {
		//System.out.println("MATT##################: Removing project [" + altId + "] from user package [" + usrPkgId + "]");

		if(userPkg) {
			//Get the user package
			UserPackage uPack = this.packageDAO.getUserPackage(pkgId);
					
			//Loop through to find the alternative with the particular ID, you do this instead
			//of using the collections routines because you would have to maintain the equals
			//method on the ProjectAltRef object, this gives you less chances for bugs as things change
			Iterator<ProjectAltRef> alts = uPack.getProjAltRefs().iterator();
			ProjectAltRef tempRef;
			
			while(alts.hasNext()) {
				tempRef = alts.next();
				if(tempRef.getId().equals(altId)) {
					uPack.getProjAltRefs().remove(tempRef);
					uPack.updateCalculations();
					this.packageDAO.save(uPack);
					return uPack;
				}
			}
			
			return uPack;			
		} else {
			
			//Get the Clustered package
			ClusteredPackage cPack = this.packageDAO.getClusteredPackage(pkgId);
					
			//Loop through to find the alternative with the particular ID, you do this instead
			//of using the collections routines because you would have to maintain the equals
			//method on the ProjectAltRef object, this gives you less chances for bugs as things change
			Iterator<ProjectAltRef> alts = cPack.getProjAltRefs().iterator();
			ProjectAltRef tempRef;
			
			while(alts.hasNext()) {
				tempRef = alts.next();
				if(tempRef.getId().equals(altId)) {
					cPack.getProjAltRefs().remove(tempRef);
					cPack.updateCalculations();
					this.packageDAO.save(cPack);
					return cPack;
				}
			}
			
			return cPack;			
		}
	}

	
	/**
	 * Adds the provided funding source to the package
	 */
	public Package addFundingAlternative(Long pkgId, Long funAltRefId, boolean userPkg) throws Exception {
		//System.out.println("MATT##################: Adding funding source alternative [" + funAltRefId + "] to user package [" + usrPkgId + "]");
		if(userPkg) {
			//Get the user package
			UserPackage uPack = this.packageDAO.getUserPackage(pkgId);
			
			//get the alternative
			FundingSourceAltRef altRef = this.fundingDAO.getFundingSourceAlternativeReference(funAltRefId);

			//Add the reference
			uPack.getFundAltRefs().add(altRef);
			
			this.fundingDAO.save(altRef);
			uPack.updateCalculations();
			this.packageDAO.save(uPack);

			return uPack;			
		} else {
			//Get the user package
			ClusteredPackage cPack = this.packageDAO.getClusteredPackage(pkgId);
			
			//get the alternative
			FundingSourceAltRef altRef = this.fundingDAO.getFundingSourceAlternativeReference(funAltRefId);

			//Add the reference
			cPack.getFundAltRefs().add(altRef);
			
			this.fundingDAO.save(altRef);
			cPack.updateCalculations();
			this.packageDAO.save(cPack);

			return cPack;			
		}
	}

	

	public Package deleteFundingAlternative(Long pkgId, Long funAltRefId, boolean userPkg) throws Exception {
		//System.out.println("MATT##################: Removing funding alternative [" + funAltRefId + "] from user package [" + usrPkgId + "]");
		if(userPkg) {
			//Get the user package
			UserPackage uPack = this.packageDAO.getUserPackage(pkgId);
					
			//Loop through to find the alternative with the particular ID, you do this instead
			//of using the collections routines because you would have to maintain the equals
			//method on the FundingSourceAltRef object, this gives you less chances for bugs as things change
			Iterator<FundingSourceAltRef> alts = uPack.getFundAltRefs().iterator();
			FundingSourceAltRef tempRef;
			
			while(alts.hasNext()) {
				tempRef = alts.next();
				if(tempRef.getId().equals(funAltRefId)) {
					uPack.getFundAltRefs().remove(tempRef);
					uPack.updateCalculations();
					this.packageDAO.save(uPack);
					return uPack;
				}
			}
			
			return uPack;			
		} else {
			//Get the clustered package
			ClusteredPackage cPack = this.packageDAO.getClusteredPackage(pkgId);
					
			//Loop through to find the alternative with the particular ID, you do this instead
			//of using the collections routines because you would have to maintain the equals
			//method on the FundingSourceAltRef object, this gives you less chances for bugs as things change
			Iterator<FundingSourceAltRef> alts = cPack.getFundAltRefs().iterator();
			FundingSourceAltRef tempRef;
			
			while(alts.hasNext()) {
				tempRef = alts.next();
				if(tempRef.getId().equals(funAltRefId)) {
					cPack.getFundAltRefs().remove(tempRef);
					cPack.updateCalculations();
					this.packageDAO.save(cPack);
					return cPack;
				}
			}
			
			return cPack;			
		}
	}
	
	/**
	 * Returns the user package with this specific id
	 * 
	 * @param	pkgId	The ID of the package you are interested in
	 * @return	The user package
	 */
	public UserPackage getUserPackage(Long pkgId) throws Exception {
		return this.packageDAO.getUserPackage(pkgId);		
	}
	
	/**
	 * Returns the clustered package with this specific id
	 * 
	 * @param	pkgId	The ID of the package you are interested in
	 * @return	The user package
	 */
	public ClusteredPackage getClusteredPackage(Long pkgId) throws Exception {
		return this.packageDAO.getClusteredPackage(pkgId);		
	}	
	
	
	
	
	/* (non-Javadoc)
	 * @see org.pgist.packages.PackageService#formPackageFundingDTOs(org.pgist.packages.ClusteredPackage)
	 */
	public List<FundingSourceDTO> createPackageFundingDTOs(ClusteredPackage cPackage, User user, long fundSuiteId) throws Exception {
		this.calcUserValues(cPackage, user, fundSuiteId);
		List<FundingSourceDTO> result = new ArrayList<FundingSourceDTO>();

		//Only show the funding sources that are in the package, but you need to find the projects in the suite that contain them
		
		//First put all of the funding sources in a hash with the key being the alternativeID, we will use this to lookup the correct
		//source
		FundingSourceSuite fSuite = this.fundingDAO.getFundingSuite(fundSuiteId);
		HashMap<Long, FundingSource> sources = new HashMap<Long, FundingSource>();
		Iterator<FundingSourceRef> iSourcesRefs = fSuite.getReferences().iterator();
		FundingSource tempSource;
		Iterator<FundingSourceAlternative> iSourceAlts;
		
		while(iSourcesRefs.hasNext()) {
			tempSource = iSourcesRefs.next().getSource();
			iSourceAlts = tempSource.getAlternatives().iterator();
			while(iSourceAlts.hasNext()) {
				sources.put(iSourceAlts.next().getId(), tempSource);
			}
		}
				
		//Now loop through the alternatives in the package and create the dto
		Iterator<FundingSourceAltRef> iSourceAltRefs = cPackage.fundAltRefs.iterator();
		FundingSourceAltRef tempAltRef;
		FundingSourceAlternative tempAlt;
		while(iSourceAltRefs.hasNext()) {
			tempAltRef = iSourceAltRefs.next();
			tempAlt = tempAltRef.getAlternative();
			
			//Now pull the source back out of the hash 
			tempSource = sources.get(tempAlt.getId());
			
			if(tempSource == null) {
				System.out.println("ERROR: A alternative with no parent was found in the clustered package");
			} else {
				//Create a new DTO
				FundingSourceDTO fsDTO = new FundingSourceDTO(tempSource);

				//See if it has already been added to the result
				if(result.contains(fsDTO)) {
					fsDTO = result.get(result.indexOf(fsDTO));
				} else {
					result.add(fsDTO);
				}
				
				//Add the new alternative
				fsDTO.getFundingSourceAlternatives().add(new FundingSourceAlternativeDTO(tempAlt, cPackage.getPersonalCost(tempAlt.getId())));
				//Sort it
				fsDTO.sort();
			}
		}
		Collections.sort(result);
		
		return result;
	}


	
	/* (non-Javadoc)
	 * @see org.pgist.packages.PackageService#getPackageVoteSuite(java.lang.String)
	 */
	public PackageVoteSuite getPackageVoteSuite(Long voteSuiteId) throws Exception {
		return this.packageDAO.getVoteSuite(voteSuiteId);
	}


	/* (non-Javadoc)
	 * @see org.pgist.packages.PackageService#createVotingPackage(java.lang.Long)
	 */
	public Long createVotingPackage(Long pkgSuiteId) throws Exception {
		PackageVoteSuite vSuite = new PackageVoteSuite();
		PackageSuite pkgSuite = this.packageDAO.getPackageSuite(pkgSuiteId);
		vSuite.setPkgSuite(pkgSuite);
		pkgSuite.getVoteSuites().add(vSuite);
		this.packageDAO.save(vSuite);
		this.packageDAO.save(pkgSuite);
		System.out.println("MATT: 2" + pkgSuite.getVoteSuites().size());
		return vSuite.getId();
	}


	public void setManualPkgDesc(Long pkgId, String desc) throws Exception {
		ClusteredPackage pkg = this.packageDAO.getClusteredPackage(pkgId);
		pkg.setDescription(desc);
		this.packageDAO.save(pkg);		
	}

	
	/* (non-Javadoc)
	 * @see org.pgist.packages.PackageService#setVotes(java.lang.Long, java.util.HashMap)
	 */
	public void setVotes(User user, Long voteSuiteId, HashMap<Long, Integer> votes) throws Exception {
		PackageVoteSuite vSuite = this.packageDAO.getVoteSuite(voteSuiteId);
		Iterator<Long> i = votes.keySet().iterator();
		Long cPkgId;
		Integer voteValue;
		ClusteredPackage clusteredPkg;
		while(i.hasNext()) {
			cPkgId = i.next();
			voteValue = votes.get(cPkgId);
			clusteredPkg = this.packageDAO.getClusteredPackage(cPkgId);
			assignVote(vSuite, user, clusteredPkg, voteValue);
		}
		recountVotes(vSuite);
	}

    /**
     * Assigns the vote of this user for this package.  It replaces it if it already exist.
     * @param user
     * @param clusterPkgId
     * @param voteValue
     */
	public void assignVote(PackageVoteSuite vSuite, User user, ClusteredPackage clusteredPkg, Integer voteValue) throws Exception {
		PackageUserVote votes = vSuite.getUserVotes().get(clusteredPkg);
		if(votes == null) {
			votes = new PackageUserVote();
			votes.setVoteSuite(vSuite);
			this.packageDAO.save(votes);
			vSuite.getUserVotes().put(clusteredPkg, votes); 
			this.packageDAO.save(vSuite);
		}
		votes.getVotes().put(user, voteValue);
		this.packageDAO.save(votes);
	}	


	public void recountVotes(PackageVoteSuite vSuite) throws Exception {
		//Clear original values
		Iterator<VoteSuiteStat> iStats = vSuite.getStats().iterator();
		while(iStats.hasNext()) {
			this.packageDAO.delete(iStats.next());
		}
		vSuite.getStats().clear();
		
		//For each package, go through and tally up the total votes
		Iterator<ClusteredPackage> cIter = vSuite.getUserVotes().keySet().iterator();
		
		ClusteredPackage tempCPkg;
		PackageUserVote votes;
		Iterator<Integer> iValues;
		int tempHigh;
		int tempMed;
		int tempLow;
		int total;
		while(cIter.hasNext()) {
			tempCPkg = cIter.next();
			System.out.println("MATT: &(*&(*&(*&(*& Recalcing for cpkg " + tempCPkg.getId());
			votes = vSuite.getUserVotes().get(tempCPkg);
			
			tempHigh = 0;
			tempMed = 0;
			tempLow = 0;
			total = votes.getVotes().size();
			if(total > 0) {
				iValues = votes.getVotes().values().iterator();
				while(iValues.hasNext()) {
					switch (iValues.next().intValue()) {
					case PackageUserVote.VOTE_HIGH:
						tempHigh++;
						break;
					case PackageUserVote.VOTE_MEDIUM:
						tempMed++;
						break;
					case PackageUserVote.VOTE_LOW:
						tempLow++;
						break;
					}
				}
				
				System.out.println("High = " + tempHigh/total + " med " + tempMed/total + " low " + tempLow/total);
				VoteSuiteStat stat = new VoteSuiteStat();
				stat.setClusteredPackage(tempCPkg);
				stat.setTotalVotes(total);
				stat.setHighVotePercent(tempHigh/total);
				stat.setMediumVotePercent(tempMed/total);
				stat.setLowVotePercent(tempLow/total);
				
				this.packageDAO.save(stat);
				vSuite.getStats().add(stat);
			}			
		}
		this.packageDAO.save(vSuite);
	}

	/* (non-Javadoc)
	 * @see org.pgist.packages.PackageService#formPackageRoadProjectDTOs(org.pgist.packages.ClusteredPackage)
	 */
	public List<ProjectDTO> createPackageRoadProjectDTOs(ClusteredPackage cPackage, long critSuiteId, long projSuiteId, User user) throws Exception {
		List<ProjectDTO> result = new ArrayList<ProjectDTO>();
		
		//Get the criteria suite
		CriteriaSuite cSuite = this.criteriaDAO.getCriteriaSuiteById(critSuiteId);
				
		//First put all of the project in a hash with the key being the alternativeID, we will use this to lookup the correct
		//source
		ProjectSuite fSuite = this.projectDAO.getProjectSuite(projSuiteId);
		HashMap<Long, Project> sources = new HashMap<Long, Project>();
		Iterator<ProjectRef> iRefs = fSuite.getReferences().iterator();
		Project tempProject;
		Iterator<ProjectAlternative> iProjectAlts;
		
		while(iRefs.hasNext()) {
			tempProject = iRefs.next().getProject();
			iProjectAlts = tempProject.getAlternatives().iterator();
			while(iProjectAlts.hasNext()) {
				sources.put(iProjectAlts.next().getId(), tempProject);
			}
		}
				
		//Now loop through the alternatives in the package and create the dto
		Iterator<ProjectAltRef> iProjectAltRefs = cPackage.projAltRefs.iterator();
		ProjectAltRef tempAltRef;
		ProjectAlternative tempAlt;
		ProjectAlternativeDTO tempAltDTO;
		
		while(iProjectAltRefs.hasNext()) {
			tempAltRef = iProjectAltRefs.next();
			tempAlt = tempAltRef.getAlternative();
						
			if(tempAlt.getGeoType() == ProjectAlternative.PGIST_PROJECT_MODE_ROAD) {
				//Now pull the source back out of the hash 
				tempProject = sources.get(tempAlt.getId());
				
				if(tempProject == null) {
					System.out.println("ERROR: A alternative with no parent was found in the clustered package");
				} else {
					//Create a new DTO
					ProjectDTO fsDTO = new ProjectDTO(tempProject);

					//See if it has already been added to the result
					if(result.contains(fsDTO)) {
						fsDTO = result.get(result.indexOf(fsDTO));
					} else {
						result.add(fsDTO);
					}
					
					//Add the new alternative
					tempAltDTO = new ProjectAlternativeDTO(tempAlt);
					tempAltDTO.setProjGrade(getProjectGrade(tempAltRef));
					tempAltDTO.setAvgGrade(getAvgGrade(tempAltRef, cSuite, user));
					tempAltDTO.setYourGrade(getYourGrade(tempAltRef, cSuite, user));
					fsDTO.getProjectAlternatives().add(tempAltDTO);
					
					//Sort it
					fsDTO.sort();
				}				
			}			
		}
		Collections.sort(result);
		return result;
	}
	
	/* (non-Javadoc)
	 * @see org.pgist.packages.PackageService#formPackageTransitProjectDTOs(org.pgist.packages.ClusteredPackage)
	 */
	public List<ProjectDTO> createPackageTransitProjectDTOs(ClusteredPackage cPackage, long critSuiteId, long projSuiteId, User user) throws Exception {
		List<ProjectDTO> result = new ArrayList<ProjectDTO>();
		
		//Get the criteria suite
		CriteriaSuite cSuite = this.criteriaDAO.getCriteriaSuiteById(critSuiteId);
		
		//First put all of the project in a hash with the key being the alternativeID, we will use this to lookup the correct
		//source
		ProjectSuite fSuite = this.projectDAO.getProjectSuite(projSuiteId);
		HashMap<Long, Project> sources = new HashMap<Long, Project>();
		Iterator<ProjectRef> iRefs = fSuite.getReferences().iterator();
		Project tempProject;
		Iterator<ProjectAlternative> iProjectAlts;
		
		while(iRefs.hasNext()) {
			tempProject = iRefs.next().getProject();
			iProjectAlts = tempProject.getAlternatives().iterator();
			while(iProjectAlts.hasNext()) {
				sources.put(iProjectAlts.next().getId(), tempProject);
			}
		}
				
		//Now loop through the alternatives in the package and create the dto
		Iterator<ProjectAltRef> iProjectAltRefs = cPackage.projAltRefs.iterator();
		ProjectAltRef tempAltRef;
		ProjectAlternative tempAlt;
		ProjectAlternativeDTO tempAltDTO;
		
		while(iProjectAltRefs.hasNext()) {
			tempAltRef = iProjectAltRefs.next();
			tempAlt = tempAltRef.getAlternative();
						
			if(tempAlt.getGeoType() == ProjectAlternative.PGIST_PROJECT_MODE_TRANSIT) {
				//Now pull the source back out of the hash 
				tempProject = sources.get(tempAlt.getId());
				
				if(tempProject == null) {
					System.out.println("ERROR: A alternative with no parent was found in the clustered package");
				} else {
					//Create a new DTO
					ProjectDTO fsDTO = new ProjectDTO(tempProject);

					//See if it has already been added to the result
					if(result.contains(fsDTO)) {
						fsDTO = result.get(result.indexOf(fsDTO));
					} else {
						result.add(fsDTO);
					}
					
					//Add the new alternative
					tempAltDTO = new ProjectAlternativeDTO(tempAlt);
					tempAltDTO.setProjGrade(getProjectGrade(tempAltRef));
					tempAltDTO.setAvgGrade(getAvgGrade(tempAltRef, cSuite, user));
					tempAltDTO.setYourGrade(getYourGrade(tempAltRef, cSuite, user));
					fsDTO.getProjectAlternatives().add(tempAltDTO);
					
					//Sort it
					fsDTO.sort();
				}				
			}			
		}
		Collections.sort(result);
		return result;
	}

	
    public User getUser(UserInfo userInfo) throws Exception {
        return this.fundingDAO.getUserById(userInfo.getId());
    }
    
	/**
	 * Checks first to see if the user package exist and if it doesn't then one is created and 
	 * returned.
	 * @param	info	The users info
	 * @param	packageSuite	The suite to look in
	 * 
	 * @return	The users package
	 */
	public UserPackage createUserPackage(Long packageSuite, UserInfo info, Long fundingSuiteId) throws Exception {
		PackageSuite suite = this.getPackageSuite(packageSuite);
		//Loop through looking for the package
		Iterator<UserPackage> pkgs = suite.getUserPkgs().iterator();
		UserPackage tempPackage;
		while(pkgs.hasNext()) {
			tempPackage = pkgs.next();
			if(tempPackage.getAuthor().getId().equals(info.getId())) {
				if(tempPackage.getSuite().getId().equals(suite.getId())) {
					//System.out.println("MATT 2");
					this.calcUserValues(tempPackage, tempPackage.getAuthor(), fundingSuiteId);
					return tempPackage;					
				}
			}
		}
		//None found, then create and save		
		UserPackage uPack = new UserPackage();		
		User user = this.packageDAO.getUserById(info.getId());
		uPack.setAuthor(user);
		uPack.setSuite(suite);
		packageDAO.save(uPack);
		suite.getUserPkgs().add(uPack);
		packageDAO.save(suite);
		this.calcUserValues(uPack, uPack.getAuthor(), fundingSuiteId);

		return uPack;
	}

	/**
	 * Figures out the composite grade for the entire project by averaging the graded criteria
	 * 
	 * @param altRef
	 * @return
	 */
	private String getProjectGrade(ProjectAltRef altRef) {
		Set<GradedCriteria> gradedC = altRef.getGradedCriteria();
		//Return NA if there are no criteria associated to this project
		if(gradedC.size() == 0) return "NA";
		Iterator<GradedCriteria> iGC = gradedC.iterator();
		GradedCriteria gc;
		float total = 0;
		while(iGC.hasNext()) {
			gc = iGC.next();
			total = total + gc.getValue();
		}
		total = total/gradedC.size();
		return GradedCriteria.convertGrade(total);
	}
	
	/**
	 * Figures out your grade by weighting your criteria against the 
	 * grades that were assigned to the project.
	 * 
	 * @param altRef
	 * @return
	 */
	private String getYourGrade(ProjectAltRef altRef, CriteriaSuite cSuite, User user) {
		Set<GradedCriteria> gradedC = altRef.getGradedCriteria();
		
		HashMap<Criteria, Integer> crits = createPersonalCritsMap(cSuite, user);

		viewCritMap(crits);
		
		//Return NA if there are no criteria associated to this project
		if(gradedC.size() == 0 || crits.size() == 0) return "NA";
		
		Iterator<GradedCriteria> iGC = gradedC.iterator();
		GradedCriteria gc;
		float total = 0;
		while(iGC.hasNext()) {
			gc = iGC.next();
			total = total + (gc.getValue()*crits.get(gc.getCriteria()))/100;
		}
		return GradedCriteria.convertGrade(total);
	}

	public static void viewCritMap(HashMap<Criteria, Integer> crits) {
		System.out.println("MATT: Showing Criteria with size " + crits.size());
		Iterator<Criteria> iCrit = crits.keySet().iterator();
		Criteria tempCrit;
		while(iCrit.hasNext()) {
			tempCrit = iCrit.next();
			System.out.println("MATT: Criteria " + tempCrit.getName() + " value = " + (Integer)crits.get(tempCrit));
		}
	}
	
	/**
	 * Creates a map of all the criteria and the value this user assigned
	 */
	private HashMap<Criteria, Integer> createPersonalCritsMap(CriteriaSuite cSuite, User user) {
		//Create a table of all the values that this user assigned to these criteria
		HashMap<Criteria, Integer> crits = new HashMap<Criteria, Integer>();
		Iterator<CriteriaRef> iRef = cSuite.getWeights().keySet().iterator();
		CriteriaRef tempRef;
		CriteriaUserWeight tempWeight;
		Integer value;
		while(iRef.hasNext()) {
			tempRef = iRef.next();
			tempWeight = (CriteriaUserWeight)cSuite.getWeights().get(tempRef);
			value = tempWeight.getWeights().get(user);
			if(value != null) {
				crits.put(tempRef.getCriterion(), value);				
			}
		}
		return crits;
	}
	
	/**
	 * Returns the average grade for everyone based on the average weight everyone used
	 * 
	 * @param altRef
	 * @param cSuite
	 * @param user
	 * @return
	 */
	private String getAvgGrade(ProjectAltRef altRef, CriteriaSuite cSuite, User user) {
		Set<GradedCriteria> gradedC = altRef.getGradedCriteria();
		
		HashMap<Criteria, Integer> crits = createEveryoneCritsMap(cSuite, user);

		viewCritMap(crits);
		
		//Return NA if there are no criteria associated to this project
		if(gradedC.size() == 0 || crits.size() == 0) return "NA";
		
		Iterator<GradedCriteria> iGC = gradedC.iterator();
		GradedCriteria gc;
		float total = 0;
		while(iGC.hasNext()) {
			gc = iGC.next();
			total = total + (gc.getValue()*crits.get(gc.getCriteria()))/100;
		}
		System.out.println("MATT: TOTAL = " + total);
		return GradedCriteria.convertGrade(total);
	}
	
	/**
	 * Creates a map of all the criteria and the value this user assigned
	 */
	private HashMap<Criteria, Integer> createEveryoneCritsMap(CriteriaSuite cSuite, User user) {
		//Create a table of all the values that this user assigned to these criteria
		HashMap<Criteria, Integer> crits = new HashMap<Criteria, Integer>();
		Iterator<CriteriaRef> iRef = cSuite.getWeights().keySet().iterator();
		CriteriaRef tempRef;
		CriteriaUserWeight tempWeight;
		Integer value = 0;
		Iterator<Integer> iValues;
		while(iRef.hasNext()) {

			tempRef = iRef.next();
			tempWeight = (CriteriaUserWeight)cSuite.getWeights().get(tempRef);
			if(tempWeight.getWeights().size() < 0) {
				value = 0;
				iValues = tempWeight.getWeights().values().iterator();
				while(iValues.hasNext()) {
					value = value + iValues.next().intValue();
				}
				crits.put(tempRef.getCriterion(), value/ tempWeight.getWeights().size());								
			}
		}
		return crits;
	}	

	
	private void displayUserValues(Package uPack) {
		//System.out.println("MATT##################: Looking up funding sources in user package");
		Iterator i = uPack.getPersonalCost().keySet().iterator();
		Long key;
		while(i.hasNext()) {
			key = (Long)i.next();
			//System.out.println("FunSourceAltRef [" + key + "] cost = [" +  uPack.getPersonalCost().get(key) + "]");
		}
		//System.out.println("MATT##################: Looking up funding sources 2");
		
	}

    public PackageSuite createPackageSuite() throws Exception {
        PackageSuite suite = new PackageSuite();       
        return suite;
    }//createPackageSuite()
    
    
    public InfoStructure publish(Long cctId, Long suiteId, String title) throws Exception {
        CCT cct = cctDAO.getCCTById(cctId);
        
        PackageSuite suite = packageDAO.getPackageSuite(suiteId);
        
        Date date = new Date();
        
        InfoStructure structure = new InfoStructure();
        structure.setType("sdpkg");
        structure.setTitle(title);
        structure.setRespTime(date);
        structure.setCctId(cct.getId());
        discussionDAO.save(structure);
        
        for (ClusteredPackage pkg : suite.getClusteredPkgs()) {
            pkg.getId();
            pkg.getBalance();
            pkg.getCreateDate();
            pkg.getDescription();
            pkg.getFundAltRefs();
            pkg.getProjAltRefs();
            pkg.getSuite();
            pkg.getTotalCost();
            pkg.getTotalFunding();
            
            InfoObject obj = new InfoObject();
            obj.setObject(pkg);
            obj.setRespTime(date);
            discussionDAO.save(obj);
            
            structure.getInfoObjects().add(obj);
        }//for ref
        
        discussionDAO.save(structure);
        
        return structure;
    }//publish()
	

	
    /**
     * Utility method that does all of the user calculations regarding the estimated annual cost
     * to you.  All the estimates are stored in the hash map for the user
     * 
     * @param	pkg	The user package
     * @param	funSuiteId		The ID of the funding suite
     * @throws Exception 
     */
    public void calcUserValues(Package pkg, User user, Long funSuiteId) throws Exception {
//System.out.println("MATT##################: Sweet ID!" + funSuiteId);    
    	if(user.getUserCommute() == null) {
    		fundingService.initializeUser(user);    		
    	}
		//Get the users last commute object		
		UserCommute commute = user.getUserCommute();
		FundingSourceSuite fsuite = this.fundingDAO.getFundingSuite(funSuiteId);
				
		float totalVValue = 0;
		float totalMilesDrive = 0;
		float avgMPG = 0;
		
		Iterator<Vehicle> vIter = user.getVehicles().iterator();
		Vehicle veh;
		while(vIter.hasNext()) {
			veh = vIter.next();
			totalVValue = totalVValue + veh.getApproxValue();
			totalMilesDrive = totalMilesDrive + veh.getMilesPerYear();
			avgMPG = avgMPG + veh.getMilesPerGallon();
			
		}
		avgMPG = avgMPG / user.getVehicles().size();
		    	
		float peakTrips = 0;
		float offPeakTrips = 0;
//		float gasCost = fundingDAO.getZipCodeGasByZipCode(tempUser.getZipcode()).getAvgGas();
//		float consumption = fundingDAO.getConsumptionByIncome(tempUser.getIncome()).getConsumption(tempUser.getFamilyCount());
    	
    	pkg.getPersonalCost().clear();
    	
    	Iterator<FundingSourceRef> refs = fsuite.getReferences().iterator();
    	Iterator<FundingSourceAltRef> fSources;
    	FundingSourceAltRef tempAltRef;
    	FundingSourceAlternative tempAlt;
    	FundingSource tempSource;
    	while(refs.hasNext()) {
    		fSources = refs.next().getAltRefs().iterator();    		
        	while(fSources.hasNext()) {
        		tempAltRef = fSources.next();
        		tempAlt = tempAltRef.getAlternative();
        		tempSource = tempAlt.getSource();
        		
        		peakTrips = TaxCalcUtils.estimatePeakTrips(commute, tempSource);
        		offPeakTrips = TaxCalcUtils.estimateOffPeakTrips(commute, tempSource);
        		
    			switch (tempSource.getType()) {

    			case FundingSource.TYPE_EMPLOYER_EXCISE_TAX:	
    				pkg.getPersonalCost().put(tempAltRef.getId(), TaxCalcUtils.calcUserEmployerExciseAlternativeCost(tempAlt.getTaxRate(), TaxCalcUtils.EMPLOYER_PERCENTAGE));
    				break;
    			case FundingSource.TYPE_GAS_TAX:			
    				pkg.getPersonalCost().put(tempAltRef.getId(), TaxCalcUtils.calcUserGasTaxCost(avgMPG, tempAlt.getTaxRate(), totalMilesDrive));
    				break;
    			case FundingSource.TYPE_LICENSE:			
    				pkg.getPersonalCost().put(tempAltRef.getId(), TaxCalcUtils.calcUserVehicleLicenseCost(tempAlt.getTaxRate(), user.getVehicles().size()));
    				break;
    			case FundingSource.TYPE_MOTOR_TAX:			
    				pkg.getPersonalCost().put(tempAltRef.getId(), TaxCalcUtils.calcUserVehicleExciseCost(tempAlt.getTaxRate(), totalVValue));
    				break;
    			case FundingSource.TYPE_PARKING_TAX:
    				pkg.getPersonalCost().put(tempAltRef.getId(), TaxCalcUtils.calcUserParkingCost(tempAlt.getTaxRate(), peakTrips, offPeakTrips));
    				break;
    			case FundingSource.TYPE_SALES_GAS_TAX:			
    				pkg.getPersonalCost().put(tempAltRef.getId(), TaxCalcUtils.calcUserGasSalesTaxCost(tempAlt.getTaxRate(), commute.getCostPerGallon(), totalMilesDrive, avgMPG));
    				break;
    			case FundingSource.TYPE_SALES_TAX:			
    				pkg.getPersonalCost().put(tempAltRef.getId(), TaxCalcUtils.calcUserSalesTaxCost(tempAlt.getTaxRate(), commute.getAnnualConsume()));
    				break;
    			case FundingSource.TYPE_TOLLS:			
    				pkg.getPersonalCost().put(tempAltRef.getId(), TaxCalcUtils.calcUserTollAlternatives(tempAlt.getPeakHourTripsRate(), peakTrips, tempAlt.getOffPeakTripsRate(), offPeakTrips));
    				break;

    			default:
    				break;
    			} 		    		
        	}
    	}
		displayUserValues(pkg);
    }
    
    //------------------------ Mike Lowery section ----------------------------------
    
	/* (non-Javadoc)
	 * @see org.pgist.packages.PackageService#createClusteredPackages(java.lang.Long, int)
	 */
	public void createClusteredPackages(Long pkgSuiteId, int pkgCount) throws Exception {
        Set packages = new HashSet();
        
        PackageSuite pSuite = this.getPackageSuite(pkgSuiteId);

        //Clean out old non manual clustered packages
        Iterator<ClusteredPackage> iCPackages = pSuite.getClusteredPkgs().iterator();
        
        Set<ClusteredPackage> result = new HashSet<ClusteredPackage>();
        ClusteredPackage cp;
        while(iCPackages.hasNext()) {
        	cp = iCPackages.next();
        	if(cp.isManual()) {
        		result.add(cp);
        	}
        }

        for(int i = 0; i < pkgCount; i++) {
            cp = new ClusteredPackage();
            cp.setManual(false);
            cp.setDescription("Auto Clustered Package " + i);
            cp.setCreateDate(new Date());
            cp.setTotalCost(1200f);
            cp.setAvgResidentCost(40f * i);
            result.add(cp);        	
        }
        
        
        pSuite.setClusteredPkgs(result);
        packageDAO.save(pSuite);               
	}

	/* (non-Javadoc)
	 * @see org.pgist.packages.PackageService#createUserPackage(org.pgist.packages.TunerConfig, float)
	 */
	public void createKSUserPackage(Long usrPkgId, TunerConfig conf, float mylimit, float avglimit) throws Exception {

		UserPackage upack = this.getUserPackage(usrPkgId);
		this.calcUserValues(upack, upack.getAuthor(), conf.getFundSuiteId());
						
		//Clear out all of the previous choices
		upack.getFundAltRefs().clear();
		upack.getProjAltRefs().clear();		

		//Using the limits and the conf, figure out what funding sources should be in the package
		findBestFundingSourceSolution(upack, conf, mylimit, avglimit);		
		
		//Recalculate to find the total funding
		upack.updateCalculations();		
		
		//Get the users weights for this project and store them in an easier to access format
		HashMap<Criteria, Integer> cWeights = findUserWeights(conf.getCritSuiteId(), upack.getAuthor());
		
		//Using the total funding available, not figure out what projects should be in the package
		findBestProjectSolution(upack, conf, upack.getTotalFunding(), cWeights);
		
		//Save the result
		this.packageDAO.save(upack);		
	}    

	/**
	 * Returns all of the users weights they assigned for the different criteria
	 * 
	 * @return	A hashmap with the Criteria as the key and an integer value as the weight
	 * @throws Exception 
	 */
	private HashMap<Criteria, Integer> findUserWeights(Long critSuiteId, User user) throws Exception {
		HashMap<Criteria, Integer> cWeights = new HashMap<Criteria, Integer>();
        CriteriaSuite csuite = criteriaDAO.getCriteriaSuiteById(critSuiteId);
        Iterator<CriteriaRef> refs = csuite.getReferences().iterator();
        CriteriaRef ref;
        CriteriaUserWeight userWeights;
        Integer value;
        while(refs.hasNext()) {
        	ref = refs.next();
            userWeights = csuite.getWeights().get(ref);
            value = userWeights.getWeights().get(user);
            cWeights.put(ref.getCriterion(), value);
        }

        //TODO Remove this after it proves to be working
        //System.out.println("For User [" + user.getFirstname() + "] the following criteria were found");
        Iterator<Criteria> i = cWeights.keySet().iterator();
        Criteria c;
        while(i.hasNext()) {
        	c = i.next();
        	//System.out.println("Criteria [" + c.getName() + "] value [" + cWeights.get(c).intValue() + "]");
        }
        
        return cWeights;
	}
	
	/**
	 * Finds the best combination of project alternatives for the user
	 * 
	 * @param	upack	The user package
	 * @param	conf	The tuner config
	 * @param	csuite	The criteria suite that holds all of the values
	 * @param	weights		The weights this user has assigned to the criteria
	 * @throws Exception 
	 */
	private void findBestProjectSolution(UserPackage upack, TunerConfig conf, float totalFunding, HashMap<Criteria, Integer> cWeight) throws Exception {
		
		//Create a collection of ProjectKSItems
		Collection<KSChoices> choiceCol = new ArrayList<KSChoices>(); 
		KSChoices choices;
		
		//Get all the projects for the suite
		ProjectSuite pSuite = projectDAO.getProjectSuite(conf.getProjSuiteId());
		ProjectRef tempRef;
		Project tempProject;

		Iterator<ProjectAltRef> pAltsRef;
		ProjectAltRef tempProjectAltRef;
		ProjectAlternative tempProjectAlt;
		Integer choice;
		
		//loop through them checking with the config to 
		Iterator<ProjectRef> refs = pSuite.getReferences().iterator();
		while(refs.hasNext()) {
			tempRef = refs.next();
			choices = new KSChoices();
			
			//Loop through the alternatives
			tempProject = tempRef.getProject();
			pAltsRef = tempRef.getAltRefs().iterator();
			while(pAltsRef.hasNext()) {
				tempProjectAltRef = pAltsRef.next();
				tempProjectAlt = tempProjectAltRef.getAlternative();
								
				//Get the users opinion on this project
				choice = (Integer)conf.getProjectChoices().get(tempProjectAlt.getId());
				if(choice == null) choice = TunerConfig.MAYBE;
				
				//figure out what to do with the item
				switch (choice) {
					case TunerConfig.MAYBE:
						ProjectKSItem tempProjectKSI = new ProjectKSItem();
						
						//Set the profit and cost for this item
						tempProjectKSI.setCost(tempProjectAlt.getCost());
						
						//Add up all the cost and weights here
						tempProjectKSI.setProfit(calcProjectProfit(tempProjectAltRef, cWeight));
						tempProjectKSI.setProjectAltRef(tempProjectAltRef);
						
						choices.getChoices().add(tempProjectKSI);
						break;
					case TunerConfig.MUST_HAVE:
						//Find the must haves and add them to the package
						upack.getProjAltRefs().add(tempProjectAltRef);
						
						//Increment the buget already spent
						totalFunding = totalFunding - (float)tempProjectAlt.getCost();
						
						break;
					case TunerConfig.NEVER:
						//Do nothing, the user hates this option
						break;
				}				
			}			
			
			if(choices.getChoices().size() > 0) {
				choiceCol.add(choices);
			}
		}
		
		//Check that the package isn't already over buget
		if(totalFunding < 0) throw new BudgetExceededException("The projects selected exceed the budget limits you provided");

		
		//Send the collection to the KSAlgorithm
		Collection<KSItem> result = KSEngine.mcknap(choiceCol, totalFunding);
		
		//Add the resulting items to the users package
		Iterator<KSItem> resultIter = result.iterator();
		ProjectKSItem tempProjectKSI;
		while(resultIter.hasNext()) {
			tempProjectKSI = (ProjectKSItem)resultIter.next();
			upack.getProjAltRefs().add(tempProjectKSI.getProjectAltRef());
		}			
	}
	
	/**
	 * Calculates the profit for the user (meaining how much they like this project) based off of
	 * their preferences and the impact judged by the experts
	 * 
	 * @param	projAltRef	The alt ref for this project (stores the experts opinion
	 * @param	cWeights 	An array list of all the weights this user has selected
	 */
	private float calcProjectProfit(ProjectAltRef projAltRef, HashMap<Criteria, Integer> cWeights ) {
		Iterator<GradedCriteria> crits = projAltRef.getGradedCriteria().iterator();

		GradedCriteria crit;
		float total = 0;
		Integer weight;
		while(crits.hasNext()) {
			crit = crits.next();
			weight = cWeights.get(crit);
			total = total + crit.getValue() *  weight.floatValue()/100;
		}
		
		return total;
	}
	
	
	/**
	 * Finds the best combination of funding source alternatives for the user
	 * 
	 * @param	upack	The user package
	 * @param	conf	The tuner config
	 * @param	mylimit	The limit for the user
	 * @param	avglimit	The limit for the average user
	 * @throws Exception, BudgetExceededException 
	 */
	private void findBestFundingSourceSolution(UserPackage upack, TunerConfig conf, float mylimit, float avglimit) throws Exception, BudgetExceededException {
		float mybudget = 0;
		float avgbudget = 0;
		float myFundingCost = 0;
		
		//Create a collection of funding source KS Items, do not add the ones that are to be left out
		Collection<KSChoices> choiceCol = new ArrayList<KSChoices>(); 
		KSChoices choices;
				
		//Get all the funding sources for the suite
		FundingSourceSuite fSuite = fundingDAO.getFundingSuite(conf.getFundSuiteId());
		FundingSourceRef tempRef;
		FundingSource tempFSource;

		Iterator<FundingSourceAltRef> fsAltsRef;
		FundingSourceAltRef tempFSourceAltRef;
		FundingSourceAlternative tempFSourceAlt;
		Integer choice;
		
		//loop through them checking with the config to 
		Iterator<FundingSourceRef> refs = fSuite.getReferences().iterator();
		while(refs.hasNext()) {
			tempRef = refs.next();
			choices = new KSChoices();
			
			//Loop through the alternatives
			tempFSource = tempRef.getSource();
			fsAltsRef = tempRef.getAltRefs().iterator();
			while(fsAltsRef.hasNext()) {
				tempFSourceAltRef = fsAltsRef.next();
				tempFSourceAlt = tempFSourceAltRef.getAlternative();
				
				//Figure out the personal cost for this alternative
				myFundingCost = upack.getPersonalCost(tempFSourceAlt.getId());				
				
				//Get the users opinion on this alternative
				choice = (Integer)conf.getFundingChoices().get(tempFSourceAlt.getId());
				if(choice == null) choice = TunerConfig.MAYBE;
				
				//figure out what to do with the item
				switch (choice) {
					case TunerConfig.MAYBE:
						FundingSourceKSItem tempFSKSI = new FundingSourceKSItem();
						
						//Set the profit and cost for this item, we only sort on the users cost
						tempFSKSI.setCost(myFundingCost);
						tempFSKSI.setProfit(tempFSourceAlt.getRevenue());
						tempFSKSI.setFundingSourceAltRef(tempFSourceAltRef);
						
						choices.getChoices().add(tempFSKSI);
						break;
					case TunerConfig.MUST_HAVE:
						//Find the must haves and add them to the package
						upack.getFundAltRefs().add(tempFSourceAltRef);
						
						//Increment the buget already spent
						mybudget = mybudget + myFundingCost;
						
						break;
					case TunerConfig.NEVER:
						//Do nothing, the user hates this option
						break;
				}				
			}			
			
			if(choices.getChoices().size() > 0) {
				choiceCol.add(choices);
			}
		}
		
		
		//Check that the package isn't already over buget
		if(mybudget > mylimit) throw new BudgetExceededException("The funding sources must have cost more than the budget you provided");
		
		mylimit = mylimit - mybudget;
		
		//Send the collection to the KSAlgorithm
		Collection<KSItem> result = KSEngine.mcknap(choiceCol, mylimit);
		
		//Add the resulting items to the users package
		Iterator<KSItem> resultIter = result.iterator();
		FundingSourceKSItem tempFSKSI;
		while(resultIter.hasNext()) {
			tempFSKSI = (FundingSourceKSItem)resultIter.next();
			upack.getFundAltRefs().add(tempFSKSI.getFundingSourceAltRef());
		}			
	}
	
}//class PackageServiceImpl
