package org.pgist.report;

import java.io.Serializable;

/**
 * <span style="color:red;">POJO</span>: PGIST Announcement Class<br>
 * <span style="color:red;">TABLE</span>: pgist_announcement
 * 
 * <p>Associate User with Workflow Instance - Currently Not Used
 * 
 * @author John
 * 
 * @hibernate.class table="pgist_user_workflow_instance" lazy="true"
 */
public class UserWorkflowInstance implements Serializable{

	
	private Long id;
	
	
	private Long workflowId;
	
	
	private Long userId;
	
	
	/**
     * @hibernate.property not-null="false"
     */
	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


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
     * @hibernate.property not-null="false"
     */
	public Long getWorkflowId() {
		return workflowId;
	}


	public void setWorkflowId(Long workflowId) {
		this.workflowId = workflowId;
	}
}
