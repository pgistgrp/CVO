package org.pgist.cvo;

import java.util.Collection;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * 
 * @author kenny
 *
 */
public class CriteriaMgrAction extends Action {

    
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
        
        if ("manage".equals(action)) {
            return mapping.findForward("manage");
        } else if ("assoc".equals(action)) {
            Long cctId = null;
            
            try {
                cctId = new Long((String) request.getParameter("cctId"));
            } catch (Exception e) {
            }
            
            if (cctId!=null) {
                CCT cct = cctService.getCCTById(cctId);
                if (cct!=null) {
                    request.setAttribute("cct", cct);
                    return mapping.findForward("assoc");
                }
            }
        }
        
        Collection ccts = cctService.getCCTs();
        
        request.setAttribute("ccts", ccts);
        
        return mapping.findForward("list");
    }//execute()


}//class CriteriaMgrAction
