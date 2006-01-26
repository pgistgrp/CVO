package org.pgist.tests;

import java.io.File;
import java.util.Date;
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
import org.pgist.cvo.CVO;
import org.pgist.cvo.Category;
import org.pgist.cvo.Tag;
import org.pgist.model.DiscourseObject;
import org.pgist.model.Post;
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
                Role roleAdmin = new Role();
                roleAdmin.setName("admin");
                roleAdmin.setDescription("Administrator");
                roleAdmin.setInternal(true);
                session.save(roleAdmin);
                System.out.println("---- successfully inserte a role: admin");
                
                //role - admin
                Role roleMember = new Role();
                roleMember.setName("member");
                roleMember.setDescription("Member");
                roleMember.setInternal(true);
                session.save(roleMember);
                System.out.println("---- successfully inserte a role: member");
                
                //role - moderator
                Role roleModerator = new Role();
                roleModerator.setName("moderator");
                roleModerator.setDescription("Moderator");
                roleModerator.setInternal(true);
                session.save(roleModerator);
                System.out.println("---- successfully inserte a role: moderator");
                
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
                admin.getRoles().add(roleModerator);
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
                guest.getRoles().add(roleMember);
                guest.getRoles().add(roleGuest);
                session.save(guest);
                System.out.println("---- successfully inserte a user: guest");
                
                //user - moderator
                User moderator = new User();
                moderator.setLoginname("moderator");
                moderator.setFirstname("moderator");
                moderator.setLastname("moderator");
                moderator.setPassword("moderator");
                moderator.encodePassword();
                moderator.setEmail("moderator@pgist.org");
                moderator.setEnabled(true);
                moderator.setDeleted(false);
                moderator.setInternal(true);
                moderator.getRoles().add(roleModerator);
                session.save(moderator);
                System.out.println("---- successfully inserte a user: moderator");
                
                //user - user1
                User user1 = new User();
                user1.setLoginname("user1");
                user1.setFirstname("user1");
                user1.setLastname("user1");
                user1.setPassword("user1");
                user1.encodePassword();
                user1.setEmail("user1@pgist.org");
                user1.setEnabled(true);
                user1.setDeleted(false);
                user1.setInternal(true);
                user1.getRoles().add(roleMember);
                session.save(user1);
                System.out.println("---- successfully inserte a user: user1");
                
                //user - user2
                User user2 = new User();
                user2.setLoginname("user2");
                user2.setFirstname("user2");
                user2.setLastname("user2");
                user2.setPassword("user2");
                user2.encodePassword();
                user2.setEmail("user2@pgist.org");
                user2.setEnabled(true);
                user2.setDeleted(false);
                user2.setInternal(true);
                user2.getRoles().add(roleMember);
                session.save(user2);
                System.out.println("---- successfully inserte a user: user2");
                
                //user - user3
                User user3 = new User();
                user3.setLoginname("user3");
                user3.setFirstname("user3");
                user3.setLastname("user3");
                user3.setPassword("user3");
                user3.encodePassword();
                user3.setEmail("user3@pgist.org");
                user3.setEnabled(true);
                user3.setDeleted(false);
                user3.setInternal(true);
                user3.getRoles().add(roleMember);
                session.save(user3);
                System.out.println("---- successfully inserte a user: user3");
                
                Post post = new Post();
                post.setRoot(post);
                post.setTarget(true);
                post.setOwner(admin);
                post.setParent(null);
                post.setTime(new Date());
                post.setContent("What is your concern about I5?");
                session.save(post);
                
                DiscourseObject dobj = new DiscourseObject();
                dobj.setOwner(admin);
                dobj.setRoot(post);
                
                CVO cvo = new CVO();
                cvo.setName("CVO test for I5");
                cvo.setOwner(admin);
                cvo.setDeleted(false);
                cvo.setDiscourseObject(dobj);
                
                dobj.setTarget(cvo);
                
                Post post1 = post.addChild("What I care most is the NOISE!", guest);
                
                Post post2 = post1.addChild("NOISE? I don't think it's important.", user1);
                
                Post post3 = post1.addChild("Em, I agree with you.", user2);
                
                Post post4 = post1.addChild("Noise is only trivial for I5!", user3);
                
                Post post5 = post4.addChild("Trivial? Oh, God. If you live besides I5!", guest);
                
                session.save(post1);
                session.save(post2);
                session.save(post3);
                session.save(post4);
                session.save(post5);
                session.save(dobj);
                session.save(cvo);
                
                Category category = new Category();
                category.setDeleted(false);
                category.setName("ALL");
                category.setParent(null);
                session.save(category);
                
                Tag tag = new Tag();
                tag.setName("Noise");
                session.save(tag);
                
                tag = new Tag();
                tag.setName("Money");
                session.save(tag);
                
                tag = new Tag();
                tag.setName("Time");
                session.save(tag);
                
                tag = new Tag();
                tag.setName("Safety");
                session.save(tag);
                
                tag = new Tag();
                tag.setName("Usability");
                session.save(tag);
                
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
