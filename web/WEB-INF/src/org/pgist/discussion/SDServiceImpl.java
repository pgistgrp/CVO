package org.pgist.discussion;

import java.util.ArrayList;
import java.util.Collection;

import org.pgist.cvo.CategoryReference;
import org.pgist.cvo.Concern;
import org.pgist.system.SystemDAO;
import org.pgist.system.YesNoVoting;
import org.pgist.tagging.Tag;
import org.pgist.util.PageSetting;


/**
 * 
 * @author kenny
 *
 */
public class SDServiceImpl implements SDService {
    
    
    private DiscussionDAO discussionDAO;
    
    private SystemDAO systemDAO;
    
    
    public void setDiscussionDAO(DiscussionDAO discussionDAO) {
        this.discussionDAO = discussionDAO;
    }
    
    
    public void setSystemDAO(SystemDAO systemDAO) {
        this.systemDAO = systemDAO;
    }


    /*
     * ------------------------------------------------------------------------
     */
    
    
    public InfoStructure getInfoStructureById(Long isid) throws Exception {
        return (InfoStructure) discussionDAO.load(InfoStructure.class, isid);
    }//getInfoStructureById()


    public InfoObject getInfoObjectById(Long ioid) throws Exception {
        return (InfoObject) discussionDAO.load(InfoObject.class, ioid);
    }//getInfoObjectById()
    
    
    public DiscussionPost getPostById(Long postid) throws Exception {
        DiscussionPost post = discussionDAO.getPostById(postid);
        
        return post;
    }//getPostById()


    public Discussion getDiscussionById(Long did) throws Exception {
        return (Discussion) discussionDAO.getDiscussion(Discussion.class, did);
    }//getDiscussionById()


    public Collection getInfoStructures() throws Exception {
        return discussionDAO.getInfoStructures();
    }//getInfoStructures()


    public Collection getPosts(InfoStructure structure, InfoObject infoObj, PageSetting setting, boolean order) throws Exception {
        Discussion discussion = null;
        
        if (infoObj==null) {
            /*
             * The discussion is on InfoStructure
             */
            discussion = discussionDAO.getDiscussion(InfoStructure.class.getName(), structure.getId());
        } else {
            /*
             * The discussion is on InfoObject
             */
            discussion = discussionDAO.getDiscussion(InfoObject.class.getName(), infoObj.getId());
        }
        
        if (discussion==null) return new ArrayList();
        
        return discussionDAO.getPosts(discussion, setting, order);
    }//getPosts()


    public Collection getReplies(DiscussionPost post, PageSetting setting) throws Exception {
        Collection list = discussionDAO.getReplies(post, setting);
        for (DiscussionReply reply : (Collection<DiscussionReply>) list) {
            YesNoVoting voting = systemDAO.getVoting(YesNoVoting.TYPE_Discussion_REPLY, reply.getId());
            reply.setObject(voting);
        }//for
        return list;
    }//getReplies()


    public DiscussionPost createPost(InfoStructure structure, String title, String content, String[] tags) throws Exception {
        String type = InfoStructure.class.getName();
        Long id = structure.getId();
        
        Discussion discussion = discussionDAO.getDiscussion(type, id);
        
        if (discussion==null) {
            discussion = discussionDAO.createDiscussion(type, id);
        }
        
        DiscussionPost post = discussionDAO.createPost(discussion, title, content, tags);
        
        /*
         * record the last post
         */
        structure.setLastPost(post);
        
        structure.setRespTime(post.getCreateTime());
        
        /*
         * count the discussions
         */
        discussionDAO.increaseDiscussions(structure);
        
        discussionDAO.save(structure);
        
        return post;
    }//createPost()


    public DiscussionPost createPost(InfoObject object, String title, String content, String[] tags) throws Exception {
        String type = InfoObject.class.getName();
        Long id = object.getId();
        
        Discussion discussion = discussionDAO.getDiscussion(type, id);
        
        if (discussion==null) {
            discussion = discussionDAO.createDiscussion(type, id);
        }
        
        DiscussionPost post = discussionDAO.createPost(discussion, title, content, tags);
        
        /*
         * preload object
         */
        CategoryReference ref = (CategoryReference) object.getObject();
        ref.getCategory();
        ref.getCct();
        ref.getChildren();
        ref.getParents();
        ref.getTags();
        ref.getTheme();
        
        /*
         * record the last post
         */
        object.setLastPost(post);
        
        object.setRespTime(post.getCreateTime());
        
        /*
         * count the discussions
         */
        discussionDAO.increaseDiscussions(object);
        
        discussionDAO.save(object);
        
        return post;
    }//createPost()


