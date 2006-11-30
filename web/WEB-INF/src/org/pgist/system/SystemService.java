package org.pgist.system;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.pgist.users.User;
import org.pgist.util.PageSetting;
import org.pgist.web.DelegatingHttpServletRequestWrapper;


/**
 * 
 * @author kenny
 *
 */
public interface SystemService {
    
    
    Collection getUsersByRole(String role) throws Exception;
    
    
    Feedback createFeedback(String action, String s) throws Exception;
    
    
    Collection getFeedbacks(PageSetting setting) throws Exception;
    
    
	User getCurrentUser() throws Exception;
    
    
    YesNoVoting getVoting(int targetType, Long targetId) throws Exception;


    void logRequest(DelegatingHttpServletRequestWrapper request) throws Exception;
    
    
}//interface SystemService
