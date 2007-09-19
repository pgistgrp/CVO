package org.pgist.discussion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.pgist.cvo.Concern;
import org.pgist.system.BaseDAOImpl;
import org.pgist.system.YesNoVoting;
import org.pgist.tagging.Tag;
import org.pgist.tagging.TagInfo;
import org.pgist.users.User;
import org.pgist.util.DBMetaData;
import org.pgist.util.PageSetting;
import org.pgist.util.WebUtils;


/**
 * 
 * @author kenny
 *
 */
public class DiscussionDAOImpl extends BaseDAOImpl implements DiscussionDAO {
    
    
    /**
     * Sorting index for discussion posts
     * <ul>
     *   <li>0 - Default (newest created/replied)</li>
     *   <li>1 - Newest to Oldest</li>
     *   <li>2 - Oldest to Newest</li>
     *   <li>3 - Most Agreement</li>
     *   <li>4 - Least Agreement</li>
     *   <li>5 - Most Comments</li>
     *   <li>6 - Most Views</li>
     *   <li>7 - Most Votes</li>
     * </ul>
     */
    private static final String[][] postSorting = {
        {
            "replyTime desc",
            "createTime desc",
            "createTime asc",
            "numAgree desc",
            "numAgree asc",
            "replies desc",
            "views desc",
            "numVote desc",
        },
        {
            "rtime desc",
            "ctime desc",
            "ctime asc",
            "nagree desc",
            "nagree asc",
            "nreply desc",
            "nview desc",
            "nvote desc",
        }
    };
    
    
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
    
    
    public DiscussionPost getPostById(Long id) throws Exception {
        return (DiscussionPost) getHibernateTemplate().load(DiscussionPost.class, id);
    }//getPostById()


    private static final String hql_getPosts_A = "from DiscussionPost p where p.discussion.id=? and p.deleted=? order by p.#sorting";
    
    
    public Collection getPosts(Discussion discussion, int sorting) throws Exception {
        return getHibernateTemplate().find(hql_getPosts_A.replace("#sorting", postSorting[0][sorting]), new Object[] {
                discussion.getId(),
                false,
        });
    }//getPosts()
    
    
    private static final String hql_getPosts_B_1 = "select count(p.id) from DiscussionPost p where p.discussion.id=? and p.deleted=?";
    
    private static final String hql_getPosts_B_2 = "from DiscussionPost p where p.discussion.id=? and p.deleted=? order by p.#sorting";
    
    
    public Collection getPosts(Discussion discussion, PageSetting setting, int sorting) throws Exception {
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
        Query query = getSession().createQuery(hql_getPosts_B_2.replace("#sorting", postSorting[0][sorting]));
        query.setLong(0, discussion.getId());
        query.setBoolean(1, false);
        query.setFirstResult(setting.getFirstRow());
        query.setMaxResults(setting.getRowOfPage());
        
        return query.list();
    }//getPosts()
    
    
    private static final String hql_getPosts_C_1 = "select count(distinct pid) from " + DBMetaData.VIEW_DPOST_TAG_IN_TARGET + " where did=? and lower(tname)=?";
    
    private static final String hql_getPosts_C_2 = "select p.id AS pid, p.views AS nview, p.replies AS nreply, p.replytime AS rtime, p.createtime AS ctime, p.numagree AS nagree, p.numvote AS nvote from pgist_discussion_post p where p.id IN (select distinct pid from " + DBMetaData.VIEW_DPOST_TAG_IN_TARGET + " where did=? and lower(tname)=?) order by #sorting OFFSET ? LIMIT ?";
    
    
    public Collection getPosts(Discussion discussion, PageSetting setting, String filter, int sorting) throws Exception {
        List list = new ArrayList();
        
        Connection conn = getSession().connection();
        PreparedStatement pstmt = conn.prepareStatement(hql_getPosts_C_1);
        
        //get total rows number
        pstmt.setLong(1, discussion.getId());
        pstmt.setString(2, filter);
        ResultSet rs = pstmt.executeQuery();
        
        if (!rs.next()) return list;
        
        int count = rs.getInt(1);
        if (setting.getRowOfPage()==-1) setting.setRowOfPage(count);
        setting.setRowSize(count);
        
        //get records
        pstmt = conn.prepareStatement(hql_getPosts_C_2.replace("#sorting", postSorting[1][sorting]));
        pstmt.setLong(1, discussion.getId());
        pstmt.setString(2, filter);
        pstmt.setInt(3, setting.getFirstRow());
        pstmt.setInt(4, setting.getRowOfPage());
        
        rs = pstmt.executeQuery();
        while (rs.next()) {
            list.add(getPostById(rs.getLong(1)));
        }
        
        return list;
    }//getPosts()


