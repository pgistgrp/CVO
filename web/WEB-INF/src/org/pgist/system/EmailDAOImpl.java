package org.pgist.system;

import java.util.List;


/**
 * 
 * @author kenny
 */
public class EmailDAOImpl extends BaseDAOImpl implements EmailDAO {
    
    
    private static String hql_getTemplateList = "from EmailTemplate e order by e.id desc";
    
    
    public List getTemplates() throws Exception {
        List list = null;
        
        list = getHibernateTemplate().find(hql_getTemplateList);
        
        return list;
    }//getTemplates()
    
    
    public void saveTemplate(EmailTemplate template) throws Exception {
        getHibernateTemplate().saveOrUpdate(template);
    }//updateTemplate()


    public EmailTemplate getTemplateByName(String name) throws Exception {
        EmailTemplate template = null;
        
        List list = getHibernateTemplate().find("from EmailTemplate where name=?", name);
        if (list.size()>0) {
            template = (EmailTemplate) list.get(0);
        }
        
        return template;
    }//getTemplateByName()


    public EmailTemplate getTemplateById(Long id) throws Exception {
        return (EmailTemplate) getHibernateTemplate().get(EmailTemplate.class, id);
    }//getTemplateById()
    
    
}//class EmailDAOImpl
