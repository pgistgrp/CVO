package org.pgist.other;

import java.util.Collection;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * An action class for importing a template to a situation.
 * 
 * @author kenny
 */
public class TemplateImportingAction extends Action {

    
    private ImportService importService = null;
    
    
    public void setImportService(ImportService importService) {
        this.importService = importService;
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
        Collection list = importService.getTemplates();
        
        request.setAttribute("templates", list);
        
        request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
        
        return mapping.findForward("list");
    }//execute()


}//class TemplateImportingAction
