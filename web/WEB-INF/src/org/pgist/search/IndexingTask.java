package org.pgist.search;



/**
 * @author kenny
 * 
 * @hibernate.class table="pgist_indexing_task" lazy="true"
 */
public class IndexingTask {
    
    
    private Long id;
    
    private String workflowId;
    
    private Long objectId;
    
    private String type;
    
    /**
     * Valid values: "indexing", "reindexing", "removing"
     */
    private String action;
    
    private String link;
    
    /**
     * true - failed task
     * false - waiting for process
     */
    private boolean markFailed;
    
    /**
     * true - to be deleted
     */
    private boolean markDeleted;
    
    /**
     * true - in process
     * false - not in process
     */
    private boolean flag;
    
    
    /**
     * @return
     * 
     * @hibernate.id generator-class="native"
     */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    
    /**
     * @return
     * @hibernate.property
     */
    public String getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }
    

    /**
     * @return
     * @hibernate.property
     */
    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }
    

    /**
     * @return
     * @hibernate.property
     */
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    

    /**
     * @return
     * @hibernate.property
     */
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    /**
     * @return
     * @hibernate.property length="1024"
     */
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
    

    /**
     * @return
     * @hibernate.property
     */
    public boolean isMarkFailed() {
        return markFailed;
    }

    public void setMarkFailed(boolean markFailed) {
        this.markFailed = markFailed;
    }

    /**
     * @return
     * @hibernate.property
     */
    public boolean isMarkDeleted() {
        return markDeleted;
    }

    public void setMarkDeleted(boolean markDeleted) {
        this.markDeleted = markDeleted;
    }

    /**
     * @return
     * @hibernate.property
     */
    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
    
    
} //class IndexingTask
