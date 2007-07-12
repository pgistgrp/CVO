package org.pgist.report;

import org.pgist.system.BaseDAOImpl;
import org.pgist.packages.PackageVoteSuite;
import org.pgist.packages.VoteSuiteStat;
import org.pgist.packages.ClusteredPackage;
import org.pgist.packages.PackageSuite;
import org.pgist.packages.UserPackage;
import org.pgist.projects.ProjectSuite;
import org.pgist.funding.FundingSourceSuite;
import org.pgist.criteria.CriteriaSuite;
import org.pgist.cvo.CCT;
import org.pgist.cvo.Concern;
import org.pgist.users.User;
import org.pgist.system.County;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;


public class ReportDAOImpl extends BaseDAOImpl implements ReportDAO {

	
	private static final String hql_getUserStatistics1 = "from User u where u.gender=?"; //Male Female stats
	private static final String hql_getUserStatistics2 = "from User u where u.age<=? and u.age>="; //Age stats
	
	public Map getUserStatistics() throws Exception {
		Map map = new HashMap();
		
		List maleList = getHibernateTemplate().find(hql_getUserStatistics1, new Object[] {new Boolean(true),});
		List femaleList = getHibernateTemplate().find(hql_getUserStatistics1, new Object[] {new Boolean(false),});
		int males = maleList.size();
		int females = femaleList.size();
		int percentMale = males /(males + females);
		int percentFemale = females /(males + females);
		map.put("percentMale", percentMale);
		map.put("percentFemale", percentFemale);
		
		List transList = getHibernateTemplate().find(hql_getUserStatistics2, new Object[] {new Boolean(true),});
		
		return map; 
	}
	
	
	public void createConcernStatistics(Long workflowId, Long cctId, Long repoSuiteId) throws Exception {
		CCT cct = (CCT)load(CCT.class, cctId);
		ReportSuite repoSuite = (ReportSuite) load(ReportSuite.class, repoSuiteId);
		ReportStats rs = repoSuite.getReportStatsConcerns();
		if(rs==null) {
			rs = new ReportStats();
		}
		
		//Variables to store stats
		Set<User> users = new HashSet();
		Map<County, Integer> countySet = new HashMap();
		Map<String, Integer> incomeSet = new HashMap();		
		Map<String, Integer> transportSet = new HashMap();	
		int male = 0;
		int female = 0;
		
		
		Set<Concern> concerns = cct.getConcerns();
		for(Concern c : concerns) {
			User u = c.getAuthor();
			
			
			//Check if users were already counted
			if(!users.contains(u)) {
				//Gender
				if(u.isGender()) {
					male++;
				} else {
					female++;
				}
				
				//County
				if(u.getCountyId()!=null) {
					County county = (County) load(County.class, u.getCountyId());
					
					if(countySet.get(county)==null) {
						countySet.put(county, 1);
					} else {
						int num = countySet.get(county);
						countySet.put(county, num+1);
					}
				}
				
				//Income
				String income = u.getIncomeRange();
				if(income!=null && !("".equals(income.trim()))) {
					if(incomeSet.get(income)==null) {
						incomeSet.put(income, 1);
					} else {
						int num = incomeSet.get(income);
						incomeSet.put(income, num+1);
					}
				}
				
				//Primary Transport
				String transport = u.getPrimaryTransport();
				if(transport!=null && !("".equals(transport.trim()))) {
					if(transportSet.get(transport)==null) {
						transportSet.put(transport, 1);
					} else {
						int num = transportSet.get(transport);
						transportSet.put(transport, num+1);
					}
				}
				//add user Users set
				users.add(u);
			} //if()	
		} //for()
		
		//calculate stats
		int totalUsers = male + female;
		if(totalUsers>0) {
			int percentMale = male/totalUsers;
			int percentFemale = female/totalUsers;
		
			//convert numbers into percents
			Set<County> countyKeys = countySet.keySet();
			for(County c : countyKeys) {
				int num = countySet.get(c);
				int percent = num/totalUsers;
				countySet.put(c, percent);
			}
			
			Set<String> incomeKeys = incomeSet.keySet();
			for(String i : incomeKeys) {
				int num = incomeSet.get(i);
				int percent = num/totalUsers;
				incomeSet.put(i, percent);
			}
			
			Set<String> transportKeys = transportSet.keySet();
			for(String t : transportKeys) {
				int num = transportSet.get(t);
				int percent = num/totalUsers;
				transportSet.put(t, percent);
			}
			// save stats to reportStats
			rs.setFemales(percentFemale);
			rs.setMales(percentMale);
		}
		//save stats to reportStats
		rs.setCountyStats(countySet);
		rs.setIncomeStats(incomeSet);
		rs.setTransportStats(transportSet);
		
		rs.setUsers(users);
		rs.setTotalUsers(totalUsers);
		save(rs);
		repoSuite.setReportStatsConcerns(rs);
		save(repoSuite);
	}
	
	
	public void createPkgStatistics(Long workflowId, Long repoSuiteId, Long pkgSuiteId) throws Exception {
		PackageSuite pkgSuite = (PackageSuite) load(PackageSuite.class, pkgSuiteId); 
		ReportSuite repoSuite = (ReportSuite) load(ReportSuite.class, repoSuiteId);
		ReportStats rs = repoSuite.getReportStatsEval();
		if(rs==null) {
			rs = new ReportStats();
		}
		
		System.out.println("****" + rs);
		
		//Variables to store stats
		Set<User> users = new HashSet();
		Map<County, Integer> countySet = new HashMap();
		Map<String, Integer> incomeSet = new HashMap();		
		Map<String, Integer> transportSet = new HashMap();	
		int male = 0;
		int female = 0;
		
		Set<UserPackage> packages = pkgSuite.getUserPkgs();
		for(UserPackage up : packages) {
			User u = up.getAuthor();
			
			// Check if users were already counted
			if(!users.contains(u)) {
				//Gender
				if(u.isGender()) {
					male++;
				} else {
					female++;
				}
				
				//County
				if(u.getCountyId()!=null) {
					County county = (County) load(County.class, u.getCountyId());
				
					if(countySet.get(county)==null) {
						countySet.put(county, 1);
					} else {
						int num = countySet.get(county);
						countySet.put(county, num+1);
					}
				}
				
				//Income
				String income = u.getIncomeRange();
				if(income!=null && !("".equals(income.trim()))) {
					if(incomeSet.get(income)==null) {
						incomeSet.put(income, 1);
					} else {
						int num = incomeSet.get(income);
						incomeSet.put(income, num+1);
					}
				}
				
				//Primary Transport
				String transport = u.getPrimaryTransport();
				if(transport!=null && !("".equals(transport.trim()))) {
					if(transportSet.get(transport)==null) {
						transportSet.put(transport, 1);
					} else {
						int num = transportSet.get(transport);
						transportSet.put(transport, num+1);
					}
				}
				//add user Users set
				users.add(u);
			} //if()	
		} //for()
		
		//calculate stats
		int totalUsers = male + female;
		if(totalUsers>0) {
			int percentMale = male/totalUsers;
			int percentFemale = female/totalUsers;
		
			//convert numbers into percents
			Set<County> countyKeys = countySet.keySet();
			for(County c : countyKeys) {
				int num = countySet.get(c);
				int percent = num/totalUsers;
				countySet.put(c, percent);
			}
			
			Set<String> incomeKeys = incomeSet.keySet();
			for(String i : incomeKeys) {
				int num = incomeSet.get(i);
				int percent = num/totalUsers;
				incomeSet.put(i, percent);
			}
			
			Set<String> transportKeys = transportSet.keySet();
			for(String t : transportKeys) {
				int num = transportSet.get(t);
				int percent = num/totalUsers;
				transportSet.put(t, percent);
			}
			// save stats to reportStats
			rs.setFemales(percentFemale);
			rs.setMales(percentMale);
		}
		//save stats to reportStats
		rs.setCountyStats(countySet);
		rs.setIncomeStats(incomeSet);
		rs.setTransportStats(transportSet);
		
		rs.setUsers(users);
		rs.setTotalUsers(totalUsers);
		save(rs);
		repoSuite.setReportStatsEval(rs);
		save(repoSuite);
	}
	
	
	public ClusteredPackage getPreferredClusteredPackage(Long pkgSuiteId) throws Exception {
		PackageSuite ps = (PackageSuite) load(PackageSuite.class, pkgSuiteId);
		Set<PackageVoteSuite> voteSuites = ps.getVoteSuites();
		
		int highGrade = -100;
		VoteSuiteStat bestVSS = new VoteSuiteStat(); 
		
		for(PackageVoteSuite pvs : voteSuites) {
			Set<VoteSuiteStat> votes = pvs.getStats();
			
			for(VoteSuiteStat vss : votes) {
				int high = vss.getHighVotePercent();
				int low = vss.getLowVotePercent();
				int grade = high - low;
				
				if(highGrade < grade) {
					highGrade = grade;
					bestVSS = vss;
				}
			}
		}
		return bestVSS.getClusteredPackage();
	}
	
	
	public Collection getVoteSuiteStats(Long pkgSuiteId) throws Exception {
		PackageSuite ps = (PackageSuite) load(PackageSuite.class, pkgSuiteId);
		Set<PackageVoteSuite> voteSuites = ps.getVoteSuites();
		
		Set allVSS = new HashSet();
		
		for(PackageVoteSuite pvs : voteSuites) {
			Set<VoteSuiteStat> votes = pvs.getStats();
			allVSS.addAll(votes);
		}
		
		return allVSS;
	}
	
	
	public ReportSuite getReportSuiteById(Long id) throws Exception {
		ReportSuite rs = (ReportSuite) load(ReportSuite.class, id);	
		return rs;
	}
	
	
	public void editReportSummary(Long reportSummaryId, String executiveSummary, String participantsSummary, String concernSummary, String criteriaSummary, String projectSummary, String packageSummary, boolean finalized) throws Exception {
		ReportSummary rSummary = (ReportSummary) load(ReportSummary.class, reportSummaryId);
		
		rSummary.setExecutiveSummary(executiveSummary);
	 	rSummary.setParticipantsSummary(participantsSummary);
	 	rSummary.setConcernSummary(concernSummary);
	 	rSummary.setCriteriaSummary(criteriaSummary);
	 	rSummary.setProjectSummary(projectSummary);
	 	rSummary.setPackageSummary(packageSummary);
	 	rSummary.setFinalized(finalized);
	 	
	 	save(rSummary);
	}

	
	public ReportSuite createReportSuite() throws Exception {
		ReportSuite rSuite = new ReportSuite();
		save(rSuite);
		ReportSummary rSummary = new ReportSummary();
		save(rSummary);
		ReportStats rStats = new ReportStats();
		save(rStats);
		
		rSuite.setReportStatsConcerns(rStats);
		rSuite.setReportSummary(rSummary);
		save(rSuite);
				
		return rSuite;
	}
	
	
}
