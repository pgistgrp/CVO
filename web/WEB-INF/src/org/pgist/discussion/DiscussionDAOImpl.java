package org.pgist.discussion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.pgist.tag.Tag;
import org.pgist.system.BaseDAOImpl;
import org.pgist.users.User;
import org.pgist.util.PageSetting;
import org.pgist.util.WebUtils;


/**
 * 
 * @author kenny
 *
 */
public class DiscussionDAOImpl extends BaseDAOImpl implements DiscussionDAO {
    
    
    private static final String hql_setPostTags = "from Tag where lower(name)=?";
    
    
    private void setPostTags(DiscussionPost post, String[] tags) throws Exception {
        List list = null;
        Tag tag = null;
        Query query = getSession().createQuery(hql_setPostTags);
        
        if (tags!=null) {
            for (String tagName : tags) {
                if (tagName==null) continue;
                tagName = tagName.trim();
                if (tagName.length()==0) continue;
                
                query.setString(0, tagName.toLowerCase());
                
                list = query.list();
                
                if (list.size()>0) {
                    tag = (Tag) list.get(0);
                    tag.setCount(tag.getCount()+1);
                } else {
                    tag = new Tag();
                    tag.setName(tagName);
                    tag.setCount(1);
                    tag.setStatus(Tag.STATUS_OFFICIAL);
                    
                    save(tag);
                }
                
                post.getTags().add(tag);
            }
        }
    }//setPostTags()
    
    
    public Discussion getDiscussion(Class klass, Long targetId) throws Exception {
        return getDiscussion(klass.getName(), targetId);
    }//getDiscussion()
    
    
    public DiscussionPost getPostById(Long id) throws Exception {
        return (DiscussionPost) getHibernateTemplate().load(DiscussionPost.class, id);
    }//getPostById()


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
    

    private static final String hql_getPosts_A = "from DiscussionPost p where p.discussionId=? and p.deleted=? and p.parent is null order by p.lastActivated desc";
    
    
    public Collection getPosts(Discussion discussion) throws Exception {
        return getHibernateTemplate().find(hql_getPosts_A, new Object[] {
                discussion.getId(),
                false,
        });
    }//getPosts()
    
    
    private static final String hql_getPosts_B_1 = "select count(p.id) from DiscussionPost p where p.discussionId=? and p.deleted=? and p.parent is null";
    
    private static final String hql_getPosts_B_2 = "from DiscussionPost p where p.discussionId=? and p.deleted=? and p.parent is null order by p.lastActivated desc";
    
    
    public Collection getPosts(Discussion discussion, PageSetting setting) throws Exception {
        //get total rows number
        List list = getHibernateTemplate().find(hql_getPosts_B_1, new Object[] {
                discussion.getId(),
                false,
        });
        
        int count = ((Number) list.get(0)).intValue();
        setting.setRowSize(count);
        if (count==0) return new ArrayList();
        
        if (setting.getRowOfPage()==-1) setting.setRowOfPage(count);
        
        //get rows
        Query query = getSession().createQuery(hql_getPosts_B_2);
        query.setLong(0, discussion.getId());
        query.setBoolean(1, false);
        query.setFirstResult(setting.getFirstRow());
        query.setMaxResults(setting.getRowOfPage());
        
        list = query.list();
        
        //get the last reply
        for (int i=0; i<list.size(); i++) {
            DiscussionPost post = (DiscussionPost) list.get(i);
            post.setLastReply(getLastReply(post));
        }//for i
        
        return list;
    }//getPosts()
    
    
    private static final String hql_getLastReply = "from DiscussionPost p where p.parent.id=? order by p.id desc";
    
    
    private DiscussionPost getLastReply(DiscussionPost post) {
        List list = getHibernateTemplate().find(hql_getLastReply, post.getId());
        
        if (list.size()==0) return null;
        
        return (DiscussionPost) list.get(0);
    }//getLastReply()


    private static final String hql_getReplies_A = "from DiscussionPost p where p.parent.id=? and p.deleted=? order by p.id";
    
    
    public Collection getReplies(DiscussionPost post) throws Exception {
        return getHibernateTemplate().find(hql_getReplies_A, new Object[] {
                post.getId(),
                false,
        });
    }//getReplies()
    
    
    private static final String hql_getReplies_B_1 = "select count(p.id) from DiscussionPost p where p.parent.id=? and p.deleted=?";
    
