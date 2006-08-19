package org.pgist.tag;

import java.util.Collection;

import org.pgist.system.BaseDAO;


/**
 * Data Access Object for Tag.
 *
 * @author kenny
 *
 */
public interface TagDAO extends BaseDAO {


    /**
     * Add the given tag to the database.<br>
     * Tag string will be checked if it's already in the tag library. If it's already in the
     * library, get the Tag object from database; if not, create a new Tag object and save it to
     * the database.
     *
     * @param tags A tag string.
     * @return A Tag object
     * @throws Exception
     */
    Tag addTag(String tag) throws Exception;


    /**
     * Get all valid tags in the system.
     *
     * @return A collection of Tag objects.
     * @throws Exception
     */
    Collection getAllTags() throws Exception;
    
    
    /**
     * Get all included OR excluded tags.
     * 
     * @param included if true, return included tags, else return excluded tags.
     * @return A collection of Tag objects.
     * @throws Exception
     */
    Collection getTags(boolean included) throws Exception;


}//interface TagDAO
