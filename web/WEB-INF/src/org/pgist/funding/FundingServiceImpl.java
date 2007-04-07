package org.pgist.funding;

import java.util.Collection;
import java.util.Iterator;

import org.pgist.projects.ProjectAltRef;
import org.pgist.projects.ProjectAlternative;
import org.pgist.projects.ProjectRef;
import org.pgist.projects.ProjectSuite;
import org.pgist.projects.UnknownProjectSuite;
import org.pgist.users.User;
import org.pgist.users.UserInfo;
import org.pgist.users.Vehicle;


/**
 * 
 * @author kenny
 *
 */
public class FundingServiceImpl implements FundingService {
    
    
    private FundingDAO fundingDAO;
    
    
    public void setFundingDAO(FundingDAO fundingDAO) {
        this.fundingDAO = fundingDAO;
    }

	public User getUser(UserInfo userInfo) throws Exception {
		return this.fundingDAO.getUserById(userInfo.getId());
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
	public User addVehicle(Long userId, Float mpg, Float value, Float mpy) throws Exception {
		User user = this.fundingDAO.getUserById(userId);

		Vehicle v = new Vehicle();
		v.setApproxValue(value);
		v.setMilesPerGallon(mpg);
		v.setMilesPerYear(mpy);
		
		user.getVehicles().add(v);
		this.fundingDAO.save(user);
		this.fundingDAO.save(v);
		
		return user;
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
	public User removeVehicle(Long userId, Long vehicleId) throws Exception {
		User user = this.fundingDAO.getUserById(userId);
		
		Iterator<Vehicle> i = user.getVehicles().iterator();
		Vehicle tempV;
		while(i.hasNext()) {
			tempV = i.next();
			if(tempV.getId().equals(vehicleId)) {
				user.getVehicles().remove(tempV);
				this.fundingDAO.delete(tempV);
				this.fundingDAO.save(user);
				return user;
			}
		}
		
		return user;
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
    	
    	if(suite == null) throw new UnknownFundingSuite("Unknown FundingSource Suite [" + suiteId +"]");
    	    	
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


    public Collection getAllTolls() throws Exception {
        /*
         * TODO:
         */
        return null;
    }//getAllTolls()

	public FundingSourceSuite getFundingSuite(Long suiteId) throws Exception {
		return fundingDAO.getFundingSuite(suiteId);
	}

}//class FundingServiceImpl
