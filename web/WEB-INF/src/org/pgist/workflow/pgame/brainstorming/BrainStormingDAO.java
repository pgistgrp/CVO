package org.pgist.workflow.pgame.brainstorming;

import org.pgist.model.Post;


/**
 * 
 * @author kenny
 *
 */
public interface BrainStormingDAO {
    
    
    BrainStorming getBrainStorming(Long activityId, int counts) throws Exception;

    void saveBrainStorming(BrainStorming brainStorming) throws Exception;

    BrainStorming getBrainStormingById(Long brainStormingId) throws Exception;
    
    Post getPostById(Long id) throws Exception;

    void savePost(Post post) throws Exception;
    
    
}//interface BrainStormingDAO
