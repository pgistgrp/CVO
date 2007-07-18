package org.pgist.report;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.pgist.system.County;
import org.pgist.users.User;

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
	
	private Long workflowId;
	
	private int totalUsers;
	
	private int males;
	
	private int females;
	 
	private Map<County, Integer> countyStats = new HashMap<County, Integer>();
	
	private Map<String, Integer> incomeStats = new HashMap<String, Integer>();
	
	private Map<String, Integer> transportStats = new HashMap<String, Integer>();
	
	private Set<User> users = new HashSet<User>();
	
	
    /**
     * @return
     * 
     * @hibernate.set lazy="true" cascade="all" order-by="id"
     * @hibernate.collection-key column="user_id"
     * @hibernate.collection-one-to-many class="org.pgist.users.User"
     */
	public Set<User> getUsers() {
		return users;
	}


	public void setUsers(Set<User> users) {
		this.users = users;
	}


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
     * @hibernate.map table="pgist_report_stats_county_num_map" cascade="all"
     * @hibernate.collection-key column="reportstats_id"
     * @hibernate.index-many-to-many column="county_id" class="org.pgist.system.County"
     * @hibernate.collection-element type="integer" column="percent"
     */
	public Map<County, Integer> getCountyStats() {
		return countyStats;
	}

	
	public void setCountyStats(Map<County, Integer> countyStats) {
		this.countyStats = countyStats;
	}


    /**
     * @hibernate.property not-null="false"
     */
	public int getFemales() {
		return females;
	}


	public void setFemales(int females) {
		this.females = females;
	}


	/**
     * @hibernate.property not-null="false"
     */
	public int getMales() {
		return males;
	}


	public void setMales(int males) {
		this.males = males;
	}


    /**
     * @return
     * 
     * @hibernate.map table="pgist_report_stats_income_num_map" cascade="all"
     * @hibernate.collection-key column="reportstats_id"
     * @hibernate.index-many-to-many column="incomeRange" class="org.pgist.users.BaseUser"
     * @hibernate.collection-element type="integer" column="num"
     */
	public Map<String, Integer> getIncomeStats() {
		return incomeStats;
	}


	public void setIncomeStats(Map<String, Integer> incomeStats) {
		this.incomeStats = incomeStats;
	}


    /**
     * @return
     * 
     * @hibernate.map table="pgist_report_stats_transport_num_map" cascade="all"
     * @hibernate.collection-key column="reportstats_id"
     * @hibernate.index-many-to-many column="primaryTransport" class="org.pgist.users.User"
     * @hibernate.collection-element type="integer" column="num"
     */
	public Map<String, Integer> getTransportStats() {
		return transportStats;
	}


	public void setTransportStats(Map<String, Integer> transportStats) {
		this.transportStats = transportStats;
	}


	/**
     * @hibernate.property not-null="false"
     */
	public int getTotalUsers() {
		return totalUsers;
	}


	public void setTotalUsers(int totalUsers) {
		this.totalUsers = totalUsers;
	}


	/**
     * @hibernate.property not-null="false"
     */
	public Long getWorkflowId() {
		return workflowId;
	}


	public void setWorkflowId(Long workflowId) {
		this.workflowId = workflowId;
	}
	
	

}
