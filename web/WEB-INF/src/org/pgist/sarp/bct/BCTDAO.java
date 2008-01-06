package org.pgist.sarp.bct;

import java.util.Collection;

import org.pgist.system.BaseDAO;
import org.pgist.util.PageSetting;


/**
 * Data Access Object for BCT.
 * 
 * @author kenny
 *
 */
public interface BCTDAO extends BaseDAO {
    
    
    BCT getBCTById(Long bctId) throws Exception;
    
    
    Concern getConcernById(Long concernId) throws Exception;
    
    
    TagReference getTagReferenceById(Long tagRefId) throws Exception;


    Comment getCommentById(Long commentId) throws Exception;


    /**
     * Get a collection of all the BCT objects.
     * 
     * @return A collection of BCT objects.
     * @throws Exception
     */
    Collection getBCTs() throws Exception;

    /**
     * Get all concerns belong to the given user.
     * 
     * @param bctId The id of the BCT object.
     * @param userId The id of the given user.
     * @return A collection of Concern objects.
     * @throws Exception
     */
    Collection getMyConcerns(Long bctId, Long userId) throws Exception;

    /**
     * Get all concerns nbelong to the given user.
     * 
     * @param bctId The id of the BCT object.
     * @param userId The id of the given user.
     * @param count The max number of concerns to retrieve.
     * @return A collection of Concern objects.
     * @throws Exception
     */
    Collection getOthersConcerns(Long bctId, Long userId, int count) throws Exception;

    /**
     * Randomly get other people's concerns.
     * 
     * @param bctId The id of the BCT object.
     * @param userId The id of the given user.
     * @param setting The page setting.
     * @return A collection of Concern objects.
     * @throws Exception
     */
    Collection getRandomConcerns(Long bctId, Long userId, PageSetting setting) throws Exception;

    /**
     * Get concerns with the given tagRefId.
     * 
     * @param tagRef the TagReference object.
     * @param count the max amount of concerns to get.
     * @return A collection of Concern objects.
     * @throws Exception
     */
    Collection getConcernsByTag(TagReference tagRef, int count) throws Exception;

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
     * @param user
     * @return
     * @throws Exception
     */
    int getConcernsTotal(BCT bct, int whose, Long userId) throws Exception;

    void delete(TagReference ref) throws Exception;

    Collection searchTags(Long id, String tag) throws Exception;
    
    
    TagReference getTagReferenceByTagId(Long bctId, Long tagId) throws Exception;


    /**
     * Search in the given BCT, and find those tags appeared in this BCT and which are the top count
     * being referenced.
     *
     * @param bctId
     * @param count
     * @return
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
     * Search in the given BCT, and find those tags appeared in this BCT and which are referenced at least
     * threshold times.
     *
     * @param bctId
     * @param threshold
     * @return
     * @throws Exception
     */
    Collection getTagsByThreshold(BCT bct, int threshold) throws Exception;
    
    
    /**
     * Increase the reference times of the given TagReference object.
     * 
     * @param ref
     * @throws Exception
     */
    void increaseRefTimes(TagReference ref) throws Exception;
    

    /**
     * Decrease the reference times of the given TagReference object.
     * 
     * @param ref
     * @throws Exception
     */
    void decreaseRefTimes(TagReference ref) throws Exception;
    
    
    Collection getContextConcerns(BCT bct, PageSetting setting, String type, int sorting) throws Exception;
    
    
    Collection getContextConcerns(BCT bct, PageSetting setting, String filter, String type, int sorting) throws Exception;

    
    void increaseVoting(Concern concern, boolean agree) throws Exception;


    void increaseReplies(Concern concern) throws Exception;
    
    
    void decreaseReplies(Concern concern) throws Exception;


    void deleteComments(Concern concern) throws Exception;


    Collection getComments(Long concernId, PageSetting setting) throws Exception;


    void increaseVoting(Comment comment, boolean agree) throws Exception;


    void increaseViews(Long concernId) throws Exception;


}//interface BCTDAO
