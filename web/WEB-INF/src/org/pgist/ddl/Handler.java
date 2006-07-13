package org.pgist.ddl;

import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.hibernate.Query;
import org.hibernate.Session;
import org.pgist.cvo.CCT;
import org.pgist.cvo.Category;
import org.pgist.cvo.Concern;
import org.pgist.cvo.StopWord;
import org.pgist.cvo.Tag;
import org.pgist.cvo.TagReference;
import org.pgist.glossary.Term;
import org.pgist.glossary.TermCategory;
import org.pgist.system.EmailTemplate;
import org.pgist.users.Role;
import org.pgist.users.User;


/**
 * 
 * @author kenny
 *
 */
public abstract class Handler {
    
    
    protected static final Random random = new Random();
    
    protected static final DateFormat format = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, Locale.ENGLISH);
    
    protected String name;

    protected static File dataPath;
    
    protected static Session session;
    
    protected static final Map<String, Role> roleMap = new HashMap<String, Role>();
    
    protected static final Map<String, User> userMap = new HashMap<String, User>();
    
    protected static final Map<String, StopWord> stopwordMap = new HashMap<String, StopWord>();
    
    protected static final Map<String, Tag> tagMap = new HashMap<String, Tag>();
    
    protected static final Map<String, Category> categoryMap = new HashMap<String, Category>();
    
    protected static final Map<String, CCT> cctMap = new HashMap<String, CCT>();
    
    protected static final Map<Long, Concern> concernMap = new HashMap<Long, Concern>();
    
    protected static final Map<Tag, TagReference> tagRefMap = new HashMap<Tag, TagReference>();
    
    protected static final Map<String, Term> termMap = new HashMap<String, Term>();
    
    protected static final Map<String, TermCategory> termCatMap = new HashMap<String, TermCategory>();
    
    
    public void setName(String name) {
        this.name = name;
    }


    public static void setSession(Session session) {
        Handler.session = session;
    }


    /*
     * ------------------------------------------------------------------------
     */
    
    
    protected String getName(String suffix) {
        StringBuffer fileName = new StringBuffer(name);
        if (suffix!=null && !"".equals(suffix)) {
            fileName.append('-').append(suffix);
        }
        fileName.append(".xml");
        return fileName.toString();
    }//getName()
    
    
    private static final String hql_getRoleByName = "from Role r where r.name=?";
    
    
    protected Role getRoleByName(String roleName) throws Exception {
        if (roleMap.containsKey(roleName)) return roleMap.get(roleName);
        
        Query query = session.createQuery(hql_getRoleByName);
        query.setString(0, roleName);
        List list = query.list();
        if (list.size()==0) return null;
        
        Role role = (Role) list.get(0);
        roleMap.put(role.getName(), role);
        
        return role;
    }//getRoleByName()
    
    
    private static final String hql_getUserByLoginName = "from User u where u.loginname=?";
    
    
    protected User getUserByLoginName(String userName) throws Exception {
        if (userMap.containsKey(userName)) return userMap.get(userName);
        
        Query query = session.createQuery(hql_getUserByLoginName);
        query.setString(0, userName);
        List list = query.list();
        if (list.size()==0) return null;
        
        User user = (User) list.get(0);
        userMap.put(user.getLoginname(), user);
        
        return user;
    }//getUserByLoginName()
    
    
    private static final String hql_getStopWordByName = "from StopWord sw where sw.name=?";
    
    
    protected StopWord getStopWordByName(String stopwordName) throws Exception {
        if (stopwordMap.containsKey(stopwordName)) return stopwordMap.get(stopwordName);
        
        Query query = session.createQuery(hql_getStopWordByName);
        query.setString(0, stopwordName);
        List list = query.list();
        if (list.size()==0) return null;
        
        StopWord stopWord = (StopWord) list.get(0);
        stopwordMap.put(stopWord.getName(), stopWord);
        
        return stopWord;
    }//getTagByName()
    
    
    private static final String hql_getTagByName = "from Tag t where t.name=?";
    
    
    protected Tag getTagByName(String tagName) throws Exception {
        if (tagMap.containsKey(tagName)) return tagMap.get(tagName);
        
        Query query = session.createQuery(hql_getTagByName);
        query.setString(0, tagName);
        List list = query.list();
        if (list.size()==0) return null;
        
        Tag tag = (Tag) list.get(0);
        tagMap.put(tag.getName(), tag);
        
        return tag;
    }//getTagByName()
    
    
    private static final String hql_getCategoryByName = "from Category c where c.name=?";
    
    
    protected Category getCategoryByName(String categoryName) throws Exception {
        if (categoryMap.containsKey(categoryName)) return categoryMap.get(categoryName);
        
        Query query = session.createQuery(hql_getCategoryByName);
        query.setString(0, categoryName);
        List list = query.list();
        if (list.size()==0) return null;
        
        Category category = (Category) list.get(0);
        categoryMap.put(category.getName(), category);
        
        return category;
    }//getTagByName()
    
    
    private static final String hql_getCCTByName = "from Category c where c.name=?";
    
    
    protected CCT getCCTByName(String name) throws Exception {
        if (cctMap.containsKey(name)) return cctMap.get(name);
        
        Query query = session.createQuery(hql_getCCTByName);
        query.setString(0, name);
        List list = query.list();
        if (list.size()==0) return null;
        
        CCT cct = (CCT) list.get(0);
        cctMap.put(cct.getName(), cct);
        
        return cct;
    }//getCCTByName()
    
    
    protected void saveRole(Role role) throws Exception {
        if (!roleMap.containsValue(role)) {
            roleMap.put(role.getName(), role);
        }
        session.saveOrUpdate(role);
    }//saveRole()
    
    
    protected void saveUser(User user) throws Exception {
        if (!userMap.containsValue(user)) {
            userMap.put(user.getLoginname(), user);
        }
        session.saveOrUpdate(user);
    }//saveUser()
    
    
    protected void saveStopWord(StopWord stopWord) throws Exception {
        if (!stopwordMap.containsValue(stopWord)) {
            stopwordMap.put(stopWord.getName(), stopWord);
        }
        session.saveOrUpdate(stopWord);
    }//saveStopWord()
    
    
    protected void saveTag(Tag tag) throws Exception {
        if (!tagMap.containsValue(tag)) {
            tagMap.put(tag.getName(), tag);
        }
        session.saveOrUpdate(tag);
    }//saveTag()
    
    
    protected void saveCategory(Category category) throws Exception {
        if (!categoryMap.containsValue(category)) {
            categoryMap.put(category.getName(), category);
        }
        session.saveOrUpdate(category);
    }//saveCategory()
    
    
    protected void saveCCT(CCT cct) throws Exception {
        if (!cctMap.containsValue(cct)) {
            cctMap.put(cct.getName(), cct);
        }
        session.saveOrUpdate(cct);
    }//saveCCT()
    
    
    protected void saveConcern(Concern concern) throws Exception {
        if (!concernMap.containsValue(concern)) {
            concernMap.put(concern.getId(), concern);
        }
        session.saveOrUpdate(concern);
    }//saveConcern()


    protected void saveTagReference(TagReference tagRef) throws Exception {
        if (!tagRefMap.containsValue(tagRef)) {
            tagRefMap.put(tagRef.getTag(), tagRef);
        }
        session.saveOrUpdate(tagRef);
    }//saveTagReference()
    
    
    private static final String hql_getRoles = "from Role order by id";
    
    
    @SuppressWarnings("unchecked")
    protected List<Role> getRoles() {
        Query query = session.createQuery(hql_getRoles);
        return (List<Role>) query.list();
    }//getRoles()
    
    
    private static final String hql_getUsers = "from User order by id";
    
    
    @SuppressWarnings("unchecked")
    protected List<User> getUsers() {
        Query query = session.createQuery(hql_getUsers);
        return (List<User>) query.list();
    }//getUsers()
    
    
    private static final String hql_getStopWords = "from StopWord order by id";
    
    
    @SuppressWarnings("unchecked")
    protected List<StopWord> getStopWords() {
        Query query = session.createQuery(hql_getStopWords);
        return (List<StopWord>) query.list();
    }//getStopWords()
    
    
    private static final String hql_getTags = "from Tag order by id";
    
    
    @SuppressWarnings("unchecked")
    protected List<Tag> getTags() {
        Query query = session.createQuery(hql_getTags);
        return (List<Tag>) query.list();
    }//getStopWords()
    
    
    private static final String hql_getCategires = "from Category order by id";
    
    
    @SuppressWarnings("unchecked")
    protected List<Category> getCategories() {
        Query query = session.createQuery(hql_getCategires);
        return (List<Category>) query.list();
    }//getStopWords()
    
    
    private static final String hql_getCCTs = "from CCT order by id";
    
    
    @SuppressWarnings("unchecked")
    protected List<CCT> getCCTs() {
        Query query = session.createQuery(hql_getCCTs);
        return (List<CCT>) query.list();
    }//getStopWords()
    
    
    private static final String hql_getConcerns = "from Concern order by id";
    
    
    @SuppressWarnings("unchecked")
    protected List<Concern> getConcerns() {
        Query query = session.createQuery(hql_getConcerns);
        return (List<Concern>) query.list();
    }//getStopWords()
    
    
    protected TagReference ensureTagReference(CCT cct, Tag tag) throws Exception {
        if (tagRefMap.containsValue(tag)) {
            return (TagReference) tagRefMap.get(tag);
        } else {
            TagReference tagRef = new TagReference();
            tagRef.setTag(tag);
            tagRef.setCctId(cct.getId());
            tagRef.setTimes(1);
            saveTagReference(tagRef);
            tagRefMap.put(tag, tagRef);
            return tagRef;
        }
    }//ensureTagReference
    
    
    protected int parseTagStatus(String statusStr) throws Exception {
        if (statusStr==null || "".equals(statusStr)) {
            return Tag.STATUS_OFFICIAL;
        } else {
            int status = Integer.parseInt(statusStr);
            if (status<Tag.STATUS_OFFICIAL || status>Tag.STATUS_REJECTED) {
                throw new Exception("invalid tag status: "+status);
            }
            return status;
        }
    }//parseTagStatus()
    
    
    private static final String hql_getTerms = "from Term order by name";
    
    
    @SuppressWarnings("unchecked")
    protected List<Term> getTerms() {
        Query query = session.createQuery(hql_getTerms);
        return (List<Term>) query.list();
    }//getTerms()


    private static final String hql_getTermByName = "from Term t where t.name=?";
    
    
    protected Term getTermByName(String name) {
        if (termMap.containsKey(name)) return termMap.get(name);
        
        Query query = session.createQuery(hql_getTermByName);
        query.setString(0, name);
        List list = query.list();
        if (list.size()==0) return null;
        
        Term term = (Term) list.get(0);
        termMap.put(term.getName(), term);
        
        return term;
    }//getTermByName()


    protected void saveTerm(Term term) {
        if (!termMap.containsValue(term)) {
            termMap.put(term.getName(), term);
        }
        session.saveOrUpdate(term);
    }//saveTerm()


    private static final String hql_getTermCatByName = "from TermCategory tc where tc.name=?";
    
    
    protected TermCategory getTermCatByName(String name) {
        if (termCatMap.containsKey(name)) return termCatMap.get(name);
        
        Query query = session.createQuery(hql_getTermCatByName);
        query.setString(0, name);
        List list = query.list();
        if (list.size()==0) return null;
        
        TermCategory tc = (TermCategory) list.get(0);
        termCatMap.put(tc.getName(), tc);
        
        return tc;
    }//getTermByName()


    protected void saveTermCat(TermCategory termCategory) {
        if (!termCatMap.containsValue(termCategory)) {
            termCatMap.put(termCategory.getName(), termCategory);
        }
        session.saveOrUpdate(termCategory);
    }//saveTerm()
    
    
    private static final String hql_getTemplates = "from EmailTemplate";
    
    
    @SuppressWarnings("unchecked")
    protected List<EmailTemplate> getTemplates() {
        Query query = session.createQuery(hql_getTemplates);
        return (List<EmailTemplate>) query.list();
    }//getTemplates()
    
    
    protected void persist(Object obj) {
        session.saveOrUpdate(obj);
    }//persist()


    /*
     * ------------------------------------------------------------------------
     */
    
    
    abstract protected void doImports(Element root) throws Exception;
    
    
    protected void imports(String suffix) throws Exception {
        String fileName = getName(suffix);
        
        SAXReader reader = new SAXReader();
        Document document = reader.read(new File(dataPath, fileName));
        Element root = document.getRootElement();
        
        doImports(root);
    }//imports()
    
    
    abstract protected void doExports(Document document) throws Exception;
    
    
    public final void exports(String suffix) throws Exception {
        Document document = DocumentHelper.createDocument();
        
        doExports(document);
        
        String fileName = getName(suffix);
        OutputFormat format = OutputFormat.createPrettyPrint();
        XMLWriter writer = new XMLWriter(new FileWriter(new File(dataPath, fileName)), format);
        writer.write(document);
        writer.close();
    }//exports()
    
    
}//class Handler
