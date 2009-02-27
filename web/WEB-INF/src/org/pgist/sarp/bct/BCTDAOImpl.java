package org.pgist.sarp.bct;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.pgist.system.BaseDAOImpl;
import org.pgist.users.User;
import org.pgist.util.DBMetaData;
import org.pgist.util.PageSetting;
import org.pgist.util.WebUtils;


/**
 * 
 * @author kenny
 *
 */
public class BCTDAOImpl extends BaseDAOImpl implements BCTDAO {
    
    
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


    public BCT getBCTById(Long bctId) throws Exception {
        return (BCT) load(BCT.class, bctId);
    }//getBCTById()


    public Concern getConcernById(Long concernId) throws Exception {
        return (Concern) load(Concern.class, concernId);
    }//getConcernById()


    public ConcernComment getConcernCommentById(Long commentId) throws Exception {
        return (ConcernComment) load(ConcernComment.class, commentId);
    }//getConcernCommentById()


    private static final String hql_getBCTs = "from BCT c where c.deleted=? order by c.id";
    
    
    public Collection getBCTs() throws Exception {
        return getHibernateTemplate().find(hql_getBCTs, new Boolean(false));
    }//getBCTs()


    private static final String hql_getMyConcerns = "from Concern c where c.deleted=? and c.bct.id=? and c.author.id=? order by c.createTime desc";
    
    
    public Collection getMyConcerns(Long bctId, Long userId) throws Exception {
        return getHibernateTemplate().find(hql_getMyConcerns, new Object[] {
            new Boolean(false),
            bctId,
            userId
        });
    }//getMyConcerns()


    private static final String hql_getOthersConcerns = "from Concern c where c.deleted=? and c.bct.id=? and author.id<>? order by c.createTime";
    
    
    public Collection getOthersConcerns(Long bctId, Long userId, int count) throws Exception {
        getHibernateTemplate().setMaxResults(count);
        return getHibernateTemplate().find(hql_getOthersConcerns, new Object[] {
            new Boolean(false),
            bctId,
            userId
        });
    }//getOthersConcerns()


