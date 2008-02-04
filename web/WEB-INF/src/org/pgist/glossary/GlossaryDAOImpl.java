package org.pgist.glossary;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.Query;
import org.pgist.system.BaseDAOImpl;
import org.pgist.users.User;
import org.pgist.util.PageSetting;
import org.pgist.util.WebUtils;


/**
 * 
 * @author kenny
 *
 */
public class GlossaryDAOImpl extends BaseDAOImpl implements GlossaryDAO {
    
    
    private static String hql_getAllTerms = "from Term t where t.deleted=? order by t.name";
    
    
    public Collection getAllTerms() throws Exception {
        return getHibernateTemplate().find(hql_getAllTerms, false);
    }//getAllTerms()


    private static String hql_getTerms_11 = "select count(t.id) from Term t where t.deleted=?";
    
    private static String hql_getTerms_12 = "from Term t where t.deleted=? order by t.name";
    
    private static String hql_getTerms_21 = "select count(t.id) from Term t where t.deleted=? and t.name like ?";
    
    private static String hql_getTerms_22 = "from Term t where t.deleted=? and t.name like ? order by t.name";
    
    
    public Collection getTerms(PageSetting setting, boolean prefixed) throws Exception {
        String filter = setting.getFilter();
        
        List list = null;
        
        if (filter==null) {
            list = getHibernateTemplate().find(hql_getTerms_11, false);
        } else {
            list = getHibernateTemplate().find(hql_getTerms_21, new Object[] {
                    false,
                    prefixed ? filter+'%' : '%'+filter+'%',
            });
        }
        
        int count = ((Number) list.get(0)).intValue();
        setting.setRowSize(count);
        if (count==0) return new ArrayList(0);
        
        //display all the terms
        if (setting.getRowOfPage()==-1) setting.setRowOfPage(count);
        
        Query query = null;
        if (filter==null) {
            query = getSession().createQuery(hql_getTerms_12);
            query.setBoolean(0, false);
        } else {
            query = getSession().createQuery(hql_getTerms_22);
            query.setBoolean(0, false);
            query.setString(1, prefixed ? filter+'%' : '%'+filter+'%');
        }
        query.setFirstResult(setting.getFirstRow());
        query.setMaxResults(setting.getRowOfPage());
        
        return query.list();
    }//getTerms()


    private String formatStatus(int[] status) {
        StringBuffer sb = new StringBuffer();
        
        for (int i=0; i<status.length; i++) {
            if (i>0) sb.append(',');
            sb.append(status[i]);
        }
        
        return sb.toString();
    }//formatStatus()
    
    
    private static String hql_getTermsByName_1 = "from Term t where t.deleted=? and t.status in (#status) order by t.name ";
    
    private static String hql_getTermsByName_2 = "from Term t where t.deleted=? and t.status in (#status) and (lower(t.name) like ? or lower(t.shortDefinition) like ?) order by t.name ";
    
    
    public Collection getTermsByName(String filter, boolean ascending, int[] status) throws Exception {
        String ascend = ascending ? "asc" : "desc";
        
        Query query = null;
        
        if (filter==null || "".equals(filter.trim())) {
            query = getSession().createQuery(hql_getTermsByName_1.replace("#status", formatStatus(status))+ascend);
            query.setBoolean(0, false);
        } else {
            query = getSession().createQuery(hql_getTermsByName_2.replace("#status", formatStatus(status))+ascend);
            query.setBoolean(0, false);
            query.setString(1, '%'+filter.toLowerCase()+'%');
            query.setString(2, '%'+filter.toLowerCase()+'%');
        }
        
        return query.list();
    }//getTermsByName()


    private static String hql_getTermsByViews_1 = "from Term t where t.deleted=? and t.status in (#status) order by t.initial, t.viewCount ";
    
