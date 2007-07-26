package org.pgist.report;

import org.apache.struts.action.ActionForm;


public class ReportForm extends ActionForm {

    
	private String executiveSummary;
	
	private String part1a;
	
	private String part1b;
	
	private String part2a;
	
	private String part3a;
	
	private String part4a;
	
	private String reason;

    private boolean save;
    
    private boolean finalized;
 
    private String finalVoteDate;

    private String finalReportDate;



	public String getExecutiveSummary() {
		return executiveSummary;
	}
	

	public void setExecutiveSummary(String executiveSummary) {
		this.executiveSummary = executiveSummary;
	}


	public String getPart1a() {
		return part1a;
	}


	public void setPart1a(String part1a) {
		this.part1a = part1a;
	}


	public String getPart1b() {
		return part1b;
	}


	public void setPart1b(String part1b) {
		this.part1b = part1b;
	}


	public String getPart2a() {
		return part2a;
	}


	public void setPart2a(String part2a) {
		this.part2a = part2a;
	}


	public String getPart3a() {
		return part3a;
	}


	public void setPart3a(String part3a) {
		this.part3a = part3a;
	}


	public String getPart4a() {
		return part4a;
	}


	public void setPart4a(String part4a) {
		this.part4a = part4a;
	}


	public boolean isSave() {
		return save;
	}


	public void setSave(boolean save) {
		this.save = save;
	}


	public String getReason() {
		return reason;
	}


	public void setReason(String reason) {
		this.reason = reason;
	}


	public boolean isFinalized() {
		return finalized;
	}


	public void setFinalized(boolean finalized) {
		this.finalized = finalized;
	}


	public String getFinalReportDate() {
		return finalReportDate;
	}


	public void setFinalReportDate(String finalReportDate) {
		this.finalReportDate = finalReportDate;
	}


	public String getFinalVoteDate() {
		return finalVoteDate;
	}


	public void setFinalVoteDate(String finalVoteDate) {
		this.finalVoteDate = finalVoteDate;
	}



	
}//class UserForm
