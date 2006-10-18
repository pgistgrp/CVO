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
    
    
    YesNoVoting getVoting(int targetType, Long targetId) throws Exception;

    
    boolean setVoting(int targetType, Long targetId, boolean agree) throws Exception;
    
    
}//interface SystemDAO