    private static final String hql_getReplies_B_2 = "from DiscussionPost p where p.parent.id=? and p.deleted=? order by p.id";
    
    
    public Collection getReplies(DiscussionPost post, PageSetting setting) throws Exception {
        //get total rows number
        List list = getHibernateTemplate().find(hql_getReplies_B_1, new Object[] {
                post.getId(),
                false,
        });
        
        int count = ((Number) list.get(0)).intValue();
        setting.setRowSize(count);
        if (count==0) return new ArrayList();
        
        if (setting.getRowOfPage()==-1) setting.setRowOfPage(count);
        
        //get rows
        Query query = getSession().createQuery(hql_getReplies_B_2);
        query.setLong(0, post.getId());
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
    
    
    public DiscussionPost createPost(Discussion discussion, DiscussionPost quote, String title, String content, String[] tags) throws Exception {
        DiscussionPost post = new DiscussionPost();
        
        post.setTitle(title);
        post.setContent(content);
        post.setDeleted(false);
        post.setDiscussionId(discussion.getId());
        post.setOwner(getUserById(WebUtils.currentUserId()));
        post.setParent(null);
        if (quote!=null) post.setQuote(quote);
        
        Date date = new Date();
        
        post.setCreateTime(date);
        post.setLastActivated(date);
        
        setPostTags(post, tags);
        
        getHibernateTemplate().save(post);
        
        return post;
    }//createPost()

    
    private static final String hql_createReply_1 = "update DiscussionPost set replies=replies+1, lastActivated=? where id=?";
    

    public DiscussionPost createReply(DiscussionPost post, DiscussionPost quote, String title, String content, String[] tags) throws Exception {
        DiscussionPost reply = new DiscussionPost();
        
        reply.setDiscussionId(post.getDiscussionId());
        reply.setTitle(title);
        reply.setContent(content);
        reply.setDeleted(false);
        
        reply.setCreateTime(new Date());
        reply.setParent(post);
        if (quote!=null) reply.setQuote(quote);
        reply.setOwner(getUserById(WebUtils.currentUserId()));
        
        setPostTags(reply, tags);
        
        getHibernateTemplate().save(reply);
        
        getSession().createQuery(hql_createReply_1)
        .setTimestamp(0, new Date())
        .setLong(1, post.getId()).executeUpdate();
        
        return reply;
    }//createReply()


    public void editPost(DiscussionPost post, String title, String content, String[] tags) throws Exception {
        post.setTitle(title);
        post.setContent(content);
        post.getTags().clear();
        
        setPostTags(post, tags);
        
        save(post);
    }//editPost()


    public void deleteDiscussion(Discussion discussion) throws Exception {
        discussion.setDeleted(true);
        getHibernateTemplate().saveOrUpdate(discussion);
    }//deleteDiscussion()


    private static final String hql_deletePost = "update DiscussionPost set replies = replies-1 where id=?";
    
    
    public void deletePost(DiscussionPost post) throws Exception {
        post.setDeleted(true);
        getHibernateTemplate().saveOrUpdate(post);
        
        DiscussionPost parent = post.getParent();
        if (parent!=null) {
            getSession().createQuery(hql_deletePost).setLong(0, parent.getId()).executeUpdate();
        }
    }//deletePost()


    private static final String hql_getInfoStructures = "from InfoStructure";
    
    
    public Collection getInfoStructures() throws Exception {
        return getHibernateTemplate().find(hql_getInfoStructures);
    }//getInfoStructures()
    
    
    private static final String hql_increaseViews = "update DiscussionPost set views=views+1 where id=?";
    
    
    public void increaseViews(DiscussionPost post) throws Exception {
        getSession().createQuery(hql_increaseViews).setLong(0, post.getId()).executeUpdate();
    }//increaseViews()


    private static final String hql_getVoting_1 = "from InfoVoting i where i.structure.id=? and i.object.id is null and i.owner.id=?";
    
    
    public InfoVoting getVoting(InfoStructure structure, User user) throws Exception {
        List list = getHibernateTemplate().find(hql_getVoting_1, new Object[] {
                structure.getId(),
                user.getId(),
        });
        
        if (list.size()==0) return null;
        return (InfoVoting) list.get(0);
    }//getVoting()


    private static final String hql_getVoting_2 = "from InfoVoting i where i.structure.id is null and i.object.id=? and i.owner.id=?";
    
    
    public InfoVoting getVoting(InfoObject infoObject, User user) throws Exception {
        List list = getHibernateTemplate().find(hql_getVoting_2, new Object[] {
                infoObject.getId(),
                user.getId(),
        });
        
        if (list.size()==0) return null;
        return (InfoVoting) list.get(0);
    }//getVoting()


    private static final String hql_increaseVoting_11 = "update InfoStructure i set i.numVote=i.numVote+1, i. where i.id=?";
    
    private static final String hql_increaseVoting_12 = "update InfoStructure i set i.numAgree=i.numAgree+1, i. where i.id=?";
    
    
    public void increaseVoting(InfoStructure structure, boolean agree) throws Exception {
        getSession().createQuery(hql_increaseVoting_11).setLong(0, structure.getId()).executeUpdate();
        if (agree) {
            getSession().createQuery(hql_increaseVoting_12).setLong(0, structure.getId()).executeUpdate();
        }
    }//increaseVoting()


    private static final String hql_increaseVoting_21 = "update InfoObject i set i.numVote=i.numVote+1 where i.id=?";
    
    private static final String hql_increaseVoting_22 = "update InfoObject i set i.numAgree=i.numAgree+1 where i.id=?";
    
    
    public void increaseVoting(InfoObject object, boolean agree) throws Exception {
        getSession().createQuery(hql_increaseVoting_21).setLong(0, object.getId()).executeUpdate();
        if (agree) {
            getSession().createQuery(hql_increaseVoting_22).setLong(0, object.getId()).executeUpdate();
        }
    }//increaseVoting()


}//class DiscussionDAOImpl
