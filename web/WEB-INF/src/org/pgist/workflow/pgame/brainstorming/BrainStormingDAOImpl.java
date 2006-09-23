package org.pgist.workflow.pgame.brainstorming;

import java.util.List;

import org.pgist.model.Post;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


/**
 * 
 * @author kenny
 *
 */
public class BrainStormingDAOImpl extends HibernateDaoSupport implements BrainStormingDAO {
    
    
    private static final String hql_getBrainStorming = "from BrainStorming bs where deleted=? and activityId=? and counts=?";
    
    
    /**
     * @param activityId
     * @param count
     * @return
     * @throws Exception
     */
    public BrainStorming getBrainStorming(Long activityId, int counts) throws Exception {
        List list = getHibernateTemplate().find(
                hql_getBrainStorming,
                new Object[] {
                        new Boolean(false),
                        activityId,
                        new Integer(counts)
                }
        );
        if (list.size()==0) return null;
        return (BrainStorming) list.get(0);
    }//getBrainStorming()
    
    
    /**
     * @param brainStorming
     * @throws Exception
     */
    public void saveBrainStorming(BrainStorming brainStorming) throws Exception {
        getHibernateTemplate().saveOrUpdate(brainStorming);
    }//saveBrainStorming()


    /**
     * @param brainStormingId
     * @return
     * @throws Exception
     */
    public BrainStorming getBrainStormingById(Long brainStormingId) throws Exception {
        BrainStorming brainStorming = (BrainStorming) getHibernateTemplate().load(BrainStorming.class, brainStormingId);
        if (brainStorming!=null && brainStorming.isDeleted()) return null;
        return brainStorming;
    }//getBrainStormingById()


    /**
     * @param id
     * @return
     * @throws Exception
     */
    public Post getPostById(Long id) throws Exception {
        return (Post) getHibernateTemplate().load(Post.class, id);
    }//getPostById()


    /**
     * @param post
     * @return
     * @throws Exception
     */
    public void savePost(Post post) throws Exception {
        getHibernateTemplate().saveOrUpdate(post);
    }//savePost()
    
    
}//class BrainStormingDAOImpl
