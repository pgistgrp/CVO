package org.pgist.workflow.pgame.brainstorming;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.directwebremoting.WebContextFactory;
import org.pgist.model.Post;
import org.pgist.search.SearchHelper;
import org.pgist.system.UserDAO;
import org.pgist.users.User;


/**
 * 
 * @author kenny
 *
 */
public class BrainStormingAgent {
    
    
    private UserDAO userDAO;
    
    private BrainStormingDAO brainStormingDAO;
    
    private SearchHelper searchHelper;
    
    
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    
    
    public void setBrainStormingDAO(BrainStormingDAO brainStormingDAO) {
        this.brainStormingDAO = brainStormingDAO;
    }
    
    
    public void setSearchHelper(SearchHelper searchHelper) {
        this.searchHelper = searchHelper;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */


    public Map extractConcern(String paragraph) throws Exception {
        Map map = new HashMap();
        
        map.put("concern", "transportation, position, noise");
        
        return map;
    }//extractConcern()
    
    
    public Map createConcern(HttpSession session, Long brainStormingId, String paragraph, String concern) throws Exception {
        Map map = new HashMap();
        
        User user = (User) session.getAttribute("user");
        user = userDAO.getUserById(user.getId(), true, false);
        
        BrainStorming brainStorming = brainStormingDAO.getBrainStormingById(brainStormingId);
        Post root = brainStorming.getDiscourseObject().getRoot();
        
        Post post = root.addChild(paragraph, user);
        post.setCategory(Post.CATEGORY_CONCERN);
        
        brainStormingDAO.saveBrainStorming(brainStorming);
        
        /**
         * Lucene Operation
         */
        IndexWriter indexWriter = searchHelper.getIndexWriter();
        Document doc = new Document();
        doc.add(Field.Text("contents", post.getContent()));
        doc.add(Field.UnIndexed("type", "concern"));
        doc.add(Field.UnIndexed("id", ""+post.getId()));
        doc.add(Field.UnIndexed("cvoId", ""+brainStormingId));
        indexWriter.addDocument(doc);
        indexWriter.optimize();
        indexWriter.close();
        
        map.put("result", "true");
        
        return map;
    }//createConcern()
    
    
    public Map getPost(Long postId) throws Exception {
        Map map = new HashMap();
        
        Post post = brainStormingDAO.getPostById(postId);
        map.put("post", post);
        
        return map;
    }//getPost()
    
    
    public Map createPost(HttpSession session, Long cvoId, Long parentId, String paragraph) throws Exception {
        Map map = new HashMap();
        
        User user = (User) session.getAttribute("user");
        user = userDAO.getUserById(user.getId(), true, false);
        
        Post parent = brainStormingDAO.getPostById(parentId);
        Post post = parent.addChild(paragraph, user);
        brainStormingDAO.savePost(post);
        
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
    
    
    public String getPostGroups(HttpServletRequest request, Map params) throws Exception {
        BrainStorming brainStorming = brainStormingDAO.getBrainStormingById(new Long((String) (params.get("brainStormingId"))));
        request.setAttribute("brainStorming", brainStorming);
        
        String myPost = (String) params.get("myPost");
        if ("0".equals(myPost)) {
            return WebContextFactory.get().forwardToString("/WEB-INF/jsp/pgame/BrainStorming/myPostGroups.jsp");
        } else {
            return WebContextFactory.get().forwardToString("/WEB-INF/jsp/pgame/BrainStorming/postGroups.jsp");
        }
    }//getPostGroups()
    
    
    /*
    public Map getCategoryTags(Long categoryId) throws Exception {
        Map map = new HashMap();
        
        Collection all = brainStormingDAO.getAllTags();
        Category category = brainStormingDAO.getCategoryById(categoryId);
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
            parent = brainStormingDAO.getCategoryById(parentId);
        }
        map.put("parent", parent);
        
        try {
            Category category = brainStormingDAO.createCategory(parent, name);
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
        
        Category category = brainStormingDAO.getCategoryById(categoryId);
        Tag tag = brainStormingDAO.getTagById(tagId);
        
        category.getTags().add(tag);
        brainStormingDAO.saveCategory(category);
        
        Collection all = brainStormingDAO.getAllTags();
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
        
        Category category = brainStormingDAO.getCategoryById(categoryId);
        Tag tag = brainStormingDAO.getTagById(tagId);
        
        category.getTags().remove(tag);
        brainStormingDAO.saveCategory(category);
        
        Collection all = brainStormingDAO.getAllTags();
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
        
        Category parent = brainStormingDAO.getCategoryById(parentId);
        Category category = brainStormingDAO.getCategoryById(id);
        category.getParent().getChildren().remove(category);
        parent.getChildren().add(category);
        category.setParent(parent);
        brainStormingDAO.saveCategory(category);
        
        map.put("parent", parent);
        map.put("category", category);
        
        map.put("result", "true");
        return map;
    }//moveCategory()
    
    
    public Map createTag(String name) throws Exception {
        Map map = new HashMap();
        map.put("result", "false");
        
        Tag tag = brainStormingDAO.createTag(name);
        map.put("tag", tag);
        
        map.put("result", "true");
        return map;
    }//createTag()
    */
    
    
}
