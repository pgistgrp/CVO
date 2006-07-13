package org.pgist.ddl;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.pgist.system.EmailTemplate;
import org.pgist.users.Role;


/**
 * 
 * @author kenny
 *
 */
public class EmailTemplateHandler extends Handler {
    
    
    public void doImports(Element root) throws Exception {
        List templates = root.elements("template");
        for (int i=0,n=templates.size(); i<n; i++) {
            Element element = (Element) templates.get(i);
            
            String name = element.attributeValue("name");
            if (name==null || "".equals(name)) throw new Exception("name is required for template");
            
            String description = element.elementTextTrim("description");
            if (description==null) description = "";
            
            String subject = element.elementTextTrim("subject");
            if (subject==null) throw new Exception("subject is required for template");
            
            String notes = element.elementTextTrim("notes");
            if (subject==null) notes = "";
            
            String content = element.elementTextTrim("content");
            if (content==null) throw new Exception("content is required for template");
            
            EmailTemplate template = new EmailTemplate();
            template.setName(name);
            template.setDescription(description);
            template.setNotes(notes);
            template.setSubject(subject);
            template.setContent(content);
            
            persist(template);
        }//for i
    }//imports()
    
    
    public void doExports(Document document) throws Exception {
        Element root = document.addElement("templates");
        
        List<EmailTemplate> templates = getTemplates();
        
        for (EmailTemplate template : templates) {
            Element one = root.addElement("template");
            one.attributeValue("name", template.getName());
            
            one.addElement("description").setText(template.getDescription());
            one.addElement("subject").setText(template.getSubject());
            one.addElement("notes").setText(template.getNotes());
            one.addElement("content").setText(template.getContent());
        }//for
    }//doExports()


}//class RoleHandler
