package org.pgist.discussion;

import java.util.ArrayList;
import java.util.Collection;

import org.pgist.cvo.CCTDAO;
import org.pgist.cvo.CategoryReference;
import org.pgist.users.User;
import org.pgist.util.PageSetting;
import org.pgist.util.WebUtils;


/**
 * 
 * @author kenny
 *
 */
public class SDServiceImpl implements SDService {
    
    
    private DiscussionDAO discussionDAO;
    
    private CCTDAO cctDAO;
    
    
    public void setDiscussionDAO(DiscussionDAO discussionDAO) {
        this.discussionDAO = discussionDAO;
    }
    
    
    public void setCctDAO(CCTDAO cctDAO) {
        this.cctDAO = cctDAO;
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
            discussion = discussionDAO.getDiscussion(InfoStructure.class.getName(), structure.getId());
        } else {
            /*
             * The discussion is on InfoObject
             */
            discussion = discussionDAO.getDiscussion(InfoObject.class.getName(), infoObj.getId());
        }
        
        if (discussion==null) return new ArrayList();
        
        return discussionDAO.getPosts(discussion, setting);
    }//getPosts()


    public Collection getReplies(DiscussionPost post, PageSetting setting) throws Exception {
        return discussionDAO.getReplies(post, setting);
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


    public InfoVoting getVoting(InfoStructure structure) throws Exception {
        User user = (User) discussionDAO.load(User.class, WebUtils.currentUserId());
        
        return discussionDAO.getVoting(structure, user);
    }//getVoting()


    public InfoVoting getVoting(InfoObject infoObject) throws Exception {
        User user = (User) discussionDAO.load(User.class, WebUtils.currentUserId());
        
        return discussionDAO.getVoting(infoObject, user);
    }//getVoting()


    public boolean setVoting(InfoStructure structure, boolean agree) throws Exception {
        User user = (User) discussionDAO.load(User.class, WebUtils.currentUserId());
        InfoVoting voting =  discussionDAO.getVoting(structure, user);
        if (voting!=null) return false;
        
        voting = new InfoVoting();
        voting.setStructure(structure);
        voting.setOwner(user);
        voting.setVoting(agree);
        
        discussionDAO.save(voting);
        
        discussionDAO.increaseVoting(structure, agree);
        
        return true;
    }//setVoting()


    public boolean setVoting(InfoObject object, boolean agree) throws Exception {
        User user = (User) discussionDAO.load(User.class, WebUtils.currentUserId());
        InfoVoting voting =  discussionDAO.getVoting(object, user);
        if (voting!=null) return false;
        
        voting = new InfoVoting();
        voting.setObject(object);
        voting.setOwner(user);
        voting.setVoting(agree);
        
        discussionDAO.save(voting);
        
        discussionDAO.increaseVoting(object, agree);
        
        return true;
    }//setVoting()


    public Collection getConcerns(InfoStructure structure, PageSetting setting) throws Exception {
        return discussionDAO.getConcerns(structure, setting);
    }//getConcerns()


    public Collection getConcerns(InfoObject object, PageSetting setting) throws Exception {
        return discussionDAO.getConcerns(object, setting);
    }//getConcerns()


}//class SDServiceImpl
