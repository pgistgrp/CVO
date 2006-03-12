package org.pgist.workflow;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.struts.action.ActionForm;
import org.pgist.wfengine.Template;
import org.pgist.wfengine.Workflow;
import org.pgist.wfengine.activity.GroupActivity;
import org.pgist.wfengine.activity.PActActivity;
import org.pgist.wfengine.activity.PGameActivity;


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
    
    private Long situationId;
    
    private Long meetingId;
    
    private Long pmethodId;
    
    private Long pgameId;
    
    private Long pactId;
    
    private DecisionSituation situation;
    
    private Workflow agenda;
    
    private GroupActivity meeting;
    
    private GroupActivity pmethod;
    
    private PGameActivity pgame;

    private PActActivity pact;
    
    private Collection runningActivities;
    
    private Collection meetings = new ArrayList();
    
    private boolean goAhead;
    
    
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


    public Long getMeetingId() {
        return meetingId;
    }


    public void setMeetingId(Long meetingId) {
        this.meetingId = meetingId;
    }


    public Long getPgameId() {
        return pgameId;
    }


    public void setPgameId(Long pgameId) {
        this.pgameId = pgameId;
    }


    public Long getPmethodId() {
        return pmethodId;
    }


    public void setPmethodId(Long pmethodId) {
        this.pmethodId = pmethodId;
    }


    public Long getSituationId() {
        return situationId;
    }


    public void setSituationId(Long situationId) {
        this.situationId = situationId;
    }


    public Long getPactId() {
        return pactId;
    }


    public void setPactId(Long pactId) {
        this.pactId = pactId;
    }


    public DecisionSituation getSituation() {
        return situation;
    }


    public void setSituation(DecisionSituation situation) {
        this.situation = situation;
    }


    public Workflow getAgenda() {
        return agenda;
    }


    public void setAgenda(Workflow agenda) {
        this.agenda = agenda;
    }


    public GroupActivity getMeeting() {
        return meeting;
    }


    public void setMeeting(GroupActivity meeting) {
        this.meeting = meeting;
    }


    public GroupActivity getPmethod() {
        return pmethod;
    }


    public void setPmethod(GroupActivity pmethod) {
        this.pmethod = pmethod;
    }


    public PGameActivity getPgame() {
        return pgame;
    }


    public void setPgame(PGameActivity pgame) {
        this.pgame = pgame;
    }


    public PActActivity getPact() {
        return pact;
    }


    public void setPact(PActActivity pact) {
        this.pact = pact;
    }


    public boolean isGoAhead() {
        return goAhead;
    }


    public void setGoAhead(boolean goAhead) {
        this.goAhead = goAhead;
    }


    public Collection getRunningActivities() {
        return runningActivities;
    }


    public void setRunningActivities(Collection runningActivities) {
        this.runningActivities = runningActivities;
    }


    public Collection getMeetings() {
        return meetings;
    }


    public void setMeetings(Collection activityStack) {
        this.meetings = activityStack;
    }
    
    
}
