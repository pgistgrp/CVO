package org.pgist.funding;

import java.util.Collection;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.util.PageSetting;


/**
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
        PageSetting setting = new PageSetting();
        
        setting.setPage((String) request.getParameter("page"));
        setting.setRowOfPage((String) request.getParameter("count"));
        
        Collection fundings = fundingService.getFundingSources(setting);
        
        request.setAttribute("fundings", fundings);
        
        return mapping.findForward("list");
    }//execute()
    
    
}//class FundingMgrAction
