package org.pgist.workflow;

import java.util.Collection;


/**
 * 
 * @author kenny
 *
 */
public interface WorkflowDAO {
    
    
    Collection getSituations() throws Exception;

    void saveSituation(DecisionSituation situation) throws Exception;
    
    
}
