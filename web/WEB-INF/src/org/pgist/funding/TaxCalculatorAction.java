package org.pgist.funding;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.packages.UserPackage;
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
 *   <li>cctId - the id of the current CCT object</li>
 * </ul>
 * 
 * the action will forward to page of "view", the following variables are available in jsp:
 * <ul>
 *   <li>cct - an CCT object</li>
 *   <li>user - the current User oject</li>
 *   <li>tolls - a list of FundingSourceAlternative objects (all alternatives which are tolls)</li>
 *   <li>userCommute - a UserCommute object</li>
 * </ul>
 * 
 * Examples:
 * <ul>
 *   <li>GET:
 *       fundingCalc.do?cctId=1234
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
    	String tempSuiteId = request.getParameter("suiteId");
    	if(tempSuiteId != null) {
    		Long suiteId = new Long(tempSuiteId);
    		FundingSourceSuite funSuite = this.fundingService.getFundingSuite(suiteId);
    		request.setAttribute("suite", funSuite);
    	}
    	           
		//Get the current user
		request.setAttribute("user", this.fundingService.getUser(WebUtils.currentUser()));
    	
    	
        /*
         * Logic:
         *   (1) get cctId from request
         *   (2) get CCT object
         *   (3) get all funding sources list
         *   (4) forward to page "view" with the following values in request attributes:
         *           "user" - current User object
         *           "cct" - current CCT object
         *           "tolls" - a list of FundingSourceAlternative objects (all alternatives which are tolls)
         *           "userCommute" - a UserCommute object
         *   (-) Any error, forward to page "error"
         */
        String cctId = request.getParameter("cctId");
        
        //TODO: load the current CCT object by cctId, transfer to jsp
        //TODO: load the current User object, transfer to jsp
        //TODO: get all tolls list, transfer to jsp
        //TODO: get the current user commute object, transfer to jsp
        
        request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
        return mapping.findForward("view");
        
    }//execute()
    
    
}//class TaxCalculatorAction
