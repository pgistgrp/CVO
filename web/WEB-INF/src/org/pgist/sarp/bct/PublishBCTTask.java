package org.pgist.sarp.bct;

import org.pgist.discussion.InfoStructure;
import org.pgist.wfengine.EnvironmentInOuts;
import org.pgist.wfengine.WorkflowInfo;
import org.pgist.wfengine.WorkflowTask;


/**
 * Automatic workflow task for creating BCT instance.
 * 
 * @author kenny
 */
public class PublishBCTTask implements WorkflowTask {
    
    
    public static final String IN_BCT_ID = "bct_id";
    
    public static final String OUT_IS_ID = "is_id";
    
    
    private BCTService bctService;
    
    
    public void setBctService(BCTService bctService) {
        this.bctService = bctService;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void execute(WorkflowInfo info, EnvironmentInOuts inouts) throws Exception {
        System.out.println("\n@ PublishBCTTask.execute()\n");
        
        Long bctId = new Long(inouts.getIntValue(IN_BCT_ID));
        
        InfoStructure infoStructure = bctService.publish(info.getWorkflow().getId(), bctId, inouts.getProperty("title"));
        
        inouts.setIntValue(OUT_IS_ID, infoStructure.getId().intValue());
    }//execute()
    
    
}//class CreateBCTTask
