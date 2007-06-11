package org.pgist.funding;

import org.pgist.discussion.InfoStructure;
import org.pgist.wfengine.EnvironmentInOuts;
import org.pgist.wfengine.WorkflowInfo;
import org.pgist.wfengine.WorkflowTask;


/**
 * Automatic workflow task for publishing fundings.
 * 
 * @author kenny
 */
public class PublishFundingsTask implements WorkflowTask {
    
    
    public static final String IN_CCT_ID = "cct_id";
    
    public static final String IN_SUITE_ID = "suite_id";
    
    public static final String OUT_ISID = "is_id";
    
    
    private FundingService fundingService;
    
    
    public void setFundingService(FundingService fundingService) {
        this.fundingService = fundingService;
    }


    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void execute(WorkflowInfo info, EnvironmentInOuts inouts) throws Exception {
        System.out.println("@ PublishFundingsTask.execute()");
        
        Long suiteId = new Long(inouts.getIntValue(IN_SUITE_ID));
        
        Long cctId = new Long(inouts.getIntValue(IN_CCT_ID));
        
        System.out.printf("\n\n\n(%d, %d)\n\n\n", suiteId, cctId);
        
        InfoStructure structure = fundingService.publish(cctId, suiteId);
        
        inouts.setIntValue(OUT_ISID, structure.getId().intValue());
    }//execute()
    
    
}//class PublishFundingsTask
