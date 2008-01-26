package org.pgist.sarp.cst;

import org.pgist.wfengine.EnvironmentInOuts;
import org.pgist.wfengine.WorkflowInfo;
import org.pgist.wfengine.WorkflowTask;


/**
 * Automatic workflow task for creating BCT instance.
 * 
 * @author kenny
 */
public class CreateCSTTask implements WorkflowTask {
    
    
    public static final String OUT_CST_ID = "cst_id";
    
    
    private CSTService cstService;
    
    
    public void setCstService(CSTService cstService) {
        this.cstService = cstService;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void execute(WorkflowInfo info, EnvironmentInOuts inouts) throws Exception {
        System.out.println("\n@ CreateCSTTask.execute()\n");
        
        String name = inouts.getProperty("name");
        String purpose = inouts.getProperty("purpose");
        String instruction = inouts.getProperty("instruction");
        
        CST cst = cstService.createCST(info.getWorkflow().getId(), name, purpose, instruction);
        
        inouts.setIntValue(OUT_CST_ID, cst.getId().intValue());
    }//execute()
    
    
}//class CreateCSTTask
