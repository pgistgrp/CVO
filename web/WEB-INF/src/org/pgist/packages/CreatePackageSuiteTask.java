package org.pgist.packages;

import org.pgist.wfengine.EnvironmentInOuts;
import org.pgist.wfengine.WorkflowTask;


/**
 * Automatic workflow task for creating package suite.
 * 
 * @author kenny
 */
public class CreatePackageSuiteTask implements WorkflowTask {
    
    
    public static final String OUT_SUITE_ID = "suite_id";
    
    
    private PackageService packageService;
    
    
    public void setPackageService(PackageService packageService) {
        this.packageService = packageService;
    }


    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void execute(EnvironmentInOuts inouts) throws Exception {
        System.out.println("@ CreatePackageSuiteTask.execute()");
        
        PackageSuite suite = packageService.createPackageSuite();
        
        inouts.setIntValue(OUT_SUITE_ID, suite.getId().intValue());
    }//execute()
    
    
}//class CreatePackageSuiteTask
