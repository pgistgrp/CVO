package org.pgist.cvo;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.util.WebUtils;


/**
 * 
 * @author kenny
 *
 */
public class CSTViewAction extends Action {

    
    private CCTService cctService;
    
    
    public CSTViewAction() {
    }
    
    
    public void setCctService(CCTService cctService) {
        this.cctService = cctService;
    }


    /*
     * ------------------------------------------------------------------------
     */
    
    
    public ActionForward execute(
            ActionMapping mapping,
            ActionForm form,
            javax.servlet.http.HttpServletRequest request,
            javax.servlet.http.HttpServletResponse response
    ) throws java.lang.Exception {
        if (!WebUtils.checkRole("moderator")) {
            throw new Exception("This function is restricted only to moderator!");
        }
        
        CCTForm cctform = (CCTForm) form;
        
        CCT cct = cctService.getCCTById(cctform.getCctId());
        
        cctform.setCct(cct);
        
        request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
        
        return mapping.findForward("view");
    }//execute()


}//class CSTViewAction
