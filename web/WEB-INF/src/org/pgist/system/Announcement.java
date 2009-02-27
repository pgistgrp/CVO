package org.pgist.system;

import java.io.Serializable;
import java.util.Date;

/**
 * <span style="color:red;">POJO</span>: PGIST Announcement Class<br>
 * <span style="color:red;">TABLE</span>: pgist_announcement
 * 
 * <p>Announcement class contains information for a moderator announcement post
 * 
 * @author John
 * 
 * @hibernate.class table="pgist_announcement" lazy="true"
 */
public class Announcement implements Serializable{
	
    /**
     * <span style="color:blue;">(Column.)</span>
     * id of the announcement. Unique id number used to identify each announcement.
     */
	private Long id;
	
    /**
     * <span style="color:blue;">(Column.)</span>
     * date of the Announcement. Date of the announcement post.
     */
	private Date date;
	
    /**
     * <span style="color:blue;">(Column.)</span>
     * Message. Message contents of the announcement.
     */
	private String message;
	
	
	/**
     * <span style="color:blue;">(Column.)</span>
     * WorkflowId. Id of the workflow instance this announcement is associated with.
     */
	private Long workflowId;
	
	
    /**
     * @hibernate.id generator-class="native"
     */
    public Long getId() {
        return id;
    }
    
    
    public void setId(Long id) {
        this.id = id;
    }
	
    
    /**
     * @hibernate.property not-null="true"
     */
    public Date getDate() {
        return date;
    }


    public void setDate(Date date) {
        this.date = date;
    }
    
    
    /**
     * @return
     * @hibernate.property type="text" not-null="true"
     */
    public String getMessage() {
        return message;
    }

    
    public void setMessage(String message) {
        this.message = message;
    }
    

    /**
     * @hibernate.property not-null="true"
     */
    public Long getWorkflowId() {
        return workflowId;
    }

    
    public void setWorkflowId(Long workflowId) {
        this.workflowId = workflowId;
    }
    
    
} //Announcement()
