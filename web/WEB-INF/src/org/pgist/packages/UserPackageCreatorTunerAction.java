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
 * UserPackageCreatorTuner in package customization Action.<br>
 * 
 * This action is used by participants to get help to creating his package.<br>
 * 
 * The action always accepts the following parameters:
 * <ul>
 *   <li>usrPkgId - the id of the user package PackageSuite object</li>
 *   <li>projSuiteId - the id of a specified PackageSuite object</li>
 *   <li>fundSuiteId - the id of a specified FundingSuite object</li>  
 *   <li>critSuiteId - the id of the criteria suite to use 
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
    
    private ProjectService projectService;
    
    private FundingService fundingService;
    
    
    public void setPackageService(PackageService packageService) {
        this.packageService = packageService;
    }

    
    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    
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
    	String tempCritSuiteId = request.getParameter("critSuiteId");
    	
    	if(tempUsrPkgId != null) {
    		Long usrPkgId = new Long(tempUsrPkgId);
    		Long projSuite = new Long(tempProjSuiteId);
    		Long fundSuite = new Long(tempFundSuiteId);
    		Long critSuite = new Long(tempCritSuiteId);
            
    		request.setAttribute("projectRefs", projectService.getProjectSuite(projSuite).getReferences());
    		request.setAttribute("fundingRefs", fundingService.getFundingSuite(fundSuite).getReferences());
    		
            UserPackage uPack = packageService.calcUserValues(usrPkgId, fundSuite);             
            float min = Float.MAX_VALUE;
            float max = Float.MIN_VALUE;
            for (Map.Entry<Long, Float> entry : uPack.getPersonalCost().entrySet()) {
                float value = entry.getValue();
                if (value<min) min = value;
                if (value>max) max = value;
            }
            
            if (min % 10 > 0) {
                min = (min / 10 + 1) * 10;
            }
            
            request.setAttribute("min", min);
            request.setAttribute("max", max);
            
    		//Return the user package
    		request.setAttribute("usrPkgId", usrPkgId);
    		request.setAttribute("projSuiteId", projSuite);
    		request.setAttribute("fundSuiteId", fundSuite);
    		request.setAttribute("critSuiteId", critSuite);
    	}
        
        request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
        
        return mapping.findForward("view");
    }//execute()
    
    
}//class UserPackageCreatorTunerAction
