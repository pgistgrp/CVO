package org.pgist.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


/**
 * 
 * @author kenny
 * 
 */
public class StaticPageFilter implements Filter {
    
    
    protected FilterConfig filterConfig = null;
    
    private String page = "/index.html";
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        page = filterConfig.getInitParameter("page");
        if (page==null || page.length()==0) page = "index.html";
    }//init()
    
    
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.getRequestDispatcher(page).forward(request, response);
    }//doFilter()
    
    
    public void destroy() {
    }//destroy()
    
    
}//class CheckRoleFilter
