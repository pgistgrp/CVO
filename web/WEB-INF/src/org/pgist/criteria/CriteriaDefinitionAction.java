package org.pgist.criteria;

import java.util.Collection;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.cvo.CCT;
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
        String action = (String) request.getParameter("action");
        
        if (action==null || action.length()==0) {
            Collection ccts = cctService.getCCTs();
            
            request.setAttribute("ccts", ccts);
            
            return mapping.findForward("list");
        } else {
            String cctId = (String) request.getParameter("cctId");
            
            Long id = new Long(cctId);
            CCT cct = cctService.getCCTById(id);
            
            request.setAttribute("cct", cct);
            
            request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
            
            return mapping.findForward("assoc");
        }
    }//execute()


}//class CriteriaPublishAction
