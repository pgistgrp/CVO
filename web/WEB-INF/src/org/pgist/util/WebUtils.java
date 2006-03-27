package org.pgist.util;

import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uk.ltd.getahead.dwr.util.SwallowingHttpServletResponse;


/**
 * 
 * @author kenny
 *
 */
public class WebUtils {
    
    
    /**
     * 
     * @param url
     * @return
     * @throws Exception
     */
    public static String forwardToString(HttpServletRequest request, HttpServletResponse response, String url) throws Exception {
        StringWriter sout = new StringWriter();
        StringBuffer buffer = sout.getBuffer();
        
        HttpServletResponse fakeResponse = new SwallowingHttpServletResponse(response, sout);
        
        request.getSession().getServletContext().getRequestDispatcher(url).forward(request, fakeResponse);
        
        return buffer.toString();
    }//forwardToString()
    
    
}//class WebUtils
