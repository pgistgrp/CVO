package org.pgist.packages;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.util.WebUtils;


/**
 * Moderator uses this action to manage the clustered packages. The management operation
 * is done through AJAX.<br>
 * 
 * The action accepts either of the following parameters:
 * <ul>
 *   <li>pkgSuiteId - int, the id of a PackageSuite object</li>
 * </ul>
 * 
 * When "pkgSuiteId" is given, the action will forward to the jsp page specified in struts-config.xml
 * with the forward name as "view". In jsp page, the following request attributes are available:
 * <ul>
 *   <li>pkgSuiteId - a PackageSuite ID object</li>
 * </ul>
 * 
 * 
 * @author kenny
 */
public class PackageMgrAction extends Action {
    
    
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
    	String tempPackageSuiteId = request.getParameter("pkgSuiteId");
    	if(tempPackageSuiteId != null) {
    		Long packSuite = new Long(tempPackageSuiteId);
    		    		    		
    		//Return the user package
    		request.setAttribute("pkgSuiteId", packSuite);
    	}
        request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
        
        return mapping.findForward("view");
    }//execute()
    
    
}//class PackageMgrAction
