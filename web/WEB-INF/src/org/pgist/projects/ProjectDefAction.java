package org.pgist.projects;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * Projects (in a specific decision situation) Definition Action.<br>
 * 
 * This action is used by moderator to manage the association of projects to a specific decision situation.<br>
 * It provides the following functionalities:
 * <ul>
 *   <li>show all projects in the system</li>
 *   <li>in the page, Do CRUD With AJAX</li>
 * </ul>
 * 
 * the action will forward to page of "view", the following variables are available in jsp:
 * <ul>
 *   <li>suite - a ProjectSuite object</li>
 *   <li>projects - a collection of Project objects</li>
 * </ul>
 * 
 * Examples:
 * <ul>
 *   <li>GET:<br>
 *       projectDefine.do?suiteId=1234
 *   </li>
 * </ul>
 * 
 * @author kenny
 *
 */
public class ProjectDefAction extends Action {
    
    
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
        request.setAttribute("projects", this.projectService.getProjects());
        return mapping.findForward("view");
    }//execute()
    
    
}//class ProjectDefAction
