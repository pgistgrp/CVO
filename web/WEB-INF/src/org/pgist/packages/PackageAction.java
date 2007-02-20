package org.pgist.packages;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * Shows the details of a specified package to participant.<br>
 * 
 * The action accepts two parameters:
 * <ul>
 *   <li>id - int, an id of a ClusteredPackage object</li>
 * </ul>
 * 
 * The action will always forward to the jsp page specified in struts-config.xml
 * with the forward name as "success".
 * In that jsp page, the following request attributes are available:
 * <ul>
 *   <li>package - a ClusteredPackage object</li>
 * </ul>
 * 
 * @author kenny
 */
public class PackageAction extends Action {
    
    
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
        String action = request.getParameter("action");
        
        if ("save".equalsIgnoreCase(action)) {
            //TODO: save the current users package
            //Note: the program should clear the alternatives in the user package,
            //      and then set the value again.
        }
        
        //show up the "Create/Modify Package" page
        //TODO: extract projects/sources/package/stat/costs and put to the request attributes
        
        request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
        
        return mapping.findForward("success");
    }//execute()
    
    
}//class PackageAction
