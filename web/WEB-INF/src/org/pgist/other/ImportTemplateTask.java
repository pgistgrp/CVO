package org.pgist.other;

import org.pgist.wfengine.EnvironmentInOuts;
import org.pgist.wfengine.WorkflowInfo;
import org.pgist.wfengine.WorkflowTask;


/**
 * Automatic workflow task for creating CCT instance.
 * 
 * @author kenny
 */
public class ImportTemplateTask implements WorkflowTask {
    
    
    public static final String IN_PROJECT_SUITE_ID = "proj_suite_id";
    
    public static final String IN_FUNDING_SUITE_ID = "fund_suite_id";
    
    public static final String IN_CRIT_SUITE_ID = "crit_suite_id";
    
    public static final String IN_TEMPLATE_ID = "template_id";
    
    
    private ImportService importService;
    
    
    public void setImportService(ImportService importService) {
        this.importService = importService;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void execute(WorkflowInfo info, EnvironmentInOuts inouts) throws Exception {
        System.out.println("@ ImportTemplateTask.execute()");
        
        //Get template name
        Integer id = inouts.getIntValue(IN_TEMPLATE_ID);
        Long templateId = null;
        if (id!=null) templateId = new Long(id);
        
        //if template name is -1, ignore it
        if (templateId==null || templateId==-1) return;
        
        Long projSuiteId = new Long(inouts.getIntValue(IN_PROJECT_SUITE_ID));
        Long fundSuiteId = new Long(inouts.getIntValue(IN_FUNDING_SUITE_ID));
        Long critSuiteId = new Long(inouts.getIntValue(IN_CRIT_SUITE_ID));
        
        importService.importTemplate(templateId, projSuiteId, fundSuiteId, critSuiteId);
    }//execute()
    
    
}//class ImportTemplateTask
