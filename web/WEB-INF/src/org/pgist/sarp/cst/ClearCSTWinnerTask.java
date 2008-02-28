package org.pgist.sarp.cst;

import org.pgist.wfengine.EnvironmentInOuts;
import org.pgist.wfengine.WorkflowInfo;
import org.pgist.wfengine.WorkflowTask;


/**
 * Automatic workflow task for clearing CST winner.
 * 
 * @author kenny
 */
public class ClearCSTWinnerTask implements WorkflowTask {
    
    
    public static final String IN_CST_ID = "cst_id";
    
    
    private CSTService cstService;
    
    
    public void setCstService(CSTService cstService) {
        this.cstService = cstService;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void execute(WorkflowInfo info, EnvironmentInOuts inouts) throws Exception {
        System.out.println("\n@ ClearCSTWinnerTask.execute()\n");
        
        Long cstId = new Long(inouts.getIntValue(IN_CST_ID));
        
        cstService.setClearCSTWinner(cstId);
    }//execute()
    
    
}//class ClearCSTWinnerTask
