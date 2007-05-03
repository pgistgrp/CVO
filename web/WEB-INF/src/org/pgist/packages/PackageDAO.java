package org.pgist.packages;

import org.pgist.system.BaseDAO;


/**
 * 
 * @author kenny
 *
 */
public interface PackageDAO extends BaseDAO {
    
	void save(UserPackage p) throws Exception;

	UserPackage getUserPackage(Long pid) throws Exception;
	
	void save(Package p) throws Exception;

	Package getPackage(Long pid) throws Exception;
	
	void save(PackageSuite p) throws Exception;

	PackageSuite getPackageSuite(Long suiteId) throws Exception;

	ClusteredPackage getClusteredPackage(Long pkgId) throws Exception;
    
    
}//interface PackageDAO
