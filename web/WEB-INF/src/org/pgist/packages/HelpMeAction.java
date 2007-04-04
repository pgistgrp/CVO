package org.pgist.packages;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.criteria.CriteriaService;
import org.pgist.criteria.CriteriaSuite;
import org.pgist.util.WebUtils;


/**
 * HelpMe in package customization Action.<br>
 * 
 * This action is used by participants to get help to creating his package.<br>
 * 
 * The action always accepts the following parameters:
 * <ul>
 *   <li>pkgsuiteId - the id of a PackageSuite object</li>
 *   <li>critsuiteId - the id of a CriteriaSuite object</li>
 * </ul>
 * 
 * The action will forward to page of "view", the following variables are available in jsp:
 * <ul>
 *   <li>userPkg - a UserPackage object</li>
 *   <li>critSuite - a CriteriaSuite object</li>
 * </ul>
 * 
 * Examples:
 * <ul>
 *   <li>helpme.do?pkgsuiteId=1234&critsuiteId=4321</li>
 * </ul>
 * 
 * @author kenny
 *
 */
public class HelpMeAction extends Action {
    
    
    private PackageService packageService;
    private CriteriaService criteriaService;
    
    
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
    	String tempPkgSuiteId = request.getParameter("pkgsuiteId");
    	if(tempPkgSuiteId != null) {
    		Long packSuite = new Long(tempPkgSuiteId);
    		//Get the current users package
    		UserPackage uPack = this.packageService.createUserPackage(packSuite, WebUtils.currentUser());
        	request.setAttribute("usrPkg", uPack);
       	}    	
        
    	String tempCritsuiteId = request.getParameter("critsuiteId");
    	CriteriaSuite critSuite = null;
    	if(tempPkgSuiteId != null) {
    		critSuite = this.criteriaService.getCriteriaSuiteById(new Long(tempCritsuiteId));
        	request.setAttribute("critSuite", critSuite);
    	} 
        
        request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
        
        return mapping.findForward("view");
    }//execute()
    
    
}//class HelpMeAction
