package org.pgist.funding;

import java.util.Collection;

import org.pgist.system.BaseDAO;


/**
 * 
 * @author kenny
 *
 */
public interface FundingDAO extends BaseDAO {
    
    Collection getFundingSources() throws Exception;
    FundingSource getFundingSourceByName(String name) throws Exception;

    void save(FundingSource source);
	void delete(FundingSource source);
    FundingSource getFundingSourceById(Long id) throws Exception;
	
    void save(FundingSourceAlternative alt);
    void delete(FundingSourceAlternative alt);
    FundingSourceAlternative getFundingSourceAltById(Long id) throws Exception;
    
}//interface FundingDAO
