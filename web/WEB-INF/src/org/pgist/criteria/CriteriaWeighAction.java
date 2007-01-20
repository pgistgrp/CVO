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
        String cctId = request.getParameter("cctId");
        
        if (cctId==null) {
            //retrieve the cct list
            Collection ccts = criteriaService.getCCTs();
            request.setAttribute("ccts", ccts);
            request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
            
            return mapping.findForward("list");
        } else {
            //retrieve the criteria list
            
            Long id = new Long(cctId);
            
            Collection criteria = criteriaService.getCriterias();
            
            CCT cct = criteriaService.getCCTById(id);
            
            request.setAttribute("cct", cct);
            request.setAttribute("criteria", criteria);
            request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
            
            return mapping.findForward("view");
        }
    }//execute()


}//class CriteriaWeighAction
