package org.pgist.criteria;

import java.util.Collection;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.pgist.cvo.CCT;

/**
 * 
 * @author kenny
 *
 */
public class CriteriaMgrAction extends Action {

    
    private CriteriaService criteriaService = null;
    
    
    public void setCriteriaService(CriteriaService criteriaService) {
        this.criteriaService = criteriaService;
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
        Collection criteria = criteriaService.getCriterias();
        
        String strSuiteId = request.getParameter("suiteid");
        Long suiteId = new Long(strSuiteId);
        CriteriaSuite cs = criteriaService.getCriteriaSuiteById(suiteId);
        
        request.setAttribute("criteriasuite", cs);
        request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
        
        return mapping.findForward("list");
    }//execute()


}//class CriteriaMgrAction
