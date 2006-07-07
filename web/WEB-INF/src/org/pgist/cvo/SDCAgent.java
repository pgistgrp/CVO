package org.pgist.cvo;

import java.util.HashMap;
import java.util.Map;


/**
 * DWR AJAX Agent class.<br>
 * Provide AJAX services to client programs.<br>
 * In this document, all the NON-AJAX methods are marked out. So all methods
 * <span style="color:red;">without</span> such a description
 * <span style="color:red;">ARE</span> AJAX service methods.<br>
 * 
 * @author kenny
 *
 */
public class SDCAgent {
    
    
    private CCTService cctService = null;
    
    private CSTService cstService = null;

    private SDCService sdcService = null;
    

    /**
     * This is not an AJAX service method.
     *
     * @param cctService
     */
    public void setCctService(CCTService cctService) {
        this.cctService = cctService;
    }


    /**
     * This is not an AJAX service method.
     *
     * @param cstService
     */
    public void setCstService(CSTService cstService) {
        this.cstService = cstService;
    }


    /**
     * This is not an AJAX service method.
     *
     * @param sdcService
     */
    public void setSdcService(SDCService sdcService) {
        this.sdcService = sdcService;
    }


    /*
     * ------------------------------------------------------------------------
     */
    
    
    /**
     * Get all concern themes in a given SDC.
     * 
     * @param params A map contains:<br>
     *         <ul>
     *           <li>sdcId - int, the id of the given SDC object</li>
     *         </ul>
     * @return A map contains:<br>
     *         <ul>
     *           <li>successful - a boolean value denoting if the operation succeeds</li>
     *           <li>reason - reason why operation failed (valid when successful==false)</li>
     *           <li>themes - a list of Theme objects</li>
     *         </ul>
     */
    public Map getAllConcernThemes(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
    }//getAllConcernThemes()
    

    /**
     * Get all concern themes in a given SDC which relates to the current user.
     * 
     * @param params A map contains:<br>
     *         <ul>
     *           <li>sdcId - long int, the id of the given SDC object</li>
     *         </ul>
     * @return A map contains:<br>
     *         <ul>
     *           <li>successful - a boolean value denoting if the operation succeeds</li>
     *           <li>reason - reason why operation failed (valid when successful==false)</li>
     *           <li>themes - a list of Theme objects</li>
     *         </ul>
     */
    public Map getMyThemes(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
    }//getMyThemes()
    

    /**
     * Get the result of the Vote for the given theme.
     * 
     * @param params A map contains:<br>
     *         <ul>
     *           <li>sdcId - long int, the id of the given SDC object</li>
     *           <li>themeId - long int, the id of the given Theme object</li>
     *         </ul>
     * @return A map contains:<br>
     *         <ul>
     *           <li>successful - a boolean value denoting if the operation succeeds</li>
     *           <li>reason - reason why operation failed (valid when successful==false)</li>
     *           <li>theme - a Theme object</li>
     *         </ul>
     */
    public Map getConcernTheme(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
    }//getConcernTheme()
    

    /**
     * Vote to the current SDC.
     * 
     * @param params A map contains:<br>
     *         <ul>
     *           <li>sdcId - long int, the id of the given SDC object</li>
     *           <li>vote - boolean, whether or not the current user agree with the current themes</li>
     *         </ul>
     * @return A map contains:<br>
     *         <ul>
     *           <li>successful - a boolean value denoting if the operation succeeds</li>
     *           <li>reason - reason why operation failed (valid when successful==false)</li>
     *         </ul>
     */
    public Map setMyVote(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
    }//setMyVote()
    

    /**
     * Get a discussion page of .
     * 
     * @param params A map contains:<br>
     *         <ul>
     *           <li>sdcId - long int, the id of the given SDC object</li>
     *           <li>themeId - long int, the id of Theme object</li>
     *           <li>page - int, page number</li>
     *           <li>numPerPage - int, records number per page</li>
     *         </ul>
     * @return A map contains:<br>
     *         <ul>
     *           <li>successful - a boolean value denoting if the operation succeeds</li>
     *           <li>reason - reason why operation failed (valid when successful==false)</li>
     *           <li>posts - a list of DiscussionPost objects</li>
     *         </ul>
     */
    public Map getDiscussion(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
    }//getDiscussion()
    

