package org.pgist.sarp.vtt;

import org.pgist.wfengine.EnvironmentInOuts;
import org.pgist.wfengine.WorkflowInfo;
import org.pgist.wfengine.WorkflowTask;


/**
 * Automatic workflow task for creating VTT instance.
 * 
 * @author kenny
 */
public class CreateVTTTask implements WorkflowTask {
    
    
	public static final String IN_CHT_ID = "cht_id";
	
    public static final String OUT_VTT_ID = "vtt_id";
    
    
    private VTTService vttService;
    
    
    public void setVttService(VTTService vttService) {
        this.vttService = vttService;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void execute(WorkflowInfo info, EnvironmentInOuts inouts) throws Exception {
        System.out.println("\n@ CreateVTTTask.execute()\n");
        
        String name = inouts.getProperty("name");
        String purpose = inouts.getProperty("purpose");
        String instruction = inouts.getProperty("instruction");
        
        Long chtId = new Long(inouts.getIntValue(IN_CHT_ID));
        
        VTT vtt = vttService.createVTT(info.getWorkflow().getId(), chtId, name, purpose, instruction);
        
        inouts.setIntValue(OUT_VTT_ID, vtt.getId().intValue());
    }//execute()
    
    
}//class CreateVTTTask
