package org.pgist.sarp.drt;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.sarp.cst.CST;
import org.pgist.sarp.cst.CSTService;
import org.pgist.sarp.cst.CategoryReference;
import org.pgist.system.SystemService;
import org.pgist.users.User;
import org.pgist.util.WebUtils;


/**
 * 
 * @author kenny
 *
 */
public class TctTreeAction extends Action {

    
    private DRTService drtService;
    
    private SystemService systemService;
    
    
    public TctTreeAction() {
    }
    
    
    public void setDrtService(DRTService drtService) {
        this.drtService = drtService;
    }


    public SystemService getSystemService() {
        return systemService;
    }


    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void setSystemService(SystemService systemService) {
        this.systemService = systemService;
    }


    public ActionForward execute(
            ActionMapping mapping,
            ActionForm form,
            javax.servlet.http.HttpServletRequest request,
            javax.servlet.http.HttpServletResponse response
    ) throws java.lang.Exception {
        InfoObject infoObject = drtService.getInfoObjectById(new Long(request.getParameter("oId")));
        
        if (!WebUtils.checkRole("moderator" )) {
            return mapping.findForward("error");
        }
        
        CST cst = (CST) infoObject.getTarget();
        
        CategoryReference catref = cst.getWinnerCategory().getCatRef();
        request.setAttribute("published", false);
        request.setAttribute("root", catref);
        request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
        
        return mapping.findForward("view");
    }//execute()


}//class CatsTreeAction