    public DiscussionReply createReply(DiscussionPost parent, String title, String content, String[] tags) throws Exception {
        return discussionDAO.createReply(parent, title, content, tags);
    }//createReply()


    public void deletePost(DiscussionPost post) throws Exception {
        discussionDAO.deletePost(post);
    }//deletePost()


    public void editPost(DiscussionPost post, String title, String content, String[] tags) throws Exception {
        discussionDAO.editPost(post, title, content, tags);
    }//editPost()


    public void increaseViews(DiscussionPost post) throws Exception {
        discussionDAO.increaseViews(post);
    }//increaseViews()


    public boolean setVoting(int targetType, Long targetId, boolean agree) throws Exception {
        if (!systemDAO.setVoting(targetType, targetId, agree)) return false;
        
        switch(targetType) {
            case YesNoVoting.TYPE_INFO_STRUCTURE: {
                InfoStructure structure = getInfoStructureById(targetId);
                discussionDAO.increaseVoting(structure, agree);
                break; }
            case YesNoVoting.TYPE_INFO_OBJECT: {
                InfoObject object = getInfoObjectById(targetId);
                discussionDAO.increaseVoting(object, agree);
                break; }
            case YesNoVoting.TYPE_Discussion_POST:
                DiscussionPost post = getDiscussionPostById(targetId);
                discussionDAO.increaseVoting(post, agree);
            case YesNoVoting.TYPE_Discussion_REPLY:
                DiscussionReply reply = getDiscussionReplyById(targetId);
                discussionDAO.increaseVoting(reply, agree);
            default:
                 //UNKNOWN TYPE
        }//switch
        
        return true;
    }//setVoting()
    
    
    public Concern getConcernById(Long Id) throws Exception {
    	Concern myConcern = discussionDAO.getConcernById(Id);
    	return myConcern;
    } //getConcernById()
    
    
    public Collection getConcerns(InfoStructure structure, String ids, PageSetting setting) throws Exception {
        if (ids==null || ids.trim().length()==0) {
            return discussionDAO.getConcerns(structure, setting);
        } else {
            return discussionDAO.getConcerns(structure, ids, setting);
        }
    }//getConcerns()


    public Collection getConcerns(InfoObject object, String ids, PageSetting setting) throws Exception {
        if (ids==null || ids.trim().length()==0) {
            return discussionDAO.getConcerns(object, setting);
        } else {
            return discussionDAO.getConcerns(object, ids, setting);
        }
    }//getConcerns()


    public int getConcernTagCount(InfoStructure structure) throws Exception {
        return discussionDAO.getConcernTagCount(structure);
    }//getConcernTagCount()


    public int getConcernTagCount(InfoObject object) throws Exception {
        return discussionDAO.getConcernTagCount(object);
    }//getConcernTagCount()


    public Collection getContextPosts(Long isid, Long pid, String ids, PageSetting setting) throws Exception {
        if (ids==null || ids.trim().length()==0) {
            if (pid==null) {
                return discussionDAO.getContextPosts(isid, setting);
            } else {
                return discussionDAO.getContextPosts(isid, pid, setting);
            }
        } else {
            if (pid==null) {
                return discussionDAO.getContextPosts(isid, ids, setting);
            } else {
                return discussionDAO.getContextPosts(isid, pid, ids, setting);
            }
        }
    }//getContextPosts()


    public int getPostTagCount(Long isid, Long postId) throws Exception {
        return discussionDAO.getPostTagCount(isid, postId);
    }//getPostTagCount()


    public Collection searchTags(InfoStructure structure, String tag, PageSetting setting) throws Exception {
        return discussionDAO.searchTags(structure, tag, setting);
    }//searchTags()

    public Tag findTagById(Long tagId) throws Exception {
    	return discussionDAO.findTagById(tagId);
    } //findTagById()

    public Collection getTagCloud(InfoStructure structure, PageSetting setting) throws Exception {
        return discussionDAO.getTagCloud(structure, setting);
    }//getTagCloud()


    public DiscussionPost getDiscussionPostById(Long id) throws Exception {
        return discussionDAO.getPostById(id);
    }//getDiscussionPostById()


    public DiscussionReply getDiscussionReplyById(Long id) throws Exception {
        return discussionDAO.getReplyById(id);
    }//getDiscussionReplyById()


}//class SDServiceImpl
