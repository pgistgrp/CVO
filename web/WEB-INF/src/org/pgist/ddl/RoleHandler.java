package org.pgist.ddl;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.pgist.users.Role;


/**
 * 
 * @author kenny
 *
 */
public class RoleHandler extends XMLHandler {
    
    
    public void doImports(Element root) throws Exception {
        List roles = root.elements("role");
        for (int i=0,n=roles.size(); i<n; i++) {
            Element element = (Element) roles.get(i);
            
            String name = element.getTextTrim();
            if (name==null || "".equals(name)) throw new Exception("name is required for role");
            
            Role role = getRoleByName(name);
            if (role==null) {
                role = new Role();
                role.setName(name);
                role.setDescription(name);
                role.setDeleted(false);
                role.setInternal(true);
                saveRole(role);
            }
        }//for i
    }//imports()
    
    
    public void doExports(Document document) throws Exception {
        Element root = document.addElement("roles");
        
        List<Role> roles = getRoles();
        
        for (Role role : roles) {
            Element one = root.addElement("role");
            one.setText(role.getName());
        }//for
    }//doExports()


}//class RoleHandler
