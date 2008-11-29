package org.pgist.system;

import java.util.List;

import org.hibernate.Query;


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


    @Override
    public void clearEmailNotifications() throws Exception {
        Query query = query = getSession().createQuery("delete EmailNotification where flag=?");
        query.setBoolean(0, true);
        query.executeUpdate();
    } //clearEmailNotifications()


    @Override
    public List<EmailNotification> getEmailNotifications() throws Exception {
        Query query = getSession().createQuery("update EmailNotification e set e.flag=?");
        query.setBoolean(0, true);
        query.executeUpdate();
        
        return getHibernateTemplate().find("from EmailNotification e where e.flag=? order by e.user.id", true);
    }
    
    
}//class EmailDAOImpl
