package org.pgist.sarp.vtt;

import org.pgist.wfengine.EnvironmentInOuts;
import org.pgist.wfengine.WorkflowInfo;
import org.pgist.wfengine.WorkflowTask;


/**
 * Automatic workflow task for toggling CST instance.
 * 
 * @author kenny
 */
public class ToggleVTTTask implements WorkflowTask {
    
    
    public static final String IN_VTT_ID = "vtt_id";
    
    
    private VTTService vttService;
    
    
    public void setVttService(VTTService vttService) {
        this.vttService = vttService;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void execute(WorkflowInfo info, EnvironmentInOuts inouts) throws Exception {
        System.out.println("\n@ ToggleVTTTask.execute()\n");
        
        Long chtId = new Long(inouts.getIntValue(IN_VTT_ID));
        
        String action = inouts.getProperty("action");
        
        if ("open".equalsIgnoreCase(action)) {
            vttService.toggleVTT(chtId, false);
        } else if ("close".equalsIgnoreCase(action)) {
            vttService.toggleVTT(chtId, true);
        } else {
        	throw new Exception("Unknow Action (expect open/close): "+action);
        }
    }//execute()
    
    
}//class ToggleVTTTask
