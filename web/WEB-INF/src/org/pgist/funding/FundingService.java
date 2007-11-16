package org.pgist.funding;

import java.util.Collection;

import org.pgist.util.PageSetting;


/**
 * 
 * @author kenny
 *
 */
public interface FundingService {
    
    
    Collection getFundingSources(PageSetting setting) throws Exception;
    
    
    FundingSource createFundingSource(String name) throws Exception;


    void editFundingSource(Long id, String name) throws Exception;
    
    
}//interface FundingService
