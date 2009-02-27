package org.pgist.sarp.cst;

import org.pgist.wfengine.EnvironmentInOuts;
import org.pgist.wfengine.WorkflowInfo;
import org.pgist.wfengine.WorkflowTask;


/**
 * Automatic workflow task for creating CST instance.
 * 
 * @author kenny
 */
public class CreateCSTTask implements WorkflowTask {
    
    
	public static final String IN_BCT_ID = "bct_id";
	
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
        
        Long bctId = new Long(inouts.getIntValue(IN_BCT_ID));
        
        CST cst = cstService.createCST(info.getWorkflow().getId(), bctId, name, purpose, instruction);
        
        inouts.setIntValue(OUT_CST_ID, cst.getId().intValue());
    }//execute()
    
    
}//class CreateCSTTask
