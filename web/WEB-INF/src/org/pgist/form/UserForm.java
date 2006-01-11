package org.pgist.form;

import org.apache.struts.action.ActionForm;
import org.pgist.users.User;


public class UserForm extends ActionForm {

    
    private static final long serialVersionUID = 5443268954560626922L;
    
    private User user = new User();
    
    private String password1;
    
    private boolean save;
    
    
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


    public boolean isSave() {
        return save;
    }


    public void setSave(boolean save) {
        this.save = save;
    }


}
