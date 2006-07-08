package org.pgist.glossary;

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
public class GlossaryPublicAction extends Action {
    
    
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
        String filter = "";
        String sort = "name";
        String direction = "asc";
        Collection terms = glossaryService.getTerms(filter, sort, direction, new int[] {Term.STATUS_OFFICIAL});
        
        request.setAttribute("filter", filter);
        request.setAttribute("sort", sort);
        request.setAttribute("direction", direction);
        request.setAttribute("terms", terms);
        
        return mapping.findForward("list");
    }//execute()


}//class GlossaryPublicAction
