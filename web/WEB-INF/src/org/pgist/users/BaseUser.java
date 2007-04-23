package org.pgist.users;

import java.io.Serializable;

import org.pgist.util.MD5;


/**
 * Basic User Class.
 * @author kenny
 * 
 * @hibernate.class table="pgist_user_base"
 */
public class BaseUser implements Serializable {
    
    
    private Long id;
    
    private String firstname = "";
    
    private String lastname = "";
    
    protected String password = "";
    
    protected String email = "";
    
    protected String homeAddr = "";
    
    protected String city = "";
    
    protected String state = "";
    
    protected String zipcode = "";
    
    protected String workZipcode = "";
    
    protected String ethnicity = "";
    
    protected int familyCount = 1;
    
    protected float income;
    
    protected String incomeRange = "";
    
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
    public String getWorkZipcode() {
        return workZipcode;
    }


    public void setWorkZipcode(String workZipcode) {
        this.workZipcode = workZipcode;
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
    public float getIncome() {
        return income;
    }


    public void setIncome(float income) {
        this.income = income;
    }


    /**
     * @return
     * @hibernate.property unique="false" not-null="false"
     */
    public String getIncomeRange() {
        return incomeRange;
    }


    public void setIncomeRange(String incomeRange) {
        this.incomeRange = incomeRange;
    }


}//class BaseUser
