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
public class SDCListAction extends Action {

    
    private CCTService cctService = null;
    
    
    public SDCListAction() {
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
        cctform.setCcts(cctService.getCCTs());
        
        return mapping.findForward("list");
    }//execute()


}//class SDCListAction
