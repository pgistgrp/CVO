package org.pgist.system;

import org.apache.struts.action.ActionForm;
import org.pgist.users.User;


public class UserForm extends ActionForm {

    
    private static final long serialVersionUID = 5443268954560626922L;
    
    private User user = new User();
    
    private String password1;
    
    private String email1;
    
    private String address1;
    
    private String address2;
    
    private String currentpassword; //Only used for profile update
    
    private String ethnicity1;
    
    private String reason;
    
    private boolean save;
    
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

    
    public String getEmail1() {
        return email1;
    }

   
    public void setEmail1(String email1) {
        this.email1 = email1;
    }
    
    
    public String getaddress1() {
        return address1;
    }

   
    public void setAddress1(String address1) {
        this.address1 = address1;
    }
    
    
    public String getaddress2() {
        return address2;
    }

   
    public void setAddress2(String address2) {
        this.address2 = address2;
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


    public boolean isSave() {
        return save;
    }


    public void setSave(boolean save) {
        this.save = save;
    }


}//class UserForm
