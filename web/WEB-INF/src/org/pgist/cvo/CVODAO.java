package org.pgist.cvo;


/**
 * A generic Data Access Object for abstract CVO.
 * 
 * @author kenny
 *
 */
public interface CVODAO {

    
    /**
     * Get a CCT object with the given cctId.<br>
     * Note: this object may be of deleted status. Check the deleted status before use.
     * 
     * @param cctId The id of the CCT object.
     * @return The requested CCT object.
     * @throws Exception
     */
    CCT getCCTById(Long cctId) throws Exception;

    /**
     * Get a concern object with the given concernId.<br>
     * Note: this object may be of deleted status. Check the deleted status before use.
     * 
     * @param concernId The id of the Concern object.
     * @return The requested Concern object.
     * @throws Exception
     */
    Concern getConcernById(Long concernId) throws Exception;
    
    /**
     * Get a Tag object with the given tagId.<br>
     * Note: this object has three kind of status. Check status before use.
     * 
     * @param tagId The id of the Tag object.
     * @return The requested Tag object.
     * @throws Exception
     */
    Tag getTagById(Long tagId) throws Exception;
    
    /**
     * Save the given CCT object to database.<br>
     * If the CCT object is already persisted, update the database; if not, create a new
     * CCT object and insert it to the database.
     * 
     * @param cct A CCT object persisted or not.
     * @throws Exception
     */
    void save(CCT cct) throws Exception;
    
    /**
     * Save the given Concern object to database.<br>
     * If the Concern object is already persisted, update the database; if not, create a new
     * Concern object and insert it to the database.
     * 
     * @param concern
     * @throws Exception
     */
    void save(Concern concern) throws Exception;
    
    /**
     * Save the given Tag object to database.<br>
     * If the Tag object is already persisted, update the database; if not, create a new
     * Tag object and insert it to the database.
     * 
     * @param tag
     * @throws Exception
     */
    void save(Tag tag) throws Exception;
    
    
}//interface CVODAO
