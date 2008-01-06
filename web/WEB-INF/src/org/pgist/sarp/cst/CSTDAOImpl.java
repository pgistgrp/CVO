package org.pgist.sarp.cst;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import org.hibernate.Query;
import org.pgist.discussion.InfoObject;
import org.pgist.discussion.InfoStructure;
import org.pgist.sarp.bct.CategoryReference;
import org.pgist.sarp.bct.TagReference;
import org.pgist.sarp.bct.Theme;
import org.pgist.system.BaseDAOImpl;
import org.pgist.tagging.Category;
import org.pgist.util.DBMetaData;
import org.pgist.util.PageSetting;


/**
 * 
 * @author kenny
 *
 */
public class CSTDAOImpl extends BaseDAOImpl implements CSTDAO {
    
    
    public TagReference getTagReferenceById(Long tagRefId) throws Exception {
        return (TagReference) load(TagReference.class, tagRefId);
    }//getTagReferenceById()


    public CategoryReference getCategoryReferenceById(Long categoryId) throws Exception {
        return (CategoryReference) load(CategoryReference.class, categoryId);
    }//getCategoryReferenceById()


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


    private static final String hql_getConcernsByTag1 = "select count(distinct c) from Concern c inner join c.tags as tr where c.cct.id=? and c.deleted=? and tr.id=?";
    private static final String hql_getConcernsByTag2 = "select distinct c from Concern c inner join c.tags as tr where c.cct.id=? and c.deleted=? and tr.id=? order by c.id";
    
    
    public Collection getConcernsByTag(Long cctId, Long tagRefId, PageSetting setting) throws Exception {
        List result = new ArrayList();
        
        List list = getHibernateTemplate().find(hql_getConcernsByTag1, new Object[] {
                cctId,
                new Boolean(false),
                tagRefId
        });
        if (list==null || list.size()==0) return result;
        
        int total = ((Number) list.get(0)).intValue();
        if (setting.getRowOfPage()==-1) setting.setRowOfPage(total);
        setting.setRowSize(total);
        
        Query query = getSession().createQuery(hql_getConcernsByTag2);
        query.setFirstResult(setting.getFirstRow());
        query.setMaxResults(setting.getRowOfPage());
        query.setLong(0, cctId);
        query.setBoolean(1, false);
        query.setLong(2, tagRefId);
        
        return query.list();
    }//getConcernsByTag()
    
    
    private static final String hql_getConcernsByTags1 = "select count(distinct c) from Concern c inner join c.tags as tr where c.cct.id=? and c.deleted=? and tr.id in (##)";
    
    private static final String hql_getConcernsByTags2 = "select distinct c from Concern c inner join c.tags as tr where c.cct.id=? and c.deleted=? and tr.id in (##) order by c.id";
    
    
    public Collection getConcernsByTags(Long cctId, int[] tagIds, PageSetting setting) throws Exception {
        StringBuffer sb = new StringBuffer();
        boolean first = true;
        for (int id : tagIds) {
            if (first) {
                first = false;
            } else {
                sb.append(',');
            }
            sb.append(id);
        }//for
        
        /*
         * query for count
         */
        List list = getHibernateTemplate().find(hql_getConcernsByTags1.replace("##", sb.toString()), new Object[] {
                cctId,
                false,
        });
        
        int count = ((Number) list.get(0)).intValue();
        setting.setRowSize(count);
        
        if (count==0) return new ArrayList();
        
        /*
         * query for results
         */
        Query query = getSession().createQuery(hql_getConcernsByTags2.replace("##", sb.toString()));
        query.setLong(0, cctId);
        query.setBoolean(1, false);
        
        query.setFirstResult(setting.getFirstRow());
        if (setting.getRowOfPage()>0) query.setMaxResults(setting.getRowOfPage());
        
        return query.list();
    }//getConcernsByTags()


    private static final String hql_getRealtedTags1 = "select cr.tags.size from CategoryReference cr where cr.id=?";
    
    private static final String hql_getRealtedTags2 = "select tr from CategoryReference cr inner join cr.tags as tr where cr.id=? order by tr.tag.name";
    
    
    public Collection getRealtedTags(Long cctId, Long categoryId, PageSetting setting) throws Exception {
        List list = getHibernateTemplate().find(hql_getRealtedTags1, categoryId);
        if (list.size()==0) return new ArrayList();
        
        int count = ((Number) list.get(0)).intValue();
        if (setting.getRowOfPage()==-1) setting.setRowOfPage(count);
        setting.setRowSize(count);
        
        Query query = getSession().createQuery(hql_getRealtedTags2);
        query.setLong(0, categoryId);
        query.setMaxResults(setting.getRowOfPage());
        query.setFirstResult(setting.getFirstRow());
        
        return query.list();
    }//getRealtedTags()


