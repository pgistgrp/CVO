package org.pgist.system;

import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.Query;
import org.pgist.util.PageSetting;


/**
 * 
 * @author kenny
 *
 */
public class SystemDAOImpl extends BaseDAOImpl implements SystemDAO {
    
    
    private static final String hql_getFeedbacks_1 = "select count(fb.id) from Feedback fb";
    
    private static final String hql_getFeedbacks_2 = "from Feedback fb order by fb.id";
    
    
    public Collection getFeedbacks(PageSetting setting) throws Exception {
        Query query = null;
        
        //get count
        query = getSession().createQuery(hql_getFeedbacks_1);
        
        query.setMaxResults(1);
        
        int count = ((Number) query.uniqueResult()).intValue();
        
        setting.setRowOfPage(count);
        
        if (count==0) return new ArrayList();
        
        //get records
        query = getSession().createQuery(hql_getFeedbacks_2);
        
        query.setFirstResult(setting.getFirstRow());
        query.setMaxResults(count);
        
        return query.list();
    }//getFeedbacks()
    
    
}//class SystemDAOImpl
