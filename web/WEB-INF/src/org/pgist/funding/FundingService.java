package org.pgist.funding;

import java.util.Collection;

import org.pgist.users.User;
import org.pgist.users.UserInfo;


/**
 * 
 * @author kenny
 *
 */
public interface FundingService {
	
	User getUser(UserInfo userInfo) throws Exception;
    
	FundingSourceSuite getFundingSuite(Long suiteId) throws Exception;
	
	
    FundingSource getFundingSourceById(Long id) throws Exception;
    
    
    FundingSourceAlternative getFundingSourceAltById(Long id) throws Exception;
    
    
    Collection getFundingSources() throws Exception;


    FundingSource createFundingSource(String name, int type) throws Exception;


    void editFundingSource(Long id, String name, int type) throws Exception;


    FundingSourceAlternative createFundingSourceAlt(Long id, String name, float revenue, float taxRate, String source, float avgCost, boolean toll, float peakHourTrips, float offPeakTrips) throws Exception;


    void editFundingSourceAlt(Long id, String name, float revenue, float taxRate, String source, float avgCost, boolean toll, float peakHourTrips, float offPeakTrips) throws Exception;


    void deleteFundingSource(Long id) throws Exception;
    
    
    void deleteFundingSourceAlt(Long id) throws Exception;

    
    void relateFundingAlt(Long suiteId, Long altId) throws Exception, UnknownFundingSuite;    
    
    
	void derelateFundingAlt(Long suiteId, Long altId) throws Exception, UnknownFundingSuite;
	
	
    void setupFundingSourcesForCCT(Long cctId, String[] ids) throws Exception;

	UserTaxInfoDTO addVehicle(Long userId, Float mpg, Float value, Float mpy) throws Exception;
	void updateVehicle(Long vehicleId, Float mpg, Float value, Float mpy) throws Exception;
	UserTaxInfoDTO deleteVehicle(Long userId, Long vehicleId) throws Exception;

	UserTaxInfoDTO createCommute(UserTaxInfoDTO user) throws InvalidZipcodeException, Exception;
    
    FundingSourceSuite createFundingSourceSuite() throws Exception;

    User updateUserTaxInfo(UserTaxInfoDTO user) throws Exception;

	UserTaxInfoDTO lookupUserTaxInfoDTO(Long userId) throws Exception;

	UserTaxInfoDTO calcCostReport(UserTaxInfoDTO user, Long fundingSuiteId) throws Exception;

}//interface FundingService
