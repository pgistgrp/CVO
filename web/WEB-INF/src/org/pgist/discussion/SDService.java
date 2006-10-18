package org.pgist.discussion;

import java.util.Collection;

import org.pgist.cvo.Concern;
import org.pgist.tagging.Tag;
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


    Collection getPosts(InfoStructure structure, InfoObject infoObj, PageSetting setting, boolean order) throws Exception;
    
    
    Collection getReplies(DiscussionPost post, PageSetting setting) throws Exception;


    DiscussionPost createPost(InfoStructure structure, String title, String content, String[] tags) throws Exception;
    
    
    DiscussionPost createPost(InfoObject object, String title, String content, String[] tags) throws Exception;
    
    
    DiscussionReply createReply(DiscussionPost parent, String title, String content, String[] tags) throws Exception;


    void deletePost(DiscussionPost post) throws Exception;


    void editPost(DiscussionPost post, String title, String content, String[] tags) throws Exception;


    void increaseViews(DiscussionPost post) throws Exception;


    Concern getConcernById(Long Id) throws Exception;
    
    
    Collection getConcerns(InfoStructure structure, String ids, PageSetting setting) throws Exception;


    Collection getConcerns(InfoObject object, String ids, PageSetting setting) throws Exception;


    int getConcernTagCount(InfoStructure structure) throws Exception;


    int getConcernTagCount(InfoObject object) throws Exception;


    Collection getContextPosts(Long isid, Long pid, String ids, PageSetting setting) throws Exception;


    int getPostTagCount(Long isid, Long postId) throws Exception;


    Collection searchTags(InfoStructure structure, String tag, PageSetting setting) throws Exception;


    Collection getTagCloud(InfoStructure structure, PageSetting setting) throws Exception;


    Tag findTagById(Long tagId) throws Exception;
    
    
    DiscussionPost getDiscussionPostById(Long id) throws Exception;


    DiscussionReply getDiscussionReplyById(Long id) throws Exception;


    public boolean setVoting(int targetType, Long targetId, boolean agree) throws Exception;
    
    
    //temp
    Collection getInfoStructures() throws Exception;


}//interface SDService
