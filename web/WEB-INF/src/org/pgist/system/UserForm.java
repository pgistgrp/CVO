package org.pgist.system;

import org.apache.struts.action.ActionForm;
import org.pgist.users.User;


public class UserForm extends ActionForm {

    
    private static final long serialVersionUID = 5443268954560626922L;
    
    private User user = new User();
    
    private String password1;
    
    private String password2;
    
    private String email;
    
    private String address1;
    
    private String address2;
    
    private String state;
    
    private String currentpassword; //Only used for profile update
    
    private String ethnicity1;
    
    private String reason;

    private String city;
    
    private String zipcode;
    
    private String workCity;
    
    private String workZipcode;
    
    private String vocation;
    
    private String primaryTransport;
    
    private String profileDesc;
    
    private boolean emailNotify;
    
    private boolean emailNotifyDisc;
    
    private boolean save;
    
    private boolean editPassword;
    
    private String test;
    
    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
    }


    public String getPassword1() {
        return password1;
    }

   
    public void setPassword1(String password1) {
        this.password1 = password1;
    }
    
    
    public String getPassword2() {
        return password2;
    }

   
    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    
    public String getEmail() {
        return email;
    }

   
    public void setEmail(String email) {
        this.email = email;
    }
    
    
    public String getAddress1() {
        return address1;
    }

   
    public void setAddress1(String address1) {
        this.address1 = address1;
    }
    
    
    public String getAddress2() {
        return address2;
    }

   
    public void setAddress2(String address2) {
        this.address2 = address2;
    }
    

    public String getState() {
        return state;
    }

   
    public void setState(String state) {
        this.state = state;
    }
    
    
    public String test() {
        return test;
    }

   
    public void setTest(String test) {
        this.test = test;
    }
    
    
    public void setCurrentpassword(String currentpassword) {
        this.currentpassword = currentpassword;
    }
    
    public String getCurrentpassword() {
    	return currentpassword;
    }
    
    
    public String getEthnicity1() {
        return ethnicity1;
    }


    public void setEthnicity1(String ethnicity1) {
        this.ethnicity1 = ethnicity1;
    }


    public String getReason() {
        return reason;
    }


    public void setReason(String reason) {
        this.reason = reason;
    }

    
    public String getCity() {
        return city;
    }


    public void setCity(String city) {
        this.city = city;
    }
    
    
    public String getZipcode() {
        return zipcode;
    }


    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
    
    
    public String getWorkCity() {
        return workCity;
    }


    public void setWorkCity(String workCity) {
        this.workCity = workCity;
    }
    
    
    public String getWorkZipcode() {
        return workZipcode;
    }


    public void setWorkZipcode(String workZipcode) {
        this.workZipcode = workZipcode;
    }
    
    
    public String getVocation() {
        return vocation;
    }


    public void setVocation(String vocation) {
        this.vocation = vocation;
    }
    
    
    public String getPrimaryTransport() {
        return primaryTransport;
    }


    public void setPrimaryTransport(String primaryTransport) {
        this.primaryTransport = primaryTransport;
    }
    
    
    public String getProfileDesc() {
        return profileDesc;
    }


    public void setProfileDesc(String profileDesc) {
        this.profileDesc = profileDesc;
    }
    
    
    public boolean isEmailNotify() {
        return emailNotify;
    }


    public void setEmailNotify(boolean emailNotify) {
        this.emailNotify = emailNotify;
    }
    
    
    public boolean isEmailNotifyDisc() {
        return emailNotifyDisc;
    }


    public void setEmailNotifyDisc(boolean emailNotifyDisc) {
        this.emailNotifyDisc = emailNotifyDisc;
    }
    
    
    public boolean isSave() {
        return save;
    }


    public void setSave(boolean save) {
        this.save = save;
    }
    
    
    public boolean isEditPassword() {
        return editPassword;
    }


    public void setEditPassword(boolean editPassword) {
        this.editPassword = editPassword;
    }


}//class UserForm
