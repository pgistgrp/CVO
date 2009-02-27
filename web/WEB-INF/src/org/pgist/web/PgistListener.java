package org.pgist.web;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.pgist.glossary.TermAnalyzer;
import org.pgist.search.SearchHelper;
import org.pgist.system.EmailSender;
import org.pgist.tagging.TagAnalyzer;
import org.pgist.util.JythonAPI;
import org.pgist.util.WebUtils;
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
    
    
    private class EmailSendingThread extends Thread {
        
        private boolean exit = false;
        
        private long sleeping = 1 * 60 * 1000;
        
        private EmailSender emailSender;
        
        public EmailSendingThread(EmailSender emailSender) {
            this.emailSender = emailSender;
        }
        
        public void run() {
            while (!exit) {
                // check and send email
                emailSender.checkAndSend();
                
                // now sleep
                try {
                    Thread.currentThread().sleep(sleeping);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } //run()
        
        public void exit() {
            this.exit = true;
            interrupt();
        }
        
    } //class EmailSendingThread
    
    
    private EmailSendingThread daemon;
    
    
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        /*
         * Get out the spring context
         */
        
        ServletContext servletContext = servletContextEvent.getServletContext();
        
        WebApplicationContext context = (WebApplicationContext) servletContext.getAttribute
            (WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        
        /*
         * Get the context path
         */
        String contextPath = servletContext.getRealPath("/");
        if (contextPath.endsWith(File.separator)) {
            contextPath = contextPath.substring(0, contextPath.length()-1);
        }
        
        WebUtils.setContextPath(contextPath);
        
        /*
         * Config Search Helper
         */
        SearchHelper helper = (SearchHelper) context.getBean("searchHelper");
        helper.setContextPath(contextPath);
        
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
            
            /*
             * Config Jython API
             */
            
            try {
                JythonAPI jythonAPI = (JythonAPI) context.getBean("jythonAPI");
                jythonAPI.setContextPath(contextPath);
            } catch(Exception e) {
                e.printStackTrace();
            }
            
            /*
             * The email sending daemon
             */
            
            EmailSender emailSender = (EmailSender) context.getBean("emailSender");
            daemon = new EmailSendingThread(emailSender);
            daemon.start();
            
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
        daemon.exit();
    }//contextDestroyed()
    
    
}//class PgistListener
