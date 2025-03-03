package org.pgist.cvo;

import org.pgist.discussion.InfoStructure;
import org.pgist.wfengine.EnvironmentInOuts;
import org.pgist.wfengine.WorkflowInfo;
import org.pgist.wfengine.WorkflowTask;


/**
 * Automatic workflow task for publishing themes.
 * 
 * @author kenny
 */
public class PublishThemeTask implements WorkflowTask {
    
    
    public static final String IN_CCT_ID = "cct_id";
    
    public static final String OUT_ISID = "is_id";
    
    
    private CSTService cstService;
    
    
    public void setCstService(CSTService cstService) {
        this.cstService = cstService;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void execute(WorkflowInfo info, EnvironmentInOuts inouts) throws Exception {
        System.out.println("@ PublishThemeTask.execute()");
        
        Long cctId = new Long(inouts.getIntValue(IN_CCT_ID));
        
        String title = inouts.getProperty("title");
        
        InfoStructure structure = cstService.publish(info.getWorkflow().getId(), cctId, title);
        
        inouts.setIntValue(OUT_ISID, structure.getId().intValue());
    }//execute()
    
    
}//class PublishThemeTask
