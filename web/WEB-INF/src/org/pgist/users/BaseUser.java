package org.pgist.users;

import java.io.Serializable;

import org.pgist.util.MD5;
import org.pgist.system.RegisterObject;

/**
 * <span style="color:red;">POJO:</span> Basic User Class<br>
 * <span style="color:red;">TABLE:</span> pgist_user_base
 * 
 * <p>BaseUser contains sensitive user information, hence should only be used internally.
 * 
 * @author kenny
 * 
 * @hibernate.class table="pgist_user_base"
 */
public class BaseUser implements Serializable {
    
    
    /**
     * <span style="color:blue;">(Column.)</span>
     * User ID. It is a system generated unique id for each user in the system.
     */
    protected Long id;
    
    /**
     * <span style="color:blue;">(Column.)</span>
     * First Name. The first name of a user.
     */
    protected String firstname = "";
    
    /**
     * <span style="color:blue;">(Column.)</span>
     * Last Name. The last name of a user.
     */
    protected String lastname = "";
    
    /**
     * <span style="color:blue;">(Column.)</span>
     * Password. The encrypted password of a user. The password is encrypted by MD5 algorithm.
     */
    protected String password = "";
    
    /**
     * <span style="color:blue;">(Column.)</span>
     * Email. The email address of a user. PGIST system may send all sorts of notification emails to this address.
     */
    protected String email = "";
    
    /**
     * <span style="color:blue;">(Column.)</span>
     * Home Address. The home address of a user.
     */
    protected String homeAddr = "";
    
    /**
     * <span style="color:blue;">(Column.)</span>
     * Home Address 2. The second line of the home address.
     */
    protected String homeAddr2 = "";
    
    /**
     * <span style="color:blue;">(Column.)</span>
     * City. The city name of a user's home.
     */
    protected String city = "";
    
    /**
     * <span style="color:blue;">(Column.)</span>
     * Work City. The city name of a user's work location.
     */
    protected String workCity = "";
    
    /**
     * <span style="color:blue;">(Column.)</span>
     * State. The state name of a user's home.
     */
    protected String state = "";
    
    /**
     * <span style="color:blue;">(Column.)</span>
     * Zip code. The zip code of a user's home.
     */
    protected String zipcode = "";
    
    /**
     * <span style="color:blue;">(Column.)</span>
     * Zip code of working place of a user.
     */
    protected String workZipcode = "";
    
    /**
     * <span style="color:blue;">(Column.)</span>
     * Ethnicity of a user.
     */
    protected String ethnicity = "";
    
    /**
     * <span style="color:blue;">(Column.)</span>
     * How many people are living in this user's house.
     */
    protected int familyCount = 1;
    
    /**
     * <span style="color:blue;">(Column.)</span>
     * The income of a user. Amount is in dollar.
     */
    protected float income;
    
    /**
     * <span style="color:blue;">(Column.)</span>
     * The income range of a user. Allowable ranges (Faked at present):
     * <ul>
     *   <li>50,000-75,000</li>
     * </ul>
     */
    protected String incomeRange = "";
    
    /**
     * <span style="color:blue;">(Column.)</span>
     * Profile Description. User's personal profile description:
     */
    protected String profileDesc = "";
    
    /**
     * <span style="color:blue;">(Column.)</span>
     * Vocation. The users Job title:
     */
    protected String vocation = "";
    
    /**
     * <span style="color:blue;">(Column.)</span>
     * WebQ. Users assigned WebQ.
     */
    protected RegisterObject webQ;
    



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
    public String getHomeAddr2() {
        return homeAddr2;
    }


    public void setHomeAddr2(String homeAddr2) {
        this.homeAddr2 = homeAddr2;
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
    public String getWorkCity() {
        return workCity;
    }


    public void setWorkCity(String workCity) {
        this.workCity = workCity;
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
    
    
    /**
     * @return
     * @hibernate.property unique="false" not-null="false"
     */
    public String getProfileDesc() {
        return profileDesc;
    }


    public void setProfileDesc(String profileDesc) {
        this.profileDesc = profileDesc;
    }

    
    /**
     * @return
     * @hibernate.property unique="false" not-null="false"
     */
    public String getVocation() {
        return vocation;
    }


    public void setVocation(String vocation) {
        this.vocation = vocation;
    }
    
    /**
     * @return
     * @hibernate.many-to-one column="webq_id" lazy="true"
     */
    public RegisterObject getWebQ() {
		return webQ;
	}


	public void setWebQ(RegisterObject webQ) {
		this.webQ = webQ;
	}
    
    
}//class BaseUser
