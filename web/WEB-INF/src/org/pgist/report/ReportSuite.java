package org.pgist.report;

import java.io.Serializable;
import java.util.Set;

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
	
	private ReportStats statsES;
	
	private ReportStats statsPart1;
	
	private ReportStats statsPart2;
	
	private ReportStats statsPart3;
	
	private ReportStats statsPart4;
	
	private Set<ReportVote> votes;
	
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
     * @hibernate.many-to-one column="reportsummary_id" cascade="all"
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
     * @hibernate.set lazy="true" cascade="all" order-by="id"
     * @hibernate.collection-key column="suite_id"
     * @hibernate.collection-one-to-many class="org.pgist.report.ReportVote"
     */
	public Set<ReportVote> getVotes() {
		return votes;
	}


	public void setVotes(Set<ReportVote> votes) {
		this.votes = votes;
	}


    /**
     * @return
     * 
     * @hibernate.many-to-one column="reportstats_part_1" cascade="all"
     */
	public ReportStats getStatsPart1() {
		return statsPart1;
	}


	public void setStatsPart1(ReportStats statsPart1) {
		this.statsPart1 = statsPart1;
	}


    /**
     * @return
     * 
     * @hibernate.many-to-one column="reportstats_part_2" cascade="all"
     */
	public ReportStats getStatsPart2() {
		return statsPart2;
	}


	public void setStatsPart2(ReportStats statsPart2) {
		this.statsPart2 = statsPart2;
	}


    /**
     * @return
     * 
     * @hibernate.many-to-one column="reportstats_part_3" cascade="all"
     */
	public ReportStats getStatsPart3() {
		return statsPart3;
	}


	public void setStatsPart3(ReportStats statsPart3) {
		this.statsPart3 = statsPart3;
	}


    /**
     * @return
     * 
     * @hibernate.many-to-one column="reportstats_part_4" cascade="all"
     */
	public ReportStats getStatsPart4() {
		return statsPart4;
	}


	public void setStatsPart4(ReportStats statsPart4) {
		this.statsPart4 = statsPart4;
	}


    /**
     * @return
     * 
     * @hibernate.many-to-one column="reportstats_es" cascade="all"
     */
	public ReportStats getStatsES() {
		return statsES;
	}


	public void setStatsES(ReportStats statsES) {
		this.statsES = statsES;
	}
	
	
}
