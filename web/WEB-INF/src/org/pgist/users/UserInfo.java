package org.pgist.users;

import java.util.HashSet;
import java.util.Set;


/**
 *
 * @author kenny
 *
 */
public class UserInfo {


    private Long id;

    private String loginname;

    private String firstname;

    private String lastname;

    private boolean gender;

    private Set<String> roles = new HashSet<String>();
    private Set<org.pgist.projects.Vehicle> vehicles = new HashSet<org.pgist.projects.Vehicle>();


    public UserInfo(User user) {
        id = user.getId();
        loginname = user.getLoginname();
        firstname = user.getFirstname();
        lastname = user.getLastname();
        gender = user.isGender();

        for (Role role : user.getRoles()) {
            roles.add(role.getName());
        }//for
    }//UserInfo()


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getFirstname() {
        return firstname;
    }


    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }


    public boolean isGender() {
        return gender;
    }


    public void setGender(boolean gender) {
        this.gender = gender;
    }


    public String getLastname() {
        return lastname;
    }


    public void setLastname(String lastname) {
        this.lastname = lastname;
    }


    public String getLoginname() {
        return loginname;
    }

    public Set getVehicles() {
        return vehicles;
    }


    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public void setVehicles(Set vehicles) {
        this.vehicles = vehicles;
    }


    /*
     * ------------------------------------------------------------------------
     */


    public boolean checkRole(String roleName) {
        return roles.contains(roleName);
    }//checkRole()


    public boolean checkLoginname(String loginname) {
        return this.loginname.equals(loginname.trim());
    }//checkLoginname

}//class UserInfo
