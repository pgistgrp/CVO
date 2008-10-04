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
public class CatsTreeAction extends Action {

    
    private CSTService cstService;
    
    private SystemService systemService;
    
    
    public CatsTreeAction() {
    }
    
    
    public void setCstService(CSTService cstService) {
        this.cstService = cstService;
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
        CST cst = cstService.getCSTById(new Long(request.getParameter("cstId")));
        
        Long oid = null;
        if (cst.isClosed()) {
            // check oid
            try {
                oid = new Long(request.getParameter("oid"));
            } catch (Exception e) {
            }
        }
        
        if (oid!=null) {
            
            // only moderator can access
            if (!WebUtils.checkRole("moderator")) {
                return mapping.findForward("error");
            }
            
            request.setAttribute("published", true);
            request.setAttribute("root", cst.getWinnerCategory().getCatRef());
            
            request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
            
            return mapping.findForward("view");
        }
        
        Long userId = null;
        try {
        	userId = new Long(request.getParameter("userId"));
        } catch (Exception e) {
		}
        
        if (userId==null) {
            userId = WebUtils.currentUserId();
        }
        
        User user = systemService.getUserById(userId);
        
        if (user!=null) {
            CategoryReference catref = cst.getCategories().get(userId);
            if (catref==null && WebUtils.currentUserId().equals(userId)) {
                request.setAttribute("published", false);
                catref = cst.getCats().get(userId);
                if (catref==null) {
                    catref = cstService.setRootCatReference(cst, user);
                }
            } else {
                request.setAttribute("published", true);
            }
            request.setAttribute("root", catref);
        }
        
        request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
        
        return mapping.findForward("view");
    }//execute()


}//class CatsTreeAction
