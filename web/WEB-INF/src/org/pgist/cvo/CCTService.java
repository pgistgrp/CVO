package org.pgist.cvo;

import java.util.Collection;
import java.util.List;


/**
 * 
 * @author kenny
 *
 */
public interface CCTService {
    
    
    /**
     * Get all the CCT objects from the database.
     * 
     * @return A collection of CCT objects.
     * @throws Exception
     */
    Collection getCCTs() throws Exception;

    /**
     * Save the given CCT object to database.<br>
     * Depending on the CCT object persisted or not, this method will automatically Insert/Update
     * the CCT object to database.
     * 
     * @param cct A CCT object.
     * @throws Exception
     */
    void save(CCT cct) throws Exception;

    /**
     * Get a CCT object by the given cctId.
     * 
     * @param cctId The id of the CCT object.
     * @return The CCT object requested.
     * @throws Exception
     */
    CCT getCCTById(Long cctId) throws Exception;
    
    /**
     * Create a new Concern object within the given CCT object and the given tags.<br>
     * If the tag is not in the tag library, a new Tag object will be created and insert
     * into the library.<br>
     * Reference count of each tag will be increased by 1. 
     * 
     * @param cct A CCT object which the new Concern object belongs to.
     * @param concern The paragraph user input.
     * @param tags A list of string, each element will be a tag.
     * @return
     * @throws Exception
     */
    Concern createConcern(CCT cct, String concern, List tags) throws Exception;
    
    
}//interface CCTService
