package org.pgist.system;

import java.util.Map;


/**
 * @author kenny
 */
public class TextIndexer {

    
    private TextIndexerDAO textIndexerDAO;
    
    
    public void setTextIndexerDAO(TextIndexerDAO textIndexerDAO) {
        this.textIndexerDAO = textIndexerDAO;
    }


    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void enqueue(String workflowId, String type, Long objectId, String url) throws Exception {
        IndexingTask task = new IndexingTask();
        
        task.setWorkflowId(workflowId);
        task.setLink(url);
        task.setType(type);
        task.setObjectId(objectId);
        
        textIndexerDAO.save(task);
    } //enqueue()
    
    
    public void enqueue(Map wfinfo, String type, Long objectId) throws Exception {
        String url = null;
        String workflowId = null;
        
        if (wfinfo!=null) {
            workflowId = (String) wfinfo.get("workflowId");
            url = String.format("workflow.do?workflowId=%s&contextId=%s&activityId=%s", wfinfo.get("workflowId"), wfinfo.get("contextId"), wfinfo.get("activityId"));
        }
        
        enqueue(workflowId, type, objectId, url);
    } //enqueue()
    
    
    public void checkAndIndex() {
    } //checkAndIndex()
    
    
} //class TextIndexer
