package org.pgist.cvo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.pgist.system.UserDAO;
import org.pgist.users.User;
import org.pgist.util.WebUtils;


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

    private TagAnalyzer analyzer = null;


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


    /**
     * This is not an AJAX service method.
     *
     * @param analyzer
     */
    public void setAnalyzer(TagAnalyzer analyzer) {
        this.analyzer = analyzer;
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
     *           <li>tags - a list of Tag objects</li>
     *           <li>reason - reason why operation failed (valid when successful==false)</li>
     *         </ul>
     * @throws Exception
     */
    public Map prepareConcern(Map params) throws Exception {
        Map map = new HashMap();

        //Temp for test
        List tags = new ArrayList();
        Tag tag = new Tag();
        tag.setDescription("tag1");
        tag.setName("tag1");
        tag.setStatus(Tag.STATUS_OFFICIAL);
        tags.add(tag);
        tag = new Tag();
        tag.setDescription("tag2");
        tag.setName("tag2");
        tag.setStatus(Tag.STATUS_OFFICIAL);
        tags.add(tag);

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
            cctService.createConcern(cct, concernObj, tags.split(","));

            Long id = WebUtils.currentUserId();
            User user = userDAO.getUserById(id, true, false);
            concernObj.setAuthor(user);

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
     *               <li>type==1, get other peopls's concerns</li>
     *             </ul>
     *           </li>
     *           <li>count - int, number of concerns to be extracted</li>
     *         </ul>
     * @return A map contains:<br>
     *         <ul>
     *           <li>successful - a boolean value denoting if the operation succeeds</li>
     *           <li>concerns - a list of Concern objects</li>
     *           <li>reason - reason why operation failed (valid when successful==false)</li>
     *         </ul>
     * @throws Exception
     */
    public Map getConcerns(Map params) throws Exception {
        Map map = new HashMap();

        Long cctId = new Long((String) params.get("cctId"));

        if(!(cctId > 0)) {
          map.put("successful", new Boolean(false));
          map.put("reason", "No CCTId is given.");
          return map;
        }

        Integer count = new Integer( (String) params.get("count"));
        if (! (count > 0))
          count = 10;

        CCT cct = cctService.getCCTById(cctId);
        if(params.get("type") != null){
          if( Integer.parseInt( (String)params.get("type")) == 0){
            map.put("concerns", cctService.getMyConcerns(cct));
            map.put("successful", new Boolean(true));
          }else{
            map.put("concerns", cctService.getOthersConcerns(cct, count));
            map.put("successful", new Boolean(true));
          }
        }else{
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
     *           <li>tags - a list of Tag objects</li>
     *           <li>reason - reason why operation failed (valid when successful==false)</li>
     *         </ul>
     * @throws Exception
     */
    public Map getTagCloud(Map params) {
        Map map = new HashMap();
        
        System.out.println("cctId ---> " + params.get("cctId"));
        System.out.println("type ---> " + params.get("type"));
        System.out.println("count ---> " + params.get("count"));
        System.out.println("threshhold ---> " + params.get("threshhold"));
        
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
                map.put("tags", tags);
            } catch(Exception e) {
                e.printStackTrace();
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
                map.put("tags", tags);
            } catch(Exception e) {
                map.put("reason", "Error: " + e.getMessage());
                return map;
            }
        } else {
            map.put("reason", "Wrong invocation type!");
        }
        
        return map;
    }//getTagCloud()


}//class CCTAgent
