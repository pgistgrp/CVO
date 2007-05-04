package org.pgist.system;

import org.pgist.users.User;

public interface ProfileDAO {

	User getUserInfo(User username) throws Exception;
	
	boolean setUserInfo(User user, String homecity, String homezipcode, String workcity, String workzipcode, String vocation, String primarytransport, String profiledesc) throws Exception;
	
	void getDiscussionPost(User user) throws Exception;
	
}