    private static final String hql_getReplies_A = "from DiscussionReply r where r.parent.id=? and r.deleted=? order by r.id";
    
    
    public Collection getReplies(DiscussionPost post) throws Exception {
        return getHibernateTemplate().find(hql_getReplies_A, new Object[] {
                post.getId(),
                false,
        });
    }//getReplies()
    
    
    private static final String hql_getReplies_B_1 = "select count(r.id) from DiscussionReply r where r.parent.id=? and r.deleted=?";
    
    private static final String hql_getReplies_B_2 = "from DiscussionReply r where r.parent.id=? and r.deleted=? order by r.id";
    
    
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
    
    
    //private static final String hql_getReplies_C_1 = "select count(r.id) from DiscussionReply r where r.parent.id=? and r.deleted=? and lower(r.tags.name)=?";
    private static final String hql_getReplies_C_1 = "select count(r.id) from DiscussionReply r inner join r.tags as tag where r.parent.id=? and r.deleted=? and lower(tag.name)=?";
    
    private static final String hql_getReplies_C_2 = "select r from DiscussionReply r inner join r.tags as tag where r.parent.id=? and r.deleted=? and lower(tag.name)=? order by r.id";
    
    
    public Collection getReplies(DiscussionPost post, PageSetting setting, String filter) throws Exception {
        Query query;
        
        //get count
        query = getSession().createQuery(hql_getReplies_C_1);
        query.setLong(0, post.getId());
        query.setBoolean(1, false);
        query.setString(2, filter.toLowerCase());
        
        int count = ((Number) query.uniqueResult()).intValue();
        
        if (setting.getRowOfPage()==-1) setting.setRowOfPage(count);
        
        setting.setRowSize(count);
        
        if (count==0) return new ArrayList();
        
        //get records
        query = getSession().createQuery(hql_getReplies_C_2);
        query.setLong(0, post.getId());
        query.setBoolean(1, false);
        query.setString(2, filter.toLowerCase());
        
        return query.list();
    }//getReplies()


    public DiscussionPost createPost(Discussion discussion, String title, String content, String[] tags, boolean emailNotify) throws Exception {
        DiscussionPost post = new DiscussionPost();
        
        Date date = new Date();
        
        post.setTitle(title);
        post.setContent(content);
        post.setDeleted(false);
        post.setDiscussion(discussion);
        post.setOwner(getUserById(WebUtils.currentUserId()));
        post.setCreateTime(date);
        post.setModifyTime(date);
        post.setReplyTime(date);
        post.setEmailNotify(emailNotify);
        
        setPostTags(post, tags);
        
        save(post);
        
        return post;
    }//createPost()
    
    
    public DiscussionReply createReply(DiscussionPost post, DiscussionReply parentReply, String title, String content, String[] tags, boolean emailNotify) throws Exception {
        DiscussionReply reply = new DiscussionReply();
        
        reply.setTitle(title);
        reply.setContent(content);
        reply.setDeleted(false);
        
        reply.setCreateTime(new Date());
        reply.setModifyTime(new Date());
        reply.setParent(post);
        reply.setParentReply(parentReply);
        reply.setOwner(getUserById(WebUtils.currentUserId()));
        reply.setEmailNotify(emailNotify);
        
        setPostTags(reply, tags);
        
        save(reply);
        
        return reply;
    }//createReply()


    public void editPost(DiscussionPost post, String title, String content, String[] tags) throws Exception {
        post.setModifyTime(new Date());
        post.setTitle(title);
        post.setContent(content);
        post.getTags().clear();
        
        setPostTags(post, tags);
        
        save(post);
    }//editPost()


    public void deleteDiscussion(Discussion discussion) throws Exception {
        discussion.setDeleted(true);
        save(discussion);
    }//deleteDiscussion()


    public void deletePost(DiscussionPost post) throws Exception {
        post.setModifyTime(new Date());
        post.setDeleted(true);
        save(post);
    }//deletePost()


