package org.pgist.funding;

import java.util.Collection;


/**
 * 
 * @author kenny
 *
 */
public interface FundingService {
    
    
    FundingSource getFundingSourceById(Long id) throws Exception;
    
    
    FundingSourceAlternative getFundingSourceAltById(Long id) throws Exception;
    
    
    Collection getFundingSources() throws Exception;


    FundingSource createFundingSource(String name, int type) throws Exception;


    void editFundingSource(Long id, String name, int type) throws Exception;


    FundingSourceAlternative createFundingSourceAlt(Long id, String name, float revenue, float taxRate, String source, float avgCost, boolean toll, float peakHourTrips, float offPeakTrips) throws Exception;


    void editFundingSourceAlt(Long id, String name, float revenue, float taxRate, String source, float avgCost, boolean toll, float peakHourTrips, float offPeakTrips) throws Exception;


    void deleteFundingSource(Long id) throws Exception;
    
    
    void deleteFundingSourceAlt(Long id) throws Exception;

    
    void relateFundingAlt(Long suiteId, Long altId) throws Exception;    
    
    
	void derelateFundingAlt(Long suiteId, Long altId) throws Exception;
	
	
    void setupFundingSourcesForCCT(Long cctId, String[] ids) throws Exception;


    Collection getAllTolls() throws Exception;


}//interface FundingService
