package org.pgist.web;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;


/**
 * 
 * @author kenny
 *
 */
public class DelegatingHttpServletRequestWrapper extends HttpServletRequestWrapper {
    
    
    private final HttpServletRequest proxy;
    
    private StringBuilder sb = new StringBuilder();
    
    private DelegatingServletInputStream stream = null;
    
    
    public DelegatingHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        proxy = request;
    }
    
    
    public ServletInputStream getInputStream() throws java.io.IOException {
        if (stream==null) {
            stream = new DelegatingServletInputStream(super.getInputStream());
        }
        return stream;
    }
    
    
    public String getLogging() {
        if (stream==null) return "";
        return stream.getContent();
    }
    
    
}//class DelegatingHttpServletRequestWrapper
