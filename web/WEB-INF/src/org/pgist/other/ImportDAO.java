package org.pgist.other;

import java.util.Collection;

import org.pgist.system.BaseDAO;


/**
 * 
 * @author Kenny
 */
public interface ImportDAO extends BaseDAO {
    
    
    Collection getTemplates() throws Exception;

    SituationTemplate getTemplateById(Long templateId) throws Exception;

    Experiment getExperimentByWorkflowId(Long workflowId) throws Exception;
    
	
}//interface ImportDAO
