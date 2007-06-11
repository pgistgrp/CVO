package org.pgist.reports;

import org.pgist.system.BaseDAOImpl;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class ReportDAOImpl extends BaseDAOImpl implements ReportDAO {

	
	private static final String hql_getUserStatistics1 = "from User u where u.gender=?"; //Male Female stats
	private static final String hql_getUserStatistics2 = "from User u where u.primaryTransport=?"; //transportation stats
	
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
	
	
	
	
}
