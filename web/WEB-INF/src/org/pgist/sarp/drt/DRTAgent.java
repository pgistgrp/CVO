package org.pgist.sarp.drt;

import java.util.HashMap;
import java.util.Map;

import org.pgist.search.SearchHelper;
import org.pgist.system.EmailSender;
import org.pgist.system.SystemService;
import org.pgist.system.YesNoVoting;
import org.pgist.wfengine.web.WorkflowUtils;


/**
 * 
 * @author kenny
 *
 */
public class DRTAgent {
    
    
    /**
     * Spring Injected
     */
    private DRTService drtService;
    
    private SystemService systemService;
    
    private EmailSender emailSender;
    
    private SearchHelper searchHelper;
    
    private WorkflowUtils workflowUtils;
    
    
    public void setDrtService(DRTService drtService) {
        this.drtService = drtService;
    }
    
    
    public void setSystemService(SystemService systemService) {
        this.systemService = systemService;
    }


    public void setEmailSender(EmailSender emailSender) {
        this.emailSender = emailSender;
    }


    public void setSearchHelper(SearchHelper searchHelper) {
        this.searchHelper = searchHelper;
    }


    public void setWorkflowUtils(WorkflowUtils workflowUtils) {
        this.workflowUtils = workflowUtils;
    }


    /*
     * ------------------------------------------------------------------------
     */
    
    
    /**
     * Get a DiscussionPost object by the given id.
     * 
     * @param params A map contains:
     *   <ul>
     *     <li>id - int, id of a DiscussionObject object</li>
     *   </ul>
     * 
     * @return A map contains:<br>
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *     <li>comment - a Comment object (valid when successful==true)</li>
     *     <li>voting - a YesNoVoting object (may be null if the current user hasn't voted yet.)</li>
     *   </ul>
     */
    public Map getCommentById(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        Long id = null;
        try {
            id = new Long((String) params.get("id"));
            if (id==null) {
                map.put("reason", "can't find this Comment");
                return map;
            }
        } catch (Exception e) {
            map.put("reason", "can't find this Comment");
            return map;
        }
        
        try {
            Comment comment = drtService.getCommentById(id);
            
            if (comment==null) {
                map.put("reason", "can't find this comment");
                return map;
            }
            
            map.put("comment", comment);
            
            YesNoVoting voting = systemService.getVoting(YesNoVoting.TYPE_SART_DRT_COMMENT, id);
            if (voting!=null) {
                map.put("voting", voting);
            }
            
            map.put("successful", true);
        } catch (Exception e) {
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//getCommentById()
    
    
}//class DRTAgent
