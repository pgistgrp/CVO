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
     * The login URL.
     */
    protected String loginURL;
    
    /*
     * All requested urls in the ignoreURLs will not be checked if the user has logged in.
     */
    protected HashSet<String> ignoreURLs = new HashSet<String>();
    
    /*
     * All requested urls in the httpsURLs will be forced to use https protocol.
     */
    protected HashSet<String> httpsURLs = new HashSet<String>();
    
    protected int httpPort = 80;
    
    protected int httpsPort = 443;
    
    
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        
        WebApplicationContext context = (WebApplicationContext) filterConfig.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        systemService = (SystemService) context.getBean("systemService");
        
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
        
        String httpsURL = filterConfig.getInitParameter("https-url");
        array = httpsURL.split(",");
        for (int i=0; i<array.length; i++) {
            httpsURLs.add(array[i].trim());
        }
        
        String port = filterConfig.getInitParameter("http-port");
        if (port!=null && port.trim().length()>0) {
            port = port.trim();
            httpPort = Integer.parseInt(port);
        }
        
        port = filterConfig.getInitParameter("https-port");
        if (port!=null && port.trim().length()>0) {
            port = port.trim();
            httpsPort = Integer.parseInt(port);
        }
    }//init()

    
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //check if the user logged in
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        
        String ctxPath = req.getContextPath();
        String path = req.getRequestURI().substring(ctxPath.length());
        
        //System.out.println("URI: "+req.getRequestURI());
        //System.out.println("URL: "+req.getRequestURL());
        //System.out.println("PgistFilter: "+path);
        
        UserInfo userInfo = null;
        
        String httpsPrefix = "https://" + req.getServerName() + ":" + httpsPort;
        req.setAttribute("httpsPrefix", httpsPrefix);
        String httpPrefix = "http://" + req.getServerName() + ":" + httpPort;
        req.setAttribute("httpPrefix", httpPrefix);
        
        /*
         * check if the requested url should use https protocol.
         */
        if (httpsURLs.contains(path)) {
            if (!req.isSecure()) {
                req.getSession(true);
                if (req.getQueryString()!=null) {
                    res.sendRedirect( httpsPrefix + req.getRequestURI() + '?' + req.getQueryString() );
                } else {
                    res.sendRedirect( httpsPrefix + req.getRequestURI() );
                }
                return;
            }
        }
        
        /*
         * check if the requested url is in ignore urls.
         */
        if (!ignoreURLs.contains(path)) {
            HttpSession session = req.getSession();
            userInfo = (UserInfo) session.getAttribute("user");
            if (userInfo==null) {
                //user has not logged on
                
                /*
                 * save the original requested URL for later user
                 */
                StringBuffer initURL = req.getRequestURL();
                if (req.getQueryString()!=null) {
                    initURL.append('?').append(req.getQueryString());
                }
                
                if (!"/".equalsIgnoreCase(req.getRequestURI())) {
                    /*
                     * Save the original requested URL in cookie
                     */
                    Cookie cookie = new Cookie("PG_INIT_URL", res.encodeRedirectURL(initURL.toString()));
                    cookie.setMaxAge(-1);
                    res.addCookie(cookie);
                }
                
                /*
                 * redirect to login page
                 */
                res.sendRedirect( httpsPrefix + loginURL );
                
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
