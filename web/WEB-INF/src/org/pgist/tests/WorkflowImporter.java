package org.pgist.tests;

import java.io.File;

import org.apache.tools.ant.AntClassLoader;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.MatchingTask;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.pgist.wfengine.WorkflowEngine;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;


/**
 * Init the PGIST system
 * @author kenny
 *
 */
public class WorkflowImporter extends MatchingTask {
    
    
    private String configPath;
    
    private File dataPath;
    
    private String definition;
    
    private ApplicationContext appContext = null;
    
    private SessionFactory sessionFactory = null; 
    
    Session session = null;
    
    
    public void setConfigPath(String configPath) {
        this.configPath = configPath;
    }


    public void setDataPath(String dataPath) {
        this.dataPath = new File(dataPath);
    }


    public void setDefinition(String definition) {
        this.definition = definition;
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
                configPath + "/context-system.xml",
                configPath + "/context-tasks.xml",
                "classpath:/config/context-workflow.xml",
                configPath + "/context-base.xml",
                configPath + "/context-cvo.xml",
                configPath + "/context-criteria.xml",
                configPath + "/context-projects.xml",
                configPath + "/context-packages.xml",
                configPath + "/context-funding.xml",
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
            initWorkflow();
        } catch (Exception e) {
            throw new BuildException(e);
        }
    }//execute()
    
    
    private void initWorkflow() throws Exception {
        Document doc = new SAXReader().read(new File(dataPath, definition));
        
        WorkflowEngine engine = (WorkflowEngine) appContext.getBean("txEngine");
        engine.importTemplates(doc);
    }//initWorkflow()
    
    
}//class WorkflowImporter
