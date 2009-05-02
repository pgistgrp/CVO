package org.pgist.system;



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
    
    private String link;
    
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
    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
    
    
} //class IndexingTask
