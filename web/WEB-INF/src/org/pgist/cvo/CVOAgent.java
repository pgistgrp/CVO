package org.pgist.cvo;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.pgist.model.DiscourseObject;
import org.pgist.model.Post;
import org.pgist.search.SearchHelper;
import org.pgist.system.UserDAO;
import org.pgist.users.User;

import uk.ltd.getahead.dwr.WebContext;
import uk.ltd.getahead.dwr.WebContextFactory;


/**
 * 
 * @author kenny
 *
 */
public class CVOAgent {
    
    
    private CCTService cvoService;
    
    private UserDAO userDAO;
    
    private CVODAO cvoDAO;
    
    private SearchHelper searchHelper;
    
    
    public void setCvoService(CCTService cvoService) {
        this.cvoService = cvoService;
    }


    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    
    
    public void setCvoDAO(CVODAO cvoDAO) {
        this.cvoDAO = cvoDAO;
    }
    
    
    public void setSearchHelper(SearchHelper searchHelper) {
        this.searchHelper = searchHelper;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */


    public Map getCVOList() throws Exception {
        Map map = new HashMap();
        Collection cvoList = cvoDAO.getCVOList();
        map.put("cvoList", cvoList);
        return map;
    }//getCVOList()
    
    
    public Map createCVO(HttpSession session, String name, String question) throws Exception {
        Map map = new HashMap();
        
        if (name==null || question==null) {
            map.put("result", "false");
            map.put("alert", "Please input the name and the question.");
            return map;
        }
        
        name = name.trim();
        question = question.trim();
        
        if ("".equals(name) || "".equals(question)) {
            map.put("result", "false");
            map.put("alert", "Please input the name and the question.");
            return map;
        }
        
        User user = (User) session.getAttribute("user");
        user = userDAO.getUserById(user.getId(), true, false);
        
        Post post = new Post();
        post.setRoot(post);
        post.setTarget(true);
        post.setOwner(user);
        post.setParent(null);
        post.setTime(new Date());
        post.setContent(question);
        
        DiscourseObject dobj = new DiscourseObject();
        dobj.setOwner(user);
        dobj.setRoot(post);
        
        CVO cvo = new CVO();
        cvo.setName(name);
        cvo.setOwner(user);
        cvo.setDeleted(false);
        cvo.setDiscourseObject(dobj);
        
        dobj.setTarget(cvo);
        
        cvoDAO.savePost(post);
        cvoDAO.saveDO(dobj);
        cvoDAO.saveCVO(cvo);
        
        map.put("result", "true");
        return map;
    }//createCVO()
    
    
    public Map extractConcern(String paragraph) throws Exception {
        Map map = new HashMap();
        
        map.put("concern", "transportation, position, noise");
        
        return map;
    }//extractConcern()
    
    
    public Map createConcern(HttpSession session, Long cvoId, String paragraph, String concern) throws Exception {
        Map map = new HashMap();
        
        User user = (User) session.getAttribute("user");
        user = userDAO.getUserById(user.getId(), true, false);
        
        CVO cvo = cvoDAO.getCVOById(cvoId);
        Post root = cvo.getDiscourseObject().getRoot();
        
        Post post = root.addChild(paragraph, user);
        post.setCategory(Post.CATEGORY_CONCERN);
        
        cvoDAO.savePost(post);
        
        /**
         * Lucene Operation
         */
        IndexWriter indexWriter = searchHelper.getIndexWriter();
        Document doc = new Document();
        doc.add(Field.Text("contents", post.getContent()));
        doc.add(Field.UnIndexed("type", "concern"));
        doc.add(Field.UnIndexed("id", ""+post.getId()));
        doc.add(Field.UnIndexed("cvoId", ""+cvoId));
        indexWriter.addDocument(doc);
        indexWriter.optimize();
        indexWriter.close();
        
        map.put("result", "true");
        
        return map;
    }//createConcern()
    
    
    public Map getPost(Long postId) throws Exception {
        Map map = new HashMap();
        
        Post post = cvoDAO.getPostById(postId);
        map.put("post", post);
        
        return map;
    }//getPost()
    
    
    public Map createPost(HttpSession session, Long cvoId, Long parentId, String paragraph) throws Exception {
        Map map = new HashMap();
        
        User user = (User) session.getAttribute("user");
        user = userDAO.getUserById(user.getId(), true, false);
        
        Post parent = cvoDAO.getPostById(parentId);
        Post post = parent.addChild(paragraph, user);
        cvoDAO.savePost(post);
        
        /**
         * Lucene Operation
         */
        IndexWriter indexWriter = searchHelper.getIndexWriter();
        Document doc = new Document();
        doc.add(Field.Text("contents", post.getContent()));
        doc.add(Field.UnIndexed("type", "comment"));
        doc.add(Field.UnIndexed("id", ""+post.getId()));
        doc.add(Field.UnIndexed("cvoId", ""+cvoId));
        indexWriter.addDocument(doc);
        indexWriter.optimize();
        indexWriter.close();
        
        map.put("result", "true");
        
        return map;
    }//createPost()
    
    
    public String getPostGroups(Map params) throws Exception {
        CVOForm form = new CVOForm();
        CVO cvo = cvoDAO.getCVOById(new Long((String) (params.get("cvoId"))));
        form.setRoot(cvo.getDiscourseObject().getRoot());
        
        WebContext context = WebContextFactory.get();
        HttpServletRequest request = context.getHttpServletRequest();
        request.setAttribute("cvoForm", form);
        
        String myPost = (String) params.get("myPost");
        if ("0".equals(myPost)) {
            return context.forwardToString("/WEB-INF/jsp/cvo/myPostGroups.jsp");
        } else {
            return context.forwardToString("/WEB-INF/jsp/cvo/postGroups.jsp");
        }
    }//getPostGroups()
    
    
    public Map getCategoryTags(Long categoryId) throws Exception {
        Map map = new HashMap();
        
        Collection all = cvoDAO.getAllTags();
        Category category = cvoDAO.getCategoryById(categoryId);
        Collection tags = category.getTags();
        all.removeAll(tags);
        map.put("all", all);
        map.put("tags", tags);
        
        return map;
    }//getCategoryTags()
    
    
    public Map createCategory(Long parentId, String name) throws Exception {
        Map map = new HashMap();
        map.put("result", "false");
        
        Category parent = null;
        if (parentId!=null) {
            parent = cvoDAO.getCategoryById(parentId);
        }
        map.put("parent", parent);
        
        try {
            Category category = cvoDAO.createCategory(parent, name);
            map.put("category", category);
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        map.put("result", "true");
        return map;
    }//createCategory()
    
    
    public Map addTagToCategory(Long categoryId, Long tagId) throws Exception {
        Map map = new HashMap();
        map.put("result", "false");
        
        Category category = cvoDAO.getCategoryById(categoryId);
        Tag tag = cvoDAO.getTagById(tagId);
        
        category.getTags().add(tag);
        cvoDAO.saveCategory(category);
        
        Collection all = cvoDAO.getAllTags();
        Collection tags = category.getTags();
        all.removeAll(tags);
        map.put("all", all);
        map.put("tags", tags);
        
        map.put("result", "true");
        return map;
    }//addTagToCategory()
    
    
    public Map deleteTagFromCategory(Long categoryId, Long tagId) throws Exception {
        Map map = new HashMap();
        map.put("result", "false");
        
        Category category = cvoDAO.getCategoryById(categoryId);
        Tag tag = cvoDAO.getTagById(tagId);
        
        category.getTags().remove(tag);
        cvoDAO.saveCategory(category);
        
        Collection all = cvoDAO.getAllTags();
        Collection tags = category.getTags();
        all.removeAll(tags);
        map.put("all", all);
        map.put("tags", tags);
        
        map.put("result", "true");
        return map;
    }//deleteTagFromCategory()
    
    
    public Map moveCategory(Long parentId, Long id) throws Exception {
        Map map = new HashMap();
        map.put("result", "false");
        
        Category parent = cvoDAO.getCategoryById(parentId);
        Category category = cvoDAO.getCategoryById(id);
        category.getParent().getChildren().remove(category);
        parent.getChildren().add(category);
        category.setParent(parent);
        cvoDAO.saveCategory(category);
        
        map.put("parent", parent);
        map.put("category", category);
        
        map.put("result", "true");
        return map;
    }//moveCategory()
    
    
    public Map createTag(String name) throws Exception {
        Map map = new HashMap();
        map.put("result", "false");
        
        Tag tag = cvoDAO.createTag(name);
        map.put("tag", tag);
        
        map.put("result", "true");
        return map;
    }//createTag()
    
    
}//class CVOAgent
