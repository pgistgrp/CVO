package org.pgist.tests;

import java.util.Scanner;

import org.apache.tools.ant.AntClassLoader;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.MatchingTask;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.pgist.search.SearchHelper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import bsh.Interpreter;


/**
 * 
 * @author kenny
 *
 */
public class SearchEngineRunner extends MatchingTask {
    
    
    public static class Param {
        private String value;
        
        public void setValue(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
    }
    
    public static class Config {
        private Param indexPath;
        private Param configPath;
        private Param script;
        
        public void addIndexPath(Param indexPath) {
            this.indexPath = indexPath;
        }
        
        public void addConfigPath(Param configPath) {
            this.configPath = configPath;
        }
        
        public void addScript(Param script) {
            this.script = script;
        }
        
        public String getIndexPath() {
            return indexPath.getValue();
        }
        
        public String getConfigPath() {
            return configPath.getValue();
        }
        
        public String getScript() {
            return script.getValue();
        }
    }//class config
    
    private Config config;
    
    private ApplicationContext appContext = null;
    
    private SessionFactory sessionFactory = null; 
    
    Session session = null;
    
    
    public void addConfig(Config config) {
        this.config = config;
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
                config.getConfigPath() + "/context-database.xml",
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
        
        SearchHelper searchHelper = new SearchHelper();
        searchHelper.setContextPath("");
        searchHelper.setIndexPath(config.getIndexPath());
        
        Interpreter interpreter = new Interpreter();
        interpreter.set("searchHelper", searchHelper);
        interpreter.set("session", session);
        
        while (true) {
            System.out.println();
            System.out.println();
            System.out.print("Press Ctrl+C to exit; or press ENTER to execute script \""+config.getScript()+"\"");
            
            String line = scanner.nextLine();
            
            try {
                tx = session.beginTransaction();
                
                interpreter.source(config.getScript());
                
                tx.commit();
            } catch (Exception e) {
                e.printStackTrace();
                tx.rollback();
            }
            
            System.out.print("------------------------------------------------------------------------");
        }//while
    }//test()
    
    
}//class TestLucene
