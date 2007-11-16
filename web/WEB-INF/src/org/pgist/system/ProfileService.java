package org.pgist.system;

import org.pgist.users.User;

public interface  ProfileService {

	User getUserInfo(String username) throws Exception;
	
	boolean setUserInfo(String username, String homecity, String homezipcode, String workcity, String workzipcode, String vocation, String primarytransport, String profiledesc) throws Exception;
	
	void getDiscussionPost() throws Exception;
	
}
