package org.pgist.cvo;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.pgist.model.DiscourseObject;
import org.pgist.model.Post;
import org.pgist.system.UserDAO;
import org.pgist.users.User;


/**
 * 
 * @author kenny
 *
 */
public class CVOAgent {
    
    
    private UserDAO userDAO;
    
    private CVODAO cvoDAO;
    
    
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    
    
    public void setCvoDAO(CVODAO cvoDAO) {
        this.cvoDAO = cvoDAO;
    }
    
    
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
        
        Post post = new Post();
        post.setContent(paragraph);
        post.setOwner(user);
        root.getChildren().add(post);
        post.setParent(root);
        post.setTime(new Date());
        
        cvoDAO.savePost(post);
        
        map.put("result", "true");
        
        return map;
    }//createConcern()
    
    
    public Map getPost(Long postId) throws Exception {
        Map map = new HashMap();
        
        Post post = cvoDAO.getPostById(postId);
        map.put("post", post);
        
        return map;
    }//getPost()
    
    
    public Map createPost(HttpSession session, Long parentId, String paragraph) throws Exception {
        Map map = new HashMap();
        
        User user = (User) session.getAttribute("user");
        user = userDAO.getUserById(user.getId(), true, false);
        
        Post parent = cvoDAO.getPostById(parentId);
        Post post = new Post();
        post.setParent(parent);
        post.setContent(paragraph);
        post.setOwner(user);
        post.setTime(new Date());
        
        cvoDAO.savePost(post);
        
        map.put("result", "true");
        
        return map;
    }//createPost()
    
    
}
