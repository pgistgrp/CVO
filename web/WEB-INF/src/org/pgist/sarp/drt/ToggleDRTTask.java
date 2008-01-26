package org.pgist.sarp.drt;

import org.pgist.wfengine.EnvironmentInOuts;
import org.pgist.wfengine.WorkflowInfo;
import org.pgist.wfengine.WorkflowTask;


/**
 * Automatic workflow task for toggling DRT instance.
 * 
 * @author kenny
 */
public class ToggleDRTTask implements WorkflowTask {
    
    
    public static final String IN_OID = "oid";
    
    
    private DRTService drtService;
    
    
    public void setDrtService(DRTService drtService) {
        this.drtService = drtService;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void execute(WorkflowInfo info, EnvironmentInOuts inouts) throws Exception {
        System.out.println("\n@ ToggleDRTTask.execute()\n");
        
        Long oid = new Long(inouts.getIntValue(IN_OID));
        
        String action = inouts.getProperty("action");
        
        if ("open".equalsIgnoreCase(action)) {
        	drtService.toggleDRT(oid, false);
        } else if ("close".equalsIgnoreCase(action)) {
        	drtService.toggleDRT(oid, true);
        } else {
        	throw new Exception("Unknow Action (expect open/close): "+action);
        }
    }//execute()
    
    
}//class ToggleDRTTask
