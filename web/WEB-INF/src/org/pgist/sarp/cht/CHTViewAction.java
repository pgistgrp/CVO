package org.pgist.sarp.cht;

import java.util.List;

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
public class CHTViewAction extends Action {

    
    private CHTService chtService;
    
    private SystemService systemService;
    
    
    public CHTViewAction() {
    }
    
    
    public void setChtService(CHTService chtService) {
        this.chtService = chtService;
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
        CHT cht = chtService.getCHTById(new Long(request.getParameter("chtId")));
        
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
        
        List<User> list = chtService.getOtherUsers(cht);
        
        request.setAttribute("ignoreRoot", cht.getIgnores().get(userId));
        request.setAttribute("user", user);
        request.setAttribute("cst", cht.getCst());
        request.setAttribute("cht", cht);
        request.setAttribute("others", list);
        
        request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
        
        return mapping.findForward("view");
    }//execute()


}//class CSTViewAction
