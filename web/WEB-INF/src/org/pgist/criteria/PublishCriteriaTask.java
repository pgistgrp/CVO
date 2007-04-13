package org.pgist.criteria;

import org.pgist.discussion.InfoStructure;
import org.pgist.wfengine.EnvironmentInOuts;
import org.pgist.wfengine.WorkflowTask;


/**
 * Automatic workflow task for publishing criteria.
 * 
 * @author kenny
 */
public class PublishCriteriaTask implements WorkflowTask {
    
    
    public static final String IN_CCT_ID = "cctId";
    
    public static final String IN_SUITE_ID = "suiteId";
    
    public static final String OUT_ISID = "is_id";
    
    
    private CriteriaService criteriaService;
    
    
    public void setCriteriaService(CriteriaService criteriaService) {
        this.criteriaService = criteriaService;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void execute(EnvironmentInOuts inouts) throws Exception {
        System.out.println("@ PublishCriteriaTask.execute()");
        
        Long suiteId = new Long(inouts.getIntValue(IN_SUITE_ID));
        
        Long cctId = new Long(inouts.getIntValue(IN_CCT_ID));
        
        InfoStructure structure = criteriaService.publish(cctId, suiteId);
        
        inouts.setIntValue(OUT_ISID, structure.getId().intValue());
    }//execute()
    
    
}//class PublishCriteriaTask
