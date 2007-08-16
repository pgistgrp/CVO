package org.pgist.report;

import java.io.Serializable;

import org.pgist.cvo.Theme;

/**
 * <span style="color:red;">POJO</span>: PGIST report theme stat Class<br>
 * <span style="color:red;">TABLE</span>: pgist_report_theme_stat
 * 
 * <p>Report theme statistics
 * 
 * @author John
 * 
 * @hibernate.class table="pgist_report_theme_stats" lazy="true"
 */
public class ReportThemeStat implements Serializable{

	private Long id;
	
	private Theme theme;
	
	private int yesVotes;
	
	private int totalVotes;

	
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
     * @hibernate.many-to-one column="theme_id" lazy="true"
     */
	public Theme getTheme() {
		return theme;
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
	}

	/**
     * @hibernate.property not-null="false"
     */
	public int getTotalVotes() {
		return totalVotes;
	}

	public void setTotalVotes(int totalVotes) {
		this.totalVotes = totalVotes;
	}

	/**
     * @hibernate.property not-null="false"
     */
	public int getYesVotes() {
		return yesVotes;
	}

	public void setYesVotes(int yesVotes) {
		this.yesVotes = yesVotes;
	}
	
}
