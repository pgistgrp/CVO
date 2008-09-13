package org.pgist.sarp.vtt;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.system.SystemService;
import org.pgist.users.Role;
import org.pgist.users.User;
import org.pgist.util.WebUtils;


/**
 * 
 * @author kenny
 *
 */
public class VTTExpertToolAction extends Action {

    
    private VTTService vttService;
    
    private SystemService systemService;
    
    
    public VTTExpertToolAction() {
    }
    
    
    public void setVttService(VTTService vttService) {
        this.vttService = vttService;
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
        VTT vtt = vttService.getVTTById(new Long(request.getParameter("vttId")));
        
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
        User currentUser = systemService.getUserById(WebUtils.currentUserId());
        if (user==null) {
            throw new Exception("can not find the given user");
        }
        
        if (!WebUtils.checkRole("expert")) {
            throw new Exception("You have no access to this function.");
        }
        
        request.setAttribute("user", user);
        request.setAttribute("currentUser", currentUser);
        request.setAttribute("vtt", vtt);
        List<User> users = new ArrayList<User>(vtt.getUsers());
        Set<User> toBeRemoved = new HashSet<User>();
        users.remove(currentUser);
        for (User one : users) {
            for (Role role : one.getRoles()) {
                if ("expert".equals(role.getName())) {
                    toBeRemoved.add(user);
                    break;
                }
            }
        }
        users.removeAll(toBeRemoved);
        request.setAttribute("others", users);
        
        request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
        
        return mapping.findForward("main");
    }//execute()


}//class VTTExpertToolAction
