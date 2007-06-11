package org.pgist.funding;

import org.pgist.wfengine.EnvironmentInOuts;
import org.pgist.wfengine.WorkflowInfo;
import org.pgist.wfengine.WorkflowTask;


/**
 * Automatic workflow task for creating funding source suite.
 * 
 * @author kenny
 */
public class CreateFundingSuiteTask implements WorkflowTask {
    
    
    public static final String OUT_SUITE_ID = "suite_id";
    
    
    private FundingService fundingService;
    
    
    public void setFundingService(FundingService fundingService) {
        this.fundingService = fundingService;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void execute(WorkflowInfo info, EnvironmentInOuts inouts) throws Exception {
        System.out.println("@ CreateFundingSuiteTask.execute()");
        
        FundingSourceSuite suite = fundingService.createFundingSourceSuite();
        
        inouts.setIntValue(OUT_SUITE_ID, suite.getId().intValue());
    }//execute()
    
    
}//class CreateFundingSuiteTask
