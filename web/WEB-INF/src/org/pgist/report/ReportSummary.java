package org.pgist.report;

import java.io.Serializable;

/**
 * <span style="color:red;">POJO</span>: PGIST Report Summary Class<br>
 * <span style="color:red;">TABLE</span>: pgist_crit
 * 
 * <p>ReportSummary class contains Report Summary text for each package
 * 
 * @author John
 * 
 * @hibernate.class table="pgist_report_summary" lazy="true"
 */
public class ReportSummary implements Serializable {

    /**
     * <span style="color:blue;">(Column.)</span>
     * id of the announcement. Unique id number used to identify each announcement.
     */
	private Long id;

	private String executiveSummary;
	
	private String part1a; 
	
	private String part1b;
	
	private String part2a;
	
	private String part3a;

	private String part4a;
	
	private boolean finalized = false;
	
    private String finalVoteDate;

    private String finalReportDate;
	
    
    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public boolean isFinalized() {
		return finalized;
	}

	public void setFinalized(boolean finalized) {
		this.finalized = finalized;
	}

	
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
     * @return
     * @hibernate.property type="text" not-null="false"
     */
	public String getExecutiveSummary() {
		return executiveSummary;
	}

	public void setExecutiveSummary(String executiveSummary) {
		this.executiveSummary = executiveSummary;
	}

	
    /**
     * @return
     * @hibernate.property type="text" not-null="false"
     */
	public String getPart1a() {
		return part1a;
	}

	public void setPart1a(String part1a) {
		this.part1a = part1a;
	}

	
    /**
     * @return
     * @hibernate.property type="text" not-null="false"
     */
	public String getPart1b() {
		return part1b;
	}

	public void setPart1b(String part1b) {
		this.part1b = part1b;
	}

	
    /**
     * @return
     * @hibernate.property type="text" not-null="false"
     */
	public String getPart2a() {
		return part2a;
	}

	public void setPart2a(String part2a) {
		this.part2a = part2a;
	}

	
    /**
     * @return
     * @hibernate.property type="text" not-null="false"
     */
	public String getPart3a() {
		return part3a;
	}

	public void setPart3a(String part3a) {
		this.part3a = part3a;
	}

	
    /**
     * @return
     * @hibernate.property type="text" not-null="false"
     */
	public String getPart4a() {
		return part4a;
	}

	public void setPart4a(String part4a) {
		this.part4a = part4a;
	}


    /**
     * @return
     * @hibernate.property type="text" not-null="false"
     */
	public String getFinalVoteDate() {
		return finalVoteDate;
	}

	public void setFinalVoteDate(String finalVoteDate) {
		this.finalVoteDate = finalVoteDate;
	}

	
    /**
     * @return
     * @hibernate.property type="text" not-null="false"
     */
	public String getFinalReportDate() {
		return finalReportDate;
	}

	public void setFinalReportDate(String finalReportDate) {
		this.finalReportDate = finalReportDate;
	}

	
	
} //class ReportSummary
