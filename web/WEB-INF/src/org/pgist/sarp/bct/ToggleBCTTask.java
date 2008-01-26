package org.pgist.sarp.bct;

import org.pgist.wfengine.EnvironmentInOuts;
import org.pgist.wfengine.WorkflowInfo;
import org.pgist.wfengine.WorkflowTask;


/**
 * Automatic workflow task for toggling BCT instance.
 * 
 * @author kenny
 */
public class ToggleBCTTask implements WorkflowTask {
    
    
    public static final String IN_BCT_ID = "bct_id";
    
    
    private BCTService bctService;
    
    
    public void setBctService(BCTService bctService) {
        this.bctService = bctService;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void execute(WorkflowInfo info, EnvironmentInOuts inouts) throws Exception {
        System.out.println("\n@ ToggleBCTTask.execute()\n");
        
        Long bctId = new Long(inouts.getIntValue(IN_BCT_ID));
        
        String action = inouts.getProperty("action");
        
        if ("open".equalsIgnoreCase(action)) {
        	bctService.toggleBCT(bctId, false);
        } else if ("close".equalsIgnoreCase(action)) {
        	bctService.toggleBCT(bctId, true);
        } else {
        	throw new Exception("Unknow Action (expect open/close): "+action);
        }
    }//execute()
    
    
}//class ToggleBCTTask
