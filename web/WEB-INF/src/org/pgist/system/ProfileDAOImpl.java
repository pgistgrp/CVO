package org.pgist.system;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.pgist.criteria.Criteria;
import org.pgist.criteria.CriteriaRef;
import org.pgist.criteria.CriteriaSuite;
import org.pgist.criteria.CriteriaUserWeight;
import org.pgist.discussion.DiscussionPost;
import org.pgist.discussion.DiscussionReply;
import org.pgist.sarp.bct.Concern;
import org.pgist.sarp.bct.TagReference;
import org.pgist.tagging.Tag;
import org.pgist.users.User;

public class ProfileDAOImpl extends BaseDAOImpl implements ProfileDAO{

    
    public User getUserInfo(User user) throws Exception {
        
        User u = new User();
        
        u.setCity(user.getCity());
        u.setZipcode(user.getZipcode());
        u.setLoginname(user.getLoginname());
        u.setProfileDesc(user.getProfileDesc());
        u.setPrimaryTransport(user.getPrimaryTransport());
        u.setVocation(user.getVocation());
        u.setWorkCity(user.getWorkCity());
        u.setWorkZipcode(user.getWorkZipcode());
        
        return u;
    }

    
    public boolean setUserInfo(User user, String homecity, String homezipcode, String workcity, String workzipcode, String vocation, String primarytransport, String profiledesc) throws Exception {
        
        if(homecity!=null || !("".equals(homecity.trim()))){
            user.setCity(homecity);
        }
        if(homezipcode!=null || !("".equals(homezipcode.trim()))){
            user.setZipcode(homezipcode);
        }
        if(workzipcode!=null || !("".equals(workzipcode.trim()))){
            user.setWorkZipcode(workzipcode);
        }
        if(workcity!=null || !("".equals(workcity.trim()))){
            user.setWorkCity(workcity);
        }
        if(profiledesc!=null || !("".equals(profiledesc.trim()))){
            user.setProfileDesc(profiledesc);
        }
        if(primarytransport!=null || !("".equals(primarytransport.trim()))){
            Long transId = Long.parseLong(primarytransport);
            RegisterObject transport = (RegisterObject) load(RegisterObject.class, transId);
            user.setPrimaryTransport(transport);
        }
        if(vocation!=null || !("".equals(vocation.trim()))){
            user.setVocation(vocation);
        }
        
        //save(user);
        return true;
    }
    
    
    private static final String hql_getReplies_A = "from DiscussionReply r where r.parent.id=? and r.deleted=? order by r.id";
    
    public Collection getReplies(DiscussionPost post) throws Exception {
        return getHibernateTemplate().find(hql_getReplies_A, new Object[] {
                post.getId(),
                false,
        });
    }//getReplies()
    
    
    private static final String hql_getLastLogin = "from SystemLog sl where sl.userId=? order by sl.time desc";
    
    public Date getLastLogin(Long userId) throws Exception {
        Query query = getSession().createQuery(hql_getLastLogin);
        query.setLong(0, userId);
        query.setMaxResults(1);
        SystemLog sl = (SystemLog) query.uniqueResult();
        Date date = sl.getTime();
        return date;
    }

    
    private static final String hql_getTotalVisits = "select count(sl) from SystemLog sl where sl.url like '%/login.do' and sl.successful=? and sl.userId=?";
    
    public int getTotalVisits(Long userId) throws Exception {
        Query query = getSession().createQuery(hql_getTotalVisits);
        query.setBoolean(0, true);
        query.setLong(1, userId);
        Number count = (Number) query.uniqueResult();
        return count.intValue();
    }
    
    
    private static final String hql_getUserDiscussion1 = "from DiscussionPost dp where dp.deleted=? and dp.owner.loginname=? order by dp.createTime desc";
    private static final String hql_getUserDiscussion2 = "from DiscussionReply dr where dr.deleted=? and dr.owner.loginname=? order by dr.createTime desc";

