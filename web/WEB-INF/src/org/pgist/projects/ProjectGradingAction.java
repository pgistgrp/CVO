package org.pgist.projects;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.actions.WorkflowAction;
import org.pgist.criteria.CriteriaService;


/**
 * Project Alternative - Criteria Grading Action.<br>
 * 
 * This action can be managed by a workflow engine, or works standalone.<br>
 * 
 * This action is used by moderator to grade project alternatives by criteria in a specific CCT.<br>
 * It provides the following functionalities:
 * <ul>
 *   <li>show up the grading page</li>
 *   <li>in the page grading is done through Ajax</li>
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
 * When activity=="", or when activity=="save" but this action is not called from a workflow instance,
 * the action will forward to page of "view", the following variables are available in jsp:
 * <ul>
 *   <li>cct - an CCT object</li>
 *   <li>projects - a collection of Project objects, each project object has a set of alternatives,
 *                  and for each alternative, a set of corresponding ProjectAlternativeCriteria
 *                  objects are set on its "object" field.
 *   </li>
 * </ul>
 * 
 * Examples:
 * <ul>
 *   <li>GET:<br>
 *       projectGrading.do?cctId=1234
 *   </li>
 *   <li>POST:<br>
 *       &lt;form action="projectGrading.do"&gt;<br>
 *       &lt;input type="hidden" name="cctId" value="1234"&gt<br>
 *       &lt;input type="hidden" name="activity" value="save"&gt<br>
 *       &lt;/form&gt;
 *   </li>
 * </ul>
 * 
 * @author kenny
 *
 */
public class ProjectGradingAction extends Action {
    
    
    private CriteriaService criteriaService;
    
    private ProjectService projectService;
    
    
    public void setCriteriaService(CriteriaService criteriaService) {
        this.criteriaService = criteriaService;
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
        /*
         * Logic:
         *   (1) get cctId from request
         *   (2) check the value of "activity", if it's "save"
         *     (2.1) check value of request attribute Workflow.ACTION_IN_WORKFLOW, if it's true,
         *         forward to action "nextstep"
         *     (2.2) else, go to (3)
         *   (3) get all projects list, and for each project load it's ProjectAlternativeCriteria object and set in that project
         *   (4) get CCT object
         *   (5) forward to page "view" with the following values in request attributes:
         *           "projects" - projects list
         *           "cct" - current CCT object
         *   (-) Any error, forward to page "error"
         */
        
        String cctId = request.getParameter("cctId");
        
        String activity = request.getParameter("activity");
        if ("save".equals(activity)) {
            if (request.getAttribute(WorkflowAction.ACTION_IN_WORKFLOW)!=null) {
                request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
                return mapping.findForward("nextstep");
            }
        }
        
        //TODO: load the current CCT object by cctId, transfer to jsp
        
        //TODO: get all project list,
        
        //TODO: for each project alternative, load its corresponding ProjectAlternativeCriteria objects, and set to its "object" field
        
        //TODO: transfer projects to jsp
        
        request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
        return mapping.findForward("view");
    }//execute()
    
    
}//class ProjectGradingAction
