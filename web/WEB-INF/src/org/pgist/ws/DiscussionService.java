package org.pgist.ws;

import java.util.Collection;

import org.pgist.model.Discussible;


/**
 * 
 * @author kenny
 *
 */
public interface DiscussionService {
    
    
    Discussible getDiscussible(Long id) throws Exception;

    Collection getBriefList(Long id, int count) throws Exception;
    
    
}//interface DiscussionService
