package org.pgist.packages;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.directwebremoting.WebContextFactory;
import org.pgist.users.User;
import org.pgist.util.WebUtils;


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
     *     <li>critSuiteId - int, id of the criteria suite</li>
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
            Long critSuiteId = new Long((String) params.get("critSuiteId"));
            
            TunerConfig config = new TunerConfig();
            config.setFundSuiteId(fundSuiteId);
            config.setProjSuiteId(projSuiteId);
            config.setCritSuiteId(critSuiteId);
                        
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
     * @param mylimit	The personal limit of this user
     * @param avgLimit	The average limit of all of the other users
     * @param userPkgId	The ID of the user package involved
     *   
     * @return a Map contains:<br>
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
    public Map createMyConfiguredPackage(TunerConfig conf, float mylimit, float avglimit, long userPkgId) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            this.packageService.createKSUserPackage(userPkgId, conf, mylimit, avglimit);
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
     *     <li>projSuiteId - int, id of the project suite</li>
     *     <li>fundSuiteId - int, id of the funding suite</li>
     *     <li>critSuiteId - int, id of the criteria suite</li>
     *     <li>mylimit - float, The personal limit of this user</li>
     *     <li>avglimit - float, The average limit of the other user</li>
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
            Long critSuiteId = new Long((String) params.get("critSuiteId"));
            Long projSuiteId = new Long((String) params.get("projSuiteId"));
            Long fundSuiteId = new Long((String) params.get("fundSuiteId"));
            float mylimit = new Float((String) params.get("mylimit"));
            float avglimit = new Float((String) params.get("avglimit"));
            
            TunerConfig config = new TunerConfig();
            config.setFundSuiteId(fundSuiteId);
            config.setProjSuiteId(projSuiteId);
            config.setCritSuiteId(critSuiteId);
            
            this.packageService.createKSUserPackage(usrPkgId, config, mylimit, avglimit);
            
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
            
            this.packageService.createClusteredPackages(pkgSuiteId, pkgCount);
            
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
     *   		 <li>projSuiteId - the id of a specified PackageSuite object</li>
     *  		 <li>fundSuiteId - the id of a specified FundingSuite object</li
     *   		 <li>critSuiteId - the id of a specified CriteriaSuite object</li>
     *         </ul>
     *     </li>
     *   </ul>
     */
    public Map getAutocreatedClusteredPackages(HttpServletRequest request, Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            Long fundSuiteId = new Long((String) params.get("fundSuiteId"));
            Long projSuiteId = new Long((String) params.get("projSuiteId"));
            Long critSuiteId = new Long((String) params.get("critSuiteId"));        	
            Long pkgSuiteId = new Long((String) params.get("pkgSuiteId"));
            
            PackageSuite pSuite = this.packageService.getPackageSuite(pkgSuiteId);

            Set packages = pSuite.getClusteredPkgs();
            Set autoPackages = new HashSet();
            
            Iterator i = packages.iterator();
            ClusteredPackage temp;
            while(i.hasNext()) {
            	temp = (ClusteredPackage)i.next();
            	if(!temp.isManual()) {
            		autoPackages.add(temp);
            	}
            }
            
            request.setAttribute("packages", autoPackages);
            request.setAttribute("pkgSuiteId", pkgSuiteId);
            request.setAttribute("projSuiteId", projSuiteId);
            request.setAttribute("fundSuiteId", fundSuiteId);
            request.setAttribute("critSuiteId", critSuiteId);
            
            //request.setAttribute("packages", pSuite.getClusteredPkgs());
            map.put("html", WebContextFactory.get().forwardToString("/WEB-INF/jsp/packages/packageMgr_packages.jsp"));            
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//getAutocreatedClusteredPackages()    

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
     *   		 <li>projSuiteId - the id of a specified PackageSuite object</li>
     *  		 <li>fundSuiteId - the id of a specified FundingSuite object</li
     *   		 <li>critSuiteId - the id of a specified CriteriaSuite object</li>
     *         </ul>
     *     </li>
     *   </ul>
     */
    public Map getClusteredPackages(HttpServletRequest request, Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            Long fundSuiteId = new Long((String) params.get("fundSuiteId"));
            Long projSuiteId = new Long((String) params.get("projSuiteId"));
            Long critSuiteId = new Long((String) params.get("critSuiteId"));        	
            Long pkgSuiteId = new Long((String) params.get("pkgSuiteId"));
            
            PackageSuite pSuite = this.packageService.getPackageSuite(pkgSuiteId);

            Set packages = pSuite.getClusteredPkgs();
            request.setAttribute("packages", packages);
            request.setAttribute("pkgSuiteId", pkgSuiteId);
            request.setAttribute("projSuiteId", projSuiteId);
            request.setAttribute("fundSuiteId", fundSuiteId);
            request.setAttribute("critSuiteId", critSuiteId);
            
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
     * Returns all of the manually created packages contained in the provided package suite
     * 
     * @param params a Map contains:<br>
     *   <ul>
     *     	<li>pkgSuiteId - int, id of the package suite these packages are in</li>
     *   	<li>projSuiteId - the id of a specified PackageSuite object</li>
     *   <li>fundSuiteId - the id of a specified FundingSuite object</li
     *   <li>critSuiteId - the id of a specified CriteriaSuite object</li>
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
    public Map getManualPackages(HttpServletRequest request, Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            Long pkgSuiteId = new Long((String) params.get("pkgSuiteId"));
            Long fundSuiteId = new Long((String) params.get("fundSuiteId"));
            Long projSuiteId = new Long((String) params.get("projSuiteId"));
            Long critSuiteId = new Long((String) params.get("critSuiteId"));
            
            PackageSuite pSuite = this.packageService.getPackageSuite(pkgSuiteId);
            
            Set packages = new HashSet();
            
            if(pSuite.getClusteredPkgs() != null) {
                Iterator i = pSuite.getClusteredPkgs().iterator();
                ClusteredPackage cp;
                while(i.hasNext()) {
                	cp = (ClusteredPackage)i.next();
                	if(cp.isManual()) {
                		packages.add(cp);
                	}
                }            	
            }

            request.setAttribute("packages", packages);
            request.setAttribute("pkgSuiteId", pkgSuiteId);
            request.setAttribute("projSuiteId", projSuiteId);
            request.setAttribute("fundSuiteId", fundSuiteId);
            request.setAttribute("critSuiteId", critSuiteId);
            
            map.put("html", WebContextFactory.get().forwardToString("/WEB-INF/jsp/packages/packageMgr_manualPackages.jsp"));            
            map.put("successful", true);
            
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//getManualPackages()    
            
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
     * Returns the clustered summary and the html snippet
     * 
     * 
     * @param params a Map contains:<br>
     *   <ul>
     *     <li>pkgId - int, id of the ClusteredPackage object</li>
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
    public Map getClusteredSummary(HttpServletRequest request, Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            Long pkgId = new Long((String) params.get("pkgId"));
            ClusteredPackage clusteredPkg = this.packageService.getClusteredPackage(pkgId);
            request.setAttribute("package", clusteredPkg);
            map.put("html", WebContextFactory.get().forwardToString("/WEB-INF/jsp/packages/createPackage_clusteredSummary.jsp"));            
            map.put("successful", true);
            
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//getClusteredSummary()    
    
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
     *     <li>userPkg - boolean, true | false. If userPkg==true, then perform this action on a user package
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
    public Map setProjectToPkg(HttpServletRequest request, Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            Long pkgId = new Long((String) params.get("pkgId"));
            Long altId = new Long((String) params.get("altId"));
            boolean deleting = "true".equals((String) params.get("deleting"));            
            boolean userPkg = "true".equals((String) params.get("userPkg"));            
            Package pkg;
            if(deleting) {
            	pkg = this.packageService.deleteProjectAlternative(pkgId, altId, userPkg);
            } else {
            	pkg = this.packageService.addProjectAlternative(pkgId, altId, userPkg);
            }
                        
			
            request.setAttribute("package", pkg);
            map.put("html", WebContextFactory.get().forwardToString("/WEB-INF/jsp/packages/createPackage_summary.jsp"));            
            
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//setProjectToPkg()
    
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
     *     <li>userPkg - boolean, true | false. If userPkg==true, then perform this action on a user package
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
    public Map setFundingToPkg(HttpServletRequest request, Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            Long pkgId = new Long((String) params.get("pkgId"));
            Long altId = new Long((String) params.get("altId"));
            boolean deleting = "true".equals((String) params.get("deleting"));      
            boolean userPkg = "true".equals((String) params.get("userPkg"));            
            Package pkg;
            if(deleting) {
            	pkg = this.packageService.deleteFundingAlternative(pkgId, altId, userPkg);
            } else {
            	pkg = this.packageService.addFundingAlternative(pkgId, altId, userPkg);
            }
            request.setAttribute("package", pkg);
            map.put("html", WebContextFactory.get().forwardToString("/WEB-INF/jsp/packages/createPackage_summary.jsp"));            
            map.put("successful", true);
            
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//setFundingToPkg()
       
    /**
     * Publishes the voting suite.
     * 
     * @param params A map contains:
     *     <ul>
     *       <li>pkgSuiteId - int, id for a PackageVoteSuite object</li>
     *     </ul>
     * 
     * @return A map contains:
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *     <li>voteSuiteId - the ID for the voting suite </li>
     *   </ul>
     */
    public Map publishPackages(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            Long pkgSuiteId = new Long((String) params.get("pkgSuiteId"));
        	
        	map.put("voteSuiteId", this.packageService.createVotingPackage(pkgSuiteId));
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//publishPackages()
   
    
    /**
     * Set the voting selection for the current user.
     *       <li>vote - int, vote value, [0 | 1 | 2 | 3]
     *         <ul>
     *           <li>0 - unknown</li> 
     *           <li>1 - High</li> 
     *           <li>2 - Medium</li> 
     *           <li>3 - Low</li> 
     *         </ul> 
     *       </li>
     * 
     * @param params A map contains:
     *     <ul>
     *       <li>voteSuiteId - int, id for a PackageVoteSuite object</li>
     *       <li>choices - A hash of the ClusterPackageId's as the key and the integer value of the vote for the value</li>
     *     </ul>
     * 
     * @return A map contains:
     *   <ul>
     *     <li>successful - a boolean value denoting if the operation succeeds</li>
     *     <li>reason - reason why operation failed (valid when successful==false)</li>
     *   </ul>
     */
    public Map setVoting(Long clusterPkgId, HashMap<Long, Integer> votes) {
        Map map = new HashMap();
        map.put("successful", false);

        try {        	
            this.packageService.setVotes(this.packageService.getUser(WebUtils.currentUser()), clusterPkgId, votes);
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
        Long suiteId = new Long((String) params.get("suiteId"));
        String description = (String) params.get("description");
        
        try {
            ClusteredPackage cpkg = this.packageService.createClusteredPackage(suiteId, description);
            map.put("pkgId", cpkg.getId());
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
     * @return A map contains:
     *     <ul>
     *       <li></li>
     *     </ul>
     */
    public Map deleteClusteredPackage(Map params) {
        Map map = new HashMap();
        map.put("successful", false);
        
        try {
            Long suiteId = new Long((String) params.get("suiteId"));
            Long pkgId = new Long((String) params.get("pkgId"));
        	
            this.packageService.deleteClusteredPackage(suiteId, pkgId);
            
            map.put("successful", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("reason", e.getMessage());
            return map;
        }
        
        return map;
    }//deleteClusteredPackage()
    
    
}//class PackageAgent
