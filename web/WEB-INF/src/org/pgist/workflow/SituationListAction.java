package org.pgist.workflow;

import java.util.Collection;

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
public class SituationListAction extends Action {

    
    private WorkflowDAO workflowDAO;
    
    private WorkflowEngine engine;
    
    
    public SituationListAction() {
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
        
        Collection list = workflowDAO.getSituations();
        wfform.setSituationList(list);
        
        return mapping.findForward("list");
    }//execute()


}
