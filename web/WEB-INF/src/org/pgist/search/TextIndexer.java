package org.pgist.search;

import java.util.List;
import java.util.Map;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;


/**
 * @author kenny
 */
public class TextIndexer {

    
    private TextIndexerDAO textIndexerDAO;
    
    private SearchHelper searchHelper;
    
    private Map<String, IndexHandler> indexHandlers;
    
    
    public void setTextIndexerDAO(TextIndexerDAO textIndexerDAO) {
        this.textIndexerDAO = textIndexerDAO;
    }


    public void setSearchHelper(SearchHelper searchHelper) {
        this.searchHelper = searchHelper;
    }

    
    public void setIndexHandlers(Map<String, IndexHandler> indexHandlers) {
        this.indexHandlers = indexHandlers;
    }


    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void enqueue(String workflowId, String type, String action, Long objectId, String url) throws Exception {
        IndexingTask task = new IndexingTask();
        
        task.setWorkflowId(workflowId);
        task.setLink(url);
        task.setType(type);
        task.setAction(action);
        task.setObjectId(objectId.toString());
        
        textIndexerDAO.save(task);
    } //enqueue()
    
    
    public void enqueue(Map wfinfo, String type, String action, Long objectId) throws Exception {
        String url = null;
        String workflowId = null;
        
        if (wfinfo!=null) {
            workflowId = (String) wfinfo.get("workflowId");
            url = String.format("workflow.do?workflowId=%s&contextId=%s&activityId=%s", wfinfo.get("workflowId"), wfinfo.get("contextId"), wfinfo.get("activityId"));
        }
        
        enqueue(workflowId, type, action, objectId, url);
    } //enqueue()
    
    
    public void checkAndIndex() throws Exception {
        List<IndexingTask> tasks = textIndexerDAO.markIndexingTasks();
        
        IndexReader reader = searchHelper.getIndexReader();
        IndexWriter writer = searchHelper.getIndexWriter();
        IndexSearcher searcher = searchHelper.getIndexSearcher(reader);
        QueryParser parser = searchHelper.getParser();
        
        for (IndexingTask task : tasks) {
            try {
                IndexHandler handler = indexHandlers.get(task.getType());
                if (handler!=null) {
                    handler.index(reader, writer, searcher, parser, task);
                    task.setMarkDeleted(true);
                } else {
                    // unknow type
                    task.setMarkFailed(true);
                    System.out.println(String.format("Error, unknow indexing task type: %d - %s", task.getId(), task.getType()));
                }
            } catch (Exception e) {
                task.setMarkFailed(true);
                e.printStackTrace();
            } finally {
                textIndexerDAO.save(task);
            }
        }
        
        writer.optimize();
        writer.close();
        
        textIndexerDAO.clearIndexingTasks();
    } //checkAndIndex()


} //class TextIndexer
