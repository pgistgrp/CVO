package org.pgist.projects;

import org.pgist.wfengine.EnvironmentInOuts;
import org.pgist.wfengine.WorkflowInfo;
import org.pgist.wfengine.WorkflowTask;


/**
 * Automatic workflow task for indexing projects.
 * 
 * @author kenny
 */
public class IndexProjectsTask implements WorkflowTask {
    
    
    public static final String IN_SUITE_ID = "suite_id";
    
    
    private ProjectService projectService;
    
    
    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void execute(WorkflowInfo info, EnvironmentInOuts inouts) throws Exception {
        System.out.println("@ IndexProjectsTask.execute()");
        
        Long suiteId = new Long(inouts.getIntValue(IN_SUITE_ID));
        
        projectService.indexProjectSuite(info.getWorkflow().getId(), suiteId);
    }//execute()
    
    
}//class IndexProjectsTask
