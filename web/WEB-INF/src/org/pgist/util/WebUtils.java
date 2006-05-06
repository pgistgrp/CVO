package org.pgist.util;

import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pgist.users.UserInfo;

import uk.ltd.getahead.dwr.util.SwallowingHttpServletResponse;


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
    
    
    /**
     * Call a jsp file and grab its response as a string.
     * This function is usually used by DWR Ajax invocation.
     * 
     * <p>Example:<br>
     * String html = WebUtils.forwardToString(request, response, "/test.jsp");
     * 
     * @param url
     * @return
     * @throws Exception
     * 
     */
    public static String forwardToString(HttpServletRequest request, HttpServletResponse response, String url) throws Exception {
        StringWriter sout = new StringWriter();
        StringBuffer buffer = sout.getBuffer();
        
        HttpServletResponse fakeResponse = new SwallowingHttpServletResponse(response, sout);
        
        request.getSession().getServletContext().getRequestDispatcher(url).forward(request, fakeResponse);
        
        return buffer.toString();
    }//forwardToString()
    
    
}//class WebUtils
