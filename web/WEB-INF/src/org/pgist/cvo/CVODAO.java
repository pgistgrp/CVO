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
     * Get a TagReference object with the given tagId.<br>
     * 
     * @param refId The id of the TagReference object.
     * @return The requested TagReference object.
     * @throws Exception
     */
    TagReference getTagReferenceById(Long refId) throws Exception;
    
    /**
     * Get a CategoryReference object with the give categoryId.<br>
     * 
     * @param categoryId
     * @return
     * @throws Exception
     */
    public CategoryReference getCategoryReferenceById(Long categoryId) throws Exception;
    
    /**
     * Save the given CCT object to database.<br>
     * If the CCT object is already persisted, update the database; if not, create a new
     * CCT object and insert it to the database.
     * 
     * @param cctId A CCT object persisted or not.
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
    
    
    /**
     * Save the given TagReference object to database.<br>
     * If the TagReference object is already persisted, update the database; if not, create a new
     * TagReference object and insert it to the database.
     * 
     * @param tag
     * @throws Exception
     */
    void save(TagReference ref) throws Exception;
    
    /**
     * Save the given CategoryReference object to database.<br>
     * If the CategoryReference object is already persisted, update the database; if not, create a new
     * CategoryReference object and insert it to the database.
     * 
     * @param ref
     * @throws Exception
     */
    void save(CategoryReference ref) throws Exception;
    
    /**
     * Save the given Category object to database.<br>
     * If the Category object is already persisted, update the database; if not, create a new
     * Category object and insert it to the database.
     * @param category
     * @throws Exception
     */
    void save(Category category) throws Exception;
    
    void refresh(Object object) throws Exception;
    
    
}//interface CVODAO
