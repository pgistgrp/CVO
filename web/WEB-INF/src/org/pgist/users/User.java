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
 * @hibernate.joined-subclass name="User" table="pgist_user"
 *                            lazy="true" proxy="org.pgist.users.User"
 * @hibernate.joined-subclass-key column="id"
 */
public class User extends BaseUser {
    
	private String loginname = "";
	
	protected boolean official = false;
    
    protected boolean transportAgency = false;
    
    protected int agencyType = 1;
    
    protected boolean gender = true;
    
    protected boolean emailNotify = true;
    
    protected boolean quota = false;
    
    protected boolean regComplete = false;
    
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
