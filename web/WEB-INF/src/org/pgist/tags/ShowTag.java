package org.pgist.tags;

import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.pgist.users.User;


/**
 * 
 * @author kenny
 *
 */
public class ShowTag extends TagSupport {

    
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
        
        User user = (User) session.getAttribute("user");
        if (user==null) return SKIP_BODY;
        
        if (condition!=null) {
            if ("true".equalsIgnoreCase(condition.trim())) {
                return EVAL_BODY_INCLUDE;
            } else {
                return SKIP_BODY;
            }
        }
        
        String loginname = (String) session.getAttribute("userLoginname");
        
        if (owner!=null) {
            if (loginname.equals(owner.trim())) {
                return EVAL_BODY_INCLUDE;
            } else {
                return SKIP_BODY;
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
                            return EVAL_BODY_INCLUDE;
                        }
                    }
                }
            }//for i
            
            if (valid) return SKIP_BODY;
        }
        
        Map map = (Map) session.getAttribute("rolesMap");
        if (roles!=null) {
            String[] roleList = roles.split(",");
            
            for (int i=0; i<roleList.length; i++) {
                if (roleList[i]!=null) {
                    roleList[i] = roleList[i].trim();
                    if (!"".equals(roleList[i]) && map.containsValue(roleList[i])) {
                            return EVAL_BODY_INCLUDE;
                    }
                }
            }//for i
        }
        
        return SKIP_BODY;
    }//doStartTag()
    
    
}//class ShowTag
