package org.pgist.packages;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.criteria.CriteriaService;
import org.pgist.criteria.CriteriaSuite;
import org.pgist.funding.FundingService;
import org.pgist.projects.ProjectService;
import org.pgist.util.WebUtils;


/**
 * UserPackageCreatorTuner in package customization Action.<br>
 * 
 * This action is used by participants to get help to creating his package.<br>
 * 
 * The action always accepts the following parameters:
 * <ul>
 *   <li>usrPkgId - the id of the user package PackageSuite object</li>
 *   <li>projSuiteId - the id of a specified PackageSuite object</li>
 *   <li>fundSuiteId - the id of a specified FundingSuite object</li>   
 * </ul>
 * 
 * The action will forward to page of "view", the following variables are available in jsp:
 * <ul>
 *   <li>userPkg - a UserPackage object</li>
 *   <li>projectRefs - a collection of all the projectAltRefs associated with this suite</li>
 *   <li>fundingRefs - a collection of all the fundingAltRefs associated with this suite</li>   
 * </ul>
 * 
 * 
 * @author kenny
 *
 */
public class UserPackageCreatorTunerAction extends Action {
    
    
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
    	String tempUsrPkgId = request.getParameter("usrPkgId");
    	String tempProjSuiteId = request.getParameter("projSuiteId");
    	String tempFundSuiteId = request.getParameter("fundSuiteId");
    	
    	
    	if(tempUsrPkgId != null) {
    		Long usrPkgId = new Long(tempUsrPkgId);
    		Long projSuite = new Long(tempProjSuiteId);
    		Long fundSuite = new Long(tempFundSuiteId);
    		
   		
    		request.setAttribute("projectRefs", projectService.getProjectSuite(projSuite).getReferences());
    		request.setAttribute("fundingRefs", fundingService.getFundingSuite(fundSuite).getReferences());
    		
    		//Return the user package
    		request.setAttribute("usrPkgId", usrPkgId);
    		request.setAttribute("projSuiteId", projSuite);
    		request.setAttribute("fundSuiteId", fundSuite);
    	}    	
        
        request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
        
        return mapping.findForward("view");
    }//execute()
    
    
}//class UserPackageCreatorTunerAction
