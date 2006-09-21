package org.pgist.system;

import java.util.Collection;

import org.pgist.util.PageSetting;


/**
 * 
 * @author kenny
 *
 */
public interface SystemDAO extends BaseDAO {
    
    
    Collection getFeedbacks(PageSetting setting) throws Exception;
    
    
}//interface SystemDAO
