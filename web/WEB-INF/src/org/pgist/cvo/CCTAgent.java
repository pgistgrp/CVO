package org.pgist.cvo;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.pgist.system.UserDAO;
import org.pgist.users.User;
import org.pgist.util.WebUtils;

import uk.ltd.getahead.dwr.WebContextFactory;


/**
 * DWR AJAX Agent class.<br>
 * Provide AJAX services to client programs.<br>
 * In this document, all the NON-AJAX methods are marked out. So all methods without such
 * a description <span style="color:red;">ARE</span> AJAX service methods.<br>
 *
 * @author kenny
 *
 */
public class CCTAgent {


    private CCTService cctService = null;

    private UserDAO userDAO = null;


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
     * @param userDAO
     */
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }


    /*
     * ------------------------------------------------------------------------
     */


    /**
     * Get all the CCT instances from the database.
     *
     * @param params An empty map.
     * @return A map contains:<br>
     *         <ul>
     *           <li>successful - a boolean value denoting if the operation succeeds</li>
     *           <li>ccts - a list of CCT objects</li>
     *           <li>reason - reason why operation failed (valid when successful==false)</li>
     *         </ul>
     * @throws Exception
     */
    public Map getCCTs(Map params)  throws Exception {
        Map map = new HashMap();

        Collection list = cctService.getCCTs();
        map.put("successful", new Boolean(true));
        map.put("ccts", list);

        return map;
    }//getCCTs()


    /**
     * Create a new CCT instance with the given parameters.
     *
     * @param params A map contains:<br>
     *         <ul>
     *           <li>name - String, name of the new CCT instance</li>
     *           <li>purpose - String, purpose of the new CCT instance</li>
     *           <li>instruction - String, instruction of the new CCT instance</li>
     *         </ul>
     * @return A map contains:<br>
     *         <ul>
     *           <li>successful - a boolean value denoting if the operation succeeds</li>
     *           <li>reason - reason why operation failed (valid when successful==false)</li>
     *         </ul>
     * @throws Exception
     */
    public Map createCCT(Map params) throws Exception {
        Map map = new HashMap();

        CCT cct = new CCT();
        cct.setName((String) params.get("name"));
        cct.setPurpose((String) params.get("purpose"));
        cct.setInstruction((String) params.get("instruction"));
        cct.setCreateTime(new Date());

        Long id = WebUtils.currentUserId();
        User user = userDAO.getUserById(id, true, false);
        cct.setCreator(user);

        cctService.save(cct);

        map.put("successful", new Boolean(true));

        return map;
    }//createCCT()


    /**
     * Analyze the given concern, extract and return the recognized tags from it.
     *
     * @param params A map contains:<br>
     *         <ul>
     *           <li>cctId - long int, the current CCT instance id</li>
     *           <li>concern - String, the new concern which user entered</li>
     *         </ul>
     * @return A map contains:<br>
     *         <ul>
     *           <li>successful - a boolean value denoting if the operation succeeds</li>
     *           <li>tags - a string array each element is a tag</li>
     *           <li>reason - reason why operation failed (valid when successful==false)</li>
     *         </ul>
     * @throws Exception
     */
    public Map prepareConcern(Map params) throws Exception {
        Map map = new HashMap();

        String[] tags = {"Traffic", "Transit", "Bus"}; 
        map.put("tags", tags);
        map.put("successful", new Boolean(true));

        return map;
    }//prepareConcern()


    /**
     * Save the given concern and its tags to the system.
     *
     * @param params A map contains:<br>
     *         <ul>
     *           <li>cctId - long int, the current CCT instance id</li>
     *           <li>concern - String, the new concern which user entered</li>
     *           <li>tags - String, a comma-separated tag list provided by current user</li>
     *         </ul>
     * @return A map contains:<br>
     *         <ul>
     *           <li>successful - a boolean value denoting if the operation succeeds</li>
     *           <li>concern - A newly created Concern object</li>
     *           <li>reason - reason why operation failed (valid when successful==false)</li>
     *         </ul>
     * @throws Exception
     */
    public Map saveConcern(Map params) throws Exception {
        Map map = new HashMap();
        
        Long cctId = new Long((String) params.get("cctId"));
        String concern = (String) params.get("concern");
        String tags = (String) params.get("tags");
        
        CCT cct = cctService.getCCTById(cctId);
        if (cct!=null) {
            Concern concernObj = new Concern();
            concernObj.setContent(concern);
            Long id = WebUtils.currentUserId();
            User user = userDAO.getUserById(id, true, false);
            concernObj.setAuthor(user);

            cctService.createConcern(cct, concernObj, tags.split(","));

            map.put("concern", concernObj);
        }
        map.put("successful", new Boolean(true));

        return map;
    }//saveConcern()


    /**
     * Get concerns conform to given conditions.
     *
     * @param params A map contains:<br>
     *         <ul>
     *           <li>cctId - long int, the current CCT instance id</li>
     *           <li>type - int
     *             <ul>
     *               <li>type==0, get the current user's concerns</li>
     *               <li>type==1, get other people's concerns</li>
     *               <li>type==2, get other people's concerns (randomly ordered)</li>
     *             </ul>
     *           </li>
     *           <li>count - int, number of concerns to be extracted</li>
     *         </ul>
     * @return A map contains:<br>
     *         <ul>
     *           <li>successful - a boolean value denoting if the operation succeeds</li>
     *           <li>html - a HTML source segment. (Generated by /WEB-INF/jsp/cvo/concerns.jsp)<br>
     *                  The following variables are available for use in the jsp:
     *                  <ul>
     *                    <li>concerns - A list of Concern objects</li>
     *                  </ul>
     *           </li>
     *           <li>reason - reason why operation failed (valid when successful==false)</li>
     *         </ul>
     * @throws Exception
     */
    public Map getConcerns(HttpServletRequest request, Map params) throws Exception {
        Map map = new HashMap();

        Long cctId = new Long((String) params.get("cctId"));

        if (!(cctId > 0)) {
          map.put("successful", new Boolean(false));
          map.put("reason", "No CCTId is given.");
          return map;
        }

        int count = Integer.parseInt( (String) params.get("count"));
        if (! (count > 0)) count = 10;
        
        CCT cct = cctService.getCCTById(cctId);
        
        Collection concerns = null;
        
        if (params.get("type") != null){
          if( Integer.parseInt( (String)params.get("type")) == 0) {
              concerns =  cctService.getMyConcerns(cct);
          } else if (Integer.parseInt( (String)params.get("type")) == 1) {
              concerns =  cctService.getOthersConcerns(cct, count);
          } else if (Integer.parseInt( (String)params.get("type")) == 2) {
              concerns =  cctService.getRandomConcerns(cct, count);
          } else {
              map.put("successful", new Boolean(false));
              map.put("reason", "Not sure who's concern is wanted. Please set type to 0 (current user) or 1 (others').");
          }
          
          request.setAttribute("concerns", concerns);
          
          map.put("html", WebContextFactory.get().forwardToString("/WEB-INF/jsp/cvo/concerns.jsp"));
          
          map.put("successful", new Boolean(true));
        } else {
          map.put("successful", new Boolean(false));
          map.put("reason", "Not sure who's concern is wanted. Please set type to 0 (current user) or 1 (others').");
        }

        return map;
    }//getConcerns()


    /**
     * Get the tag cloud of the current CCT instance.
     *
     * @param params A map contains:<br>
     *         <ul>
     *           <li>cctId - long int, the current CCT instance id</li>
     *           <li>type - int
     *             <ul>
     *               <li>type==0, search top count tags which are the hotest</li>
     *               <li>type==1, search tags which are over a specific threshhold</li>
     *             </ul>
     *           </li>
     *           <li>count - valid when type==0, default is 10</li>
     *           <li>threshhold - valid when type==1, default is 2</li>
     *         </ul>
     * @return A map contains:<br>
     *         <ul>
     *           <li>successful - a boolean value denoting if the operation succeeds</li>
     *           <li>tags - a list of TagReference objects</li>
     *           <li>reason - reason why operation failed (valid when successful==false)</li>
     *         </ul>
     */
    public Map getTagCloud(Map params) {
        Map map = new HashMap();
        
        int type = -1;
        CCT cct = null;
        
        try {
            type = Integer.parseInt((String) params.get("type"));
            Long cctId = new Long((String) params.get("cctId"));
            cct = cctService.getCCTById(cctId);
        } catch(Exception e) {
            e.printStackTrace();
            map.put("successful", false);
            if (type==-1) map.put("reason", "Wrong invocation type!");
            if (cct==null) map.put("reason", "No CCTId is given.");
            return map;
        }
        
        if (type==0) {
            int count = -1;
            try {
                count = Integer.parseInt((String) params.get("count"));
                if (count<1) count = 2;
            } catch(Exception e) {
                e.printStackTrace();
                count = 2;
            }
            
            try {
                Collection tags = cctService.getTagsByRank(cct, count);
                map.put("successful", true);
                map.put("tags", tags);
            } catch(Exception e) {
                e.printStackTrace();
                map.put("successful", false);
                map.put("reason", "Error: " + e.getMessage());
                return map;
            }
        } else if (type==1) {
            int threshhold = -1;
            try {
                threshhold = Integer.parseInt((String) params.get("count"));
                if (threshhold<1) threshhold = 10;
                else if (threshhold>100) threshhold = 100;
            } catch(Exception e) {
                threshhold = 10;
            }
            
            try {
                Collection tags = cctService.getTagsByThreshold(cct, threshhold);
                map.put("successful", true);
                map.put("tags", tags);
            } catch(Exception e) {
                map.put("successful", false);
                map.put("reason", "Error: " + e.getMessage());
                return map;
            }
        } else {
            map.put("reason", "Wrong invocation type!");
        }
        
        return map;
    }//getTagCloud()
    
    
    /**
     * Get concerns attached to a tag.
     * 
     * @param params A map contains:<br>
     *         <ul>
     *           <li>tagRefId - long int, the TagReference instance id</li>
     *           <li>count - the Max concerns to get, default is 10. -1 denotes no limit.</li>
     *         </ul>
     * @return A map contains:<br>
     *         <ul>
     *           <li>successful - a boolean value denoting if the operation succeeds</li>
     *           <li>html - a HTML source segment. (Generated by /WEB-INF/jsp/cvo/tagConcerns.jsp)<br>
     *                  The following variables are available for use in the jsp:
     *                  <ul>
     *                    <li>concerns - A list of Concern objects</li>
     *                  </ul>
     *           </li>
     *           <li>reason - reason why operation failed (valid when successful==false)</li>
     *         </ul>
     */
    public Map getConcernsByTag(Map params) {
        Map map = new HashMap();
        
        
        
        return map;
    }//getConcernsByTag()


}//class CCTAgent
