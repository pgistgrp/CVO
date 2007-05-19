package org.pgist.tests.packages.cluster;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;
import org.pgist.funding.FundingDAO;
import org.pgist.funding.FundingSource;
import org.pgist.funding.FundingSourceAltRef;
import org.pgist.funding.FundingSourceAlternative;
import org.pgist.funding.FundingSourceRef;
import org.pgist.funding.FundingSourceSuite;
import org.pgist.packages.ClusteredPackage;
import org.pgist.packages.PackageDAO;
import org.pgist.packages.PackageServiceImpl;
import org.pgist.packages.PackageSuite;
import org.pgist.packages.UserPackage;
import org.pgist.projects.Project;
import org.pgist.projects.ProjectAltRef;
import org.pgist.projects.ProjectAlternative;
import org.pgist.projects.ProjectRef;
import org.pgist.projects.ProjectSuite;
import org.pgist.tests.funding.MockFundingSourceDAO;
import org.pgist.tests.projects.MockProjectDAO;

/**
 * Used to test the KSEngine
 * 
 */
public class TestClusteringPackages {

	@Before
	public void setUp() {
	}
	
	ProjectAltRef pAltRef1;
	ProjectAltRef pAltRef2;
	ProjectAltRef pAltRef3;
	ProjectAltRef pAltRef4;
	FundingSourceAltRef fAltRef1;
	FundingSourceAltRef fAltRef2;
	FundingSourceAltRef fAltRef3;
	FundingSourceAltRef fAltRef4;

