package org.pgist.tests;

import java.io.File;

import org.apache.tools.ant.AntClassLoader;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.MatchingTask;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.pgist.projects.Project;
import org.pgist.projects.ProjectAlternative;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

/**
 * Ant task to load projects, alternatives, and project foorprints 
 * 
 * @author Guirong
 */
public class GISDataLoader extends MatchingTask {

    private ApplicationContext appContext = null;    
	private SessionFactory sessionFactory = null; 

    private String configPath;
    private String xlsPath;
	
    Session session = null;	
    
    public void setConfigPath(String configPath) {
        this.configPath = configPath;
    } 
    
    public void setXlsPath(String dataPath) {
    	this.xlsPath = dataPath;
    }
    
    /**
     * Execute the task
     */
    public void execute() throws BuildException {        	
    	setupConnection();
        Transaction tx = null;
        try {
            
        	////open excel file:
        	System.out.println(">>using data path: " + xlsPath);
        	
        	////print out all project names:
        	Workbook wb = Workbook.getWorkbook(new File(xlsPath) );
        	Sheet sht = wb.getSheet(0);     	
        	Cell cell;
        	
        	////todo: load data from file; insert into db
        	tx = session.beginTransaction();
        
            //clearOldData();
        	Project current = null;

        	for(int i=1; i<sht.getRows(); i++){   //the first row is omitted as column title
        		cell = sht.getCell(0, i);
        		String content = cell.getContents();
        		if(content != null && content.compareTo("") != 0){
        			if(current == null || current.getName().compareToIgnoreCase(content) != 0){
        				current = new Project();
        				current.setName(content);
	        			
	        			System.out.println(">>inserted project: " + content);     			
        			}
        			
        			ProjectAlternative pa = new ProjectAlternative();
    				pa.setName(sht.getCell(1, i).getContents());
    				
    				content = sht.getCell(2, i).getContents();
    				if(content!=null && content.compareTo("")!=0){
    					pa.setCost(makeNumber(content));
    				}
    				
    				pa.setCounty(sht.getCell(4, i).getContents());
    				pa.setProject(current);
    				current.getAlternatives().add(pa);
    				session.save(current);
    				session.save(pa);
        		}
        	}
            
            tx.commit();
        } catch(Exception e) {
        	System.out.println("EXCEPTION loading GIS data: " + e.getMessage());
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
                    configPath + "/context-funding.xml",
                }
            );    	
    	
      sessionFactory = (SessionFactory) appContext.getBean("sessionFactory");
      session = SessionFactoryUtils.getSession(sessionFactory, true);
      TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));    	
    }
    
    private void clearOldData() {
   		int num = session.createQuery("delete Project c").executeUpdate();
        System.out.println("Deleted " +num + " Project");        
    }
    
    /**
     * Make a right float number from a String
     * @param s
     * @return
     */
    private Double makeNumber(String s){
		String[] sa = s.split(",");
		StringBuffer sb = new StringBuffer();
		for(int j=0; j<sa.length; j++)
			sb.append(sa[j]);
		
		return Double.parseDouble(sb.toString());
    }
    
}
