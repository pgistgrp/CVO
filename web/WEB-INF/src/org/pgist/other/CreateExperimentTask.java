package org.pgist.other;

import org.pgist.wfengine.EnvironmentInOuts;
import org.pgist.wfengine.WorkflowInfo;
import org.pgist.wfengine.WorkflowTask;


/**
 * Automatic workflow task for creating experiment.
 * 
 * @author kenny
 */
public class CreateExperimentTask implements WorkflowTask {
    
    
    public static final String OUT_EXPERIMENT_ID = "experiment_id";
    
    public static final String IN_CCT_ID = "cct_id";
    
    public static final String IN_PROJ_SUITE_ID = "project_suite_id";
    
    public static final String IN_FUND_SUITE_ID = "funding_suite_id";
    
    public static final String IN_PKG_SUITE_ID = "package_suite_id";
    
    public static final String IN_CRIT_SUITE_ID = "crit_suite_id";
    
    public static final String IN_REPORT_SUITE_ID = "report_suite_id";
    
    
    private ImportService importService;
    
    
    public void setImportService(ImportService importService) {
        this.importService = importService;
    }


    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void execute(WorkflowInfo info, EnvironmentInOuts inouts) throws Exception {
        System.out.println("@ CreateExperimentTask.execute()");
        
        Long cctId = new Long(inouts.getIntValue(IN_CCT_ID));
        Long projSuiteId = new Long(inouts.getIntValue(IN_PROJ_SUITE_ID));
        Long fundSuiteId = new Long(inouts.getIntValue(IN_FUND_SUITE_ID));
        Long pkgSuiteId = new Long(inouts.getIntValue(IN_PKG_SUITE_ID));
        Long critSuiteId = new Long(inouts.getIntValue(IN_CRIT_SUITE_ID));
        Long reportSuiteId = new Long(inouts.getIntValue(IN_REPORT_SUITE_ID));
        
        Experiment experiment = importService.createExperiment(info.getWorkflow().getId(), cctId, projSuiteId, fundSuiteId, pkgSuiteId, critSuiteId, reportSuiteId);
        
        inouts.setIntValue(OUT_EXPERIMENT_ID, experiment.getId().intValue());
    }//execute()
    
    
}//class CreateExperimentTask
