package org.pgist.projects;

import java.util.Collection;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.cvo.CCTService;


public class ProjectPublishAction extends Action {
    
    
    private CCTService cctService;
    
    private ProjectService projectService;
    
    
    public void setCctService(CCTService cctService) {
        this.cctService = cctService;
    }


    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    public ActionForward execute(
            ActionMapping mapping,
            ActionForm form,
            javax.servlet.http.HttpServletRequest request,
            javax.servlet.http.HttpServletResponse response
    ) throws Exception {
        String action = request.getParameter("action");
        
        if ("publish".equals(action)) {
            //do publish
            
            //get the active ccts
            String cctId = request.getParameter("cctId");
            
            String[] projectIds = request.getParameterValues("projectId");
            
            //save the association
            
            return mapping.findForward("success");
        } else {
            //goto the page
            
            Collection ccts = cctService.getCCTs();
            
            Collection projects = projectService.getProjects();
            
            request.setAttribute("ccts", ccts);
            request.setAttribute("projects", projects);
            
            return mapping.findForward("list");
        }
        
    }//execute()
    
    
}//class ProjectPublishAction
