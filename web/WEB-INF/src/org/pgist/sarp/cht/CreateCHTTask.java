package org.pgist.sarp.cht;

import org.pgist.wfengine.EnvironmentInOuts;
import org.pgist.wfengine.WorkflowInfo;
import org.pgist.wfengine.WorkflowTask;


/**
 * Automatic workflow task for creating CHT instance.
 * 
 * @author kenny
 */
public class CreateCHTTask implements WorkflowTask {
    
    
	public static final String IN_CST_ID = "cst_id";
	
    public static final String OUT_CHT_ID = "cht_id";
    
    
    private CHTService chtService;
    
    
    public void setChtService(CHTService chtService) {
        this.chtService = chtService;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void execute(WorkflowInfo info, EnvironmentInOuts inouts) throws Exception {
        System.out.println("\n@ CreateCHTTask.execute()\n");
        
        String name = inouts.getProperty("name");
        String purpose = inouts.getProperty("purpose");
        String instruction = inouts.getProperty("instruction");
        
        Long cstId = new Long(inouts.getIntValue(IN_CST_ID));
        
        CHT cht = chtService.createCHT(info.getWorkflow().getId(), cstId, name, purpose, instruction);
        
        inouts.setIntValue(OUT_CHT_ID, cht.getId().intValue());
    }//execute()
    
    
}//class CreateCHTTask
