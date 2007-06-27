package org.pgist.report;

import org.pgist.wfengine.EnvironmentInOuts;
import org.pgist.wfengine.WorkflowInfo;
import org.pgist.wfengine.WorkflowTask;


/**
 * Automatic workflow task for creating criteria suite.
 * 
 * @author John  
 */
public class CreateReportSuiteTask implements WorkflowTask {
    
    
    public static final String OUT_SUITE_ID = "suite_id";
    
    
    private ReportService reportService;
    
	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void execute(WorkflowInfo info, EnvironmentInOuts inouts) throws Exception {
        System.out.println("@ CreateReportSuiteTask.execute()");
        
        ReportSuite suite = reportService.createReportSuite();
        
        inouts.setIntValue(OUT_SUITE_ID, suite.getId().intValue());
    }//execute()







    
    
}//class CreateReportSuiteTask
