package org.pgist.system;

import java.util.Collection;

import org.pgist.discussion.DiscussionPost;
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
}
