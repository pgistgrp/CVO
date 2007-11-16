package org.pgist.discussion;

import java.util.Collection;

import org.pgist.util.PageSetting;



/**
 * 
 * @author kenny
 *
 */
public interface DiscussionDAO {
    
    
    Discussion getDiscussion(String targetType, Long targetId) throws Exception;
    
    
    Collection getPosts(Discussion discussion) throws Exception;
    
    
    Collection getPosts(Discussion discussion, PageSetting setting) throws Exception;
    
    
    Collection getReplies(DiscussionPost post) throws Exception;
    
    
    Collection getReplies(DiscussionPost post, PageSetting setting) throws Exception;
    
    
    Discussion createDiscussion(String targetType, Long targetId) throws Exception;
    
    
    DiscussionPost createPost(Discussion discussion, String content) throws Exception;
    
    
    DiscussionPost createPost(Discussion discussion, DiscussionPost quote, String content) throws Exception;
    
    
    DiscussionPost createReply(DiscussionPost post, String content) throws Exception;
    
    
    DiscussionPost createReply(DiscussionPost post, DiscussionPost quote, String content) throws Exception;


    DiscussionPost getPostById(Long id) throws Exception;
    
    
}//interface DiscussionDAO
