package org.pgist.packages;

import java.util.Map;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.funding.FundingService;
import org.pgist.projects.ProjectService;
import org.pgist.util.WebUtils;


/**
 * Create participant's own package.<br>
 * 
 * This action is used by participants to create his package.<br>
 * 
 * In the page you can:
 * <ul>
 *   <li>Extract/Create a package for the current participant</li>
 *   <li>Select project alternatives into a package</li>
 *   <li>Select funding source alternatives into a package</li>
 * </ul>
 * 
 * The action accepts the following parameters:
 * <ul>
 *   <li>pkgSuiteId - the id of a specified PackageSuite object</li>
 *   <li>projSuiteId - the id of a specified PackageSuite object</li>
 *   <li>fundSuiteId - the id of a specified FundingSuite object</li>
 *   <li>critSuiteId - the id of a specified CriteriaSuite object</li>
 * </ul>
 * 
 * the action will forward to page of "view", the following variables are available in jsp:
 * <ul>
 *   <li>userPkg - a UserPackage object</li>
 *   <li>projectRefs - a collection of all the projectAltRefs associated with this suite</li>
 *   <li>fundingRefs - a collection of all the fundingAltRefs associated with this suite</li>   
 *   <li>projSuiteId - the id of a specified PackageSuite object</li>
 *   <li>fundSuiteId - the id of a specified FundingSuite object</li>
 *   <li>critSuiteId - the id of a specified CriteriaSuite object</li>
 * </ul>
 * 
 * Examples:
 * <ul>
 *   <li>GET:
 *       createPackage.do?suiteId=1234
 *   </li>
 * </ul>
 * 
 * @author kenny
 *
 */
public class CreatePackageAction extends Action {
    
    
    private PackageService packageService;
        
    public void setPackageService(PackageService packageService) {
        this.packageService = packageService;
    }

    private ProjectService projectService;
    
    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    private FundingService fundingService;
    
    public void setFundingService(FundingService fundingService) {
        this.fundingService = fundingService;
    }
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    public ActionForward execute(
            ActionMapping mapping,
            ActionForm form,
            javax.servlet.http.HttpServletRequest request,
            javax.servlet.http.HttpServletResponse response
    ) throws Exception {
    	String tempPackageSuiteId = request.getParameter("pkgSuiteId");
    	String tempProjSuiteId = request.getParameter("projSuiteId");
    	String tempFundSuiteId = request.getParameter("fundSuiteId");
    	String tempCritSuiteId = request.getParameter("critSuiteId");
    	if(tempPackageSuiteId != null) {
    		Long packSuite = new Long(tempPackageSuiteId);
    		Long projSuite = new Long(tempProjSuiteId);
    		Long fundSuite = new Long(tempFundSuiteId);
    		Long critSuite = new Long(tempCritSuiteId);
    		    		
    		UserPackage uPack = packageService.createUserPackage(packSuite, WebUtils.currentUser(), fundSuite);    			
    		request.setAttribute("userPkg", uPack);
    		
    		float min = Float.MAX_VALUE;
    		float max = Float.MIN_VALUE;
    		for (Map.Entry<Long, Float> entry : uPack.getPersonalCost().entrySet()) {
    		    float value = entry.getValue();
    		    if (value<min) min = value;
    		    if (value>max) max = value;
    		}
    		
    		request.setAttribute("projectRefs", projectService.getProjectSuite(projSuite).getReferences());
    		request.setAttribute("fundingRefs", fundingService.getFundingSuite(fundSuite).getReferences());
    		request.setAttribute("projSuiteId", projSuite);
    		request.setAttribute("fundSuiteId", fundSuite);
    		request.setAttribute("critSuiteId", critSuite);
    		request.setAttribute("min", min);
    		request.setAttribute("max", max);
    	}
        request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
        
        return mapping.findForward("view");
    }//execute()
    
    
}//class CreatePackageAction
