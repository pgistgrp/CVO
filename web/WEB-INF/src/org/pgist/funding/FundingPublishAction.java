package org.pgist.funding;

import java.util.Collection;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.cvo.CCTService;


public class FundingPublishAction extends Action {
    
    
    private CCTService cctService;
    
    private FundingService fundingService;
    
    
    public void setCctService(CCTService cctService) {
        this.cctService = cctService;
    }


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
        String action = request.getParameter("action");
        
        if ("publish".equals(action)) {
            //do publish
            
            //get the active ccts
            String cctId = request.getParameter("cctId");
            
            String[] fundingIds = request.getParameterValues("fundingId");
            
            //save the association
            
            return mapping.findForward("success");
        } else {
            //goto the page
            
            Collection ccts = cctService.getCCTs();
            
            Collection fundings = fundingService.getFundingSources();
            
            request.setAttribute("ccts", ccts);
            request.setAttribute("fundings", fundings);
            
            return mapping.findForward("list");
        }
        
    }//execute()
    
    
}//class FundingPublishAction
