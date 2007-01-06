package org.pgist.funding;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * Funding Source Management Action.<br>
 * 
 * This action works standalone.<br>
 * 
 * This action is used by moderator to manage all funding sources in the whole system.<br>
 * 
 * It provides the following functionalities:
 * <ul>
 *   <li>list all funding sources and their alternatives</li>
 *   <li>in the page, it performs CRUD operations with AJAX</li>
 * </ul>
 * 
 * The action accepts no parameters.
 * 
 * The action forwards to page "view", with the following variables available in the jsp:
 * <ul>
 *   <li>sources - a collection of FundingSource objects</li>
 * </ul>
 * 
 * Examples:
 * <ul>
 *   <li>GET:
 *       fundingManage.do
 *   </li>
 * </ul>
 * 
 * @author kenny
 *
 */
public class FundingMgrAction extends Action {
    
    
    private FundingService fundingService;
    
    
    public void setFundingService(FundingService fundingService) {
        this.fundingService = fundingService;
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
         *   (1) simply load all projects from the persistence layer
         *   (3) forward to page "view" with the following values in request attributes:
         *           "projects" - projects list
         *   (-) Any error, forward to page "error"
         */
        
        request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
        
        return mapping.findForward("view");
    }//execute()
    
    
}//class FundingMgrAction
