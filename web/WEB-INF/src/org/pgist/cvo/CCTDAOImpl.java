package org.pgist.cvo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.hibernate.Query;
import org.pgist.system.BaseDAOImpl;
import org.pgist.util.DBMetaData;
import org.pgist.util.PageSetting;
import org.pgist.util.WebUtils;


/**
 * 
 * @author kenny
 *
 */
public class CCTDAOImpl extends BaseDAOImpl implements CCTDAO {
    
    
    /**
     * Sorting index for concerns
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
    private static final String[][] concernSorting = {
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
    
    
    private TagReferenceComparator comparator = new TagReferenceComparator(false);


    public CCT getCCTById(Long cctId) throws Exception {
        return (CCT) load(CCT.class, cctId);
    }//getCCTById()


    public Concern getConcernById(Long concernId) throws Exception {
        return (Concern) load(Concern.class, concernId);
    }//getConcernById()


    public Comment getCommentById(Long commentId) throws Exception {
        return (Comment) load(Comment.class, commentId);
    }//getCommentById()


    private static final String hql_getCCTs = "from CCT c where c.deleted=? order by c.id";
    
    
    public Collection getCCTs() throws Exception {
        return getHibernateTemplate().find(hql_getCCTs, new Boolean(false));
    }//getCCTs()


    private static final String hql_getMyConcerns = "from Concern c where c.deleted=? and c.cct.id=? and c.author.id=? order by c.createTime desc";
    
    
    public Collection getMyConcerns(Long cctId, Long userId) throws Exception {
        return getHibernateTemplate().find(hql_getMyConcerns, new Object[] {
            new Boolean(false),
            cctId,
            userId
        });
    }//getMyConcerns()


    private static final String hql_getOthersConcerns = "from Concern c where c.deleted=? and c.cct.id=? and author.id<>? order by c.createTime";
    
    
    public Collection getOthersConcerns(Long cctId, Long userId, int count) throws Exception {
        getHibernateTemplate().setMaxResults(count);
        return getHibernateTemplate().find(hql_getOthersConcerns, new Object[] {
            new Boolean(false),
            cctId,
            userId
        });
    }//getOthersConcerns()


    private static final String getRandomConcerns1 = "select count(c.id) from Concern c where c.deleted=? and c.cct.id=? and c.author.id<>?";
    private static final String getRandomConcerns2 = "from Concern c where c.deleted=? and c.cct.id=? and c.author.id<>? order by c.sortOrder";
    
    
    public Collection getRandomConcerns(Long cctId, Long userId, PageSetting setting) throws Exception {
        List result = new ArrayList();
        
        List list = getHibernateTemplate().find(getRandomConcerns1, new Object[] {new Boolean(false), cctId, userId});
        if (list==null || list.size()==0) return result;
        
        int total = ((Number) list.get(0)).intValue();
        if (setting.getRowOfPage()==-1) setting.setRowOfPage(total);
        setting.setRowSize(total);
        
        Query query = getSession().createQuery(getRandomConcerns2);
        query.setFirstResult(setting.getFirstRow());
        query.setMaxResults(setting.getRowOfPage());
        query.setBoolean(0, false);
        query.setLong(1, cctId);
        query.setLong(2, userId);
        
        return query.list();
    }//getRandomConcerns()


    private static final String hql_getConcernsByTag = "select c from Concern c inner join c.tags t where c.deleted=? and t.id=?";
    
    
    public Collection getConcernsByTag(TagReference tagRef, int count) throws Exception {
        if (count>0) getHibernateTemplate().setMaxResults(count);
        return getHibernateTemplate().find(hql_getConcernsByTag, new Object[] {
                new Boolean(false),
                tagRef.getId()
        });
    }//getConcernByTag()

    
    private static final String hql_getConcernsTotal0 = "select count(c.id) from Concern c where c.deleted=?";
    private static final String hql_getConcernsTotal1 = "select count(c.id) from Concern c where c.deleted=? and c.author.id=?";
    private static final String hql_getConcernsTotal2 = "select count(c.id) from Concern c where c.deleted=? and c.author.id!=?";
    

    public int getConcernsTotal(CCT cct, int whose, Long userId) throws Exception {
        List list = null;
        
        switch (whose) {
            case 0:
                list = getHibernateTemplate().find(hql_getConcernsTotal0, new Boolean(false));
                break;
            case 1:
                list = getHibernateTemplate().find(hql_getConcernsTotal1, new Object[] {
                        new Boolean(false),
                        userId
                });
                break;
            case 2:
                list = getHibernateTemplate().find(hql_getConcernsTotal2, new Object[] {
                        new Boolean(false),
                        userId
                });
                break;
            default:
        }
        
        if (list!=null && list.size()>0) return ((Number)list.get(0)).intValue();
        
        return 0;
    }//getConcernsTotal()


    public void delete(TagReference ref) throws Exception {
        getHibernateTemplate().delete(ref);
    }//delete()


    private static final String hql_searchTags = "select tr from CCT c, TagReference tr where c.id=? and lower(tr.tag.name) like ?";
    
    
    public Collection searchTags(Long id, String tag) throws Exception {
        return getHibernateTemplate().find(hql_searchTags, new Object[] {
                id,
                '%' + tag + '%'
        });
    }//searchTags()


    public TagReference getTagReferenceById(Long tagRefId) throws Exception {
        return (TagReference) load(TagReference.class, tagRefId);
    }//getTagReferenceById()


    private static final String hql_getTagReferenceByTagId = "from TagReference tr where tr.cctId=? and tr.tag.id=?";
    
    
    /**
     * @param cctId
     * @param tagId
     * @return
     * @exception
     */
    public TagReference getTagReferenceByTagId(Long cctId, Long tagId) throws Exception {
        List list = getHibernateTemplate().find(hql_getTagReferenceByTagId, new Object[] {
                cctId,
                tagId,
        });
        if (list.size()>0) {
            TagReference ref = (TagReference) list.get(0);
            return ref;
        }
        return null;
    }//getTagReferenceByTagId()


