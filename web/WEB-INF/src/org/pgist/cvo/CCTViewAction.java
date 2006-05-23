package org.pgist.cvo;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * 
 * @author kenny
 *
 */
public class CCTViewAction extends Action {

    
    private CCTService cctService;
    
    
    public CCTViewAction() {
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
        CCTForm cctform = (CCTForm) form;
        
        CCT cct = cctService.getCCTById(cctform.getCctId());
        
        cctform.setCct(cct);
        
        return mapping.findForward("view");
    }//execute()


}//class CCTViewAction
