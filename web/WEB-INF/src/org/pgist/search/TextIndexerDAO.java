package org.pgist.search;

import java.util.List;

import org.pgist.system.BaseDAO;


/**
 * 
 * @author kenny
 */
public interface TextIndexerDAO extends BaseDAO {
    
    
    List<IndexingTask> markIndexingTasks() throws Exception;


    void clearIndexingTasks() throws Exception;


}//interface EmailDAO
