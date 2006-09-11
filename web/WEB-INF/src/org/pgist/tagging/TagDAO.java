package org.pgist.tagging;

import java.util.Collection;

import org.pgist.system.BaseDAO;
import org.pgist.util.PageSetting;


/**
 * Data Access Object for Tag.
 *
 * @author kenny
 *
 */
public interface TagDAO extends BaseDAO {

    
    Tag getTagById(Long id) throws Exception;
    

    /**
     * Add the given tag to the database.<br>
     * Tag string will be checked if it's already in the tag library. If it's already in the
     * library, get the Tag object from database; if not, create a new Tag object and save it to
     * the database.
     *
     * @param tag A tag string.
     * @param type tag type
     * @param status tag status
     * @return A Tag object
     * @throws Exception
     */
    Tag addTag(String tag, int status, boolean included) throws Exception;


    /**
     * Get all included OR excluded tags.
     * 
     * @param included if true, return included tags, else return excluded tags.
     * @return A collection of Tag objects.
     * @throws Exception
     */
    Collection getTags(boolean included) throws Exception;
    
    
    Collection getTags(PageSetting setting, boolean included) throws Exception;
    
    
    Collection getAllTags(boolean included) throws Exception;
    
    
    Collection getAllTags() throws Exception;


    void deleteTag(Long id) throws Exception;


    Collection searchTags(String name, boolean included) throws Exception;


}//interface TagDAO
