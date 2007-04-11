package org.pgist.funding;

import java.util.Collection;

import org.pgist.system.BaseDAO;
import org.pgist.users.Vehicle;


/**
 * 
 * @author kenny
 *
 */
public interface FundingDAO extends BaseDAO {
    
    Collection getFundingSources() throws Exception;
    FundingSource getFundingSourceByName(String name) throws Exception;

	FundingSourceSuite getFundingSuite(Long suiteID) throws Exception;    
    
    void save(FundingSource source);
	void delete(FundingSource source);
    FundingSource getFundingSourceById(Long id) throws Exception;
	
    void save(FundingSourceAlternative alt);
    void delete(FundingSourceAlternative alt);
    FundingSourceAlternative getFundingSourceAltById(Long id) throws Exception;
	FundingSourceAlternative getFundingSourceAlternative(Long altId);
	void delete(FundingSourceAltRef altRef);
	FundingSourceAltRef getFundingSourceAlternativeReference(Long altId);
	void delete(FundingSourceRef fundingRef);
	
	
    public ZipCodeGas getZipCodeGasByZipCode(String zipcode) throws Exception;
	public void save(ZipCodeGas zcg) throws Exception;
	public ZipCodeFactor getZipCodeFactorByZipCode(String zipcode) throws Exception;    
	public void save(ZipCodeFactor zcg) throws Exception;
    public Consumption getConsumptionByIncome(Float incomeLevel) throws Exception;
	public void save(Consumption zcg) throws Exception;	
    
    void save(Vehicle v) throws Exception;
    void delete(Vehicle v) throws Exception;
    Vehicle getVehicle(Long vehicleId) throws Exception;
	UserCommute getCommuteForUser(Long userId) throws Exception;
	
}//interface FundingDAO
