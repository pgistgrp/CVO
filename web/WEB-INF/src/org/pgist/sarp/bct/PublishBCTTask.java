package org.pgist.sarp.bct;

import org.pgist.sarp.drt.InfoObject;
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
    
    public static final String IN_OID = "oid";
    
    public static final String OUT_OID = "oid";
    
    
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
        
        long oid = -1;
        try {
        	oid = inouts.getIntValue(IN_OID);
        } catch (Exception e) {
		}
        
        if (oid==-1) {
        	InfoObject infoObject = bctService.publish(bctId, inouts.getProperty("title"));
            inouts.setIntValue(OUT_OID, infoObject.getId().intValue());
        }
    }//execute()
    
    
}//class PublishBCTTask
