package org.pgist.tests;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.loader.AntClassLoader2;
import org.apache.tools.ant.taskdefs.MatchingTask;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.pgist.cvo.TagDAO;
import org.pgist.cvo.TagMatcher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;


/**
 * 
 * @author kenny
 *
 */
public class TagMatcherTest extends MatchingTask {
    
    
    private String configPath;

    private ApplicationContext appContext = null;
    
    private SessionFactory sessionFactory = null;
    
    private Transaction tx;
    
    private TagMatcher tagMatcher;
    
    private TagDAO tagDAO;
    
    
    public void setConfigPath(String configPath) {
        this.configPath = configPath;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */


    private void setUp() throws Exception {
        //code to handle cnf issues with taskdef classloader
        AntClassLoader2 antClassLoader = null;
        Object obj = this.getClass().getClassLoader();
        if (obj instanceof AntClassLoader2) {
            antClassLoader = (AntClassLoader2) obj;
            antClassLoader.setThreadContextLoader();
        }
        //end code to handle classnotfound issue

        appContext = new FileSystemXmlApplicationContext(
            new String[] {
                configPath + "/context-database.xml",
                configPath + "/context-system.xml",
                configPath + "/context-webservice.xml",
                configPath + "/context-workflow.xml",
                configPath + "/context-cvo.xml",
                configPath + "/context-pgames.xml",
            }
        );
        
        sessionFactory = (SessionFactory) appContext.getBean("sessionFactory");
        Session session = SessionFactoryUtils.getSession(sessionFactory, true);
        TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
        
        tagDAO = (TagDAO) appContext.getBean("tagDAO");
        tagMatcher = (TagMatcher) appContext.getBean("tagMatcher");
        
        tx = session.beginTransaction();
        
    }//setUp()
    
    
    protected void tearDown() throws Exception {
        tx.commit();
        SessionHolder sessionHolder = (SessionHolder) TransactionSynchronizationManager.unbindResource(sessionFactory);
        //sessionHolder.getSession().close();
        SessionFactoryUtils.releaseSession(sessionHolder.getSession(), sessionFactory);
    }//tearDown()
    
    
    /**
     * Execute the task
     */
    public void execute() throws BuildException {
        try {
            //setup hibernate and spring
            setUp();
            
            System.out.println();
            System.out.println();
            System.out.println("Begin TagMatcher Testing ------->");
            System.out.println();
            System.out.println();
            test();
            System.out.println();
            System.out.println();
            System.out.println("Test TagMatcher finished ------->");
            System.out.println();
            System.out.println();
            
            //tear down hibernate and spring
            tearDown();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BuildException(e);
        }
    }//execute()


    /**
     * This is the test case for TagMatcher
     * 
     * @throws Exception
     */
    private void test() throws Exception {
        //Add your codes here.
    }//test()
    
    
}//class TagMatcherTest
