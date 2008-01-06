package org.pgist.sarp.cst;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.sarp.bct.BCT;
import org.pgist.sarp.bct.BCTService;


/**
 * 
 * @author kenny
 *
 */
public class CatsTreeAction extends Action {

    
    private BCTService bctService;
    
    
    public CatsTreeAction() {
    }
    
    
    public void setBctService(BCTService bctService) {
        this.bctService = bctService;
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
        BCT bct = bctService.getBCTById(new Long(request.getParameter("bctId")));
        
        request.setAttribute("root", bct.getRootCategory());
        
        request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
        
        return mapping.findForward("view");
    }//execute()


}//class CatsTreeAction