    private static final String hql_getUnrelatedTags1 =
        "select count(distinct tr.id) from CategoryReference cr inner join cr.tags as tr where cr.cct.id=? and tr.id not in (select tr.id from CategoryReference cr inner join cr.tags as tr where cr.id=?)";
    
    
    private static final String hql_getUnrelatedTags2 =
        "select distinct tr, tr.tag.name as name from CategoryReference cr inner join cr.tags as tr where cr.cct.id=? and tr.id not in (select tr.id from CategoryReference cr inner join cr.tags as tr where cr.id=?) order by name";
    
    
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
        List results = new ArrayList();
        
        List list = getHibernateTemplate().find(hql_getUnrelatedTags1, new Object[] {
                cctId,
                categoryId,
        });
        
        if (list.size()==0) return results;
        
        int count = ((Number) list.get(0)).intValue();
        if (setting.getRowOfPage()==-1) setting.setRowOfPage(count);
        setting.setRowSize(count);
        
        Query query = getSession().createQuery(hql_getUnrelatedTags2);
        query.setLong(0, cctId);
        query.setLong(1, categoryId);
        query.setMaxResults(setting.getRowOfPage());
        query.setFirstResult(setting.getFirstRow());
        
        list = query.list();
        
        for (Object[] array : (List<Object[]>) list) {
            results.add(array[0]);
        }
        
        return results;
    }//getUnrelatedTags()


    private static final String hql_getOrphanTags1 = "select count(tr.id) from TagReference tr where tr.cctId=? and tr.id not in (select tr.id from CategoryReference cr inner join cr.tags as tr where cr.cct.id=?)";
    
    
    private static final String hql_getOrphanTags2 = "select distinct tr, tr.tag.name as name from TagReference tr where tr.cctId=? and tr.id not in (select tr.id from CategoryReference cr inner join cr.tags as tr where cr.cct.id=?) order by name";
    
    
    public Collection getOrphanTags(Long cctId, PageSetting setting) throws Exception {
        List results = new ArrayList();
        
        List list = getHibernateTemplate().find(hql_getOrphanTags1, new Object[] {
                cctId,
                cctId,
        });
        
        int count = ((Number) list.get(0)).intValue();
        if (count==0) return new ArrayList();
        
        if (setting.getRowOfPage()<1) setting.setRowOfPage(count);
        setting.setRowSize(count);
        
        Query query = getSession().createQuery(hql_getOrphanTags2);
        query.setLong(0, cctId);
        query.setLong(1, cctId);
        query.setMaxResults(setting.getRowOfPage());
        query.setFirstResult(setting.getFirstRow());
        
        list = query.list();
        
        for (Object[] array : (List<Object[]>) list) {
            results.add(array[0]);
        }
        
        return results;
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

    
    private static final String sql_publish = "INSERT INTO "+DBMetaData.TABLE_CAT_TAG_IN_CST+" (cctid,isid,ioid,crid,trid) VALUES (?,?,?,?,?)";
    

    public void publish(InfoStructure structure, InfoObject obj, CategoryReference ref) throws Exception {
        long cctid = structure.getCctId();
        long isid = structure.getId();
        long ioid = obj.getId();
        long crid = ref.getId();
        
        Connection connection = getSession().connection();
        PreparedStatement pstmt = connection.prepareStatement(sql_publish);
        
        pstmt.setLong(1, cctid);
        pstmt.setLong(2, isid);
        pstmt.setLong(3, ioid);
        pstmt.setLong(4, crid);
        
        Queue<CategoryReference> queue = new LinkedList<CategoryReference>();
        queue.add(ref);
        
        while (!queue.isEmpty()) {
            CategoryReference one = queue.poll();
            for (CategoryReference two : (Set<CategoryReference>) one.getChildren()) {
                queue.offer(two);
            }//for two
            
            for (TagReference two : (Set<TagReference>) one.getTags()) {
                pstmt.setLong(5, two.getId());
                pstmt.executeUpdate();
            }//for two
        }//while
    }//publish()


    private static final String hql_getInfoObjectIdByThemeId = "select cr.id from CategoryReference cr where cr.theme.id=?";
    
    private static final String sql_getInfoObjectIdByThemeId = "select ioid from "+DBMetaData.TABLE_CAT_TAG_IN_CST+" where crid=";
    
    
    public Long getInfoObjectIdByThemeId(Long themeId) throws Exception {
        Query query = null;
        
        query = getSession().createQuery(hql_getInfoObjectIdByThemeId);
        
        query.setLong(0, themeId);
        query.setMaxResults(1);
        
        Long id = ((Number) query.uniqueResult()).longValue();
        
        Connection connection = getSession().connection();
        Statement stmt = connection.createStatement();
        
        ResultSet rs = stmt.executeQuery(sql_getInfoObjectIdByThemeId+id);
        
        if (rs.next()) {
            return rs.getLong(1);
        }
        
        return null;
    }//getInfoObjectIdByThemeId()


}//class CSTDAOImpl
