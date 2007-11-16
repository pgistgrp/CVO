package org.pgist.report;

import org.pgist.wfengine.EnvironmentInOuts;
import org.pgist.wfengine.WorkflowInfo;
import org.pgist.wfengine.WorkflowTask;

/**
 * Automatic workflow task for publishing criteria.
 * 
 * @author John Le
 */

public class CreateReportStatisticsTask implements WorkflowTask{
    
	private ReportService reportService;
    
    public static final String IN_CCT_ID = "cct_id";
    
    public static final String IN_PROJ_SUITE_ID = "proj_suite_id";
    
    public static final String IN_FUND_SUITE_ID = "fund_suite_id";
    
    public static final String IN_CRIT_SUITE_ID = "crit_suite_id";
    
    public static final String IN_PROJ_ISID = "proj_isid";
    
    public static final String IN_FUND_ISID = "fund_isid";
    
    public static final String IN_REPO_SUITE_ID = "repo_suite_id";
    
	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void execute(WorkflowInfo info, EnvironmentInOuts inouts) throws Exception {
        System.out.println("@ CreateReportStatisticsTask.execute()");
        
        Long workflowId = info.getWorkflow().getId();
        Long cctId = new Long(inouts.getIntValue(IN_CCT_ID));
        Long projSuiteId = new Long(inouts.getIntValue(IN_PROJ_SUITE_ID)); 
        Long fundSuiteId = new Long(inouts.getIntValue(IN_FUND_SUITE_ID));
        Long critSuiteId = new Long(inouts.getIntValue(IN_CRIT_SUITE_ID));
        Long projISID = new Long(inouts.getIntValue(IN_PROJ_ISID));
        Long fundISID = new Long(inouts.getIntValue(IN_FUND_ISID));
        Long repoSuiteId = new Long(inouts.getIntValue(IN_REPO_SUITE_ID));
        
        reportService.createStatistics(workflowId, cctId, projSuiteId, fundSuiteId, critSuiteId, repoSuiteId, projISID, fundISID);
        
    }//execute()

    
}//class CreateReportStatisticsTask
