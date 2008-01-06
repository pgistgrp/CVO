package org.pgist.sarp.bct;

import org.pgist.wfengine.EnvironmentInOuts;
import org.pgist.wfengine.WorkflowInfo;
import org.pgist.wfengine.WorkflowTask;


/**
 * Automatic workflow task for creating BCT instance.
 * 
 * @author kenny
 */
public class CreateBCTTask implements WorkflowTask {
    
    
    public static final String OUT_BCT_ID = "bct_id";
    
    
    private BCTService bctService;
    
    
    public void setBctService(BCTService bctService) {
        this.bctService = bctService;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void execute(WorkflowInfo info, EnvironmentInOuts inouts) throws Exception {
        System.out.println("@ CreateBCTTask.execute()");
        
        String name = inouts.getProperty("name");
        String purpose = inouts.getProperty("purpose");
        String instruction = inouts.getProperty("instruction");
        
        BCT bct = bctService.createBCT(info.getWorkflow().getId(), name, purpose, instruction);
        
        inouts.setIntValue(OUT_BCT_ID, bct.getId().intValue());
    }//execute()
    
    
}//class CreateBCTTask
