package org.pgist.sarp.cht;

import org.pgist.sarp.drt.InfoObject;
import org.pgist.wfengine.EnvironmentInOuts;
import org.pgist.wfengine.WorkflowInfo;
import org.pgist.wfengine.WorkflowTask;


/**
 * Automatic workflow task for creating CHT instance.
 * 
 * @author kenny
 */
public class PublishCHTTask implements WorkflowTask {
    
    
    public static final String IN_CHT_ID = "cht_id";
    
    public static final String IN_OID = "oid";
    
    public static final String OUT_OID = "oid";
    
    
    private CHTService chtService;
    
    
    public void setChtService(CHTService chtService) {
        this.chtService = chtService;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void execute(WorkflowInfo info, EnvironmentInOuts inouts) throws Exception {
        System.out.println("\n@ PublishCHTTask.execute()\n");
        
        Long chtId = new Long(inouts.getIntValue(IN_CHT_ID));
        long oid = -1;
        try {
        	oid = inouts.getIntValue(IN_OID);
        } catch (Exception e) {
		}
        
        if (oid==-1) {
        	InfoObject infoObject = chtService.publish(chtId, inouts.getProperty("title"));
            inouts.setIntValue(OUT_OID, infoObject.getId().intValue());
        }
    }//execute()
    
    
}//class PublishCHTTask
