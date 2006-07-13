package org.pgist.system;

import java.util.Collection;


/**
 * 
 * @author kenny
 *
 */
public class EmailServiceImpl implements EmailService {
    
    
    private EmailDAO emailDAO;
    
    
    public void setEmailDAO(EmailDAO emailDAO) {
        this.emailDAO = emailDAO;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */


    public EmailTemplate getTemplateById(Long id) throws Exception {
        return emailDAO.getTemplateById(id);
    }//getTemplateById()
    
    
    public EmailTemplate getTemplateByName(String name) throws Exception {
        return emailDAO.getTemplateByName(name);
    }//getTemplateByName()
    
    
    public void saveTemplate(EmailTemplate template) throws Exception {
        if (template.getId()==null) {
            emailDAO.saveTemplate(template);
        } else {
            emailDAO.saveTemplate(template);
        }
    }//saveTemplate()


    public Collection getTemplates() throws Exception {
        return emailDAO.getTemplates();
    }//getTemplates()


}//class EmailServiceImpl
