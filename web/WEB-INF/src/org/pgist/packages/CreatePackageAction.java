package org.pgist.packages;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * Create participant's own package.<br>
 * 
 * This action works standalone.<br>
 * 
 * This action is used by participants to create his package.<br>
 * 
 * In the page you can:
 * <ul>
 *   <li>Create a package for the current participant</li>
 *   <li>Select project alternatives into a package</li>
 *   <li>Select funding source alternatives into a package</li>
 * </ul>
 * 
 * The action always accepts the following parameters:
 * <ul>
 *   <li>cctId - the id of the current CCT object</li>
 * </ul>
 * 
 * the action will forward to page of "view", the following variables are available in jsp:
 * <ul>
 *   <li>cct - an CCT object</li>
 *   <li>projects - a collection of Projects objects</li>
 *   <li>sources - a collection of FundingSource objects</li>
 *   <li>userPkg - a UserPackage object</li>
 * </ul>
 * 
 * Examples:
 * <ul>
 *   <li>GET:
 *       createPackage.do?cctId=1234
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
        /*
         * Logic:
         *   (1) get cctId from request
         *   (2) get all projects list
         *   (3) get all funding sources list
         *   (4) get CCT object
         *   (5) get the UserPackage object for current participant
         *   (6) forward to page "view" with the following values in request attributes:
         *           "cct" - current CCT object
         *           "projects" - projects list
         *           "sources" - funding sources list
         *           "userPkg" - a UserPackage object
         *   (-) Any error, forward to page "error"
         */
        
        String cctId = request.getParameter("cctId");
        
        //TODO: load the current CCT object by cctId, transfer to jsp
        //TODO: get all projects list, transfer to jsp
        //TODO: get all funding sources list, transfer to jsp
        //TODO: get the UserPackage of the current participant
        
        request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
        return mapping.findForward("view");
        
    }//execute()
    
    
}//class HelpMeAction
