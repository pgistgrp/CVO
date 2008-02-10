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
            if (catref==null) {
                catref = cstService.setRootCategoryReference(cst, user);
            }
            request.setAttribute("root", catref);
        }
        
        request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
        
        return mapping.findForward("view");
    }//execute()


}//class CatsTreeAction
