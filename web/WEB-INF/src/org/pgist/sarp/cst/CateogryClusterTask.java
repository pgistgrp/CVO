package org.pgist.sarp.cst;

import org.pgist.wfengine.EnvironmentInOuts;
import org.pgist.wfengine.WorkflowInfo;
import org.pgist.wfengine.WorkflowTask;


/**
 * Automatic workflow task for custering categories.
 * 
 * @author kenny
 */
public class CateogryClusterTask implements WorkflowTask {
    
    
    public static final String IN_CST_ID = "cst_id";
    
    
    private CSTService cstService;
    
    
    public void setCstService(CSTService cstService) {
        this.cstService = cstService;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void execute(WorkflowInfo info, EnvironmentInOuts inouts) throws Exception {
        System.out.println("\n@ CateogryClusterTask.execute()\n");
        
        Long cstId = new Long(inouts.getIntValue(IN_CST_ID));
        
        cstService.setClusteredCategory(cstId);
    }//execute()
    
    
}//class CateogryClusterTask
