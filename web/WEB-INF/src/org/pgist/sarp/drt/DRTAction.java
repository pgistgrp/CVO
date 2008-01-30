package org.pgist.sarp.drt;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.system.SystemService;
import org.pgist.system.YesNoVoting;


/**
 * Discussion and Review Tool Action.
 * 
 * SDAction accepts a paramter from the request:
 *   <ul>
 *     <li>oid - the id of a InfoObject object</li>
 *   </ul>
 *   
 * When executing, DRTAction forwards to the jsp file mapped to name "main" in
 * struts-config.xml to render a HTML page.
 * 
 * @author kenny
 */
public class DRTAction extends Action {
    
    
    /**
     * Spring injected service
     */
	private DRTService drtService;
	
    private SystemService systemService;
    
    
    public void setDrtService(DRTService drtService) {
		this.drtService = drtService;
	}


	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}


    /*
     * ------------------------------------------------------------------------
     */
    
    
	/**
     * Load a InfoObject from the database with the given id of "oid".<br>
     * If the object exists and is loaded successfully, it forwards to page with the
     * mapping name of "main" to render the page. In the jsp page, the following variables
     * are available for use:
     *   <ul>
     *     <li>infoObject - a InfoObject object</li>
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
         * id of a InfoObject
         */
        Long oid = new Long((String) request.getParameter("oid"));
        
        /*
         * Load the specified InfoObject object from database.
         */
        InfoObject infoObject = drtService.getInfoObjectById(oid);
        YesNoVoting voting = systemService.getVoting(YesNoVoting.TYPE_SART_DRT_INFOOBJ, oid);
        
        if (infoObject!=null) {
        	request.setAttribute("infoObject", infoObject);
            request.setAttribute("voting", voting);
            request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
            return mapping.findForward("main");
        }
        
        return mapping.findForward("error");
    }//execute()
    
    
}//class DRTAction
