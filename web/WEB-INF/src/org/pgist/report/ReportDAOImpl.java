package org.pgist.report;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.pgist.criteria.CriteriaRef;
import org.pgist.criteria.CriteriaSuite;
import org.pgist.criteria.CriteriaUserWeight;
import org.pgist.cvo.CCT;
import org.pgist.cvo.CSTService;
import org.pgist.cvo.CategoryReference;
import org.pgist.cvo.Concern;
import org.pgist.cvo.Theme;
import org.pgist.discussion.InfoObject;
import org.pgist.funding.FundingSourceAltRef;
import org.pgist.funding.FundingSourceAlternative;
import org.pgist.funding.FundingSourceSuite;
import org.pgist.packages.ClusteredPackage;
import org.pgist.packages.PackageSuite;
import org.pgist.packages.PackageVoteSuite;
import org.pgist.packages.UserPackage;
import org.pgist.packages.VoteSuiteStat;
import org.pgist.projects.ProjectAltRef;
import org.pgist.projects.ProjectAlternative;
import org.pgist.projects.ProjectService;
import org.pgist.projects.ProjectSuite;
import org.pgist.system.BaseDAOImpl;
import org.pgist.system.County;
import org.pgist.system.RegisterObject;
import org.pgist.system.SystemService;
import org.pgist.users.User;



public class ReportDAOImpl extends BaseDAOImpl implements ReportDAO {

	private SystemService systemService;
	
	private CSTService cstService;

