package org.pgist.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.pgist.util.WebUtils;


/**
 * 
 * @author michalis
 * 
 */
public class CheckAssocFilter implements Filter {
    
    
    protected FilterConfig filterConfig = null;
    
    String assoc = "";
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        assoc = filterConfig.getInitParameter("assoc");
    }//init()
    
    
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!WebUtils.checkAssoc(assoc)) {
            throw new ServletException("Only "+assoc+" has the access right to this function!");
        }
        
        chain.doFilter(request, response);
    }//doFilter()
    
    
    public void destroy() {
    }//destroy()
    
    
}//class CheckAssocFilter
