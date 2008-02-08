package org.pgist.sarp.cst;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.system.SystemService;
import org.pgist.users.User;
import org.pgist.util.WebUtils;


/**
 * 
 * @author kenny
 *
 */
public class CSTViewAction extends Action {

    
    private CSTService cstService;
    
    private SystemService systemService;
    
    
    public CSTViewAction() {
    }
    
    
    public void setCstService(CSTService cstService) {
        this.cstService = cstService;
    }


    public void setSystemService(SystemService systemService) {
        this.systemService = systemService;
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
        
        Long userId = null;
        try {
            userId = new Long(request.getParameter("userId"));
        } catch (Exception e) {
            userId = WebUtils.currentUserId();
        }
        
        User user = systemService.getUserById(userId);
        if (user==null) {
            user = systemService.getCurrentUser();
        }
        
        request.setAttribute("user", user);
        request.setAttribute("bct", cst.getBct());
        request.setAttribute("cst", cst);
        
        request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
        
        return mapping.findForward("view");
    }//execute()


}//class CSTViewAction
