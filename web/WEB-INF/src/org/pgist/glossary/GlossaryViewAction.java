package org.pgist.glossary;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * 
 * @author kenny
 *
 */
public class GlossaryViewAction extends Action {
    
    
    private GlossaryService glossaryService;
    
    
    public void setGlossaryService(GlossaryService glossaryService) {
        this.glossaryService = glossaryService;
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
        Long id = new Long(request.getParameter("id"));
        
        Term term = glossaryService.getTermById(id);
        glossaryService.increaseViewCount(term);
        
        request.setAttribute("term", term);
        
        request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
        
        return mapping.findForward("view");
    }//execute()


}//class GlossaryPublicAction
