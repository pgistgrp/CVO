package org.pgist.discussion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.pgist.system.BaseDAOImpl;
import org.pgist.util.PageSetting;
import org.pgist.util.WebUtils;


/**
 * 
 * @author kenny
 *
 */
public class DiscussionDAOImpl extends BaseDAOImpl implements DiscussionDAO {
    
    
    private static final String hql_getDiscussion = "from Discussion d where d.deleted=? and d.targetType=? and d.targetId=?";
    
    
    public Discussion getDiscussion(String targetType, Long targetId) throws Exception {
        List list = getHibernateTemplate().find(hql_getDiscussion, new Object[] {
                false,
                targetType,
                targetId,
        });
        
        if (list.size()==0) return null;
        
        return (Discussion) list.get(0);
    }//getDiscussion()
    

    private static final String hql_getPosts_A = "from DiscussionPost p where p.discussionId=? and p.deleted=? and p.parent=? order by p.id";
    
    
    public Collection getPosts(Discussion discussion) throws Exception {
        return getHibernateTemplate().find(hql_getPosts_A, new Object[] {
                discussion.getId(),
                false,
                null,
        });
    }//getPosts()
    
    
    private static final String hql_getPosts_B_1 = "select count(p.id) from DiscussionPost p where p.discussionId=? and p.deleted=? and p.parent=? order by p.id";
    
    private static final String hql_getPosts_B_2 = "from DiscussionPost p where p.discussionId=? and p.deleted=? and p.parent=? order by p.id";
    
    
    public Collection getPosts(Discussion discussion, PageSetting setting) throws Exception {
        //get total rows number
        List list = getHibernateTemplate().find(hql_getPosts_B_1, new Object[] {
                discussion.getId(),
                false,
                null,
        });
        
        int count = ((Number) list.get(0)).intValue();
        setting.setRowSize(count);
        if (count==0) return new ArrayList();
        
        if (setting.getRowOfPage()==-1) setting.setRowOfPage(count);
        
        //get rows
        Query query = getSession().createQuery(hql_getPosts_B_2);
        query.setLong(0, discussion.getId());
        query.setBoolean(1, false);
        query.setEntity(2, null);
        query.setFirstResult(setting.getFirstRow());
        query.setMaxResults(setting.getRowOfPage());
        
        return query.list();
    }//getPosts()
    
    
    private static final String hql_getReplies_A = "from DiscussionPost p where p.parent=? and p.deleted=? order by p.id";
    
    
    public Collection getReplies(DiscussionPost post) throws Exception {
        return getHibernateTemplate().find(hql_getReplies_A, new Object[] {
                post,
                false,
        });
    }//getReplies()
    
    
    private static final String hql_getReplies_B_1 = "select count(p.id) from DiscussionPost p where p.parent=? and p.deleted=? order by p.id";
    
    private static final String hql_getReplies_B_2 = "from DiscussionPost p where p.parent=? and p.deleted=? order by p.id";
    
    
    public Collection getReplies(DiscussionPost post, PageSetting setting) throws Exception {
        //get total rows number
        List list = getHibernateTemplate().find(hql_getReplies_B_1, new Object[] {
                post,
                false,
        });
        
        int count = ((Number) list.get(0)).intValue();
        setting.setRowSize(count);
        if (count==0) return new ArrayList();
        
        if (setting.getRowOfPage()==-1) setting.setRowOfPage(count);
        
        //get rows
        Query query = getSession().createQuery(hql_getReplies_B_2);
        query.setEntity(0, post);
        query.setBoolean(1, false);
        query.setFirstResult(setting.getFirstRow());
        query.setMaxResults(setting.getRowOfPage());
        
        return query.list();
    }//getReplies()
    
    
    public Discussion createDiscussion(String targetType, Long targetId) throws Exception {
        Discussion discussion = new Discussion();
        
        discussion.setDeleted(false);
        discussion.setTargetType(targetType);
        discussion.setTargetId(targetId);
        
        getHibernateTemplate().save(discussion);
        
        return discussion;
    }//createDiscussion()
    
    
    public DiscussionPost createPost(Discussion discussion, String content) throws Exception {
        return createPost(discussion, null, content);
    }//createPost()
    
    
    public DiscussionPost createPost(Discussion discussion, DiscussionPost quote, String content) throws Exception {
        DiscussionPost post = new DiscussionPost();
        
        post.setContent(content);
        post.setDeleted(false);
        post.setDiscussionId(discussion.getId());
        post.setOwner(getUserById(WebUtils.currentUserId()));
        post.setParent(null);
        post.setQuote(quote);
        post.setTime(new Date());
        
        getHibernateTemplate().save(post);
        
        return post;
    }//createPost()
    
    
    public DiscussionPost createReply(DiscussionPost post, String content) throws Exception {
        return createReply(post, null, content);
    }//addReply()
    

    public DiscussionPost createReply(DiscussionPost post, DiscussionPost quote, String content) throws Exception {
        DiscussionPost reply = new DiscussionPost();
        
        reply.setContent(content);
        reply.setDeleted(false);
        reply.setTime(new Date());
        reply.setParent(post);
        reply.setQuote(quote);
        reply.setOwner(getUserById(WebUtils.currentUserId()));
        
        getHibernateTemplate().save(reply);
        
        return reply;
    }//createReply()


    public DiscussionPost getPostById(Long id) throws Exception {
        return (DiscussionPost) getHibernateTemplate().load(DiscussionPost.class, id);
    }//getPostById()
    
    
}//class DiscussionDAOImpl
