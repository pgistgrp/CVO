package org.pgist.projects;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.actions.WorkflowAction;


/**
 * Projects (in one CCT) Definition Action.<br>
 * 
 * This action can be managed by a workflow engine, or works standalone.<br>
 * 
 * This action is used by moderator to manage the association of projects to a specific CCT.<br>
 * It provides the following functionalities:
 * <ul>
 *   <li>associate the selected projects to a specific CCT instance</li>
 * </ul>
 * 
 * The action always accepts the following parameters:
 * <ul>
 *   <li>cctId - the id of the current CCT object</li>
 *   <li>activity, it's value can be:
 *     <ul>
 *       <li>"" - get the projects list</li>
 *       <li>"save" - save the current projects setup</li>
 *     </ul>
 *   </li>
 * </ul>
 * 
 * The action requires the following parameters when activity==save:
 * <ul>
 *   <li>projectId - an array of ids of projects to be related to current CCT</li>
 * </ul>
 * 
 * Examples:
 * <ul>
 *   <li>projectDefine.do?cctId=1234</li>
 *   <li>&lt;form action="projectDefine.do"&gt;<br>
 *       &lt;input type="hidden" name="cctId" value="1234"&gt<br>
 *       &lt;input type="hidden" name="activity" value="save"&gt<br>
 *       &lt;input type="checkbox" name="projectId" value="2001"&gt<br>
 *       &lt;input type="checkbox" name="projectId" value="2002"&gt<br>
 *       &lt;input type="checkbox" name="projectId" value="2003"&gt<br>
 *       &lt;input type="checkbox" name="projectId" value="2004"&gt<br>
 *       &lt;/form&gt;
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
        /*
         * Logic:
         *   (1) get cctId from request
         *   (2) check the value of "activity", if it's "save"
         *     (2.1) use request.getParameterValues("projectId") to get all selected projects ids as string array
         *     (2.2) save the associations
         *     (2.3) check value of request attribute Workflow.ACTION_IN_WORKFLOW, if it's true,
         *         forward to action "nextstep"
         *     (2.4) else, go to (3)
         *   (3) get all projects list
         *   (4) get CCT object
         *   (5) forward to page "view" with the following values in request attributes:
         *           "projects" - projects list
         *           "cct" - current CCT object
         *   (-) Any error, forward to page "error"
         */
        
        String cctId = request.getParameter("cctId");
        
        String activity = request.getParameter("activity");
        if ("save".equals(activity)) {
            /*
             * TODO: get the selected project ids, call projectService.setupProjectsForCCT(cctId, ids)
             *       to save the associations.
             */
            
            if (request.getAttribute(WorkflowAction.ACTION_IN_WORKFLOW)!=null) {
                request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
                return mapping.findForward("nextstep");
            }
        }
        
        //TODO: load the current CCT object by cctId, transfer to jsp
        
        //TODO: get all project list, transfer to jsp
        
        request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
        return mapping.findForward("view");
    }//execute()
    
    
}//class ProjectDefAction