    /**
     * Get all tags in a given SDC.
     * 
     * @param params A map contains:<br>
     *         <ul>
     *           <li>sdcId - long int, the id of the given SDC object</li>
     *         </ul>
     * @return A map contains:<br>
     *         <ul>
     *           <li>successful - a boolean value denoting if the operation succeeds</li>
     *           <li>reason - reason why operation failed (valid when successful==false)</li>
     *           <li>tags - a list of TagReference objects</li>
     *         </ul>
     */
    public Map getAllTags(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
    }//getAllTags()
    

    /**
     * Get the result of the Vote for the given theme.
     * 
     * @param params A map contains:<br>
     *         <ul>
     *           <li>sdcId - long int, the id of the given SDC object</li>
     *           <li>themeId - long int, the id of the given Theme object</li>
     *         </ul>
     * @return A map contains:<br>
     *         <ul>
     *           <li>successful - a boolean value denoting if the operation succeeds</li>
     *           <li>reason - reason why operation failed (valid when successful==false)</li>
     *           <li>agree - int, number of participants who agree.</li>
     *           <li>against - int, number of participants who against.</li>
     *         </ul>
     */
    public Map getVoteResult(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
    }//getVoteResult()
    

    /**
     * Get the concerns relates with a theme.
     * 
     * @param params A map contains:<br>
     *         <ul>
     *           <li>sdcId - long int, the id of the given SDC object</li>
     *           <li>themeId - long int, the id of the given Theme object</li>
     *           <li>page - int, page number</li>
     *           <li>numPerPage - int, records number per page</li>
     *           <li>tagFilter - string, the filter pattern for tag</li>
     *         </ul>
     * @return A map contains:<br>
     *         <ul>
     *           <li>successful - a boolean value denoting if the operation succeeds</li>
     *           <li>reason - reason why operation failed (valid when successful==false)</li>
     *           <li>concerns - a list of Concern objects</li>
     *         </ul>
     */
    public Map getThemeConcerns(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
    }//getThemeConcerns()
    

    /**
     * Create a new post for discussion of a theme.
     * 
     * @param params A map contains:<br>
     *         <ul>
     *           <li>sdcId - long int, the id of the given SDC object</li>
     *           <li>themeId - long int, the id of the given Theme object</li>
     *           <li>title - string, title of the post</li>
     *           <li>content - string, body of the post</li>
     *           <li>tags -  a list of strings</li>
     *         </ul>
     * @return A map contains:<br>
     *         <ul>
     *           <li>successful - a boolean value denoting if the operation succeeds</li>
     *           <li>reason - reason why operation failed (valid when successful==false)</li>
     *         </ul>
     */
    public Map createPost(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
    }//createPost()
    

    /**
     * ??????? Not sure about this method, yet. ?????????
     * 
     * @param params A map contains:<br>
     *         <ul>
     *           <li>sdcId - long int, the id of the given SDC object</li>
     *           <li>themeId - long int, the id of the given Theme object</li>
     *           <li>title - string, title of the post</li>
     *           <li>content - string, body of the post</li>
     *           <li>tags -  a list of strings</li>
     *         </ul>
     * @return A map contains:<br>
     *         <ul>
     *           <li>successful - a boolean value denoting if the operation succeeds</li>
     *           <li>reason - reason why operation failed (valid when successful==false)</li>
     *         </ul>
     */
    public Map getSingleDiscussion(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
    }//getSingleDiscussion()
    

    /**
     * ??????? Not sure about this method, yet. ?????????
     * 
     * @param params A map contains:<br>
     *         <ul>
     *           <li>sdcId - long int, the id of the given SDC object</li>
     *           <li>themeId - long int, the id of the given Theme object</li>
     *           <li>title - string, title of the post</li>
     *           <li>content - string, body of the post</li>
     *           <li>tags -  a list of strings</li>
     *         </ul>
     * @return A map contains:<br>
     *         <ul>
     *           <li>successful - a boolean value denoting if the operation succeeds</li>
     *           <li>reason - reason why operation failed (valid when successful==false)</li>
     *         </ul>
     */
    public Map getAllDiscussion(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
    }//getAllDiscussion()
    

}//class SDCAgent
