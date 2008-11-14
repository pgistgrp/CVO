package org.pgist.sarp.cht;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.Query;
import org.pgist.sarp.bct.TagReference;
import org.pgist.sarp.cst.CategoryReference;
import org.pgist.system.BaseDAOImpl;
import org.pgist.users.User;
import org.pgist.util.PageSetting;
import org.pgist.util.WebUtils;


/**
 * 
 * @author kenny
 *
 */
public class CHTDAOImpl extends BaseDAOImpl implements CHTDAO {
    
    
    public TagReference getTagReferenceById(Long tagRefId) throws Exception {
        return (TagReference) load(TagReference.class, tagRefId);
    }//getTagReferenceById()


    public CategoryReference getCategoryReferenceById(Long categoryId) throws Exception {
        return (CategoryReference) load(CategoryReference.class, categoryId);
    }//getCategoryReferenceById()


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


	@Override
	public CHT getCHTById(Long chtId) throws Exception {
		return (CHT) load(CHT.class, chtId);
	}//getCSTById()


    private static final String hql_getOtherUsers = "from User u where u.id in (select indices(cht.categories) from CHT cht where cht.id=?) and u.id<>? order by u.loginname";
    
    
    @Override
    public List<User> getOtherUsers(CHT cht) throws Exception {
        return getHibernateTemplate().find(hql_getOtherUsers, new Object[] {
                cht.getId(),
                WebUtils.currentUserId(),
        });
    }//getOtherUsers()


    private static final String hql_getComments1 = "select count(id) from CHTComment c where c.deleted=false and c.catRef.id=?";
    private static final String hql_getComments2 = "from CHTComment c where c.deleted=false and c.catRef.id=? order by c.id desc";
    
    
    @Override
    public Collection<CHTComment> getComments(Long catRefId, PageSetting setting) throws Exception {
        List<CHTComment> list = new ArrayList<CHTComment>();
        
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


    private static final String hql_increaseVoting_21 = "update GenericComment c set c.numVote=c.numVote+1 where c.id=?";
    
    private static final String hql_increaseVoting_22 = "update GenericComment c set c.numAgree=c.numAgree+1 where c.id=?";
    
    
    @Override
    public void increaseVoting(CHTComment comment, boolean agree) throws Exception {
        getSession().createQuery(hql_increaseVoting_21).setLong(0, comment.getId()).executeUpdate();
        if (agree) {
            getSession().createQuery(hql_increaseVoting_22).setLong(0, comment.getId()).executeUpdate();
        }
    }//increaseVoting()


    private static final String hql_getPathsByChtId = "from CategoryPath p where p.cht.id=? order by p.";
    
    @Override
    public List<CategoryPath> getPathsByChtId(Long chtId, String orderby) {
        if (orderby==null || orderby.trim().length()==0) orderby = "frequency desc";
        
        Query query = getSession().createQuery(hql_getPathsByChtId + orderby);
        query.setLong(0, chtId);
        
        return query.list();
    }


    private static final String hql_increaseVoting_31 = "update CategoryPath cp set cp.numVote=cp.numVote+1 where cp.id=?";
    
    private static final String hql_increaseVoting_32 = "update CategoryPath cp set cp.numAgree=cp.numAgree+1 where cp.id=?";
    
    @Override
    public void increaseVoting(CategoryPath path, boolean agree) throws Exception {
        getSession().createQuery(hql_increaseVoting_31).setLong(0, path.getId()).executeUpdate();
        if (agree) {
            getSession().createQuery(hql_increaseVoting_32).setLong(0, path.getId()).executeUpdate();
        }
    } //increaseVoting()


    private static final String hql_checkPath = "from CategoryPath cp where cp.cht.id=? and cp.title=?";
    
    @Override
    public boolean checkPath(Long chtId, String title) throws Exception {
        Query query = getSession().createQuery(hql_checkPath);
        query.setLong(0, chtId);
        query.setString(1, title);
        
        CategoryPath path = (CategoryPath) query.uniqueResult();
        
        return path!=null;
    } //checkPath()


}//class CHTDAOImpl
