package org.pgist.report;

import java.io.Serializable;

/**
 * <span style="color:red;">POJO</span>: PGIST Announcement Class<br>
 * <span style="color:red;">TABLE</span>: pgist_announcement
 * 
 * <p>Report Suite
 * 
 * @author John
 * 
 * @hibernate.class table="pgist_report_suite" lazy="true"
 */
public class ReportSuite implements Serializable{

	
	private Long id;
	
	private ReportSummary reportSummary;
	
	private ReportStats reportStats;

	
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
     * 
     * @hibernate.one-to-one column="reportstats_id" cascade="all"
     */
	public ReportStats getReportStats() {
		return reportStats;
	}

	
	public void setReportStats(ReportStats reportStats) {
		this.reportStats = reportStats;
	}

    /**
     * @return
     * 
     * @hibernate.one-to-one column="reportsummary_id" cascade="all"
     */
	public ReportSummary getReportSummary() {
		return reportSummary;
	}

	
	public void setReportSummary(ReportSummary reportSummary) {
		this.reportSummary = reportSummary;
	}
	
	
}
