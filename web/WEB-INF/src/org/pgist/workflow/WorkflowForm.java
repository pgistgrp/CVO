package org.pgist.workflow;

import java.util.Collection;

import org.apache.struts.action.ActionForm;
import org.pgist.wfengine.Template;


/**
 * 
 * @author kenny
 *
 */
public class WorkflowForm extends ActionForm {

    
    private static final long serialVersionUID = 5443268954560626922L;
    
    private Collection templateList;
    
    private Collection situationList;
    
    private Template template;
    
    
    public Template getTemplate() {
        return template;
    }
    
    
    public void setTemplate(Template template) {
        this.template = template;
    }
    
    
    public Collection getTemplateList() {
        return templateList;
    }
    
    
    public void setTemplateList(Collection templateList) {
        this.templateList = templateList;
    }


    public Collection getSituationList() {
        return situationList;
    }


    public void setSituationList(Collection situationList) {
        this.situationList = situationList;
    }
    
    
}
