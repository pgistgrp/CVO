package org.pgist.packages;

import org.pgist.users.UserInfo;



/**
 * 
 * @author kenny
 *
 */
public interface PackageService {

	PackageSuite getPackageSuite(Long pkgId) throws Exception;

	UserPackage removeProjectAlternative(Long pkgId, Long altId) throws Exception;

	UserPackage addProjectAlternative(Long pkgId, Long altId) throws Exception;

	UserPackage removeFundingAlternative(Long pkgId, Long funAltRefId) throws Exception;

	UserPackage addFundingAlternative(Long pkgId, Long funAltRefId) throws Exception;	
	
	UserPackage createUserPackage(Long pkgSuiteId, UserInfo info) throws Exception;
}//interface PackageService
