package org.pgist.packages;

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
	
	UserPackage createUserPackage(Long pkgSuiteId, UserInfo info) throws Exception;

    PackageSuite createPackageSuite() throws Exception;

	UserPackage getUserPackage(Long pkgId) throws Exception;

	void createUserPackage(TunerConfig conf, float mylimit, float avglimit) throws Exception;

	void createClusteredPackages(Long pkgSuiteId, int pkgCount) throws Exception;
    
    InfoStructure publish(Long cctId, Long suiteId) throws Exception;
    
    
}//interface PackageService
