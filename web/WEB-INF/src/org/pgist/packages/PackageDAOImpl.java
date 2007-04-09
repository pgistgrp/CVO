package org.pgist.packages;

import org.pgist.funding.FundingSourceAltRef;
import org.pgist.system.BaseDAOImpl;


/**
 * 
 * @author kenny
 *
 */
public class PackageDAOImpl extends BaseDAOImpl implements PackageDAO {
	public UserPackage getUserPackage(Long pid) {
		return (UserPackage)getHibernateTemplate().get(UserPackage.class, pid);
	}
	public void save(UserPackage uPack) throws Exception {
		getHibernateTemplate().saveOrUpdate(uPack);				
	}
	
	public void save(PackageSuite p) throws Exception {
		getHibernateTemplate().saveOrUpdate(p);				
	}
	public Package getPackage(Long pid){
		return (Package)getHibernateTemplate().load(Package.class, pid);
	}
	
	public void save(Package p) throws Exception{
		getHibernateTemplate().saveOrUpdate(p);		
	}	
	public PackageSuite getPackageSuite(Long pid){
		return (PackageSuite)getHibernateTemplate().load(PackageSuite.class, pid);
	}	
}//class PackageDAOImpl
