package org.pgist.ws;

import java.util.Collection;

import org.pgist.model.Discussible;


/**
 * 
 * @author kenny
 *
 */
public interface DiscussibleDAO {
    
    
    Discussible getDiscussible(Long id) throws Exception;

    Collection getBriefList(Long rootId, int count) throws Exception;
    
    
}//interface DiscussibleDAO
