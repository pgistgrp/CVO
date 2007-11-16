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
    
    
    private static String hql_getTerms_11 = "select count(t.id) from Term t";
    
    private static String hql_getTerms_12 = "from Term t order by t.name";
    
    private static String hql_getTerms_21 = "select count(t.id) from Term t where t.name like ?";
    
    private static String hql_getTerms_22 = "from Term t where t.name like ? order by t.name";
    
    
    public Collection getTerms(PageSetting setting) throws Exception {
        String filter = setting.getFilter();
        
        List list = null;
        
        if (filter==null) {
            list = getHibernateTemplate().find(hql_getTerms_11);
        } else {
            list = getHibernateTemplate().find(hql_getTerms_21, filter+'%');
        }
        
        int count = ((Number) list.get(0)).intValue();
        setting.setRowSize(count);
        if (count==0) return new ArrayList(0);
        
        //display all the terms
        if (setting.getRowOfPage()==-1) setting.setRowOfPage(count);
        
        Query query = null;
        if (filter==null) {
            query = getSession().createQuery(hql_getTerms_12);
        } else {
            query = getSession().createQuery(hql_getTerms_22);
            query.setString(0, filter+'%');
        }
        query.setFirstResult(setting.getFirstRow());
        query.setMaxResults(setting.getRowOfPage());
        
        return query.list();
    }//getTerms()


    public Term getTermById(Long id) throws Exception {
        return (Term) getHibernateTemplate().load(Term.class, id);
    }//getTermById()
    
    
    private static String hql_getTermByName = "from Term t where t.name=?";
    

    public Term getTermByName(String name) throws Exception {
        List list = getHibernateTemplate().find(hql_getTermByName, name);
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
