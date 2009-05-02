package org.pgist.system;

import java.util.List;


/**
 * @author kenny
 *
 */
public class TextIndexerDAOImpl extends BaseDAOImpl implements TextIndexerDAO {
    

    @Override
    public void clearIndexingTasks() throws Exception {
        
    } //clearIndexingTasks()
    
    
    @Override
    public List<IndexingTask> getIndexingTasks() throws Exception {
        return null;
    } //getIndexingTasks()
    
    
} //class TextIndexerDAOImpl
