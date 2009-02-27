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
        
        Long targetUserId = null;
        try {
            String str = request.getParameter("targetUserId");
            if (str==null || str.trim().length()==0) {
                targetUserId = WebUtils.currentUserId();
            } else {
                targetUserId = new Long(str);
            }
        } catch (Exception e) {
            throw new Exception("can not find the given user");
        }
        
        User targetUser = systemService.getUserById(targetUserId);
        User currentUser = systemService.getUserById(WebUtils.currentUserId());
        if (targetUser==null) {
            throw new Exception("can not find the given user");
        }
        
        if (!WebUtils.checkRole("expert")) {
            throw new Exception("You have no access to this function.");
        }
        
        request.setAttribute("targetUser", targetUser);
        request.setAttribute("currentUser", currentUser);
        request.setAttribute("vtt", vtt);
        List<User> users = new ArrayList<User>(vtt.getExperts());
        
        if (targetUser==currentUser) {
            request.setAttribute("isOwner", true);
            request.setAttribute("published", users.contains(targetUser));
        } else {
            request.setAttribute("isOwner", false);
            request.setAttribute("published", false);
        }
        
        users.remove(currentUser);
        request.setAttribute("others", users);
        request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
        
        return mapping.findForward("main");
    }//execute()


}//class VTTExpertToolAction
