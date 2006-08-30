package org.pgist.discussion;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * SDRoomAction is the entry for a specific Theme/Project discussion.<br>
 * 
 * SDRoomAction accepts paramters from the request:
 *   <ul>
 *     <li>isid - the id of a InfoStructure object</li>
 *     <li>ioid - the id of a InfoObject object</li>
 *   </ul>
 *   
 * When executing, SDRoomAction forwards to the jsp file mapped to name "main" in
 * struts-config.xml to render a HTML page.
 * 
 * @author kenny
 */
public class SDRoomAction extends Action {
    
    
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
     *     <li>object - a InfoObject object</li>
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
         * isid of a InfoStructure object
         */
        Long isid = new Long((String) request.getParameter("isid"));
        
        /*
         * Load the specified InfoStructure object from database.
         */
        InfoStructure structure = sdService.getInfoStructureById(isid);
        
        if (structure==null) return mapping.findForward("error");
        
        request.setAttribute("structure", structure);
        
        try {
            /*
             * ioid of a InfoObject object
             */
            Long ioid = new Long((String) request.getParameter("ioid"));
            
            /*
             * Load the specified InfoObject object from database.
             */
            InfoObject object = sdService.getInfoObjectById(ioid);
            
            if (object==null) return mapping.findForward("error");
            
            request.setAttribute("object", object);
        } catch(Exception e) {
        }
        
        return mapping.findForward("main");
    }//execute()
    
    
}//class SDRoomAction
