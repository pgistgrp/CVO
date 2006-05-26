package org.pgist.cvo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.Query;
import org.pgist.util.PageSetting;


/**
 * 
 * @author kenny
 *
 */
public class CSTDAOImpl extends CVODAOImpl implements CSTDAO {
    
    
    private static final String hql_getCategoryByName = "from Category c where c.deleted=? and lower(c.name)=?";
    
    
    public Category getCategoryByName(String name) throws Exception {
        List list = getHibernateTemplate().find(hql_getCategoryByName, new Object[] {
                new Boolean(false),
                name
        });
        if (list.size()>0) return (Category) list.get(0);
        return null;
    }//getCategoryByName()
    
    
    private static final String hql_getCategoryReferenceByName = "from CategoryReference cr where cr.cct.id=? and lower(cr.category.name)=?";
    
    
    public CategoryReference getCategoryReferenceByName(Long cctId, String name) throws Exception {
        List list = getHibernateTemplate().find(hql_getCategoryReferenceByName, new Object[] {
                cctId,
                name.toLowerCase(),
        });
        if (list.size()>0) return (CategoryReference) list.get(0);
        return null;
    }//getCategoryReferenceByName()


    private static final String hql_getConcernsByTag1 = "select count(c.id) from Concern c where c.deleted=? and c.cct.id=? and c.tags.id=?";
    private static final String hql_getConcernsByTag2 = "from Concern c where c.deleted=? and c.cct.id=? and c.tags.id=?";
    
    
    public Collection getConcernsByTag(Long cctId, Long tagRefId, PageSetting setting) throws Exception {
        List result = new ArrayList();
        
        List list = getHibernateTemplate().find(hql_getConcernsByTag1, new Object[] {
                new Boolean(false),
                cctId,
                tagRefId
        });
        if (list==null || list.size()==0) return result;
        
        int total = ((Integer) list.get(0)).intValue();
        if (setting.getRowOfPage()==-1) setting.setRowOfPage(total);
        setting.setRowSize(total);
        
        Query query = getSession().createQuery(hql_getConcernsByTag2);
        query.setFirstResult(setting.getFirstRow());
        query.setMaxResults(setting.getRowOfPage());
        query.setBoolean(0, false);
        query.setLong(1, cctId);
        query.setLong(2, tagRefId);
        
        return query.list();
    }//getConcernsByTag()
    
    
    private static final String hql_getRealtedTags1 = "select count(cr.tags.id) from CategoryReference cr where cr.id=?";
    
    private static final String hql_getRealtedTags2 = "from TagReference tr where tr.id in (select cr.tags.id from CategoryReference cr where cr.id=?) order by tr.tag.name";
    
    
    public Collection getRealtedTags(Long cctId, Long categoryId, PageSetting setting) throws Exception {
        List list = getHibernateTemplate().find(hql_getRealtedTags1, categoryId);
        if (list.size()==0) return new ArrayList();
        
        int count = ((Integer) list.get(0)).intValue();
        if (setting.getRowOfPage()==-1) setting.setRowOfPage(count);
        setting.setRowSize(count);
        
        Query query = getSession().createQuery(hql_getRealtedTags2);
        query.setLong(0, categoryId);
        query.setMaxResults(setting.getRowOfPage());
        query.setFirstResult(setting.getFirstRow());
        
        return query.list();
    }//getRealtedTags()


    private static final String hql_getUnrelatedTags1 = "select count(cct.tagRefs.id) from CCT cct where cct.tagRefs.id not in (select cr.tags.id from CategoryReference cr where cr.id=?)";
    
    private static final String hql_getUnrelatedTags2 = "from TagReference tr where tr.cctId=? and tr.id not in (select cr.tags.id from CategoryReference cr where cr.id=?) order by tr.tag.name";
    
    
    public Collection getUnrelatedTags(Long cctId, Long categoryId, PageSetting setting) throws Exception {
        List list = getHibernateTemplate().find(hql_getUnrelatedTags1, categoryId);
        if (list.size()==0) return new ArrayList();
        
        int count = ((Integer) list.get(0)).intValue();
        if (setting.getRowOfPage()==-1) setting.setRowOfPage(count);
        setting.setRowSize(count);
        
        Query query = getSession().createQuery(hql_getUnrelatedTags2);
        query.setLong(0, cctId);
        query.setLong(1, categoryId);
        query.setMaxResults(setting.getRowOfPage());
        query.setFirstResult(setting.getFirstRow());
        
        return query.list();
    }//getUnrelatedTags()


}//class CSTDAOImpl
