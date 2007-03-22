package org.pgist.funding;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * Funding Sources (in one CCT) Definition Action.<br>
 * 
 * This action can be managed by a workflow engine, or works standalone.<br>
 * 
 * This action is used by moderator to manage the association of funding sources to a specific CCT.<br>
 * 
 * It provides the following functionalities:
 * <ul>
 *   <li>associate the selected funding sources to a specific CCT instance</li>
 * </ul>
 * 
 * The action always accepts the following parameters:
 * <ul>
 *   <li>suiteId - the id of the a FundingSourceSuite object</li>
 * </ul>
 * 
 * The action will forward to page of "view" in struts-config, the following variables are available in jsp:
 * <ul>
 *   <li>suite - a FundingSourceSuite object</li>
 *   <li>sources - a collection of FundingSource objects</li>
 * </ul>
 * 
 * Examples:
 * <ul>
 *   <li>fundingDefine.do?suiteId=1234</li>
 * </ul>
 * 
 * @author kenny
 *
 */
public class FundingDefAction extends Action {
    
    
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

        request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);

    	String tempSuiteId = request.getParameter("suiteId");
    	if(tempSuiteId != null) {
    		Long suiteId = new Long(tempSuiteId);
    		request.setAttribute("suite", this.fundingService.getFundingSuite(suiteId));
    	}
    	    	
        request.setAttribute("sources", this.fundingService.getFundingSources());        
        
        return mapping.findForward("view");
    }//execute()
    
    
}//class FundingDefAction
