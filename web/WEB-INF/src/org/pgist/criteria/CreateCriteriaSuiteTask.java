package org.pgist.criteria;

import org.pgist.wfengine.EnvironmentInOuts;
import org.pgist.wfengine.WorkflowInfo;
import org.pgist.wfengine.WorkflowTask;


/**
 * Automatic workflow task for creating criteria suite.
 * 
 * @author kenny
 */
public class CreateCriteriaSuiteTask implements WorkflowTask {
    
    
    public static final String OUT_SUITE_ID = "suite_id";
    
    
    private CriteriaService criteriaService;
    
    
    public void setCriteriaService(CriteriaService criteriaService) {
        this.criteriaService = criteriaService;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void execute(WorkflowInfo info, EnvironmentInOuts inouts) throws Exception {
        System.out.println("@ CreateCriteriaSuiteTask.execute()");
        
        CriteriaSuite suite = criteriaService.createCriteriaSuite();
        
        inouts.setIntValue(OUT_SUITE_ID, suite.getId().intValue());
    }//execute()
    
    
}//class CreateCriteriaSuiteTask
