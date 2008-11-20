package org.pgist.sarp.bct;

import java.util.Collection;
import java.util.Set;

import org.pgist.sarp.drt.InfoObject;
import org.pgist.users.User;
import org.pgist.util.PageSetting;


/**
 *
 * @author kenny
 *
 */
public interface BCTService {


    /**
     * Create a BCT object.
     *
     * @param workflowId
     * @param name
     * @param purpose
     * @param instruction
     * @return
     * @throws Exception
     */
    BCT createBCT(Long workflowId, String name, String purpose, String instruction) throws Exception;

    /**
     * Save the given BCT object to database.<br>
     * Depending on the BCT object persisted or not, this method will automatically Insert/Update
     * the BCT object to database.
     *
     * @param bctId A BCT object.
     * @throws Exception
     */
    //void save(BCT bct) throws Exception;

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
     * Get a BCT object by the given bctId.
     *
     * @param bctId The id of the BCT object.
     * @return The BCT object requested.
     * @throws Exception
     */
    BCT getBCTById(Long bctId) throws Exception;

    /**
     * Create a new Concern object within the given BCT object and the given tags.<br>
     * If the tag is not in the tag library, a new Tag object will be created and insert
     * into the library.<br>
     * Reference count of each tag will be increased by 1.
     *
     * @param bctId A BCT object which the new Concern object belongs to.
     * @param concern A Concern object.
     * @param tags A list of string, each element will be a tag.
     * @return
     * @throws Exception
     */
    Concern createConcern(Long bctId, String concern, String[] tags) throws Exception;

    /**
     * Get the current user's concerns.
     *
     * @param bctId A BCT object which the current user is working on.
     * @return A collection of Concern objects, the current user's concerns.
     * @throws Exception
     */
    Collection getMyConcerns(BCT bct) throws Exception;

    /**
     * Get other users' concerns than the current user.
     *
     * @param bctId A BCT object which the current user is working on.
     * @param count The max number of concerns to retrieve.
     * @return A collection of Concern objects, other users' concerns.
     * @throws Exception
     */
    Collection getOthersConcerns(BCT bct, int count) throws Exception;

    /**
     * Randomly get other people's concerns.
     *
     * @param bctId
     * @param setting The page setting.
     * @return
     * @throws Exception
     */
    Collection getRandomConcerns(BCT bct, PageSetting setting) throws Exception;

    /**
     * Get the top N tags in the given bctId according to the frequency of reference.
     *
     * @param bctId A BCT object which the current user is working on.
     * @param count The max number of tags to be retrieved.
     * @return A collection of Tag objects.
     * @throws Exception
     */
    Collection getTagsByRank(BCT bct, int count) throws Exception;

    /**
     * Get the top N tags in the given bctId according to the frequency of reference.
     *
     * @param bctId A BCT object which the current user is working on.
     * @param setting The page setting.
     * @return A collection of Tag objects.
     * @throws Exception
     */
    Collection getTagCloud(BCT bct, PageSetting setting) throws Exception;
    
    /**
     * Get tags whose reference times are greater than the given threshold.
     *
     * @param bctId A BCT object which the current user is working on.
     * @param threshold The least reference times of the tag.
     * @return A collection of Tag objects.
     * @throws Exception
     */
    Collection getTagsByThreshold(BCT bct, int threshold) throws Exception;

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
     * @param concernId id of the Concern object.
     * @throws Exception
     */
    void deleteConcern(Long concernId) throws Exception;

    /**
     * Edit the tag list of the given Concern object.
     *
     * @param concern The Concern object.
     * @param tags A list of string, each element will be a tag.
     * @throws Exception
     */
    void editConcernTags(Concern concern, String[] tags) throws Exception;

    /**
     * Get the total number of concerns in a BCT object.
     *
     * @param bctId A BCT object which the current user is working on.
     * @param whose The mode to count the total number of concerns.<br>
     *        <ul>
     *          <li>whose==0: all concerns</li>
     *          <li>whose==1: my concerns</li>
     *          <li>whose==2: other's concerns</li>
     *        </ul>
     * @return
     * @throws Exception
     */
    int getConcernsTotal(BCT bct, int whose) throws Exception;

    /**
     * Get the TagReference object with the given tagRefId.
     *
     * @param tagRefId
     * @return The TagReference object
     * @throws Exception
     */
    TagReference getTagReferenceById(Long tagRefId) throws Exception;

    /**
     * Search all matched tags in the given BCT by tag name. Approximate match is used for the tag string.
     * @param bctId A BCT object which the current user is working on.
     * @param tag A string which will be searched in tags.
     * @return A collection of matched TagReference objects.
     * @throws Exception
     */
    Collection searchTags(BCT bct, String tag) throws Exception;
    
    
    Collection getContextConcerns(BCT bct, PageSetting setting, String filter, String type, int sorting) throws Exception;

    
    boolean setVotingOnConcern(Long id, boolean agree) throws Exception;

    
    ConcernComment createConcernComment(Long workflowId, Long concernId, String title, String content, String[] tags) throws Exception;
    
    
    ConcernComment editConcernComment(Long commentId, String title, String content, String[] tags) throws Exception;
    
    
    void deleteConcernComment(Long commentId) throws Exception;

    
    Collection getConcernComments(Long concernId, PageSetting setting) throws Exception;
    
    
    boolean setVotingOnConcernComment(Long id, boolean agree) throws Exception;
    
    
    void increaseViews(Long concernId) throws Exception;
    
    
    ConcernComment getConcernCommentById(Long commentId) throws Exception;


    InfoObject publish(Long bctId, String title) throws Exception;
    
    
	void toggleBCT(Long bctId, boolean equals) throws Exception;
	
	
	Collection getConciseTags(BCT bct, PageSetting setting, int sorting) throws Exception;

	
    Set<User> getThreadUsers(Long concernId) throws Exception;
    
    
}//interface BCTService
