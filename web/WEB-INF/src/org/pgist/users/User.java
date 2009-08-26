package org.pgist.users;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.List;
import java.util.Arrays;

import org.pgist.funding.UserCommute;
import org.pgist.system.RegisterObject;
import org.pgist.util.MD5;


/**
 * <span style="color:red;">POJO</span>: PGIST User Class<br>
 * <span style="color:red;">TABLE</span>: pgist_user
 * 
 * <p>User class contains insensitive user information, and inherited sensitive user information
 * from its parent class User.
 * 
 * @author kenny
 * 
 * @hibernate.joined-subclass name="User" table="pgist_user"
 *                            lazy="true" proxy="org.pgist.users.User"
 * @hibernate.joined-subclass-key column="id"
 */
public class User extends BaseUser {
    
    
    /**
     * <span style="color:blue;">(Column.)</span>
     * Login name of a user. Login name is unique for the whole system.
     */
    protected String loginname = "";
	
    /**
     * <span style="color:blue;">(Column.)</span>
     * Not used yet. Maybe deleted later.
     */
	protected boolean official = false;
    
    /**
     * <span style="color:blue;">(Column.)</span>
     * Whether or not the user belongs to a transport agency.
     */
    protected boolean transportAgency = false;
    
    /**
     * <span style="color:blue;">(Column.)</span>
     * The agency type. Valid only when field transportagency equals to false.
     */
    protected int agencyType = 1;
    
    /**
     * <span style="color:blue;">(Column.)</span>
     * Gender of a user. True for male, false for female.
     */
    protected boolean gender = true;
    
    /**
     * <span style="color:blue;">(Column.)</span>
     * A global flag to denote if the user would like to receive all email notification.
     */
    protected boolean emailNotify = true;
    
    /**
     * <span style="color:blue;">(Column.)</span>
     * Receive email notifications for disccusion post responses:
     */
    protected boolean emailNotifyDisc = false;
    
    /**
     * <span style="color:blue;">(Column.)</span>
     * Whether the user is qualified to be paid by PGIST. 
     */
    protected boolean quota = false;
    
    /**
     * <span style="color:blue;">(Column.)</span>
     * A flag indicate if the registration completed or not.
     */
    protected boolean regComplete = false;
    
    /**
     * <span style="color:blue;">(Column.)</span>
     * Whether or not the user has agreed to be recorded.
     */
    protected String recording = "Not Eligible";
    
    /**
     * <span style="color:blue;">(Column.)</span>
     * Whether or not the user has agreed to be interviewed.
     */
    protected String interview = "Not Eligible";
   
    /**
     * <span style="color:blue;">(Column.)</span>
     * Whether or not the user has consented to quota.
     */
    protected String consented = "Non-Quota";
    
    /**
     * <span style="color:blue;">(Column.)</span>
     * The ID of the user's county. 
     */
    protected Long countyId;
   
    /**
     * <span style="color:blue;">(Column.)</span>
     * How many days the user drives per week.
     */
    protected int driveDays;
    
    /**
     * <span style="color:blue;">(Column.)</span>
     * How many days the user carpools per week.
     */
    protected int carpoolDays;
    
    /**
     * <span style="color:blue;">(Column.)</span>
     * How many people the user carpools with.
     */
    protected int carpoolPeople;
    
    /**
     * <span style="color:blue;">(Column.)</span>
     * How many days per week a user rides the bus.
     */
    protected int busDays;
    
    /**
     * <span style="color:blue;">(Column.)</span>
     * How many days per week a user walks.
     */
    protected int walkDays;
    
    /**
     * <span style="color:blue;">(Column.)</span>
     * How many days per week a user bikes.
     */
    protected int bikeDays;
    
    /**
     * <span style="color:blue;">(Column.)</span>
     * If the user account is enabled. Used for locking accounts.
     */
    private boolean enabled;
    
    /**
     * <span style="color:blue;">(Column.)</span>
     * Flag the account as deleted.
     */
    private boolean deleted;
    
    /**
     * <span style="color:blue;">(Column.)</span>
     * To be completed by John.
     */
    private boolean internal = false;
    
    /**
     * <span style="color:blue;">(One To Many Association.)</span>
     * The Role of the user. A users role is their permissions level. IE: guest, participant, moderator, admin.
     */
    protected Set<Role> roles = new HashSet<Role>();
    
    /**
     * <span style="color:blue;">(Many To Many Association.)</span>
     * The association of the user. A users association. IE: political, geographic, etc.
     */
    protected Set<Assoc> assocs = new HashSet<Assoc>();
    
    /**
     * <span style="color:blue;">(One To Many Association.)</span>
     * The vehicles the user owns
     */
    private Set<Vehicle> vehicles = new HashSet<Vehicle>();
    
    /**
     * <span style="color:blue;">(Column.)</span>
     * To be complete by John.
     */
    private UserCommute commute;
    
  
    /**
     * <span style="color:blue;">(Column.)</span>
     * primaryTransport. The user's primary method of transportation:
     */
    protected RegisterObject primaryTransport;
    
    protected int age;
    
    protected int education;
    
    
    /**
     * @return
     * 
     * hibernate.many-to-one column="commute_id" cascade="all"
     */
    public UserCommute getUserCommute() {
        return commute;
    }


    public void setUserCommute(UserCommute commute) {
        this.commute = commute;
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
     * @hibernate.set lazy="true" table="pgist_user_assoc_link" cascade="all" order-by="assoc_id"
     * @hibernate.collection-key column="user_id"
     * @hibernate.collection-many-to-many column="assoc_id" class="org.pgist.users.Assoc"
     */
    public Set<Assoc> getAssocs() {
        return assocs;
    }


    public void setAssocs(Set<Assoc> assocs) {
        this.assocs = assocs;
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
    public boolean isEmailNotifyDisc() {
        return emailNotifyDisc;
    }


    public void setEmailNotifyDisc(boolean emailNotifyDisc) {
        this.emailNotifyDisc = emailNotifyDisc;
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
    public boolean getRegComplete() {
        return regComplete;
    }


    public void setRegComplete(boolean regComplete) {
        this.regComplete = regComplete;
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
     * hibernate.set lazy="true" cascade="all" order-by="id"
     * hibernate.collection-key column="owner_id"
     * hibernate.collection-one-to-many class="org.pgist.users.Vehicle"
     */
    public Set<Vehicle> getVehicles() {
        return vehicles;
    }


    public void setVehicles(Set<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
    
    
    /**
     * @return
     * hibernate.many-to-one column="primary_transport_id" lazy="true"
     */
	public RegisterObject getPrimaryTransport() {
		return primaryTransport;
	}


	public void setPrimaryTransport(RegisterObject primaryTransport) {
		this.primaryTransport = primaryTransport;
	}

	
    /**
     * @return
     * @hibernate.property
     */
    public int getAge() {
        return age;
    }


    public void setAge(int age) {
        this.age = age;
    }


    /**
     * @return
     * @hibernate.property
     */
    public int getEducation() {
        return education;
    }


    public void setEducation(int education) {
        this.education = education;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void addRole(Role role) {
        roles.add(role);
    }

    public void addAssoc(Assoc assoc) {
        assocs.add(assoc);
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
    
    public String getAssocString() {
        StringBuffer sb = new StringBuffer();
        
        boolean first = true;
        for (Iterator iter=assocs.iterator(); iter.hasNext(); ) {
            Assoc assoc = (Assoc) iter.next();
            if (first) {
                first = false;
            } else {
                sb.append(", ");
            }
            sb.append(assoc.getName());
        }//for iter
        
        return sb.toString();
    }//getRoleString()




}//class User
