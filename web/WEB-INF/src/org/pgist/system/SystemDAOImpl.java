package org.pgist.system;

import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.Query;
import org.pgist.users.User;
import org.pgist.util.PageSetting;
import org.pgist.util.WebUtils;


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

    
    private static final String hql_getVoting = "from YesNoVoting v where v.targetType=? and v.targetId=? and v.owner=?";
    
    
    public YesNoVoting getVoting(int targetType, Long targetId) throws Exception {
        Query query = getSession().createQuery(hql_getVoting);
        
        query.setInteger(0, targetType);
        query.setLong(1, targetId);
        query.setLong(2, WebUtils.currentUserId());
        query.setMaxResults(1);
        
        return (YesNoVoting) query.uniqueResult();
    }//getVoting()


    public boolean setVoting(int targetType, Long targetId, boolean agree) throws Exception {
        YesNoVoting voting = getVoting(targetType, targetId);
        if (voting!=null) return false;
        
        User user = (User) load(User.class, WebUtils.currentUserId());
        
        voting = new YesNoVoting();
        
        voting.setTargetType(targetType);
        voting.setTargetId(targetId);
        voting.setOwner(user);
        voting.setVoting(agree);
        
        save(voting);
        
        return true;
    }//setVoting()
    
    
}//class SystemDAOImpl
