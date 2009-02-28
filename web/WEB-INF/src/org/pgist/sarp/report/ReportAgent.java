package org.pgist.sarp.report;

import org.pgist.search.SearchHelper;
import org.pgist.system.EmailSender;
import org.pgist.system.SystemService;


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


}//class ReportAgent
