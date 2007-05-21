package org.pgist.system;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;

import org.pgist.discussion.DiscussionPost;
import org.pgist.discussion.DiscussionReply;
import org.pgist.discussion.GenericPost;
import org.pgist.users.User;
import org.pgist.util.WebUtils;

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
			user.setPrimaryTransport(primarytransport);
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
    
    
    private static final String hql_getLastLogin = "from SystemLog sl where sl.userId=? order by sl.time";
    
    public Date getLastLogin(String username) {
    	User user = (User) getUserByUsername(username);
    	List list = getHibernateTemplate().find(hql_getLastLogin, new Object[] {user.getId(),});
    	SystemLog sl = (SystemLog) list.get((list.size()-1));
    	Date date = sl.getTime();
    	return date;
    }

    
    //private static final String hql_getTotalVisits = "from SystemLog sl where order by sl.time";
    
    public int getTotalVisits(String username) {
    	User user = (User) getUserByUsername(username);
    	List list = getHibernateTemplate().find(hql_getLastLogin, new Object[] {user.getId(),});
    	Iterator itList = list.iterator();
    	int visits = 0;
    	while(itList.hasNext()) {
    		SystemLog sl = (SystemLog) itList.next();
    		String url = sl.getUrl();
    		if(url.contains("login.do")) {
    			visits++;
    		}
    	}
    	return visits;
    }
    
    
    private static final String hql_getUserDiscussion1 = "from DiscussionPost dp where dp.deleted=? and dp.owner.loginname=? order by dp.createTime desc";
    private static final String hql_getUserDiscussion2 = "from DiscussionReply dr where dr.deleted=? and dr.owner.loginname=? order by dr.createTime desc";

    public Collection getUserDiscussion(String username) {
    	List list1 = getHibernateTemplate().find(hql_getUserDiscussion1, new Object[] {new Boolean(false), username,});
    	List list2 = getHibernateTemplate().find(hql_getUserDiscussion2, new Object[] {new Boolean(false), username,});
    	
    	List list = new LinkedList();
    	
    	int count = 0;
    	
    	Iterator itL1 = list1.iterator();
    	Iterator itL2 = list2.iterator();
    	
    	DiscussionPost dp = null;
    	DiscussionReply dr = null;
    	
    	if(itL1.hasNext()){dp = (DiscussionPost) itL1.next();}
    	if(itL2.hasNext()){dr = (DiscussionReply) itL2.next();}
    	// is there an easy way to do this with just HQL?
    	while(count<6) {
    		if(dp==null && dr!=null) {
    			list.add(dr);
    			if(itL2.hasNext()){dr = (DiscussionReply) itL2.next();} else {dr = null;}
    			count++;
    		} else if(dr==null && dp!=null) {
    			list.add(dp);
    			if(itL1.hasNext()){dp = (DiscussionPost) itL1.next();}  else {dp = null;}
    			count++;
    		} else if(dp==null && dr == null){
    			//count=6;
    			break;
    		} else if(dp.getCreateTime().compareTo(dr.getCreateTime()) > 0) {
    			list.add(dp);
    			if(itL1.hasNext()){dp = (DiscussionPost) itL1.next();} else {dp = null;}
    			count++;
    		} else if (dp.getCreateTime().compareTo(dr.getCreateTime()) < 0) {
    			list.add(dr);
    			if(itL2.hasNext()){dr = (DiscussionReply) itL2.next();}  else {dr = null;}
    			count++;
    		} else {
    			list.add(dp);
    			list.add(dr);
    			if(itL1.hasNext()){dp = (DiscussionPost) itL1.next();}  else {dp = null;}
    			if(itL2.hasNext()){dr = (DiscussionReply) itL2.next();}  else {dr = null;}
    			count+=2;
    		}
    	}
    	
    	return list;
    }
    
    
    public int getPostCount(String username) {
    	//User u = getUserByUsername(username);
    	List list1 = getHibernateTemplate().find(hql_getUserDiscussion1, new Object[] {new Boolean(false), username,});
    	List list2 = getHibernateTemplate().find(hql_getUserDiscussion2, new Object[] {new Boolean(false), username,});
    	int post = list1.size() + list2.size();
    	return post;
    }

    
    private static final String hql_getUserByUsername = "from User u where u.loginname=?";
    
    public User getUserByUsername(String username) {
    	List list = getHibernateTemplate().find(hql_getUserByUsername, new Object[] {username,});
    	User u = (User)list.get(0);
    	return u;
    }
    
    
    private static final String hql_getUserConcerns = "from Concern c where c.deleted=? and c.author.loginname=? order by c.createTime desc";

    public Collection getUserConcerns(String username) {
    	return getHibernateTemplate().find(hql_getUserConcerns, new Object[] {new Boolean(false), username,});
    }
    
    

    
}
