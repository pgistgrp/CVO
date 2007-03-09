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
 *   <li>suiteId - int, an id of a PackageVoteSuite object</li>
 * </ul>
 * 
 * <p>According to whether the current user voted or not in the current phase, the action forwards to different page.
 * <p>If the current user not voted
 *   <ul>
 *     <li>request contains the following attributes:
 *       <ul>
 *         <li>voteSuite - a PackageVoteSuite object</li>
 *       </ul>
 *     </li>
 *     <li>it will forward to the jsp page specified in struts-config.xml as "view";</li>
 *   </ul>
 *
 * <p>otherwise
 *   <ul>
 *    <li>request contains the following attributes:
 *       <ul>
 *         <li>voteSuite - a PackageVoteSuite object</li>
 *       </ul>
 *    </li>
 *    <li>forward to "results"</li>
 *   </ul>
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
        /*
         * TODO: extract required objects and put in request attributes
         */
        
        request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
        
        return mapping.findForward("view");
        
        //return mapping.findForward("results");
    }//execute()
    
    
}//class PackageVoteAction
