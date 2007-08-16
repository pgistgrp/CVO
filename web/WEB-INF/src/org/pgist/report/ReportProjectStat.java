package org.pgist.report;

import java.io.Serializable;

import org.pgist.projects.Project;
import org.pgist.projects.ProjectRef;
import org.pgist.projects.ProjectAltRef;

/**
 * <span style="color:red;">POJO</span>: PGIST Announcement Class<br>
 * <span style="color:red;">TABLE</span>: pgist_announcement
 * 
 * <p>Report project statistics
 * 
 * @author John
 * 
 * @hibernate.class table="pgist_report_project_stats" lazy="true"
 */
public class ReportProjectStat implements Serializable{

	private Long id;
	
	private Project project;
	
	private ProjectRef projRef;
	
	private int yesVotes = 0;
	
	private int totalVotes = 0;

	
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
     * @hibernate.property not-null="true"
     */
	public int getTotalVotes() {
		return totalVotes;
	}

	public void setTotalVotes(int totalVotes) {
		this.totalVotes = totalVotes;
	}

    /**
     * @return
     * @hibernate.property not-null="true"
     */
	public int getYesVotes() {
		return yesVotes;
	}

	public void setYesVotes(int yesVotes) {
		this.yesVotes = yesVotes;
	}

	
    /**
     * @return
     * @hibernate.many-to-one column="project_id" lazy="true"
     */
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

    /**
     * @return
     * @hibernate.many-to-one column="proj_ref_id" lazy="true"
     */
	public ProjectRef getProjRef() {
		return projRef;
	}

	public void setProjRef(ProjectRef projRef) {
		this.projRef = projRef;
	}
	
}
