package org.pgist.users;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.pgist.funding.UserCommute;
import org.pgist.util.MD5;


/**
 * PGIST User class.
 * 
 * @author kenny
 * 
 * @hibernate.class table="pgist_user"
 */
public class User extends BaseUser {
    
    
    private boolean internal = false;
    
    protected Set<Role> roles = new HashSet<Role>();
    
    private Set<Vehicle> vehicles = new HashSet<Vehicle>();
    
    private UserCommute commute;
    
    /**
     * @return
     * 
     * @hibernate.many-to-one column="commute_id" cascade="none"
     */
    public UserCommute getUserCommute() {
        return commute;
    }


    public void setUserCommute(UserCommute commute) {
        this.commute = commute;
    }    
    
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
     * @hibernate.set lazy="true" table="pgist_user_role_link" cascade="all" order-by="role_id"
     * @hibernate.collection-key column="user_id"
     * @hibernate.collection-many-to-many column="role_id" class="org.pgist.users.Role"
     */
    public Set<Role> getRoles() {
        return roles;
    }


    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    
    
    /**
     * @return
     * @hibernate.set lazy="true" table="pgist_user_vehicle_link" cascade="all" order-by="vehicle_id"
     * @hibernate.collection-key column="user_id"
     * @hibernate.collection-many-to-many column="vehicle_id" class="org.pgist.users.Vehicle"
     */
    public Set<Vehicle> getVehicles() {
        return vehicles;
    }


    public void setVehicles(Set<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void addRole(Role role) {
        roles.add(role);
    }
    
    
    public boolean checkPassword(String providedPWD) {
        return password.equals(MD5.getDigest(providedPWD));
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
    }//getRoleString()


}//class User
