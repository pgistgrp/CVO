package org.pgist.sarp.drt;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.system.SystemService;


/**
 * A modification tool for moderator in DRT modification Action.
 * 
 * @author kenny
 */
public class DRTModAction extends Action {
    
    
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
        
        if (infoObject!=null) {
        	request.setAttribute("infoObject", infoObject);
            request.setAttribute("announcements", infoObject.getAnnouncements());
            request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
            return mapping.findForward("main");
        }
        
        return mapping.findForward("error");
    }//execute()
    
    
}//class DRTModAction
