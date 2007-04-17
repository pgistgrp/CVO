package org.pgist.packages;

import java.util.Iterator;

import org.pgist.funding.FundingDAO;
import org.pgist.funding.FundingSourceAltRef;
import org.pgist.projects.ProjectAltRef;
import org.pgist.projects.ProjectDAO;
import org.pgist.users.User;
import org.pgist.users.UserInfo;


/**
 * 
 * @author kenny
 *
 */
public class PackageServiceImpl implements PackageService {

	
    FundingDAO fundingDAO;
    
    
    public void setFundingDAO(FundingDAO fundingDAO) {
        this.fundingDAO = fundingDAO;
    }	
    
    
    PackageDAO packageDAO;
    
    
    public void setPackageDAO(PackageDAO packageDAO) {
        this.packageDAO = packageDAO;
    }

    ProjectDAO projectDAO;
    
    
    public void setProjectDAO(ProjectDAO projectDAO) {
        this.projectDAO = projectDAO;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
	public PackageSuite getPackageSuite(Long pkgId) throws Exception {
		return packageDAO.getPackageSuite(pkgId);
	}


	/**
	 * Adds the provided alternative project to the package
	 */
	public UserPackage addProjectAlternative(Long usrPkgId, Long altId) throws Exception {
		
		//Get the user package
		UserPackage uPack = this.packageDAO.getUserPackage(usrPkgId);
		
		//get the alternative
		ProjectAltRef altRef = this.projectDAO.getProjectAlternativeReference(altId); 
		//Add the reference
		uPack.getProjAltRefs().add(altRef);
		
		uPack.updateCalculations();
		this.packageDAO.save(uPack);

		return uPack;
	}

	public UserPackage deleteProjectAlternative(Long usrPkgId, Long altId) throws Exception {
		//Get the user package
		UserPackage uPack = this.packageDAO.getUserPackage(usrPkgId);
				
		//Loop through to find the alternative with the particular ID, you do this instead
		//of using the collections routines because you would have to maintain the equals
		//method on the ProjectAltRef object, this gives you less chances for bugs as things change
		Iterator<ProjectAltRef> alts = uPack.getProjAltRefs().iterator();
		ProjectAltRef tempRef;
		
		while(alts.hasNext()) {
			tempRef = alts.next();
			if(tempRef.getId().equals(altId)) {
				uPack.getProjAltRefs().remove(tempRef);
				uPack.updateCalculations();
				this.packageDAO.save(uPack);
				return uPack;
			}
		}
		
		return uPack;
	}

	
	/**
	 * Adds the provided funding source to the package
	 */
	public UserPackage addFundingAlternative(Long usrPkgId, Long funAltRefId) throws Exception {
		//Get the user package
		UserPackage uPack = this.packageDAO.getUserPackage(usrPkgId);
		
		//get the alternative
		FundingSourceAltRef altRef = this.fundingDAO.getFundingSourceAlternativeReference(funAltRefId);

		//Add the reference
		uPack.getFundAltRefs().add(altRef);
		
		this.fundingDAO.save(altRef);
		uPack.updateCalculations();
		this.packageDAO.save(uPack);

		return uPack;
	}

	

	public UserPackage deleteFundingAlternative(Long usrPkgId, Long funAltRefId) throws Exception {
		//Get the user package
		UserPackage uPack = this.packageDAO.getUserPackage(usrPkgId);
				
		//Loop through to find the alternative with the particular ID, you do this instead
		//of using the collections routines because you would have to maintain the equals
		//method on the FundingSourceAltRef object, this gives you less chances for bugs as things change
		Iterator<FundingSourceAltRef> alts = uPack.getFundAltRefs().iterator();
		FundingSourceAltRef tempRef;
		
		while(alts.hasNext()) {
			tempRef = alts.next();
			if(tempRef.getId().equals(funAltRefId)) {
				uPack.getFundAltRefs().remove(tempRef);
				uPack.updateCalculations();
				this.packageDAO.save(uPack);
				return uPack;
			}
		}
		
		return uPack;
	}
	
	/**
	 * Returns the user package with this specific id
	 * 
	 * @param	pkgId	The ID of the package you are interested in
	 * @return	The user package
	 */
	public UserPackage getUserPackage(Long pkgId) throws Exception {
		return this.packageDAO.getUserPackage(pkgId);		
	}
	
	
	/**
	 * Checks first to see if the user package exist and if it doesn't then one is created and 
	 * returned.
	 * 
	 * @param	packageSuite	The suite to look in
	 * @param	info	The users info
	 * @return	The users package
	 */
	public UserPackage createUserPackage(Long packageSuite, UserInfo info) throws Exception {
		PackageSuite suite = this.getPackageSuite(packageSuite);		

		//Loop through looking for the package
		Iterator<UserPackage> pkgs = suite.getUserPkgs().iterator();
		UserPackage tempPackage;
		while(pkgs.hasNext()) {
			tempPackage = pkgs.next();
			if(tempPackage.getAuthor().getId().equals(info.getId())) {
				if(tempPackage.getSuite().getId().equals(suite.getId())) {
					return tempPackage;					
				}
			}
		}
		//None found, then create and save		
		UserPackage uPack = new UserPackage();		
		User user = this.packageDAO.getUserById(info.getId());
		uPack.setAuthor(user);
		uPack.setSuite(suite);
		packageDAO.save(uPack);
		suite.getUserPkgs().add(uPack);
		packageDAO.save(suite);
		return uPack;
	}


    public PackageSuite createPackageSuite() throws Exception {
        PackageSuite suite = new PackageSuite();       
        return suite;
    }//createPackageSuite()


    //------------------------ Mike Lowery section ----------------------------------
    
	/* (non-Javadoc)
	 * @see org.pgist.packages.PackageService#createClusteredPackages(java.lang.Long, int)
	 */
	public void createClusteredPackages(Long pkgSuiteId, int pkgCount) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.pgist.packages.PackageService#createUserPackage(org.pgist.packages.TunerConfig, float)
	 */
	public void createUserPackage(TunerConfig conf, float limit) throws Exception {
		// TODO Auto-generated method stub
		
	}    
	
}//class PackageServiceImpl
