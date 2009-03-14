package org.pgist.sarp.report;

import java.util.HashMap;
import java.util.Map;

import org.pgist.sarp.drt.Comment;
import org.pgist.search.SearchHelper;
import org.pgist.system.EmailSender;
import org.pgist.system.SystemService;
import org.pgist.system.YesNoVoting;


public class ReportAgent {
    
    
    private ReportService reportService = null;
    
    private SystemService systemService = null;
    
    private SearchHelper searchHelper;
    
    private EmailSender emailSender;
    
    
    public void setEmailSender(EmailSender emailSender) {
        this.emailSender = emailSender;
    }


    public void setReportService(ReportService reportService) {
        this.reportService = reportService;
    }


    public void setSystemService(SystemService systemService) {
        this.systemService = systemService;
    }


    public void setSearchHelper(SearchHelper searchHelper) {
        this.searchHelper = searchHelper;
    }


    /*
     * ------------------------------------------------------------------------
     */

    
    /**
     * Get a DRT Comment object by the given id.
     * 
     * @param params A map contains:
     *   <ul>
     *     <li>id - int, id of a report object</li>
     *     <li>content - string, content of the report</li>
     *   </ul>
     * 
     * @return A map contains:<br>
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
    public Map saveReport(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        Long id = null;
        try {
            id = new Long((String) params.get("id"));
            if (id==null) {
                map.put("reason", "can't find this report");
                return map;
            }
        } catch (Exception e) {
            map.put("reason", "can't find this report");
            return map;
        }
        
        String content = (String) params.get("content");
        
        try {
            reportService.saveReportContent(id, content);
            map.put("successful", true);
        } catch (Exception e) {
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//getCommentById()
    
    
}//class ReportAgent

