package org.pgist.funding;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.pgist.cvo.CCT;
import org.pgist.cvo.CCTDAO;
import org.pgist.discussion.DiscussionDAO;
import org.pgist.discussion.InfoObject;
import org.pgist.discussion.InfoStructure;
import org.pgist.users.User;
import org.pgist.users.UserInfo;
import org.pgist.users.Vehicle;


/**
 * 
 * @author kenny
 *
 */
public class FundingServiceImpl implements FundingService {
    

    public static final float NO_CAR_FACTOR = 0.3f;    
    public static final float DEFAULT_GAS_COST = 2.85f;
    public static final float DEFAULT_CONSUMPTION = 19748;
	
    private FundingDAO fundingDAO;
    
    private CCTDAO cctDAO;
    
    private DiscussionDAO discussionDAO;
    
    
    public void setFundingDAO(FundingDAO fundingDAO) {
        this.fundingDAO = fundingDAO;
    }
    
    
    public void setCctDAO(CCTDAO cctDAO) {
        this.cctDAO = cctDAO;
    }
    
    
    public void setDiscussionDAO(DiscussionDAO discussionDAO) {
        this.discussionDAO = discussionDAO;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    public User getUser(UserInfo userInfo) throws Exception {
        return this.fundingDAO.getUserById(userInfo.getId());
    }
    
    
	/**
	 * Forms a commute object from the user
	 * 
	 * @param	user	The user to base the commute off of
	 * @return	A commute object depicting the users commute
	 */
	public UserTaxInfoDTO createCommute(UserTaxInfoDTO user) throws InvalidZipcodeException, Exception {

		//Save the user object
		User tempUser = this.updateUserTaxInfo(user);
		
		//Get the users last commute object		
		UserCommute commute = tempUser.getUserCommute();
		copyAcrossTolls(user.getTolls(), commute);
				
		String zipcode = user.getZipcode();		
		//Check for a valid zipcode
		try {
			int tempZip = Integer.parseInt(zipcode);
			if(tempZip < 98001 || tempZip > 98940) {
				throw new InvalidZipcodeException("Zipcode of [" + zipcode + "] outside the range of the calculator");				
			}
		} catch (NumberFormatException e) {
			throw new InvalidZipcodeException("Zipcode of [" + zipcode + "] is not a valid number");
		}
		
		ZipCodeFactor zcf = this.fundingDAO.getZipCodeFactorByZipCode(zipcode);
		
		float carFactor = 1;
		if(tempUser.getVehicles().size() == 0) {
			carFactor = NO_CAR_FACTOR;
		}
		int driveAlone = tempUser.getDriveDays();
		int carpool = tempUser.getCarpoolDays();
		int numPassengers = tempUser.getCarpoolPeople();		
		
		Iterator<UserFundingSourceToll> tolls = commute.getTolls().iterator();
		while(tolls.hasNext()) {
			setTripRates(tolls.next(), zcf, carFactor, driveAlone, carpool, numPassengers);			
		}						
		
				
		//Include the gas cost
		ZipCodeGas gasZip = this.fundingDAO.getZipCodeGasByZipCode(zipcode);
		if(gasZip == null) {
			commute.setCostPerGallon(DEFAULT_GAS_COST);
		} else {
			commute.setCostPerGallon(gasZip.getAvgGas());			
		}
		
		//Include the annual consumption for the sales tax
		Consumption con = this.fundingDAO.getConsumptionByIncome(user.getIncome());
		if(con == null) {
			commute.setAnnualConsume(DEFAULT_CONSUMPTION);
		} else {
			commute.setAnnualConsume(con.getConsumption(user.getFamilyCount()));
		}

		
		//Save the commute
		this.fundingDAO.save(commute);
		
		user.loadWithCommuteInfo(commute);
		return user;
	}
	
	/**
	 * Creates a report from the provided UserTaxInfoDTO and funding suite
	 * 
	 * @param	user	The UserTaxInfoDTO with all the info in it
	 * @param	fundingSuiteID	The funding suite 
	 */
	public UserTaxInfoDTO createCostReport(UserTaxInfoDTO user, Long fundingSuiteId) throws Exception {		
		
		//Save the user object
		User tempUser = this.updateUserTaxInfo(user);
		
		//Get the users last commute object		
		UserCommute commute = tempUser.getUserCommute();
		copyAcrossTolls(user.getTolls(), commute);
				
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
		//Get rid of infinite division errors
		if(tempUser.getVehicles().size() == 0) {
			avgMPG = 0;
		} else {
			avgMPG = avgMPG / tempUser.getVehicles().size();			
		}
		
		
		//Go through the funding suite and calculate the users costs
		FundingSourceSuite suite = this.fundingDAO.getFundingSuite(fundingSuiteId);

		if(suite == null) throw new Exception("Could not create cost report because no funding suite with an ID of [" + fundingSuiteId + "] could be found");
		
		user.getCosts().clear();
		Iterator<FundingSourceRef> refs = suite.getReferences().iterator();
		while(refs.hasNext()) {
			TaxCalcUtils.createReport(refs.next().getSource(), user.getCosts(), 
					user.getAnnualConsume(), tempUser.getVehicles().size(), totalVValue, totalMilesDrive, avgMPG, user.getCostPerGallon() );
		}
		
		//Go through the tolls and calculate the costs
		Iterator<UserFundingSourceToll> tolls = commute.getTolls().iterator();
		UserFundingSourceToll tempToll;
		while(tolls.hasNext()) {
			tempToll = tolls.next();
			if(tempToll.getName().equals(UserFundingSourceToll.PARKING_DOWNTOWN)) {
				TaxCalcUtils.createParkingTollReport(tempToll, user.getCosts());								
			} else {
				TaxCalcUtils.createTollReport(tempToll, user.getCosts());				
			}
		}
						
		return user;
	}
	
	/**
	 * Returns the User asked for
	 * 
	 * @param	userId	The ID of the user desired
	 */
	public UserTaxInfoDTO createUserTaxInfoDTO(Long userId) throws Exception {
		UserTaxInfoDTO utax = new UserTaxInfoDTO();
		User user = this.fundingDAO.getUserById(userId);
		initializeUser(user);
		utax.loadWithUserInfo(user);
			
		return utax;
	}
		
	/**
	 * Initializes the user with all of the necessary tolls and the user commute
	 */
	public void initializeUser(User user) throws Exception {
    	if(user.getUserCommute() == null) {
    		UserCommute commute = new UserCommute();    		
    		user.setUserCommute(commute);
    		this.fundingDAO.save(commute);
    		this.fundingDAO.save(user);       		
    	}   
    	if(user.getUserCommute().getTolls().size() == 0) {
    		//Add the tolls
    		user.getUserCommute().setTolls(createUserTolls());
    		
    		this.fundingDAO.save(user.getUserCommute());    		
    	}
	}
	
    /**
     * Creates all of the user tolls
     */
    public SortedSet<UserFundingSourceToll> createUserTolls() throws Exception {
        SortedSet<UserFundingSourceToll> tolls = new TreeSet<UserFundingSourceToll>(new UserFundingSourceTollComparator());
    	tolls.clear();
    	tolls.add(createToll(UserFundingSourceToll.PARKING_DOWNTOWN));
    	tolls.add(createToll(UserFundingSourceToll.ALASKA_WAY_VIADUCT));
    	tolls.add(createToll(UserFundingSourceToll.I405N));
    	tolls.add(createToll(UserFundingSourceToll.I405S));
    	tolls.add(createToll(UserFundingSourceToll.SR520));
    	tolls.add(createToll(UserFundingSourceToll.I90));
    	tolls.add(createToll(UserFundingSourceToll.SR167));
    	return tolls;
    }

    /**
     * Creates a user toll
     * 
     * @param	name	The name of the toll
     * @return	An initialized toll
     */
    private UserFundingSourceToll createToll(String name) throws Exception {
    	UserFundingSourceToll toll;
    	toll = new UserFundingSourceToll();
    	toll.setName(name);
    	toll.setPeakTrips(0);
    	toll.setOffPeakTrips(0);
    	toll.setUsed(false);
		try {
			linkFundingSource(toll);
		} catch (UnknownFundingSourceException e) {
			System.out.println("ERROR: " + e.getMessage());
		}
    	
    	return toll;
    }    
    	
	/**
	 * Updates the users information.
	 * 
	 * NOTE: This only updates information related to the FundingCalculator game
	 * 
	 * @param	user	The user to update
	 * @return	The saved user class
	 */
	public User updateUserTaxInfo(UserTaxInfoDTO user) throws Exception {
		
		//Load the original user
		User tempUser = this.fundingDAO.getUserById(user.getUserId());
		
		user.loadUserWithData(tempUser);		
		//Save it
		this.fundingDAO.save(tempUser);
		return tempUser;
	}

	/**
	 * Adds a vehicle to the user
	 * 
	 * @param	userId	The userID
	 * @param	mpg	Miles per gallon
	 * @param	value	Value of the vehicle
	 * @param	mpy	Miles per year
	 * @throws Exception 
	 */
	public UserTaxInfoDTO addVehicle(Long userId, Float mpg, Float value, Float mpy) throws Exception {
		User user = this.fundingDAO.getUserById(userId);

		Vehicle v = new Vehicle();
		v.setApproxValue(value);
		v.setMilesPerGallon(mpg);
		v.setMilesPerYear(mpy);
		
		user.getVehicles().add(v);
		this.fundingDAO.save(user);
		this.fundingDAO.save(v);
		
		return this.createUserTaxInfoDTO(userId);
	}

	/**
	 * Updates the vehicle
	 * 
	 * @param	vehicleId	The ID of the vehicle
	 * @param	mpg	Miles per gallon
	 * @param	value	Value of the vehicle
	 * @param	mpy	Miles per year
	 * @throws Exception 
	 */
	public void updateVehicle(Long vehicleId, Float mpg, Float value, Float mpy) throws Exception {
		
		Vehicle v = this.fundingDAO.getVehicle(vehicleId);
		v.setApproxValue(value);
		v.setMilesPerGallon(mpg);
		v.setMilesPerYear(mpy);
		this.fundingDAO.save(v);
	}
	
	/**
	 * Removes the vehicle
	 * 
	 * @param	userId
	 * @param	vehicleId
	 */
	public UserTaxInfoDTO deleteVehicle(Long userId, Long vehicleId) throws Exception {
		User user = this.fundingDAO.getUserById(userId);
		
		Iterator<Vehicle> i = user.getVehicles().iterator();
		Vehicle tempV;
		while(i.hasNext()) {
			tempV = i.next();
			if(tempV.getId().equals(vehicleId)) {
				user.getVehicles().remove(tempV);
				this.fundingDAO.delete(tempV);
				this.fundingDAO.save(user);
				break;
			}
		}
		
		return this.createUserTaxInfoDTO(userId);
	}
	
    /**
     * Relate the given FundingAlternative object to FundingSuite object
     */
    public void relateFundingAlt(Long suiteId, Long altId) throws Exception {
    	FundingSourceSuite suite = fundingDAO.getFundingSuite(suiteId);
    	    	
    	//Check in the suite to see if if there is a Funding the alternative is already related
    	if(!suite.containsAlts(altId)) {
    		
        	//If not then, load the alternative
        	FundingSourceAlternative alternative = fundingDAO.getFundingSourceAlternative(altId);
    		
        	//Relate it back to the Alt Ref 
        	FundingSourceAltRef altRef = new FundingSourceAltRef();
        	altRef.setAlternative(alternative);
        	fundingDAO.save(altRef);
    		
           	//Pull the funding ID from the alternative
        	FundingSource funding = alternative.getSource();
        	
        	//Get the funding reference
        	FundingSourceRef fundingReference = suite.getFundingSourceReference(funding);
        	
        	//If the reference doesn't exist then create it and add the funding
        	if(fundingReference == null) {
        		fundingReference = new FundingSourceRef();
        		fundingReference.setSuite(suite);
        		fundingReference.setSource(funding);
        		
            	//Save the funding reference
            	fundingDAO.save(fundingReference);
            	
            	//Add it to the suite
            	suite.getReferences().add(fundingReference);
            	
            	//Save the suite (thus connecting the suite and funding ref together
            	fundingDAO.save(suite);       		
        	}
        	
        	//Now put the altRef into the funding ref
        	fundingReference.getAltRefs().add(altRef);
        	
        	//Save the funding reference
        	fundingDAO.save(fundingReference);
        	    		
    	}    	
    }//relateFundingAlt()


    /**
     * Derelates the given FundingSourceAlternative object to FundingSourceSuite object
     * 
     * @param	suiteId	The id of the suite to remove the reference from
     * @param	altId	The alternative ID that tells of the funding alternative to remove
     */
    public void derelateFundingAlt(Long suiteId, Long altId) throws Exception {
    	//Get a reference to the suite
    	FundingSourceSuite suite = fundingDAO.getFundingSuite(suiteId);
    	
    	if(suite == null) throw new UnknownFundingSuiteException("Unknown FundingSource Suite [" + suiteId +"]");
    	    	
    	FundingSourceAlternative fundingAlt = fundingDAO.getFundingSourceAlternative(altId);

    	//Get the funding reference that has this alternative reference in it
    	FundingSourceRef fundingRef = suite.getFundingSourceReference(fundingAlt);    	
    	FundingSourceAltRef altRef = fundingRef.getFundingSourceAltRef(fundingAlt);
    	    	
    	if(fundingRef != null) {
    		//Remove the reference to the alt ref
    		fundingRef.removeAltRef(altRef);
    		
        	//Delete the funding alterative reference provided
    		fundingDAO.delete(altRef);
        	
        	//If that was the last alternative in that funding reference then delete the funding reference
    		if(fundingRef.getNumAltRefs() <= 0) {
    			fundingDAO.delete(fundingRef);
    		} else {
    			fundingDAO.save(fundingRef);
    		}
    	}    	
    }//derelateFundingAlt()
    

    /*
     * ------------------------------------------------------------------------
     */
    
    
    public FundingSourceAlternative getFundingSourceAltById(Long id) throws Exception {
        return fundingDAO.getFundingSourceAltById(id);
    }//getFundingSourceAltById()


    public FundingSource getFundingSourceById(Long id) throws Exception {
        return fundingDAO.getFundingSourceById(id);
    }//getFundingSourceById()
    
    
    public Collection getFundingSources() throws Exception {
        return fundingDAO.getFundingSources();
    }//getFundingSources()


    public FundingSource createFundingSource(String name, int type) throws Exception {
        /*
         * Check if the same name exists.
         */
        FundingSource funding = fundingDAO.getFundingSourceByName(name);
        if (funding!=null) throw new Exception("another funding source with the same name already exists");
        
        /*
         * Name available, create the funding source
         */
        
        funding = new FundingSource();
        funding.setName(name);
        funding.setType(type);
        
        fundingDAO.save(funding);
        
        return funding;
    }//createFundingSource()


    public void editFundingSource(Long id, String name, int type) throws Exception {
        /*
         * Retrieve the funding source
         */
        FundingSource funding = fundingDAO.getFundingSourceById(id);
        if (funding==null) throw new Exception("can't find this funding source");
        
        funding.setName(name);
        funding.setType(type);
        
        fundingDAO.save(funding);
    }//editFundingSource()


    public void deleteFundingSource(Long id) throws Exception {
    	FundingSource source = fundingDAO.getFundingSourceById(id);
    	fundingDAO.delete(source);
    }//deleteFundingSource()


    public FundingSourceAlternative createFundingSourceAlt(Long id, String name, float revenue, float taxRate, String source, float avgCost, boolean toll, float peakHourTrips, float offPeakTrips) throws Exception {
    	FundingSource funSource = fundingDAO.getFundingSourceById(id);
    	
    	FundingSourceAlternative alternative = new FundingSourceAlternative();
    	alternative.setName(name);
    	alternative.setRevenue(revenue);
    	alternative.setTaxRate(taxRate);
    	alternative.setSourceURL(source);
    	alternative.setAvgCost(avgCost);
    	alternative.setToll(toll);
    	alternative.setPeakHourTripsRate(peakHourTrips);
    	alternative.setOffPeakTripsRate(offPeakTrips);
    	
    	fundingDAO.save(alternative);
    	
    	funSource.getAlternatives().add(alternative);
    	
    	fundingDAO.save(funSource);
    	
        return alternative;
    }//createFundingSourceAlt()


    public void editFundingSourceAlt(Long id, String name, float revenue, float taxRate, String source, float avgCost, boolean toll, float peakHourTrips, float offPeakTrips) throws Exception {
   	
    	FundingSourceAlternative alternative = fundingDAO.getFundingSourceAltById(id);
    	alternative.setName(name);

    	alternative.setRevenue(revenue);
    	alternative.setTaxRate(taxRate);
    	alternative.setSourceURL(source);
    	alternative.setAvgCost(avgCost);
    	alternative.setToll(toll);
    	alternative.setPeakHourTripsRate(peakHourTrips);
    	alternative.setOffPeakTripsRate(offPeakTrips);
    	
    	fundingDAO.save(alternative);
    }//editFundingSourceAlt()


    public void deleteFundingSourceAlt(Long id) throws Exception {
    	
    	FundingSourceAlternative alternative = fundingDAO.getFundingSourceAltById(id);
    	fundingDAO.delete(alternative);

    	
    	
    }//deleteFundingSourceAlt()


    /**
     * Setup the association between funding sources and CCT.
     * 
     * @param cctId id of the CCT object to be associated
     * @param ids ids of FundingSource objects to be assosicated
     * @throws Exception
     */
    public void setupFundingSourcesForCCT(Long cctId, String[] ids) throws Exception {
        /*
         * TODO:
         *   Load CCT object by cctId, throw exception if failed
         *   Load each ProjectAlternative object by id, throw exception if any failed
         *   put each ProjectAlternative object to CCT.projects
         *   persist objects
         */
    }//setupFundingSourcesForCCT()


	public FundingSourceSuite getFundingSuite(Long suiteId) throws Exception {
		return fundingDAO.getFundingSuite(suiteId);
	}
	
    
    public FundingSourceSuite createFundingSourceSuite() throws Exception {
        FundingSourceSuite suite = new FundingSourceSuite();
        
        fundingDAO.save(suite);
        
        return suite;
    }//createFundingSourceSuite()

    
	/**
	 * Copys the toll info from the remote toll to the users commute toll and then saves the
	 * new information
	 */
	private void copyAcrossTolls(List<UserFundingSourceTollDTO> remoteTolls, UserCommute commute) throws Exception {
		Iterator<UserFundingSourceTollDTO> away = remoteTolls.iterator();
		Iterator<UserFundingSourceToll> home = commute.getTolls().iterator();
		UserFundingSourceTollDTO tollAway;
		UserFundingSourceToll tollHome;
		
		while(away.hasNext()) {
			tollAway = away.next();

			//Search the local tolls for the match
			tollHome = findToll(tollAway.getId(), commute.getTolls());
			
			if(tollHome == null) {
				//Add the new toll
				tollHome = new UserFundingSourceToll();
				tollAway.loadTollFromThis(tollHome);
				try {
					linkFundingSource(tollHome);
				} catch (UnknownFundingSourceException e) {
					System.out.println(e.getMessage());
				}				
				commute.getTolls().add(tollHome);
				this.fundingDAO.save(tollHome);
				this.fundingDAO.save(commute);
			}
			tollHome.setUsed(tollAway.isUsed());
			tollHome.setPeakTrips(tollAway.getPeakTrips());
			tollHome.setOffPeakTrips(tollAway.getOffPeakTrips());
			
			this.fundingDAO.save(tollHome);
		}
	}
	
	/**
	 * Links the provided toll to the correct funding source with the exact same name
	 * <p>
	 * NOTE: It does not save the toll
	 * 
	 * @param	toll 	to link
	 * @throws 	UnknownFundingSourceException if there is no funding source that matches the name
	 * 			of the toll
	 */
	private void linkFundingSource(UserFundingSourceToll toll) throws UnknownFundingSourceException, Exception {
		FundingSource source = this.fundingDAO.getFundingSourceByName(toll.getName());
		if(source == null) {
			throw new UnknownFundingSourceException("Could not find the FundingSource[" +toll.getName()+"] to link to the toll");
		}
		toll.setFundingSource(source);
	}
	
	/**
	 * Returns the toll from the set that matches the specified id
	 */
	private UserFundingSourceToll findToll(Long id, Set<UserFundingSourceToll> tolls) {
		Iterator<UserFundingSourceToll> i = tolls.iterator();
		UserFundingSourceToll toll;
		while(i.hasNext()) {
			toll = i.next();
			if(toll.getId().equals(id)) {
				return toll;
			}
		}
		return null;
	}
	
	/**
	 * Sets the trip rates for the toll
	 * 
	 * @param	toll	The funding toll source
	 */
	private void setTripRates(UserFundingSourceToll toll, ZipCodeFactor zcf, float carFactor, int driveAlone, int carpool, int numPassengers) {
//System.out.println("MATT: Calc toll for " + toll.getName());		
		try {
			if(toll.getName().equals(UserFundingSourceToll.PARKING_DOWNTOWN)) {
				toll.setPeakTrips(TaxCalcUtils.calcPeakHours(zcf.getParking(), carFactor, driveAlone, carpool, numPassengers, toll.isUsed()));
				toll.setOffPeakTrips(TaxCalcUtils.calcOffPeakHours(zcf.getParking(), carFactor, toll.isUsed()));
			} else if(toll.getName().equals(UserFundingSourceToll.ALASKA_WAY_VIADUCT)) {
				toll.setPeakTrips(TaxCalcUtils.calcPeakHours(zcf.getSR99(), carFactor, driveAlone, carpool, numPassengers, toll.isUsed()));
				toll.setOffPeakTrips(TaxCalcUtils.calcOffPeakHours(zcf.getSR99(), carFactor, toll.isUsed()));
			} else if(toll.getName().equals(UserFundingSourceToll.I405N)) {
				toll.setPeakTrips(TaxCalcUtils.calcPeakHours(zcf.getI405N(), carFactor, driveAlone, carpool, numPassengers, toll.isUsed()));
				toll.setOffPeakTrips(TaxCalcUtils.calcOffPeakHours(zcf.getI405N(), carFactor, toll.isUsed()));
			} else if(toll.getName().equals(UserFundingSourceToll.I405S)) {
				toll.setPeakTrips(TaxCalcUtils.calcPeakHours(zcf.getI405S(), carFactor, driveAlone, carpool, numPassengers, toll.isUsed()));
				toll.setOffPeakTrips(TaxCalcUtils.calcOffPeakHours(zcf.getI405S(), carFactor, toll.isUsed()));
			} else if(toll.getName().equals(UserFundingSourceToll.SR520)) {
				toll.setPeakTrips(TaxCalcUtils.calcPeakHours(zcf.getSR520(), carFactor, driveAlone, carpool, numPassengers, toll.isUsed()));
				toll.setOffPeakTrips(TaxCalcUtils.calcOffPeakHours(zcf.getSR520(), carFactor, toll.isUsed()));
			} else if(toll.getName().equals(UserFundingSourceToll.I90)) {
				toll.setPeakTrips(TaxCalcUtils.calcPeakHours(zcf.getI90(), carFactor, driveAlone, carpool, numPassengers, toll.isUsed()));
				toll.setOffPeakTrips(TaxCalcUtils.calcOffPeakHours(zcf.getI90(), carFactor, toll.isUsed()));
			} else if(toll.getName().equals(UserFundingSourceToll.SR167)) {
				toll.setPeakTrips(TaxCalcUtils.calcPeakHours(zcf.getSR167(), carFactor, driveAlone, carpool, numPassengers, toll.isUsed()));
				toll.setOffPeakTrips(TaxCalcUtils.calcOffPeakHours(zcf.getSR167(), carFactor, toll.isUsed()));
			}			
		} catch (NullPointerException e) {
			throw new RuntimeException("Could not calculate user data, because you probibly need to load the tax data");
		}
	}
		
    
    public InfoStructure publish(Long cctId, Long suiteId) throws Exception {
        CCT cct = cctDAO.getCCTById(cctId);
        
        FundingSourceSuite suite = fundingDAO.getFundingSuite(suiteId);
        
        Date date = new Date();
        
        InfoStructure structure = new InfoStructure();
        structure.setType("sdf");
        structure.setTitle("Step 3b: Review Fundings.");
        structure.setRespTime(date);
        structure.setCctId(cct.getId());
        discussionDAO.save(structure);
        
        for (FundingSourceRef ref : suite.getReferences()) {
            ref.getId();
            ref.getAltRefs();
            ref.getNumAltRefs();
            ref.getSource();
            ref.getSuite();
            
            InfoObject obj = new InfoObject();
            obj.setObject(ref);
            obj.setRespTime(date);
            discussionDAO.save(obj);
            
            structure.getInfoObjects().add(obj);
        }//for ref
        
        discussionDAO.save(structure);
        
        return structure;
    }//publish()
    

}//class FundingServiceImpl
