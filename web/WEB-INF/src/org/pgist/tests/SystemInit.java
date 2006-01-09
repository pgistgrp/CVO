package org.pgist.tests;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.MatchingTask;
import org.apache.tools.ant.types.FileSet;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.util.ArrayHelper;
import org.pgist.users.Role;
import org.pgist.users.User;


/**
 * Init the PGIST system
 * @author kenny
 *
 */
public class SystemInit extends MatchingTask {
    
    
    private List fileSets = new LinkedList();
    private String configurationFile = null;
    
    
    public void addFileset(FileSet set) {
        fileSets.add(set);
    }
    
    
    /**
     * Set a properties file
     * @param propertiesFile the properties file name
     */
    public void setProperties(File propertiesFile) {
        if ( !propertiesFile.exists() ) {
            throw new BuildException("Properties file: " + propertiesFile + " does not exist.");
        }

        log("Using properties file " + propertiesFile, Project.MSG_DEBUG);
    }
    

    /**
     * Set a <literal>.cfg.xml</literal> file, which will be
     * loaded as a resource, from the classpath
     * @param configurationFile the path to the resource
     */
    public void setConfig(String configurationFile) {
        this.configurationFile = configurationFile;
    }


    /**
     * Execute the task
     */
    public void execute() throws BuildException {
        try {
            Configuration cfg = getConfiguration();
            SessionFactory sessionFactory = cfg.buildSessionFactory();
            
            //initialization begins here
            
            /* create administrator user */
            Session session = null;
            try {
                session = sessionFactory.openSession();
                Transaction transaction = session.beginTransaction();
                
                //role - member
                Role roleMember = new Role();
                roleMember.setName("admin");
                roleMember.setDescription("Administrator");
                roleMember.setInternal(true);
                session.save(roleMember);
                System.out.println("---- successfully inserte a role: admin");
                
                //role - admin
                Role roleAdmin = new Role();
                roleAdmin.setName("member");
                roleAdmin.setDescription("Member");
                roleAdmin.setInternal(true);
                session.save(roleAdmin);
                System.out.println("---- successfully inserte a role: member");
                
                //role - guest
                Role roleGuest = new Role();
                roleGuest.setName("guest");
                roleGuest.setDescription("Guest");
                roleGuest.setInternal(true);
                session.save(roleGuest);
                System.out.println("---- successfully inserte a role: guest");
                
                //user - admin
                User admin = new User();
                admin.setLoginname("admin");
                admin.setFirstname("Admin");
                admin.setLastname("Admin");
                admin.setPassword("admin");
                admin.encodePassword();
                admin.setEmail("admin@pgist.org");
                admin.setEnabled(true);
                admin.setDeleted(false);
                admin.setInternal(true);
                admin.getRoles().add(roleMember);
                admin.getRoles().add(roleAdmin);
                session.save(admin);
                System.out.println("---- successfully inserte a user: admin");
                
                //user - guest
                User guest = new User();
                guest.setLoginname("guest");
                guest.setFirstname("Guest");
                guest.setLastname("Guest");
                guest.setPassword("guest");
                guest.encodePassword();
                guest.setEmail("guest@pgist.org");
                guest.setEnabled(true);
                guest.setDeleted(false);
                guest.setInternal(true);
                guest.getRoles().add(roleGuest);
                session.save(guest);
                System.out.println("---- successfully inserte a user: guest");
                
                transaction.commit();
            } catch(Exception ex) {
                ex.printStackTrace();
            } finally {
                session.close();
            }
            
            //initialization ends here
            
        } catch (Exception e) {
            throw new BuildException(e);
        }
    }
    

    private String[] getFiles() {

        List files = new LinkedList();
        for ( Iterator i = fileSets.iterator(); i.hasNext(); ) {

            FileSet fs = (FileSet) i.next();
            DirectoryScanner ds = fs.getDirectoryScanner( getProject() );

            String[] dsFiles = ds.getIncludedFiles();
            for (int j = 0; j < dsFiles.length; j++) {
                File f = new File(dsFiles[j]);
                if ( !f.isFile() ) {
                    f = new File( ds.getBasedir(), dsFiles[j] );
                }

                files.add( f.getAbsolutePath() );
            }
        }

        return ArrayHelper.toStringArray(files);
    }
    

    private Configuration getConfiguration() throws Exception {
        Configuration cfg = new Configuration();
        if (configurationFile != null) cfg.configure( new File(configurationFile) );

        String[] files = getFiles();
        for (int i = 0; i < files.length; i++) {
            String filename = files[i];
            if ( filename.endsWith(".jar") ) {
                cfg.addJar( new File(filename) );
            }
            else {
                cfg.addFile(filename);
            }
        }
        return cfg;
    }


}//class SystemInit
