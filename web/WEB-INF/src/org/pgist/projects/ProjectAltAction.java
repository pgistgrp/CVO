package org.pgist.projects;

import java.util.Collection;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * Project Alternative Details Action.<br>
 * 
 * This action is used to show up the detailed information for a project alternative.<br>
 * 
 * The action accepts two parameters:
 * <ul>
 *   <li>cctId - int, an id of a CCT object</li>
 *   <li>altId - int, an id of a ProjectAlternative object</li>
 * </ul>
 * 
 * The action will forward to the jsp page specified in struts-config.xml with the forward name is "success".
 * In that jsp page, the following request attributes are available:
 * <ul>
 *   <li>grades - a collection of ProjectCriteria objects</li>
 *   <li>alternative - a ProjectAlternative instance</li>
 * </ul>
 * 
 * @author kenny
 */
public class ProjectAltAction extends Action {
    
    
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
        
        Long cctId = new Long(request.getParameter("cctId"));
        Long altId = new Long(request.getParameter("altId"));
        
        Collection grades = projectService.getProjectAlternativeGrades(cctId, altId);
        ProjectAlternative alternative = projectService.getProjectAlternativeById(altId);
        
        request.setAttribute("grades", grades);
        request.setAttribute("alternative", alternative);
        
        request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
        
        return mapping.findForward("success");
    }//execute()
    
    
}//class ProjectAltAction