    private static String hql_getTermsByViews_2 = "from Term t where t.deleted=? and t.status in (#status) and t.name like ? order by t.viewCount ";
    
    
    public Collection getTermsByViews(String filter, boolean ascending, int[] status) throws Exception {
        String ascend = ascending ? "asc" : "desc";
        
        Query query = null;
        
        if (filter==null || "".equals(filter.trim())) {
            query = getSession().createQuery(hql_getTermsByViews_1.replace("#status", formatStatus(status))+ascend);
            query.setBoolean(0, false);
        } else {
            query = getSession().createQuery(hql_getTermsByViews_2.replace("#status", formatStatus(status))+ascend);
            query.setBoolean(0, false);
            query.setString(1, '%'+filter+'%');
        }
        
        return query.list();
    }//getTermsByViews()


    private static String hql_getTermsByComments_1 = "from Term t where t.deleted=? and t.status in (#status) order by t.initial, t.commentCount ";
    
    private static String hql_getTermsByComments_2 = "from Term t where t.deleted=? and t.status in (#status) and t.name like ? order by t.commentCount ";
    
    
    public Collection getTermsByComments(String filter, boolean ascending, int[] status) throws Exception {
        String ascend = ascending ? "asc" : "desc";
        
        Query query = null;
        
        if (filter==null || "".equals(filter.trim())) {
            query = getSession().createQuery(hql_getTermsByComments_1.replace("#status", formatStatus(status))+ascend);
            query.setBoolean(0, false);
        } else {
            query = getSession().createQuery(hql_getTermsByComments_2.replace("#status", formatStatus(status))+ascend);
            query.setBoolean(0, false);
            query.setString(1, '%'+filter+'%');
        }
        
        return query.list();
    }//getTermsByComments()


    private static String hql_getTermsByCreateTime_1 = "from Term t where t.deleted=? and t.status in (#status) order by t.initial, t.createTime ";
    
    private static String hql_getTermsByCreateTime_2 = "from Term t where t.deleted=? and t.status in (#status) and t.name like ? order by t.createTime ";
    
    
    public Collection getTermsByCreateTime(String filter, boolean ascending, int[] status) throws Exception {
        String ascend = ascending ? "asc" : "desc";
        
        Query query = null;
        
        if (filter==null || "".equals(filter.trim())) {
            query = getSession().createQuery(hql_getTermsByCreateTime_1.replace("#status", formatStatus(status))+ascend);
            query.setBoolean(0, false);
        } else {
            query = getSession().createQuery(hql_getTermsByCreateTime_2.replace("#status", formatStatus(status))+ascend);
            query.setBoolean(0, false);
            query.setString(1, '%'+filter+'%');
        }
        
        return query.list();
    }//getTermsByCreateTime()


    public Term getTermById(Long id) throws Exception {
        Term term = (Term) getHibernateTemplate().load(Term.class, id);
        if (term==null || term.isDeleted()) return null;
        return term;
    }//getTermById()
    
    
    private static String hql_getTermByName = "from Term t where t.deleted=? and t.name=?";
    

    public Term getTermByName(String name) throws Exception {
        List list = getHibernateTemplate().find(hql_getTermByName, new Object[] {false, name});
        if (list.size()==0) return null;
        return (Term) list.get(0);
    }//getTermByName()
    
    
    private static String hql_getCategoryByName = "from TermCategory tc where tc.name=?";
    

    public TermCategory getCategoryByName(String name) throws Exception {
        List list = getHibernateTemplate().find(hql_getCategoryByName, name);
        if (list.size()==0) return null;
        return (TermCategory) list.get(0);
    }//getCategoryByName()


    public void saveTermLink(TermLink link) throws Exception {
        getHibernateTemplate().saveOrUpdate(link);
    }//saveTermLink()


    public void saveTermSource(TermSource source) throws Exception {
        getHibernateTemplate().saveOrUpdate(source);
    }//saveTermSource()


    public void saveTerm(Term term) throws Exception {
        getHibernateTemplate().saveOrUpdate(term);
    }//saveTerm()


