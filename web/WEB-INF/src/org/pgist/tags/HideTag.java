package org.pgist.tags;

import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;


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
        HttpSession session = pageContext.getSession();
        
        if (condition!=null) {
            if ("true".equalsIgnoreCase(condition.trim())) {
                return SKIP_BODY;
            } else {
                return EVAL_BODY_INCLUDE;
            }
        }
        
        String loginname = (String) session.getAttribute("userLoginname");
        
        if (owner!=null) {
            if (loginname.equals(owner.trim())) {
                return SKIP_BODY;
            } else {
                return EVAL_BODY_INCLUDE;
            }
        }
        
        if (users!=null) {
            String[] userList = users.split(",");
            boolean valid = false;
            
            for (int i=0; i<userList.length; i++) {
                if (userList[i]!=null) {
                    userList[i] = userList[i].trim();
                    if (!"".equals(userList[i])) {
                        valid = true;
                        if (loginname.equals(userList[i])) {
                            return SKIP_BODY;
                        }
                    }
                }
            }//for i
            
            if (valid) return EVAL_BODY_INCLUDE;
        }
        
        Map map = (Map) session.getAttribute("rolesMap");
        if (roles!=null) {
            String[] roleList = roles.split(",");
            
            for (int i=0; i<roleList.length; i++) {
                if (roleList[i]!=null) {
                    roleList[i] = roleList[i].trim();
                    if (!"".equals(roleList[i]) && map.containsValue(roleList[i])) {
                            return SKIP_BODY;
                    }
                }
            }//for i
        }
        
        return EVAL_BODY_INCLUDE;
    }//doStartTag()
    
    
}//class ShowTag
