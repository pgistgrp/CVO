package org.pgist.system;

import java.util.List;


/**
 * 
 * @author kenny
 */
public interface TextIndexerDAO extends BaseDAO {
    
    
    List<IndexingTask> getIndexingTasks() throws Exception;


    void clearIndexingTasks() throws Exception;
    
    
}//interface EmailDAO
