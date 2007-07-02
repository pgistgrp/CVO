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
	
	private ReportStats reportStatsConcerns;

	private ReportStats reportStatsEval;
	
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
     * @hibernate.one-to-one column="reportstatsconcerns_id" cascade="all"
     */
	public ReportStats getReportStatsConcerns() {
		return reportStatsConcerns;
	}

	
	public void setReportStatsConcerns(ReportStats reportStatsConcerns) {
		this.reportStatsConcerns = reportStatsConcerns;
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


    /**
     * @return
     * 
     * @hibernate.one-to-one column="reportstatseval_id" cascade="all"
     */
	public ReportStats getReportStatsEval() {
		return reportStatsEval;
	}


	public void setReportStatsEval(ReportStats reportStatsEval) {
		this.reportStatsEval = reportStatsEval;
	}
	
	
}
