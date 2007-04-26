package org.pgist.packages;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.pgist.cvo.CCT;
import org.pgist.cvo.CCTDAO;
import org.pgist.discussion.DiscussionDAO;
import org.pgist.discussion.InfoObject;
import org.pgist.discussion.InfoStructure;
import org.pgist.funding.FundingDAO;
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
import org.pgist.projects.ProjectAltRef;
import org.pgist.projects.ProjectDAO;
import org.pgist.users.User;
import org.pgist.users.UserInfo;
import org.pgist.users.Vehicle;


/**
 * 
 * @author kenny
 *
 */
public class PackageServiceImpl implements PackageService {

	
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
	 * 
	 * @param	packageSuite	The suite to look in
	 * @param	info	The users info
	 * @return	The users package
	 */
	public UserPackage createUserPackage(Long packageSuite, UserInfo info) throws Exception {
		PackageSuite suite = this.getPackageSuite(packageSuite);		

		//Loop through looking for the package
		Iterator<UserPackage> pkgs = suite.getUserPkgs().iterator();
		UserPackage tempPackage;
		while(pkgs.hasNext()) {
			tempPackage = pkgs.next();
			if(tempPackage.getAuthor().getId().equals(info.getId())) {
				if(tempPackage.getSuite().getId().equals(suite.getId())) {
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
		return uPack;
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
     * @throws Exception 
     */
    public void calcUserValues(UserPackage usrPkg) throws Exception {
    
    	User tempUser = usrPkg.getAuthor();
		//Get the users last commute object		
		UserCommute commute = tempUser.getUserCommute();
				
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
    	Iterator<FundingSourceAltRef> fSources = usrPkg.getFundAltRefs().iterator();
    	FundingSourceAlternative tempAlt;
    	FundingSource tempSource;
    	while(fSources.hasNext()) {
    		tempAlt = fSources.next().getAlternative();
    		tempSource = tempAlt.getSource();
    		
    		peakTrips = TaxCalcUtils.estimatePeakTrips(commute, tempSource);
    		offPeakTrips = TaxCalcUtils.estimateOffPeakTrips(commute, tempSource);
    		
			switch (tempSource.getType()) {

			case FundingSource.TYPE_EMPLOYER_EXCISE_TAX:	
				usrPkg.getPersonalCost().put(tempAlt.getId(), TaxCalcUtils.calcUserEmployerExciseAlternativeCost());
				break;
			case FundingSource.TYPE_GAS_TAX:			
				usrPkg.getPersonalCost().put(tempAlt.getId(), TaxCalcUtils.calcUserGasTaxCost(avgMPG, tempAlt.getTaxRate(), totalMilesDrive));
				break;
			case FundingSource.TYPE_LICENSE:			
				usrPkg.getPersonalCost().put(tempAlt.getId(), TaxCalcUtils.calcUserVehicleLicenseCost(tempAlt.getTaxRate(), tempUser.getVehicles().size()));
				break;
			case FundingSource.TYPE_MOTOR_TAX:			
				usrPkg.getPersonalCost().put(tempAlt.getId(), TaxCalcUtils.calcUserVehicleExciseCost(tempAlt.getTaxRate(), totalVValue));
				break;
			case FundingSource.TYPE_PARKING_TAX:
				usrPkg.getPersonalCost().put(tempAlt.getId(), TaxCalcUtils.calcUserParkingCost(tempAlt.getTaxRate(), peakTrips, offPeakTrips));
				break;
			case FundingSource.TYPE_SALES_GAS_TAX:			
				usrPkg.getPersonalCost().put(tempAlt.getId(), TaxCalcUtils.calcUserGasSalesTaxCost(tempAlt.getTaxRate(), commute.getCostPerGallon(), totalMilesDrive, avgMPG));
				break;
			case FundingSource.TYPE_SALES_TAX:			
				usrPkg.getPersonalCost().put(tempAlt.getId(), TaxCalcUtils.calcUserSalesTaxCost(tempAlt.getTaxRate(), commute.getAnnualConsume()));
				break;
			case FundingSource.TYPE_TOLLS:			
				usrPkg.getPersonalCost().put(tempAlt.getId(), TaxCalcUtils.calcUserTollAlternatives(tempAlt.getPeakHourTripsRate(), peakTrips, tempAlt.getOffPeakTripsRate(), offPeakTrips));
				break;

			default:
				break;
			} 		    		
    	}
    	
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
	public void createUserPackage(Long usrPkgId, TunerConfig conf, float mylimit, float avglimit) throws Exception {

		UserPackage upack = this.getUserPackage(usrPkgId);
		this.calcUserValues(upack);
		
		float mybudget = 0;
		float avgbudget = 0;
		float myFundingCost = 0;
		
		//Clear out all of the previous choices
		upack.getFundAltRefs().clear();
		upack.getProjAltRefs().clear();
		

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
						
						//Set the profit and cost for this item
//TODO, somewhere around here I need to set the user cost vs the avg cost						
						tempFSKSI.setCost(myFundingCost);
						tempFSKSI.setProfit(tempFSourceAlt.getRevenue());
						
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
		
		//Send the collection to the KSAlgorithm
		Collection<KSItem> result = KSEngine.mcknap(choiceCol, mylimit);
		
		//Add the resulting items to the users package
		
		
		//Update the total amount for the projects
		//Create a collection of ProjectKSItems
		//Send the collection to the KSAlgorithm 
		//Add the resulting items to the users package

		//Save the result
		this.packageDAO.save(upack);		
	}    
	
}//class PackageServiceImpl
