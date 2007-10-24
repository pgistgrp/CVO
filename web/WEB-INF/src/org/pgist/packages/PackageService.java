package org.pgist.packages;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.pgist.criteria.CriteriaSuite;
import org.pgist.discussion.InfoStructure;
import org.pgist.projects.ProjectAltRef;
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

	Package deleteProjectAlternative(Long pkgId, Long altId, boolean userPkg, Long fundingSuiteId) throws Exception;

	Package addProjectAlternative(Long pkgId, Long altId, boolean userPkg, Long fundingSuiteId) throws Exception;

	Package deleteFundingAlternative(Long pkgId, Long funAltRefId, boolean userPkg, Long fundingSuiteId) throws Exception;

	Package addFundingAlternative(Long pkgId, Long funAltRefId, boolean userPkg, Long fundingSuiteId) throws Exception;	
	
	UserPackage createUserPackage(Long pkgSuiteId, UserInfo info, Long fundingSuiteId) throws Exception;

    PackageSuite createPackageSuite() throws Exception;

	UserPackage getUserPackage(Long pkgId) throws Exception;

	void createKSUserPackage(Long usrPkg, TunerConfig conf, float mylimit, float avglimit) throws Exception;

	void createClusteredPackages(Long pkgSuiteId, int pkgCount, Long projSuiteId, Long fundSuiteId) throws Exception;
    
    InfoStructure publish(Long workflowId, Long cctId, Long suiteId, String title) throws Exception;

	ClusteredPackage createClusteredPackage(Long suiteId, String description) throws Exception;

	Package getPackage(Long pkgId) throws Exception;

	void deleteClusteredPackage(Long suiteId, Long pkgId) throws Exception;

	ClusteredPackage getClusteredPackage(Long pkgId) throws Exception;

	List<ProjectDTO> createPackageProjectDTOs(ClusteredPackage pack, long critSuiteId, long projSuiteId, User user, int transMode) throws Exception; 

	List<FundingSourceDTO> createPackageFundingDTOs(ClusteredPackage cPackage, long fundSuiteId) throws Exception;
	
	List<FundingSourceDTO> createPackageFundingDTOs(ClusteredPackage pack, User user, long fundSuiteId) throws Exception;

	Long createVotingPackage(Long pkgId) throws Exception;

	PackageVoteSuite getPackageVoteSuite(Long voteSuiteId) throws Exception;

	void setVotes(User user, Long voteSuiteId, HashMap<Long, Integer> votes) throws Exception;

	void setManualPkgDesc(Long pkgId, String desc) throws Exception;

    PackageVoteSuite createPackageVoteSuite(Long pkgSuiteId, boolean finalVote) throws Exception; 
    
    Set getVoteSuiteStatsBySuite(Long pkgVoteSuiteId) throws Exception;
    
    UserPackage calcUserValues(Long userPkgId, Long funSuiteId) throws Exception;
    
    void calcUserValues(Package pkg, UserInfo userInfo, Long funSuiteId) throws Exception;
    
    void calculatePreferredPackage(Long pkgSuiteId, Long voteSuiteId) throws Exception;
    
    String getYourGrade(ProjectAltRef altRef, CriteriaSuite cSuite, User user) throws Exception;
    
    String getAvgGrade(ProjectAltRef altRef, CriteriaSuite cSuite, User user) throws Exception;
    
    String getProjectGrade(ProjectAltRef altRef) throws Exception;
    
}//interface PackageService
