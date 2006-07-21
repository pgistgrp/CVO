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
                name.toLowerCase()
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


    private static final String hql_getUnrelatedTags1 =
         "select count(tr.id) from TagReference tr where "
       + " tr.cctId=? "
       + " and tr.id not in (select distinct tag.id from TagReference tag, CategoryReference cr where cr.cct.id=? and tag.id in cr.tags.id) "
       + " and tr.tag.id not in (select cr.tags.id from CategoryReference cr where cr.id=?) ";
    
    
    private static final String hql_getUnrelatedTags2 =
        "from TagReference tr where "
       + " tr.cctId=? "
       + " and tr.id not in (select distinct tag.id from TagReference tag, CategoryReference cr where cr.cct.id=? and tag.id in cr.tags.id) "
       + " and tr.tag.id not in (select cr.tags.id from CategoryReference cr where cr.id=?) "
       + " order by tr.tag.name";
    
    
    /**
     * get tag references which are not related to the given categoryId, and also not the orphan tags.
     * 
     * @param cctId
     * @param categoryId
     * @param setting
     * @return
     * @throws Exception
     * 
     */
    public Collection getUnrelatedTags(Long cctId, Long categoryId, PageSetting setting) throws Exception {
        List list = getHibernateTemplate().find(hql_getUnrelatedTags1, new Object[] {
                cctId,
                cctId,
                categoryId,
        });
        
        if (list.size()==0) return new ArrayList();
        
        int count = ((Integer) list.get(0)).intValue();
        if (setting.getRowOfPage()==-1) setting.setRowOfPage(count);
        setting.setRowSize(count);
        
        Query query = getSession().createQuery(hql_getUnrelatedTags2);
        query.setLong(0, cctId);
        query.setLong(1, cctId);
        query.setLong(2, categoryId);
        query.setMaxResults(setting.getRowOfPage());
        query.setFirstResult(setting.getFirstRow());
        
        return query.list();
    }//getUnrelatedTags()


    private static final String hql_getOrphanTags1 = "select count(distinct ref.id) from TagReference ref where ref.cctId=? and ref.id not in "
        + "(select distinct tag from TagReference tag, CategoryReference cr where cr.cct.id=? and tag.id in cr.tags.id) ";
    private static final String hql_getOrphanTags2 = "select distinct ref, ref.tag.name from TagReference ref where ref.cctId=? and ref.id not in "
        + "(select distinct tag from TagReference tag, CategoryReference cr where cr.cct.id=? and tag.id in cr.tags.id) "
        + "order by ref.tag.name asc";
    
    
    public Collection getOrphanTags(Long cctId, PageSetting setting) throws Exception {
        List list = getHibernateTemplate().find(hql_getOrphanTags1, new Object[] {
                cctId,
                cctId,
        });
        
        int count = ((Integer) list.get(0)).intValue();
        if (count==0) return new ArrayList();
        
        if (setting.getRowOfPage()<1) setting.setRowOfPage(count);
        setting.setRowSize(count);
        
        Query query = getSession().createQuery(hql_getOrphanTags2);
        query.setLong(0, cctId);
        query.setLong(1, cctId);
        query.setMaxResults(setting.getRowOfPage());
        query.setFirstResult(setting.getFirstRow());
        
        List tmp = query.list();
        
        if (tmp.size()==0) return new ArrayList();
        
        list = new ArrayList(tmp.size());
        for (Object[] objs : (List<Object[]>) tmp) {
            list.add(objs[0]);
        }//for
        
        return list;
    }//getOrphanTags()


    public Theme getThemeById(Long themeId) throws Exception {
        return (Theme) getHibernateTemplate().load(Theme.class, themeId);
    }//getThemeById()


    public void save(Theme theme) throws Exception {
        getHibernateTemplate().saveOrUpdate(theme);
    }//save()


    public void delete(CategoryReference ref) throws Exception {
        getHibernateTemplate().delete(ref);
    }//delete()


}//class CSTDAOImpl
