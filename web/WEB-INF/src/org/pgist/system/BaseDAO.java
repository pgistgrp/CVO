package org.pgist.system;

import org.pgist.users.User;


/**
 * 
 * @author kenny
 *
 */
public interface BaseDAO {

    
    User getUserById(Long id) throws Exception;
    
    
}//interface BaseDAO
