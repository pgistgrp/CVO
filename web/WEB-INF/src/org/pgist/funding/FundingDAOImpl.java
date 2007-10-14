package org.pgist.funding;

import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.pgist.system.BaseDAOImpl;
import org.pgist.users.User;
import org.pgist.users.Vehicle;


/**
 * 
 * @author kenny
 *
 */
public class FundingDAOImpl extends BaseDAOImpl implements FundingDAO {
    
	/**
	 * Initializes the user with all of the necessary tolls and the user commute
	 */
	public void initializeUser(User user) throws Exception {
    	if(user.getUserCommute() == null) {
    		UserCommute commute = new UserCommute();    		
    		user.setUserCommute(commute);
    		save(commute);
    		save(user);       		
    	}   
    	if(user.getUserCommute().getTolls().size() == 0) {
    		//Add the tolls
    		user.getUserCommute().setTolls(createUserTolls());
    		
    		save(user.getUserCommute());    		
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
        tolls.add(createToll(UserFundingSourceToll.I5N));
        tolls.add(createToll(UserFundingSourceToll.I5S));
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
			e.printStackTrace();
		}
    	
    	return toll;
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
	public void linkFundingSource(UserFundingSourceToll toll) throws UnknownFundingSourceException, Exception {
	    System.out.println("-------------> "+toll.getName());
		FundingSource source = getFundingSourceByName(toll.getName());
		if(source == null) {
			throw new UnknownFundingSourceException("Could not find the FundingSource[" +toll.getName()+"] to link to the toll");
		}
		toll.setFundingSource(source);
	}    
	
	public FundingSource getFundingSourceById(Long id) throws Exception {
        return (FundingSource) getHibernateTemplate().load(FundingSource.class, id);
    }//getFundingSourceById()

	private static final String hql_getFundingSourceByName = "from FundingSource fs where fs.name=?";
    
    
    public FundingSource getFundingSourceByName(String name) throws Exception {
        List list = getHibernateTemplate().find(hql_getFundingSourceByName, name);
        
        if (list.size()==0) return null;
        
        return (FundingSource) list.get(0);
    }//getFundingSourceByName()


    public Collection getFundingSources() throws Exception {
    	return getHibernateTemplate().find("from FundingSource fs order by fs.name asc");
    }//getFundingSources()

	public void save(FundingSource source) {
		getHibernateTemplate().saveOrUpdate(source);		
	}

	public void delete(FundingSource source) {
        getHibernateTemplate().delete(source);		
	}
    
    public FundingSourceAlternative getFundingSourceAltById(Long id) throws Exception {
        return (FundingSourceAlternative) getHibernateTemplate().load(FundingSourceAlternative.class, id);
    }//getFundingSourceAltById()
    
	public void save(FundingSourceAlternative alt) {
		getHibernateTemplate().saveOrUpdate(alt);				
	}

	public void delete(FundingSourceAlternative alt) {
        getHibernateTemplate().delete(alt);		
	}
    
	
	
    // ************* Funding Suite ******************************** 
    
	public void delete(FundingSourceAltRef altRef) {
		getHibernateTemplate().delete(altRef);
	}


	public void delete(FundingSourceRef fundingRef) {
        getHibernateTemplate().delete(fundingRef);		
	}


	public FundingSourceAlternative getFundingSourceAlternative(Long altId) {
		return (FundingSourceAlternative)getHibernateTemplate().load(FundingSourceAlternative.class, altId);
	}


	public FundingSourceAltRef getFundingSourceAlternativeReference(Long altId) {
		return (FundingSourceAltRef)getHibernateTemplate().get(FundingSourceAltRef.class, altId);		
	}

	public FundingSourceSuite getFundingSuite(Long suiteID) throws Exception {
		return (FundingSourceSuite)getHibernateTemplate().load(FundingSourceSuite.class, suiteID);
	}	
	

    // ************* Tax Calculator ********************************
	private static final float DEFAULT_GAS_PRICE = 3.00f; 
    private static final String hql_getZipCodeGasByZipCode = "from ZipCodeGas zcg where zcg.zipcode=?";
    public ZipCodeGas getZipCodeGasByZipCode(String zipcode) throws Exception {
        List list = getHibernateTemplate().find(hql_getZipCodeGasByZipCode, zipcode);
        
        if (list.size()==0) {
        	ZipCodeGas zcg = new ZipCodeGas();
        	zcg.setAvgGas(DEFAULT_GAS_PRICE);
        	return zcg;
        }
        
        return (ZipCodeGas) list.get(0);
    }//getFundingSourceAltById()
    
	public void save(ZipCodeGas zcg) throws Exception {
		getHibernateTemplate().saveOrUpdate(zcg);				
	}
	
    private static final String hql_getZipCodeFactorByZipCode = "from ZipCodeFactor zcg where zcg.zipcode=?";
    public ZipCodeFactor getZipCodeFactorByZipCode(String zipcode) throws Exception {
        List list = getHibernateTemplate().find(hql_getZipCodeFactorByZipCode, zipcode);
        
        if (list.size()==0) return null;
        
        return (ZipCodeFactor) list.get(0);
    }//getFundingSourceAltById()
    
	public void save(ZipCodeFactor zcg) throws Exception {
		getHibernateTemplate().saveOrUpdate(zcg);				
	}


    private static final String hql_getConsumptionByIncomeLevel = "from Consumption zcg where ? between zcg.incomeLower and zcg.incomeUpper";
//    private static final String hql_getConsumptionByIncomeLevel = "from Consumption zcg where zcg.incomeLower <? and zcg.incomeUpper > ?";
    public Consumption getConsumptionByIncome(Float incomeLevel) throws Exception {
    	//To get rid of the lower bounds
    	if(incomeLevel <= 0) incomeLevel = 1f;
    	if(incomeLevel >= 1000000) incomeLevel = 1000000f;
    	List list = getHibernateTemplate().find(hql_getConsumptionByIncomeLevel, new Object[] {incomeLevel});
        
        if (list.size()==0) return null;
        
        return (Consumption) list.get(0);
    }//getFundingSourceAltById()
    
	public void save(Consumption zcg) throws Exception {
		getHibernateTemplate().saveOrUpdate(zcg);				
	}
	
    public void save(Vehicle v) throws Exception {
    	getHibernateTemplate().saveOrUpdate(v);
    }
    public void delete(Vehicle v) throws Exception {
    	getHibernateTemplate().delete(v);
    }
    public Vehicle getVehicle(Long vehicleId) throws Exception {
		return (Vehicle)getHibernateTemplate().get(Vehicle.class, vehicleId);		    	
    }
    
    
    private static final String hql_getFundingSourceAlternativeByName = "from FundingSourceAlternative where name=?";
    
    
    public FundingSourceAlternative getFundingSourceAlternativeByName(String fundAltName) throws Exception {
        List list = getHibernateTemplate().find(hql_getFundingSourceAlternativeByName, fundAltName);
        
        if (list.size()==0) return null;
        
        return (FundingSourceAlternative) list.get(0);
    }//getFundingSourceAlternativeByName()
    
    
}//class FundingDAOImpl
