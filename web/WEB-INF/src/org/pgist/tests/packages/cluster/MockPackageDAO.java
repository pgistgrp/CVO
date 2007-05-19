package org.pgist.tests.packages.cluster;

import java.util.HashMap;

import org.pgist.packages.ClusteredPackage;
import org.pgist.packages.Package;
import org.pgist.packages.PackageDAO;
import org.pgist.packages.PackageSuite;
import org.pgist.packages.PackageVoteSuite;
import org.pgist.packages.UserPackage;
import org.pgist.packages.VoteSuiteStat;
import org.pgist.users.User;

public class MockPackageDAO implements PackageDAO {

	public User getUserById(Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public Object load(Class klass, Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public void save(Object object) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void delete(VoteSuiteStat stat) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public ClusteredPackage getClusteredPackage(Long pkgId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public Package getPackage(Long pid) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public PackageSuite getPackageSuite(Long suiteId) throws Exception {
		return packSuite;
	}

	public UserPackage getUserPackage(Long pid) throws Exception {		
		return userPkgs.get(pid);
	}

	public PackageVoteSuite getVoteSuite(Long voteSuiteId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public void save(Package p) throws Exception {
		// TODO Auto-generated method stub
		
	}

	private PackageSuite p;
	public void save(PackageSuite p) throws Exception {
		this.p = p;
	}

	public void save(PackageVoteSuite p) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void save(UserPackage p) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void save(VoteSuiteStat p) throws Exception {
		// TODO Auto-generated method stub
		
	}

	PackageSuite packSuite;
	public void setPackageSuite(PackageSuite packSuite) {
		this.packSuite = packSuite;
	}	
	
	HashMap<Long, UserPackage> userPkgs = new HashMap<Long, UserPackage>();
	public void setUserPackage(UserPackage uPack) {
		userPkgs.put(uPack.getId(), uPack);
	}

	public PackageSuite getSavePkgSuite() {
		return p;
	}
}
