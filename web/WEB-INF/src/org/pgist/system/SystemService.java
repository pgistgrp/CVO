package org.pgist.system;

import java.util.Collection;

import org.pgist.users.User;
import org.pgist.util.PageSetting;


/**
 * 
 * @author kenny
 *
 */
public interface SystemService {
    
    
    void createFeedback(String action, String s) throws Exception;

    Collection getFeedbacks(PageSetting setting) throws Exception;

	User getCurrentUser() throws Exception;
    
    
}//interface SystemService
