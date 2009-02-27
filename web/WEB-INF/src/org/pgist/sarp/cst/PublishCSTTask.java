package org.pgist.sarp.cst;

import org.pgist.sarp.drt.InfoObject;
import org.pgist.wfengine.EnvironmentInOuts;
import org.pgist.wfengine.WorkflowInfo;
import org.pgist.wfengine.WorkflowTask;


/**
 * Automatic workflow task for creating BCT instance.
 * 
 * @author kenny
 */
public class PublishCSTTask implements WorkflowTask {
    
    
    public static final String IN_CST_ID = "cst_id";
    
    public static final String IN_OID = "oid";
    
    public static final String OUT_OID = "oid";
    
    
    private CSTService cstService;
    
    
    public void setCstService(CSTService cstService) {
        this.cstService = cstService;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void execute(WorkflowInfo info, EnvironmentInOuts inouts) throws Exception {
        System.out.println("\n@ PublishCSTTask.execute()\n");
        
        Long cstId = new Long(inouts.getIntValue(IN_CST_ID));
        long oid = -1;
        try {
        	oid = inouts.getIntValue(IN_OID);
        } catch (Exception e) {
		}
        
        if (oid==-1) {
        	InfoObject infoObject = cstService.publish(cstId, inouts.getProperty("title"));
            inouts.setIntValue(OUT_OID, infoObject.getId().intValue());
        }
    }//execute()
    
    
}//class PublishCSTTask
