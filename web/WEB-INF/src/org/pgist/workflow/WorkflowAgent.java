package org.pgist.workflow;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.pgist.system.UserDAO;
import org.pgist.users.User;
import org.pgist.wfengine.Workflow;
import org.pgist.wfengine.WorkflowEngine;


/**
 * 
 * @author kenny
 *
 */
public class WorkflowAgent {
    
    
    private UserDAO userDAO;
    
    private WorkflowEngine engine;
    
    private WorkflowDAO workflowDAO;
    
    
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }


    public void setEngine(WorkflowEngine engine) {
        this.engine = engine;
    }
    
    
    public void setWorkflowDAO(WorkflowDAO workflowDAO) {
        this.workflowDAO = workflowDAO;
    }


    /*
     * ------------------------------------------------------------------------------
     */
    
    
    public String getTemplates() throws Exception {
        WorkflowForm wfform = new WorkflowForm();
        wfform.setTemplateList(engine.getSituationTemplates());
        
        WebContext context = WebContextFactory.get();
        HttpServletRequest request = context.getHttpServletRequest();
        request.setAttribute("workflowForm", wfform);
        
        return WebContextFactory.get().forwardToString("/WEB-INF/jsp/workflow/templateList.jsp");
    }//getTemplates()
    
    
    public boolean createSituation(HttpSession session, Long templateId, String title) throws Exception {
        User user = (User) session.getAttribute("user");
        user = userDAO.getUserById(user.getId(), true, false);
        
        Workflow workflow = engine.spawn(templateId);
        
        DecisionSituation situation = new DecisionSituation();
        situation.setAgenda(workflow);
        situation.setCreateTime(new Date());
        situation.setCreator(user);
        situation.setDeleted(false);
        situation.setTitle(title);
        
        workflowDAO.saveSituation(situation);
        
        return true;
    }//createSituation()
    
    
    public String refreshSituationList()  throws Exception {
        WorkflowForm wfform = new WorkflowForm();
        
        Collection list = workflowDAO.getSituations();
        wfform.setSituationList(list);
        
        WebContext context = WebContextFactory.get();
        HttpServletRequest request = context.getHttpServletRequest();
        request.setAttribute("workflowForm", wfform);
        
        return WebContextFactory.get().forwardToString("/WEB-INF/jsp/workflow/situationList_list.jsp");
    }//refreshSituationList()
    
    
}//class WorkflowAgent
