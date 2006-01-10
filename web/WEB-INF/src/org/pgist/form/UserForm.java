package org.pgist.form;

import org.apache.struts.action.ActionForm;


public class UserForm extends ActionForm {

    
    private static final long serialVersionUID = 5443268954560626922L;

    private Long id;
    
    private String loginname;
    
    private String password;
    
    private String password1;
    
    private String lastname;
    
    private String firstname;
    
    private String email;
    
    private boolean save = false;
    
    
    public Long getId() {
        return id;
    }
    
    
    public void setId(Long id) {
        this.id = id;
    }
    
    
    public String getLoginname() {
        return loginname;
    }
    
    
    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }
    
    
    public String getPassword() {
        return password;
    }
    
    
    public void setPassword(String password) {
        this.password = password;
    }


    public String getPassword1() {
        return password1;
    }


    public void setPassword1(String password1) {
        this.password1 = password1;
    }


    public String getFirstname() {
        return firstname;
    }


    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }


    public String getLastname() {
        return lastname;
    }


    public void setLastname(String lastname) {
        this.lastname = lastname;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public boolean isSave() {
        return save;
    }


    public void setSave(boolean save) {
        this.save = save;
    }
    
    
}
