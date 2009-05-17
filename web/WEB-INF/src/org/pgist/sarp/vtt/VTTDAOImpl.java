package org.pgist.sarp.vtt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.pgist.sarp.cht.CategoryPath;
import org.pgist.system.BaseDAOImpl;
import org.pgist.users.User;
import org.pgist.util.PageSetting;
import org.pgist.util.WebUtils;


/**
 * 
 * @author kenny
 *
 */
public class VTTDAOImpl extends BaseDAOImpl implements VTTDAO {
    
    
    @Override
    public VTT getVTTById(Long vttId) throws Exception {
        return (VTT) load(VTT.class, vttId);
    } //getVTTById()
    
    
    private static final String hql_getComments1 = "select count(id) from VTTComment c where c.deleted=false and c.vtt.id=? and c.owner.id=?";
    private static final String hql_getComments2 = "from VTTComment c where c.deleted=false and c.vtt.id=? and c.owner.id=? order by c.id desc";
    
    
    @Override
    public Collection<VTTComment> getComments(Long userId, Long vttId, PageSetting setting) throws Exception {
        List<VTTComment> list = new ArrayList<VTTComment>();
        
        //get total rows number
        Query query = getSession().createQuery(hql_getComments1);
        query.setLong(0, vttId);
        query.setLong(1, userId);
        int count = ((Number) query.uniqueResult()).intValue();
        
        if (count==0) return list;
        
        if (setting.getRowOfPage()==-1) setting.setRowOfPage(count);
        setting.setRowSize(count);
        
        //get records
        query = getSession().createQuery(hql_getComments2);
        query.setLong(0, vttId);
        query.setLong(1, userId);
        query.setFirstResult(setting.getFirstRow());
        query.setMaxResults(setting.getRowOfPage());
        
        return query.list();
    } //getComments()


    private static final String hql_increaseVoting_21 = "update GenericComment c set c.numVote=c.numVote+1 where c.id=?";
    
    private static final String hql_increaseVoting_22 = "update GenericComment c set c.numAgree=c.numAgree+1 where c.id=?";
    
    
    @Override
    public void increaseVoting(VTTComment comment, boolean agree) throws Exception {
        getSession().createQuery(hql_increaseVoting_21).setLong(0, comment.getId()).executeUpdate();
        if (agree) {
            getSession().createQuery(hql_increaseVoting_22).setLong(0, comment.getId()).executeUpdate();
        }
    } //increaseVoting()


    private static final String hql_getOtherUsers = "from User u where u.id in (select indices(vtt.categories) from VTT vtt where vtt.id=?) and u.id<>? order by u.loginname";
    
    
    @Override
    public List<User> getOtherUsers(VTT vtt) throws Exception {
        return getHibernateTemplate().find(hql_getOtherUsers, new Object[] {
                vtt.getId(),
                WebUtils.currentUserId(),
        });
    } //getOtherUsers()


    private static final String hql_getCategoryPathValueByPathId = "from CategoryPathValue pv where pv.path.id=? and pv.user.id=?";
    
    @Override
    public CategoryPathValue getCategoryPathValueByPathId(Long userId, Long pathId) throws Exception {
        Query query = getSession().createQuery(hql_getCategoryPathValueByPathId);
        query.setLong(0, pathId);
        query.setLong(1, userId);
        return (CategoryPathValue) query.uniqueResult();
    } //getCategoryValueById()

    
    private static final String hql_getCategoryPathValuesByPathId = "from CategoryPathValue pv where pv.path.id=?";
    
    @Override
    public List<CategoryPathValue> getCategoryPathValuesByPathId(Long id) throws Exception {
        Query query = getSession().createQuery(hql_getCategoryPathValuesByPathId);
        query.setLong(0, id);
        return query.list();
    } //getCategoryPathValuesByPathId()

    
    private static final String hql_getMUnitSetByPathId = "from MUnitSet m where m.path.id=?";
    
