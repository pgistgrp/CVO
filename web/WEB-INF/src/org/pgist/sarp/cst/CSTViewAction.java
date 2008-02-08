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
public class CSTViewAction extends Action {

    
    private CSTService cstService;
    
    
    public CSTViewAction() {
    }
    
    
    public void setCstService(CSTService cstService) {
        this.cstService = cstService;
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
        CST cst = cstService.getCSTById(new Long(request.getParameter("cstId")));
        
        request.setAttribute("bct", cst.getBct());
        request.setAttribute("cst", cst);
        
        request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
        
        return mapping.findForward("view");
    }//execute()


}//class CSTViewAction
