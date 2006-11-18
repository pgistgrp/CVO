package org.pgist.util;

import java.util.Date;

import org.pgist.users.UserInfo;


/**
 * 
 * @author kenny
 *
 */
public class WebUtils {
    
    
    private static ThreadLocal<UserInfo> threadLocalCurrentUser = new ThreadLocal<UserInfo>();
    
    private static ThreadLocal<Date> threadLocalDate = new ThreadLocal<Date>();
    
    
    public static UserInfo currentUser() {
        return threadLocalCurrentUser.get();
    }//currentUser()
    
    
    public static Long currentUserId() {
        return threadLocalCurrentUser.get().getId();
    }//currentUserId()
    
    
    public static void setCurrentUser(UserInfo userInfo) {
        if (userInfo==null) {
            threadLocalCurrentUser.remove();
        } else {
            threadLocalCurrentUser.set(userInfo);
        }
    }//setCurrentUser()
    
    
    public static boolean checkRole(String roleName) {
        return threadLocalCurrentUser.get().checkRole(roleName);
    }//checkRole()
    
    
    public static Date getDate() {
        Date date = threadLocalDate.get();
        
        if (date==null) {
            date = new Date();
            threadLocalDate.set(date);
        }
        
        return date;
    }//getDate()
    
    
    public static void clearDate() {
        threadLocalDate.remove();
    }//clearDate()
    
    
}//class WebUtils
