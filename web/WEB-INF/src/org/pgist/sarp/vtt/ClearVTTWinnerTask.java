package org.pgist.sarp.vtt;

import org.pgist.wfengine.EnvironmentInOuts;
import org.pgist.wfengine.WorkflowInfo;
import org.pgist.wfengine.WorkflowTask;


/**
 * Automatic workflow task for clearing CHT winner.
 * 
 * @author kenny
 */
public class ClearVTTWinnerTask implements WorkflowTask {
    
    
    public static final String IN_VTT_ID = "vtt_id";
    
    
    private VTTService vttService;
    
    
    public void setVttService(VTTService vttService) {
        this.vttService = vttService;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void execute(WorkflowInfo info, EnvironmentInOuts inouts) throws Exception {
        System.out.println("\n@ ClearVTTWinnerTask.execute()\n");
        
        Long vttId = new Long(inouts.getIntValue(IN_VTT_ID));
        
        vttService.setClearVTTWinner(vttId);
    }//execute()
    
    
}//class ClearVTTWinnerTask
