package org.pgist.report;

import org.apache.struts.action.ActionForm;


public class ReportForm extends ActionForm {

    
	private String executiveSummary;
	
	private String participantsSummary;
	
	private String concernSummary;
	
	private String criteriaSummary;
	
	private String projectSummary;
	
	private String packageSummary;
	
	private String reason;

    private boolean save;
    
    private boolean finalized;
 
    private String finalVoteDate;

    private String finalReportDate;



	public String getConcernSummary() {
		return concernSummary;
	}

	
	public void setConcernSummary(String concernSummary) {
		this.concernSummary = concernSummary;
	}

	
	public String getCriteriaSummary() {
		return criteriaSummary;
	}

	
	public void setCriteriaSummary(String criteriaSummary) {
		this.criteriaSummary = criteriaSummary;
	}

	public String getExecutiveSummary() {
		return executiveSummary;
	}
	

	public void setExecutiveSummary(String executiveSummary) {
		this.executiveSummary = executiveSummary;
	}

	
	public String getPackageSummary() {
		return packageSummary;
	}

	
	public void setPackageSummary(String packageSummary) {
		this.packageSummary = packageSummary;
	}

	
	public String getParticipantsSummary() {
		return participantsSummary;
	}

	
	public void setParticipantsSummary(String participantsSummary) {
		this.participantsSummary = participantsSummary;
	}

	
	public String getProjectSummary() {
		return projectSummary;
	}

	
	public void setProjectSummary(String projectSummary) {
		this.projectSummary = projectSummary;
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
