package org.pgist.system;

import java.util.Collection;


/**
 * 
 * @author kenny
 *
 */
public interface EmailService {
    
    
    EmailTemplate getTemplateById(Long id) throws Exception;
    
    
    EmailTemplate getTemplateByName(String name) throws Exception;
    

    void saveTemplate(EmailTemplate template) throws Exception;
    

    Collection getTemplates() throws Exception;
    
    
}//interface EmailService
