package org.pgist.tests;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.tools.ant.AntClassLoader;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.MatchingTask;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.pgist.funding.Consumption;
import org.pgist.funding.ZipCodeFactor;
import org.pgist.funding.ZipCodeGas;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import au.com.bytecode.opencsv.CSVReader;

/**
 * Use this task to reload all the tax calculator data
 * 
 * @author Matt Paulin
 */
public class TaxCalcDataLoader extends MatchingTask {

    private ApplicationContext appContext = null;    
	private SessionFactory sessionFactory = null; 

    private String configPath;
	
    Session session = null;	
	
    private File dataPath;

    public void setDataPath(String dataPath) {
        this.dataPath = new File(dataPath);
    }
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
            
			loadConsumptionData();
	    	loadZipCodeGas();
	    	loadZipCodeFactor();
            
            tx.commit();
        } catch(Exception e) {
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
    }
    
    private void loadConsumptionData() throws IOException {
    	
    	deleteAllConsumption();
    	
    	Consumption cons;
    	
        CSVReader reader = new CSVReader(new FileReader(new File(dataPath, "consumption.csv")));
        String [] nextLine;
        
        while ((nextLine = reader.readNext()) != null) {

        	cons = new Consumption();
        	cons.setIncomeLower(toFloat(nextLine[0]));
        	cons.setIncomeUpper(toFloat(nextLine[1]));
        	cons.setSize1(toFloat(nextLine[2]));
        	cons.setSize2(toFloat(nextLine[3]));
        	cons.setSize3(toFloat(nextLine[4]));
        	cons.setSize4(toFloat(nextLine[5]));
        	cons.setSize5(toFloat(nextLine[6]));
        	save(cons);
        }    	
    }
    
    private void loadZipCodeGas() throws IOException {
    	deleteAllZipCodeGas();
    	
    	ZipCodeGas zcg;
    	
        CSVReader reader = new CSVReader(new FileReader(new File(dataPath, "zipCodeGas.csv")));
        String [] nextLine;
        
        while ((nextLine = reader.readNext()) != null) {

        	zcg = new ZipCodeGas();
        	zcg.setZipcode(nextLine[0]);
        	zcg.setAvgGas(toFloat(nextLine[1]));
        	save(zcg);
        }    	    	
    }
    
    private void loadZipCodeFactor() throws IOException {
    	deleteAllZipCodeFactor();
    	
    	ZipCodeFactor zcf;
    	
        CSVReader reader = new CSVReader(new FileReader(new File(dataPath, "zipCodeFactors.csv")));
        String [] nextLine;
        
        while ((nextLine = reader.readNext()) != null) {

        	zcf = new ZipCodeFactor();
        	zcf.setZipcode(nextLine[0]);
        	zcf.setSR99(toInt(nextLine[1]));
        	zcf.setI405S(toInt(nextLine[2]));
        	zcf.setSR520(toInt(nextLine[3]));
        	zcf.setI90(toInt(nextLine[4]));
        	zcf.setSR167(toInt(nextLine[5]));
        	zcf.setParking(toInt(nextLine[6]));
        	zcf.setI405N(toInt(nextLine[7]));
        	save(zcf);
        }    	    	    	
    }
    
    public Float toFloat(String str) {    	
    	Float f = Float.parseFloat(str);
    	return f;
    }

    public int toInt(String str) {    	
    	int i = Integer.parseInt(str);
    	return i;
    }
    
	public void save(ZipCodeFactor zcg) {
		session.saveOrUpdate(zcg);				
	}

	public void deleteAllZipCodeFactor() {
		int num = session.createQuery("delete ZipCodeFactor c").executeUpdate();
        System.out.println("Deleted " +num + " ZipCodeFactor");        
	}	    
    
    
	public void save(ZipCodeGas zcg) {
		session.saveOrUpdate(zcg);				
	}

	public void deleteAllZipCodeGas() {
		int num = session.createQuery("delete ZipCodeGas c").executeUpdate();
        System.out.println("Deleted " +num + " ZipCodeGas");        
	}	    
    
    
	public void save(Consumption zcg) {
		session.saveOrUpdate(zcg);				
	}

	public void deleteAllConsumption() {
		int num = session.createQuery("delete Consumption c").executeUpdate();
        System.out.println("Deleted " +num + " Consumptions");        
	}	    
}
