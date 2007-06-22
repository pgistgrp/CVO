package org.pgist.web;

import java.io.IOException;
import java.util.HashSet;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.pgist.system.SystemService;
import org.pgist.users.UserInfo;
import org.pgist.util.WebUtils;
import org.springframework.web.context.WebApplicationContext;


/**
 * Filter for PGIST
 * @author kenny
 * 
 * Function 1: check if the user already logged in for most requests.
 *          2: automacticly close any database connection involved in one request
 *
 */
public class PgistFilter implements Filter {

    private SystemService systemService;
    
    protected FilterConfig filterConfig = null;
    
    /*
     * If forceCloseConnection is true, each request will cause the system to try to close
     * any database connection involved in this request.
     */
    protected boolean forceCloseConnection = true;
    
    /*
     * The login URL.
     */
    protected String loginURL = null;
    
    /*
     * All request urls in the ignoreURLs will not be checked if the user has logged in.
     */
    protected HashSet ignoreURLs = new HashSet();
    
    
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        
        WebApplicationContext context = (WebApplicationContext) filterConfig.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        systemService = (SystemService) context.getBean("systemService");
        
        forceCloseConnection = "true".equals(filterConfig.getInitParameter("force-close-connection"));
        
        /*
         * loginURL should be in the ignoreURLs.
         */
        loginURL = filterConfig.getInitParameter("login-url");
        ignoreURLs.add(loginURL);
        
        String ignoreURL = filterConfig.getInitParameter("ignore-url");
        String[] array = ignoreURL.split(",");
        for (int i=0; i<array.length; i++) {
            ignoreURLs.add(array[i].trim());
        }
    }//init()

    
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //check if the user logged in
        HttpServletRequest req = (HttpServletRequest) request;
        String path = req.getRequestURI();
        String ctxPath = req.getContextPath();
        path = path.substring(ctxPath.length());
        
        //System.out.println("URI: "+req.getRequestURI());
        //System.out.println("URL: "+req.getRequestURL());
        //System.out.println("PgistFilter: "+path);
        
        UserInfo userInfo = null;
        
        if (!ignoreURLs.contains(path)) {
            HttpSession session = req.getSession();
            userInfo = (UserInfo) session.getAttribute("user");
            if (userInfo==null) {
                //user has not logged on
                
                /*
                 * save the current URL for later user
                 */
                StringBuilder loginURL = new StringBuilder();
                loginURL.append(req.getContextPath());
                loginURL.append(loginURL);
                
                HttpServletResponse res = (HttpServletResponse) response;
                
                StringBuffer sb = req.getRequestURL();
                sb.append('?').append(req.getQueryString());
                
                /*
                 * Save the original requested URL in cookie
                 */
                Cookie cookie = new Cookie("PG_INIT_URL", res.encodeRedirectURL(sb.toString()));
                cookie.setMaxAge(-1);
                res.addCookie(cookie);
                
                /*
                 * redirect to login page
                 */
                res.sendRedirect( loginURL.toString() );
                
                return;
            }
            
            //You can use the variable "baseuser" on any jsp page.
            request.setAttribute("baseuser", userInfo);
        }
        
        DelegatingHttpServletRequestWrapper wrapper = new DelegatingHttpServletRequestWrapper((HttpServletRequest) request);
        
        try {
            //hold userInfo for later use
            WebUtils.setCurrentUser(userInfo);
            chain.doFilter(wrapper, response);
        } finally {
            //log request
            if (!path.endsWith(".js")) {
                try {
                    systemService.logRequest(wrapper);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //release userInfo
            if (userInfo!=null) WebUtils.setCurrentUser(null);
            WebUtils.clearDate();
        }
    }//doFilter()
    

    public void destroy() {
    }//destroy()
    

}//class PgistFilter
