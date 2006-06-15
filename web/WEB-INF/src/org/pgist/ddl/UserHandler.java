package org.pgist.ddl;

import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.pgist.users.Role;
import org.pgist.users.User;


/**
 * 
 * @author kenny
 *
 */
public class UserHandler extends Handler {
    
    
    public void doImports(Element root) throws Exception {
        List users = root.elements("user");
        for (int i=0,n=users.size(); i<n; i++) {
            Element element = (Element) users.get(i);
            
            String loginname = element.elementTextTrim("loginname");
            if (loginname==null || "".equals(loginname)) throw new Exception("loginname is required for user");
            
            User user = getUserByLoginName(loginname);
            if (user==null) {
                user = new User();
                user.setLoginname(loginname);
                user.setDeleted(false);
                user.setInternal(true);
                user.setEnabled(true);
            } else {
                user.getRoles().clear();
            }
            
            String lastname = element.elementTextTrim("lastname");
            if (lastname==null || "".equals(lastname)) throw new Exception("lastname is required for user");
            user.setLastname(lastname);
            
            String firstname = element.elementTextTrim("firstname");
            if (firstname==null || "".equals(firstname)) throw new Exception("firstname is required for user");
            user.setFirstname(firstname);
            
            String password = element.elementTextTrim("password");
            if (password==null || "".equals(password)) throw new Exception("password is required for user");
            user.setPassword(password);
            
            boolean encrypted = "true".equals(element.valueOf("password/@encrypted"));
            if (!encrypted) user.encodePassword();
            
            String email = element.elementTextTrim("email");
            if (email==null || "".equals(email)) throw new Exception("email is required for user");
            user.setEmail(email);
            
            String gender = element.elementTextTrim("gender");
            if (gender==null || "".equals(gender)) throw new Exception("gender is required for user");
            if ("Male".equals(gender)) {
                user.setGender(true);
            } else if ("Female".equals(gender)) {
                user.setGender(false);
            } else {
                throw new Exception("user gender must be either 'Male' or 'Female'");
            }
            
            List roles = element.element("roles").elements("role");
            for (int j=0,m=roles.size(); j<m; j++) {
                Element one = (Element) roles.get(j);
                String roleName = one.getTextTrim();
                Role role = getRoleByName(roleName);
                if (role==null) throw new Exception("can't find role with name: "+roleName);
                user.getRoles().add(role);
            }//for j
            
            saveUser(user);
        }//for i
        
    }//imports()
    
    
    public void doExports(Document document) throws Exception {
        Element root = document.addElement("users");
        
        List<User> users = getUsers();
        for (User user : users) {
            Element one = root.addElement("user");
            
            Element loginname = one.addElement("loginname");
            loginname.setText(user.getLoginname());
            
            Element lastname = one.addElement("lastname");
            lastname.setText(user.getLastname());
            
            Element firstname = one.addElement("firstname");
            firstname.setText(user.getFirstname());
            
            Element password = one.addElement("password");
            password.addAttribute("encrypted", "true");
            password.setText(user.getPassword());
            
            Element email = one.addElement("email");
            email.setText(user.getEmail());
            
            Element gender = one.addElement("gender");
            gender.setText(user.isGender() ? "Male" : "Female");
            
            Element roles = one.addElement("roles");
            for (Iterator iter=user.getRoles().iterator(); iter.hasNext(); ) {
                Role role = (Role) iter.next();
                Element two = roles.addElement("role");
                two.setText(role.getName());
            }//for iter
        }//for user
    }//doExports()
    
    
}//class UserHandler
