package org.pgist.criteria;

import java.util.Collection;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.cvo.CCT;
import org.pgist.discussion.InfoStructure;
import org.pgist.discussion.SDService;


/**
 * 
 * @author kenny
 *
 */
public class CriteriaWeighAction extends Action {

    
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
    	 String strSuiteId = request.getParameter("suiteid");
    	 Long suiteId = new Long(strSuiteId);

        
         if (suiteId==null) {
            //retrieve the criteriaSuites
             Collection cs = criteriaService.getCriteriaSuites();
             request.setAttribute("cs", cs);
             request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
            
             return mapping.findForward("list");
         } else {
            //retrieve the criteria list
            
             Collection criteria = criteriaService.getCriterias();
            
             CriteriaSuite cs = criteriaService.getCriteriaSuiteById(suiteId);
            
             request.setAttribute("criteriasuite", cs);
             request.setAttribute("criteria", criteria);
             request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
            
             return mapping.findForward("view");
         }
    }//execute()


}//class CriteriaWeighAction
