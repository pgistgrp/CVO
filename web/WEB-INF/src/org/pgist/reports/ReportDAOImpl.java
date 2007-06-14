package org.pgist.reports;

import org.pgist.system.BaseDAOImpl;
import org.pgist.packages.PackageVoteSuite;
import org.pgist.packages.VoteSuiteStat;
import org.pgist.packages.ClusteredPackage;
import org.pgist.packages.PackageSuite;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
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
	
	
	public void createStatistics(Long workflowId, Long cctId, Long projSuiteId, Long fundSuiteId, Long critSuiteId, Long projISID, Long fundISID) throws Exception {
		
		
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
	
	
}
