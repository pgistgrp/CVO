package org.pgist.web;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.pgist.search.SearchHelper;
import org.springframework.web.context.WebApplicationContext;


/**
 * 
 * @author kenny
 *
 */
public class PgistListener implements ServletContextListener {
    
    
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        WebApplicationContext context = (WebApplicationContext) servletContext.getAttribute
            (WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        SearchHelper helper = (SearchHelper) context.getBean("searchHelper");
        String contextPath = servletContext.getRealPath("/");
        if (contextPath.endsWith(File.separator)) {
            contextPath = contextPath.substring(0, contextPath.length()-1);
        }
        helper.setContextPath(contextPath);
    }//contextInitialized()
    
    
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }//contextDestroyed()
    
    
}//class PgistListener
