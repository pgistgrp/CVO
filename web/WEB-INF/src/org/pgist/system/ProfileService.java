package org.pgist.system;

import java.util.Date;

import org.pgist.users.User;

public interface  ProfileService {

	User getUserInfo(String username) throws Exception;
	
	boolean setUserInfo(String username, String homecity, String homezipcode, String workcity, String workzipcode, String vocation, String primarytransport, String profiledesc) throws Exception;
	
	void getDiscussionPost() throws Exception;

	Date getLastLogin(String username) throws Exception;
	
	int getTotalVisits(String username) throws Exception;
	
	int getPostCount(String username) throws Exception;
	
}
