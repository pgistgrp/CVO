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
    
    
    private static final String hql_getCategoryReferenceByName = "from CategoryReference cr where cr.deleted=? and cr.cctId=? and lower(cr.name)=?";
    
    
    public CategoryReference getCategoryReferenceByName(Long cctId, String name) throws Exception {
        List list = getHibernateTemplate().find(hql_getCategoryReferenceByName, new Object[] {
                new Boolean(false),
                cctId,
                name,
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
    
    
}//class CSTDAOImpl
