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
    
    public static final String OUT_SUITE_ID = "poll_suite_id";
    public static final String OUT_SUITE_ID_FINAL = "vote_suite_id";
    
    
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
        
        PackageVoteSuite suite1 = packageService.createPackageVoteSuite(pkgSuiteId, false);
        PackageVoteSuite suite2 = packageService.createPackageVoteSuite(pkgSuiteId, true);
        
        inouts.setIntValue(OUT_SUITE_ID, suite2.getId().intValue());
        inouts.setIntValue(OUT_SUITE_ID_FINAL, suite2.getId().intValue());
    }//execute()
    
    
}//class CreateVoteSuiteTask
