package org.pgist.cvo;

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
        String action = (String) request.getParameter("action");
        
        if ("manage".equals(action)) {
            return mapping.findForward("manage");
        } else if ("".equals(action)) {
            
        }
        
        return mapping.findForward("list");
    }//execute()


}//class CriteriaMgrAction
