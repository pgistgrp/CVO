package org.pgist.system;

import org.pgist.users.User;


/**
 * 
 * @author kenny
 *
 */
public interface BaseDAO {

    
    Object load(Class klass, Long id) throws Exception;
    
    void save(Object object) throws Exception;
    
    User getUserById(Long id) throws Exception;
    
    
}//interface BaseDAO