    private void adjustStatistics(Term term) throws Exception {
        increaseParticipantCount(term);
        
        int participantCount = term.getParticipantCount();
        
        int averageCount = 0;
        if (participantCount!=0) averageCount = (int) Math.ceil(term.getViewCount()/participantCount);
        term.setAverageCount(averageCount);
        
        getHibernateTemplate().saveOrUpdate(term);
    }//adjustStatistics()


    private static final String hql_setViewedByCurrentUser = "select count(tpr.id) from TermParticipantRecord tpr where tpr.term.id=? and tpr.user.id=?";
    
    
    synchronized public void setViewedByCurrentUser(Term term) throws Exception {
        Long userId = WebUtils.currentUserId();
        if (userId!=null) {
            User user = getUserById(WebUtils.currentUserId());
            
            List list = getHibernateTemplate().find(hql_setViewedByCurrentUser, new Object[] {
                    term.getId(),
                    user.getId(),
            });
            
            int count = ((Number) list.get(0)).intValue();
            
            if (count==0) {
                TermParticipantRecord record = new TermParticipantRecord();
                record.setTerm(term);
                record.setUser(user);
                getHibernateTemplate().save(record);
                
                adjustStatistics(term);
            }
        } else {
            adjustStatistics(term);
        }
    }//setViewedByCurrentUser()


    private static final String hql_decreaseCommentCount = "update Term set commentCount=commentCount-1 where id=?";
    
    
    public void decreaseCommentCount(Term term) throws Exception {
        getSession().createQuery(hql_decreaseCommentCount).setLong(0, term.getId()).executeUpdate();
    }//decreaseCommentCount()


    private static final String hql_increaseCommentCount = "update Term set commentCount=commentCount+1 where id=?";
    
    
    public void increaseCommentCount(Term term) throws Exception {
        getSession().createQuery(hql_increaseCommentCount).setLong(0, term.getId()).executeUpdate();
    }//increaseCommentCount()


    private static final String hql_decreaseParticipantCount = "update Term set participantCount=participantCount-1 where id=?";
    
    
    public void decreaseParticipantCount(Term term) throws Exception {
        getSession().createQuery(hql_decreaseParticipantCount).setLong(0, term.getId()).executeUpdate();
    }//decreaseParticipantCount()


    private static final String hql_increaseParticipantCount = "update Term set participantCount=participantCount+1 where id=?";
    
    
    public void increaseParticipantCount(Term term) throws Exception {
        getSession().createQuery(hql_increaseParticipantCount).setLong(0, term.getId()).executeUpdate();
    }//increaseParticipantCount()


    private static final String hql_decreaseViewCount = "update Term set viewCount=viewCount-1 where id=?";
    
    
    public void decreaseViewCount(Term term) throws Exception {
        getSession().createQuery(hql_decreaseViewCount).setLong(0, term.getId()).executeUpdate();
    }//decreaseViewCount()


    private static final String hql_increaseViewCount = "update Term set viewCount=viewCount+1 where id=?";
    
    
    public void increaseViewCount(Term term) throws Exception {
        getSession().createQuery(hql_increaseViewCount).setLong(0, term.getId()).executeUpdate();
    }//increaseViewCount()


    private static final String hql_decreaseHighlightCount = "update Term set highlightCount=highlightCount-1 where id=?";
    
    
    public void decreaseHighlightCount(Term term) throws Exception {
        getSession().createQuery(hql_decreaseHighlightCount).setLong(0, term.getId()).executeUpdate();
    }//decreaseHighlightCount()


    private static final String hql_increaseHighlightCount = "update Term set highlightCount=highlightCount+1 where id=?";
    
    
    public void increaseHighlightCount(Term term) throws Exception {
        getSession().createQuery(hql_increaseHighlightCount).setLong(0, term.getId()).executeUpdate();
    }//increaseHighlightCount()


	@Override
	public TermComment getCommentById(Long id) throws Exception {
		return (TermComment) load(TermComment.class, id);
	}//getCommentById()


}//class GlossaryDAOImpl
