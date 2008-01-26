package org.pgist.sarp.cst;

import org.pgist.wfengine.EnvironmentInOuts;
import org.pgist.wfengine.WorkflowInfo;
import org.pgist.wfengine.WorkflowTask;


/**
 * Automatic workflow task for toggling CST instance.
 * 
 * @author kenny
 */
public class ToggleCSTTask implements WorkflowTask {
    
    
    public static final String IN_CST_ID = "cst_id";
    
    
    private CSTService cstService;
    
    
    public void setCstService(CSTService cstService) {
        this.cstService = cstService;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void execute(WorkflowInfo info, EnvironmentInOuts inouts) throws Exception {
        System.out.println("\n@ ToggleCSTTask.execute()\n");
        
        Long cstId = new Long(inouts.getIntValue(IN_CST_ID));
        
        String action = inouts.getProperty("action");
        
        if ("open".equalsIgnoreCase(action)) {
        	cstService.toggleCST(cstId, false);
        } else if ("close".equalsIgnoreCase(action)) {
        	cstService.toggleCST(cstId, true);
        } else {
        	throw new Exception("Unknow Action (expect open/close): "+action);
        }
    }//execute()
    
    
}//class ToggleCSTTask
