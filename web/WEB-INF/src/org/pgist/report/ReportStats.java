package org.pgist.report;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.pgist.system.County;
import org.pgist.users.User;
import org.pgist.packages.ClusteredPackage;

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
	
	private Long workflowId; //not used, delete later
	
	private int totalUsers;
	
	private int males;
	
	private int females;
	
	private Set<County> counties = new HashSet<County>();
	
	private Set<String> incomeRanges = new HashSet<String>();
	
	private Set<String> transTypes = new HashSet<String>();
	 
	private Map<County, Integer> countyStats = new HashMap<County, Integer>();
	
	private Map<String, Integer> incomeStats = new HashMap<String, Integer>();
	
	private Map<String, Integer> transportStats = new HashMap<String, Integer>();
	
	private Set<User> users = new HashSet<User>();
	
	private int totalPackages;
	
	private int userCompleted;
	
	private int quanity;
	
	private ClusteredPackage preferredPackage;

	private int totalVotes;
	
	private int numEndorsed;
	
	private int totalCost;
	
	//# of projects inside the preferred package
	private int totalProjects; 
	
	
	/**
     * @hibernate.property not-null="false"
     */
	public int getTotalProjects() {
		return totalProjects;
	}


	public void setTotalProjects(int totalProjects) {
		this.totalProjects = totalProjects;
	}


	/**
     * @hibernate.property not-null="false"
     */
	public int getNumEndorsed() {
		return numEndorsed;
	}


	public void setNumEndorsed(int numEndorsed) {
		this.numEndorsed = numEndorsed;
	}


	/**
     * @hibernate.property not-null="false"
     */
	public int getTotalCost() {
		return totalCost;
	}


	public void setTotalCost(int totalCost) {
		this.totalCost = totalCost;
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
	 * @return the clusteredPackage
     * @hibernate.many-to-one column="clustered_pkg_id" cascade="all" class="org.pgist.packages.ClusteredPackage"
	 */
	public ClusteredPackage getPreferredPackage() {
		return preferredPackage;
	}


	public void setPreferredPackage(ClusteredPackage preferredPackage) {
		this.preferredPackage = preferredPackage;
	}


	/**
     * @hibernate.property not-null="false"
     */
	public int getQuanity() {
		return quanity;
	}


	public void setQuanity(int quanity) {
		this.quanity = quanity;
	}


	/**
     * @hibernate.property not-null="false"
     */
	public int getUserCompleted() {
		return userCompleted;
	}


	public void setUserCompleted(int userCompleted) {
		this.userCompleted = userCompleted;
	}


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


    /**
     * @return
     * 
     * @hibernate.set lazy="true" cascade="all" order-by="id"
     * @hibernate.collection-key column="reportstats_id"
     * @hibernate.collection-one-to-many class="org.pgist.system.County"
     */
	public Set<County> getCounties() {
		return counties;
	}


	public void setCounties(Set<County> counties) {
		this.counties = counties;
	}

	
	/**
	* @hibernate.set table="pgist_report_income_ranges"
	* @hibernate.collection-key column="reportstats_id"
	* @hibernate.collection-element type="string" column="income_range"
	*/
	public Set getIncomeRanges() {
		return incomeRanges;
	}


	public void setIncomeRanges(Set<String> incomeRanges) {
		this.incomeRanges = incomeRanges;
	}


	
	/**
	* @hibernate.set table="pgist_report_trans_types"
	* @hibernate.collection-key column="reportstats_id"
	* @hibernate.collection-element type="string" column="trans_types"
	*/
	public Set<String> getTransTypes() {
		return transTypes;
	}


	public void setTransTypes(Set<String> transTypes) {
		this.transTypes = transTypes;
	}


	/**
     * @hibernate.property not-null="false"
     */
    public int getTotalPackages() {
		return totalPackages;
	}


	public void setTotalPackages(int totalPackages) {
		this.totalPackages = totalPackages;
	}


}
