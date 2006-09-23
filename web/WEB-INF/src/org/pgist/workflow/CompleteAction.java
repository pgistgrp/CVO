package org.pgist.workflow;

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
import org.pgist.wfengine.activity.PGameActivity;


/**
 * 
 * @author kenny
 *
 */
public class CompleteAction extends Action {

    
    private WorkflowDAO workflowDAO;
    
    private WorkflowEngine engine;
    
    
    public CompleteAction() {
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
    
    
    private Activity getActivty(Set set, Long id) {
        Activity activity = null;
        for (Iterator iter=set.iterator(); iter.hasNext(); ) {
            Activity one = (Activity) iter.next();
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
        if (meetingId==null) return mapping.findForward("error");
        
        //Meeting
        GroupActivity meeting = (GroupActivity) getActivty(agenda.getRunningActivities(Activity.TYPE_MEETING), meetingId);
        
        if (meeting==null) return mapping.findForward("error");
        
        wfform.setMeeting(meeting);
        
        Long pmethodId = wfform.getPmethodId();
        if (pmethodId==null) return mapping.findForward("error");
        
        //PMethod
        GroupActivity pmethod = (GroupActivity) getActivty(meeting.getRunningActivities(Activity.TYPE_PMETHOD), pmethodId);
        
        if (pmethod==null) return mapping.findForward("error");
        
        wfform.setPmethod(pmethod);
        
        Long pgameId = wfform.getPgameId();
        if (pgameId==null) return mapping.findForward("error");
        
        //PGame
        PGameActivity pgame = (PGameActivity) getActivty(pmethod.getRunningActivities(Activity.TYPE_PGAME), pgameId);
        
        if (pgame==null) return mapping.findForward("error");
        
        wfform.setPgame(pgame);
        
        pgame.setExpression(1);
        
        if (pmethod.getContext().proceed(pgame)) {
            if (meeting.getContext().proceed(pmethod)) {
                agenda.getContext().proceed(meeting);
            }
        }
        
        engine.saveWorkflow(agenda);
        
        ActionForward forward = new ActionForward();
        forward.setModule("");
        forward.setName("temporary");
        forward.setPath("/agendaDisplay.do");
        forward.setRedirect(false);
        
        return forward;
    }//execute()


}//class CompleteAction
