package org.pgist.other;

import java.util.Collection;


/**
 * 
 * @author kenny
 *
 */
public interface ImportService {
    
    
    void importTemplate(Long templateId, Long projSuiteId, Long fundSuiteId, Long critSuiteId) throws Exception;

    Collection getTemplates() throws Exception;

    Experiment createExperiment(Long workflowId, Long cctId, Long projSuiteId, Long fundSuiteId, Long pkgSuiteId, Long critSuiteId, Long reportSuiteId) throws Exception;
    
    
}//interface ImportService
