package org.pgist.funding;

import java.util.Collection;

import org.pgist.projects.ProjectSuite;
import org.pgist.system.BaseDAO;


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
    
}//interface FundingDAO
