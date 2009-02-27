package org.pgist.system;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

import org.pgist.users.User;

public interface  ProfileService {

	User getUserInfo(Long userId) throws Exception;
	
	boolean setUserInfo(String username, String homecity, String homezipcode, String workcity, String workzipcode, String vocation, String primarytransport, String profiledesc) throws Exception;

	Date getLastLogin(Long id) throws Exception;
	
	int getTotalVisits(Long id) throws Exception;
	
	int getPostCount(Long id) throws Exception;
	
	Collection getUserConcerns(Long userId) throws Exception;
	
	Collection getUserDiscussion(String username, int start, int end) throws Exception;
	
	String[] getAllTags(Long userId) throws Exception;
	
	Set getUserCriteria(String username) throws Exception;
	
}
