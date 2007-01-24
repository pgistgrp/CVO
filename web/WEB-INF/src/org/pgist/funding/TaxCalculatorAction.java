package org.pgist.funding;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.actions.WorkflowAction;


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
 *   <li>activity, it's value can be:
 *     <ul>
 *       <li>"" - get the funding sources list</li>
 *       <li>"save" - save the current funding sources setup</li>
 *     </ul>
 *   </li>
 * </ul>
 * 
 * The action requires the following parameters when activity=="save":
 * <ul>
 *   <li>sourceId - an array of ids of funding sources to be related to current CCT</li>
 * </ul>
 * 
 * When activity=="", or when activity=="save" but this action is not called from a workflow instance,
 * the action will forward to page of "view", the following variables are available in jsp:
 * <ul>
 *   <li>cct - an CCT object</li>
 *   <li>sources - a collection of FundingSource objects</li>
 * </ul>
 * 
 * Examples:
 * <ul>
 *   <li>GET:
 *       fundingDefine.do?cctId=1234
 *   </li>
 *   <li>POST:
 *       &lt;form action="fundingDefine.do"&gt;<br>
 *       &lt;input type="hidden" name="cctId" value="1234"&gt<br>
 *       &lt;input type="hidden" name="activity" value="save"&gt<br>
 *       &lt;input type="checkbox" name="sourceId" value="2001"&gt<br>
 *       &lt;input type="checkbox" name="sourceId" value="2002"&gt<br>
 *       &lt;input type="checkbox" name="sourceId" value="2003"&gt<br>
 *       &lt;input type="checkbox" name="sourceId" value="2004"&gt<br>
 *       &lt;/form&gt;
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
        /*
         * Logic:
         *   (1) get cctId from request
         *   (2) check the value of "activity", if it's "save"
         *     (2.1) use request.getParameterValues("sourceId") to get all selected funding sources ids as string array
         *     (2.2) save the associations
         *     (2.3) check value of request attribute Workflow.ACTION_IN_WORKFLOW, if it's true,
         *         forward to action "nextstep"
         *     (2.4) else, go to (3)
         *   (3) get all funding sources list
         *   (4) get CCT object
         *   (5) forward to page "view" with the following values in request attributes:
         *           "sources" - funding sources list
         *           "cct" - current CCT object
         *   (-) Any error, forward to page "error"
         */
        
        String cctId = request.getParameter("cctId");
        
        String activity = request.getParameter("activity");
        if ("save".equals(activity)) {
            /*
             * TODO: get the selected funding source ids, call fundingService.setupFundingSourcesForCCT(cctId, ids)
             *       to save the associations.
             */
            
            if (request.getAttribute(WorkflowAction.ACTION_IN_WORKFLOW)!=null) {
                request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
                return mapping.findForward("nextstep");
            }
        }
        
        //TODO: load the current CCT object by cctId, transfer to jsp
        
        //TODO: get all funding sources list, transfer to jsp
        
        request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
        return mapping.findForward("view");
        
    }//execute()
    
    
}//class TaxCalculatorAction
