package org.pgist.sarp.cht;

import org.pgist.wfengine.EnvironmentInOuts;
import org.pgist.wfengine.WorkflowInfo;
import org.pgist.wfengine.WorkflowTask;


/**
 * Automatic workflow task for toggling CST instance.
 * 
 * @author kenny
 */
public class ToggleCHTTask implements WorkflowTask {
    
    
    public static final String IN_CHT_ID = "cht_id";
    
    
    private CHTService chtService;
    
    
    public void setChtService(CHTService chtService) {
        this.chtService = chtService;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void execute(WorkflowInfo info, EnvironmentInOuts inouts) throws Exception {
        System.out.println("\n@ ToggleCHTTask.execute()\n");
        
        Long chtId = new Long(inouts.getIntValue(IN_CHT_ID));
        
        String action = inouts.getProperty("action");
        
        if ("open".equalsIgnoreCase(action)) {
        	chtService.toggleCHT(chtId, false);
        } else if ("close".equalsIgnoreCase(action)) {
        	chtService.toggleCHT(chtId, true);
        } else {
        	throw new Exception("Unknow Action (expect open/close): "+action);
        }
    }//execute()
    
    
}//class ToggleCHTTask
