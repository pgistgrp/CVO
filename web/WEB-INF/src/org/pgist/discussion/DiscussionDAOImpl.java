package org.pgist.discussion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.pgist.cvo.Concern;
import org.pgist.system.BaseDAOImpl;
import org.pgist.tagging.Tag;
import org.pgist.tagging.TagInfo;
import org.pgist.util.DBMetaData;
import org.pgist.util.PageSetting;
import org.pgist.util.WebUtils;


/**
 * 
 * @author kenny
 *
 */
public class DiscussionDAOImpl extends BaseDAOImpl implements DiscussionDAO {
    
    
    private static final String hql_setPostTags = "from Tag where lower(name)=?";
    
    
    private void setPostTags(GenericPost post, String[] tags) throws Exception {
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
    

    private static final String hql_getPosts_A = "from DiscussionPost p where p.discussion.id=? and p.deleted=? order by p.replyTime";
    
    
    public Collection getPosts(Discussion discussion, boolean order) throws Exception {
        return getHibernateTemplate().find(hql_getPosts_A + (order?"":" desc"), new Object[] {
                discussion.getId(),
                false,
        });
    }//getPosts()
    
    
    private static final String hql_getPosts_B_1 = "select count(p.id) from DiscussionPost p where p.discussion.id=? and p.deleted=?";
    
    private static final String hql_getPosts_B_2 = "from DiscussionPost p where p.discussion.id=? and p.deleted=? order by p.replyTime";
    
    
    public Collection getPosts(Discussion discussion, PageSetting setting, boolean order) throws Exception {
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
        Query query = getSession().createQuery(hql_getPosts_B_2 + (order?"":" desc"));
        query.setLong(0, discussion.getId());
        query.setBoolean(1, false);
        query.setFirstResult(setting.getFirstRow());
        query.setMaxResults(setting.getRowOfPage());
        
        return query.list();
    }//getPosts()
    
    
    private static final String hql_getLastReply = "from DiscussionReply p where p.parent.id=? and p.deleted=? order by p.id desc";
    
    
    private DiscussionPost getLastReply(DiscussionPost post) {
        List list = getHibernateTemplate().find(hql_getLastReply, new Object[] {
                post.getId(),
                false,
        });
        
        if (list.size()==0) return null;
        
        return (DiscussionPost) list.get(0);
    }//getLastReply()


    private static final String hql_getReplies_A = "from DiscussionReply p where p.parent.id=? and p.deleted=? order by p.id";
    
    
    public Collection getReplies(DiscussionPost post) throws Exception {
        return getHibernateTemplate().find(hql_getReplies_A, new Object[] {
                post.getId(),
                false,
        });
    }//getReplies()
    
    
    private static final String hql_getReplies_B_1 = "select count(p.id) from DiscussionReply p where p.parent.id=? and p.deleted=?";
    
    private static final String hql_getReplies_B_2 = "from DiscussionReply p where p.parent.id=? and p.deleted=? order by p.id";
    
    
    public Collection getReplies(DiscussionPost post, PageSetting setting) throws Exception {
        //get total rows number
        List list = getHibernateTemplate().find(hql_getReplies_B_1, new Object[] {
                post.getId(),
                false,
        });
        
        int count = ((Number) list.get(0)).intValue();
        if (setting.getRowOfPage()==-1) setting.setRowOfPage(count);
        setting.setRowSize(count);
        if (count==0) return new ArrayList();
        
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
    
    
    public DiscussionPost createPost(Discussion discussion, String title, String content, String[] tags) throws Exception {
        DiscussionPost post = new DiscussionPost();
        
        Date date = new Date();
        
        post.setTitle(title);
        post.setContent(content);
        post.setDeleted(false);
        post.setDiscussion(discussion);
        post.setOwner(getUserById(WebUtils.currentUserId()));
        post.setCreateTime(date);
        post.setReplyTime(date);
        
        setPostTags(post, tags);
        
        save(post);
        
        return post;
    }//createPost()
    
    
    private static final String hql_createReply_1 = "update DiscussionPost set replies=replies+1 where id=?";
    
    
    public DiscussionReply createReply(DiscussionPost post, String title, String content, String[] tags) throws Exception {
        DiscussionReply reply = new DiscussionReply();
        
        reply.setTitle(title);
        reply.setContent(content);
        reply.setDeleted(false);
        
        reply.setCreateTime(new Date());
        reply.setParent(post);
        reply.setOwner(getUserById(WebUtils.currentUserId()));
        
        setPostTags(reply, tags);
        
        /*
         * count the replies
         */
        getSession().createQuery(hql_createReply_1).setLong(0, post.getId()).executeUpdate();
        
        save(reply);
        
        post.setLastReply(reply);
        post.setReplyTime(reply.getCreateTime());
        
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
        save(discussion);
        
        /*
         * TODO: Do we still allow participants to delete their posts?
         */
    }//deleteDiscussion()


    public void deletePost(DiscussionPost post) throws Exception {
        post.setDeleted(true);
        save(post);
        
        /*
         * TODO: Do we still allow participants to delete their posts?
         */
    }//deletePost()


    private static final String hql_getInfoStructures = "from InfoStructure";
    
    
    public Collection getInfoStructures() throws Exception {
        return getHibernateTemplate().find(hql_getInfoStructures);
    }//getInfoStructures()
    
    
    private static final String hql_increaseDiscussions_1 = "update InfoStructure set numDiscussion=numDiscussion+1 where id=?";
    
    
    public void increaseDiscussions(InfoStructure structure) throws Exception {
        getSession().createQuery(hql_increaseDiscussions_1).setLong(0, structure.getId()).executeUpdate();
    }//increaseDiscussions()


    private static final String hql_increaseDiscussions_2 = "update InfoObject set numDiscussion=numDiscussion+1 where id=?";
    
    
    public void increaseDiscussions(InfoObject object) throws Exception {
        getSession().createQuery(hql_increaseDiscussions_2).setLong(0, object.getId()).executeUpdate();
    }//increaseDiscussions()


    private static final String hql_increaseViews = "update DiscussionPost set views=views+1 where id=?";
    
    
    public void increaseViews(DiscussionPost post) throws Exception {
        getSession().createQuery(hql_increaseViews).setLong(0, post.getId()).executeUpdate();
    }//increaseViews()


    private static final String hql_increaseVoting_11 = "update InfoStructure i set i.numVote=i.numVote+1 where i.id=?";
    
    private static final String hql_increaseVoting_12 = "update InfoStructure i set i.numAgree=i.numAgree+1 where i.id=?";
    
    
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

    
    private static final String hql_increaseVoting_31 = "update DiscussionPost p set p.numVote=p.numVote+1 where p.id=?";
    
    private static final String hql_increaseVoting_32 = "update DiscussionPost p set p.numAgree=p.numAgree+1 where p.id=?";
    
    
    public void increaseVoting(DiscussionPost post, boolean agree) throws Exception {
        getSession().createQuery(hql_increaseVoting_31).setLong(0, post.getId()).executeUpdate();
        if (agree) {
            getSession().createQuery(hql_increaseVoting_32).setLong(0, post.getId()).executeUpdate();
        }
    }//increaseVoting()


    private static final String hql_increaseVoting_41 = "update DiscussionReply r set r.numVote=r.numVote+1 where r.id=?";
    
    private static final String hql_increaseVoting_42 = "update DiscussionReply r set r.numAgree=r.numAgree+1 where r.id=?";
    
    
    public void increaseVoting(DiscussionReply reply, boolean agree) throws Exception {
        getSession().createQuery(hql_increaseVoting_41).setLong(0, reply.getId()).executeUpdate();
        if (agree) {
            getSession().createQuery(hql_increaseVoting_42).setLong(0, reply.getId()).executeUpdate();
        }
    }//increaseVoting()


    public Concern getConcernById(Long Id) throws Exception {
    	
        return (Concern) getHibernateTemplate().load(Concern.class, Id);
    	
    }//getConcernById()
    
    
    private static final String sql_getConcerns_A_11 = "select count(distinct v.cid) from "+DBMetaData.VIEW_CONCERN_TAG_IN_STRUCTURE+" v where v.cctid=:cctid and v.isid=:isid";
    
    private static final String sql_getConcerns_A_12 = "select v.cid from "+DBMetaData.VIEW_CONCERN_TAG_IN_STRUCTURE+" v where v.cctid=:cctid and v.isid=:isid group by v.cid order by count(v.cid) desc";
    
    
    public Collection getConcerns(InfoStructure structure, PageSetting setting) throws Exception {
        List concerns = new ArrayList();
        
        String sql = sql_getConcerns_A_11.replace(":cctid", structure.getCctId().toString()).replace(":isid", structure.getId().toString());
        
        Connection connection = getSession().connection();
        Statement stmt = connection.createStatement();
        
        //get count
        
        ResultSet rs = stmt.executeQuery(sql);
        
        rs.next();
        
        int count = rs.getInt(1);
        
        if (setting.getRowOfPage()==-1) setting.setRowOfPage(count);
        
        setting.setRowSize(count);
        
        if (count==0) return concerns;
        
        //get records
        
        sql = sql_getConcerns_A_12.replace(":cctid", structure.getCctId().toString()).replace(":isid", structure.getId().toString());
        
        rs = stmt.executeQuery(sql+" offset "+setting.getFirstRow()+" limit "+setting.getRowOfPage());
        
        while (rs.next()) {
            Concern concern = (Concern) getHibernateTemplate().load(Concern.class, rs.getLong(1));
            concerns.add(concern);
        }//while
        
        return concerns;
    }//getConcerns()


    private static final String sql_getConcerns_A_20 = "SELECT cid as xid from "+DBMetaData.VIEW_CONCERN_TAG_IN_STRUCTURE+" where cctid=:cctid and isid=:isid";
    
    private static final String sql_getConcerns_A_21 = "select distinct v.cid as xid from "+DBMetaData.VIEW_CONCERN_TAG_IN_STRUCTURE+" v where v.cctid=:cctid and trid=";
    
    
    public Collection getConcerns(InfoStructure structure, String ids, PageSetting setting) throws Exception {
        List concerns = new ArrayList();
        
        String[] idArray = ids.split(",");
        
        StringBuilder sb = new StringBuilder(sql_getConcerns_A_20);
        
        for (int i=0; i<idArray.length; i++) {
            sb.append(" INTERSECT ").append(sql_getConcerns_A_21).append(idArray[i]);
        }
        String piece = sb.toString().replace(":cctid", structure.getCctId().toString()).replace(":isid", structure.getId().toString());
        
        //get count
        
        String sql = "select count(distinct x.xid) from ("+piece+") as x";
        
        Connection connection = getSession().connection();
        
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        
        rs.next();
        
        int count = rs.getInt(1);
        
        if (setting.getRowOfPage()==-1) setting.setRowOfPage(count);
        
        setting.setRowSize(count);
        
        if (count==0) return concerns;
        
        //get records
        
        stmt = connection.createStatement();
        
        sql = piece+" order by xid desc offset "+setting.getFirstRow()+" limit "+setting.getRowOfPage();
        
        rs = stmt.executeQuery(sql);
        
        while (rs.next()) {
            long id = rs.getLong(1);
            Concern concern = (Concern) getHibernateTemplate().load(Concern.class, id);
            concerns.add(concern);
        }//while
        
        return concerns;
    }//getConcerns()


    private static final String sql_getConcerns_B_11 = "select count(distinct v.cid) from "+DBMetaData.VIEW_CONCERN_TAG_IN_STRUCTURE+" v where v.cctid=:cctid and v.isid=:isid and v.ioid=:ioid";
    
    private static final String sql_getConcerns_B_12 = "select v.cid from "+DBMetaData.VIEW_CONCERN_TAG_IN_STRUCTURE+" v where v.cctid=:cctid and v.isid=:isid and v.ioid=:ioid group by v.cid order by count(v.cid) desc";
    
    
    public Collection getConcerns(InfoObject object, PageSetting setting) throws Exception {
        InfoStructure structure = object.getStructure();
        
        List concerns = new ArrayList();
        
        String sql = sql_getConcerns_B_11.replace(":cctid", structure.getCctId().toString()).replace(":isid", structure.getId().toString()).replace(":ioid", object.getId().toString());
        
        Connection connection = getSession().connection();
        Statement stmt = connection.createStatement();
        
        //get count
        
        ResultSet rs = stmt.executeQuery(sql);
        
        rs.next();
        
        int count = rs.getInt(1);
        
        if (setting.getRowOfPage()==-1) setting.setRowOfPage(count);
        
        setting.setRowSize(count);
        
        if (count==0) return concerns;
        
        //get records
        
        sql = sql_getConcerns_B_12.replace(":cctid", structure.getCctId().toString()).replace(":isid", structure.getId().toString()).replace(":ioid", object.getId().toString());
        
        rs = stmt.executeQuery(sql+" offset "+setting.getFirstRow()+" limit "+setting.getRowOfPage());
        
        while (rs.next()) {
            Concern concern = (Concern) getHibernateTemplate().load(Concern.class, rs.getLong(1));
            concerns.add(concern);
        }//while
        
        return concerns;
    }//getConcerns()


    private static final String sql_getConcerns_B_20 = "SELECT cid as xid from "+DBMetaData.VIEW_CONCERN_TAG_IN_STRUCTURE+" where cctid=:cctid and isid=:isid and ioid=:ioid";
    
    private static final String sql_getConcerns_B_21 = "select distinct v.cid as xid from "+DBMetaData.VIEW_CONCERN_TAG_IN_STRUCTURE+" v where v.cctid=:cctid and v.trid=";
    
    
    public Collection getConcerns(InfoObject object, String ids, PageSetting setting) throws Exception {
        InfoStructure structure = object.getStructure();
        
        List concerns = new ArrayList();
        
        String[] idArray = ids.split(",");
        
        StringBuilder sb = new StringBuilder(sql_getConcerns_B_20);
        
        for (int i=0; i<idArray.length; i++) {
            sb.append(" INTERSECT ");
            sb.append(sql_getConcerns_B_21).append(idArray[i]);
        }
        String piece = sb.toString().replace(":cctid", structure.getCctId().toString()).replace(":isid", structure.getId().toString()).replace(":ioid", object.getId().toString());
        
        //get count
        
        String sql = "select count(distinct x.xid) from ("+piece+") as x";
        
        Connection connection = getSession().connection();
        
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        
        rs.next();
        
        int count = rs.getInt(1);
        
        if (setting.getRowOfPage()==-1) setting.setRowOfPage(count);
        
        setting.setRowSize(count);
        
        if (count==0) return concerns;
        
        //get records
        
        stmt = connection.createStatement();
        
        sql = piece+" order by xid desc offset "+setting.getFirstRow()+" limit "+setting.getRowOfPage();
        
        rs = stmt.executeQuery(sql);
        
        while (rs.next()) {
            long id = rs.getLong(1);
            Concern concern = (Concern) getHibernateTemplate().load(Concern.class, id);
            concerns.add(concern);
        }//while
        
        return concerns;
    }//getConcerns()


    private static final String sql_getConcernTagCount_1 = "select count(distinct trid) from "+DBMetaData.VIEW_CONCERN_TAG_IN_STRUCTURE+" where cctid=? and isid=?";
    
    
    public int getConcernTagCount(InfoStructure structure) throws Exception {
        Connection connection = getSession().connection();
        PreparedStatement pstmt = connection.prepareStatement(sql_getConcernTagCount_1);
        
        pstmt.setLong(1, structure.getCctId());
        pstmt.setLong(2, structure.getId());
        
        ResultSet rs = pstmt.executeQuery();
        
        rs.next();
        
        return rs.getInt(1);
    }//getConcernTagCount()


    private static final String sql_getConcernTagCount_2 = "select count(distinct trid) from "+DBMetaData.VIEW_CONCERN_TAG_IN_STRUCTURE+" where cctid=? and isid=? and ioid=?";
    
    
    public int getConcernTagCount(InfoObject object) throws Exception {
        Connection connection = getSession().connection();
        PreparedStatement pstmt = connection.prepareStatement(sql_getConcernTagCount_2);
        
        pstmt.setLong(1, object.getStructure().getCctId());
        pstmt.setLong(2, object.getStructure().getId());
        pstmt.setLong(3, object.getId());
        
        ResultSet rs = pstmt.executeQuery();
        
        rs.next();
        
        return rs.getInt(1);
    }//getConcernTagCount()


    private static final String sql_getDiscussions_A_1 = "SELECT count(distinct pid) from "+DBMetaData.VIEW_POST_REPLY_TAG_IN_DISCUSSION+" v where v.isid=:isid and v.pid<>:pid and v.tid in (select tid from view_post_reply_tags where pid=:pid);";
    
    private static final String sql_getDiscussions_A_2 = "SELECT pid, ioid from "+DBMetaData.VIEW_POST_REPLY_TAG_IN_DISCUSSION+" v where v.isid=:isid and v.pid<>:pid and v.tid in (select tid from view_post_reply_tags where pid=:pid) group by pid, ioid order by count(pid) desc;";
    
    
    public Collection getContextPosts(Long isid, Long pid, PageSetting setting) throws Exception {
        List posts = new ArrayList();
        
        Connection connection = getSession().connection();
        Statement stmt = connection.createStatement();
        
        String sql = sql_getDiscussions_A_1.replace(":isid", isid.toString()).replace(":pid", pid.toString());
        
        //get count
        
        ResultSet rs = stmt.executeQuery(sql);
        
        rs.next();
        
        int count = rs.getInt(1);
        
        setting.setRowSize(count);
        
        if (count==0) return posts;
        
        //get records
        
        sql = sql_getDiscussions_A_2.replace(":isid", isid.toString()).replace(":pid", pid.toString());
        
        rs = stmt.executeQuery(sql);
        
        while (rs.next()) {
            Long postId = rs.getLong(1);
            Long ioid = rs.getLong(2);
            
            DiscussionPost post = (DiscussionPost) getHibernateTemplate().load(DiscussionPost.class, postId);
            
            if (ioid>0) {
                InfoObject obj = (InfoObject) getHibernateTemplate().load(InfoObject.class, ioid);
                post.setValue(obj);
            }
            
            posts.add(post);
        }//while
        
        return posts;
    }//getContextPosts()


    private static final String sql_getContextPosts_B = "select distinct v.pid as xid, v.ioid as yid from "+DBMetaData.VIEW_POST_REPLY_TAG_IN_DISCUSSION+" v where v.isid=:isid and v.pid<>:pid and v.tid=";
    
    
    public Collection getContextPosts(Long isid, Long pid, String ids, PageSetting setting) throws Exception {
        List posts = new ArrayList();
        
        String[] idArray = ids.split(",");
        
        StringBuilder sb = new StringBuilder();
        
        for (int i=0; i<idArray.length; i++) {
            if (i>0) sb.append(" INTERSECT ");
            sb.append(sql_getContextPosts_B).append(idArray[i]);
        }
        String piece = sb.toString().replace(":isid", isid.toString()).replace(":pid", pid.toString());
        
        //get count
        
        String sql = "select count(distinct x.xid) from ("+piece+") as x";
        
        Connection connection = getSession().connection();
        
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        
        rs.next();
        
        int count = rs.getInt(1);
        
        if (setting.getRowOfPage()==-1) setting.setRowOfPage(count);
        
        setting.setRowSize(count);
        
        if (count==0) return posts;
        
        //get records
        
        stmt = connection.createStatement();
        
        sql = piece+" order by xid desc offset "+setting.getFirstRow()+" limit "+setting.getRowOfPage();
        
        rs = stmt.executeQuery(sql);
        
        while (rs.next()) {
            Long postId = rs.getLong(1);
            Long ioid = rs.getLong(2);
            
            DiscussionPost post = (DiscussionPost) getHibernateTemplate().load(DiscussionPost.class, postId);
            
            if (ioid>0) {
                InfoObject obj = (InfoObject) getHibernateTemplate().load(InfoObject.class, ioid);
                post.setValue(obj);
            }
            
            posts.add(post);
        }//while
        
        return posts;
    }//getContextPosts()


    private static final String hql_getContextPosts_A_1 = "select count(p.id) from DiscussionPost p where p.discussion.targetId in (select o.id from InfoObject o where o.structure.id=?)";
    
    private static final String hql_getContextPosts_A_2 = "from DiscussionPost p where p.discussion.targetId in (select o.id from InfoObject o where o.structure.id=?) order by p.id desc";
    
    
    public Collection getContextPosts(Long isid, PageSetting setting) throws Exception {
        List list = new ArrayList();
        
        Query query = null;
        
        query = getSession().createQuery(hql_getContextPosts_A_1);
        query.setLong(0, isid);
        query.setMaxResults(1);
        
        int count = ((Number) query.uniqueResult()).intValue();
        
        if (setting.getRowOfPage()==-1) setting.setRowOfPage(count);
        
        setting.setRowSize(count);
        
        if (count==0) return list;
        
        query = getSession().createQuery(hql_getContextPosts_A_2);
        query.setLong(0, isid);
        
        query.setFirstResult(setting.getFirstRow());
        query.setMaxResults(setting.getRowOfPage());
        
        list = query.list();
        
        for (DiscussionPost post : (List<DiscussionPost>) list) {
            if (post.getDiscussion().getTargetType().equals(InfoObject.class.getName())) {
                InfoObject obj = (InfoObject) getHibernateTemplate().load(InfoObject.class, post.getDiscussion().getTargetId());
                post.setValue(obj);
            }
        }//for list
        
        return list;
    }//getContextPosts()


    private static final String hql_getContextPosts_B_1 = "select count(p.id) from DiscussionPost p where p.discussion.targetId in (select o.id from InfoObject o where o.structure.id=?) and p.tags.id in (##)";
    
    private static final String hql_getContextPosts_B_2 = "from DiscussionPost p where p.discussion.targetId in (select o.id from InfoObject o where o.structure.id=?) and p.tags.id in (##) order by p.id desc";
    
    
    public Collection getContextPosts(Long isid, String ids, PageSetting setting) throws Exception {
        List list = new ArrayList();
        
        Query query = getSession().createQuery(hql_getContextPosts_B_1.replace("##", ids));
        query.setLong(0, isid);
        query.setMaxResults(1);
        
        int count = ((Number) query.uniqueResult()).intValue();
        
        if (setting.getRowOfPage()==-1) setting.setRowOfPage(count);
        
        setting.setRowSize(count);
        
        if (count==0) return list;
        
        query = getSession().createQuery(hql_getContextPosts_B_2.replace("##", ids));
        query.setLong(0, isid);
        
        query.setFirstResult(setting.getFirstRow());
        query.setMaxResults(setting.getRowOfPage());
        
        list = query.list();
        
        for (DiscussionPost post : (List<DiscussionPost>) list) {
            if (post.getDiscussion().getTargetType().equals(InfoObject.class.getName())) {
                InfoObject obj = (InfoObject) getHibernateTemplate().load(InfoObject.class, post.getDiscussion().getTargetId());
                post.setValue(obj);
            }
        }//for list
        
        return list;
    }//getContextPosts()


    private static final String sql_getPostTagCount = "select count(distinct tid) from "+DBMetaData.VIEW_POST_REPLY_TAG_IN_DISCUSSION+" where isid=? and pid=?";
    
    
    public int getPostTagCount(Long isid, Long postId) throws Exception {
        Connection connection = getSession().connection();
        PreparedStatement pstmt = connection.prepareStatement(sql_getPostTagCount);
        
        pstmt.setLong(1, isid);
        pstmt.setLong(2, postId);
        
        ResultSet rs = pstmt.executeQuery();
        
        rs.next();
        
        int num = rs.getInt(1);
        
        return num;
    }//getPostTagCount()


    private static final String sql_searchTags_1 = "select count(distinct tid) from "+DBMetaData.VIEW_DPOST_TAG_IN_TARGET+" where target in (##) and tname like ?";
    
    private static final String sql_searchTags_2 = "select distinct tid, tname from "+DBMetaData.VIEW_DPOST_TAG_IN_TARGET+" where target in (##) and tname like ? order by tname";
    
    
    public Collection searchTags(InfoStructure structure, String tag, PageSetting setting) throws Exception {
        List list = new ArrayList();
        
        StringBuilder sb = new StringBuilder(structure.getId().toString());
        for (InfoObject obj : (Set<InfoObject>) structure.getInfoObjects()) {
            sb.append(',').append(obj.getId().toString());
        }
        
        Connection connection = getSession().connection();
        PreparedStatement pstmt = connection.prepareStatement(sql_searchTags_1.replace("##", sb.toString()));
        
        pstmt.setString(1, '%'+tag+'%');
        
        ResultSet rs = pstmt.executeQuery();
        
        rs.next();
        int count = rs.getInt(1);
        if (setting.getRowOfPage()==-1) setting.setRowOfPage(count);
        setting.setRowSize(count);
        
        if (count==0) return list;
        
        pstmt = connection.prepareStatement(sql_searchTags_2.replace("##", sb.toString())+" OFFSET "+setting.getFirstRow()+" LIMIT "+setting.getRowOfPage());
        
        pstmt.setString(1, '%'+tag+'%');
        
        rs = pstmt.executeQuery();
        
        while (rs.next()) {
            Tag one = (Tag) getHibernateTemplate().load(Tag.class, rs.getLong(1));
            list.add(one);
        }//while
        
        return list;
    }//searchTags()
    
    public Tag findTagById(Long tagId) throws Exception {
        return (Tag) getHibernateTemplate().load(Tag.class, tagId);
    }//findTagById()
    
    private static final String hql_deleteVotings = "delete InfoVoting iv where iv.object.id=?";
    
    
    public void deleteVotings(Long infoObjectId) throws Exception {
        Query query = getSession().createQuery(hql_deleteVotings);
        query.setLong(0, infoObjectId);
        query.executeUpdate();
    }//deleteVotings()


    private static final String sql_getTagCloud_1 = "select count(distinct tid) from "+DBMetaData.VIEW_DPOST_TAG_IN_TARGET+" where target in (##)";
    
    private static final String sql_getTagCloud_2 = "select tid, count(tid) from "+DBMetaData.VIEW_DPOST_TAG_IN_TARGET+" where target in (##) group by tid order by count(tid)";
    
    
    public Collection getTagCloud(InfoStructure structure, PageSetting setting) throws Exception {
        List list = new ArrayList();
        
        Connection connection = getSession().connection();
        Statement stmt = connection.createStatement();
        
        StringBuilder sb = new StringBuilder(structure.getId().toString());
        
        for (InfoObject obj : (Set<InfoObject>) structure.getInfoObjects()) {
            sb.append(",").append(obj.getId().toString());
        }//for
        
        String ids = sb.toString();
        
        ResultSet rs = stmt.executeQuery(sql_getTagCloud_1.replace("##", ids));
        
        rs.next();
        
        int count = rs.getInt(1);
        if (setting.getRowOfPage()==-1) setting.setRowOfPage(count);
        setting.setRowSize(count);
        
        if (count==0) return list;
        
        rs = stmt.executeQuery(sql_getTagCloud_2.replace("##", ids)+" offset "+setting.getFirstRow()+" limit "+setting.getRowOfPage());
        
        while (rs.next()) {
            Long id = rs.getLong(1);
            int times = rs.getInt(2);
            
            Tag tag = (Tag) getHibernateTemplate().load(Tag.class, id);
            
            TagInfo info = new TagInfo();
            info.setId(id);
            info.setName(tag.getName());
            info.setTimes(times);
            
            list.add(info);
        }//while
        
        return list;
    }//getTagCloud()


    public DiscussionReply getReplyById(Long id) throws Exception {
        return (DiscussionReply) getHibernateTemplate().load(DiscussionReply.class, id);
    }//getReplyById()


}//class DiscussionDAOImpl
