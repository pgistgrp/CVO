package org.pgist.tests;

import org.apache.tools.ant.AntClassLoader;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.MatchingTask;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.pgist.funding.FundingSource;
import org.pgist.funding.FundingSourceAltRef;
import org.pgist.funding.FundingSourceAlternative;
import org.pgist.funding.FundingSourceRef;
import org.pgist.funding.FundingSourceSuite;
import org.pgist.packages.PackageSuite;
import org.pgist.projects.Project;
import org.pgist.projects.ProjectAltRef;
import org.pgist.projects.ProjectAlternative;
import org.pgist.projects.ProjectRef;
import org.pgist.projects.ProjectSuite;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * Special task that sets up a defal testing database to work from 
 * 
 * @author Matt Paulin
 */
public class PrepTestDataLoader extends MatchingTask {

    private ApplicationContext appContext = null;    
	private SessionFactory sessionFactory = null; 

    private String configPath;
	
    Session session = null;	
	
    
    private FundingSourceSuite fsSuite;
    private ProjectSuite pSuite;
    private PackageSuite packSuite;

    public void setConfigPath(String configPath) {
        this.configPath = configPath;
    }     
    
    /**
     * Execute the task
     */
    public void execute() throws BuildException {        	
    	setupConnection();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
        
            clearOldData();
            
            if(loadSuites()) {
            	createFundingSources();
            	createProjects();
            } else {
        		System.out.println("Error loading database: You need to create a funding source suite, project suite, and package suite with ID = 200");         	
            }
            
            tx.commit();
        } catch(Exception e) {
        	System.out.println("EXCEPTION: " + e.getMessage());
        	e.printStackTrace();
            tx.rollback();
			throw new BuildException(e);
        }
    }
    
    private void setupConnection() {

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
                    configPath + "/context-base.xml",
                    configPath + "/context-cvo.xml",
                    configPath + "/context-projects.xml",
                }
            );    	
    	
      sessionFactory = (SessionFactory) appContext.getBean("sessionFactory");
      session = SessionFactoryUtils.getSession(sessionFactory, true);
      TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));    	
    }
    
    private void clearOldData() {
   		int num = session.createQuery("delete FundingSourceAltRef c").executeUpdate();
        System.out.println("Deleted " +num + " FundingSourceAltRef");
        num = session.createQuery("delete FundingSourceRef c").executeUpdate();
        System.out.println("Deleted " +num + " FundingSourceRef");        
        num = session.createQuery("delete FundingSourceAlternative c").executeUpdate();
        System.out.println("Deleted " +num + " FundingSourceAlternative");        
        num = session.createQuery("delete FundingSource c").executeUpdate();
        System.out.println("Deleted " +num + " FundingSource");
        
        num = session.createQuery("delete ProjectAltRef c").executeUpdate();
        System.out.println("Deleted " +num + " ProjectAltRef");        
        num = session.createQuery("delete ProjectRef c").executeUpdate();
        System.out.println("Deleted " +num + " ProjectRef");        
        num = session.createQuery("delete ProjectAlternative c").executeUpdate();
        System.out.println("Deleted " +num + " ProjectAlternative");        
        num = session.createQuery("delete Project c").executeUpdate();
        System.out.println("Deleted " +num + " Project");        
         
    }
    
    private boolean loadSuites() {
    	try {
			//Create all of the suite 200s
			pSuite = (ProjectSuite)session.createQuery("from ProjectSuite as suite where suite.id = 200").list().get(0);
			fsSuite = (FundingSourceSuite)session.createQuery("from FundingSourceSuite as suite where suite.id = 200").list().get(0);
			packSuite = (PackageSuite)session.createQuery("from PackageSuite as suite where suite.id = 200").list().get(0);
			return true;
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    		e.printStackTrace();
        	return false;
    	}    	
    }
    
    private void createFundingSources() {
    	
    	int total = 8;
    	int totalAlts = 3;
    	
    	FundingSource[] sources = new FundingSource[total];
    	FundingSourceAlternative[] alts = new FundingSourceAlternative[total];
    	
    	for(int i = 0; i < total; i++) {
        	//Create the sources    		
    		sources[i] = createFundingSource("Funding Source " + i, i);
    		
    		//Add your alternatives
    		for(int j = 0; j< totalAlts; j++) {
    			if(j == 0) {
    				alts[i] = createAlternative("Funding Source Alternative "+j, (float)j, sources[i]);
    			} else {
        			createAlternative("Funding Source Alternative "+j, (float)j, sources[i]);    				
    			}
    		}    		
    	}
    	    	    	
    	//Create the suite
    	
    	FundingSourceRef ref;
    	FundingSourceAltRef altRef;
    	
    	for(int i = 0; i < total/2; i++) {
    		ref = new FundingSourceRef();
    		ref.setSource(sources[i]);    		
    		session.saveOrUpdate(ref);

    		altRef = new FundingSourceAltRef();
    		altRef.setAlternative(alts[i]);
    		ref.getAltRefs().add(altRef);    	    		
    		    		
    		fsSuite.getReferences().add(ref);
    		ref.setSuite(fsSuite);
    		
    		session.saveOrUpdate(altRef);
    		session.saveOrUpdate(ref);
    		session.saveOrUpdate(fsSuite);
    	}    	
    }
    
    private FundingSource createFundingSource(String name, int type) {
    	if(type < 1) type = 1;
    	if(type > 8) type = 8;
    	FundingSource source = new FundingSource();
    	source.setName(name);
    	source.setType(type);    	
    	session.saveOrUpdate(source);
    	return source;
    }
    
    private FundingSourceAlternative createAlternative(String name, float avgCost, FundingSource source) {
    	FundingSourceAlternative a1 = new FundingSourceAlternative();
    	a1.setName(name);
    	a1.setAvgCost(avgCost);
    	a1.setOffPeakTripsRate(100);
    	a1.setPeakHourTripsRate(100);
    	a1.setRevenue(new Float(100));
    	a1.setSourceURL("");
    	a1.setTaxRate(new Float(.04));
    	a1.setToll(false);
    	
    	session.saveOrUpdate(a1);
    	source.getAlternatives().add(a1);
    	session.saveOrUpdate(source);    	
    	
    	return a1;    	
    }
    
    private void createProjects() {
    	
    	int total = 8;
    	int totalAlts = 3;
    	
    	Project[] projects = new Project[total];
    	ProjectAlternative[] alts = new ProjectAlternative[total];
    	
    	for(int i = 0; i < total; i++) {
        	//Create the sources    		
    		projects[i] = createProject("Project " + i, i);
    		
    		//Add your alternatives
    		for(int j = 0; j< totalAlts; j++) {
    			if(j == 0) {
    				alts[i] = createAlternative("Project Alt "+j, (float)j, projects[i]);
    			} else {
        			createAlternative("Project Alt "+j, (float)j, projects[i]);    				
    			}
    		}    		
    	}
    	    	    	
    	//Create the suite
    	
    	ProjectRef ref;
    	ProjectAltRef altRef;
    	
    	for(int i = 0; i < total/2; i++) {
    		ref = new ProjectRef();
    		ref.setProject(projects[i]);    		
    		session.saveOrUpdate(ref);

    		altRef = new ProjectAltRef();
    		altRef.setAlternative(alts[i]);
    		ref.getAltRefs().add(altRef);    	    		
    		    		
    		pSuite.getReferences().add(ref);
    		ref.setSuite(pSuite);
    		
    		session.saveOrUpdate(altRef);
    		session.saveOrUpdate(ref);
    		session.saveOrUpdate(pSuite);
    	}    	    	
    }
    
    
    private Project createProject(String name, int type) {
    	if(type < 1) type = 1;
    	if(type > 8) type = 8;
    	Project project = new Project();
    	project.setName(name);
    	project.setTransMode(1);
    	project.setDescription("desc");
    	session.saveOrUpdate(project);
    	return project;
    }
    
    private ProjectAlternative createAlternative(String name, float cost, Project project) {
    	ProjectAlternative projAlt = new ProjectAlternative();
    	projAlt.setName(name);
    	projAlt.setShortDesc("shortDescription");
    	projAlt.setDetailedDesc("detailedDescription");
    	projAlt.setCost(cost);
    	projAlt.setLinks("links");
    	projAlt.setSponsor("sponsor");
    	projAlt.setStatementFor("statementFor");
    	projAlt.setStatementAgainst("statementAgainst");
    	projAlt.setCounty("county");
    	
    	session.saveOrUpdate(projAlt);
    	project.addAlternative(projAlt);
    	session.saveOrUpdate(project);    	
    	
    	return projAlt;    	
    }    
    
    public Float toFloat(String str) {    	
    	Float f = Float.parseFloat(str);
    	return f;
    }

    public int toInt(String str) {    	
    	int i = Integer.parseInt(str);
    	return i;
    }    
}