    public void deleteReply(DiscussionReply reply) throws Exception {
        reply.setModifyTime(new Date());
        reply.setDeleted(true);
        save(reply);
    }//deleteReply()


    private static final String hql_getInfoStructures = "from InfoStructure";
    
    
    public Collection getInfoStructures() throws Exception {
        return getHibernateTemplate().find(hql_getInfoStructures);
    }//getInfoStructures()
    
    
    private static final String hql_increaseDiscussions = "update Discussion set numPosts=numPosts+1 where id=?";
    
    
    public void increaseDiscussions(Discussion discussion) throws Exception {
        Query query = getSession().createQuery(hql_increaseDiscussions);
        query.setLong(0, discussion.getId());
        query.executeUpdate();
    }//increaseDiscussions()


    private static final String hql_decreaseDiscussions = "update Discussion set numPosts=numPosts-1 where id=?";
    
    
    public void decreaseDiscussions(Discussion discussion) throws Exception {
        Query query = getSession().createQuery(hql_decreaseDiscussions);
        query.setLong(0, discussion.getId());
        query.executeUpdate();
    }//decreaseDiscussions()


    private static final String sql_increaseReplies = "update pgist_discussion_post set replies=replies+1 where id=?";
    
    
    public void increaseReplies(DiscussionPost post) throws Exception {
        Connection conn = getSession().connection();
        PreparedStatement pstmt = conn.prepareStatement(sql_increaseReplies);
        pstmt.setLong(1, post.getId());
        pstmt.executeUpdate();
        getSession().flush();
    }//increaseReplies()


