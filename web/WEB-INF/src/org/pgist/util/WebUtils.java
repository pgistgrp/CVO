package org.pgist.util;

import java.util.Date;

import org.pgist.users.UserInfo;


/**
 * 
 * @author kenny
 *
 */
public class WebUtils {
    
    
    private static String contextPath;
    
    private static ThreadLocal<UserInfo> threadLocalCurrentUser = new ThreadLocal<UserInfo>();
    
    private static ThreadLocal<Date> threadLocalDate = new ThreadLocal<Date>();
    
    
    public static String getContextPath() {
        return contextPath;
    }


    public static void setContextPath(String contextPath) {
        WebUtils.contextPath = contextPath;
    }


    public static UserInfo currentUser() {
        return threadLocalCurrentUser.get();
    }//currentUser()
    
    
    public static Long currentUserId() {
        UserInfo userInfo = threadLocalCurrentUser.get();
        if (userInfo==null) return null;
        else return userInfo.getId();
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
    
    
    public static boolean checkUser(Long userId) {
        UserInfo info = threadLocalCurrentUser.get();
        
        if (info!=null && info.getId()!=null && info.getId().equals(userId)) return true;
        
        return false;
    }//checkUser()
    
    
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
