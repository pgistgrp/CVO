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
    
    protected float income;
    
    protected boolean official = false;
    
    protected boolean transportAgency = false;
    
    protected int agencyType = 1;
    
    protected boolean gender = true;
    
    protected boolean emailNotify = true;
    
    protected boolean quota = false;
    
    protected String recording = "Not Eligible";
    
    protected String interview = "Not Eligible";
   
    protected String consented = "Non-Quota";
    
    protected Long countyId;
   
    protected int driveDays;
    
    protected int carpoolDays;
    
    protected int carpoolPeople;
    
    protected int busDays;
    
    protected int walkDays;
    
    protected int bikeDays;
    
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
    public float getIncome() {
        return income;
    }


    public void setIncome(float income) {
        this.income = income;
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


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public int getBikeDays() {
        return bikeDays;
    }


    public void setBikeDays(int bikeDays) {
        this.bikeDays = bikeDays;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public int getBusDays() {
        return busDays;
    }


    public void setBusDays(int busDays) {
        this.busDays = busDays;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public int getCarpoolDays() {
        return carpoolDays;
    }


    public void setCarpoolDays(int carpoolDays) {
        this.carpoolDays = carpoolDays;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public int getCarpoolPeople() {
        return carpoolPeople;
    }


    public void setCarpoolPeople(int carpoolPeople) {
        this.carpoolPeople = carpoolPeople;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public int getDriveDays() {
        return driveDays;
    }


    public void setDriveDays(int driveDays) {
        this.driveDays = driveDays;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public int getWalkDays() {
        return walkDays;
    }


    public void setWalkDays(int walkDays) {
        this.walkDays = walkDays;
    }
    
    
    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public boolean getQuota() {
        return quota;
    }


    public void setQuota(boolean quota) {
        this.quota = quota;
    }
    
    
    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public String getRecording() {
        return recording;
    }


    public void setRecording(String recording) {
        this.recording = recording;
    }
    
    
    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public String getInterview() {
        return interview;
    }


    public void setInterview(String interview) {
        this.interview = interview;
    }

    
    /**
     * @return
     * @hibernate.property not-null="true"
     */    
    public String getConsented() {
        return consented;
    }


    public void setConsented(String consented) {
        this.consented = consented;
    }

    
    /**
     * @return
     * @hibernate.property not-null="false"
     */    
    public Long getCountyId() {
        return countyId;
    }


    public void setCountyId(Long countyId) {
        this.countyId = countyId;
    }


}//class BaseUser
