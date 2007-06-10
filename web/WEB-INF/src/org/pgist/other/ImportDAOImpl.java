package org.pgist.other;

import java.util.Collection;

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
    
    
}//class ImportDAOImpl
