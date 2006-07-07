package org.pgist.glossary;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.Query;
import org.pgist.util.PageSetting;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


/**
 * 
 * @author kenny
 *
 */
public class GlossaryDAOImpl extends HibernateDaoSupport implements GlossaryDAO {
    
    
    private static String hql_getAllTerms = "from Term t where t.deleted=? order by t.name";
    
    
    public Collection getAllTerms() throws Exception {
        return getHibernateTemplate().find(hql_getAllTerms, false);
    }//getAllTerms()


    private static String hql_getTerms_11 = "select count(t.id) from Term t where t.deleted=?";
    
    private static String hql_getTerms_12 = "from Term t where t.deleted=? order by t.name";
    
    private static String hql_getTerms_21 = "select count(t.id) from Term t where t.deleted=? and t.name like ?";
    
    private static String hql_getTerms_22 = "from Term t where t.deleted=? and t.name like ? order by t.name";
    
    
    public Collection getTerms(PageSetting setting) throws Exception {
        String filter = setting.getFilter();
        
        List list = null;
        
        if (filter==null) {
            list = getHibernateTemplate().find(hql_getTerms_11, false);
        } else {
            list = getHibernateTemplate().find(hql_getTerms_21, new Object[] {
                    false,
                    filter+'%'
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
            query.setString(1, filter+'%');
        }
        query.setFirstResult(setting.getFirstRow());
        query.setMaxResults(setting.getRowOfPage());
        
        return query.list();
    }//getTerms()


    private static String hql_getTermsByName_1 = "from Term t where t.deleted=? order by t.name ";
    
    private static String hql_getTermsByName_2 = "from Term t where t.deleted=? and t.name like ? order by t.name ";
    
    
    public Collection getTermsByName(String filter, boolean ascending) throws Exception {
        String ascend = ascending ? "asc" : "desc";
        
        Query query = null;
        
        if (filter==null || "".equals(filter.trim())) {
            query = getSession().createQuery(hql_getTermsByName_1+ascend);
            query.setBoolean(0, false);
        } else {
            query = getSession().createQuery(hql_getTermsByName_2+ascend);
            query.setBoolean(0, false);
            query.setString(1, '%'+filter+'%');
        }
        
        return query.list();
    }//getTermsByName()


    private static String hql_getTermsByViews_1 = "from Term t where t.deleted=? order by t.initial, t.refCount";
    
    private static String hql_getTermsByViews_2 = "from Term t where t.deleted=? and t.name like ? order by t.initial, t.refCount";
    
    
    public Collection getTermsByViews(String filter, boolean ascending) throws Exception {
        String ascend = ascending ? "asc" : "desc";
        
        Query query = null;
        
        if (filter==null || "".equals(filter.trim())) {
            query = getSession().createQuery(hql_getTermsByViews_1+ascend);
            query.setBoolean(0, false);
        } else {
            query = getSession().createQuery(hql_getTermsByViews_2+ascend);
            query.setBoolean(0, false);
            query.setString(1, '%'+filter+'%');
        }
        
        return query.list();
    }//getTermsByViews()


    private static String hql_getTermsByComments_1 = "from Term t where t.deleted=? order by t.initial, t.commentCount";
    
    private static String hql_getTermsByComments_2 = "from Term t where t.deleted=? and t.name like ? order by t.initial, t.commentCount";
    
    
    public Collection getTermsByComments(String filter, boolean ascending) throws Exception {
        String ascend = ascending ? "asc" : "desc";
        
        Query query = null;
        
        if (filter==null || "".equals(filter.trim())) {
            query = getSession().createQuery(hql_getTermsByComments_1+ascend);
            query.setBoolean(0, false);
        } else {
            query = getSession().createQuery(hql_getTermsByComments_2+ascend);
            query.setBoolean(0, false);
            query.setString(1, '%'+filter+'%');
        }
        
        return query.list();
    }//getTermsByComments()


    private static String hql_getTermsByCreateTime_1 = "from Term t where t.deleted=? order by t.initial, t.createTime";
    
    private static String hql_getTermsByCreateTime_2 = "from Term t where t.deleted=? and t.name like ? order by t.initial, t.createTime";
    
    
    public Collection getTermsByCreateTime(String filter, boolean ascending) throws Exception {
        String ascend = ascending ? "asc" : "desc";
        
        Query query = null;
        
        if (filter==null || "".equals(filter.trim())) {
            query = getSession().createQuery(hql_getTermsByCreateTime_1+ascend);
            query.setBoolean(0, false);
        } else {
            query = getSession().createQuery(hql_getTermsByCreateTime_2+ascend);
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
    
    
    public void saveTerm(Term term) throws Exception {
        getHibernateTemplate().saveOrUpdate(term);
    }//saveTerm()


    private static String hql_getCategoryByName = "from TermCategory tc where tc.name=?";
    

    public TermCategory getCategoryByName(String name) throws Exception {
        List list = getHibernateTemplate().find(hql_getCategoryByName, name);
        if (list.size()==0) return null;
        return (TermCategory) list.get(0);
    }//getCategoryByName()


}//class GlossaryDAOImpl
