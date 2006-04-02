package org.pgist.ws;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pgist.model.Discussible;
import org.pgist.util.WebUtils;


/**
 * 
 * @author kenny
 *
 */
public class DiscussionAgent {
    
    
    private DiscussionService discussionService = null;
    
    
    public void setDiscussionService(DiscussionService discussionService) {
        this.discussionService = discussionService;
    }


    /*
     * ------------------------------------------------------------------------
     */
    
    
    /**
     * INPUT:<br>
     *   id - id of Discussible<br>
     *   count - maximus posts to be grabed
     * OUTPUT:<br>
     *   successful - whether operation is successful<br>
     *   briefList - list of posts (Only when successful==true)<br>
     *   reason - reason why operation failed (Only when successful==false)<br>
     * 
     * @param request
     * @param response
     * @param params
     * @return
     * @throws Exception
     */
    public Map getDiscussionBrief(Map params) throws Exception {
        Map map = new HashMap();
        
        Collection briefList = null;
        
        try {
            Long id = new Long((String)map.get("id"));
            int count = Integer.parseInt((String)map.get("count"));
            briefList = discussionService.getBriefList(id, count);
        } catch(Exception e) {
            map.put("successful", new Boolean(false));
            map.put("reason", e.getMessage());
        }
        
        map.put("briefList", briefList);
        map.put("successful", new Boolean(true));
        
        return map;
    }//getDiscussionBrief()
    
    
    /**
     * 
     * @param request
     * @param response
     * @param params
     * @return
     * @throws Exception
     */
    public Map postComment(HttpServletRequest request, HttpServletResponse response, Map params) throws Exception {
        Map map = new HashMap();
        
        try {
            Long id = new Long((String) map.get("id"));
            String content = (String) map.get("content");
            if (content!=null && "".equals(content.trim())) {
                //Get the discussible
            }
        } catch(Exception e) {
            map.put("successful", new Boolean(false));
            map.put("reason", e.getMessage());
        }
        
        map.put("successful", new Boolean(true));
        
        return map;
    }//postComment()
    
    
}//class DiscussionAgent
