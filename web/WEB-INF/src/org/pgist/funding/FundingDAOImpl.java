package org.pgist.funding;

import java.util.Collection;
import java.util.List;

import org.pgist.system.BaseDAOImpl;


/**
 * 
 * @author kenny
 *
 */
public class FundingDAOImpl extends BaseDAOImpl implements FundingDAO {
    
	public FundingSource getFundingSourceById(Long id) throws Exception {
        return (FundingSource) getHibernateTemplate().load(FundingSource.class, id);
    }//getFundingSourceById()



    private static final String hql_getFundingSourceByName = "from FundingSource fs where lower(fs.name)=?";
    
    
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
    
	public FundingSourceSuite getFundingSuite(Long suiteID) throws Exception {
		return (FundingSourceSuite)getHibernateTemplate().load(FundingSourceSuite.class, suiteID);
	}	
	
}//class FundingDAOImpl
