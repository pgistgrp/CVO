package org.pgist.cvo;

import java.util.Collection;


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
     * @param concern A Concern object.
     * @param tags A list of string, each element will be a tag.
     * @return
     * @throws Exception
     */
    Concern createConcern(CCT cct, Concern concern, String[] tags) throws Exception;

    /**
     * Get the current user's concerns.
     *
     * @param cct A CCT object which the current user is working on.
     * @return A collection of Concern objects, the current user's concerns.
     * @throws Exception
     */
    Collection getMyConcerns(CCT cct) throws Exception;

    /**
     * Get other users' concerns than the current user.
     *
     * @param cct A CCT object which the current user is working on.
     * @param count The max number of concerns to retrieve.
     * @return A collection of Concern objects, other users' concerns.
     * @throws Exception
     */
    Collection getOthersConcerns(CCT cct, int count) throws Exception;

    /**
     * Randomly get other people's concerns.
     *
     * @param cct
     * @param count
     * @return
     * @throws Exception
     */
    Collection getRandomConcerns(CCT cct, int count) throws Exception;

    /**
     * Get the top N tags in the given cct according to the frequency of reference.
     *
     * @param cct A CCT object which the current user is working on.
     * @param count The max number of tags to be retrieved.
     * @return A collection of Tag objects.
     * @throws Exception
     */
    Collection getTagsByRank(CCT cct, int count) throws Exception;

    /**
     * Get tags whose reference times are greater than the given threshold.
     *
     * @param cct A CCT object which the current user is working on.
     * @param threshold The least reference times of the tag.
     * @return A collection of Tag objects.
     * @throws Exception
     */
    Collection getTagsByThreshold(CCT cct, int threshold) throws Exception;

    /**
     *
     * @param statement String
     * @return Collection
     * @throws Exception
     */
    Collection getSuggestedTags(String statement) throws Exception;

    /**
     * Get concerns by the given tagRefId.
     *
     * @param tagRefId the id of a TagReference object.
     * @param count the max amount of concerns to get.
     * @return A collection of Concern objects.
     * @throws Exception
     */
    Collection getConcernsByTag(Long tagRefId, int count) throws Exception;

}//interface CCTService
