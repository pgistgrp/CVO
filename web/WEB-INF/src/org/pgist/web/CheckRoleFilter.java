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
 * @author kenny
 * 
 */
public class CheckRoleFilter implements Filter {
    
    
    protected FilterConfig filterConfig = null;
    
    String role = "";
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        role = filterConfig.getInitParameter("role");
    }//init()
    
    
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!WebUtils.checkRole(role)) {
            throw new ServletException("Only "+role+" has the access right to this function!");
        }
        
        chain.doFilter(request, response);
    }//doFilter()
    
    
    public void destroy() {
    }//destroy()
    
    
}//class CheckRoleFilter
