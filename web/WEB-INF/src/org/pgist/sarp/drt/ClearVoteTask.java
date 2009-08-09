package org.pgist.sarp.drt;

import org.pgist.wfengine.EnvironmentInOuts;
import org.pgist.wfengine.WorkflowInfo;
import org.pgist.wfengine.WorkflowTask;


/**
 * Automatic workflow task for clearing DRT vote.
 * 
 * @author kenny
 */
public class ClearVoteTask implements WorkflowTask {
    
    
    public static final String IN_OID = "oid";
    
    
    private DRTService drtService;
    
    
    public void setDrtService(DRTService drtService) {
        this.drtService = drtService;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void execute(WorkflowInfo info, EnvironmentInOuts inouts) throws Exception {
        System.out.println("\n@ ClearVoteTask.execute()\n");
        
        Long oid = new Long(inouts.getIntValue(IN_OID));
        
        drtService.clearVote(oid);
    }//execute()
    
    
}//class ClearVoteTask
