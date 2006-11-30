package org.pgist.discussion;

import java.util.Collection;
import java.util.Set;

import org.pgist.cvo.Concern;
import org.pgist.system.BaseDAO;
import org.pgist.tagging.Tag;
import org.pgist.util.PageSetting;


/**
 * 
 * @author kenny
 *
 */
public interface DiscussionDAO extends BaseDAO {
    
    
    Discussion getDiscussionById(Long did) throws Exception;


    DiscussionPost getPostById(Long id) throws Exception;
    
    
    DiscussionReply getReplyById(Long id) throws Exception;


    Collection getPosts(Discussion discussion, int sorting) throws Exception;
    
    
    Collection getPosts(Discussion discussion, PageSetting setting, int sorting) throws Exception;
    
    
    Collection getPosts(Discussion discussion, PageSetting setting, String filter, int sorting) throws Exception;
    
    
    Collection getReplies(DiscussionPost post) throws Exception;
    
    
    Collection getReplies(DiscussionPost post, PageSetting setting) throws Exception;
    
    
    Collection getReplies(DiscussionPost post, PageSetting setting, String filter) throws Exception;


    DiscussionPost createPost(Discussion discussion, String title, String content, String[] tags, boolean emailNotify) throws Exception;
    
    
    DiscussionReply createReply(DiscussionPost post, String title, String content, String[] tags, boolean emailNotify) throws Exception;
    
    
    void editPost(DiscussionPost post, String title, String content, String[] tags) throws Exception;


    void deleteDiscussion(Discussion discussion) throws Exception;
    
    
    void deletePost(DiscussionPost post) throws Exception;
    
    
    void deleteReply(DiscussionReply reply) throws Exception;


    void increaseDiscussions(Discussion discussion) throws Exception;


    void increaseReplies(DiscussionPost post) throws Exception;


    void decreaseDiscussions(Discussion discussion) throws Exception;


    void decreaseReplies(DiscussionPost post) throws Exception;


    void increaseViews(DiscussionPost post) throws Exception;


    void increaseVoting(InfoStructure structure, boolean agree) throws Exception;


    void increaseVoting(InfoObject object, boolean agree) throws Exception;

    
    void increaseVoting(DiscussionPost post, boolean agree) throws Exception;


    void increaseVoting(DiscussionReply reply, boolean agree) throws Exception;


    Long[] processIds(Long isid, String ids, boolean tagId) throws Exception;


    Concern getConcernById(Long tagId) throws Exception;
	
    
    Collection getConcerns(InfoStructure structure, PageSetting setting) throws Exception;


    Collection getConcerns(InfoStructure structure, Long[] ids, PageSetting setting) throws Exception;
    

    Collection getConcerns(InfoObject object, PageSetting setting) throws Exception;


    Collection getConcerns(InfoObject object, Long[] ids, PageSetting setting) throws Exception;


    int getConcernTagCount(InfoStructure structure) throws Exception;


    int getConcernTagCount(InfoObject object) throws Exception;


    Object getRelatedInfo(Discussion discussion) throws Exception;


    Collection getContextPosts(Long isid, Long pid, PageSetting setting, int sorting) throws Exception;


    Collection getContextPosts(Long isid, Long pid, Long[] ids, PageSetting setting, int sorting) throws Exception;


    Collection getContextPosts(Long isid, PageSetting setting, int sorting) throws Exception;


    Collection getContextPosts(Long isid, Long[] ids, PageSetting setting, int sorting) throws Exception;


    int getPostTagCount(Long isid, Long postId) throws Exception;


    Collection searchTags(InfoStructure structure, String tag, PageSetting setting) throws Exception;
    
    
    Collection searchTags(InfoStructure structure, InfoObject infoObject, String tag, PageSetting setting) throws Exception;


    Tag findTagById(Long tagId) throws Exception; //John Added 9/29
    
    
    void deleteVotings(Long infoObjectId) throws Exception;


    Collection getTagCloud(InfoStructure structure, PageSetting setting) throws Exception;
    
    
    Set getEmailUsers(DiscussionPost parent, DiscussionReply reply) throws Exception;


    InfoObject getInfoObjectByDiscussionId(Long did) throws Exception;


    void setupPostEmailNotify(Long id, boolean turnon) throws Exception;


    void setupReplyEmailNotify(Long id, boolean turnon) throws Exception;


    //temp
    
    Collection getInfoStructures() throws Exception;


}//interface DiscussionDAO
