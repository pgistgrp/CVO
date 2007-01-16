package org.pgist.funding;

import java.util.Collection;

import org.pgist.system.BaseDAO;


/**
 * 
 * @author kenny
 *
 */
public interface FundingDAO extends BaseDAO {
    
    
    FundingSource getFundingSourceById(Long id) throws Exception;
    
    
    FundingSourceAlternative getFundingSourceAltById(Long id) throws Exception;
    
    
    FundingSource getFundingSourceByName(String name) throws Exception;


    Collection getFundingSources() throws Exception;
    
    
}//interface FundingDAO
