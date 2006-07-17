package org.pgist.util;

import org.pgist.users.UserInfo;


/**
 * 
 * @author kenny
 *
 */
public class WebUtils {
    
    
    private static ThreadLocal<UserInfo> threadLocalCurrentUser = new ThreadLocal<UserInfo>();
    
    
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
    
    
}//class WebUtils
