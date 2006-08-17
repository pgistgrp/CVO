package org.pgist.discussion;

import java.util.ArrayList;
import java.util.Collection;

import org.pgist.util.PageSetting;


/**
 * 
 * @author kenny
 *
 */
public class SDServiceImpl implements SDService {
    
    
    private DiscussionDAO discussionDAO;
    
    
    public void setDiscussionDAO(DiscussionDAO discussionDAO) {
        this.discussionDAO = discussionDAO;
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


    public Collection getPosts(InfoStructure structure, InfoObject infoObj, PageSetting setting) throws Exception {
        Discussion discussion = null;
        
        if (infoObj==null) {
            /*
             * The discussion is on InfoStructure
             */
            discussion = discussionDAO.getDiscussion(structure.getType(), structure.getId());
        } else {
            /*
             * The discussion is on InfoObject
             */
            discussion = discussionDAO.getDiscussion(structure.getType(), infoObj.getId());
        }
        
        if (discussion==null) return new ArrayList();
        
        return discussionDAO.getPosts(discussion, setting);
    }//getPosts()


    public Collection getReplies(DiscussionPost post, PageSetting setting) throws Exception {
        return discussionDAO.getReplies(post, setting);
    }//getReplies()


    public DiscussionPost createPost(String type, Long targetId, String title, String content, String[] tags) throws Exception {
        Discussion discussion = discussionDAO.getDiscussion(type, targetId);
        
        if (discussion==null) {
            discussion = new Discussion();
            discussion.setDeleted(false);
            discussion.setTargetId(targetId);
            discussion.setTargetType(type);
            
            discussionDAO.save(discussion);
        }
        
        return discussionDAO.createPost(discussion, null, title, content, tags);
    }//createPost()


    public DiscussionPost createReply(DiscussionPost parent, DiscussionPost quote, String title, String content, String[] tags) throws Exception {
        return discussionDAO.createReply(parent, quote, title, content, tags);
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


}//class SDServiceImpl
