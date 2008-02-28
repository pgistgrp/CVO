package org.pgist.sarp.cht;

import org.pgist.wfengine.EnvironmentInOuts;
import org.pgist.wfengine.WorkflowInfo;
import org.pgist.wfengine.WorkflowTask;


/**
 * Automatic workflow task for clearing CHT winner.
 * 
 * @author kenny
 */
public class ClearCHTWinnerTask implements WorkflowTask {
    
    
    public static final String IN_CHT_ID = "cht_id";
    
    
    private CHTService chtService;
    
    
    public void setChtService(CHTService chtService) {
        this.chtService = chtService;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void execute(WorkflowInfo info, EnvironmentInOuts inouts) throws Exception {
        System.out.println("\n@ ClearCHTWinnerTask.execute()\n");
        
        Long chtId = new Long(inouts.getIntValue(IN_CHT_ID));
        
        chtService.setClearCHTWinner(chtId);
    }//execute()
    
    
}//class ClearCHTWinnerTask
