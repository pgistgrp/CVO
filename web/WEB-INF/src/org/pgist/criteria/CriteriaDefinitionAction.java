package org.pgist.criteria;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.cvo.CCTService;

/**
 * 
 * @author kenny
 *
 */
public class CriteriaDefinitionAction extends Action {

    
    private CriteriaService criteriaService = null;
    
    private CCTService cctService;
    
    
    public void setCriteriaService(CriteriaService criteriaService) {
        this.criteriaService = criteriaService;
    }


    public void setCctService(CCTService cctService) {
        this.cctService = cctService;
    }


    /*
     * ------------------------------------------------------------------------
     */
    
    
    public ActionForward execute(
            ActionMapping mapping,
            ActionForm form,
            javax.servlet.http.HttpServletRequest request,
            javax.servlet.http.HttpServletResponse response
    ) throws java.lang.Exception {
    	
	    Collection criteria = criteriaService.getAllCriterion();
        	
            String strSuiteId = request.getParameter("suiteId");
            String strCctId = request.getParameter("cctId");
            String strThemeIsid = request.getParameter("theme_isid");
            System.out.println("*** ThemeISID:" + strThemeIsid);
            Long suiteId = Long.parseLong(strSuiteId);
            CriteriaSuite cs = criteriaService.getCriteriaSuiteById(suiteId);
            
            Long cctId = Long.parseLong(strCctId);
            Long themeIsid = Long.parseLong(strThemeIsid);
            Set infoObjects = criteriaService.getInfoObjects(themeIsid);
            System.out.println("***ThemeIsid" + themeIsid);
           
            request.setAttribute("criteriasuite", cs);
            request.setAttribute("infoObjects", infoObjects);
            request.setAttribute("cctId", cctId);
            request.setAttribute("isid", themeIsid);
            System.out.println("***InfoObjects" + infoObjects);
            //request.setAttribute("criteria", criteria);
            
            request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
            
            return mapping.findForward("assoc");
        
    }//execute()


}//class CriteriaDefinitionAction
