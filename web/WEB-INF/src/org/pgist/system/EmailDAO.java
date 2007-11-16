package org.pgist.system;

import java.util.List;


/**
 * 
 * @author kenny
 */
public interface EmailDAO {
    
    
    EmailTemplate getTemplateByName(String name) throws Exception;
    
    
    List getTemplates() throws Exception;
    
    
    void saveTemplate(EmailTemplate template) throws Exception;
    

    EmailTemplate getTemplateById(Long id) throws Exception;
    
    
}//interface EmailDAO
