package org.pgist.packages;

import org.pgist.wfengine.EnvironmentInOuts;
import org.pgist.wfengine.WorkflowInfo;
import org.pgist.wfengine.WorkflowTask;


/**
 * Automatic workflow task for grouping packages.
 * 
 * @author kenny
 */
public class GroupPackagesTask implements WorkflowTask {
    
    
    public static final String IN_SUITE_ID = "suite_id";
    
    
    private PackageService packageService;
    
    
    public void setPackageService(PackageService packageService) {
        this.packageService = packageService;
    }


    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void execute(WorkflowInfo info, EnvironmentInOuts inouts) throws Exception {
        System.out.println("@ GroupPackagesTask.execute()");
        
        Long suiteId = new Long(inouts.getIntValue(IN_SUITE_ID));
        
        /*
         * TODO: connect to Mike's clustering algorithm
         */
    }//execute()
    
    
}//class GroupPackagesTask
