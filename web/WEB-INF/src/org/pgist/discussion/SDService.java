package org.pgist.discussion;

import java.util.Collection;

import org.pgist.util.PageSetting;



/**
 * 
 * @author kenny
 *
 */
public interface SDService {
    
    
    InfoStructure getInfoStructureById(Long isid) throws Exception;
    
    
    InfoObject getInfoObjectById(Long ioid) throws Exception;


    Discussion getDiscussionById(Long did) throws Exception;


    DiscussionPost getPostById(Long postid) throws Exception;


    Collection getPosts(InfoStructure structure, InfoObject infoObj, PageSetting setting) throws Exception;
    
    
    Collection getReplies(DiscussionPost post, PageSetting setting) throws Exception;


    void createPost(String className, Long targetId, String content) throws Exception;


    void createReply(DiscussionPost parent, DiscussionPost quote, String content) throws Exception;


    void deletePost(DiscussionPost post) throws Exception;


    void editPost(DiscussionPost post, String content) throws Exception;


    //temp
    Collection getInfoStructures() throws Exception;
    void saveIt() throws Exception;


}//interface SDService
