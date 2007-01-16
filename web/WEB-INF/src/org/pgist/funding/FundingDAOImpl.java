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


    public FundingSourceAlternative getFundingSourceAltById(Long id) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }//getFundingSourceAltById()


    private static final String hql_getFundingSourceByName = "from FundingSource fs where lower(fs.name)=?";
    
    
    public FundingSource getFundingSourceByName(String name) throws Exception {
        List list = getHibernateTemplate().find(hql_getFundingSourceByName, name);
        
        if (list.size()==0) return null;
        
        return (FundingSource) list.get(0);
    }//getFundingSourceByName()


    public Collection getFundingSources() throws Exception {
        /*
         * TODO
         */
        return null;
    }//getFundingSources()


}//class FundingDAOImpl
