package org.pgist.workflow;

import java.util.Collection;
import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


/**
 * 
 * @author kenny
 *
 */
public class WorkflowDAOImpl extends HibernateDaoSupport implements WorkflowDAO {
    
    
    private static final String hql_getSituations = "from DecisionSituation ds where deleted=?";
    
    
    public Collection getSituations() throws Exception {
        return getHibernateTemplate().find(hql_getSituations, new Boolean(false));
    }//getSituations()
    
    
    public void saveSituation(DecisionSituation situation) throws Exception {
        getHibernateTemplate().saveOrUpdate(situation);
    }//saveSituation()


    private static final String hql_getSituationById = "from DecisionSituation ds where id=? and deleted=?";
    
    
    public DecisionSituation getSituationById(Long situationId) throws Exception {
        List list = getHibernateTemplate().find(hql_getSituationById, new Object[] {
                situationId,
                new Boolean(false),
        });
        if (list.size()>0) return (DecisionSituation) list.get(0);
        return null;
    }//getSituationById()
    
    
}//class WorkflowDAOImpl