    private static String hql_getTagsByRank = "from TagReference tr where tr.cctId=? order by tr.times desc, tr.tag.name";


    public Collection getTagsByRank(CCT cct, int count) throws Exception {
        getHibernateTemplate().setMaxResults(count);
        List list = getHibernateTemplate().find(hql_getTagsByRank, cct.getId());
        Collections.sort(list, comparator);
        return list;
    } //getTagsByRank()
    
    
    private static final String getTagCloud1 = "select count(tr.id) from TagReference tr where tr.cctId=?";
    
    private static final String getTagCloud2 = "from TagReference tr where tr.cctId=? order by tr.times desc, tr.tag.name";
    
    
    public Collection getTagCloud(CCT cct, PageSetting setting) throws Exception {
    	List result = new ArrayList();
        
        List list = getHibernateTemplate().find(getTagCloud1, cct.getId());
        
        int total = ((Number) list.get(0)).intValue();
        if (setting.getRowOfPage()==-1) setting.setRowOfPage(total);
        setting.setRowSize(total);
        
        if (total==0) return result;
        
        Query query = getSession().createQuery(getTagCloud2);
        
        query.setLong(0, cct.getId());
        
        query.setFirstResult(setting.getFirstRow());
        query.setMaxResults(setting.getRowOfPage());
        
        return query.list();
    } //getTagCloud()


    private static String getTagsByThreshold = "from TagReference tr where tr.cctId=? and tr.times>? order by tr.times desc, tr.tag.name";


    public Collection getTagsByThreshold(CCT cct, int threshold) throws Exception {
        List list = getHibernateTemplate().find(
                getTagsByThreshold,
                new Object[] {
                cct.getId(),
                new Integer(threshold),
        });
        Collections.sort(list, comparator);
        return list;
    } //getTagsByThreshold()


    private static final String hql_increaseRefTimes = "update TagReference tr set tr.times=tr.times+1 where tr.id=?";


    public void increaseRefTimes(TagReference ref) throws Exception {
        getSession().createQuery(hql_increaseRefTimes).setLong(0, ref.getId()).executeUpdate();
    }//increaseRefTimes()


    private static final String hql_decreaseRefTimes = "update TagReference tr set tr.times=tr.times-1 where tr.id=?";


    public void decreaseRefTimes(TagReference ref) throws Exception {
        getSession().createQuery(hql_decreaseRefTimes).setLong(0, ref.getId()).executeUpdate();
        if (ref.getTimes()<=0) {
            delete(ref);
        } else {
            save(ref);
        }
    }//decreaseRefTimes()


    private static final String hql_getContextConcerns_A_11 = "select count(c.id) from Concern c where c.deleted=? and c.cct.id=? #type";
    
