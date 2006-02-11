package org.pgist.workflow;

import java.util.Iterator;
import java.util.Set;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.wfengine.Workflow;
import org.pgist.wfengine.WorkflowEngine;
import org.pgist.wfengine.activity.GroupActivity;


/**
 * 
 * @author kenny
 *
 */
public class WorkflowAction extends Action {

    
    private WorkflowDAO workflowDAO;
    
    
    public WorkflowAction() {
    }
    
    
    public void setWorkflowDAO(WorkflowDAO workflowDAO) {
        this.workflowDAO = workflowDAO;
    }


    /*
     * ------------------------------------------------------------------------------
     */
    
    
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
        
        Long situationId = wfform.getSituationId();
        if (situationId==null) return mapping.findForward("error");
        
        //Decision Situation
        DecisionSituation situation = workflowDAO.getSituationById(situationId);
        wfform.setSituation(situation);
        
        Workflow agenda = situation.getAgenda();
        wfform.setAgenda(agenda);
        
        Long meetingId = wfform.getMeetingId();
        if (meetingId==null) return mapping.findForward("meetings");
        
        //Meeting
        GroupActivity meeting = getActivty(agenda.getRunningActivities(), meetingId);
        
        if (meeting==null) return mapping.findForward("error");
        
        wfform.setMeeting(meeting);
        
        Long pmethodId = wfform.getPmethodId();
        if (pmethodId==null) return mapping.findForward("pmethods");
        
        //PMethod
        GroupActivity pmethod = getActivty(meeting.getRunningActivities(), pmethodId);
        
        if (pmethod==null) return mapping.findForward("error");
        
        wfform.setPmethod(pmethod);
        
        Long pgameId = wfform.getPgameId();
        if (pgameId==null) return mapping.findForward("pgames");
        
        //PGame
        GroupActivity pgame = getActivty(pmethod.getRunningActivities(), pgameId);
        
        if (pgame==null) return mapping.findForward("error");
        
        wfform.setPgame(pgame);
        
        
        return mapping.findForward("list");
    }//execute()


}//class TemplateListAction