    private static final String getRandomConcerns1 = "select count(c.id) from Concern c where c.deleted=? and c.bct.id=? and c.author.id<>?";
    private static final String getRandomConcerns2 = "from Concern c where c.deleted=? and c.bct.id=? and c.author.id<>? order by c.sortOrder";
    
    
    public Collection getRandomConcerns(Long bctId, Long userId, PageSetting setting) throws Exception {
        List result = new ArrayList();
        
        List list = getHibernateTemplate().find(getRandomConcerns1, new Object[] {new Boolean(false), bctId, userId});
        if (list==null || list.size()==0) return result;
        
        int total = ((Number) list.get(0)).intValue();
        if (setting.getRowOfPage()==-1) setting.setRowOfPage(total);
        setting.setRowSize(total);
        
        Query query = getSession().createQuery(getRandomConcerns2);
        query.setFirstResult(setting.getFirstRow());
        query.setMaxResults(setting.getRowOfPage());
        query.setBoolean(0, false);
        query.setLong(1, bctId);
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
    

    public int getConcernsTotal(BCT bct, int whose, Long userId) throws Exception {
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


    private static final String hql_searchTags = "select tr from BCT c, TagReference tr where c.id=? and lower(tr.tag.name) like ?";
    
    
    public Collection searchTags(Long id, String tag) throws Exception {
        return getHibernateTemplate().find(hql_searchTags, new Object[] {
                id,
                '%' + tag + '%'
        });
    }//searchTags()


    public TagReference getTagReferenceById(Long tagRefId) throws Exception {
        return (TagReference) load(TagReference.class, tagRefId);
    }//getTagReferenceById()


    private static final String hql_getTagReferenceByTagId = "from TagReference tr where tr.bctId=? and tr.tag.id=?";
    
    
    /**
     * @param bctId
     * @param tagId
     * @return
     * @exception
     */
    public TagReference getTagReferenceByTagId(Long bctId, Long tagId) throws Exception {
        List list = getHibernateTemplate().find(hql_getTagReferenceByTagId, new Object[] {
                bctId,
                tagId,
        });
        if (list.size()>0) {
            TagReference ref = (TagReference) list.get(0);
            return ref;
        }
        return null;
    }//getTagReferenceByTagId()


    private static String hql_getTagsByRank = "from TagReference tr where tr.bctId=? order by tr.times desc, tr.tag.name";


    public Collection getTagsByRank(BCT bct, int count) throws Exception {
        getHibernateTemplate().setMaxResults(count);
        List list = getHibernateTemplate().find(hql_getTagsByRank, bct.getId());
        Collections.sort(list, comparator);
        return list;
    } //getTagsByRank()
    
    
    private static final String getTagCloud1 = "select count(tr.id) from TagReference tr where tr.bctId=?";
    
    private static final String getTagCloud2 = "from TagReference tr where tr.bctId=? order by tr.times desc, tr.tag.name";
    
    
    public Collection getTagCloud(BCT bct, PageSetting setting) throws Exception {
    	List result = new ArrayList();
        
        List list = getHibernateTemplate().find(getTagCloud1, bct.getId());
        
        int total = ((Number) list.get(0)).intValue();
        if (setting.getRowOfPage()==-1) setting.setRowOfPage(total);
        setting.setRowSize(total);
        
        if (total==0) return result;
        
        Query query = getSession().createQuery(getTagCloud2);
        
        query.setLong(0, bct.getId());
        
        query.setFirstResult(setting.getFirstRow());
        query.setMaxResults(setting.getRowOfPage());
        
        return query.list();
    } //getTagCloud()


    private static String getTagsByThreshold = "from TagReference tr where tr.bctId=? and tr.times>? order by tr.times desc, tr.tag.name";


    public Collection getTagsByThreshold(BCT bct, int threshold) throws Exception {
        List list = getHibernateTemplate().find(
                getTagsByThreshold,
                new Object[] {
                bct.getId(),
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


    private static final String hql_getContextConcerns_A_11 = "select count(c.id) from Concern c where c.deleted=? and c.bct.id=? #type";
    
    private static final String hql_getContextConcerns_A_12 = "from Concern c where c.deleted=? and c.bct.id=? #type order by c.#sorting";
    
    
    public Collection getContextConcerns(BCT bct, PageSetting setting, String type, int sorting) throws Exception {
        List list = new ArrayList();
        
        Query query = null;
        
        long uid = WebUtils.currentUserId();
        
        //get count
        String hql1 = hql_getContextConcerns_A_11;
        if("all".equalsIgnoreCase(type)) {
            query = getSession().createQuery(hql1.replace("#type", ""));
        	query.setBoolean(0, false);
        	query.setLong(1, bct.getId());
        } else if ("owner".equalsIgnoreCase(type)) {
            query = getSession().createQuery(hql1.replace("#type", " and c.author.id=? "));
            query.setBoolean(0, false);
            query.setLong(1, bct.getId());
            query.setLong(2, uid);
        } else if ("other".equalsIgnoreCase(type)) {
            query = getSession().createQuery(hql1.replace("#type", " and c.author.id<>? "));
            query.setBoolean(0, false);
            query.setLong(1, bct.getId());
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
            query.setLong(1, bct.getId());
        } else if ("owner".equalsIgnoreCase(type)) {
            query = getSession().createQuery(hql2.replace("#type", " and c.author.id=? "));
            query.setBoolean(0, false);
            query.setLong(1, bct.getId());
            query.setLong(2, uid);
        } else if ("other".equalsIgnoreCase(type)) {
            query = getSession().createQuery(hql2.replace("#type", " and c.author.id<>? "));
            query.setBoolean(0, false);
            query.setLong(1, bct.getId());
            query.setLong(2, uid);
        } else {
            throw new Exception("unknown type: "+type);
        }
        
        query.setFirstResult(setting.getFirstRow());
        query.setMaxResults(setting.getRowOfPage());
        
        return query.list();
    }//getContextConcerns()


    private static final String sql_getContextConcerns_B_1 = "select count(distinct cid) from " + DBMetaData.VIEW_CONCERN_TAG_IN_BCT + " where bctid=? #type and lower(tname)=?";
    
    private static final String sql_getContextConcerns_B_2 = "select c.id AS cid, c.views AS nview, c.replies AS nreply, "
    	+ "c.replytime AS rtime, c.createtime AS ctime, c.numagree AS nagree, c.numvote AS nvote from sarp_concerns c "
    	+ "where c.id IN (select distinct cid from " + DBMetaData.VIEW_CONCERN_TAG_IN_BCT
    	+ " where bctid=? #type and lower(tname)=?) order by #sorting OFFSET ? LIMIT ?";
    
    
    public Collection getContextConcerns(BCT bct, PageSetting setting, String filter, String type, int sorting) throws Exception {
        List list = new ArrayList();
        
        Connection connection = getSession().connection();
        PreparedStatement pstmt = null;
        
        long uid = WebUtils.currentUserId();
        
        //get count
        String sql1 = sql_getContextConcerns_B_1;
        if("all".equalsIgnoreCase(type)) {
            pstmt = connection.prepareStatement(sql1.replace("#type", ""));
            pstmt.setLong(1, bct.getId());
            pstmt.setString(2, filter.toLowerCase());
        } else if ("owner".equalsIgnoreCase(type)) {
            pstmt = connection.prepareStatement(sql1.replace("#type", " and c.author.id=? "));
            pstmt.setLong(1, bct.getId());
            pstmt.setString(2, filter.toLowerCase());
            pstmt.setLong(3, uid);
        } else if ("other".equalsIgnoreCase(type)) {
            pstmt = connection.prepareStatement(sql1.replace("#type", " and c.author.id<>? "));
            pstmt.setLong(1, bct.getId());
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
            pstmt.setLong(1, bct.getId());
            pstmt.setString(2, filter.toLowerCase());
            pstmt.setInt(3, setting.getFirstRow());
            pstmt.setInt(4, setting.getRowOfPage());
        } else if ("owner".equalsIgnoreCase(type)) {
            pstmt = connection.prepareStatement(sql2.replace("#type", " and c.author.id=? "));
            pstmt.setLong(1, bct.getId());
            pstmt.setString(2, filter.toLowerCase());
            pstmt.setLong(3, uid);
            pstmt.setInt(4, setting.getFirstRow());
            pstmt.setInt(5, setting.getRowOfPage());
        } else if ("other".equalsIgnoreCase(type)) {
            pstmt = connection.prepareStatement(sql2.replace("#type", " and c.author.id<>? "));
            pstmt.setLong(1, bct.getId());
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


    private static final String hql_deleteConcernComments = "update ConcernComment set deleted=? where concern_id=?";
    
    
    public void deleteConcernComments(Concern concern) throws Exception {
        Query query = getSession().createQuery(hql_deleteConcernComments);
        query.setBoolean(0, true);
        query.setLong(1, concern.getId());
        query.executeUpdate();
    }//deleteConcernComments()


    private static final String hql_getConcernComments_A_1 = "select count(c.id) from ConcernComment c where c.deleted=? and c.concern.id=?";
    
    private static final String hql_getConcernComments_A_2 = "from ConcernComment c where c.deleted=? and c.concern.id=? order by c.id desc";
    
    
    public Collection getConcernComments(Long concernId, PageSetting setting) throws Exception {
        //get count
        Query query = getSession().createQuery(hql_getConcernComments_A_1);
        query.setBoolean(0, false);
        query.setLong(1, concernId);
        
        int count = ((Number) query.uniqueResult()).intValue();
        
        if (setting.getRowOfPage()==-1) setting.setRowOfPage(count);
        setting.setRowSize(count);
        
        if (count==0) return new ArrayList();
        
        //get records
        query = getSession().createQuery(hql_getConcernComments_A_2);
        query.setBoolean(0, false);
        query.setLong(1, concernId);
        query.setFirstResult(setting.getFirstRow());
        query.setMaxResults(setting.getRowOfPage());
        
        return query.list();
    }//getConcernComments()


    private static final String hql_increaseConcernCommentVoting_11 = "update GenericComment c set c.numVote=c.numVote+1 where c.id=?";
    
    private static final String hql_increaseConcernCommentVoting_12 = "update GenericComment c set c.numAgree=c.numAgree+1 where c.id=?";
    
    
    public void increaseVoting(ConcernComment comment, boolean agree) throws Exception {
        getSession().createQuery(hql_increaseConcernCommentVoting_11).setLong(0, comment.getId()).executeUpdate();
        if (agree) {
            getSession().createQuery(hql_increaseConcernCommentVoting_12).setLong(0, comment.getId()).executeUpdate();
        }
    }//increaseVoting()


    private static final String hql_increaseViews = "update Concern set views=views+1 where id=?";
    
    
    public void increaseViews(Long concernId) throws Exception {
        Query query = getSession().createQuery(hql_increaseViews);
        query.setLong(0, concernId);
        query.executeUpdate();
    }//increaseViews()


    private static final String hql_getConciseTags_1 = "select count(tr.id) from TagReference tr where tr.bctId=? and tr.times>0";
    
    private static final String[] hql_getConciseTags_2 = {
    	"from TagReference tr where tr.bctId=? and tr.times>0 order by tr.tag.name",
    	"from TagReference tr where tr.bctId=? and tr.times>0 order by tr.tag.name desc",
    	"from TagReference tr where tr.bctId=? and tr.times>0 order by tr.times desc, tr.tag.name",
    };
    
    
	@Override
	public Collection getConciseTags(BCT bct, PageSetting setting, int sorting) throws Exception {
        //get count
        Query query = getSession().createQuery(hql_getConciseTags_1);
        query.setLong(0, bct.getId());
        
        int count = ((Number) query.uniqueResult()).intValue();
        
        if (setting.getRowOfPage()==-1) setting.setRowOfPage(count);
        setting.setRowSize(count);
        
        if (count==0) return new ArrayList();
        
        //get records
        query = getSession().createQuery(hql_getConciseTags_2[sorting]);
        query.setLong(0, bct.getId());
        query.setFirstResult(setting.getFirstRow());
        query.setMaxResults(setting.getRowOfPage());
        
        return query.list();
	}//getConciseTags()


    private static final String hql_getThreadUsers = "select cc.author from ConcernComment cc where cc.concern.id=?";


    @Override
    public Set<User> getThreadUsers(Long concernId) throws Exception {
        Set<User> users = new HashSet<User>();
        users.addAll(getHibernateTemplate().find(hql_getThreadUsers, concernId));
        return users;
    }


}//class BCTDAOImpl
