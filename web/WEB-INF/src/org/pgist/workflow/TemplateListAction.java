package org.pgist.workflow;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.wfengine.WorkflowEngine;


/**
 * 
 * @author kenny
 *
 */
public class TemplateListAction extends Action {

    
    private WorkflowDAO workflowDAO;
    
    private WorkflowEngine engine;
    
    
    public TemplateListAction() {
    }
    
    
    public void setWorkflowDAO(WorkflowDAO workflowDAO) {
        this.workflowDAO = workflowDAO;
    }


    public void setEngine(WorkflowEngine engine) {
        this.engine = engine;
    }


    /*
     * ------------------------------------------------------------------------------
     */
    
    
    public ActionForward execute(
            ActionMapping mapping,
            ActionForm form,
            javax.servlet.http.HttpServletRequest request,
            javax.servlet.http.HttpServletResponse response
    ) throws java.lang.Exception {
        WorkflowForm wfform = (WorkflowForm) form;
        
        wfform.setTemplateList(engine.getSituationTemplates());
        
        return mapping.findForward("list");
    }//execute()


}//class TemplateListAction
