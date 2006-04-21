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
public class CCTDAOImpl extends CVODAOImpl implements CCTDAO {
    
    
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
        
        int total = ((Integer) list.get(0)).intValue();
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


    private static final String hql_getConcernByTag = "from Concern c where c.deleted=? and c.tags.id=?";
    
    
    public Collection getConcernsByTag(TagReference tagRef, int count) throws Exception {
        if (count>0) getHibernateTemplate().setMaxResults(count);
        return getHibernateTemplate().find(hql_getConcernByTag, new Object[] {
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
        
        if (list!=null && list.size()>0) return ((Integer)list.get(0)).intValue();
        
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
    
    
}//class CCTDAOImpl
