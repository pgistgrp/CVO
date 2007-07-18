package org.pgist.report;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.pgist.system.Feedback;
import org.pgist.users.User;

/**
 * DWR AJAX Agent class.<br>
 * Provide AJAX services to client programs.<br>
 * In this document, all the NON-AJAX methods are marked out. So all methods
 * <span style="color:red;">without</span> such a description
 * <span style="color:red;">ARE</span> AJAX service methods.<br>
 *
 * @author John
 *
 */

public class ReportAgent {

	private ReportService reportService;

	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}
	
	
	/**
     * Vote for Final Report
     * 
     * @param a map contains:
     *   <ul>
     *     <li>suiteId - Long report suite id</li>
     *     <li>vote - string, yes or no</li>
     *   </ul>
     * 
     * @return a map contains:
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
    public Map createReportVote(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        String strSuiteId = (String) params.get("suiteId");
        String strVote = (String) params.get("vote");
        
        if (strSuiteId==null || "".equals(strSuiteId.trim())) {
            map.put("reason", "The content of suiteId can't be empty!");
            return map;
        }
        
        if (strVote==null || "".equals(strVote.trim())) {
        	map.put("reason", "The content of vote can't be empty!");
            return map;
        }

        try {
        	Long suiteId = Long.parseLong(strSuiteId);
        	boolean vote = false;
        	if(strVote.equals("yes")) {
        		vote = true;
        	}
        	
        	reportService.createReportVote(suiteId, vote);
            
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        return map;
    }
    
    
    /**
     * Get User Voted
     * 
     * @param a map contains:
     *   <ul>
     *     <li>suiteId - Long report suite id</li>
     *     <li>userId - sLong, user's id/li>
     *   </ul>
     * 
     * @return a map contains:
     *   <ul>
     *     <li>voted - a boolean value for if the user voted</li>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
    public Map getUserVoted(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        String strSuiteId = (String) params.get("suiteId");
        String strUserId = (String) params.get("userId");
        
        if (strSuiteId==null || "".equals(strSuiteId.trim())) {
            map.put("reason", "The content of suiteId can't be empty!");
            return map;
        }
        
        if (strUserId==null || "".equals(strUserId.trim())) {
        	map.put("reason", "The content of userId can't be empty!");
            return map;
        }

        try {
        	Long suiteId = Long.parseLong(strSuiteId);
        	Long userId = Long.parseLong(strUserId);
        	
        	boolean voted = reportService.getUserVoted(suiteId, userId);
        	
            map.put("voted", voted);
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
    }
    
    
    /**
     * Get Vote Stats
     * 
     * @param a map contains:
     *   <ul>
     *     <li>suiteId - Long report suite id</li>
     *   </ul>
     * 
     * @return a map contains:
     *   <ul>
     *     <li>yes - int, yes votes</li>
     *     <li>no - int, no votes</li>
     *     <li>total - int, total votes</li>
     *     <li>percentyes - int, percent yes</li>
     *     <li>percentno -int, percent no</li>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
    public Map getVoteStats(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        String strSuiteId = (String) params.get("suiteId");
        
        if (strSuiteId==null || "".equals(strSuiteId.trim())) {
            map.put("reason", "The content of suiteId can't be empty!");
            return map;
        }

        try {
        	Long suiteId = Long.parseLong(strSuiteId);
        	
        	Map voteStats = reportService.getVoteStats(suiteId);
        	        	
            map.put("yes", voteStats.get("yes"));
            map.put("no", voteStats.get("no"));
            map.put("total", voteStats.get("total"));
            map.put("percentyes",voteStats.get("percentyes"));
            map.put("percentno",voteStats.get("percentno"));
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
        }
        
        return map;
    }
    
    
} //ReportAgent
