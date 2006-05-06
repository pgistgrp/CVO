package org.pgist.workflow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.util.WebUtils;
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
public class AgendaDisplayAction extends Action {

    
    private WorkflowDAO workflowDAO;
    
    private WorkflowEngine engine;
    
    
    public AgendaDisplayAction() {
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
        
        Long situationId = wfform.getSituationId();
        if (situationId==null) return mapping.findForward("error");
        
        //Decision Situation
        DecisionSituation situation = workflowDAO.getSituationById(situationId);
        wfform.setSituation(situation);
        
        //Agenda
        Workflow agenda = situation.getAgenda();
        wfform.setAgenda(agenda);
        
        Collection meetings = wfform.getMeetings();
        Set meetingSet = agenda.getRunningActivities(Activity.TYPE_MEETING);
        
        for (Iterator iter1=meetingSet.iterator(); iter1.hasNext(); ) {
            GroupActivity meeting = (GroupActivity) iter1.next();
            Map meetingMap = new HashMap();
            meetings.add(meetingMap);
            meetingMap.put("id", meeting.getId());
            meetingMap.put("name", meeting.getName());
            Collection pmethods = new ArrayList();
            meetingMap.put("pmethods", pmethods);
            
            Set pmethodSet = meeting.getRunningActivities(Activity.TYPE_PMETHOD);
            for (Iterator iter2=pmethodSet.iterator(); iter2.hasNext(); ) {
                GroupActivity pmethod = (GroupActivity) iter2.next();
                Map pmethodMap = new HashMap();
                pmethods.add(pmethodMap);
                pmethodMap.put("id", pmethod.getId());
                pmethodMap.put("name", pmethod.getName());
                Collection pgames = new ArrayList();
                pmethodMap.put("pgames", pgames);
                
                Set pgameSet = pmethod.getRunningActivities(Activity.TYPE_PGAME);
                for (Iterator iter3=pgameSet.iterator(); iter3.hasNext(); ) {
                    PGameActivity pgame = (PGameActivity) iter3.next();
                    Map pgameMap = new HashMap();
                    pgames.add(pgameMap);
                    pgameMap.put("id", pgame.getId());
                    pgameMap.put("name", pgame.getName());
                }//for iter3
            }//for iter2
        }//for iter1
        
        if (WebUtils.checkRole("moderator")) {
            return mapping.findForward("moderator");
        } else if (WebUtils.checkRole("member")) {
            return mapping.findForward("member");
        } else {
            return mapping.findForward("error");
        }
    }//execute()


}//class AgendaDisplayAction
