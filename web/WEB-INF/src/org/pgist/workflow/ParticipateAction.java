package org.pgist.workflow;

import java.util.Iterator;
import java.util.Set;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.wfengine.Activity;
import org.pgist.wfengine.Workflow;
import org.pgist.wfengine.activity.GroupActivity;
import org.pgist.wfengine.activity.PActActivity;
import org.pgist.wfengine.activity.PGameActivity;


/**
 * 
 * @author kenny
 *
 */
public class ParticipateAction extends Action {

    
    private WorkflowDAO workflowDAO;
    
    
    public ParticipateAction() {
    }
    
    
    public void setWorkflowDAO(WorkflowDAO workflowDAO) {
        this.workflowDAO = workflowDAO;
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
        if (meetingId==null) {
            wfform.setRunningActivities(agenda.getRunningActivities(Activity.TYPE_MEETING));
            return mapping.findForward("meetings");
        }
        
        //Meeting
        GroupActivity meeting = (GroupActivity) getActivty(agenda.getRunningActivities(Activity.TYPE_MEETING), meetingId);
        
        if (meeting==null) return mapping.findForward("error");
        
        wfform.setMeeting(meeting);
        
        Long pmethodId = wfform.getPmethodId();
        if (pmethodId==null) {
            wfform.setRunningActivities(meeting.getRunningActivities(Activity.TYPE_PMETHOD));
            return mapping.findForward("pmethods");
        }
        
        //PMethod
        GroupActivity pmethod = (GroupActivity) getActivty(meeting.getRunningActivities(Activity.TYPE_PMETHOD), pmethodId);
        
        if (pmethod==null) return mapping.findForward("error");
        
        wfform.setPmethod(pmethod);
        
        Long pgameId = wfform.getPgameId();
        if (pgameId==null) {
            wfform.setRunningActivities(pmethod.getRunningActivities(Activity.TYPE_PGAME));
            return mapping.findForward("pgames");
        }
        
        //PGame
        PGameActivity pgame = (PGameActivity) getActivty(pmethod.getRunningActivities(Activity.TYPE_PGAME), pgameId);
        
        if (pgame==null) return mapping.findForward("error");
        
        wfform.setPgame(pgame);
        
        String actionPath = pgame.getAction();
        if (actionPath==null) return mapping.findForward("error");
        
        ActionForward forward = new ActionForward();
        forward.setModule("");
        forward.setName("temporary");
        forward.setPath(actionPath);
        forward.setRedirect(false);
        
        return forward;
    }//execute()


}//class ParticipateAction
