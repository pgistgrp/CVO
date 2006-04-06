package org.pgist.tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.loader.AntClassLoader2;
import org.apache.tools.ant.taskdefs.MatchingTask;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jaxen.expr.iter.IterableAncestorAxis;
import org.pgist.cvo.CCT;
import org.pgist.cvo.CCTService;
import org.pgist.cvo.CVO;
import org.pgist.cvo.CVODAO1;
import org.pgist.cvo.Category;
import org.pgist.cvo.Concern;
import org.pgist.cvo.Tag;
import org.pgist.model.DiscourseObject;
import org.pgist.model.Post;
import org.pgist.system.UserDAO;
import org.pgist.users.Role;
import org.pgist.users.User;
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
public class SystemInit extends MatchingTask {
    
    
    private String configPath;
    
    private String dataPath;
    
    private ApplicationContext appContext = null;
    
    private SessionFactory sessionFactory = null; 
    
    private CCTService cctService;
    
    private CVODAO1 cvoDAO;
    
    private UserDAO userDAO;
    
    private WorkflowEngine engine;
    
    private Map roles = new HashMap();
    
    private Map users = new HashMap();
    
    
    public void setConfigPath(String configPath) {
        this.configPath = configPath;
    }


    public void setDataPath(String dataPath) {
        this.dataPath = dataPath;
    }


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
        
