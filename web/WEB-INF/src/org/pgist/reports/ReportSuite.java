package org.pgist.reports;

public class ReportSuite {

	
	private Long id;
	
	public ReportSummary reportSummary;
	
	public ReportStats reportStats;

	
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
     * @hibernate.one-to-one column="reportstats_id" cascade="none"
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
     * @hibernate.one-to-one column="reportsummary_id" cascade="none"
     */
	public ReportSummary getReportSummary() {
		return reportSummary;
	}

	
	public void setReportSummary(ReportSummary reportSummary) {
		this.reportSummary = reportSummary;
	}
	
	
}
