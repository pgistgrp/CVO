package org.pgist.funding;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.users.User;
import org.pgist.util.WebUtils;


/**
 * Tax Calculator Action for Funding Sources (in one CCT).<br>
 * 
 * This action works standalone.<br>
 * 
 * This action is used by participants to manage his own tax calculation.<br>
 * 
 * It provides the following functionalities:
 * <ul>
 *   <li>calculate taxes based on user input</li>
 * </ul>
 * 
 * The action always accepts the following parameters:
 * <ul>
 *   <li>suiteId - the id of the a FundingSourceSuite object</li>
 * </ul>
 * 
 * the action will forward to page of "view", the following variables are available in jsp:
 * <ul>
 *   <li>fundSuiteId - the funding suiteId that you will need for the ajax calls</li>
 *   <li>user - the User Tax InfoDTO that contains all information about the user for calculations</li>
 * </ul>
 * 
 * Examples:
 * <ul>
 *   <li>GET:
 *       taxCalculator.do?suiteId=200
 *   </li>
 * </ul>
 * 
 * @author kenny
 *
 */
public class TaxCalculatorAction extends Action {
    
    
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
    	String tempFundSuiteId = request.getParameter("fundSuiteId");
    	
		//Get the current user
    	User user = this.fundingService.getUser(WebUtils.currentUser());
        
    	UserTaxInfoDTO taxInfo = this.fundingService.createUserTaxInfoDTO(user.getId());
		request.setAttribute("user", taxInfo);
		request.setAttribute("fundSuiteId", tempFundSuiteId);
    	
        request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
        
        return mapping.findForward("view");
    }//execute()
    
    
}//class TaxCalculatorAction
