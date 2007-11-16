package org.pgist.packages;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.criteria.CriteriaService;
import org.pgist.criteria.CriteriaSuite;
import org.pgist.util.WebUtils;


/**
 * UserPackageCreatorTuner in package customization Action.<br>
 * 
 * This action is used by participants to get help to creating his package.<br>
 * 
 * The action always accepts the following parameters:
 * <ul>
 *   <li>usrPkgId - the id of the user package PackageSuite object</li>
 * </ul>
 * 
 * The action will forward to page of "view", the following variables are available in jsp:
 * <ul>
 *   <li>userPkgId - the userPkgId</li>
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
    	if(tempUsrPkgId != null) {
    		Long usrPkgId = new Long(tempUsrPkgId);
    		
        	request.setAttribute("usrPkgId", usrPkgId);
       	}    	
        
        request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
        
        return mapping.findForward("view");
    }//execute()
    
    
}//class UserPackageCreatorTunerAction