        cctService = (CCTService) appContext.getBean("cctService");
        cvoDAO = (CVODAO1) appContext.getBean("cvoDAO");
        userDAO = (UserDAO) appContext.getBean("userDAO");
        engine = (WorkflowEngine) appContext.getBean("engine");
    }//setUp()
    
    
    protected void tearDown() throws Exception {
        SessionHolder sessionHolder = (SessionHolder) TransactionSynchronizationManager.unbindResource(sessionFactory);
        sessionHolder.getSession().close();
        SessionFactoryUtils.releaseSession(sessionHolder.getSession(), sessionFactory);
    }//tearDown()
    
    
    /**
     * Execute the task
     */
    public void execute() throws BuildException {
        try {
            //setup hibernate and spring
            setUp();
            
            initRole();
            initUser();
            initCCT();
            initCVO();
            initTags();
            
            initWorkflow();
            
            //tear down hibernate and spring
            tearDown();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BuildException(e);
        }
    }
    
    
    private void initRole() throws Exception {
        //role - admin
        Role roleAdmin = new Role();
        roleAdmin.setName("admin");
        roleAdmin.setDescription("Administrator");
        roleAdmin.setInternal(true);
        userDAO.addRole(roleAdmin);
        roles.put("admin", roleAdmin);
        System.out.println("---- successfully inserte a role: admin");
        
        //role - member
        Role roleMember = new Role();
        roleMember.setName("member");
        roleMember.setDescription("Member");
        roleMember.setInternal(true);
        userDAO.addRole(roleMember);
        roles.put("member", roleMember);
        System.out.println("---- successfully inserte a role: member");
        
        //role - moderator
        Role roleModerator = new Role();
        roleModerator.setName("moderator");
        roleModerator.setDescription("Moderator");
        roleModerator.setInternal(true);
        userDAO.addRole(roleModerator);
        roles.put("moderator", roleModerator);
        System.out.println("---- successfully inserte a role: moderator");
        
        //role - guest
        Role roleGuest = new Role();
        roleGuest.setName("guest");
        roleGuest.setDescription("Guest");
        roleGuest.setInternal(true);
        userDAO.addRole(roleGuest);
        roles.put("guest", roleGuest);
        System.out.println("---- successfully inserte a role: guest");
    }//initRole()
    
    
    private void initUser() throws Exception {
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
        admin.getRoles().add(roles.get("member"));
        admin.getRoles().add(roles.get("moderator"));
        admin.getRoles().add(roles.get("admin"));
        userDAO.saveUser(admin);
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
        guest.getRoles().add(roles.get("member"));
        guest.getRoles().add(roles.get("guest"));
        userDAO.saveUser(guest);
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
        moderator.getRoles().add(roles.get("moderator"));
        userDAO.saveUser(moderator);
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
        user1.getRoles().add(roles.get("member"));
        userDAO.saveUser(user1);
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
        user2.getRoles().add(roles.get("member"));
        userDAO.saveUser(user2);
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
        user3.getRoles().add(roles.get("member"));
        userDAO.saveUser(user3);
        System.out.println("---- successfully inserte a user: user3");
    }//initUser()
    
    
    private void initCCT() throws Exception {
        CCT cct = new CCT();
        cct.setCreateTime(new Date());
        cct.setCreator((User) users.get("moderator"));
        cct.setInstruction("This is a test.");
        cct.setName("CCT Test.");
        cct.setPurpose("To test cct.");
        
        cctService.save(cct);
        
        File file = new File(dataPath, "concerns.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        
        Map participants = new HashMap();
        Map tags = new HashMap();
        Map categories = new HashMap();
        
        while (true) {
            String line = reader.readLine();
            if (line==null) break;
            
            if (line.length()<1) continue;
            
            if (line.charAt(0)=='#') continue;
            
            String[] s = line.split("######");
            User user = (User) participants.get(s[2]);
            if (user==null) {
                user = new User();
                user.setLoginname(s[2].toLowerCase());
                user.setLastname(s[2]);
                user.setFirstname(s[3]);
                user.setEmail(s[2].toLowerCase()+"@pgist.org");
                user.setEnabled(true);
                user.setDeleted(false);
                user.setPassword(s[2].toLowerCase());
                user.encodePassword();
                userDAO.saveUser(user);
                participants.put(s[2], user);
            }
            
            Concern concern = new Concern();
            concern.setAuthor(user);
            concern.setCct(cct);
            concern.setContent(s[4]);
            concern.setCreateTime(new Date());
            concern.setDeleted(false);
            
            String[] cateStrs = s[6].split(",");
            for (int i=0; i<cateStrs.length; i++) {
                Category category = (Category) categories.get(cateStrs[i]);
                if (category==null) {
                    category = new Category();
                    category.setName(cateStrs[i]);
                    category.setDeleted(false);
                    cvoDAO.saveCategory(category);
                }
            }//for i
            
            String[] tagStrs = s[5].split(",");
            for (int i=0; i<tagStrs.length; i++) {
                Tag tag = (Tag) tags.get(tagStrs[i]);
                if (tag==null) {
                    tag = new Tag();
                    tag.setCount(tag.getCount()+1);
                    tag.setDescription(tagStrs[i]);
                    tag.setStatus(Tag.STATUS_CANDIDATE);
                    tag.setName(tagStrs[i]);
                    tags.put(tagStrs[i], tag);
                    cvoDAO.saveTag(tag);
                }
                concern.getTags().add(tag);
                for (Iterator iter=categories.keySet().iterator(); iter.hasNext(); ) {
                    Category category = (Category) iter.next();
                    category.getTags().add(tag);
                    tag.getCategories().add(category);
                }
            }//for i
            
            cct.getConcerns().add(concern);
            cctService.save(cct);
        }//while
    }//initCCT()


    private void initCVO() throws Exception {
        Post post = new Post();
        post.setRoot(post);
        post.setTarget(true);
        post.setOwner((User) users.get("admin"));
        post.setParent(null);
        post.setTime(new Date());
        post.setContent("What is your concern about I5?");
        cvoDAO.savePost(post);
        
        DiscourseObject dobj = new DiscourseObject();
        dobj.setOwner((User) users.get("admin"));
        dobj.setRoot(post);
        cvoDAO.saveDO(dobj);
        
        CVO cvo = new CVO();
        cvo.setName("CVO test for I5");
        cvo.setOwner((User) users.get("admin"));
        cvo.setDeleted(false);
        cvo.setDiscourseObject(dobj);
        cvoDAO.saveCVO(cvo);
        
        dobj.setTarget(cvo);
        cvoDAO.saveDO(dobj);
        
        Post post1 = post.addChild("What I care most is the NOISE!", (User) users.get("guest"));
        cvoDAO.savePost(post1);
        
        Post post2 = post1.addChild("NOISE? I don't think it's important.", (User) users.get("user1"));
        cvoDAO.savePost(post2);
        
        Post post3 = post1.addChild("Em, I agree with you.", (User) users.get("user2"));
        cvoDAO.savePost(post3);
        
        Post post4 = post1.addChild("Noise is only trivial for I5!", (User) users.get("user3"));
        cvoDAO.savePost(post4);
        
        Post post5 = post4.addChild("Trivial? Oh, God. If you live besides I5!", (User) users.get("guest"));
        cvoDAO.savePost(post5);
    }//initCVO()
    
    
    private void initTags() throws Exception {
        Category category = new Category();
        category.setDeleted(false);
        category.setName("ALL");
        category.setParent(null);
        cvoDAO.saveCategory(category);
        
        Tag tag = new Tag();
        tag.setName("Noise");
        cvoDAO.saveTag(tag);
        
        tag = new Tag();
        tag.setName("Money");
        cvoDAO.saveTag(tag);
        
        tag = new Tag();
        tag.setName("Time");
        cvoDAO.saveTag(tag);
        
        tag = new Tag();
        tag.setName("Safety");
        cvoDAO.saveTag(tag);
        
        tag = new Tag();
        tag.setName("Usability");
        cvoDAO.saveTag(tag);
    }//initTags()
    
    
    private void initWorkflow() throws Exception {
        File file = new File(dataPath, "pgames.xml");
        engine.addPGames(new FileInputStream(file));
        file = new File(dataPath, "templates.xml");
        engine.addTemplates(new FileInputStream(file));
    }//initWorkflow()
    

}//class SystemInit
