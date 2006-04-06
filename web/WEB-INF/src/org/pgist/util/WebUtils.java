package org.pgist.util;

import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uk.ltd.getahead.dwr.WebContext;
import uk.ltd.getahead.dwr.WebContextFactory;
import uk.ltd.getahead.dwr.util.SwallowingHttpServletResponse;


/**
 * 
 * @author kenny
 *
 */
public class WebUtils {
    
    
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
    
    
    /**
     * 
     * @return
     * @throws Exception
     */
    public static Long currentUserId() throws Exception {
        WebContext ctx = WebContextFactory.get();
        HttpSession session = ctx.getSession();
        Long id = (Long) session.getAttribute("userId");
        return id;
    }//getCurrentUser()
    
    
}//class WebUtils
