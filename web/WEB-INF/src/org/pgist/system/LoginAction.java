package org.pgist.system;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.users.Role;
import org.pgist.users.User;


/**
 * 
 * @author kenny
 *
 */
public class LoginAction extends Action {

    
    private UserDAO userDAO;
    
    
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    
    public ActionForward execute(
            ActionMapping mapping,
            ActionForm form,
            javax.servlet.http.HttpServletRequest request,
            javax.servlet.http.HttpServletResponse response
    ) throws java.lang.Exception {
        //Invalidate the Session
        HttpSession session = request.getSession(false);
        if (session!=null) session.invalidate();
        
        UserForm uform = (UserForm) form;
        
        String loginname = uform.getUser().getLoginname();
        if (loginname==null || "".equals(loginname)) {
            return mapping.findForward("login");
        }
        
        String password = uform.getUser().getPassword();
        if (password==null || "".equals(password)) {
            return mapping.findForward("login");
        }
        
        User user = userDAO.getUserByName(loginname, true, false);
        if (user!=null && user.checkPassword(password)) {
            session = request.getSession(true);
            session.setAttribute("user", user);
            session.setAttribute("userId", user.getId());
            session.setAttribute("userLoginname", user.getLoginname());
            
            Map map = new HashMap();
            for (Iterator iter=user.getRoles().iterator(); iter.hasNext(); ) {
                Role role = (Role) iter.next();
                map.put(role.getId(), role.getName());
            }//for iter
            
            session.setAttribute("rolesMap", map);
            
            return mapping.findForward("main");
        }
        
        return mapping.findForward("login");
    }//execute()
    
    
}
