package org.pgist.packages;

import org.pgist.wfengine.EnvironmentInOuts;
import org.pgist.wfengine.WorkflowInfo;
import org.pgist.wfengine.WorkflowTask;


/**
 * Automatic workflow task for calculating the preferred package
 * 
 * @author John
 */
public class CreatePreferredPackageTask implements WorkflowTask {
    
    
    public static final String IN_SUITE_ID = "pkg_suite_id";
    public static final String IN_VOTE_ID = "vote_suite_id";
    
    
    private PackageService packageService;
    
    
    public void setPackageService(PackageService packageService) {
        this.packageService = packageService;
    }


    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void execute(WorkflowInfo info, EnvironmentInOuts inouts) throws Exception {
        System.out.println("@ CreatePreferredPackageTask.execute()");
        
        Long pkgSuiteId = new Long(inouts.getIntValue(IN_SUITE_ID));
        Long voteSuiteId = new Long(inouts.getIntValue(IN_VOTE_ID));
        
        packageService.calculatePreferredPackage(pkgSuiteId, voteSuiteId);
        
    }//execute()
    
    
}//class CreatePreferredPackageTask
