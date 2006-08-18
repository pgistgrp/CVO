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


    DiscussionPost createPost(String className, Long targetId, String title, String content, String[] tags) throws Exception;


    DiscussionPost createReply(DiscussionPost parent, DiscussionPost quote, String title, String content, String[] tags) throws Exception;


    void deletePost(DiscussionPost post) throws Exception;


    void editPost(DiscussionPost post, String title, String content, String[] tags) throws Exception;


    void increaseViews(DiscussionPost post) throws Exception;


    InfoVoting getVoting(InfoStructure structure) throws Exception;


    InfoVoting getVoting(InfoObject infoObject) throws Exception;


    boolean setVoting(InfoStructure structure, boolean agree) throws Exception;


    boolean setVoting(InfoObject object, boolean agree) throws Exception;


    //temp
    Collection getInfoStructures() throws Exception;


}//interface SDService
