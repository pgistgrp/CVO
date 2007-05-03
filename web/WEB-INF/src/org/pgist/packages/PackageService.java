package org.pgist.packages;

import org.pgist.discussion.InfoStructure;
import org.pgist.users.UserInfo;



/**
 * 
 * @author kenny
 *
 */
public interface PackageService {
    
    
	PackageSuite getPackageSuite(Long pkgId) throws Exception;

	UserPackage deleteProjectAlternative(Long pkgId, Long altId) throws Exception;

	UserPackage addProjectAlternative(Long pkgId, Long altId) throws Exception;

	UserPackage deleteFundingAlternative(Long pkgId, Long funAltRefId) throws Exception;

	UserPackage addFundingAlternative(Long pkgId, Long funAltRefId) throws Exception;	
	
	UserPackage createUserPackage(Long pkgSuiteId, UserInfo info, Long fundingSuiteId) throws Exception;

    PackageSuite createPackageSuite() throws Exception;

	UserPackage getUserPackage(Long pkgId) throws Exception;

	void createKSUserPackage(Long usrPkg, TunerConfig conf, float mylimit, float avglimit) throws Exception;

	void createClusteredPackages(Long pkgSuiteId, int pkgCount) throws Exception;
    
    InfoStructure publish(Long cctId, Long suiteId) throws Exception;

	ClusteredPackage createClusteredPackage(Long suiteId, String description) throws Exception;

	Package getPackage(Long pkgId) throws Exception;

	void deleteClusteredPackage(Long suiteId, Long pkgId) throws Exception;

	ClusteredPackage getClusteredPackage(Long pkgId) throws Exception; 
    
    
}//interface PackageService
