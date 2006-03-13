package org.pgist.workflow.pgame;

import java.util.Iterator;
import java.util.Set;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.wfengine.Activity;
import org.pgist.wfengine.Workflow;
import org.pgist.wfengine.WorkflowEngine;
import org.pgist.wfengine.activity.GroupActivity;
import org.pgist.wfengine.activity.PActActivity;
import org.pgist.workflow.DecisionSituation;
import org.pgist.workflow.WorkflowDAO;
import org.pgist.workflow.WorkflowForm;


/**
 * 
 * @author kenny
 *
 */
public class VotingAction extends Action {
    
    
    private WorkflowEngine engine;
    
    private WorkflowDAO workflowDAO;
    
    
    public void setEngine(WorkflowEngine engine) {
        this.engine = engine;
    }


    public void setWorkflowDAO(WorkflowDAO workflowDAO) {
        this.workflowDAO = workflowDAO;
    }


    private GroupActivity getActivty(Set set, Long id) {
        GroupActivity activity = null;
        for (Iterator iter=set.iterator(); iter.hasNext(); ) {
            GroupActivity one = (GroupActivity) iter.next();
            if (one.getId().longValue()==id.longValue()) {
                activity = one;
                break;
            }
        }//for iter
        return activity;
    }//getActivty()
    
    
    public ActionForward execute(
            ActionMapping mapping,
            ActionForm form,
            javax.servlet.http.HttpServletRequest request,
            javax.servlet.http.HttpServletResponse response
    ) throws java.lang.Exception {
        WorkflowForm wfform = (WorkflowForm) form;
        
        return mapping.findForward("show");
    }//execute()
    
    
}//class VotingAction
