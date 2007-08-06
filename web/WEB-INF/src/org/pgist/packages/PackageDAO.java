package org.pgist.packages;

import org.pgist.system.BaseDAO;

import java.util.Set;
import java.util.Collection;

/**
 * 
 * @author kenny
 *
 */
public interface PackageDAO extends BaseDAO {
	void save(PackageVoteSuite p) throws Exception;
	void save(VoteSuiteStat p) throws Exception;	
	void save(UserPackage p) throws Exception;

	UserPackage getUserPackage(Long pid) throws Exception;
	
	void save(Package p) throws Exception;

	Package getPackage(Long pid) throws Exception;
	
	void save(PackageSuite p) throws Exception;

	PackageSuite getPackageSuite(Long suiteId) throws Exception;

	ClusteredPackage getClusteredPackage(Long pkgId) throws Exception;

	PackageVoteSuite getVoteSuite(Long voteSuiteId) throws Exception;
	void delete(VoteSuiteStat stat) throws Exception;
	void delete(ClusteredPackage cp) throws Exception;
    
	Set getVoteSuiteStatsBySuite(Long pkgVoteSuiteId) throws Exception;
    
}//interface PackageDAO
