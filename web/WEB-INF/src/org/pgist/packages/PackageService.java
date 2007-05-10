package org.pgist.packages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.pgist.discussion.InfoStructure;
import org.pgist.users.User;
import org.pgist.users.UserInfo;



/**
 * 
 * @author kenny
 *
 */
public interface PackageService {
    
	User getUser(UserInfo userInfo) throws Exception;
	
	PackageSuite getPackageSuite(Long pkgId) throws Exception;

	Package deleteProjectAlternative(Long pkgId, Long altId, boolean userPkg) throws Exception;

	Package addProjectAlternative(Long pkgId, Long altId, boolean userPkg) throws Exception;

	Package deleteFundingAlternative(Long pkgId, Long funAltRefId, boolean userPkg) throws Exception;

	Package addFundingAlternative(Long pkgId, Long funAltRefId, boolean userPkg) throws Exception;	
	
	UserPackage createUserPackage(Long pkgSuiteId, UserInfo info, Long fundingSuiteId) throws Exception;

    PackageSuite createPackageSuite() throws Exception;

	UserPackage getUserPackage(Long pkgId) throws Exception;

	void createKSUserPackage(Long usrPkg, TunerConfig conf, float mylimit, float avglimit) throws Exception;

	void createClusteredPackages(Long pkgSuiteId, int pkgCount) throws Exception;
    
    InfoStructure publish(Long cctId, Long suiteId, String title) throws Exception;

	ClusteredPackage createClusteredPackage(Long suiteId, String description) throws Exception;

	Package getPackage(Long pkgId) throws Exception;

	void deleteClusteredPackage(Long suiteId, Long pkgId) throws Exception;

	ClusteredPackage getClusteredPackage(Long pkgId) throws Exception;

	List<ProjectDTO> createPackageRoadProjectDTOs(ClusteredPackage pack, long critSuiteId, long projSuiteId) throws Exception; 

	List<ProjectDTO> createPackageTransitProjectDTOs(ClusteredPackage pack, long critSuiteId, long projSuiteId) throws Exception; 

	List<FundingSourceDTO> createPackageFundingDTOs(ClusteredPackage pack, User user, long fundSuiteId) throws Exception;

	Long createVotingPackage(Long pkgId) throws Exception;

	PackageVoteSuite getPackageVoteSuite(Long voteSuiteId) throws Exception;

	void setVotes(User user, Long voteSuiteId, HashMap<Long, Integer> votes) throws Exception; 
    
    
}//interface PackageService