    public Collection getUserDiscussion(String username, int start, int end) throws Exception {
        List list1 = getHibernateTemplate().find(hql_getUserDiscussion1, new Object[] {new Boolean(false), username,});
        List list2 = getHibernateTemplate().find(hql_getUserDiscussion2, new Object[] {new Boolean(false), username,});
        
        List list = new LinkedList();
        
        boolean bool_list1 = true;
        boolean bool_list2 = true;
        
        if(list1.size() <= 0) {
            bool_list1 = false;
        }
        if(list2.size() <= 0) {
            bool_list2 = false;
        }
        
        if(bool_list1 && !bool_list2) {
            list = list1;
        } else if(!bool_list1 && bool_list2) {
            list = list2;
        } else if(bool_list1 && bool_list2) {
            Iterator itL1 = list1.iterator();
            Iterator itL2 = list2.iterator();
            
            DiscussionPost dp = null;
            DiscussionReply dr = null;
            
            boolean first = true;
            if(itL1.hasNext()){dp = (DiscussionPost) itL1.next();}
            if(itL2.hasNext()){dr = (DiscussionReply) itL2.next();}
            // is there an easy way to sort this with just HQL?
            while(itL1.hasNext() || itL2.hasNext() || first) {
                first = false;
                if(dp==null && dr!=null) {
                    list.add(dr);
                    if(itL2.hasNext()){dr = (DiscussionReply) itL2.next();} else {dr = null;}
                } else if(dr==null && dp!=null) {
                    list.add(dp);
                    if(itL1.hasNext()){dp = (DiscussionPost) itL1.next();}  else {dp = null;}
                } else if(dp.getCreateTime().compareTo(dr.getCreateTime()) > 0) {
                    list.add(dp);
                    if(itL1.hasNext()){dp = (DiscussionPost) itL1.next();} else {dp = null;}
                } else if (dp.getCreateTime().compareTo(dr.getCreateTime()) < 0) {
                    list.add(dr);
                    if(itL2.hasNext()){dr = (DiscussionReply) itL2.next();}  else {dr = null;}
                } else {
                    list.add(dp);
                    list.add(dr);
                    if(itL1.hasNext()){dp = (DiscussionPost) itL1.next();}  else {dp = null;}
                    if(itL2.hasNext()){dr = (DiscussionReply) itL2.next();}  else {dr = null;}
                }
            }
        }
        
        //paging
        if(list.size() < 5 || list == null) {
            
        } else if(start >= 0) {
            if(end > list.size() || start > list.size()) {
                start = list.size() - 5;
                end = list.size();
            }
            list = list.subList(start, end);
        }
        
        return list;
    }
    
    
    public int getPostCount(Long userId) throws Exception {
        Query query = getSession().createQuery("select count(g) from GenericComment g where g.deleted=? and g.author.id=?");
        query.setBoolean(0, false);
        query.setLong(1, userId);
        Number count = (Number) query.uniqueResult();
        return count.intValue();
    }

    
    private static final String hql_getUserByUsername = "from User u where u.loginname=?";
    
    public User getUserByUsername(String username) throws Exception {
        List list = getHibernateTemplate().find(hql_getUserByUsername, new Object[] {username,});
        User u = (User)list.get(0);
        return u;
    }
    
    
    private static final String hql_getUserConcerns = "from Concern c where c.deleted=? and c.author.id=? order by c.createTime desc";

    public Collection getUserConcerns(Long userId) throws Exception {
        return getHibernateTemplate().find(hql_getUserConcerns, new Object[] {new Boolean(false), userId,});
    }
    
    
    private static final String hql_getUserCriteria = "from CriteriaRef cr order by lower(cr.criterion.name)";
    
    public Set getUserCriteria(String username) throws Exception {
        List list = getHibernateTemplate().find(hql_getUserCriteria);
        User user = getUserByUsername(username);
        Set criterias = new LinkedHashSet();
        
        Iterator it = list.iterator();
        while(it.hasNext()){
            CriteriaRef cr = (CriteriaRef) it.next();
            CriteriaSuite cs = cr.getSuite();
            Criteria c = cr.getCriterion();
            
            Map csWeights = cs.getWeights();
            CriteriaUserWeight cuw = (CriteriaUserWeight) csWeights.get(cr);
            Map cuwWeights = cuw.getWeights();
            Object o = cuwWeights.get(user);
            if(o!=null) {
                int cWeight = (Integer) o;
                c.setTempWeight(cWeight);
                criterias.add(c);
            }
        }       
        return criterias;
    }

        
    public String[] getAllTags(Long userId) throws Exception {

        Set allTags = new HashSet();
        Collection concerns = getUserConcerns(userId);
        
        Iterator itConcerns = concerns.iterator();
        while(itConcerns.hasNext()) {
            Concern c = (Concern) itConcerns.next();
            Set cTags = c.getTags();
            Iterator itTags = cTags.iterator();
            while(itTags.hasNext()) {
                TagReference tr = (TagReference) itTags.next();
                Tag t = tr.getTag();
                allTags.add(t.getName());
            }
        }
        
        String[] tags = new String[allTags.size()];
        
        int count = 0;
        Iterator it = allTags.iterator();
        while(it.hasNext()) {
            Object o = it.next();
            String tag = o.toString();
            tags[count] = tag;
            count++;
        }
        
        java.util.Arrays.sort(tags, String.CASE_INSENSITIVE_ORDER);
        
        return tags;
    }
    
    
}
