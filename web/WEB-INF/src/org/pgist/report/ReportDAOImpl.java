package org.pgist.report;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.pgist.cvo.CCT;
import org.pgist.cvo.Concern;
import org.pgist.packages.ClusteredPackage;
import org.pgist.packages.PackageSuite;
import org.pgist.packages.PackageVoteSuite;
import org.pgist.packages.UserPackage;
import org.pgist.packages.VoteSuiteStat;
import org.pgist.system.BaseDAOImpl;
import org.pgist.system.County;
import org.pgist.system.SystemService;
import org.pgist.users.User;
import org.pgist.criteria.CriteriaSuite;
import org.pgist.projects.ProjectSuite;



public class ReportDAOImpl extends BaseDAOImpl implements ReportDAO {

	private SystemService systemService;
	

	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}
	
	
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
	
	
	public void createStatsPart1(Long workflowId, Long cctId, Long repoSuiteId) throws Exception {
		CCT cct = (CCT)load(CCT.class, cctId);
		ReportSuite repoSuite = (ReportSuite) load(ReportSuite.class, repoSuiteId);
		ReportStats rs = new ReportStats();
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
		Set<String> incomeRanges = new HashSet<String>();
		Set<String> transTypes = new HashSet<String>();
		
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
				incomeRanges.add(income);
				
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
				transTypes.add(transport);
				
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
			int percentMale = (male/totalUsers)  * 100;
			int percentFemale = (female/totalUsers)  * 100;
		
			//convert numbers into percents
			Set<County> countyKeys = countySet.keySet();
			for(County c : countyKeys) {
				int num = countySet.get(c);
				int percent = (num/totalUsers) * 100;
				countySet.put(c, percent);
			}
			
			Set<String> incomeKeys = incomeSet.keySet();
			for(String i : incomeKeys) {
				int num = incomeSet.get(i);
				int percent = (num/totalUsers) * 100;
				incomeSet.put(i, percent);
			}
			
			Set<String> transportKeys = transportSet.keySet();
			for(String t : transportKeys) {
				int num = transportSet.get(t);
				int percent = (num/totalUsers)  * 100;
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
		rs.setIncomeRanges(incomeRanges);
		rs.setTransTypes(transTypes);
		//Complete: Add counties, incomeranges, transporttypes
		Collection<County> counties = systemService.getAllCounties();
		Set<County> sc = new HashSet<County>();
		sc.addAll(counties);
		rs.setCounties(sc);
		
		rs.setUsers(users);
		rs.setTotalUsers(totalUsers);
		save(rs);
		repoSuite.setStatsPart1(rs);
		save(repoSuite);
	}
	
	
	public void createStatsPart2(Long workflowId, Long cctId, Long repoSuiteId, Long critSuiteId) throws Exception {
		ReportSuite repoSuite = (ReportSuite) load(ReportSuite.class, repoSuiteId);
		ReportStats rs = new ReportStats();
		CriteriaSuite cs = (CriteriaSuite) load(CriteriaSuite.class, critSuiteId);
		int critNum = cs.getReferences().size();
		
		rs.setQuanity(critNum);
		save(rs);
		save(repoSuite);
	}
	
	
	public void createStatsPart3(Long workflowId, Long cctId, Long repoSuiteId, Long projSuiteId, Long packSuiteId) throws Exception {
		ReportSuite repoSuite = (ReportSuite) load(ReportSuite.class, repoSuiteId);
		ReportStats rs = new ReportStats();
		
		ProjectSuite ps = (ProjectSuite) load(ProjectSuite.class, projSuiteId);
		int projectNum = ps.getReferences().size(); //save this number
		rs.setQuanity(projectNum);
		
		PackageSuite pkgSuite = (PackageSuite) load(PackageSuite.class, packSuiteId); 
		int userNumCompleted = pkgSuite.getUserPkgs().size(); //save this number
		rs.setUserCompleted(userNumCompleted);
		
		save(rs);
		repoSuite.setStatsPart3(rs);
		save(repoSuite);
	}
	
	
	public void createStatsPart4(Long workflowId, Long repoSuiteId, Long pkgSuiteId) throws Exception {
		PackageSuite pkgSuite = (PackageSuite) load(PackageSuite.class, pkgSuiteId); 
		ReportSuite repoSuite = (ReportSuite) load(ReportSuite.class, repoSuiteId);
		ReportStats rs = new ReportStats();
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
		Set<String> incomeRanges = new HashSet<String>();
		Set<String> transTypes = new HashSet<String>();
		int totalPackages = 0;
		
		Set<UserPackage> packages = pkgSuite.getUserPkgs();
		totalPackages = packages.size();
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
				incomeRanges.add(income);
				
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
				transTypes.add(transport);
				
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
			int percentMale = (male/totalUsers) * 100;
			int percentFemale = (female/totalUsers) * 100;
		
			//convert numbers into percents
			Set<County> countyKeys = countySet.keySet();
			for(County c : countyKeys) {
				int num = countySet.get(c);
				int percent = (num/totalUsers) * 100;
				countySet.put(c, percent);
			}
			
			Set<String> incomeKeys = incomeSet.keySet();
			for(String i : incomeKeys) {
				int num = incomeSet.get(i);
				int percent = (num/totalUsers) * 100;
				incomeSet.put(i, percent);
			}
			
			Set<String> transportKeys = transportSet.keySet();
			for(String t : transportKeys) {
				int num = transportSet.get(t);
				int percent = (num/totalUsers) * 100;
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
		rs.setIncomeRanges(incomeRanges);
		rs.setTransTypes(transTypes);
		rs.setTotalPackages(totalPackages);
		
		rs.setUsers(users);
		rs.setTotalUsers(totalUsers);
		save(rs);
		repoSuite.setStatsPart4(rs);
		save(repoSuite);
	}
	
	
	public void createStatsES(Long workflowId, Long repoSuiteId, Long packSuiteId) throws Exception {
		ReportSuite repoSuite = (ReportSuite) load(ReportSuite.class, repoSuiteId);
		ReportStats rs = new ReportStats();
		
		ReportStats report1 = repoSuite.getStatsPart1();
		ReportStats report4 = repoSuite.getStatsPart4();
		
		HashSet allUsers = new HashSet();
		Set<User> users1 = report1.getUsers();
		Set<User> users2 = report4.getUsers();
		allUsers.addAll(users1);
		allUsers.addAll(users2);
		
		int allUserNum = allUsers.size();
		
		
		rs.setTotalUsers(allUserNum);
		
		PackageSuite ps = (PackageSuite) load(PackageSuite.class, packSuiteId);
		Set pkgVoteSuites = ps.getVoteSuites();
		
		//test code
		System.out.println("Step5: # of PackageVoteSuites: " + pkgVoteSuites.size());
		
		Iterator it = pkgVoteSuites.iterator();
		
		PackageVoteSuite pvs = (PackageVoteSuite) it.next();
		
		Set voteStats = pvs.getStats();
		System.out.println("Step5: # of voteStats: " + voteStats.size());
		
		int high = -1;
		int percentHighVote = -1;
		int totalVotes = -1;
		ClusteredPackage preferredPackage = null;
		
		
		Iterator itVotes = voteStats.iterator();
		while(itVotes.hasNext()) {
			VoteSuiteStat vss = (VoteSuiteStat) itVotes.next(); //Error Here
			int pHigh = vss.getHighVotePercent();
			int pMid = vss.getMediumVotePercent();
			int composite = pHigh + pMid;
			
			
			if(composite > high) {
				preferredPackage = vss.getClusteredPackage();
				high = composite;
				percentHighVote = pHigh;
				totalVotes = vss.getTotalVotes();
			}
		}

		
		int totalCost = (int) preferredPackage.getTotalCost();
		int numEndorsed = totalVotes * (high-100);
		
		//save
		rs.setNumEndorsed(numEndorsed);
		rs.setTotalCost(totalCost);
		rs.setTotalPorjects(preferredPackage.getFundAltRefs().size());
		rs.setPreferredPackage(preferredPackage);
		save(rs);
		repoSuite.setStatsES(rs);
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
	
	
	public void editReportSummary(Long reportSummaryId, String executiveSummary, String part1a, String part1b, String part2a, String part3a, String part4a, boolean finalized, String finalVoteDate, String finalReportDate) throws Exception {
		ReportSummary rSummary = (ReportSummary) load(ReportSummary.class, reportSummaryId);
		
		rSummary.setExecutiveSummary(executiveSummary);
	 	rSummary.setPart1a(part1a);
	 	rSummary.setPart1b(part1b);
	 	rSummary.setPart2a(part2a);
	 	rSummary.setPart3a(part3a);
	 	rSummary.setPart4a(part4a);
	 	
	 	rSummary.setFinalized(finalized);
	 	rSummary.setFinalVoteDate(finalVoteDate);
	 	rSummary.setFinalReportDate(finalReportDate);
	 	
	 	save(rSummary);
	}

	
	public ReportSuite createReportSuite() throws Exception {
		ReportSuite rSuite = new ReportSuite();
		save(rSuite);
		ReportSummary rSummary = new ReportSummary();
		save(rSummary);

		
		rSuite.setReportSummary(rSummary);
		save(rSuite);
				
		return rSuite;
	}
	
	
	public void createReportVote(Long suiteId, boolean vote, Long userId) throws Exception {
		User user = (User) load(User.class, userId);
		ReportSuite rs = (ReportSuite) load(ReportSuite.class, suiteId);
		
		
		
		boolean save = true;
		Set votes = rs.getVotes();
		Iterator it = votes.iterator();
		while(it.hasNext()) {
			ReportVote rv = (ReportVote) it.next();
			if(rv.getOwner()==user) {
				save = false;
			}
		}
		
		if(save){
			ReportVote reportVote = new ReportVote();
			
			reportVote.setOwner(user);
			reportVote.setVoting(vote);
			
			save(reportVote);
			rs.getVotes().add(reportVote);
			save(rs);
		}
	}
	
	
	public boolean getUserVoted(Long suiteId, Long userId) throws Exception {
		User user = (User) load(User.class, userId);
		ReportSuite rs = (ReportSuite) load(ReportSuite.class, suiteId);
		Set<ReportVote> votes = rs.getVotes();
		
		Iterator it = votes.iterator();
		while(it.hasNext()) {
			ReportVote rv = (ReportVote) it.next();
			if(rv.getOwner()==user) {
				return true;
			}
		}
		return false;
	}
	
	
	public Map getVoteStats(Long suiteId) throws Exception {
		ReportSuite rs = (ReportSuite) load(ReportSuite.class, suiteId);
		Set<ReportVote> votes = rs.getVotes();
		
		int yes = 0;
		int total = votes.size();
		
		Iterator it = votes.iterator();
		while(it.hasNext()) {
			ReportVote rv = (ReportVote) it.next();
			if(rv.isVoting()) {
				yes++;
			}
		}
		
		Map map = new HashMap();
		map.put("yes", yes);
		map.put("total", total);
		map.put("no", total-yes);
		
		return map;
	}


	
}
