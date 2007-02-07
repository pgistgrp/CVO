package org.pgist.packages;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * Participants use this action to create/modify their packages.<br>
 * 
 * The action accepts two parameters:
 * <ul>
 *   <li>cctId - int, an id of a CCT object</li>
 *   <li>action - string, valid values are
 *     <ul>
 *       <li>'' - show up the page</li>
 *       <li>'save' - user submit his choices</li>
 *     </ul>
 *   </li>
 * </ul>
 * 
 * The action will always forward to the jsp page specified in struts-config.xml
 * with the forward name as "success".
 * In that jsp page, the following request attributes are available:
 * <ul>
 *   <li>projects - a collection of Project objects</li>
 *   <li>sources - a collection of FundingSource objects</li>
 *   <li>package - a UserPackage object of the current user</li>
 *   <li>stat - a PackageStat object</li>
 *   <li>costs - a set of UserCost objects</li>
 * </ul>
 * 
 * When user submits his choices ("save".equals(action)), the choices will be submitted in form parameters,
 * "proj-0000" and "fund-0000", where "0000" should be the id of one project instance. The action should parse
 * out that id, and populate to the user package.
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
