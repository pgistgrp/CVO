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
		
		this.packageDAO.save(uPack);

		return uPack;
	}

	public UserPackage removeProjectAlternative(Long usrPkgId, Long altId) throws Exception {
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

		return uPack;
	}

	public UserPackage removeFundingAlternative(Long usrPkgId, Long funAltRefId) throws Exception {
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
				this.fundingDAO.save(tempRef);
				return uPack;
			}
		}
		
		return uPack;
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
System.out.println("I'm user " + info.getId() + " with suite " + packageSuite);		
		//Loop through looking for the package
		Iterator<UserPackage> pkgs = suite.getUserPkgs().iterator();
		UserPackage tempPackage;
		while(pkgs.hasNext()) {
			tempPackage = pkgs.next();
			if(tempPackage.getAuthor().getId().equals(info.getId())) {
System.out.println("MATT: Found it");				
				return tempPackage;
			}
		}
System.out.println("MATT: NOT IN THERE");		
		//None found, then create and save		
		UserPackage uPack = new UserPackage();		
		User user = this.packageDAO.getUserById(info.getId());
System.out.println("MATT: got the user package");
		uPack.setAuthor(user);
		packageDAO.save(uPack);
System.out.println("MATT: SAVED THE PACKAGE");		
		suite.getUserPkgs().add(uPack);
		packageDAO.save(suite);
System.out.println("MATT: Creating it");		
		return uPack;
	}
	
	
	
}//class PackageServiceImpl
