package org.pgist.other;

import java.util.Collection;

import org.hibernate.Query;
import org.pgist.system.BaseDAOImpl;


/**
 * 
 * @author kenny
 *
 */
public class ImportDAOImpl extends BaseDAOImpl implements ImportDAO {
    
    
    private static final String hql_getTemplates = "from SituationTemplate";
    
    
    public Collection getTemplates() throws Exception {
        return getHibernateTemplate().find(hql_getTemplates);
    }//getTemplates()


    public SituationTemplate getTemplateById(Long templateId) throws Exception {
        return (SituationTemplate) getHibernateTemplate().load(SituationTemplate.class, templateId);
    }//getTemplateById()
    
    
    private static final String hql_getExperimentByWorkflowId = "from Experiment e where e.workflowId=?";
    
    
    public Experiment getExperimentByWorkflowId(Long workflowId) throws Exception {
        Query query = getSession().createQuery(hql_getExperimentByWorkflowId);
        query.setLong(0, workflowId);
        query.setMaxResults(1);
        
        return (Experiment) query.uniqueResult();
    }//getExperimentByWorkflowId()
    
    
}//class ImportDAOImpl
