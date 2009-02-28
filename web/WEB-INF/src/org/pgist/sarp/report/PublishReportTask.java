package org.pgist.sarp.report;

import org.pgist.sarp.drt.InfoObject;
import org.pgist.wfengine.EnvironmentInOuts;
import org.pgist.wfengine.WorkflowInfo;
import org.pgist.wfengine.WorkflowTask;


/**
 * Automatic workflow task for creating CHT instance.
 * 
 * @author kenny
 */
public class PublishReportTask implements WorkflowTask {
    
    
    public static final String IN_REPORT_ID = "report_id";
    
    public static final String IN_OID = "oid";
    
    public static final String OUT_OID = "oid";
    
    
    private ReportService reportService;
    
    
    public void setReportService(ReportService reportService) {
        this.reportService = reportService;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void execute(WorkflowInfo info, EnvironmentInOuts inouts) throws Exception {
        System.out.println("\n@ PublishReportTask.execute()\n");
        
        Long reportId = new Long(inouts.getIntValue(IN_REPORT_ID));
        long oid = -1;
        try {
        	oid = inouts.getIntValue(IN_OID);
        } catch (Exception e) {
		}
        
        if (oid==-1) {
        	InfoObject infoObject = reportService.publish(reportId, inouts.getProperty("title"));
            inouts.setIntValue(OUT_OID, infoObject.getId().intValue());
        }
    }//execute()
    
    
}//class PublishReportTask
