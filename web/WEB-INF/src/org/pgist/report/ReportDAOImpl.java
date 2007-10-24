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
import org.pgist.cvo.Comment;
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
import org.pgist.packages.PackageUserVote;
import org.pgist.projects.ProjectAltRef;
import org.pgist.projects.ProjectAlternative;
import org.pgist.projects.ProjectService;
import org.pgist.projects.ProjectSuite;
import org.pgist.system.BaseDAOImpl;
import org.pgist.system.County;
import org.pgist.system.RegisterObject;
import org.pgist.system.SystemService;
import org.pgist.users.User;
import org.pgist.discussion.InfoStructure;
import org.pgist.discussion.InfoObject;
import org.pgist.discussion.Discussion;
import org.pgist.discussion.DiscussionPost;
import org.pgist.discussion.DiscussionReply;
import org.pgist.system.YesNoVoting;



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
	
	private static final String hql_getUserStatistics1 = "from User u where u.gender=? and u.deleted=? and u.enabled=?"; //Male Female stats
	
	public Map getUserStatistics() throws Exception {
		Map map = new HashMap();
		
		List maleList = getHibernateTemplate().find(hql_getUserStatistics1, new Object[] {new Boolean(true), new Boolean(false), new Boolean(true),});
		List femaleList = getHibernateTemplate().find(hql_getUserStatistics1, new Object[] {new Boolean(false), new Boolean(false), new Boolean(true),});
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
	private static final String hql_createStatsPart1_4 = "from County c"; 
	
	public void createStatsPart1(Long workflowId, Long cctId, Long repoSuiteId, Long packSuiteId, Long critSuiteId, Long projSuiteId, Long fundSuiteId) throws Exception {
		System.out.println("***Excecute CreateStatsPart1()");
		CCT cct = (CCT)load(CCT.class, cctId);
		ReportSuite repoSuite = (ReportSuite) load(ReportSuite.class, repoSuiteId);
		ReportStats rs = new ReportStats();
		

		//Variables to store stats
		Set<User> allUsers = new HashSet<User>();
		Set<User> adminUsers = new HashSet<User>();
		Set<RegisterObject> incomeRanges = new HashSet<RegisterObject>();
		Set<RegisterObject> transTypes = new HashSet<RegisterObject>();
		Set<County> counties = new HashSet<County>();
		Map<County, Integer> countySet = new HashMap<County, Integer>();
		Map<RegisterObject, Integer> incomeSet = new HashMap<RegisterObject, Integer>();		
		Map<RegisterObject, Integer> transportSet = new HashMap<RegisterObject, Integer>();	
		int male = 0;
		int female = 0;
		
//		Get Themes and stats
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
		
		//Get all counties
		List countiesList = getHibernateTemplate().find(hql_createStatsPart1_4);
		Iterator itC = countiesList.iterator();
		while(itC.hasNext()){
			County c = (County)itC.next();
			counties.add(c);
		}
		
		
		//get Income ReportObjects
		Collection roIncome = getHibernateTemplate().find(hql_createStatsPart1_3, new Object[] {"income",});
		incomeRanges.addAll(roIncome);

		Collection roTransport = getHibernateTemplate().find(hql_createStatsPart1_3, new Object[] {"transport",});
		transTypes.addAll(roTransport);
		
		Set<Concern> concerns = cct.getConcerns();
		for(Concern c : concerns) {
			allUsers.add(c.getAuthor());
		}
		allUsers.addAll(this.getDiscussionUsers("sdp"));
		allUsers.addAll(this.getDiscussionUsers("sdf"));
		allUsers.addAll(this.getDiscussionUsers("sdc"));
		allUsers.addAll(this.getDiscussionUsers("sdcrit"));
		allUsers.addAll(this.getDiscussionUsers("sdPkg"));
		//allUsers.addAll(this.getDiscussionUsers("sdr"));
		allUsers.addAll(this.getVoteUsers(packSuiteId, false));
		allUsers.addAll(this.getYesNoVotingUsers(cctId, packSuiteId, critSuiteId, projSuiteId, fundSuiteId));
		allUsers.addAll(this.getPackageUsers(packSuiteId));
		
		for(User u : allUsers) {

			if(u.getRoles().size()<2) { //make sure they aren't a mod or admin
				//Gender
				if(u.isGender()) {
					male++;
				} else {
					female++;
				}
				
				//County
				if(u.getCountyId()!=null && !(u.getCountyId().equals(""))) {
					County county = (County) load(County.class, u.getCountyId());
					if(county != null) {
						if(countySet.get(county)==null) {
							countySet.put(county, 1);
						} else {
							int num = countySet.get(county);
							countySet.remove(county);
							countySet.put(county, num+1);
						}	
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
				RegisterObject pt = null;
				if(u.getPrimaryTransport()!=null && !(u.getPrimaryTransport().equals(""))) {
					pt = u.getPrimaryTransport();
				} else {
					pt = this.getPrimaryTransport(u);
				}
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
				
			} else { //if()
				adminUsers.add(u);
				System.out.println("Mod/admin removed " + u.getLoginname() + " count " + adminUsers.size());
			}
		} //for()
		
		allUsers.removeAll(adminUsers); //remove admins from the count
		
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
		rs.setCounties(counties);
		rs.setIncomeStats(incomeSet);
		rs.setIncomeRanges(incomeRanges);
		rs.setTransportStats(transportSet);
		rs.setTransTypes(transTypes);

		System.out.println("***ReportDAO: " + counties.size());
		
		rs.setUsers(allUsers);
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
				System.out.println("**Stats Part 2 " + user.getLoginname());
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

//		rs.setProjRefs(ps.getReferences());
//		
//		FundingSourceSuite fundSuite = (FundingSourceSuite) load(FundingSourceSuite.class, fundSuiteId);
//		rs.setFundRefs(fundSuite.getReferences());
//		save(rs);
		
		Set userPkgs = pkgSuite.getUserPkgs();
		Set modPkgs = new HashSet();
		Iterator itPkg = userPkgs.iterator();
		while(itPkg.hasNext()) {
			
			UserPackage up = (UserPackage) itPkg.next();
			if(up.getAuthor().getRoles().size()<2) {
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
			} else {
				modPkgs.add(up);
				System.out.println("**Mod package found " + up.getAuthor().getLoginname());
			}
		}
		userPkgs.removeAll(modPkgs);
		
		int userNumCompleted = pkgSuite.getUserPkgs().size(); 
    	rs.setUserCompleted(userNumCompleted);
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
		Set<User> allUsers = new HashSet<User>();
		Set<User> adminUsers = new HashSet<User>();
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
		

		//Set voteSuites = pkgSuite.getVoteSuites();
		//Iterator vsIt = voteSuites.iterator();
		
		allUsers.addAll(this.getVoteUsers(pkgSuiteId, true));
		int numCompleted = allUsers.size();
//		allUsers.addAll(this.getDiscussionUsers("sdPkg"));		
//		while(vsIt.hasNext()) {
//			PackageVoteSuite pvs = (PackageVoteSuite) vsIt.next();
//			Map<ClusteredPackage, PackageUserVote> uservotes = pvs.getUserVotes();
//			Collection<PackageUserVote> puv = uservotes.values();
//			for(PackageUserVote p : puv) {
//				allUsers.addAll(p.getVotes().keySet());
//			}
//		}

		Set<UserPackage> packages = pkgSuite.getUserPkgs();
     	totalPackages = packages.size();
//		for(UserPackage up : packages) {
		for(User u : allUsers) {
			//User u = up.getAuthor();
			
			System.out.println("ReportDAOImpl Stats part 4 User: " + u.getLoginname());
			
			// Check if users were already counted
			if(u.getRoles().size()<2) {
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
				RegisterObject pt = null;
				if(u.getPrimaryTransport()!=null && !(u.getPrimaryTransport().equals(""))) {
					pt = u.getPrimaryTransport();
				} else {
					pt = this.getPrimaryTransport(u);
				}
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
				
				//add user Users set
			} else {
				adminUsers.add(u);
				System.out.println("Mod/admin removed " + u.getLoginname() + " count " + adminUsers.size());
			}
		} //for()
		
		allUsers.removeAll(adminUsers); //remove admins from the count
		
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
		rs.setUserCompleted(numCompleted);
		//Complete: Add counties, incomeranges, transporttypes
		//countySet.remove("");
		//rs.setCounties(countySet.keySet());
		
		rs.setUsers(allUsers);
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
		rs.setTotalProjects(preferredPackage.getProjAltRefs().size());
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
	
	
 
	public Set<User> getVoteUsers(Long suiteId, boolean finalvote) throws Exception {
		System.out.println("***ReportDAOImpl start getVoteUsers");
		Set<User> allUsers = new HashSet<User>();
		
		PackageSuite ps = (PackageSuite) load(PackageSuite.class, suiteId);
		Set<PackageVoteSuite> allpvs = ps.getVoteSuites();
		
		Iterator it = allpvs.iterator();
		while(it.hasNext()) {
			PackageVoteSuite pvs = (PackageVoteSuite) it.next();
			if(finalvote) {
				if(pvs.isFinalVote()) {
					Map<ClusteredPackage, PackageUserVote> puvMap = pvs.getUserVotes();
					Set<ClusteredPackage> keys = puvMap.keySet();
					for(ClusteredPackage cp : keys) {
						PackageUserVote puv = puvMap.get(cp);
						Map<User, Integer> votes = puv.getVotes();
						allUsers.addAll(votes.keySet());
						System.out.println("***ReportDAOImpl getVoteUsers, Users found " + votes.keySet().size());
					}	
				}
			} else {
				Map<ClusteredPackage, PackageUserVote> puvMap = pvs.getUserVotes();
				Set<ClusteredPackage> keys = puvMap.keySet();
				for(ClusteredPackage cp : keys) {
					PackageUserVote puv = puvMap.get(cp);
					Map<User, Integer> votes = puv.getVotes();
					allUsers.addAll(votes.keySet());
					System.out.println("***ReportDAOImpl getVoteUsers, Users found " + votes.keySet().size());
				}
			}
				
		}
		System.out.println("***ReportDAOImpl end getVoteUsers, All Users found " + allUsers.size());
		return allUsers;
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
	
	
	private static final String hql_getNumUsers = "from User u where u.deleted=? and u.enabled=? and u.roles.size < 2";

	public int getNumUsers() throws Exception {
		List userlist = getHibernateTemplate().find(hql_getNumUsers, new Object[] {new Boolean(false), new Boolean(true)});	
		
		return userlist.size();
	}
	
	
	private static final String hql_getPrimaryTransport1 = "from RegisterObject ro where ro.value=?"; 
	
	public RegisterObject getPrimaryTransport(User u) throws Exception {		
		Map<String, Integer> trans = new HashMap<String, Integer>();
		trans.put("Walk or Bike", u.getBikeDays()+u.getWalkDays());
		trans.put("Drive Alone", u.getDriveDays());
		trans.put("Bus or Transit", u.getBusDays());
		trans.put("Carpool or Vanpool", u.getCarpoolDays());
		
		String highTrans = "Undefined";
		int highValue = 0;
		
		Iterator it = trans.keySet().iterator();
		while(it.hasNext()) {
			String key = (String)it.next();
			int temp = trans.get(key);
			if(temp > highValue) {
				highValue = temp;
				highTrans = key;
			}
		}
		
		List list = getHibernateTemplate().find(hql_getPrimaryTransport1, new Object[] {highTrans,});	
		if(list.size()<1) {
			System.out.println("ReportDAOImpl, getPrimaryTransport, no transport found");
			return null;
		}
		RegisterObject ro = (RegisterObject) list.get(0);
		return ro;
	}
	
	
	private static final String hql_getDiscussionUsers1 = "from InfoStructure info where info.type=?"; 
	private static final String hql_getDiscussionUsers2 = "from DiscussionPost dp where dp.discussion=?"; 
	private static final String hql_getDiscussionUsers3 = "from DiscussionReply dr where dr.parent=?"; 
	
	public Set<User> getDiscussionUsers(String type) throws Exception {
		System.out.println("--***Start getDiscussionUsers");
		Set<Discussion> allDisc = new HashSet<Discussion>();
		Set<User> allUsers = new HashSet<User>();
		Set<Long> allIds = new HashSet<Long>();
		List list = getHibernateTemplate().find(hql_getDiscussionUsers1, new Object[] {type,});
		if(list.size()<1) {
			//System.out.println("ReportDAOImpl, getInfoObjects, no InfoStructure found");
			return allUsers;
		}
		//System.out.println("--**list size" + list.size());
		InfoStructure infoStructure = (InfoStructure) list.get(0);
		allDisc.add(infoStructure.getDiscussion());
		Set infoObjects = infoStructure.getInfoObjects();
		Iterator ioIt = infoObjects.iterator();
		while(ioIt.hasNext()) {
			InfoObject io = (InfoObject) ioIt.next();
			allDisc.add(io.getDiscussion());
		}
		
		
		Iterator idIt = allDisc.iterator();
		while(idIt.hasNext()) {
			Discussion discussion = (Discussion)idIt.next();
			//System.out.println("--**discussion id" + discussion.getId());
			List discussionList = getHibernateTemplate().find(hql_getDiscussionUsers2, new Object[] {discussion,});
			//System.out.println("--**discussion list size" + discussionList.size());
			
			Iterator it = discussionList.iterator();
			while(it.hasNext()) {
				DiscussionPost dp = (DiscussionPost) it.next();
				allUsers.add(dp.getOwner());
				allIds.add(dp.getId());
				//System.out.println("--***User found post " + dp.getOwner().getLoginname());
				
				List replyList = getHibernateTemplate().find(hql_getDiscussionUsers3, new Object[] {dp,});
				//System.out.println("--**reply list size" + replyList.size());
				
				Iterator itR = replyList.iterator();
				while(itR.hasNext()) {
					DiscussionReply dr = (DiscussionReply) itR.next();		
					allUsers.add(dr.getOwner());
					allIds.add(dr.getId());
					//System.out.println("--***User found reply " + dr.getOwner().getLoginname());

				}
			}
		}
		
		System.out.println("--***DiscussionUsers found " + allUsers.size());
		
		//Get yes no voting users on these discussions
		allUsers.addAll(this.getYesNoVotingUsersById(allIds));
		
		System.out.println("--***End getDiscussionUsers, discussion yes no users added, total size " + allUsers.size());

		return allUsers;
	}
	
	
	
	private static final String hql_getYesNoVotingUsers2 = "from Concern c where c.cct.id=?"; 
	private static final String hql_getYesNoVotingUsers3 = "from Comment c where c.concern=?"; 
	private static final String hql_getYesNoVotingUsers4 = "from InfoStructure infoS where infoS.cctId=?"; 
	
	public Set<User> getYesNoVotingUsers(Long cctId, Long packSuiteId, Long critSuiteId, Long projSuiteId, Long fundSuiteId) throws Exception {
		Set<User> allUsers = new HashSet<User>();
		Set<Long> allIds = new HashSet<Long>();
		
		
		//Get all Concerns & comments
		System.out.println("***YesNoVoting Start Concerns and comments");
		List listConcerns = getHibernateTemplate().find(hql_getYesNoVotingUsers2, new Object[] {cctId,});
		Iterator itConcerns = listConcerns.iterator();
		while(itConcerns.hasNext()) {
			Concern concern = (Concern) itConcerns.next();
			allIds.add(concern.getId());
			List listComments = getHibernateTemplate().find(hql_getYesNoVotingUsers3, new Object[] {concern,});
			Iterator itComments = listComments.iterator();
			while(itComments.hasNext()) {
				Comment comments = (Comment) itComments.next();
				allIds.add(comments.getId());
			}
		}
		System.out.println("***YesNoVoting End Concerns and comments, Ids List size " + allIds.size());
		
		//Get InfoStructure 
		System.out.println("***YesNoVoting Start InfoStructure");
		List listInfoS = getHibernateTemplate().find(hql_getYesNoVotingUsers4, new Object[] {cctId,});
		Iterator itInfoS = listInfoS.iterator();
		while(itInfoS.hasNext()) {
			InfoStructure infoS = (InfoStructure) itInfoS.next();
			allIds.add(infoS.getId());
		}
		System.out.println("***YesNoVoting End InfoStructure, Ids List size " + allIds.size());
		
		allUsers.addAll(this.getYesNoVotingUsersById(allIds));
		
		System.out.println("--**YesNoVoting users size" + allUsers.size());
		return allUsers;
	}
	
	
	private static final String hql_getYesNoVotingUsersById1 = "from YesNoVoting ynv where ynv.targetId=?";
	
	public Set<User> getYesNoVotingUsersById(Set<Long> allIds) throws Exception {
		Set<User> allUsers = new HashSet<User>();
		
		Iterator itIds = allIds.iterator();
		while(itIds.hasNext()) {
			Long targetId = (Long) itIds.next();
			List list = getHibernateTemplate().find(hql_getYesNoVotingUsersById1, new Object[] {targetId,});
			
			Iterator it = list.iterator();
			while(it.hasNext()) {
				YesNoVoting ynv = (YesNoVoting) it.next();
				allUsers.add(ynv.getOwner());
			}
		}
		
		return allUsers;
	}
	
	
	public Set<User> getPackageUsers(Long pkgSuiteId) throws Exception {
		Set<User> allUsers = new HashSet<User>();
		PackageSuite pkgSuite = (PackageSuite) load(PackageSuite.class, pkgSuiteId);
		Set userPackages = pkgSuite.getUserPkgs();
		Iterator it = userPackages.iterator();
		
		while(it.hasNext()) {
			UserPackage up = (UserPackage) it.next();
			allUsers.add(up.getAuthor());
		}
		System.out.println("--**User Packages users size" + allUsers.size());
		return allUsers;
	}
	
}
