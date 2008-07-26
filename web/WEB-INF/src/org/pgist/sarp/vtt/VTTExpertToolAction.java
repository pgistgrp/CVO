package org.pgist.sarp.vtt;

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
        
        Long userId = WebUtils.currentUserId();
        User user = systemService.getUserById(userId);
        if (user==null) {
            throw new Exception("can not find the given user");
        }
        
        if (!WebUtils.checkRole("expert")) {
            throw new Exception("You have no access to this function.");
        }
        
        List<User> list = vttService.getOtherUsers(vtt);
        
        request.setAttribute("user", user);
        request.setAttribute("vtt", vtt);
        request.setAttribute("others", list);
        
        request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
        
        return mapping.findForward("main");
    }//execute()


}//class VTTExpertToolAction
