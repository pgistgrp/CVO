package org.pgist.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.pgist.glossary.TermAnalyzer;
import org.pgist.tagging.TagAnalyzer;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.context.WebApplicationContext;


/**
 * 
 * @author kenny
 *
 */
public class PgistListener implements ServletContextListener {
    
    
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        /*
         * Get out the spring context
         */
        
        ServletContext servletContext = servletContextEvent.getServletContext();
        
        WebApplicationContext context = (WebApplicationContext) servletContext.getAttribute
            (WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        
        /*
         * Config Search Helper
         */
        /*
        SearchHelper helper = (SearchHelper) context.getBean("searchHelper");
        
        String contextPath = servletContext.getRealPath("/");
        if (contextPath.endsWith(File.separator)) {
            contextPath = contextPath.substring(0, contextPath.length()-1);
        }
        
        helper.setContextPath(contextPath);
        */
        /*
         * The following operations need the OpenSessionInView
         */
        
        SessionFactory sf = null;
        Session session = null;
        try {
            /*
             * hold the hibernate session
             */
            
            sf = (SessionFactory) context.getBean("sessionFactory");
            session = SessionFactoryUtils.getSession(sf, true);
            TransactionSynchronizationManager.bindResource(sf, new SessionHolder(session));
            
            /*
             * Config Tag Analyzer
             */
            
            try {
                TagAnalyzer tagAnalyzer = (TagAnalyzer) context.getBean("tagAnalyzer");
                tagAnalyzer.reload();
            } catch(Exception e) {
                e.printStackTrace();
            }
            
            /*
             * Config Term Analyzer
             */
            
            try {
                TermAnalyzer termAnalyzer = (TermAnalyzer) context.getBean("termAnalyzer");
                termAnalyzer.reload();
            } catch(Exception e) {
                e.printStackTrace();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            /*
             * release the hibernate session
             */
            TransactionSynchronizationManager.unbindResource(sf);
            SessionFactoryUtils.releaseSession(session, sf);
        }
        
    }//contextInitialized()
    
    
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }//contextDestroyed()
    
    
}//class PgistListener
