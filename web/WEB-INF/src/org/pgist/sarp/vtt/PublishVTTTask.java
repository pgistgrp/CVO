package org.pgist.sarp.vtt;

import org.pgist.sarp.drt.InfoObject;
import org.pgist.wfengine.EnvironmentInOuts;
import org.pgist.wfengine.WorkflowInfo;
import org.pgist.wfengine.WorkflowTask;


/**
 * Automatic workflow task for creating CHT instance.
 * 
 * @author kenny
 */
public class PublishVTTTask implements WorkflowTask {
    
    
    public static final String IN_VTT_ID = "vtt_id";
    
    public static final String IN_OID = "oid";
    
    public static final String OUT_OID = "oid";
    
    
    private VTTService vttService;
    
    
    public void setVttService(VTTService vttService) {
        this.vttService = vttService;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void execute(WorkflowInfo info, EnvironmentInOuts inouts) throws Exception {
        System.out.println("\n@ PublishVTTTask.execute()\n");
        
        Long vttId = new Long(inouts.getIntValue(IN_VTT_ID));
        long oid = -1;
        try {
        	oid = inouts.getIntValue(IN_OID);
        } catch (Exception e) {
		}
        
        if (oid==-1) {
        	InfoObject infoObject = vttService.publish(vttId, inouts.getProperty("title"));
            inouts.setIntValue(OUT_OID, infoObject.getId().intValue());
        }
    }//execute()
    
    
}//class PublishVTTTask
