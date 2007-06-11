package org.pgist.cvo;

import org.pgist.wfengine.EnvironmentInOuts;
import org.pgist.wfengine.WorkflowInfo;
import org.pgist.wfengine.WorkflowTask;


/**
 * Automatic workflow task for creating CCT instance.
 * 
 * @author kenny
 */
public class CreateCCTTask implements WorkflowTask {
    
    
    public static final String OUT_CCT_ID = "cct_id";
    
    
    private CCTService cctService;
    
    
    public void setCctService(CCTService cctService) {
        this.cctService = cctService;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void execute(WorkflowInfo info, EnvironmentInOuts inouts) throws Exception {
        System.out.println("@ CreateCCTTask.execute()");
        
        String name = inouts.getProperty("name");
        String purpose = inouts.getProperty("purpose");
        String instruction = inouts.getProperty("instruction");
        
        CCT cct = cctService.createCCT(name, purpose, instruction);
        
        inouts.setIntValue(OUT_CCT_ID, cct.getId().intValue());
    }//execute()
    
    
}//class CreateFundingSuiteTask
