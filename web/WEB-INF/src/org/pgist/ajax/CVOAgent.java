package org.pgist.ajax;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.pgist.cvo.CVO;
import org.pgist.cvo.CVODAO;
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
    
    
    public boolean createCVO(HttpSession session, String name, String question) throws Exception {
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
        
        return true;
    }
    
    
}
