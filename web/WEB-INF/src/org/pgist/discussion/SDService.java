package org.pgist.discussion;

import java.util.Collection;

import org.pgist.util.PageSetting;



/**
 * 
 * @author kenny
 *
 */
public interface SDService {
    
    
    InfoStructure getInfoStructureById(Long id) throws Exception;
    
    
    InfoObject getInfoObjectById(Long isid) throws Exception;


    DiscussionPost getPostById(Long postid) throws Exception;


    Collection getPosts(InfoStructure structure, InfoObject infoObj, PageSetting setting) throws Exception;
    
    
    Collection getReplies(DiscussionPost post, PageSetting setting) throws Exception;


    //temp
    Collection getInfoStructures() throws Exception;
    void saveIt() throws Exception;


}//interface SDService