	/**
	 * Test that the clustering works
	 * @throws Exception 
	 */
	@Test
	public void testClustering() throws Exception {
		PackageServiceImpl pService = new PackageServiceImpl();
		
		//Create the project suite
		Project project1 = new Project();
		project1.setName("project1");
		ProjectRef pRef1 = new ProjectRef();
		pRef1.setProject(project1);
		
		ProjectAlternative pAlt1 = new ProjectAlternative();
		pAlt1.setName("pa1");
		pAlt1.setId(1l);
		pAltRef1 = new ProjectAltRef();
		pAltRef1.setAlternative(pAlt1);
		
		project1.getAlternatives().add(pAlt1);
		pRef1.getAltRefs().add(pAltRef1);
		
		ProjectAlternative pAlt2 = new ProjectAlternative();
		pAlt2.setId(2l);
		pAlt2.setName("pa2");
		pAltRef2 = new ProjectAltRef();
		pAltRef2.setAlternative(pAlt2);

		project1.getAlternatives().add(pAlt2);
		pRef1.getAltRefs().add(pAltRef2);

		//Next Project
		Project project2 = new Project();
		project2.setName("project2");
		ProjectRef pRef2 = new ProjectRef();
		pRef2.setProject(project2);
		ProjectAlternative pAlt3 = new ProjectAlternative();
		pAlt3.setId(3l);
		pAlt3.setName("pa3");
		pAltRef3 = new ProjectAltRef();
		pAltRef3.setAlternative(pAlt3);

		project2.getAlternatives().add(pAlt3);
		pRef2.getAltRefs().add(pAltRef3);
		
		ProjectAlternative pAlt4 = new ProjectAlternative();
		pAlt4.setId(4l);
		pAlt4.setName("pa4");
		pAltRef4 = new ProjectAltRef();
		pAltRef4.setAlternative(pAlt4);
		
		project2.getAlternatives().add(pAlt4);
		pRef2.getAltRefs().add(pAltRef4);
						
		ProjectSuite pSuite = new ProjectSuite();
		pSuite.getReferences().add(pRef1);
		pSuite.getReferences().add(pRef2);
				
		//Create the funding suite
		FundingSource funding1 = new FundingSource();
		funding1.setName("funding1");
		FundingSourceRef fRef1 = new FundingSourceRef();
		fRef1.setSource(funding1);
		
		FundingSourceAlternative fAlt1 = new FundingSourceAlternative();
		fAlt1.setId(5l);
		fAlt1.setName("fa1");
		fAltRef1 = new FundingSourceAltRef();
		fAltRef1.setAlternative(fAlt1);
		
		funding1.getAlternatives().add(fAlt1);
		fRef1.getAltRefs().add(fAltRef1);
		
		FundingSourceAlternative fAlt2 = new FundingSourceAlternative();
		fAlt2.setId(6l);
		fAlt2.setName("fa2");
		fAltRef2 = new FundingSourceAltRef();
		fAltRef2.setAlternative(fAlt2);

		funding1.getAlternatives().add(fAlt2);
		fRef1.getAltRefs().add(fAltRef2);

		//Next FundingSource
		FundingSource funding2 = new FundingSource();
		funding2.setName("funding2");
		FundingSourceRef fRef2 = new FundingSourceRef();
		fRef2.setSource(funding2);
		FundingSourceAlternative fAlt3 = new FundingSourceAlternative();
		fAlt3.setId(7l);
		fAlt3.setName("fa3");
		fAltRef3 = new FundingSourceAltRef();
		fAltRef3.setAlternative(fAlt3);

		funding2.getAlternatives().add(fAlt3);
		fRef2.getAltRefs().add(fAltRef3);
		
		FundingSourceAlternative fAlt4 = new FundingSourceAlternative();
		fAlt4.setId(8l);
		fAlt4.setName("fa4");
		fAltRef4 = new FundingSourceAltRef();
		fAltRef4.setAlternative(fAlt4);
		
		funding2.getAlternatives().add(fAlt4);
		fRef2.getAltRefs().add(fAltRef4);
						
		FundingSourceSuite fSuite = new FundingSourceSuite();
		fSuite.getReferences().add(fRef1);
		fSuite.getReferences().add(fRef2);
		
		//Make the package suite
		MockPackageDAO pkgDAO = new MockPackageDAO();
		PackageSuite packSuite = new PackageSuite();
		packSuite.getUserPkgs().add(createUserPackage(9l, 1, 0, 1, 0, 0, 0, 0, 1, pkgDAO));
		packSuite.getUserPkgs().add(createUserPackage(10l, 1, 0, 0, 0, 0, 1, 0, 1, pkgDAO));
		packSuite.getUserPkgs().add(createUserPackage(11l, 0, 1, 1, 0, 1, 0, 0, 1, pkgDAO));
		packSuite.getUserPkgs().add(createUserPackage(12l, 0, 0, 1, 0, 0, 0, 0, 1, pkgDAO));
		packSuite.getUserPkgs().add(createUserPackage(13l, 0, 1, 1, 0, 0, 0, 0, 1, pkgDAO));
		packSuite.getUserPkgs().add(createUserPackage(14l, 0, 1, 1, 0, 0, 0, 0, 1, pkgDAO));
		packSuite.getUserPkgs().add(createUserPackage(15l, 0, 0, 1, 0, 0, 0, 0, 1, pkgDAO));
		packSuite.getUserPkgs().add(createUserPackage(16l, 0, 0, 1, 0, 0, 0, 0, 1, pkgDAO));
						
		//Create the fake DAO's and load them into the package suite
		MockFundingSourceDAO funDAO = new MockFundingSourceDAO();
		funDAO.setFundingSuite(fSuite);
		MockProjectDAO projDAO = new MockProjectDAO();
		projDAO.setProjectSuite(pSuite);

		pkgDAO.setPackageSuite(packSuite);
		
		pService.setPackageDAO(pkgDAO);
		pService.setProjectDAO(projDAO);
		pService.setFundingDAO(funDAO);
		
		pService.createClusteredPackages(200l, 4, 200l, 200l);
		
		//Share the wonderful results
		PackageSuite pSuite2 = pkgDAO.getSavePkgSuite();
		Iterator iCPkgs = pSuite2.getClusteredPkgs().iterator();
		ClusteredPackage tempCPackage;
		Iterator iUPkgs;
		UserPackage tempUPackage;
		while(iCPkgs.hasNext()) {
			tempCPackage = (ClusteredPackage)iCPkgs.next();
			iUPkgs = tempCPackage.getUserPkgs().iterator();
			System.out.println("Next Cluster...");
			while(iUPkgs.hasNext()) {
				tempUPackage = (UserPackage)iUPkgs.next();
				System.out.println("Found UPackage " + tempUPackage.getId());
			}
			System.out.println("\n\n");
		}
	}
	
	public UserPackage createUserPackage(Long id, int p1, int p2, int p3, int p4, int f1, int f2, int f3, int f4, MockPackageDAO pkgDAO) {
		UserPackage uPack = new UserPackage();
		uPack.setId(id);
		
		if(p1 == 1) {
			uPack.getProjAltRefs().add(pAltRef1);			
		}
		if(p2 == 1) {
			uPack.getProjAltRefs().add(pAltRef2);			
		}
		if(p3 == 1) {
			uPack.getProjAltRefs().add(pAltRef3);			
		}
		if(p4 == 1) {
			uPack.getProjAltRefs().add(pAltRef4);			
		}

		if(f1 == 1) {
			uPack.getFundAltRefs().add(fAltRef1);			
		}
		if(f2 == 1) {
			uPack.getFundAltRefs().add(fAltRef2);			
		}
		if(f3 == 1) {
			uPack.getFundAltRefs().add(fAltRef3);			
		}
		if(f4 == 1) {
			uPack.getFundAltRefs().add(fAltRef4);			
		}
		pkgDAO.setUserPackage(uPack);
		return uPack;
	}
}

