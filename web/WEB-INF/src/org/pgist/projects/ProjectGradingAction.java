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
 *   <li>projsuiteId - the id of the a ProjectSuite object</li>
 *   <li>critsuiteId - the id of the a CriteriaSuite object</li>
 * </ul>
 * 
 * The action will forward to page of "view" in struts-config, the following variables are available in jsp:
 * <ul>
 *   <li>projSuite - a ProjectSuite object
 *   <li>critSuite - a CriteriaSuite object
 *   </li>
 * </ul>
 * 
 * Examples:
 * <ul>
 *   <li>projectGrading.do?projsuiteId=1234&critsuiteId=4321</li>
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
    	String tempProjSuiteId = request.getParameter("projsuiteId");
    	if(tempProjSuiteId != null) {
    		Long projSuite = new Long(tempProjSuiteId);
    		request.setAttribute("projSuite", this.projectService.getProjectSuite(projSuite));
    	}
    	String tempCritSuiteId = request.getParameter("critsuiteId");
    	if(tempCritSuiteId != null) {
    		Long critSuite = new Long(tempCritSuiteId);
    		request.setAttribute("critSuite", this.criteriaService.getCriteriaSuiteById(critSuite));
    	}
    	       
        request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
        
        return mapping.findForward("view");
    }//execute()
    
    
}//class ProjectGradingAction
