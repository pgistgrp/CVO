package org.pgist.report;

import org.pgist.discussion.InfoStructure;
import org.pgist.wfengine.EnvironmentInOuts;
import org.pgist.wfengine.WorkflowInfo;
import org.pgist.wfengine.WorkflowTask;


/**
 * Automatic workflow task for publishing criteria.
 * 
 * @author John
 */
public class PublishReportTask implements WorkflowTask {
    
    
    public static final String IN_CCT_ID = "cct_id";
    
    public static final String IN_SUITE_ID = "suite_id";
    
    public static final String OUT_ISID = "is_id";
    
    
    private ReportService reportService;
      
    public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}
    
    
    /*
     * ------------------------------------------------------------------------
     */
    

	public void execute(WorkflowInfo info, EnvironmentInOuts inouts) throws Exception {
        System.out.println("@ PublishCriteriaTask.execute()");
        
        Long suiteId = new Long(inouts.getIntValue(IN_SUITE_ID));
        
        Long cctId = new Long(inouts.getIntValue(IN_CCT_ID));
        
        System.out.println(suiteId);
        
        String title = inouts.getProperty("title");
        
        InfoStructure structure = reportService.publish(info.getWorkflow().getId(), cctId, suiteId, title);
        
        inouts.setIntValue(OUT_ISID, structure.getId().intValue());
    }//execute()
    
    
}//class PublishReportTask
