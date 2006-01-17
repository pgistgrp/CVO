package org.pgist.users;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.pgist.util.MD5;


/**
 * PGIST User class.
 * @author kenny
 * @hibernate.class table="pgist_user"
 */
public class User extends BaseUser {
    
    
    private boolean internal = false;
    protected Set roles = new HashSet();
    
    
    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public boolean isInternal() {
        return internal;
    }


    public void setInternal(boolean internal) {
        this.internal = internal;
    }


    /**
     * @return
     * @hibernate.set lazy="true" table="pgist_user_role_link" cascade="none" order-by="role_id"
     * @hibernate.collection-key  column="user_id"
     * @hibernate.collection-many-to-many column="role_id" class="org.pgist.users.Role"
     */
    public Set getRoles() {
        return roles;
    }


    public void setRoles(Set roles) {
        this.roles = roles;
    }
    
    
    public void addRole(Role role) {
        roles.add(role);
    }
    
    
    public boolean checkPassword(String providedPWD) {
        return this.password.equals(MD5.getDigest(providedPWD));
    }
    
    
    public String getRoleString() {
        StringBuffer sb = new StringBuffer();
        
        boolean first = true;
        for (Iterator iter=roles.iterator(); iter.hasNext(); ) {
            Role role = (Role) iter.next();
            if (first) {
                first = false;
            } else {
                sb.append(", ");
            }
            sb.append(role.getName());
        }//for iter
        
        return sb.toString();
    }
    
    
}//class User

