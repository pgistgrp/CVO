package org.pgist.cvo;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.directwebremoting.WebContextFactory;
import org.pgist.system.SystemService;
import org.pgist.system.UserDAO;
import org.pgist.system.YesNoVoting;
import org.pgist.tagging.Tag;
import org.pgist.users.User;
import org.pgist.util.PageSetting;
import org.pgist.util.StringUtil;
import org.pgist.util.WebUtils;


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
public class CCTAgent {


    private CCTService cctService = null;

    private UserDAO userDAO = null;
    
    private SystemService systemService;


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
    public void setSystemService(SystemService systemService) {
        this.systemService = systemService;
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
    public Map getCCTs(Map params) throws Exception {
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
     */
    public Map createCCT(Map params) {
        Map map = new HashMap();
        map.put("successful", false);

        String name = (String) params.get("name");
        if (name == null || "".equals(name.trim())) {
            map.put("reason", "name can not be empty.");
            return map;
        }

        String purpose = (String) params.get("purpose");
        if (purpose == null || "".equals(purpose.trim())) {
            map.put("reason", "purpose can not be empty.");
            return map;
        }

        String instruction = (String) params.get("instruction");
        if (instruction == null || "".equals(instruction.trim())) {
            map.put("reason", "instruction can not be empty.");
            return map;
        }

        try {
            CCT cct = cctService.createCCT(name, purpose, instruction);
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }

        return map;
    }//createCCT()


    /**
     * Analyze the given concern, extract and return the recognized tags from it.
     *
     * @param params A map contains:<br>
     *         <ul>
     *           <li>concern - A string, the new concern which user entered</li>
     *         </ul>
     * @return A map contains:<br>
     *         <ul>
     *           <li>successful - a boolean value denoting if the operation succeeds</li>
     *           <li>tags - a string array each element is name for an existing tag</li>
     *           <li>potentialtags - a string array each element is a possible tag name</li>
     *           <li>reason - reason why operation failed (valid when successful==false)</li>
     *         </ul>
     */
    public Map prepareConcern(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            String concern = (String) params.get("concern");
            
            String[][] tags = cctService.getSuggestedTags(concern);
            
            map.put("tags", tags[0]);
            map.put("potentialtags", tags[1]);
            
            map.put("successful", new Boolean(true));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return map;
    }//prepareConcern()


    /**
     * Save the given new concern and its tags to the system.
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
     */
    public Map saveConcern(Map params) {
        Map map = new HashMap();
        map.put("successful", false);

        Long cctId = new Long((String) params.get("cctId"));

        String concern = (String) params.get("concern");
        if (concern == null || "".equals(concern.trim())) {
            map.put("reason", "concern can not be empty.");
            return map;
        }

        String tags = (String) params.get("tags");

        try {
            Concern c = cctService.createConcern(cctId, concern, tags.split(","));
            map.put("concern", c);
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }

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
     *           <li>page - int, the page number of concerns to be extracted (only valid when type==2)</li>
     *         </ul>
     * @return A map contains:<br>
     *         <ul>
     *           <li>successful - a boolean value denoting if the operation succeeds</li>
     *           <li>html - a HTML source segment. (Generated by /WEB-INF/jsp/cvo/concerns.jsp)<br>
     *                  The following variables are available for use in the jsp:
     *                  <ul>
     *                    <li>concerns - A list of Concern objects</li>
     *                    <li>setting - A PageSetting objects</li>
     *                  </ul>
     *           </li>
     *           <li>count - int, number of concerns actually extracted (valid when successful==true)</li>
     *           <li>total - int, number of total my/other concerns in the database (valid when successful==true)</li>
     *           <li>reason - reason why operation failed (valid when successful==false)</li>
     *         </ul>
     * @throws Exception
     */
    public Map getConcerns(HttpServletRequest request, Map params) throws
            Exception {
        Map map = new HashMap();

        Long cctId = new Long((String) params.get("cctId"));

        if (!(cctId > 0)) {
            map.put("successful", new Boolean(false));
            map.put("reason", "No CCTId is given.");
            return map;
        }

        int count = Integer.parseInt((String) params.get("count"));
        if (!(count > 0)) count = 10;

        CCT cct = cctService.getCCTById(cctId);

        Collection concerns = null;
        Integer type = null;
        String url = "";

        try {
            type = new Integer((String) params.get("type"));
            switch (type.intValue()) {
            case 0:
                concerns = cctService.getMyConcerns(cct);
                map.put("total", "" + cctService.getConcernsTotal(cct, 1));

                request.setAttribute("showIcon", new Boolean(true));

                url = "/WEB-INF/jsp/cvo/concerns.jsp";
                break;
            case 1:
                concerns = cctService.getOthersConcerns(cct, count);
                map.put("total", "" + cctService.getConcernsTotal(cct, 2));

                request.setAttribute("showIcon", new Boolean(false));

                url = "/WEB-INF/jsp/cvo/concerns.jsp";
                break;
            case 2:
                PageSetting setting = new PageSetting();
                setting.setRowOfPage(count);
                try {
                    setting.setPage(Integer.parseInt((String) params.get("page")));
                } catch (Exception e) {
                    setting.setPage(1);
                }
                concerns = cctService.getRandomConcerns(cct, setting);
                map.put("total", "" + setting.getRowSize());

                request.setAttribute("setting", setting);
                request.setAttribute("showIcon", new Boolean(false));

                url = "/WEB-INF/jsp/cvo/concerns.jsp";
                break;
            default:
                map.put("successful", new Boolean(false));
                map.put("reason", "Not sure who's concern is wanted. Please set type to 0 (current user) or 1 (others').");
                return map;
            }

            request.setAttribute("showTitle", new Boolean(false));

            request.setAttribute("concerns", concerns);
            request.setAttribute("type", type);

            map.put("html", WebContextFactory.get().forwardToString(url));
            map.put("successful", new Boolean(true));
        } catch (Exception e) {
            e.printStackTrace();
            map.put("successful", new Boolean(false));
            if (type == null) {
                map.put("reason", "Not sure who's concern is wanted. Please set type to 0 (current user) or 1 (others').");
            } else {
                map.put("reason", e.getMessage());
            }
            return map;
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
     *               <li>type==0, search top count tags which are the hottest</li>
     *               <li>type==1, search tags which are over a specific threshhold</li>
     *               <li>type==2, search all tags</li>
     *             </ul>
     *           </li>
     *           <li>count - valid when type==0, default is 10; or type==2, default is -1</li>
     *           <li>page - valid when type==2, default is 1</li>
     *           <li>threshhold - valid when type==1, default is 2</li>
     *         </ul>
     * @return A map contains:<br>
     *         <ul>
     *           <li>successful - a boolean value denoting if the operation succeeds</li>
     *           <li>html - a HTML source segment. (Generated by /WEB-INF/jsp/cvo/tagCloud.jsp)<br>
     *                  The following variables are available for use in the jsp:
     *                  <ul>
     *                    <li>tags - a list of TagReference objects</li>
     *                    <li>setting - An PageSetting object</li>
     *                  </ul>
     *           </li>
     *           <li>reason - reason why operation failed (valid when successful==false)</li>
     *         </ul>
     */
    public Map getTagCloud(HttpServletRequest request, Map params) {
        Map map = new HashMap();

        int type = -1;
        CCT cct = null;

        try {
            type = Integer.parseInt((String) params.get("type"));
            Long cctId = new Long((String) params.get("cctId"));
            cct = cctService.getCCTById(cctId);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("successful", false);
            if (type == -1) map.put("reason", "Wrong invocation type!");
            if (cct == null) map.put("reason", "No CCTId is given.");
            return map;
        }

        if (type == 0) {
            int count = -1;
            try {
                count = Integer.parseInt((String) params.get("count"));
                if (count < 1) count = 2;
            } catch (Exception e) {
                e.printStackTrace();
                count = 2;
            }
            
            try {
                Collection tags = cctService.getTagsByRank(cct, count);
                request.setAttribute("tags", tags);
                map.put("html", WebContextFactory.get().forwardToString("/WEB-INF/jsp/cvo/tagCloud.jsp"));
                map.put("successful", true);
            } catch (Exception e) {
                e.printStackTrace();
                map.put("successful", false);
                map.put("reason", "Error: " + e.getMessage());
                return map;
            }
        } else if (type == 1) {
            int threshhold = -1;
            try {
                threshhold = Integer.parseInt((String) params.get("count"));
                if (threshhold < 1) threshhold = 10;
                else if (threshhold > 100) threshhold = 100;
            } catch (Exception e) {
                threshhold = 10;
            }

            try {
                Collection tags = cctService.getTagsByThreshold(cct, threshhold);
                request.setAttribute("tags", tags);
                map.put("html", WebContextFactory.get().forwardToString("/WEB-INF/jsp/cvo/tagCloud.jsp"));
                map.put("successful", true);
            } catch (Exception e) {
                map.put("successful", false);
                map.put("reason", "Error: " + e.getMessage());
                return map;
            }
        } else if (type == 2) {
            try {
                PageSetting setting = new PageSetting();
                setting.setRowOfPage((String) params.get("count"));
                setting.setPage((String) params.get("page"));
                
                Collection tags = cctService.getTagCloud(cct, setting);
                request.setAttribute("tags", tags);
                request.setAttribute("setting", setting);
                map.put("html", WebContextFactory.get().forwardToString("/WEB-INF/jsp/cvo/tagCloud.jsp"));
                
                map.put("successful", true);
            } catch (Exception e) {
                map.put("reason", e.getMessage());
            }
        } else {
            map.put("reason", "Wrong invocation type!");
        }

        return map;
    }//getTagCloud()


    /**
     * Get a Concern object with the given id.
     * @param id the id of Concern object.
     * @return A map contains:<br>
     *         <ul>
     *           <li>successful - a boolean value denoting if the operation succeeds</li>
     *           <li>concern - a Concern object. (valid when successful==true)</li>
     *           <li>reason - reason why operation failed (valid when successful==false)</li>
     *           <li>voting - a YesNoVoting object (may be null if the current user hasn't voted yet.)</li>
     *         </ul>
     */
    public Map getConcernById(Long id) {
        Map map = new HashMap();

        try {
            Concern concern = cctService.getConcernById(id);
            
            if (concern == null) {
                map.put("successful", false);
                map.put("reason", "concern not found with id " + id);
                return map;
            }
            
            YesNoVoting voting = systemService.getVoting(YesNoVoting.TYPE_CONCERN, id);
            if (voting!=null) {
                map.put("voting", voting);
            }
            
            map.put("successful", true);
            map.put("concern", concern);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("successful", false);
            map.put("reason", e.getMessage());
        }

        return map;
    }//getConcernById


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
     *                    <li>tagRef - The TagReference objects</li>
     *                    <li>concerns - A list of Concern objects</li>
     *                  </ul>
     *           </li>
     *           <li>reason - reason why operation failed (valid when successful==false)</li>
     *         </ul>
     */
    public Map getConcernsByTag(HttpServletRequest request, Map params) {
        Map map = new HashMap();

        Long tagRefId = null;
        int count = -1;

        try {
            tagRefId = new Long((String) params.get("tagRefId"));
            count = Integer.parseInt((String) params.get("count"));
        } catch (NumberFormatException e) {
            if (tagRefId == null) {
                map.put("successful", new Boolean(false));
                map.put("reason", "tagRefId is required!");
                return map;
            }
        }

        try {
            Collection concerns = cctService.getConcernsByTag(tagRefId, count);
            TagReference tagRef = cctService.getTagReferenceById(tagRefId);

            request.setAttribute("showIcon", new Boolean(false));
            request.setAttribute("showTitle", new Boolean(true));
            request.setAttribute("tagRef", tagRef);
            request.setAttribute("concerns", concerns);

            map.put("html",
                    WebContextFactory.get().forwardToString(
                            "/WEB-INF/jsp/cvo/concerns.jsp"));
        } catch (Exception e) {
            e.printStackTrace();
            map.put("successful", new Boolean(false));
            map.put("reason", e.getMessage());
            return map;
        }

        map.put("successful", new Boolean(true));

        return map;
    }//getConcernsByTag()


    /**
     * Edit the given Concern object. Before edit, the current user will be check if he is the author of
     * this concern.
     *
     * @param params A map contains:<br>
     *         <ul>
     *           <li>concernId - long int, the Concern instance id</li>
     *           <li>concern - A string, the concern which user edited, can be null or "" after trimed, means don't edit the concern string.</li>
     *         </ul>
     * @return A map contains:<br>
     *         <ul>
     *           <li>successful - a boolean value denoting if the operation succeeds</li>
     *           <li>reason - reason why operation failed (valid when successful==false)</li>
     *         </ul>
     */
    public Map editConcern(Map params) {
        Map map = new HashMap();

        Long concernId = null;
        Concern concern = null;

        String newConcern = (String) params.get("concern");
        if (newConcern == null || "".equals(newConcern.trim())) {
            map.put("successful", new Boolean(false));
            map.put("reason", "Concern string can't be empty.");
            return map;
        }

        try {
            concernId = new Long((String) params.get("concernId"));
            concern = cctService.getConcernById(concernId);
            if (concern.isDeleted()) {
                map.put("successful", new Boolean(false));
                map.put("reason", "This concern is already deleted.");
                return map;
            }
        } catch (Exception e) {
            map.put("successful", new Boolean(false));
            if (concernId == null) {
                map.put("reason", "concernId is required.");
            } else {
                map.put("reason",
                        "failed to extract concern object with id " + concernId);
            }
            return map;
        }

        //Check if the current user is the author of this concern.
        try {
            Long userId = WebUtils.currentUserId();
            User user = userDAO.getUserById(userId, true, false);
            if (user.getId().doubleValue() == concern.getAuthor().getId()) {
                concern.setContent(newConcern);
                concern.setCreateTime(new Date());
                cctService.save(concern);
                map.put("successful", new Boolean(true));
            } else {
                map.put("successful", new Boolean(false));
                map.put("reason", "You have no right to edit this concern.");
                return map;
            }
        } catch (Exception e) {
            map.put("successful", new Boolean(false));
            map.put("reason", e.getMessage());
            return map;
        }

        return map;
    }//editConcern()


    /**
     * Delete the given Concern object. Before delete, the current user will be check if he is the author of
     * this concern.
     * @param params A map contains:<br>
     *         <ul>
     *           <li>concernId - long int, the Concern instance id</li>
     *         </ul>
     * @return A map contains:<br>
     *         <ul>
     *           <li>successful - a boolean value denoting if the operation succeeds</li>
     *           <li>reason - reason why operation failed (valid when successful==false)</li>
     *         </ul>
     */
    public Map deleteConcern(Map params) {
        Map map = new HashMap();

        Long concernId = null;
        Concern concern = null;

        try {
            concernId = new Long((String) params.get("concernId"));
            concern = cctService.getConcernById(concernId);
            if (concern.isDeleted()) {
                map.put("successful", new Boolean(false));
                map.put("reason", "This concern is already deleted.");
                return map;
            }
        } catch (Exception e) {
            map.put("successful", new Boolean(false));
            if (concernId == null) {
                map.put("reason", "concernId is required.");
            } else {
                map.put("reason",
                        "failed to extract concern object with id " + concernId);
            }
            return map;
        }

        //Check if the current user is the author of this concern.
        try {
            Long userId = WebUtils.currentUserId();
            User user = userDAO.getUserById(userId, true, false);
            if (user.getId().doubleValue() == concern.getAuthor().getId()) {
                cctService.deleteConcern(concern);
                map.put("successful", new Boolean(true));
            } else {
                map.put("successful", new Boolean(false));
                map.put("reason", "You have no right to edit this concern.");
                return map;
            }
        } catch (Exception e) {
            map.put("successful", new Boolean(false));
            map.put("reason", e.getMessage());
            return map;
        }

        return map;
    }//deleteConcern()


    /**
     * Delete the given TagReference objects from the given Concern object. Before delete, the current user will be check if he is the author of
     * this concern.
     *
     * @param params A map contains:<br>
     *         <ul>
     *           <li>concernId - long int, the Concern instance id</li>
     *           <li>tags - A string, a comma-separated tag name list which is the final tags</li>
     *         </ul>
     * @return A map contains:<br>
     *         <ul>
     *           <li>successful - a boolean value denoting if the operation succeeds</li>
     *           <li>reason - reason why operation failed (valid when successful==false)</li>
     *         </ul>
     */
    public Map editTags(Map params) {
        Map map = new HashMap();

        Long concernId = null;
        Concern concern = null;

        try {
            concernId = new Long((String) params.get("concernId"));
            concern = cctService.getConcernById(concernId);
            if (concern.isDeleted()) {
                map.put("successful", new Boolean(false));
                map.put("reason", "This concern is already deleted.");
                return map;
            }
        } catch (Exception e) {
            map.put("successful", new Boolean(false));
            if (concernId == null) {
                map.put("reason", "concernId is required.");
            } else {
                map.put("reason",
                        "failed to extract concern object with id " + concernId);
            }
            return map;
        }

        //Check if the current user is the author of this concern.
        try {
            Long userId = WebUtils.currentUserId();
            User user = userDAO.getUserById(userId, true, false);
            String tagStr = (String) params.get("tags");
            String tags[] = null;
            if (tagStr == null) tagStr = "";
            tags = tagStr.trim().split(",");
            if (user.getId().doubleValue() == concern.getAuthor().getId()) {
                concern.setCreateTime(new Date());
                cctService.editConcernTags(concern, tags);
                map.put("successful", new Boolean(true));
            } else {
                map.put("successful", new Boolean(false));
                map.put("reason", "You have no right to edit this concern.");
                return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("successful", new Boolean(false));
            map.put("reason", e.getMessage());
            return map;
        }

        return map;
    }//editTags()

    
    /**
     * Search all matched tags in the given CCT by tag name. Approximate match is used for the tag string.
     *
     * @param params A map contains:<br>
     *         <ul>
     *           <li>cctId - long int, the current CCT instance id</li>
     *           <li>tag - A string, all or part of a Tag name</li>
     *         </ul>
     * @return A map contains:<br>
     *         <ul>
     *           <li>successful - a boolean value denoting if the operation succeeds</li>
     *           <li>count - amount of matched Tag objects (valid when successful==true)</li>
     *           <li>html - a HTML source segment. (valid when count>0) <br>
     *                  (Generated by /WEB-INF/jsp/cvo/tagSearch.jsp)<br>
     *                  The following variables are available for use in the jsp:
     *                  <ul>
     *                    <li>tags - A list of TagReference objects</li>
     *                  </ul>
     *           </li>
     *           <li>reason - reason why operation failed (valid when successful==false)</li>
     *         </ul>
     */
    public Map searchTags(HttpServletRequest request, Map params) {
        Map map = new HashMap();

        CCT cct = null;

        try {
            Long cctId = new Long((String) params.get("cctId"));
            cct = cctService.getCCTById(cctId);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("successful", false);
            if (cct == null) map.put("reason", "No cctId is given.");
            else map.put("reason", e.getMessage());
            return map;
        }

        String tag = (String) params.get("tag");
        if (tag == null || tag.trim().equals("")) {
            map.put("successful", new Boolean(false));
            map.put("reason", "tag string to be searched is required!");
        }

        try {
            Collection tags = cctService.searchTags(cct, tag);
            map.put("count", tags.size());
            if (tags.size() > 0) {
                request.setAttribute("tags", tags);
                map.put("html",
                        WebContextFactory.get().forwardToString(
                                "/WEB-INF/jsp/cvo/tagSearch.jsp"));
            }
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("successful", false);
            map.put("reason", e.getMessage());
        }

        return map;
    }//searchTags()

    /**
     * Get a specific tag based upon the tagReferenceID.
     * 
     * @param id Long<br>
     * 
     * @return A map contains:<br>
     *         <ul>
     *           <li>successful - a boolean value denoting if the operation succeeds</li>
     *           <li>count - amount of matched Tag objects (valid when successful==true)</li>
     *           <li>tag - The Tag</li>
     *           <li>html - a HTML source segment. (valid when count>0) <br>
     *                  (Generated by /WEB-INF/jsp/cvo/tagSearch.jsp)<br>
     *                  The following variables are available for use in the jsp:
     *                  <ul>
     *                    <li>tags - A list of TagReference objects</li>
     *                  </ul>
     *           </li>
     *           <li>reason - reason why operation failed (valid when successful==false)</li>
     *         </ul>
     */
    public Map getTagByTagRefId(String strTagId) {
    	Map map = new HashMap();
        map.put("successful", false);
  
        Long tagId = Long.parseLong(strTagId);
    	try {
	    	TagReference ref = cctService.getTagReferenceById(tagId);
	    	Tag myTag = ref.getTag();
	    	//request.setAttribute("tag", myTag);
	    	
	    	map.put("tag", myTag);
	    	
	    	map.put("successful", true);    	
    	} catch (Exception e) {
    		e.printStackTrace();
            map.put("reason", e.getMessage());  		
    	}
    	return map;
    } //getTagByTagRefId()
    

    /**
     * Get conerns in current CCT context.
     * 
     * @param params A map contains:
     *   <ul>
     *     <li>cctId - long int, the current CCT instance id</li>
     *     <li>filter - string, tag name as filter (Optional)</li>
     *     <li>count - number of records shown per page. Optional, default is -1, means all records.</li>
     *     <li>page - page number. Optional, default is 1.</li>
     *     <li>sorting - int, the sorting index, 1-7, referencing CCTDAOImpl.java for the meaning</li>
     *     <li>
     *       type - string, types of searching
     *       <ul>
     *         <li>empty or "all" - show all concerns</li>
     *         <li>"owner" - show only the current user's concerns</li>
     *         <li>"other" - show only the other user's concerns</li>
     *       </ul>
     *     </li>
     *   </ul>
     * 
     * @return A map contains:
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *     <li>html - a HTML source segment. (valid when count>0) <br>
     *       (Generated by /WEB-INF/jsp/cvo/contextConcerns.jsp)<br>
     *       The following variables are available for use in the jsp:
     *       <ul>
     *         <li>concerns - A list of Concern objects, each concern's "object" field may be a YesNoVoting object, or null.</li>
     *         <li>setting - An PageSetting object</li>
     *         <li>contextAware - boolean</li>
     *       </ul>
     *     </li>
     *   </ul>
     * 
     */
    public Map getContextConcerns(HttpServletRequest request, Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        CCT cct = null;

        try {
            Long cctId = new Long((String) params.get("cctId"));
            cct = cctService.getCCTById(cctId);
        } catch (Exception e) {
            e.printStackTrace();
            if (cct == null) map.put("reason", "No cctId is given.");
            else map.put("reason", e.getMessage());
            return map;
        }
        
        String filter = (String) params.get("filter");
        
        try {
            int sorting = 0;
            try {
                sorting = Integer.parseInt((String) params.get("sorting"));
            } catch (Exception e) {
            }
            
            String type = (String) params.get("type");
            if (type==null || "".equals(type)) type = "all";
            
            PageSetting setting = new PageSetting();
            setting.setRowOfPage((String) params.get("count"));
            setting.setPage((String) params.get("page"));
            
            Collection concerns = cctService.getContextConcerns(cct, setting, filter, type, sorting);
            
            request.setAttribute("concerns", concerns);
            request.setAttribute("setting", setting);
            request.setAttribute("filter", filter);
            request.setAttribute("type", type);
            request.setAttribute("sorting", sorting);
            
            map.put("html", WebContextFactory.get().forwardToString("/WEB-INF/jsp/cvo/contextConcerns.jsp"));
            
            map.put("successful", true);        
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());          
        }
        
        return map;
    }//getContextConcerns()
    
    
    /**
     * Set the voting choice on the given concern.
     * 
     * @param params A map contains:
     *   <ul>
     *     <li>id - int, id of the Concern object. Required.</li>
     *     <li>agree - string, "true" or "false". Whether or not the current user agree with the current object.</li>
     *   </ul>
     *   
     * @return A map contains:<br>
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
    public Map setVoting(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        Long id = null;
        try {
            id = new Long((String) params.get("id"));
        } catch (Exception e) {
            map.put("reason", "id is required.");
            return map;
        }
        
        boolean agree = "true".equalsIgnoreCase((String) params.get("agree"));
        
        try {
            cctService.setVotingOnConcern(id, agree);
            
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//setVoting()
    
    
    /**
     * Get the comments on the given concern.
     * 
     * @param params A map contains:
     *   <ul>
     *     <li>concernId - int, id of the Concern object. Required.</li>
     *     <li>page - int, current page number.</li>
     *     <li>count - int, total number of pages.</li>
     *   </ul>
     *   
     * @return A map contains:<br>
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *     <li>html - a HTML source segment. <br>
     *       (Generated by /WEB-INF/jsp/cvo/comments.jsp)<br>
     *       The following variables are available for use in the jsp:
     *       <ul>
     *         <li>concern - a Concern object, "object" field may be a YesNoVoting object, or null.</li>
     *         <li>comments - A list of Comment objects, each concern's "object" field may be a YesNoVoting object, or null.</li>
     *         <li>setting - An PageSetting object</li>
     *       </ul>
     *     </li>
     *   </ul>
     */
    public Map getComments(HttpServletRequest request, Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        Long concernId = null;

        try {
            concernId = new Long((String) params.get("concernId"));
        } catch (Exception e) {
            map.put("reason", "concernId is required.");
            return map;
        }
        
        try {
            PageSetting setting = new PageSetting();
            setting.setPage((String) map.get("page"));
            setting.setRowOfPage((String) map.get("count"));
            
            Concern concern = cctService.getConcernById(concernId);
            YesNoVoting voting = systemService.getVoting(YesNoVoting.TYPE_CONCERN, concernId);
            if (voting!=null) concern.setObject(voting);
            
            Collection comments = cctService.getComments(concernId, setting);
            for (Comment comment : (Collection<Comment>) comments) {
                voting = systemService.getVoting(YesNoVoting.TYPE_COMMENT, comment.getId());
                if (voting!=null) comment.setObject(voting);
            }
            
            request.setAttribute("concern", concern);
            request.setAttribute("comments", comments);
            
            map.put("html", WebContextFactory.get().forwardToString("/WEB-INF/jsp/cvo/comments.jsp"));
            
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//getComments()
    
    
    /**
     * Create a comment on the given concern.
     * 
     * @param params A map contains:
     *   <ul>
     *     <li>concernId - int, id of the Concern object. Required.</li>
     *     <li>title - string, title of the comment. Required.</li>
     *     <li>content - string, content of the comment. Required.</li>
     *     <li>tags - string, comma separated tag name list. Required.</li>
     *   </ul>
     *   
     * @return A map contains:<br>
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
    public Map createComment(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        Long concernId = null;

        try {
            concernId = new Long((String) params.get("concernId"));
        } catch (Exception e) {
            map.put("reason", "concernId is required.");
            return map;
        }
        
        String title = (String) params.get("title");
        String content = (String) params.get("content");
        
        if (title==null || content==null) {
            map.put("reason", "title and content can't be empty");
            return map;
        }
        
        title = title.trim();
        content = content.trim();
        
        if (title.length()==0 || content.length()==0) {
            map.put("reason", "title and content can't be empty");
            return map;
        }
        
        String[] tags = StringUtil.splitCSL((String) params.get("tags"));
        
        try {
            Comment comment = cctService.createComment(concernId, title, content, tags);
            
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//createComment()
    
    
    /**
     * Edit the given comment.
     * 
     * @param params A map contains:
     *   <ul>
     *     <li>commentId - int, id of the Comment object. Required.</li>
     *     <li>title - string, title of the comment. Required.</li>
     *     <li>content - string, content of the comment. Required.</li>
     *     <li>tags - string, comma separated tag name list. Required.</li>
     *   </ul>
     *   
     * @return A map contains:<br>
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
    public Map editComment(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        Long commentId = null;

        try {
            commentId = new Long((String) params.get("commentId"));
        } catch (Exception e) {
            map.put("reason", "commentId is required.");
            return map;
        }
        
        String title = (String) params.get("title");
        String content = (String) params.get("content");
        
        if (title==null || content==null) {
            map.put("reason", "title and content can't be empty");
            return map;
        }
        
        title = title.trim();
        content = content.trim();
        
        if (title.length()==0 || content.length()==0) {
            map.put("reason", "title and content can't be empty");
            return map;
        }
        
        String[] tags = StringUtil.splitCSL((String) params.get("tags"));
        
        try {
            cctService.editComment(commentId, title, content, tags);
            
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//editComment()
    
    
    /**
     * Delete the given comment.
     * 
     * @param params A map contains:
     *   <ul>
     *     <li>commentId - int, id of the Concern object. Required.</li>
     *   </ul>
     *   
     * @return A map contains:<br>
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
    public Map deleteComment(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        Long commentId = null;

        try {
            commentId = new Long((String) params.get("commentId"));
        } catch (Exception e) {
            map.put("reason", "commentId is required.");
            return map;
        }
        
        try {
            cctService.deleteComment(commentId);
            
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//deleteComment()
    
    
    /**
     * Set voting to the given comment.
     * 
     * @param params A map contains:
     *   <ul>
     *     <li>id - int, id of the Comment object. Required.</li>
     *     <li>agree - string, "true" or "false". Whether or not the current user agree with the current object.</li>
     *   </ul>
     *   
     * @return A map contains:<br>
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
    public Map setCommentVoting(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        Long id = null;
        try {
            id = new Long((String) params.get("id"));
        } catch (Exception e) {
            map.put("reason", "id is required.");
            return map;
        }
        
        boolean agree = "true".equalsIgnoreCase((String) params.get("agree"));
        
        try {
            cctService.setVotingOnComment(id, agree);
            
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//setCommentVoting()
    
    
}//class CCTAgent