    private static final String sql_decreaseReplies = "update pgist_discussion_post set replies=replies-1 where id=?";
    
    
    public void decreaseReplies(DiscussionPost post) throws Exception {
        Connection conn = getSession().connection();
        PreparedStatement pstmt = conn.prepareStatement(sql_decreaseReplies);
        pstmt.setLong(1, post.getId());
        pstmt.executeUpdate();
    }//decreaseReplies()


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
    
    
    public Collection getConcerns(InfoStructure structure, Long[] ids, PageSetting setting) throws Exception {
        List concerns = new ArrayList();
        
        StringBuilder sb = new StringBuilder(sql_getConcerns_A_20);
        
        for (int i=0; i<ids.length; i++) {
            sb.append(" INTERSECT ").append(sql_getConcerns_A_21).append(ids[i]);
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
    
    
    public Collection getConcerns(InfoObject object, Long[] ids, PageSetting setting) throws Exception {
        InfoStructure structure = object.getStructure();
        
        List concerns = new ArrayList();
        
        StringBuilder sb = new StringBuilder(sql_getConcerns_B_20);
        
        for (int i=0; i<ids.length; i++) {
            sb.append(" INTERSECT ");
            sb.append(sql_getConcerns_B_21).append(ids[i]);
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


    private static final String sql_getRelatedInfo = "select isid, ioid from view_structure_discussions where did=?";
    
    
    public Object getRelatedInfo(Discussion discussion) throws Exception {
        Connection conn = getSession().connection();
        
        PreparedStatement pstmt = conn.prepareStatement(sql_getRelatedInfo);
        
        pstmt.setLong(1, discussion.getId());
        
        ResultSet rs = pstmt.executeQuery();
        
        if (rs.next()) {
            Long isid = rs.getLong("isid");
            Long ioid = rs.getLong("ioid");
            if (ioid!=0) {
                return getHibernateTemplate().load(InfoObject.class, ioid);
            } else {
                return getHibernateTemplate().load(InfoStructure.class, isid);
            }
        }
        
        return null;
    }//getRelatedInfo()


    private static final String sql_getContextPosts_A_1 =
        "SELECT count(distinct pid) from " +
        DBMetaData.VIEW_POST_REPLY_TAG_IN_DISCUSSION +
        " v where v.isid=:isid and v.pid<>:pid and v.tid in (select tid from view_post_reply_tags where pid=:pid);";
    
    private static final String sql_getContextPosts_A_2 =
        "SELECT distinct v.pid, v.ioid, p.views AS nview, p.replies AS nreply, p.replytime AS rtime, p.createtime AS ctime, p.numagree AS nagree, p.numvote AS nvote from "+
        DBMetaData.VIEW_POST_REPLY_TAG_IN_DISCUSSION+
        " v, pgist_discussion_post p where p.id=v.pid and v.isid=? and v.pid<>? and v.tid in (select tid from view_post_reply_tags where pid=?) order by #sorting OFFSET ? LIMIT ?;";
    
    
    public Collection getContextPosts(Long isid, Long pid, PageSetting setting, int sorting) throws Exception {
        List posts = new ArrayList();
        
        Connection connection = getSession().connection();
        Statement stmt = connection.createStatement();
        
        String sql = sql_getContextPosts_A_1.replace(":isid", isid.toString()).replace(":pid", pid.toString());
        
        //get count
        
        ResultSet rs = stmt.executeQuery(sql);
        
        rs.next();
        
        int count = rs.getInt(1);
        
        setting.setRowSize(count);
        
        if (count==0) return posts;
        
        //get records
        
        sql = sql_getContextPosts_A_2.replace("#sorting", postSorting[1][sorting]);
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setLong(1, isid);
        pstmt.setLong(2, pid);
        pstmt.setLong(3, pid);
        pstmt.setInt(4, setting.getFirstRow());
        pstmt.setInt(5, setting.getRowOfPage());
        rs = pstmt.executeQuery();
        
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


    private static final String sql_getContextPosts_B =
        "select distinct v.pid as xid, v.ioid as yid from " +
        DBMetaData.VIEW_POST_REPLY_TAG_IN_DISCUSSION +
        " v where v.isid=:isid and v.pid<>:pid and v.tid=";
    
    
    public Collection getContextPosts(Long isid, Long pid, Long[] ids, PageSetting setting, int sorting) throws Exception {
        List posts = new ArrayList();
        
        StringBuilder sb = new StringBuilder();
        
        for (int i=0; i<ids.length; i++) {
            if (i>0) sb.append(" INTERSECT ");
            sb.append(sql_getContextPosts_B).append(ids[i]);
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
        
        sql = "select distinct p.id, v.ioid, p.views AS nview, p.replies AS nreply, p.replytime AS rtime, p.createtime AS ctime, p.numagree AS nagree, p.numvote AS nvote from (piece) AS v, pgist_discussion_post AS p WHERE v.pid=p.id order by #sorting offset "+setting.getFirstRow()+" limit "+setting.getRowOfPage();
        
        rs = stmt.executeQuery(sql.replace("#sorting", postSorting[1][sorting]));
        
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


    private static final String hql_getContextPosts_A_1 = "select count(p.id) from DiscussionPost p where p.discussion.id in (select o.discussion.id from InfoObject o where o.structure.id=?)";
    
    private static final String hql_getContextPosts_A_2 = "from DiscussionPost p where p.discussion.id in (select o.discussion.id from InfoObject o where o.structure.id=?) order by #sorting";
    
    
    public Collection getContextPosts(Long isid, PageSetting setting, int sorting) throws Exception {
        List list = new ArrayList();
        
        Query query = null;
        
        query = getSession().createQuery(hql_getContextPosts_A_1);
        query.setLong(0, isid);
        query.setMaxResults(1);
        
        int count = ((Number) query.uniqueResult()).intValue();
        
        if (setting.getRowOfPage()==-1) setting.setRowOfPage(count);
        
        setting.setRowSize(count);
        
        if (count==0) return list;
        
        query = getSession().createQuery(hql_getContextPosts_A_2.replace("#sorting", postSorting[0][sorting]));
        query.setLong(0, isid);
        
        query.setFirstResult(setting.getFirstRow());
        query.setMaxResults(setting.getRowOfPage());
        
        return query.list();
    }//getContextPosts()


    private static final String hql_getContextPosts_B_1 = "select count(p.id) from DiscussionPost p where p.discussion.id in (select o.discussion.id from InfoObject o where o.structure.id=?) and p.tags.id in (##)";
    
    private static final String hql_getContextPosts_B_2 = "from DiscussionPost p where p.discussion.id in (select o.discussion.id from InfoObject o where o.structure.id=?) and p.tags.id in (##) order by #sorting";
    
    
    public Collection getContextPosts(Long isid, Long[] ids, PageSetting setting, int sorting) throws Exception {
        List list = new ArrayList();
        
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<ids.length; i++) {
            if (i>0) sb.append(',');
            sb.append(ids[i]);
        }
        String idstring = sb.toString();
        
        Query query = getSession().createQuery(hql_getContextPosts_B_1.replace("##", idstring));
        query.setLong(0, isid);
        query.setMaxResults(1);
        
        int count = ((Number) query.uniqueResult()).intValue();
        
        if (setting.getRowOfPage()==-1) setting.setRowOfPage(count);
        
        setting.setRowSize(count);
        
        if (count==0) return list;
        
        query = getSession().createQuery(hql_getContextPosts_B_2.replace("##", idstring).replace("#sorting", postSorting[0][sorting]));
        query.setLong(0, isid);
        
        query.setFirstResult(setting.getFirstRow());
        query.setMaxResults(setting.getRowOfPage());
        
        return query.list();
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


    private static final String sql_searchTags_A_1 = "select count(distinct tid) from "+DBMetaData.VIEW_DPOST_TAG_IN_TARGET+" where did in (##) and tname like ?";
    
    private static final String sql_searchTags_A_2 = "select distinct tid, tname from "+DBMetaData.VIEW_DPOST_TAG_IN_TARGET+" where did in (##) and tname like ? order by tname";
    
    
    public Collection searchTags(InfoStructure structure, String tag, PageSetting setting) throws Exception {
        List list = new ArrayList();
        
        StringBuilder sb = new StringBuilder(structure.getId().toString());
        for (InfoObject obj : (Set<InfoObject>) structure.getInfoObjects()) {
            sb.append(',').append(obj.getDiscussion().getId().toString());
        }
        
        Connection connection = getSession().connection();
        PreparedStatement pstmt = connection.prepareStatement(sql_searchTags_A_1.replace("##", sb.toString()));
        
        pstmt.setString(1, '%'+tag.toLowerCase()+'%');
        
        ResultSet rs = pstmt.executeQuery();
        
        rs.next();
        int count = rs.getInt(1);
        if (setting.getRowOfPage()==-1) setting.setRowOfPage(count);
        setting.setRowSize(count);
        
        if (count==0) return list;
        
        pstmt = connection.prepareStatement(sql_searchTags_A_2.replace("##", sb.toString())+" OFFSET "+setting.getFirstRow()+" LIMIT "+setting.getRowOfPage());
        
        pstmt.setString(1, '%'+tag.toLowerCase()+'%');
        
        rs = pstmt.executeQuery();
        
        while (rs.next()) {
            Tag one = (Tag) getHibernateTemplate().load(Tag.class, rs.getLong(1));
            list.add(one);
        }//while
        
        return list;
    }//searchTags()
    
    
    private static final String sql_searchTags_B_1 = "select count(distinct tid) from "+DBMetaData.VIEW_DPOST_TAG_IN_TARGET+" where did=? and tname like ?";
    
    private static final String sql_searchTags_B_2 = "select distinct tid, tname from "+DBMetaData.VIEW_DPOST_TAG_IN_TARGET+" where did=? and lower(tname) like ? order by tname";
    
    
    public Collection searchTags(InfoStructure structure, InfoObject infoObject, String tag, PageSetting setting) throws Exception {
        List list = new ArrayList();
        
        Connection connection = getSession().connection();
        PreparedStatement pstmt = connection.prepareStatement(sql_searchTags_B_1);
        
        pstmt.setLong(1, infoObject.getId());
        pstmt.setString(2, '%'+tag.toLowerCase()+'%');
        
        ResultSet rs = pstmt.executeQuery();
        
        rs.next();
        int count = rs.getInt(1);
        if (setting.getRowOfPage()==-1) setting.setRowOfPage(count);
        setting.setRowSize(count);
        
        if (count==0) return list;
        
        pstmt = connection.prepareStatement(sql_searchTags_B_2+" OFFSET "+setting.getFirstRow()+" LIMIT "+setting.getRowOfPage());
        
        pstmt.setLong(1, infoObject.getId());
        pstmt.setString(2, '%'+tag.toLowerCase()+'%');
        
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
    
    
    private static final String hql_deleteVotings = "delete YesNoVoting v where v.targetType=? and v.targetId=?";
    
    
    public void deleteVotings(Long infoObjectId) throws Exception {
        Query query = getSession().createQuery(hql_deleteVotings);
        query.setInteger(0, YesNoVoting.TYPE_INFO_OBJECT);
        query.setLong(1, infoObjectId);
        query.executeUpdate();
    }//deleteVotings()


    private static final String sql_getTagCloud_1 = "select count(distinct tid) from "+DBMetaData.VIEW_DPOST_TAG_IN_TARGET+" where did in (##)";
    
    private static final String sql_getTagCloud_2 = "select tid, count(tid) from "+DBMetaData.VIEW_DPOST_TAG_IN_TARGET+" where did in (##) group by tid order by count(tid)";
    
    
    public Collection getTagCloud(InfoStructure structure, PageSetting setting) throws Exception {
        List list = new ArrayList();
        
        Connection connection = getSession().connection();
        Statement stmt = connection.createStatement();
        
        StringBuilder sb = new StringBuilder(structure.getDiscussion().getId().toString());
        
        for (InfoObject obj : (Set<InfoObject>) structure.getInfoObjects()) {
            sb.append(",").append(obj.getDiscussion().getId().toString());
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


    public Discussion getDiscussionById(Long did) throws Exception {
        return (Discussion) load(Discussion.class, did);
    }//getDiscussionById()

    
    private static final String hql_processId = "select tr.id from TagReference tr, CCT cct, InfoStructure istr where istr.id=? and istr.cctId=cct.id and tr.cctId=cct.id and tr.tag.id=?";
    
    
    public Long processId(Long isid, Long tagId) {
        List list = getHibernateTemplate().find(hql_processId, new Object[] {
                isid,
                tagId
        });
        
        if (list.size()>0) return (Long) list.get(0);
        
        return null;
    }//processId()
    
    
    public Long[] processIds(Long isid, String ids, boolean tagId) throws Exception {
        ArrayList list = new ArrayList();
        
        for (String s : ids.split(",")) {
            if (s!=null && s.length()>0) {
                Long id = new Long(s);
                if (tagId)  id = processId(isid, id);
                if (id!=null) list.add(id);
            }
        }
        
        Long[] result = new Long[list.size()];
        
        for (int i=0; i<list.size(); i++) result[i] = (Long) list.get(i);
        
        return result;
    }//processIds()


    private static final String hql_getEmailUsers = "select r.owner from DiscussionReply r where r.parent.id=? and r.id<>?";
    
    
    public Set getEmailUsers(DiscussionPost parent, DiscussionReply reply) throws Exception {
        List list = getHibernateTemplate().find(hql_getEmailUsers, new Object[] {
                parent.getId(),
                reply.getId(),
        });
        
        Long owner = reply.getOwner().getId();
        
        Set set = new HashSet();
        if (parent.getOwner().getId()!=owner) set.add(parent.getOwner());
        
        for (User user : (List<User>) list) {
            if (user.getId()==owner) continue;
            if (!user.isEmailNotify()) continue;
            
            set.add(user);
        }//for
        
        return set;
    }//getEmailUsers()


    private static final String sql_getInfoObjectByDiscussionId = "select ioid from view_structure_discussions where did=?";
    
    
    public InfoObject getInfoObjectByDiscussionId(Long did) throws Exception {
        Connection connection = getSession().connection();
        PreparedStatement pstmt = connection.prepareStatement(sql_getInfoObjectByDiscussionId);
        
        pstmt.setLong(1, did);
        ResultSet rs = pstmt.executeQuery();
        
        if (rs.next()) {
            Long ioid = rs.getLong(1);
            if (ioid>0) {
                return (InfoObject) getHibernateTemplate().load(InfoObject.class, ioid);
            } else {
                return null;
            }
        } else {
            throw new Exception("can't find the ioid corresponds to the did.");
        }
    }//getInfoObjectByDiscussionId()


    public void setupPostEmailNotify(Long id, boolean turnon) throws Exception {
        DiscussionPost post = (DiscussionPost) getHibernateTemplate().load(DiscussionPost.class, id);
        
        if (!post.getOwner().getId().equals(WebUtils.currentUserId())) {
            throw new Exception("you are not the author of this post");
        }
        
        post.setEmailNotify(turnon);
        
        save(post);
    }//setupPostEmailNotify()


    public void setupReplyEmailNotify(Long id, boolean turnon) throws Exception {
        DiscussionReply reply = (DiscussionReply) getHibernateTemplate().load(DiscussionReply.class, id);
        
        if (!reply.getOwner().getId().equals(WebUtils.currentUserId())) {
            throw new Exception("you are not the author of this reply");
        }
        
        reply.setEmailNotify(turnon);
        
        save(reply);
    }//setupReplyEmailNotify()


}//class DiscussionDAOImpl
