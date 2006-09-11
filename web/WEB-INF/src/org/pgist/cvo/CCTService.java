package org.pgist.cvo;

import java.util.Collection;

import org.pgist.util.PageSetting;
import java.util.Map;


/**
 *
 * @author kenny
 *
 * @soap.service urn="CCTService" scope="Request"
 * @axis.service urn="CCTService" scope="request"
 */
public interface CCTService {


    /**
     * Get all the CCT objects from the database.
     *
     * @soap.method
     * @axis.method
     * 
     * @return A collection of CCT objects.
     * @throws Exception
     */
    Collection getCCTs() throws Exception;

    /**
     * Create a CCT object.
     *
     * @param name
     * @param purpose
     * @param instruction
     * @return
     * @throws Exception
     */
    CCT createCCT(String name, String purpose, String instruction) throws Exception;

    /**
     * Save the given CCT object to database.<br>
     * Depending on the CCT object persisted or not, this method will automatically Insert/Update
     * the CCT object to database.
     *
     * @param cctId A CCT object.
     * @throws Exception
     */
    //void save(CCT cct) throws Exception;

    /**
     * Save the given Concern object to database.<br>
     * Depending on the Concern object persisted or not, this method will automatically Insert/Update
     * the Concern object to database.
     *
     * @param concern
     * @throws Exception
     */
    void save(Concern concern) throws Exception;

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
     * @param cctId A CCT object which the new Concern object belongs to.
     * @param concern A Concern object.
     * @param tags A list of string, each element will be a tag.
     * @return
     * @throws Exception
     */
    Concern createConcern(Long cctId, String concern, String[] tags) throws Exception;

    /**
     * Get the current user's concerns.
     *
     * @param cctId A CCT object which the current user is working on.
     * @return A collection of Concern objects, the current user's concerns.
     * @throws Exception
     */
    Collection getMyConcerns(CCT cct) throws Exception;

    /**
     * Get other users' concerns than the current user.
     *
     * @param cctId A CCT object which the current user is working on.
     * @param count The max number of concerns to retrieve.
     * @return A collection of Concern objects, other users' concerns.
     * @throws Exception
     */
    Collection getOthersConcerns(CCT cct, int count) throws Exception;

    /**
     * Randomly get other people's concerns.
     *
     * @param cctId
     * @param setting The page setting.
     * @return
     * @throws Exception
     */
    Collection getRandomConcerns(CCT cct, PageSetting setting) throws Exception;

    /**
     * Get the top N tags in the given cctId according to the frequency of reference.
     *
     * @param cctId A CCT object which the current user is working on.
     * @param count The max number of tags to be retrieved.
     * @return A collection of Tag objects.
     * @throws Exception
     */
    Collection getTagsByRank(CCT cct, int count) throws Exception;

    /**
     * Get tags whose reference times are greater than the given threshold.
     *
     * @param cctId A CCT object which the current user is working on.
     * @param threshold The least reference times of the tag.
     * @return A collection of Tag objects.
     * @throws Exception
     */
    Collection getTagsByThreshold(CCT cct, int threshold) throws Exception;

    /**
     *
     * @param statement String
     * @return A 2D array of strings. string[0] is an array of matched tags, string[1] is the suggested words
     * @throws Exception
     */
    String[][] getSuggestedTags(String statement) throws Exception;

    /**
     * Get concerns by the given tagRefId.
     *
     * @param tagRefId the id of a TagReference object.
     * @param count the max amount of concerns to get.
     * @return A collection of Concern objects.
     * @throws Exception
     */
    Collection getConcernsByTag(Long tagRefId, int count) throws Exception;

    /**
     * Get the Concern object with id concernId.
     *
     * @param concernId the id of the Concern object.
     * @return The Concern object.
     * @throws Exception
     */
    Concern getConcernById(Long concernId) throws Exception;

    /**
     * Delete the given Concern object.
     *
     * @param concern The Concern object.
     * @throws Exception
     */
    void deleteConcern(Concern concern) throws Exception;

    /**
     * Edit the tag list of the given Concern object.
     *
     * @param concern The Concern object.
     * @param tags A list of string, each element will be a tag.
     * @throws Exception
     */
    void editConcernTags(Concern concern, String[] tags) throws Exception;

    /**
     * Get the total number of concerns in a CCT object.
     *
     * @param cctId A CCT object which the current user is working on.
     * @param whose The mode to count the total number of concerns.<br>
     *        <ul>
     *          <li>whose==0: all concerns</li>
     *          <li>whose==1: my concerns</li>
     *          <li>whose==2: other's concerns</li>
     *        </ul>
     * @return
     * @throws Exception
     */
    int getConcernsTotal(CCT cct, int whose) throws Exception;

    /**
     * Get the TagReference object with the given tagRefId.
     *
     * @param tagRefId
     * @return The TagReference object
     * @throws Exception
     */
    TagReference getTagReferenceById(Long tagRefId) throws Exception;

    /**
     * Search all matched tags in the given CCT by tag name. Approximate match is used for the tag string.
     * @param cctId A CCT object which the current user is working on.
     * @param tag A string which will be searched in tags.
     * @return A collection of matched TagReference objects.
     * @throws Exception
     */
    Collection searchTags(CCT cct, String tag) throws Exception;


}//interface CCTService
