package org.pgist.web;

import java.io.IOException;
import java.util.HashSet;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author kenny
 *
 */
public class SecurityFilter implements Filter {
    
    
    protected FilterConfig filterConfig = null;
    
    /*
     * All requested urls in the httpsURLs will be forced to use https protocol.
     */
    protected HashSet<String> httpsURLs = new HashSet<String>();
    
    protected int httpPort = 80;
    
    protected int httpsPort = 443;
    
    
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        
        httpsURLs.add("");
        httpsURLs.add("/");
        String httpsURL = filterConfig.getInitParameter("https-url");
        String[] array = httpsURL.split(",");
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
        
        String httpsPrefix = "https://" + req.getServerName() + ":" + httpsPort;
        req.setAttribute("httpsPrefix", httpsPrefix);
        String httpPrefix = "http://" + req.getServerName();
        if (httpPort!=80) httpPrefix += ":" + httpPort;
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
        } else {
            // bypass if it's /scripts or /images
            if (!path.startsWith("/scripts/") && !path.startsWith("/images/")) {
                if (req.isSecure()) {
                    req.getSession(true);
                    if (req.getQueryString()!=null) {
                        res.sendRedirect( httpPrefix + req.getRequestURI() + '?' + req.getQueryString() );
                    } else {
                        res.sendRedirect( httpPrefix + req.getRequestURI() );
                    }
                    return;
                }
            }
        }
        
        chain.doFilter(request, response);
    }//doFilter()
    

    public void destroy() {
    }//destroy()
    

} //class SecurityFilter
