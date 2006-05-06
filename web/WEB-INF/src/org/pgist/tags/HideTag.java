package org.pgist.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.pgist.users.UserInfo;
import org.pgist.util.WebUtils;


/**
 * 
 * @author kenny
 *
 */
public class HideTag extends TagSupport {

    
    private static final long serialVersionUID = -4358153734927746923L;
    
    private String condition;
    
    private String owner;
    
    private String roles;
    
    private String users;
    
    
    public void setCondition(String condition) {
        this.condition = condition;
    }


    public void setOwner(String owner) {
        this.owner = owner;
    }


    public void setRoles(String roles) {
        this.roles = roles;
    }


    public void setUsers(String users) {
        this.users = users;
    }


    public int doStartTag() throws JspException {
        if (condition!=null) {
            if ("true".equalsIgnoreCase(condition.trim())) {
                return SKIP_BODY;
            } else {
                return EVAL_BODY_INCLUDE;
            }
        }
        
        UserInfo userInfo = WebUtils.currentUser();
        
        if (owner!=null) {
            if (userInfo.checkLoginname(owner.trim())) {
                return SKIP_BODY;
            } else {
                return EVAL_BODY_INCLUDE;
            }
        }
        
        if (users!=null) {
            boolean valid = false;
            for (String loginname : users.split(",")) {
                if (loginname==null || "".equals(loginname.trim())) continue;
                valid = true;
                if (userInfo.checkLoginname(loginname.trim())) return SKIP_BODY;
            }//for
            
            if (valid) return EVAL_BODY_INCLUDE;
        }
        
        for (String roleName : roles.split(",")) {
            if (roleName==null || "".equals(roleName.trim())) continue;
            if (WebUtils.checkRole(roleName.trim())) return SKIP_BODY;
        }//for
        
        return EVAL_BODY_INCLUDE;
    }//doStartTag()
    
    
}//class ShowTag
