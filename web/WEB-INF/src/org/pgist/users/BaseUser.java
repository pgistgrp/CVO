package org.pgist.users;

import java.io.Serializable;

import org.pgist.util.MD5;


/**
 * Basic User Class.
 * @author kenny
 *
 */
public class BaseUser implements Serializable {
    
    
    private Long id;
    
    private String loginname = "";
    
    private String firstname = "";
    
    private String lastname = "";
    
    protected String password = "";
    
    protected String email = "";
    
    protected String homeAddr = "";
    
    protected String city = "";
    
    protected String state = "";
    
    protected String zipcode = "";
    
    protected String ethnicity = "";
    
    protected int familyCount = 1;
    
    protected boolean official = false;
    
    protected boolean transportAgency = false;
    
    protected int agencyType = 1;
    
    protected boolean gender = true;
    
    protected boolean emailNotify = true;
    
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
        this.loginname = loginname.toLowerCase();
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
     * @hibernate.property
     */
    public String getHomeAddr() {
        return homeAddr;
    }


    public void setHomeAddr(String homeAddr) {
        this.homeAddr = homeAddr;
    }


    /**
     * @return
     * @hibernate.property
     */
    public String getCity() {
        return city;
    }


    public void setCity(String city) {
        this.city = city;
    }


    /**
     * @return
     * @hibernate.property
     */
    public String getState() {
        return state;
    }


    public void setState(String state) {
        this.state = state;
    }


    /**
     * @return
     * @hibernate.property
     */
    public String getZipcode() {
        return zipcode;
    }


    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }


    /**
     * @return
     * @hibernate.property
     */
    public String getEthnicity() {
        return ethnicity;
    }


    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
    }


    /**
     * @return
     * @hibernate.property
     */
    public int getFamilyCount() {
        return familyCount;
    }


    public void setFamilyCount(int familyCount) {
        this.familyCount = familyCount;
    }


    /**
     * @return
     * @hibernate.property
     */
    public boolean isOfficial() {
        return official;
    }


    public void setOfficial(boolean official) {
        this.official = official;
    }


    /**
     * @return
     * @hibernate.property
     */
    public boolean isTransportAgency() {
        return transportAgency;
    }


    public void setTransportAgency(boolean transportAgency) {
        this.transportAgency = transportAgency;
    }


    public int getAgencyType() {
        return agencyType;
    }


    public void setAgencyType(int agencyType) {
        this.agencyType = agencyType;
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


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public boolean isEmailNotify() {
        return emailNotify;
    }


    public void setEmailNotify(boolean emailNotify) {
        this.emailNotify = emailNotify;
    }


}//class BaseUser