    private static final String hql_getContextConcerns_A_12 = "from Concern c where c.deleted=? and c.cct.id=? #type order by c.#sorting";
    
    
    public Collection getContextConcerns(CCT cct, PageSetting setting, String type, int sorting) throws Exception {
        List list = new ArrayList();
        
        Query query = null;
        
        long uid = WebUtils.currentUserId();
        
        //get count
        String hql1 = hql_getContextConcerns_A_11;
        if("all".equalsIgnoreCase(type)) {
            query = getSession().createQuery(hql1.replace("#type", ""));
        	query.setBoolean(0, false);
        	query.setLong(1, cct.getId());
        } else if ("owner".equalsIgnoreCase(type)) {
            query = getSession().createQuery(hql1.replace("#type", " and c.author.id=? "));
            query.setBoolean(0, false);
            query.setLong(1, cct.getId());
            query.setLong(2, uid);
        } else if ("other".equalsIgnoreCase(type)) {
            query = getSession().createQuery(hql1.replace("#type", " and c.author.id<>? "));
            query.setBoolean(0, false);
            query.setLong(1, cct.getId());
            query.setLong(2, uid);
        } else {
            throw new Exception("unknown type: "+type);
        }
        
        int count = ((Number) query.uniqueResult()).intValue();
        
        if (setting.getRowOfPage()==-1) setting.setRowOfPage(count);
        
        setting.setRowSize(count);
        
        if (count==0) return list;
        
        //get records
        String hql2 = hql_getContextConcerns_A_12.replace("#sorting", concernSorting[0][sorting]);
        if("all".equalsIgnoreCase(type)) {
            query = getSession().createQuery(hql2.replace("#type", ""));
            query.setBoolean(0, false);
            query.setLong(1, cct.getId());
        } else if ("owner".equalsIgnoreCase(type)) {
            query = getSession().createQuery(hql2.replace("#type", " and c.author.id=? "));
            query.setBoolean(0, false);
            query.setLong(1, cct.getId());
            query.setLong(2, uid);
        } else if ("other".equalsIgnoreCase(type)) {
            query = getSession().createQuery(hql2.replace("#type", " and c.author.id<>? "));
            query.setBoolean(0, false);
            query.setLong(1, cct.getId());
            query.setLong(2, uid);
        } else {
            throw new Exception("unknown type: "+type);
        }
        
        query.setFirstResult(setting.getFirstRow());
        query.setMaxResults(setting.getRowOfPage());
        
        return query.list();
    }//getContextConcerns()


    private static final String sql_getContextConcerns_B_1 = "select count(distinct cid) from " + DBMetaData.VIEW_CONCERN_TAG_IN_CCT + " where cctid=? #type and lower(tname)=?";
    
    private static final String sql_getContextConcerns_B_2 = "select c.id AS cid, c.views AS nview, c.replies AS nreply, c.replytime AS rtime, c.createtime AS ctime, c.numagree AS nagree, c.numvote AS nvote from pgist_cvo_concerns c where c.id IN (select distinct cid from " + DBMetaData.VIEW_CONCERN_TAG_IN_CCT + " where cctid=? #type and lower(tname)=?) order by #sorting OFFSET ? LIMIT ?";
    
    
    public Collection getContextConcerns(CCT cct, PageSetting setting, String filter, String type, int sorting) throws Exception {
        List list = new ArrayList();
        
        Connection connection = getSession().connection();
        PreparedStatement pstmt = null;
        
        long uid = WebUtils.currentUserId();
        
        //get count
        String sql1 = sql_getContextConcerns_B_1;
        if("all".equalsIgnoreCase(type)) {
            pstmt = connection.prepareStatement(sql1.replace("#type", ""));
            pstmt.setLong(1, cct.getId());
            pstmt.setString(2, filter.toLowerCase());
        } else if ("owner".equalsIgnoreCase(type)) {
            pstmt = connection.prepareStatement(sql1.replace("#type", " and c.author.id=? "));
            pstmt.setLong(1, cct.getId());
            pstmt.setString(2, filter.toLowerCase());
            pstmt.setLong(3, uid);
        } else if ("other".equalsIgnoreCase(type)) {
            pstmt = connection.prepareStatement(sql1.replace("#type", " and c.author.id<>? "));
            pstmt.setLong(1, cct.getId());
            pstmt.setString(2, filter.toLowerCase());
            pstmt.setLong(3, uid);
        } else {
            throw new Exception("unknown type: "+type);
        }
        
        ResultSet rs = pstmt.executeQuery();
        
        rs.next();
        
        int count = rs.getInt(1);
        
        if (setting.getRowOfPage()==-1) setting.setRowOfPage(count);
        
        setting.setRowSize(count);
        
        if (count==0) return list;
        
        //get records
        String sql2 = sql_getContextConcerns_B_2.replace("#sorting", concernSorting[0][sorting]);
        if("all".equalsIgnoreCase(type)) {
            pstmt = connection.prepareStatement(sql2.replace("#type", ""));
            pstmt.setLong(1, cct.getId());
            pstmt.setString(2, filter.toLowerCase());
            pstmt.setInt(3, setting.getFirstRow());
            pstmt.setInt(4, setting.getRowOfPage());
        } else if ("owner".equalsIgnoreCase(type)) {
            pstmt = connection.prepareStatement(sql2.replace("#type", " and c.author.id=? "));
            pstmt.setLong(1, cct.getId());
            pstmt.setString(2, filter.toLowerCase());
            pstmt.setLong(3, uid);
            pstmt.setInt(4, setting.getFirstRow());
            pstmt.setInt(5, setting.getRowOfPage());
        } else if ("other".equalsIgnoreCase(type)) {
            pstmt = connection.prepareStatement(sql2.replace("#type", " and c.author.id<>? "));
            pstmt.setLong(1, cct.getId());
            pstmt.setString(2, filter.toLowerCase());
            pstmt.setLong(3, uid);
            pstmt.setInt(4, setting.getFirstRow());
            pstmt.setInt(5, setting.getRowOfPage());
        } else {
            throw new Exception("unknown type: "+type);
        }
        
        rs = pstmt.executeQuery();
        
        while (rs.next()) {
            Long one = rs.getLong(1);
            list.add(getHibernateTemplate().load(Concern.class, one));
        }//while
        
        return list;
    }//getContextConcerns()


