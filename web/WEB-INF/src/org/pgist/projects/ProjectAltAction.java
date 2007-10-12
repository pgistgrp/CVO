package org.pgist.projects;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.criteria.CriteriaService;
import org.pgist.criteria.CriteriaSuite;
import org.pgist.packages.PackageService;
import org.pgist.users.User;
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
 *   <li>average - The average grade for this project alternative</li>
 *   <li>personalAverage - The personal average for this project alternative</li>
 *   <li>everyoneAverage - The average grade for everyone involved</li>
 * </ul>
 * 
 * @author kenny
 */
public class ProjectAltAction extends Action {
    
    
    private ProjectService projectService;
    
    private PackageService packageService;
    
    private CriteriaService criteriaService;

	public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }
    
	public void setPackageService(PackageService packageService) {
		this.packageService = packageService;
	}
    
    public void setCriteriaService(CriteriaService criteriaService) {
		this.criteriaService = criteriaService;
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
        Long critSuiteId = new Long(request.getParameter("critSuiteId"));
        
        ProjectAltRef reference = projectService.getProjectAltRefById(altrefId);
        CriteriaSuite critSuite = criteriaService.getCriteriaSuiteById(critSuiteId);
        
        
        //Get avg weights using matt's functions
        User user = projectService.getCurrentUser();
        String avg = packageService.getProjectGrade(reference); // this is actually the avg
        String personalAvg = packageService.getYourGrade(reference, critSuite, user);
        String everyoneAvg = packageService.getAvgGrade(reference, critSuite, user); // this is actually everyones average
        
        //Set Varaibles
        request.setAttribute("reference", reference);
        request.setAttribute("average", avg);
        request.setAttribute("personalAverage", personalAvg);
        request.setAttribute("everyoneAverage", everyoneAvg);
        
        request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
        
        return mapping.findForward("view");
    }//execute()



    
    
}//class ProjectAltAction
