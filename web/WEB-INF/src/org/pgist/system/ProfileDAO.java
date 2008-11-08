package org.pgist.system;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

import org.pgist.users.User;

public interface ProfileDAO {

	User getUserInfo(User username) throws Exception;
	
	boolean setUserInfo(User user, String homecity, String homezipcode, String workcity, String workzipcode, String vocation, String primarytransport, String profiledesc) throws Exception;
	
	Date getLastLogin(Long userId) throws Exception;
	
	int getTotalVisits(Long userId) throws Exception;
	
	int getPostCount(Long userId) throws Exception;
	
	Collection getUserConcerns(Long userId) throws Exception;
	
	Collection getUserDiscussion(String username, int start, int end) throws Exception;
	
	String[] getAllTags(Long userId) throws Exception;
	
	Set getUserCriteria(String username) throws Exception;
	
}
