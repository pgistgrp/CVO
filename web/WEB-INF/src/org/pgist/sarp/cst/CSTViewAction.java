package org.pgist.sarp.cst;

import java.util.List;

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
    ) throws Exception {
        CST cst = cstService.getCSTById(new Long(request.getParameter("cstId")));
        
        Long userId = null;
        try {
            String str = request.getParameter("userId");
            if (str==null || str.trim().length()==0) {
                userId = WebUtils.currentUserId();
            } else {
                userId = new Long(str);
            }
        } catch (Exception e) {
            throw new Exception("can not find the given user");
        }
        
        User user = systemService.getUserById(userId);
        if (user==null) {
            throw new Exception("can not find the given user");
        } else {
            CategoryReference catref = cst.getCategories().get(userId);
            if (catref==null && WebUtils.currentUserId().equals(userId)) {
                catref = cstService.setRootCategoryReference(cst, user);
                request.setAttribute("published", false);
            } else {
                request.setAttribute("published", true);
            }
            request.setAttribute("root", catref);
        }
        
        List<User> list = cstService.getOtherUsers(cst);
        
        request.setAttribute("user", user);
        request.setAttribute("bct", cst.getBct());
        request.setAttribute("cst", cst);
        request.setAttribute("others", list);
        
        request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
        
        return mapping.findForward("view");
    }//execute()
    
    
}//class CSTViewAction
