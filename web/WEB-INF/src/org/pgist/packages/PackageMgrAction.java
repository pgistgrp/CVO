package org.pgist.packages;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * Moderator uses this action to manage the clustered packages.<br>
 * 
 * The action accepts two parameters:
 * <ul>
 *   <li>cctId - int, an id of a CCT object</li>
 *   <li>activity - string, valid values are
 *     <ul>
 *       <li>'' - show up the list page</li>
 *       <li>'edit' - show up the package editing page</li>
 *       <li>'delete' - delete a specified package</li>
 *       <li>'publish' - delete a specified package</li>
 *     </ul>
 *   </li>
 * </ul>
 * 
 * The action will always forward to the jsp page specified in struts-config.xml
 * with the forward name as "success".
 * In that jsp page, the following request attributes are available:
 * <ul>
 *   <li>cct - a CCT object</li>
 *   <li>clusteredPackages - a collection of ClusteredPackage objects</li>
 *   <li>manualPackages - a collection of ClusteredPackage objects (Manually added by moderator)</li>
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
        
        Long cctId = new Long(request.getParameter("cctId"));
        String activity = request.getParameter("activity");
        
        if ("save".equalsIgnoreCase(activity)) {
            //TODO: save the current users package
            //Note: the program should clear the alternatives in the user package,
            //      and then set the value again.
        }
        
        //show up the "Create/Modify Package" page
        //TODO: extract projects/sources/package/stat/costs and put to the request attributes
        
        request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
        
        return mapping.findForward("view");
        //return mapping.findForward("edit");
    }//execute()
    
    
}//class PackageMgrAction
