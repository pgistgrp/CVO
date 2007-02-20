package org.pgist.packages;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * Participants use this action to vote on the clustered packages.<br>
 * 
 * The action accepts two parameters:
 * <ul>
 *   <li>cctId - int, an id of a CCT object</li>
 *   <li>phase - int, "1" for the first time voting, "2" for the second time voting, other value is invalid.</li>
 *   <li>action - string, valid values are
 *     <ul>
 *       <li>'' - show up the page</li>
 *       <li>'vote' - user submit his choices</li>
 *     </ul>
 *   </li>
 * </ul>
 * 
 * The action will always forward to the jsp page specified in struts-config.xml
 * with the forward name as "success".
 * In that jsp page, the following request attributes are available:
 * <ul>
 *   <li>packageVotes - a collection of PackageVote objects</li>
 *   <li>polls - a collection of a collection packageVoteStats objects,
 *               each is for one phase voting. The first collection in polls
 *               is always the newest voting results.
 *   </li>
 * </ul>
 * 
 * When user submits his choices ("save".equals(action)), the choices will be submitted in form parameters,
 * "proj-0000" and "fund-0000", where "0000" should be the id of one project instance. The action should parse
 * out that id, and populate to the user package.
 * 
 * @author kenny
 */
public class PackageVoteAction extends Action {
    
    
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
        
        //return mapping.findForward("view");
        return mapping.findForward("results");
    }//execute()
    
    
}//class PackageVoteAction
