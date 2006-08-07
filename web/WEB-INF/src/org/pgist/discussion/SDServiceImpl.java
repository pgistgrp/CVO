package org.pgist.discussion;

import java.util.ArrayList;
import java.util.Collection;

import org.pgist.cvo.CategoryReference;
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
        return discussionDAO.getPostById(postid);
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
            discussion = discussionDAO.getDiscussion(InfoStructure.class, structure.getId());
        } else {
            /*
             * The discussion is on InfoObject
             */
            discussion = discussionDAO.getDiscussion(InfoObject.class, infoObj.getId());
        }
        
        if (discussion==null) return new ArrayList();
        
        return discussionDAO.getPosts(discussion, setting);
    }//getPosts()


    public Collection getReplies(DiscussionPost post, PageSetting setting) throws Exception {
        return discussionDAO.getReplies(post, setting);
    }//getReplies()


    public DiscussionPost createPost(String className, Long targetId, String title, String content, String[] tags) throws Exception {
        Discussion discussion = discussionDAO.getDiscussion(className, targetId);
        
        if (discussion==null) {
            discussion = new Discussion();
            discussion.setDeleted(false);
            discussion.setTargetId(targetId);
            discussion.setTargetType(className);
            
            discussionDAO.save(discussion);
        }
        
        return discussionDAO.createPost(discussion, null, title, content, tags);
    }//createPost()


    public DiscussionPost createReply(DiscussionPost parent, DiscussionPost quote, String title, String content, String[] tags) throws Exception {
        return discussionDAO.createReply(parent, quote, title, content, tags);
    }//createReply()


    public void deletePost(DiscussionPost post) throws Exception {
        post.setDeleted(true);
    }//deletePost()


    public void editPost(DiscussionPost post, String title, String content, String[] tags) throws Exception {
        discussionDAO.editPost(post, title, content, tags);
    }//editPost()


}//class SDServiceImpl
