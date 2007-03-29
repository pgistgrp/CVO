package org.pgist.packages;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.projects.ProjectSuite;
import org.pgist.users.User;
import org.pgist.util.WebUtils;


/**
 * Create participant's own package.<br>
 * 
 * This action is used by participants to create his package.<br>
 * 
 * In the page you can:
 * <ul>
 *   <li>Extract/Create a package for the current participant</li>
 *   <li>Select project alternatives into a package</li>
 *   <li>Select funding source alternatives into a package</li>
 * </ul>
 * 
 * The action accepts the following parameters:
 * <ul>
 *   <li>suiteId - the id of a specified PackageSuite object</li>
 * </ul>
 * 
 * the action will forward to page of "view", the following variables are available in jsp:
 * <ul>
 *   <li>userPkg - a UserPackage object</li>
 * </ul>
 * 
 * Examples:
 * <ul>
 *   <li>GET:
 *       createPackage.do?suiteId=1234
 *   </li>
 * </ul>
 * 
 * @author kenny
 *
 */
public class CreatePackageAction extends Action {
    
    
    private PackageService packageService;
    
    
    public void setPackageService(PackageService packageService) {
        this.packageService = packageService;
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
    	String tempPackageSuiteId = request.getParameter("suiteId");
    	if(tempPackageSuiteId != null) {
    		Long packSuite = new Long(tempPackageSuiteId);
    		
    		System.out.println("Package service = " + this.packageService);
    		
    		//Get the current users package
    		UserPackage uPack = this.packageService.createUserPackage(packSuite, WebUtils.currentUser());
    		
    		//Return the user package
    		request.setAttribute("userPkg", uPack);
    	}
        request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
        
        return mapping.findForward("view");
    }//execute()
    
    
}//class CreatePackageAction
