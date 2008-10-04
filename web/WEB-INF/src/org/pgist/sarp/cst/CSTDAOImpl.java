package org.pgist.sarp.cst;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.hibernate.Query;
import org.pgist.sarp.bct.TagReference;
import org.pgist.system.BaseDAOImpl;
import org.pgist.tagging.Category;
import org.pgist.tagging.Tag;
import org.pgist.users.User;
import org.pgist.util.DBMetaData;
import org.pgist.util.PageSetting;
import org.pgist.util.WebUtils;


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
    
    
    private static final String hql_getCategoryReferenceByName = "from CategoryReference cr where cr.cstId=? and lower(cr.category.name)=?";
    
    
    public CategoryReference getCategoryReferenceByName(Long cstId, String name) throws Exception {
        List list = getHibernateTemplate().find(hql_getCategoryReferenceByName, new Object[] {
                cstId,
                name.toLowerCase(),
        });
        if (list.size()>0) return (CategoryReference) list.get(0);
        return null;
    }//getCategoryReferenceByName()


    private static final String hql_getTagReferenceByName = "from TagReference tr where tr.bctId=? and lower(tr.tag.name)=?";
    
    
    public TagReference getTagReferenceByName(Long bctId, String name) throws Exception {
        List list = getHibernateTemplate().find(hql_getTagReferenceByName, new Object[] {
                bctId,
                name.toLowerCase(),
        });
        if (list.size()>0) return (TagReference) list.get(0);
        return null;
    }//getTagReferenceByName()


    private static final String getTagByName = "from Tag t where lower(t.name)=?";
    
    
    @Override
    public Tag getTagByName(String name) throws Exception {
        List list = getHibernateTemplate().find(getTagByName, new Object[] {
                name.toLowerCase(),
        });
        if (list.size()>0) return (Tag) list.get(0);
        return null;
    }


    private static final String hql_getConcernsByTag1 = "select count(distinct c) from Concern c inner join c.tags as tr where c.bct.id=? and c.deleted=? and tr.id=?";
    private static final String hql_getConcernsByTag2 = "select distinct c from Concern c inner join c.tags as tr where c.bct.id=? and c.deleted=? and tr.id=? order by c.id";
    
    
    public Collection getConcernsByTag(Long bctId, Long tagRefId, PageSetting setting) throws Exception {
        List result = new ArrayList();
        
        List list = getHibernateTemplate().find(hql_getConcernsByTag1, new Object[] {
                bctId,
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
        query.setLong(0, bctId);
        query.setBoolean(1, false);
        query.setLong(2, tagRefId);
        
        return query.list();
    }//getConcernsByTag()
    
    
    private static final String hql_getConcernsByTags1 = "select count(distinct c) from Concern c inner join c.tags as tr where c.bct.id=? and c.deleted=? and tr.id in (##)";
    
    private static final String hql_getConcernsByTags2 = "select distinct c from Concern c inner join c.tags as tr where c.bct.id=? and c.deleted=? and tr.id in (##) order by c.id";
    
    
    public Collection getConcernsByTags(Long bctId, int[] tagIds, PageSetting setting) throws Exception {
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
                bctId,
                false,
        });
        
        int count = ((Number) list.get(0)).intValue();
        setting.setRowSize(count);
        
        if (count==0) return new ArrayList();
        
        /*
         * query for results
         */
        Query query = getSession().createQuery(hql_getConcernsByTags2.replace("##", sb.toString()));
        query.setLong(0, bctId);
        query.setBoolean(1, false);
        
        query.setFirstResult(setting.getFirstRow());
        if (setting.getRowOfPage()>0) query.setMaxResults(setting.getRowOfPage());
        
        return query.list();
    }//getConcernsByTags()


    private static final String hql_getRealtedTags1 = "select cr.tags.size from CategoryReference cr where cr.id=?";
    
    private static final String hql_getRealtedTags2 = "select tr from CategoryReference cr inner join cr.tags as tr where cr.id=? order by tr.tag.name";
    
    
    public Collection getRealtedTags(Long bctId, Long categoryId, PageSetting setting) throws Exception {
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
        "select count(distinct tr.id) from CategoryReference cr inner join cr.tags as tr where cr.id in (##) and tr.id not in (select tr.id from CategoryReference cr inner join cr.tags as tr where cr.id=?)";
    
    
    private static final String hql_getUnrelatedTags2 =
        "select distinct tr, tr.tag.name as name from CategoryReference cr inner join cr.tags as tr where cr.id in (##) and tr.id not in (select tr.id from CategoryReference cr inner join cr.tags as tr where cr.id=?) order by name";
    
    
    /**
     * get tag references which are not related to the given categoryId, and also not the orphan tags.
     * 
     * @param bctId
     * @param categoryId
     * @param setting
     * @return
     * @throws Exception
     * 
     */
    public Collection getUnrelatedTags(Long cstId, Long categoryId, PageSetting setting) throws Exception {
        CST cst = (CST) load(CST.class, cstId);
        
        CategoryReference focus = getCategoryReferenceById(categoryId);
        CategoryReference focusRoot = focus;
        while (focusRoot.getParents().size()>0) focusRoot = focusRoot.getParents().iterator().next();
        
        CategoryReference root = null;
        
        if (cst.getWinnerCategory()!=null && focusRoot==cst.getWinnerCategory().getCatRef()) {
            root = focusRoot;
        } else {
            root = cst.getCategories().get(WebUtils.currentUserId());
            if (root==null) {
                root = cst.getCats().get(WebUtils.currentUserId());
            }
        }
        
        StringBuilder sb = new StringBuilder();
        Queue<CategoryReference> queue = new LinkedList<CategoryReference>();
        queue.add(root);
        while (!queue.isEmpty()) {
            CategoryReference catRef = queue.remove();
            if (catRef.getChildren().size()>0) queue.addAll(catRef.getChildren());
            sb.append(catRef.getId());
            if (!queue.isEmpty()) sb.append(",");
        }
        
        List results = new ArrayList();
        
        List list = getHibernateTemplate().find(hql_getUnrelatedTags1.replace("##", sb.toString()), new Object[] { categoryId });
        
        if (list.size()==0) return results;
        
        int count = ((Number) list.get(0)).intValue();
        if (setting.getRowOfPage()==-1) setting.setRowOfPage(count);
        setting.setRowSize(count);
        
        Query query = getSession().createQuery(hql_getUnrelatedTags2.replace("##", sb.toString()));
        query.setLong(0, categoryId);
        query.setMaxResults(setting.getRowOfPage());
        query.setFirstResult(setting.getFirstRow());
        
        list = query.list();
        
        for (Object[] array : (List<Object[]>) list) {
            results.add(array[0]);
        }
        
        return results;
    }//getUnrelatedTags()


    private static final String hql_getOrphanTags1 = "select count(tr.id) from TagReference tr where tr.times>0 and tr.bctId=? and tr.id not in (select tr.id from CategoryReference cr inner join cr.tags as tr where cr.id in (##))";
    
    
    private static final String hql_getOrphanTags2 = "select distinct tr, tr.tag.name as name from TagReference tr where tr.times>0 and  tr.bctId=? and tr.id not in (select tr.id from CategoryReference cr inner join cr.tags as tr where cr.id in (##)) order by name";
    
    
    public Collection getOrphanTags(Long cstId, PageSetting setting, boolean modtool) throws Exception {
        CST cst = (CST) load(CST.class, cstId);
        
        if (modtool) {
            return getOrphanTags(cstId, cst.getWinnerCategory().getCatRef().getId(), setting);
        } else {
            CategoryReference root = cst.getCategories().get(WebUtils.currentUserId());
            if (root==null) {
                root = cst.getCats().get(WebUtils.currentUserId());
            }
            return getOrphanTags(cstId, root.getId(), setting);
        }
    }
    
    
    public Collection getOrphanTags(Long cstId, Long categoryId, PageSetting setting) throws Exception {
    	CST cst = (CST) load(CST.class, cstId);
    	Long bctId = cst.getBct().getId();
    	
    	//get IN string
        CategoryReference focusRoot = getCategoryReferenceById(categoryId);
        while (focusRoot.getParents().size()>0) focusRoot = focusRoot.getParents().iterator().next();
        
        CategoryReference root = null;
        
        if (cst.getWinnerCategory()!=null && focusRoot==cst.getWinnerCategory().getCatRef()) {
            root = focusRoot;
        } else {
            root = cst.getCategories().get(WebUtils.currentUserId());
            if (root==null) {
                root = cst.getCats().get(WebUtils.currentUserId());
            }
        }
        
        StringBuilder sb = new StringBuilder();
        Queue<CategoryReference> queue = new LinkedList<CategoryReference>();
        queue.add(root);
        while (!queue.isEmpty()) {
            CategoryReference catRef = queue.remove();
            if (catRef.getChildren().size()>0) queue.addAll(catRef.getChildren());
            sb.append(catRef.getId());
            if (!queue.isEmpty()) sb.append(",");
        }
        
        List results = new ArrayList();
        
        List list = getHibernateTemplate().find(hql_getOrphanTags1.replace("##", sb.toString()), new Object[] { bctId });
        
        int count = ((Number) list.get(0)).intValue();
        if (count==0) return new ArrayList();
        
        if (setting.getRowOfPage()<1) setting.setRowOfPage(count);
        setting.setRowSize(count);
        
        Query query = getSession().createQuery(hql_getOrphanTags2.replace("##", sb.toString()));
        query.setLong(0, bctId);
        query.setMaxResults(setting.getRowOfPage());
        query.setFirstResult(setting.getFirstRow());
        
        list = query.list();
        
        for (Object[] array : (List<Object[]>) list) {
            results.add(array[0]);
        }
        
        return results;
    }//getOrphanTags()


    public void delete(CategoryReference ref) throws Exception {
        getHibernateTemplate().delete(ref);
    }//delete()

    
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


	@Override
	public CST getCSTById(Long cstId) throws Exception {
		return (CST) load(CST.class, cstId);
	}//getCSTById()


    private static final String hql_getOtherUsers = "from User u where u.id in (select indices(cst.categories) from CST cst where cst.id=?) and u.id<>? order by u.loginname";
    
    
    @Override
    public List<User> getOtherUsers(CST cst) throws Exception {
        return getHibernateTemplate().find(hql_getOtherUsers, new Object[] {
                cst.getId(),
                WebUtils.currentUserId(),
        });
    }//getOtherUsers()


    private static final String hql_getComments1 = "select count(id) from CSTComment c where c.deleted=false and c.catRef.id=?";
    private static final String hql_getComments2 = "from CSTComment c where c.deleted=false and c.catRef.id=? order by c.id desc";
    
    
    @Override
    public Collection<CSTComment> getComments(Long catRefId, PageSetting setting) throws Exception {
        List<CSTComment> list = new ArrayList<CSTComment>();
        
        //get total rows number
        Query query = getSession().createQuery(hql_getComments1);
        query.setLong(0, catRefId);
        int count = ((Number) query.uniqueResult()).intValue();
        
        if (count==0) return list;
        
        if (setting.getRowOfPage()==-1) setting.setRowOfPage(count);
        setting.setRowSize(count);
        
        //get records
        query = getSession().createQuery(hql_getComments2);
        query.setLong(0, catRefId);
        query.setFirstResult(setting.getFirstRow());
        query.setMaxResults(setting.getRowOfPage());
        
        return query.list();
    }//getComments()


    private static final String hql_increaseVoting_21 = "update CSTComment c set c.numVote=c.numVote+1 where c.id=?";
    
    private static final String hql_increaseVoting_22 = "update CSTComment c set c.numAgree=c.numAgree+1 where c.id=?";
    
    
    @Override
    public void increaseVoting(CSTComment comment, boolean agree) throws Exception {
        getSession().createQuery(hql_increaseVoting_21).setLong(0, comment.getId()).executeUpdate();
        if (agree) {
            getSession().createQuery(hql_increaseVoting_22).setLong(0, comment.getId()).executeUpdate();
        }
    }//increaseVoting()


}//class CSTDAOImpl
