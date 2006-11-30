package org.pgist.discussion;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * SDAction is the entry for a specific Structured Discussion instance.<br>
 * 
 * SDAction accepts a paramter from the request:
 *   <ul>
 *     <li>isid - the id of a InfoStructure object</li>
 *   </ul>
 *   
 * When executing, SDAction forwards to the jsp file mapped to name "main" in
 * struts-config.xml to render a HTML page.
 * 
 * @author kenny
 */
public class SDAction extends Action {
    
    
    /**
     * Spring injected service
     */
    private SDService sdService;
    
    
    public void setSdService(SDService sdService) {
        this.sdService = sdService;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    /**
     * Load a InfoStructure object from the database with the given id of "isid".<br>
     * If the object exists and is loaded successfully, it forwards to page with the
     * mapping name of "main" to render the page. In the jsp page, the following variables
     * are available for use:
     *   <ul>
     *     <li>structure - a InfoStructure object</li>
     *   </ul>
     *   
     * If the object doesn't exist or failed to be loaded, it forwards to page with the
     * mapping name of "error" to render the page.
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward execute(
            ActionMapping mapping,
            ActionForm form,
            javax.servlet.http.HttpServletRequest request,
            javax.servlet.http.HttpServletResponse response
    ) throws Exception {
        /*
         * id of a InfoStructure object
         */
        Long id = new Long((String) request.getParameter("isid"));
        
        /*
         * Load the specified InfoStructure object from database.
         */
        InfoStructure structure = sdService.getInfoStructureById(id);
        
        if (structure!=null) {
            request.setAttribute("structure", structure);
            request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
            return mapping.findForward("main");
        }
        
        return mapping.findForward("error");
    }//execute()
    
    
}//class SDAction
