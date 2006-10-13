package org.pgist.funding;

import java.util.Collection;

import org.pgist.system.BaseDAO;
import org.pgist.util.PageSetting;


/**
 * 
 * @author kenny
 *
 */
public interface FundingDAO extends BaseDAO {
    
    
    Collection getFundingSources(PageSetting setting) throws Exception;
    
    
    FundingSource getFundingSourceById(Long id) throws Exception;
    
    
    FundingSource getFundingSourceByName(String name) throws Exception;


    Collection getFundingSources() throws Exception;
    
    
}//interface FundingDAO
