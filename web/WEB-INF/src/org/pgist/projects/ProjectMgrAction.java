package org.pgist.projects;

import java.util.Collection;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * 
 * @author kenny
 *
 */
public class ProjectMgrAction extends Action {
    
    
    private ProjectService projectService;
    
    
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
        String id = (String) request.getParameter("id");
        
        if (id==null || "".equals(id)) {
            //get the list page
            
            Collection projects = projectService.getProjects();
            request.setAttribute("projects", projects);
            
            return mapping.findForward("list");
        }
        
        //get the edit page
        try {
            Project project = null;
            
            Long pid = new Long(id);
            
            project = projectService.getProjectById(pid);
            
            request.setAttribute("project", project);
        } catch (Exception e) {
            e.printStackTrace();
            return mapping.findForward("error");
        }
        
        return mapping.findForward("edit");
    }//execute()
    
    
}//class ProjectMgrAction