    private static final String hql_increaseVoting_11 = "update Concern c set c.numVote=c.numVote+1 where c.id=?";
    
    private static final String hql_increaseVoting_12 = "update Concern c set c.numAgree=c.numAgree+1 where c.id=?";
    
    
    public void increaseVoting(Concern concern, boolean agree) throws Exception {
        getSession().createQuery(hql_increaseVoting_11).setLong(0, concern.getId()).executeUpdate();
        if (agree) {
            getSession().createQuery(hql_increaseVoting_12).setLong(0, concern.getId()).executeUpdate();
        }
    }//increaseVoting()


    private static final String hql_increaseReplies = "update Concern set replies=replies+1 where id=?";
    
    
    public void increaseReplies(Concern concern) throws Exception {
        Query query = getSession().createQuery(hql_increaseReplies);
        query.setLong(0, concern.getId());
        query.executeUpdate();
    }//increaseReplies()


    private static final String hql_decreaseReplies = "update Concern set replies=replies-1 where id=?";
    
    
    public void decreaseReplies(Concern concern) throws Exception {
        Query query = getSession().createQuery(hql_decreaseReplies);
        query.setLong(0, concern.getId());
        query.executeUpdate();
    }//decreaseReplies()


    private static final String hql_deleteComments = "update Comment set deleted=? where concern_id=?";
    
    
    public void deleteComments(Concern concern) throws Exception {
        Query query = getSession().createQuery(hql_deleteComments);
        query.setBoolean(0, true);
        query.setLong(1, concern.getId());
        query.executeUpdate();
    }//deleteComments()


    private static final String hql_getComments_A_1 = "select count(c.id) from Comment c where c.deleted=? and c.concern.id=?";
    
    private static final String hql_getComments_A_2 = "from Comment c where c.deleted=? and c.concern.id=? order by c.id asc";
    
    
    public Collection getComments(Long concernId, PageSetting setting) throws Exception {
        //get count
        Query query = getSession().createQuery(hql_getComments_A_1);
        query.setBoolean(0, false);
        query.setLong(1, concernId);
        
        int count = ((Number) query.uniqueResult()).intValue();
        
        if (setting.getRowOfPage()==-1) setting.setRowOfPage(count);
        setting.setRowSize(count);
        
        if (count==0) return new ArrayList();
        
        //get records
        query = getSession().createQuery(hql_getComments_A_2);
        query.setBoolean(0, false);
        query.setLong(1, concernId);
        query.setFirstResult(setting.getFirstRow());
        query.setMaxResults(setting.getRowOfPage());
        
        return query.list();
    }//getComments()


    private static final String hql_increaseCommentVoting_11 = "update Comment c set c.numVote=c.numVote+1 where c.id=?";
    
    private static final String hql_increaseCommentVoting_12 = "update Comment c set c.numAgree=c.numAgree+1 where c.id=?";
    
    
    public void increaseVoting(Comment comment, boolean agree) throws Exception {
        getSession().createQuery(hql_increaseCommentVoting_11).setLong(0, comment.getId()).executeUpdate();
        if (agree) {
            getSession().createQuery(hql_increaseCommentVoting_12).setLong(0, comment.getId()).executeUpdate();
        }
    }//increaseVoting()


    private static final String hql_increaseViews = "update Concern set views=views+1 where id=?";
    
    
    public void increaseViews(Long concernId) throws Exception {
        Query query = getSession().createQuery(hql_increaseViews);
        query.setLong(0, concernId);
        query.executeUpdate();
    }//increaseViews()


}//class CCTDAOImpl
