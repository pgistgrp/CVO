package org.pgist.system;

import java.util.Collection;
import java.util.Date;
import java.util.SortedSet;

import org.pgist.users.User;

public interface ProfileDAO {

	User getUserInfo(User username) throws Exception;
	
	boolean setUserInfo(User user, String homecity, String homezipcode, String workcity, String workzipcode, String vocation, String primarytransport, String profiledesc) throws Exception;
	
	Date getLastLogin(String username) throws Exception;
	
	int getTotalVisits(String username) throws Exception;
	
	int getPostCount(String username) throws Exception;
	
	Collection getUserConcerns(String username) throws Exception;
	
	Collection getUserDiscussion(String username) throws Exception;
	
	String[] getAllTags(String username) throws Exception;
	
}
