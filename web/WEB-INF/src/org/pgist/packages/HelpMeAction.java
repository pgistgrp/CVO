package org.pgist.packages;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.actions.WorkflowAction;


/**
 * HelpMe in package customization Action.<br>
 * 
 * This action works standalone.<br>
 * 
 * This action is used by participants to get help to creating his package.<br>
 * 
 * It provides the following functionalities:
 * <ul>
 *   <li>Re-Weigh criteria</li>
 *   <li>Define how much they're willing to pay each year</li>
 * </ul>
 * 
 * The action always accepts the following parameters:
 * <ul>
 *   <li>cctId - the id of the current CCT object</li>
 *   <li>activity, it's value can be:
 *     <ul>
 *       <li>"" - get the projects list</li>
 *       <li>"save" - save the current setup</li>
 *     </ul>
 *   </li>
 * </ul>
 * 
 * When activity=="save", the action needs the following extra parameters:
 * <ul>
 *   <li>crit_XXXX - XXXX is an id for CriteriaWeight object, the the value of this field is the weight</li>
 *   <li>yourCost - cost for the current participant</li>
 *   <li>avgCost - cost for the average resident</li>
 *   <li>includeSelection - true | false, include current selections or not</li>
 * </ul>
 * 
 * When activity=="", the action will forward to page of "view", the following variables are available in jsp:
 * <ul>
 *   <li>cct - an CCT object</li>
 *   <li>weights - a collection of CriteriaWeight objects</li>
 * </ul>
 * 
 * When activity=="save", the action will forward to page of "close".
 * 
 * Examples:
 * <ul>
 *   <li>GET:
 *       helpme.do?cctId=1234
 *   </li>
 *   <li>POST:
 *       &lt;form action="helpme.do"&gt;<br>
 *       &lt;input type="hidden" name="cctId" value="1234"&gt<br>
 *       &lt;input type="hidden" name="activity" value="save"&gt<br>
 *       &lt;input type="text" name="yourCost" value="400"&gt<br>
 *       &lt;input type="radio" name="includeSelection" value="true"&gtInclude Selection<br>
 *       &lt;input type="radio" name="includeSelection" value="false"&gtDon't Include Selection<br>
 *       &lt;input type="text" name="avgCost" value="100"&gt<br>
 *       &lt;input type="text" name="crit_2001" value="40"&gt<br>
 *       &lt;input type="text" name="crit_2001" value="40"&gt<br>
 *       &lt;input type="text" name="crit_2002" value="34"&gt<br>
 *       &lt;input type="text" name="crit_2003" value="89"&gt<br>
 *       &lt;input type="text" name="crit_2004" value="55"&gt<br>
 *       &lt;/form&gt;
 *   </li>
 * </ul>
 * 
 * @author kenny
 *
 */
public class HelpMeAction extends Action {
    
    
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
    
    
}//class HelpMeAction