    @Override
    public List<MUnitSet> getMUnitSetsByPathId(Long pathId) throws Exception {
        Query query = getSession().createQuery(hql_getMUnitSetByPathId);
        query.setLong(0, pathId);
        return (List<MUnitSet>) query.list();
    } //getMUnitSetByPathId()

    
    private static final String hql_getSpecialistComments1 = "select count(id) from VTTSpecialistComment c where c.deleted=false and c.vtt.id=? and c.owner.id=?";
    private static final String hql_getSpecialistComments2 = "from VTTSpecialistComment c where c.deleted=false and c.vtt.id=? and c.owner.id=? order by c.id desc";
    
    
    @Override
    public Collection<VTTSpecialistComment> getSpecialistComments(Long targetUserId, Long vttId, PageSetting setting) throws Exception {
        List<VTTSpecialistComment> list = new ArrayList<VTTSpecialistComment>();
        
        //get total rows number
        Query query = getSession().createQuery(hql_getSpecialistComments1);
        query.setLong(0, vttId);
        query.setLong(1, targetUserId);
        int count = ((Number) query.uniqueResult()).intValue();
        
        if (count==0) return list;
        
        if (setting.getRowOfPage()==-1) setting.setRowOfPage(count);
        setting.setRowSize(count);
        
        //get records
        query = getSession().createQuery(hql_getSpecialistComments2);
        query.setLong(0, vttId);
        query.setLong(1, targetUserId);
        query.setFirstResult(setting.getFirstRow());
        query.setMaxResults(setting.getRowOfPage());
        
        return query.list();
    }
    
    
    private static final String hql_increaseSpecialistVoting_21 = "update GenericComment c set c.numVote=c.numVote+1 where c.id=?";
    
    private static final String hql_increaseSpecialistVoting_22 = "update GenericComment c set c.numAgree=c.numAgree+1 where c.id=?";
    
    
    @Override
    public void increaseSpecialistVoting(VTTSpecialistComment comment, boolean agree) throws Exception {
        getSession().createQuery(hql_increaseSpecialistVoting_21).setLong(0, comment.getId()).executeUpdate();
        if (agree) {
            getSession().createQuery(hql_increaseSpecialistVoting_22).setLong(0, comment.getId()).executeUpdate();
        }
    } //increaseSpecialistVoting()


    @Override
    public MUnitSet getMUnitSetById(Long musetId) throws Exception {
        return (MUnitSet) load(MUnitSet.class, musetId);
    } //getMUnitSetById()


    private static final String hql_checkPath = "select cp from VTT vtt join vtt.paths cp where vtt.id=? and cp.title=?";
    
    
    @Override
    public boolean checkPath(Long vttId, String title) throws Exception {
        Query query = getSession().createQuery(hql_checkPath);
        query.setLong(0, vttId);
        query.setString(1, title);
        
        CategoryPath path = (CategoryPath) query.uniqueResult();
        
        return path!=null;
    }


    @Override
    public CategoryPath getCategoryPathById(Long pathId) throws Exception {
        return (CategoryPath) getHibernateTemplate().load(CategoryPath.class, pathId);
    }


    @Override
    public void delete(Object object) throws Exception {
        getHibernateTemplate().delete(object);
    }


    private static final String hql_getThreadUsers = "select c.author as a from VTTComment c where c.vtt.id=? and c.owner.id=?";


    @Override
    public Set<User> getThreadUsers(Long ownerId, Long vttId) throws Exception {
        Set<User> users = new HashSet<User>();
        users.addAll(getHibernateTemplate().find(hql_getThreadUsers, new Object[] {vttId, ownerId}));
        return users;
    }


    private static final String hql_getExpertPathComment = "from ExpertPathComment e where e.path.id=? and e.owner.id=?";
    
    
    @Override
    public ExpertPathComment getExpertPathComment(Long pathId, Long userId) throws Exception {
        List<ExpertPathComment> results = getHibernateTemplate().find(hql_getExpertPathComment, new Object[] {pathId, userId});
        
        if (results.size()>0) return results.get(0);
        
        return null;
    }


    @Override
    public VTTComment getCommentById(Long commentId) throws Exception {
        return (VTTComment) load(VTTComment.class, commentId);
    }

    
} //class VTTDAOImpl
