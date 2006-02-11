package org.pgist.workflow;

import java.util.Collection;

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
    
    
}//class WorkflowDAOImpl
