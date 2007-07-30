package org.pgist.tests;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tools.ant.AntClassLoader;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.MatchingTask;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.pgist.ddl.SystemHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;


/**
 * Init the PGIST system
 * @author kenny
 *
 */
public class SystemInit extends MatchingTask {
    
    
    private String action = "createdb";
    
    private String configPath;
    
    private File dataPath;
    
    private ApplicationContext appContext = null;
    
    private SessionFactory sessionFactory = null; 
    
    Session session = null;
    
    Map roleMap = new HashMap();
    
    Map userMap = new HashMap();
    
    
    public void setAction(String action) {
        this.action = action;
    }


    public void setConfigPath(String configPath) {
        this.configPath = configPath;
    }


    public void setDataPath(String dataPath) {
        this.dataPath = new File(dataPath);
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
                configPath + "/context-other.xml",
                "classpath:/config/context-workflow.xml",
                configPath + "/context-base.xml",
                configPath + "/context-cvo.xml",
                configPath + "/context-criteria.xml",
                configPath + "/context-projects.xml",
                configPath + "/context-funding.xml",
                configPath + "/context-report.xml",
                configPath + "/context-packages.xml",
            }
        );
        
        sessionFactory = (SessionFactory) appContext.getBean("sessionFactory");
        session = SessionFactoryUtils.getSession(sessionFactory, true);
        TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
        
        LocalSessionFactoryBean slfb = (LocalSessionFactoryBean) appContext.getBean("&sessionFactory");
        
        if ("createdb".equalsIgnoreCase(action)) {
            
            SAXReader reader = new SAXReader();
            Document document = reader.read(new File(dataPath, "schema.sql.xml"));
            Element root = document.getRootElement();
            
            List<SchemaManager> managers = new ArrayList<SchemaManager>();
            
            List scripts = root.elements("script");
            for (int i=0; i<scripts.size(); i++) {
                Element element = (Element) scripts.get(i);
                
                String file = element.elementTextTrim("file");
                
                SchemaManager manager = null;
                
                manager = new SchemaManager();
                
                manager.setFile(new File(dataPath, file));
                
                managers.add(manager);
            }//for i
            
            Connection connection = session.connection();
            
            //drop additional schemas
            for (int i=0; i<managers.size(); i++) {
                managers.get(i).executeDrop(connection);
            }
            
            slfb.dropDatabaseSchema();
            slfb.createDatabaseSchema();
            
            //create additional schemas
            for (int i=0; i<managers.size(); i++) {
                managers.get(i).executeCreate(connection);
            }
            
        } else if ("restoredb".equalsIgnoreCase(action)) {
            slfb.dropDatabaseSchema();
            slfb.createDatabaseSchema();
        } else if ("updatedb".equalsIgnoreCase(action)) {
            slfb.updateDatabaseSchema();
            return false;
        }
        
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
        boolean bInitSystem = false;
        try {
            bInitSystem = setUp();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BuildException(e);
        }
        
        try {
            if (bInitSystem) initSystem();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BuildException(e);
        } finally {
            try {
                tearDown();
            } catch(Exception e) {
                e.printStackTrace();
                throw new BuildException(e);
            }
        }
    }//execute()
    
    
    private void initSystem() throws Exception {
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            
            SystemHandler handler = new SystemHandler(appContext, session, dataPath, "handlers.xml");
            
            if ("backup".equalsIgnoreCase(action)) {
                handler.exports(null);
                System.out.println("PGIST database is succesfully exported to directory: "+dataPath);
            } else {
                handler.imports(null);
            }
            
            tx.commit();
        } catch(Exception e) {
            tx.rollback();
            throw e;
        }
    }//initSystem()
    
    
}//class SystemInit
