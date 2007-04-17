package org.pgist.packages;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.directwebremoting.WebContextFactory;


/**
 * DWR AJAX Agent class for packages.
 * 
 * @author kenny
 *
 */
public class PackageAgent {
    
    
    private PackageService packageService;
    
    
    public void setPackageService(PackageService packageService) {
        this.packageService = packageService;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
    /**
     * Using the users preferences this algorithm generates a user package
     * 
     * @param params a Map contains:<br>
     *   <ul>
     *     <li>usrPkgId - int, id of the UserPackage object</li>
     *     <li>projSuiteId - int, id of the project suite</li>
     *     <li>fundSuiteId - int, id of the funding suite</li>
     *   </ul>
     *   
     * @return a Map contains:<br>
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *     <li>config - The tuner config object preloaded with values related to the existing project</li>
     *   </ul>
     */
    public Map getTunerConfig(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            Long usrPkgId = new Long((String) params.get("usrPkgId"));
            Long projSuiteId = new Long((String) params.get("projSuiteId"));
            Long fundSuiteId = new Long((String) params.get("fundSuiteId"));
            
            TunerConfig config = new TunerConfig();
            config.setFundSuiteId(fundSuiteId);
            config.setProjSuiteId(projSuiteId);

            //TODO return the turner package config
            //TODO default this to maybe's and must haves
            
            map.put("config", config);
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;      
    }
    
    /**
     * Using the users preferences this algorithm generates a user package
     * 
     * @param conf	The configuration for figuring out this users package
     * @param limit	The personal limit of this user<
     *   
     * @return a Map contains:<br>
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
    public Map createMyConfiguredPackage(TunerConfig conf, float limit) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            
System.out.println("MATT: Creating the configured package with a limit of " + limit);  
			//TODO get the criteria info
            //TODO package creation algorithm, if an alternative isn't in the map then its a maybe
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;    	
    }
    
    /**
     * Using the users preferences this algorithm generates a user package
     * 
     * @param params a Map contains:<br>
     *   <ul>
     *     <li>usrPkgId - int, id of the UserPackage object</li>
     *     <li>limit - float, The personal limit of this user</li>
     *   </ul>
     *   
     * @return a Map contains:<br>
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *     <li>package - a UserPackage object</li>
     *   </ul>
     */
    public Map createMyPackage(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            Long usrPkgId = new Long((String) params.get("usrPkgId"));
            float limit = new Float((String) params.get("limit"));
            
System.out.println("MATT: Creating my package with a limit of " + limit);            
            //TODO package creation algorithm
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;    	
    }    
    
    
    /**
     * Fires off the algorithm to create a limited set of clustered packages from the existing
     * user packages
     * 
     * @param params a Map contains:<br>
     *   <ul>
     *     <li>pkgSuiteId - int, id of the package suite these packages are in</li>
     *     <li>pkgCount - int, The number of packages to reduce it to, limited to between 3 and 7</li>
     *   </ul>
     *   
     * @return a Map contains:<br>
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
    public Map createClusteredPackages(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            Long pkgSuiteId = new Long((String) params.get("pkgSuiteId"));
            int pkgCount = new Integer((String) params.get("pkgCount"));
            //TODO clustering algorithm
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//createClusteredPackages()    

    /**
     * Returns all of the clustered packages contained in the provided package suite
     * 
     * @param params a Map contains:<br>
     *   <ul>
     *     <li>pkgSuiteId - int, id of the package suite these packages are in</li>
     *   </ul>
     *   
     * @return a Map contains:<br>
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *     <li>
     *       html - string, html source segment generated by "/WEB-INF/jsp/packages/createPackage_summary.jsp". In this page the following variables are avaiable:<br>
     *         <ul>
     *           <li>packages - a Set of clustered package objects</li>
     *         </ul>
     *     </li>
     *   </ul>
     */
    public Map getClusteredPackages(HttpServletRequest request, Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            Long pkgSuiteId = new Long((String) params.get("pkgSuiteId"));
            
            PackageSuite pSuite = this.packageService.getPackageSuite(pkgSuiteId);
            
            Set packages = new HashSet();
            ClusteredPackage cp = new ClusteredPackage();
            cp.setCreateDate(new Date());
            cp.setTotalCost(1200f);
            cp.setTotalCostForAvgResident(40f);
            
            ClusteredPackage cp2 = new ClusteredPackage();
            cp2.setCreateDate(new Date());
            cp2.setTotalCost(121f);
            cp2.setTotalCostForAvgResident(42f);
            
            ClusteredPackage cp3 = new ClusteredPackage();
            cp3.setCreateDate(new Date());
            cp3.setTotalCost(99f);
            cp3.setTotalCostForAvgResident(19.95f);
            
            packages.add(cp);
            packages.add(cp2);
            packages.add(cp3);
            
            request.setAttribute("packages", packages);

            //request.setAttribute("packages", pSuite.getClusteredPkgs());
            map.put("html", WebContextFactory.get().forwardToString("/WEB-INF/jsp/packages/packageMgr_packages.jsp"));            
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//getClusteredPackages()    
    
    
    /**
     * Set (Add/Delete) a project from a user package for the current participant
     * 
     * @param params a Map contains:<br>
     *   <ul>
     *     <li>pkgId - int, id of the UserPackage object</li>
     *     <li>altId - int, id of the ProjectAlternative object</li>
     *     <li>deleting - boolean, true | false. If deleting==true, delete the project alternative
     *         from package, else add the project alternative to package, default is "false".
     *     </li>
     *   </ul>
     *   
     * @return a Map contains:<br>
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *     <li>
     *       html - string, html source segment generated by "/WEB-INF/jsp/packages/createPackage_summary.jsp". In this page the following variables are avaiable:<br>
     *         <ul>
     *           <li>package - a UserPackage object</li>
     *         </ul>
     *     </li>
     *   </ul>
     */
    public Map setProjectToUserPkg(HttpServletRequest request, Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            Long pkgId = new Long((String) params.get("pkgId"));
            Long altId = new Long((String) params.get("altId"));
            boolean deleting = "true".equals((String) params.get("deleting"));            
            UserPackage userPkg;
            if(deleting) {
            	userPkg = this.packageService.deleteProjectAlternative(pkgId, altId);
            } else {
            	userPkg = this.packageService.addProjectAlternative(pkgId, altId);
            }
                        
			
            request.setAttribute("package", userPkg);
            map.put("html", WebContextFactory.get().forwardToString("/WEB-INF/jsp/packages/createPackage_summary.jsp"));            
            
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//setProjectToUserPkg()
    
    /**
     * Returns the user summary and the html snippet
     * 
     * 
     * @param params a Map contains:<br>
     *   <ul>
     *     <li>pkgId - int, id of the UserPackage object</li>
     *   </ul>
     *   
     * @return a Map contains:<br>
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *     <li>
     *       html - string, html source segment generated by "/WEB-INF/jsp/packages/createPackage_summary.jsp". In this page the following variables are avaiable:<br>
     *         <ul>
     *           <li>package - a UserPackage object</li>
     *         </ul>
     *     </li>
     *   </ul>
     */
    public Map getSummary(HttpServletRequest request, Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            Long pkgId = new Long((String) params.get("pkgId"));
            UserPackage userPkg = this.packageService.getUserPackage(pkgId);
            request.setAttribute("package", userPkg);
            map.put("html", WebContextFactory.get().forwardToString("/WEB-INF/jsp/packages/createPackage_summary.jsp"));            
            map.put("successful", true);
            
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//getSummary()
    
    /**
     * Set (Add/Delete) a funding source from a user package for the current participant
     * 
     * @param params a Map contains:<br>
     *   <ul>
     *     <li>pkgId - int, id of the UserPackage object</li>
     *     <li>altId - int, id of the FundingAlternative object</li>
     *     <li>deleting - boolean, true | false. If deleting==true, delete the funding source alternative
     *         from package, else add the funding source alternative to package
     *     </li>
     *   </ul>
     *   
     * @return a Map contains:<br>
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *     <li>
     *       html - string, html source segment generated by "/WEB-INF/jsp/packages/createPackage_summary.jsp". In this page the following variables are avaiable:<br>
     *         <ul>
     *           <li>package - a UserPackage object</li>
     *         </ul>
     *     </li>
     *   </ul>
     */
    public Map setFundingToUserPkg(HttpServletRequest request, Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            Long pkgId = new Long((String) params.get("pkgId"));
            Long altId = new Long((String) params.get("altId"));
            boolean deleting = "true".equals((String) params.get("deleting"));      
System.out.println("MATT: setFundingToUserPkg: UsrPkg = " + pkgId + " altId = " + altId + " deleting = " + deleting + " d2 " + params.get("deleting"));            
            UserPackage userPkg;
            if(deleting) {
            	userPkg = this.packageService.deleteFundingAlternative(pkgId, altId);
            } else {
            	userPkg = this.packageService.addFundingAlternative(pkgId, altId);
            }
System.out.println("MATT: 2");
            request.setAttribute("package", userPkg);
            map.put("html", WebContextFactory.get().forwardToString("/WEB-INF/jsp/packages/createPackage_summary.jsp"));            
            map.put("successful", true);
System.out.println("MATT: 3");                        
            
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//setFundingToUserPkg()
    
    
    /**
     * Set (Add/Delete) a project from a clustered package for the current participant
     * 
     * @param params a Map contains:<br>
     *   <ul>
     *     <li>pkgId - int, id of the ClusteredPackage object</li>
     *     <li>altId - int, id of the ProjectAlternative object</li>
     *     <li>deleting - boolean, true | false. If deleting==true, delete the project alternative
     *         from package, else add the project alternative to package
     *     </li>
     *   </ul>
     *   
     * @return a Map contains:<br>
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *     <li>
     *       html - string, html source segment generated by "/WEB-INF/jsp/packages/createPackage_clusteredSummary.jsp". In this page the following variables are avaiable:<br>
     *         <ul>
     *           <li>package - a ClusteredPackage object</li>
     *           <li>stat - a PackageStat object</li>
     *         </ul>
     *     </li>
     *   </ul>
     */
    public Map setProjectToClusteredPkg(HttpServletRequest request, Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            /*
             * TODO: persist the association
             */
            
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//setProjectToClusteredPkg()
    
    
    /**
     * Set (Add/Delete) a funding source from a clustered package for the current participant
     * 
     * @param params a Map contains:<br>
     *   <ul>
     *     <li>pkgId - int, id of the ClusteredPackage object</li>
     *     <li>altId - int, id of the FundingAlternative object</li>
     *     <li>deleting - boolean, true | false. If deleting==true, delete the funding source alternative
     *         from package, else add the funding source alternative to package
     *     </li>
     *   </ul>
     *   
     * @return a Map contains:<br>
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *     <li>
     *       html - string, html source segment generated by "/WEB-INF/jsp/packages/createPackage_clusteredSummary.jsp". In this page the following variables are avaiable:<br>
     *         <ul>
     *           <li>package - a ClusteredPackage object</li>
     *           <li>stat - a PackageStat object</li>
     *         </ul>
     *     </li>
     *   </ul>
     */
    public Map setFundingToClusteredPkg(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            /*
             * TODO: persist the association
             */
            
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//setFundingToClusteredPkg()
    
    
    /**
     * Set the voting selection for the current user.
     * 
     * @param params A map contains:
     *     <ul>
     *       <li>suiteId - int, id for a PackageVoteSuite object</li>
     *       <li>vote - int, vote value, [0 | 1 | 2 | 3]
     *         <ul>
     *           <li>0 - unknown</li> 
     *           <li>1 - High</li> 
     *           <li>2 - Medium</li> 
     *           <li>3 - Low</li> 
     *         </ul> 
     *       </li>
     *     </ul>
     * 
     * @return A map contains:
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
    public Map setVoting(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            /*
             * TODO: persist the voting selection
             */
            
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//setVoting()
    
    
    /**
     * Create a new clustered package in a specified PackageSuite.
     * 
     * @param params A map contains:
     *     <ul>
     *       <li>suiteId - int, id for a PackageSuite object</li>
     *       <li>description - string</li>
     *     </ul>
     *     
     * @returnA A map contains:
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *     <li>pkgId - int, the newly created package id (valid when successful==false)</li>
     *   </ul>
     */
    public Map createClusteredPackage(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            /*
             * TODO: create a new ClusteredPackage
             */
            
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//createClusteredPackage()
    
    
    /**
     * Delete a specified clustered package from a PackageSuite.
     * 
     * @param params A map contains:
     *     <ul>
     *       <li>suiteId - int, id for a PackageSuite object</li>
     *       <li>pkgId - int, id for a ClusteredPackage object</li>
     *     </ul>
     *     
     * @returnA A map contains:
     *     <ul>
     *       <li></li>
     *     </ul>
     */
    public Map deleteClusteredPackage(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            /*
             * TODO: delete the specified package
             */
            
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//deleteClusteredPackage()
    
    
}//class PackageAgent
