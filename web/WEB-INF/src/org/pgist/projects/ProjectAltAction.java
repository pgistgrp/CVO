package org.pgist.projects;

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
 *   <li>altrefId - int, an id of a ProjectAltRef object</li>
 * </ul>
 * 
 * The action will forward to the jsp page specified in struts-config.xml with the forward name is "view".
 * In that jsp page, the following request attributes are available:
 * <ul>
 *   <li>reference - a ProjectAltRef object</li>
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
        
        Long altrefId = new Long(request.getParameter("altrefId"));
        
        ProjectAltRef reference = projectService.getProjectAltRefById(altrefId);
        
        request.setAttribute("reference", reference);
        
        request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
        
        return mapping.findForward("view");
    }//execute()
    
    
}//class ProjectAltAction
