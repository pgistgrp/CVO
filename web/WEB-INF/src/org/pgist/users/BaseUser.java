package org.pgist.users;

import org.pgist.util.MD5;


/**
 * Basic User Class.
 * @author kenny
 *
 */
public class BaseUser {
    
    
    private Long id;
    
    private String loginname = "";
    
    private String firstname = "";
    
    private String lastname = "";
    
    protected String password = "";
    
    protected String email = "";
    
    protected boolean gender = true;
    
    private boolean enabled;
    
    private boolean deleted;
    
    
    /**
     * @return
     * @hibernate.id generator-class="native"
     */
    public Long getId() {
        return id;
    }
    
    
    public void setId(Long id) {
        this.id = id;
    }
    
    
    /**
     * @return
     * @hibernate.property unique="true" not-null="true"
     */
    public String getLoginname() {
        return loginname;
    }
    
    
    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }


    /**
     * @return
     * @hibernate.property unique="false" not-null="true"
     */
    public String getFirstname() {
        return firstname;
    }


    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }


    /**
     * @return
     * @hibernate.property unique="false" not-null="true"
     */
    public String getLastname() {
        return lastname;
    }


    public void setLastname(String lastname) {
        this.lastname = lastname;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public void encodePassword() {
        password = MD5.getDigest(password);
    }

    
    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public boolean isEnabled() {
        return enabled;
    }


    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public boolean isDeleted() {
        return deleted;
    }


    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public boolean isGender() {
        return gender;
    }


    public void setGender(boolean gender) {
        this.gender = gender;
    }


}//class BaseUser
