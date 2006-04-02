package org.pgist.ws;

import java.util.Collection;

import org.pgist.model.Discussible;
import org.pgist.model.Post;


/**
 * 
 * @author kenny
 *
 */
public class DiscussionServiceImpl implements DiscussionService {
    
    
    private DiscussibleDAO discussibleDAO = null;
    
    
    public void setDiscussibleDAO(DiscussibleDAO discussibleDAO) {
        this.discussibleDAO = discussibleDAO;
    }


    /*
     * ------------------------------------------------------------------------
     */
    
    
    public Discussible getDiscussible(Long id) throws Exception {
        return discussibleDAO.getDiscussible(id);
    }//getDiscussible()


    public Collection getBriefList(Long id, int count) throws Exception {
        Discussible discussible = getDiscussible(id);
        if (discussible!=null) {
            Post root = discussible.getDiscourseObject().getRoot();
            return discussibleDAO.getBriefList(root.getId(), count);
        }
        return null;
    }//getDiscussionBriefList()
    
    
}//class DiscussionServiceImpl
