package org.pgist.packages;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * Moderator uses this action to manage the clustered packages. The management operation
 * is done through AJAX.<br>
 * 
 * The action accepts either of the following two parameters:
 * <ul>
 *   <li>suiteId - int, the id of a PackageSuite object</li>
 *   <li>pkgId - int, the id of a ClusteredPackage object</li>
 * </ul>
 * 
 * Only one of the two parameters can be given, and if both are given, "suiteId" is used.
 *
 * If "suiteId" is given, the action will forward to the jsp page specified in struts-config.xml
 * with the forward name as "view". In jsp page, the following request attributes are available:
 * <ul>
 *   <li>suite - a PackageSuite object</li>
 * </ul>
 * 
 * If "pkgId" is given, the action will forward to the jsp
 * page specified in struts-config.xml with the forward name as "edit".
 * In jsp page, the following request attributes are available:
 * <ul>
 *   <li>pkg - a ClusteredPackage object</li>
 * </ul>
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
        /*
         * TODO: extract all required objects and put in request attributes
         */
        
        request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
        
        return mapping.findForward("view");
        //return mapping.findForward("edit");
    }//execute()
    
    
}//class PackageMgrAction
