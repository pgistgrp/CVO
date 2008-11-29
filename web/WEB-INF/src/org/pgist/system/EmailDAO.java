package org.pgist.system;

import java.util.List;


/**
 * 
 * @author kenny
 */
public interface EmailDAO extends BaseDAO {
    
    
    EmailTemplate getTemplateByName(String name) throws Exception;
    
    
    List getTemplates() throws Exception;
    
    
    void saveTemplate(EmailTemplate template) throws Exception;
    

    EmailTemplate getTemplateById(Long id) throws Exception;


    List<EmailNotification> getEmailNotifications() throws Exception;


    void clearEmailNotifications() throws Exception;
    
    
}//interface EmailDAO
