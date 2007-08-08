package org.pgist.projects;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.util.WebUtils;


/**
 * Project Management Action.<br>
 * 
 * This action is used by moderator to manage all projects in the whole system.<br>
 * 
 * It provides the following functionalities:
 * <ul>
 *   <li>list all projects and their alternatives</li>
 *   <li>in the page, it performs CRUD operations with AJAX</li>
 * </ul>
 * 
 * The action accepts no parameters.
 * 
 * The action forwards to page "view", with the following variables available in the jsp:
 * <ul>
 *   <li>projects - a collection of Project objects</li>
 * </ul>
 * 
 * Examples:
 * <ul>
 *   <li>GET:
 *       projectManage.do
 *   </li>
 * </ul>
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
        /*
         * Logic:
         *   (1) simply load all projects from the persistence layer
         *   (3) forward to page "view" with the following values in request attributes:
         *           "projects" - projects list
         *   (-) Any error, forward to page "error"
         */
        
        if (!WebUtils.checkRole("moderator")) {
            throw new Exception("This function is restricted only to moderator!");
        }
        
        request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
        
        return mapping.findForward("view");
    }//execute()
    
    
}//class ProjectMgrAction
