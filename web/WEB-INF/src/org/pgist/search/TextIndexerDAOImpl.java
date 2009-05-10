package org.pgist.search;

import java.util.List;

import org.hibernate.Query;
import org.pgist.system.BaseDAOImpl;


/**
 * @author kenny
 *
 */
public class TextIndexerDAOImpl extends BaseDAOImpl implements TextIndexerDAO {
    

    /**
     * delete all indexing tasks with status=false and flag=true
     */
    @Override
    public void clearIndexingTasks() throws Exception {
        
    } //clearIndexingTasks()
    
    
    private static final String hql_markIndexingTasks1 = "update IndexingTask i set i.flag=true where i.markFailed=false and i.flag=false and i.markDeleted=false";
    private static final String hql_markIndexingTasks2 = "from IndexingTask i where i.markFailed=false and i.flag=true and i.markDeleted=false order by id";
    
    
    /**
     * M=ark indexing tasks with status=false to flag=true, and then return all tasks with status=false and flag=true
     */
    @Override
    public List<IndexingTask> markIndexingTasks() throws Exception {
        Query query = getSession().createQuery(hql_markIndexingTasks1);
        query.executeUpdate();
        
        return getHibernateTemplate().find(hql_markIndexingTasks2);
    } //getIndexingTasks()
    
    
} //class TextIndexerDAOImpl
