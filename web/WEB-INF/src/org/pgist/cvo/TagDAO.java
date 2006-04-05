package org.pgist.cvo;

import java.util.List;


/**
 * Data Access Object for Tag.
 * 
 * @author kenny
 *
 */
public interface TagDAO {
    
    
    /**
     * Add the given tags to the database, return a list of corresponding Tag objects.<br>
     * Each tag string will be check if it's already in the tag library. If it's already in the
     * library, get the Tag object from database; if not, create a new Tag object and save it to
     * the database.
     * 
     * @param tags A list of string, each is a tag.
     * @return A list of Tag object
     * @throws Exception
     */
    List addTags(List tags) throws Exception;
    
    
}//interface TagDAO
