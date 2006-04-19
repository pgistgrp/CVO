package org.pgist.cvo;

import java.util.Collection;

import org.pgist.util.PageSetting;


/**
 * Data Access Object for CCT.
 * 
 * @author kenny
 *
 */
public interface CCTDAO extends CVODAO {
    
    
    /**
     * Get a collection of all the CCT objects.
     * 
     * @return A collection of CCT objects.
     * @throws Exception
     */
    Collection getCCTs() throws Exception;

    /**
     * Get all concerns belong to the given user.
     * 
     * @param cctId The id of the CCT object.
     * @param userId The id of the given user.
     * @return A collection of Concern objects.
     * @throws Exception
     */
    Collection getMyConcerns(Long cctId, Long userId) throws Exception;

    /**
     * Get all concerns nbelong to the given user.
     * 
     * @param cctId The id of the CCT object.
     * @param userId The id of the given user.
     * @param count The max number of concerns to retrieve.
     * @return A collection of Concern objects.
     * @throws Exception
     */
    Collection getOthersConcerns(Long cctId, Long userId, int count) throws Exception;

    /**
     * Randomly get other people's concerns.
     * 
     * @param cctId The id of the CCT object.
     * @param userId The id of the given user.
     * @param setting The page setting.
     * @return A collection of Concern objects.
     * @throws Exception
     */
    Collection getRandomConcerns(Long cctId, Long userId, PageSetting setting) throws Exception;

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
     * Get the total number of concerns in a CCT object.
     * 
     * @param cct A CCT object which the current user is working on.
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
    int getConcernsTotal(CCT cct, int whose, Long userId) throws Exception;
    

}//interface CCTDAO
