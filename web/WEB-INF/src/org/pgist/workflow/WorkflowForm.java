package org.pgist.workflow;

import java.util.Collection;

import org.apache.struts.action.ActionForm;
import org.pgist.wfengine.Template;
import org.pgist.wfengine.Workflow;
import org.pgist.wfengine.activity.GroupActivity;


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
    
    private DecisionSituation situation;
    
    private Workflow agenda;
    
    private GroupActivity meeting;
    
    private GroupActivity pmethod;
    
    private GroupActivity pgame;
    
    
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


    public GroupActivity getPgame() {
        return pgame;
    }


    public void setPgame(GroupActivity pgame) {
        this.pgame = pgame;
    }
    
    
}
