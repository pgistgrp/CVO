package org.pgist.projects;

import org.pgist.wfengine.EnvironmentInOuts;
import org.pgist.wfengine.WorkflowInfo;
import org.pgist.wfengine.WorkflowTask;


/**
 * Automatic workflow task for creating project suite.
 * 
 * @author kenny
 */
public class CreateProjectSuiteTask implements WorkflowTask {
    
    
    public static final String OUT_SUITE_ID = "suite_id";
    
    
    private ProjectService projectService;
    
    
    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void execute(WorkflowInfo info, EnvironmentInOuts inouts) throws Exception {
        System.out.println("@ CreateProjectSuiteTask.execute()");
        
        ProjectSuite suite = projectService.createProjectSuite();
        
        inouts.setIntValue(OUT_SUITE_ID, suite.getId().intValue());
    }//execute()
    
    
}//class CreateProjectSuiteTask
