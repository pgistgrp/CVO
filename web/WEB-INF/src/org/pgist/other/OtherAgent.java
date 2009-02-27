package org.pgist.other;

import java.util.HashMap;
import java.util.Map;

import org.pgist.wfengine.EnvironmentHandler;
import org.pgist.wfengine.EnvironmentInOuts;
import org.pgist.wfengine.WorkflowEngine;


/**
 * DWR AJAX Agent class.<br>
 * Provide AJAX services to client programs.<br>
 * In this document, all the NON-AJAX methods are marked out. So all methods
 * <span style="color:red;">without</span> such a description
 * <span style="color:red;">ARE</span> AJAX service methods.<br>
 *
 * @author kenny
 *
 */
public class OtherAgent {
    
    
    private WorkflowEngine engine;
    
    
    public void setEngine(WorkflowEngine engine) {
        this.engine = engine;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    /**
     * Set the situation template.
     * 
     * @param params a Map contains:
     *   <ul>
     *     <li>workflowId - int, id of the Workflow object</li>
     *     <li>contextId - int, id of the WorkflowContext object</li>
     *     <li>activityId - int, id of the Activity object</li>
     *     <li>templateName - string, name of the situation template</li>
     *   </ul>
     *   
     * @return a Map contains:
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
    public Map setSituationTemplate(final Map params) {
        Map results = new HashMap();
        results.put("successful", false);
        
        try {
            Long workflowId = new Long((String) params.get("workflowId"));
            Long contextId = new Long((String) params.get("contextId"));
            Long activityId = new Long((String) params.get("activityId"));
            
            engine.setEnvVars(
                workflowId, contextId, activityId,
                new EnvironmentHandler() {
                    public void handleEnvVars(EnvironmentInOuts inouts) throws Exception {
                        try {
                            Integer templateId = new Integer((String) params.get("templateId"));
                            inouts.setIntValue("template_id", templateId);
                        } catch(Exception e) {
                            inouts.setIntValue("template_id", -1);
                        }
                    }//handleEnvVars()
                }
            );
            
            results.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            results.put("reason", e.getMessage());
        }
        
        return results;
    }//setSituationTemplate()
    
    
}//class OtherAgent
