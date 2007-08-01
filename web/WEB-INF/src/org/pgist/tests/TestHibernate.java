package org.pgist.tests;

import java.util.List;
import java.util.Scanner;

import org.apache.tools.ant.AntClassLoader;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.MatchingTask;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import bsh.Interpreter;


/**
 * Init the PGIST system
 * @author kenny
 *
 */
public class TestHibernate extends MatchingTask {
    
    
    private String configPath;
    
    private String script;
    
    private ApplicationContext appContext = null;
    
    private SessionFactory sessionFactory = null; 
    
    Session session = null;
    
    
    public void setConfigPath(String configPath) {
        this.configPath = configPath;
    }


    public void setScript(String script) {
        this.script = script;
    }


    private boolean setUp() throws Exception {
        //code to handle cnf issues with taskdef classloader
        AntClassLoader antClassLoader = null;
        Object obj = this.getClass().getClassLoader();
        if (obj instanceof AntClassLoader) {
            antClassLoader = (AntClassLoader) obj;
            antClassLoader.setThreadContextLoader();
        }
        //end code to handle classnotfound issue
        
        appContext = new FileSystemXmlApplicationContext(
            new String[] {
                configPath + "/context-database.xml",
            }
        );
        
        sessionFactory = (SessionFactory) appContext.getBean("sessionFactory");
        session = SessionFactoryUtils.getSession(sessionFactory, true);
        TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
        
        return true;
    }//setUp()
    
    
    protected void tearDown() throws Exception {
        SessionHolder sessionHolder = (SessionHolder) TransactionSynchronizationManager.unbindResource(sessionFactory);
        //sessionHolder.getSession().close();
        SessionFactoryUtils.releaseSession(sessionHolder.getSession(), sessionFactory);
    }//tearDown()
    
    
    /**
     * Execute the task
     */
    public void execute() throws BuildException {
        try {
            setUp();
            test();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BuildException(e);
        }
    }//execute()
    
    
    private void test() throws Exception {
        Transaction tx = null;
        
        Scanner scanner = new Scanner(System.in);
        
        Interpreter interpreter = new Interpreter();
        interpreter.set("session", session);
        
        while (true) {
            System.out.println();
            System.out.println();
            System.out.print("Press Ctrl+C to exit; or press ENTER to execute script \""+script+"\"");
            
            String line = scanner.nextLine();
            
            try {
                tx = session.beginTransaction();
                
                interpreter.source(script);
                
                tx.commit();
            } catch (Exception e) {
                e.printStackTrace();
                tx.rollback();
            }
            
            System.out.print("------------------------------------------------------------------------");
        }//while
    }//initWorkflow()
    
    
}//class WorkflowImporter
