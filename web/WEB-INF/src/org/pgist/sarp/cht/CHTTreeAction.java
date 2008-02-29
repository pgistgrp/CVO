package org.pgist.sarp.cht;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.sarp.cst.CategoryReference;
import org.pgist.system.SystemService;
import org.pgist.users.User;
import org.pgist.util.WebUtils;


/**
 * 
 * @author kenny
 *
 */
public class CHTTreeAction extends Action {

    
    private CHTService chtService;
    
    private SystemService systemService;
    
    
    public CHTTreeAction() {
    }
    
    
    public void setChtService(CHTService chtService) {
        this.chtService = chtService;
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
        CHT cht = chtService.getCHTById(new Long(request.getParameter("chtId")));
        
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
            CategoryReference catref = cht.getCategories().get(userId);
            if (catref==null && WebUtils.currentUserId().equals(userId)) {
                request.setAttribute("published", false);
                catref = cht.getCats().get(userId);
                if (catref==null) {
                    catref = chtService.setRootCatReference(cht, user);
                }
            } else {
                request.setAttribute("published", true);
            }
            request.setAttribute("root", catref);
        }
        
        request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
        
        return mapping.findForward("view");
    }//execute()


}//class CHTTreeAction
