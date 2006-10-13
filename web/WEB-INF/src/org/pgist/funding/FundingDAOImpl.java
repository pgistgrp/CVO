package org.pgist.funding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.Query;
import org.pgist.system.BaseDAOImpl;
import org.pgist.util.PageSetting;


/**
 * 
 * @author kenny
 *
 */
public class FundingDAOImpl extends BaseDAOImpl implements FundingDAO {
    
    
    public Collection getFundingSources() throws Exception {
        return null;
    }//getFundingSources()


    private static final String hql_getFundingSources_1 = "select count(fs.id) from FundingSource fs";
    
    private static final String hql_getFundingSources_2 = "from FundingSource fs order by fs.id";
    
    
    public Collection getFundingSources(PageSetting setting) throws Exception {
        Query query = getSession().createQuery(hql_getFundingSources_1);
        
        int count = ((Number) query.uniqueResult()).intValue();
        
        if (setting.getRowOfPage()==-1) setting.setRowOfPage(count);
        
        setting.setRowSize(count);
        
        if (count==0) return new ArrayList();
        
        query = getSession().createQuery(hql_getFundingSources_2);
        query.setFirstResult(setting.getFirstRow());
        query.setMaxResults(setting.getRowOfPage());
        
        return query.list();
    }//getFundingSources()


    public FundingSource getFundingSourceById(Long id) throws Exception {
        return (FundingSource) getHibernateTemplate().load(FundingSource.class, id);
    }//getFundingSourceById()


    private static final String hql_getFundingSourceByName = "from FundingSource fs where lower(fs.name)=?";
    
    
    public FundingSource getFundingSourceByName(String name) throws Exception {
        List list = getHibernateTemplate().find(hql_getFundingSourceByName, name);
        
        if (list.size()==0) return null;
        
        return (FundingSource) list.get(0);
    }//getFundingSourceByName()


}//class FundingDAOImpl
