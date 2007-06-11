package org.pgist.packages;

import org.pgist.wfengine.EnvironmentInOuts;
import org.pgist.wfengine.WorkflowInfo;
import org.pgist.wfengine.WorkflowTask;


/**
 * Automatic workflow task for creating vote suite.
 * 
 * @author kenny
 */
public class CreateVoteSuiteTask implements WorkflowTask {
    
    
    public static final String IN_SUITE_ID = "pkg_suite_id";
    
    public static final String OUT_SUITE_ID = "suite_id";
    
    
    private PackageService packageService;
    
    
    public void setPackageService(PackageService packageService) {
        this.packageService = packageService;
    }


    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void execute(WorkflowInfo info, EnvironmentInOuts inouts) throws Exception {
        System.out.println("@ CreateVoteSuiteTask.execute()");
        
        Long pkgSuiteId = new Long(inouts.getIntValue(IN_SUITE_ID));
        
        PackageVoteSuite suite = packageService.createPackageVoteSuite(pkgSuiteId);
        
        inouts.setIntValue(OUT_SUITE_ID, suite.getId().intValue());
    }//execute()
    
    
}//class CreateVoteSuiteTask
