package org.pgist.reports;

import java.io.Serializable;

/**
 * <span style="color:red;">POJO</span>: PGIST Announcement Class<br>
 * <span style="color:red;">TABLE</span>: pgist_announcement
 * 
 * <p>Report statistics
 * 
 * @author John
 * 
 * @hibernate.class table="pgist_report_stats" lazy="true"
 */
public class ReportStats implements Serializable{

	private Long id;
	
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
	
}
