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
import org.pgist.wfengine.activity.PActActivity;


/**
 * 
 * @author kenny
 *
 */
public class ConcernCollectionAction extends Action {
    
    
    private WorkflowDAO workflowDAO;
    
    private WorkflowEngine engine;
    
    
    public void setWorkflowDAO(WorkflowDAO workflowDAO) {
        this.workflowDAO = workflowDAO;
    }


    public void setEngine(WorkflowEngine engine) {
        this.engine = engine;
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
        if (wfform.isGoAhead()) {
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
            
            //PAct, in principle, there should be only one running activity?
            Set set = pgame.getRunningActivities();
            if (set==null || set.size()==0) return mapping.findForward("error");
            
            PActActivity pact = null;
            
            for (Iterator iter=set.iterator(); iter.hasNext(); ) {
                Activity one = (Activity) iter.next();
                if (one.getId().longValue()!=wfform.getPactId().longValue()) continue;
                pact = (PActActivity) one;
                wfform.setPact(pact);
            }//for iter
            
            if (pact==null) return mapping.findForward("error");
            
            pact.setExpression(1);
            
            pgame.getContext().proceed(pact);
            
            engine.saveWorkflow(agenda);
            
            set = pgame.getRunningActivities();
            
            if (set.size()==0) {
                wfform.setPact(null);
                wfform.setPactId(null);
                pmethod.getContext().proceed(pgame);
                
                engine.saveWorkflow(agenda);
                
                set = pmethod.getRunningActivities();
                
                if (set.size()==0) {
                    wfform.setPgame(null);
                    wfform.setPgameId(null);
                    meeting.getContext().proceed(pmethod);
                    
                    engine.saveWorkflow(agenda);
                    
                    set = meeting.getRunningActivities();
                    
                    if (set.size()==0) {
                        wfform.setPmethod(null);
                        wfform.setPmethodId(null);
                        agenda.getContext().proceed(meeting);
                        
                        engine.saveWorkflow(agenda);
                        
                        set = agenda.getRunningActivities();
                    }
                }
            } else {
                for (Iterator iter=set.iterator(); iter.hasNext(); ) {
                    Activity one = (Activity) iter.next();
                    if (one.getId().longValue()!=wfform.getPactId().longValue()) continue;
                    pact = (PActActivity) one;
                    wfform.setPact(pact);
                }//for iter
                
                if (pact==null) return mapping.findForward("error");
                
                wfform.setPactId(pact.getId());
            }
            
            ActionForward forward = new ActionForward();
            forward.setModule("");
            forward.setName("temporary");
            forward.setPath("/workflow.do");
            forward.setRedirect(false);
            
            return forward;
        }
        return mapping.findForward("show");
    }//execute()
    
    
}//class ConcernCollectionAction
