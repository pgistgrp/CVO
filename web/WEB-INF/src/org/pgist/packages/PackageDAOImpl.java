package org.pgist.packages;

import org.pgist.system.BaseDAOImpl;


/**
 * 
 * @author kenny
 *
 */
public class PackageDAOImpl extends BaseDAOImpl implements PackageDAO {
    
	

	/* (non-Javadoc)
	 * @see org.pgist.packages.PackageDAO#save(org.pgist.packages.PackageVoteSuite)
	 */
	public void save(PackageVoteSuite p) throws Exception {
		getHibernateTemplate().saveOrUpdate(p);		
	}


	/* (non-Javadoc)
	 * @see org.pgist.packages.PackageDAO#save(org.pgist.packages.VoteSuiteStat)
	 */
	public void save(VoteSuiteStat p) throws Exception {
		getHibernateTemplate().saveOrUpdate(p);		
	}


	public ClusteredPackage getClusteredPackage(Long pid) {
		return (ClusteredPackage)getHibernateTemplate().get(ClusteredPackage.class, pid);
	}
    
    
	public void save(ClusteredPackage cPack) throws Exception {
		getHibernateTemplate().saveOrUpdate(cPack);				
	}
	/**
	 * Deletes the stat
	 * 	
	 * @param p		The stat to delete
	 * @throws Exception
	 */
    public void delete(VoteSuiteStat a) throws Exception {
        getHibernateTemplate().delete(a);
    }//delete()		
	
    public void delete(ClusteredPackage cp) throws Exception {
        getHibernateTemplate().delete(cp);
    }//delete()	    
    
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
	
    
	public PackageSuite getPackageSuite(Long suiteId){
		return (PackageSuite)getHibernateTemplate().load(PackageSuite.class, suiteId);
    }
    
	public PackageVoteSuite getVoteSuite(Long suiteId){
		return (PackageVoteSuite)getHibernateTemplate().load(PackageVoteSuite.class, suiteId);
    }    
}//class PackageDAOImpl