	private ProjectService projectService;
	
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}
	
	public void setCstService(CSTService cstService) {
		this.cstService = cstService;
	}
	
	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
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
		
		//List transList = getHibernateTemplate().find(hql_getUserStatistics2, new Object[] {new Boolean(true),});
		
		return map; 
	}
	
	private static final String hql_createStatsPart1_1 = "from CategoryReference cr where cr.theme=?"; 
	private static final String hql_createStatsPart1_2 = "from InfoObject io where io.object.id=?"; 
	private static final String hql_createStatsPart1_3 = "from RegisterObject ro where ro.type=?"; 
	
	public void createStatsPart1(Long workflowId, Long cctId, Long repoSuiteId) throws Exception {
		System.out.println("***Excecute CreateStatsPart1()");
		CCT cct = (CCT)load(CCT.class, cctId);
		ReportSuite repoSuite = (ReportSuite) load(ReportSuite.class, repoSuiteId);
		ReportStats rs = new ReportStats();
		
		//Get Themes and stats
		List themelist = cstService.getThemes(cct);
		Set themes = new HashSet();
		themes.addAll(themelist);
		Iterator itThemes = themes.iterator();
		while(itThemes.hasNext()) {
			Theme theme = (Theme) itThemes.next();
			ReportThemeStat tempRTS = new ReportThemeStat();
			tempRTS.setTheme(theme);
			List catRefList = getHibernateTemplate().find(hql_createStatsPart1_1, new Object[] {theme,});			
			CategoryReference cr = (CategoryReference)catRefList.get(0);
			System.out.println("***ReportStats1 cr size: " + catRefList.size());
			
			List InfoObjList = getHibernateTemplate().find(hql_createStatsPart1_2, new Object[] {cr.getId(),});
			InfoObject io = (InfoObject) InfoObjList.get(0);
			System.out.println("***ReportStats1 io size: " + InfoObjList.size());
			
			tempRTS.setYesVotes(io.getNumAgree());
			tempRTS.setTotalVotes(io.getNumVote());
			
			rs.getReportThemeStats().add(tempRTS);
		}
		
		//Variables to store stats
		Set<User> users = new HashSet<User>();
		Set<RegisterObject> incomeRanges = new HashSet<RegisterObject>();
		Set<RegisterObject> transTypes = new HashSet<RegisterObject>();
		Map<County, Integer> countySet = new HashMap<County, Integer>();
		Map<RegisterObject, Integer> incomeSet = new HashMap<RegisterObject, Integer>();		
		Map<RegisterObject, Integer> transportSet = new HashMap<RegisterObject, Integer>();	
		int male = 0;
		int female = 0;
		
		//get Income ReportObjects
		Collection roIncome = getHibernateTemplate().find(hql_createStatsPart1_3, new Object[] {"income",});
		incomeRanges.addAll(roIncome);

		Collection roTransport = getHibernateTemplate().find(hql_createStatsPart1_3, new Object[] {"transport",});
		transTypes.addAll(roTransport);
		
		Set<Concern> concerns = cct.getConcerns();
		System.out.println("*** Concerns" + concerns.size());
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
				if(u.getIncomeRange()!=null && !(u.getIncomeRange().equals(""))){
					RegisterObject ro = u.getIncomeRange();
					if(ro!=null) {	
						incomeRanges.add(ro);					
						if(incomeSet.get(ro)==null) {
							incomeSet.put(ro, 1);
						} else {
							int num = incomeSet.get(ro);
							incomeSet.remove(ro);
							incomeSet.put(ro, num+1);
						}
						
					}
				}
				
				//Primary Transport
				//System.out.println("CreateStatsPart4: PrimaryTransport Username: " +u.getLoginname() + " primaryTransport: " + u.getPrimaryTransport().getValue());
				if(u.getPrimaryTransport()!=null && !(u.getPrimaryTransport().equals(""))) {
					RegisterObject pt = u.getPrimaryTransport();
					if(pt!=null){
						transTypes.add(pt);
						if(transportSet.get(pt)==null) {
							transportSet.put(pt, 1);
						} else {
							int num = transportSet.get(pt);
							transportSet.remove(pt);
							transportSet.put(pt, num+1);
						}
						
					}
				}
				
				//add user Users set
				users.add(u);
			} //if()	
		} //for()
		
		//calculate stats
		int totalUsers = male + female;

		if(totalUsers>0) {
		
			//convert numbers into percents
			Set<County> countyKeys = countySet.keySet();
			for(County c : countyKeys) {
				int num = countySet.get(c);
				countySet.put(c, num);
			}
			
			Set<RegisterObject> incomeKeys = incomeSet.keySet();
			for(RegisterObject i : incomeKeys) {
				int num = incomeSet.get(i);
				incomeSet.put(i, num);
			}
			
			Set<RegisterObject> transportKeys = transportSet.keySet();
			for(RegisterObject t : transportKeys) {
				int num = transportSet.get(t);
				transportSet.put(t, num);
			}
			// save stats to reportStats
			rs.setFemales(female);
			rs.setMales(male);
		}
		//save stats to reportStats
		rs.setCountyStats(countySet);
		rs.setIncomeStats(incomeSet);
		rs.setTransportStats(transportSet);
		rs.setIncomeRanges(incomeRanges);
		rs.setTransTypes(transTypes);
		//Complete: Add counties, incomeranges, transporttypes
		
		rs.setCounties(countySet.keySet());
		
		rs.setUsers(users);
		rs.setTotalUsers(totalUsers);
		save(rs);
		repoSuite.setStatsPart1(rs);
		save(repoSuite);
	}
	
	
	public void createStatsPart2(Long workflowId, Long cctId, Long repoSuiteId, Long critSuiteId) throws Exception {
		System.out.println("***Excecute CreateStatsPart2()");
		ReportSuite repoSuite = (ReportSuite) load(ReportSuite.class, repoSuiteId);
		ReportStats rs = new ReportStats();
		CriteriaSuite cs = (CriteriaSuite) load(CriteriaSuite.class, critSuiteId);
		int critNum = cs.getReferences().size();
		
		Set critRefs = cs.getReferences();
		Iterator itCr = critRefs.iterator();
		while(itCr.hasNext()) {
			CriteriaRef cr = (CriteriaRef) itCr.next();
			CriteriaUserWeight cuw = cs.getWeights().get(cr);
			Map<User, Integer> userWeights = cuw.getWeights();
			Set<User> keys = userWeights.keySet();
			Iterator itKeys = keys.iterator();
			int sumWeight = 0;
			while(itKeys.hasNext()) {
				User user = (User) itKeys.next();
				int weight = userWeights.get(user);
				sumWeight += weight;
			}
			if(userWeights.size() > 0) {
				cr.setGrade(sumWeight/userWeights.size());
			} else {
				cr.setGrade(0);
			}
			save(cr);
		}
		
		rs.setQuanity(critNum);
		save(cs);
		save(rs);
		save(repoSuite);
	}
	
	
	public void createStatsPart3(Long workflowId, Long cctId, Long repoSuiteId, Long projSuiteId, Long packSuiteId, Long fundSuiteId) throws Exception {
		System.out.println("***Excecute CreateStatsPart3()");
		ReportSuite repoSuite = (ReportSuite) load(ReportSuite.class, repoSuiteId);
		ReportStats rs = new ReportStats();
		
		ProjectSuite ps = (ProjectSuite) load(ProjectSuite.class, projSuiteId);
		int projectNum = ps.getReferences().size(); 
		rs.setQuanity(projectNum);
		
		PackageSuite pkgSuite = (PackageSuite) load(PackageSuite.class, packSuiteId); 
		int userNumCompleted = pkgSuite.getUserPkgs().size(); 
    	rs.setUserCompleted(userNumCompleted);
//		rs.setProjRefs(ps.getReferences());
//		
//		FundingSourceSuite fundSuite = (FundingSourceSuite) load(FundingSourceSuite.class, fundSuiteId);
//		rs.setFundRefs(fundSuite.getReferences());
//		save(rs);
		
		Set userPkgs = pkgSuite.getUserPkgs();
		Iterator itPkg = userPkgs.iterator();
		while(itPkg.hasNext()) {
			
			UserPackage up = (UserPackage) itPkg.next();
			Set ProjAltRefs = up.getProjAltRefs();
			Iterator itPar = ProjAltRefs.iterator();
			while(itPar.hasNext()) {
				ProjectAltRef par = (ProjectAltRef) itPar.next(); 
				ProjectAlternative pa = par.getAlternative();
				if(pa.getNumVotes()==0) {
					pa.setYesVotes(1);
					pa.setNumVotes(1);					
				} else {
					pa.setYesVotes(pa.getYesVotes()+1);
					pa.setNumVotes(pa.getNumVotes()+1);
				}
				save(pa);
			}
			
			Set fundAltRefs = up.getFundAltRefs();
			Iterator itFar = fundAltRefs.iterator();
			while(itFar.hasNext()) {
				FundingSourceAltRef far = (FundingSourceAltRef) itFar.next(); 
				FundingSourceAlternative fsa = far.getAlternative();
				if(fsa.getNumVotes()==0) {
					fsa.setYesVotes(1);
					fsa.setNumVotes(1);					
				} else {
					fsa.setYesVotes(fsa.getYesVotes()+1);
					fsa.setNumVotes(fsa.getNumVotes()+1);
				}
				save(fsa);
			}
		}
		
		save(rs);
		repoSuite.setStatsPart3(rs);
		save(repoSuite);
	}
	
	
	public void createStatsPart4(Long workflowId, Long repoSuiteId, Long pkgSuiteId) throws Exception {
		System.out.println("***Excecute CreateStatsPart4()");
		PackageSuite pkgSuite = (PackageSuite) load(PackageSuite.class, pkgSuiteId); 
		ReportSuite repoSuite = (ReportSuite) load(ReportSuite.class, repoSuiteId);
		ReportStats rs = new ReportStats();
		if(rs==null) {
			rs = new ReportStats();
		}
		
		//Variables to store stats
		Set<User> users = new HashSet<User>();
		Map<County, Integer> countySet = new HashMap<County, Integer>();
		Map<RegisterObject, Integer> incomeSet = new HashMap<RegisterObject, Integer>();		
		Map<RegisterObject, Integer> transportSet = new HashMap<RegisterObject, Integer>();	
		int male = 0;
		int female = 0;
		Set<RegisterObject> incomeRanges = new HashSet<RegisterObject>();
		Set<RegisterObject> transTypes = new HashSet<RegisterObject>();
		int totalPackages = 0;
		
//		//get Income ReportObjects
		Collection roIncome = getHibernateTemplate().find(hql_createStatsPart1_3, new Object[] {"income",});
		incomeRanges.addAll(roIncome);

		Collection roTransport = getHibernateTemplate().find(hql_createStatsPart1_3, new Object[] {"transport",});
		transTypes.addAll(roTransport);
		
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
				if(u.getIncomeRange()!=null && !(u.getIncomeRange().equals(""))){
					RegisterObject ro = u.getIncomeRange();
					if(ro!=null) {	
						incomeRanges.add(ro);					
						if(incomeSet.get(ro)==null) {
							incomeSet.put(ro, 1);
						} else {
							int num = incomeSet.get(ro);
							incomeSet.remove(ro);
							incomeSet.put(ro, num+1);
						}
						
					}
				}
				
				//Primary Transport
				//System.out.println("CreateStatsPart4: PrimaryTransport Username: " +u.getLoginname() + " primaryTransport: " + u.getPrimaryTransport().getValue());
				if(u.getPrimaryTransport()!=null && !(u.getPrimaryTransport().equals(""))) {
					RegisterObject pt = u.getPrimaryTransport();
					if(pt!=null){
						transTypes.add(pt);
						if(transportSet.get(pt)==null) {
							transportSet.put(pt, 1);
						} else {
							int num = transportSet.get(pt);
							transportSet.remove(pt);
							transportSet.put(pt, num+1);
						}
						
					}
				}
				
				//add user Users set
				users.add(u);
			} //if()	
		} //for()
		
		//calculate stats
		int totalUsers = male + female;
		if(totalUsers>0) {
		
			//convert numbers into percents
			Set<County> countyKeys = countySet.keySet();
			for(County c : countyKeys) {
				int num = countySet.get(c);
				countySet.put(c, num);
			}
			
			Set<RegisterObject> incomeKeys = incomeSet.keySet();
			for(RegisterObject i : incomeKeys) {
				int num = incomeSet.get(i);
				incomeSet.put(i, num);
			}
			
			Set<RegisterObject> transportKeys = transportSet.keySet();
			for(RegisterObject t : transportKeys) {
				int num = transportSet.get(t);
				transportSet.put(t, num);
			}
			// save stats to reportStats
			rs.setFemales(female);
			rs.setMales(male);
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
		System.out.println("***Excecute CreateStatsES()*");
		ReportSuite repoSuite = (ReportSuite) load(ReportSuite.class, repoSuiteId);
		ReportStats rs = new ReportStats();
		System.out.println("***ES start total users");
		
		rs.setTotalUsers(this.getNumUsers());
		System.out.println("***ES finish total users");
		PackageSuite ps = (PackageSuite) load(PackageSuite.class, packSuiteId);
		
		VoteSuiteStat vss = ps.getPrefPkgVoteSuiteStat();
		ClusteredPackage preferredPackage = vss.getClusteredPackage();	
		System.out.println("***ES finish preferred package");
		int numEndorsed = vss.getHighVotes() + vss.getMediumVotes();
		int totalVotes = vss.getTotalVotes();
		System.out.println("***ES startcost");
		
		int totalCost = (int) preferredPackage.getTotalCost();

		//save
		rs.setTotalVotes(totalVotes);
		rs.setNumEndorsed(numEndorsed);
		rs.setTotalCost(totalCost);
		rs.setTotalProjects(preferredPackage.getFundAltRefs().size());
		rs.setPreferredPackage(preferredPackage);
		save(rs);
		repoSuite.setStatsES(rs);
		save(repoSuite);
		System.out.println("***ES finish all");
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
		
			System.out.println("***RS created***"); //Print
		
		ReportSummary rSummary = new ReportSummary();
		save(rSummary);
		
			System.out.println("***rSummary created***"); //Print
		
		rSuite.setReportSummary(rSummary);
		save(rSuite);
		
			System.out.println("***Saved***"); //print		
		
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
	
	
	public Set getFundRefbySuiteId(Long suiteId) throws Exception {
		FundingSourceSuite fss = (FundingSourceSuite) load(FundingSourceSuite.class, suiteId);
			
		return fss.getReferences();
	}
	
	public Set getProjRefbySuiteId(Long suiteId) throws Exception {
		ProjectSuite ps = (ProjectSuite) load(ProjectSuite.class, suiteId);
			
		return ps.getReferences();
	}

	public User getUserById(Long id) throws Exception {
		return (User)load(User.class, id);
	}
	
	
	private static final String hql_getNumUsers = "from User u";

	public int getNumUsers() throws Exception {
		List userlist = getHibernateTemplate().find(hql_getNumUsers);	
		return userlist.size();
	}
	
}
