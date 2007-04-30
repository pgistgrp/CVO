package org.pgist.packages;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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


	/**
	 * Adds the provided alternative project to the package
	 */
	public UserPackage addProjectAlternative(Long usrPkgId, Long altId) throws Exception {
		
		//Get the user package
		UserPackage uPack = this.packageDAO.getUserPackage(usrPkgId);
		
		//get the alternative
		ProjectAltRef altRef = this.projectDAO.getProjectAlternativeReference(altId); 
		//Add the reference
		uPack.getProjAltRefs().add(altRef);
		
		uPack.updateCalculations();
		this.packageDAO.save(uPack);

		return uPack;
	}

	public UserPackage deleteProjectAlternative(Long usrPkgId, Long altId) throws Exception {
		//Get the user package
		UserPackage uPack = this.packageDAO.getUserPackage(usrPkgId);
				
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
	}

	
	/**
	 * Adds the provided funding source to the package
	 */
	public UserPackage addFundingAlternative(Long usrPkgId, Long funAltRefId) throws Exception {
		//Get the user package
		UserPackage uPack = this.packageDAO.getUserPackage(usrPkgId);
		
		//get the alternative
		FundingSourceAltRef altRef = this.fundingDAO.getFundingSourceAlternativeReference(funAltRefId);

		//Add the reference
		uPack.getFundAltRefs().add(altRef);
		
		this.fundingDAO.save(altRef);
		uPack.updateCalculations();
		this.packageDAO.save(uPack);

		return uPack;
	}

	

	public UserPackage deleteFundingAlternative(Long usrPkgId, Long funAltRefId) throws Exception {
		//Get the user package
		UserPackage uPack = this.packageDAO.getUserPackage(usrPkgId);
				
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
					System.out.println("MATT 2");
					this.calcUserValues(tempPackage, fundingSuiteId);
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
		this.calcUserValues(uPack, fundingSuiteId);

		return uPack;
	}

	private void displayUserValues(UserPackage uPack) {
		System.out.println("MATT: Looking up funding sources in user package");
		Iterator i = uPack.getPersonalCost().keySet().iterator();
		Long key;
		while(i.hasNext()) {
			key = (Long)i.next();
			System.out.println("FunSourceAltRef [" + key + "] cost = [" +  uPack.getPersonalCost().get(key) + "]");
		}
		System.out.println("MATT: Looking up funding sources 2");
		
	}

    public PackageSuite createPackageSuite() throws Exception {
        PackageSuite suite = new PackageSuite();       
        return suite;
    }//createPackageSuite()
    
    
    public InfoStructure publish(Long cctId, Long suiteId) throws Exception {
        CCT cct = cctDAO.getCCTById(cctId);
        
        PackageSuite suite = packageDAO.getPackageSuite(suiteId);
        
        Date date = new Date();
        
        InfoStructure structure = new InfoStructure();
        structure.setType("sdpkg");
        structure.setTitle("Step 4a: Review Packages");
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
     * @param	usrPkg	The user package
     * @param	funSuiteId		The ID of the funding suite
     * @throws Exception 
     */
    public void calcUserValues(UserPackage usrPkg, Long funSuiteId) throws Exception {
System.out.println("MATT: Sweet ID!" + funSuiteId);    
    	User tempUser = usrPkg.getAuthor();
    	if(tempUser.getUserCommute() == null) {
    		fundingService.initializeUser(tempUser);    		
    	}
		//Get the users last commute object		
		UserCommute commute = tempUser.getUserCommute();
		FundingSourceSuite fsuite = this.fundingDAO.getFundingSuite(funSuiteId);
				
		float totalVValue = 0;
		float totalMilesDrive = 0;
		float avgMPG = 0;
		
		Iterator<Vehicle> vIter = tempUser.getVehicles().iterator();
		Vehicle veh;
		while(vIter.hasNext()) {
			veh = vIter.next();
			totalVValue = totalVValue + veh.getApproxValue();
			totalMilesDrive = totalMilesDrive + veh.getMilesPerYear();
			avgMPG = avgMPG + veh.getMilesPerGallon();
			
		}
		avgMPG = avgMPG / tempUser.getVehicles().size();
		    	
		float peakTrips = 0;
		float offPeakTrips = 0;
//		float gasCost = fundingDAO.getZipCodeGasByZipCode(tempUser.getZipcode()).getAvgGas();
//		float consumption = fundingDAO.getConsumptionByIncome(tempUser.getIncome()).getConsumption(tempUser.getFamilyCount());
    	
    	usrPkg.getPersonalCost().clear();
    	
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
    				usrPkg.getPersonalCost().put(tempAltRef.getId(), TaxCalcUtils.calcUserEmployerExciseAlternativeCost(tempAlt.getTaxRate(), TaxCalcUtils.EMPLOYER_PERCENTAGE));
    				break;
    			case FundingSource.TYPE_GAS_TAX:			
    				usrPkg.getPersonalCost().put(tempAltRef.getId(), TaxCalcUtils.calcUserGasTaxCost(avgMPG, tempAlt.getTaxRate(), totalMilesDrive));
    				break;
    			case FundingSource.TYPE_LICENSE:			
    				usrPkg.getPersonalCost().put(tempAltRef.getId(), TaxCalcUtils.calcUserVehicleLicenseCost(tempAlt.getTaxRate(), tempUser.getVehicles().size()));
    				break;
    			case FundingSource.TYPE_MOTOR_TAX:			
    				usrPkg.getPersonalCost().put(tempAltRef.getId(), TaxCalcUtils.calcUserVehicleExciseCost(tempAlt.getTaxRate(), totalVValue));
    				break;
    			case FundingSource.TYPE_PARKING_TAX:
    				usrPkg.getPersonalCost().put(tempAltRef.getId(), TaxCalcUtils.calcUserParkingCost(tempAlt.getTaxRate(), peakTrips, offPeakTrips));
    				break;
    			case FundingSource.TYPE_SALES_GAS_TAX:			
    				usrPkg.getPersonalCost().put(tempAltRef.getId(), TaxCalcUtils.calcUserGasSalesTaxCost(tempAlt.getTaxRate(), commute.getCostPerGallon(), totalMilesDrive, avgMPG));
    				break;
    			case FundingSource.TYPE_SALES_TAX:			
    				usrPkg.getPersonalCost().put(tempAltRef.getId(), TaxCalcUtils.calcUserSalesTaxCost(tempAlt.getTaxRate(), commute.getAnnualConsume()));
    				break;
    			case FundingSource.TYPE_TOLLS:			
    				usrPkg.getPersonalCost().put(tempAltRef.getId(), TaxCalcUtils.calcUserTollAlternatives(tempAlt.getPeakHourTripsRate(), peakTrips, tempAlt.getOffPeakTripsRate(), offPeakTrips));
    				break;

    			default:
    				break;
    			} 		    		
        	}
    	}
		displayUserValues(usrPkg);
    }
    
    //------------------------ Mike Lowery section ----------------------------------
    
	/* (non-Javadoc)
	 * @see org.pgist.packages.PackageService#createClusteredPackages(java.lang.Long, int)
	 */
	public void createClusteredPackages(Long pkgSuiteId, int pkgCount) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.pgist.packages.PackageService#createUserPackage(org.pgist.packages.TunerConfig, float)
	 */
	public void createKSUserPackage(Long usrPkgId, TunerConfig conf, float mylimit, float avglimit) throws Exception {

		UserPackage upack = this.getUserPackage(usrPkgId);
		this.calcUserValues(upack, conf.getFundSuiteId());
						
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
        System.out.println("For User [" + user.getFirstname() + "] the following criteria were found");
        Iterator<Criteria> i = cWeights.keySet().iterator();
        Criteria c;
        while(i.hasNext()) {
        	c = i.next();
        	System.out.println("Criteria [" + c.getName() + "] value [" + cWeights.get(c).intValue() + "]");
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
