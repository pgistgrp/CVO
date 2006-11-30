package org.pgist.criteria;

import java.util.Collection;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.discussion.InfoStructure;
import org.pgist.discussion.SDService;


/**
 * 
 * @author kenny
 *
 */
public class CriteriaWeighAction extends Action {

    
    private CriteriaService criteriaService = null;
    
    private SDService sdService;
    
    
    public void setCriteriaService(CriteriaService criteriaService) {
        this.criteriaService = criteriaService;
    }


    public void setSdService(SDService sdService) {
        this.sdService = sdService;
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
        String isid = request.getParameter("isid");
        
        InfoStructure structure = sdService.getInfoStructureById(new Long(isid));
        
        request.setAttribute("structure", structure);
        
        request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
        
        return mapping.findForward("main");
    }//execute()


}//class CriteriaWeighAction
