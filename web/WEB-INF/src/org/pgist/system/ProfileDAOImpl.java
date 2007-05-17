package org.pgist.system;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Iterator;

import org.pgist.discussion.DiscussionPost;
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
	
	
    private static final String hql_getDiscussionPost = "from DiscussionReply r where r.owner=? order by r.replyTime";
    
	public void getDiscussionPost(User user) throws Exception {
		
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
    
    
    private static final String hql_getPostCount1 = "from DiscussionPost dp where dp.owner.loginname=?";
    private static final String hql_getPostCount2 = "from DiscussionReply dr where dr.owner.loginname=?";
    
    public int getPostCount(String username) {
    	//User u = getUserByUsername(username);
    	List list = getHibernateTemplate().find(hql_getPostCount1, new Object[] {username,});
    	List list2 = getHibernateTemplate().find(hql_getPostCount2, new Object[] {username,});
    	int post = list.size() + list2.size();
    	return post;
    }

    
    private static final String hql_getUserByUsername = "from User u where u.loginname=?";
    
    public User getUserByUsername(String username) {
    	List list = getHibernateTemplate().find(hql_getUserByUsername, new Object[] {username,});
    	User u = (User)list.get(0);
    	return u;
    }
    
}
