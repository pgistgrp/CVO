package org.pgist.discussion;

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
        
        return discussionDAO.getPosts(discussion, setting);
    }//getPosts()


    public Collection getReplies(DiscussionPost post, PageSetting setting) throws Exception {
        return discussionDAO.getReplies(post, setting);
    }//getReplies()


    public void createPost(String className, Long targetId, String content) throws Exception {
        Discussion discussion = discussionDAO.getDiscussion(className, targetId);
        if (discussion==null) throw new Exception("can't find this discussion.");
        
        discussionDAO.createPost(discussion, content);
    }//createPost()


    //temp
    
    
    public void saveIt() throws Exception {
        InfoStructure structure = new InfoStructure();
        structure.setType("sdc");
        
        CategoryReference ref = (CategoryReference) discussionDAO.load(CategoryReference.class, new Long(1810));
        
        ref.getCategory();
        ref.getChildren();
        ref.getChildren();
        ref.getCct();
        ref.getTags();
        ref.getTheme();
        
        InfoObject obj = new InfoObject();
        obj.setObject(ref);
        discussionDAO.save(obj);
        
        structure.getInfoObjects().add(obj);
        
//        ref = (CategoryReference) discussionDAO.load(CategoryReference.class, new Long(1813));
//        
//        obj = new InfoObject();
//        obj.setObject(ref);
//        discussionDAO.save(obj);
//        
//        structure.getInfoObjects().add(obj);
//        
//        ref = (CategoryReference) discussionDAO.load(CategoryReference.class, new Long(1816));
//        
//        obj = new InfoObject();
//        obj.setObject(ref);
//        discussionDAO.save(obj);
//        
//        structure.getInfoObjects().add(obj);
        
        discussionDAO.save(structure);
    }//saveIt()


}//class SDServiceImpl
