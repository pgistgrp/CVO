package org.pgist.discussion;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * 
 * @author kenny
 *
 */
public class SDConcernsAction extends Action {
    
    
    /**
     * Spring injected service
     */
    private SDService sdService;
    
    
    public void setSdService(SDService sdService) {
        this.sdService = sdService;
    }
    
    
    public ActionForward execute(
            ActionMapping mapping,
            ActionForm form,
            javax.servlet.http.HttpServletRequest request,
            javax.servlet.http.HttpServletResponse response
    ) throws Exception {
        String isidStr = request.getParameter("isid");
        String ioidStr = request.getParameter("ioid");
        
        request.setAttribute("ioid", ioidStr);
        request.setAttribute("isid", isidStr);
        
        Long isid = null;
        Long ioid = null;
        
        try {
            isid = new Long(isidStr);
        } catch (Exception e) {
        }
        
        try {
            ioid = new Long(ioidStr);
        } catch (Exception e) {
        }
        
        if (ioid!=null) {
            InfoObject object = sdService.getInfoObjectById(ioid);
            
            if (object==null) {
                return mapping.findForward("error");
            } else {
                request.setAttribute("infoObject", object);
                request.setAttribute("structure", object.getStructure());
            }
        } else if (isid!=null) {
            InfoStructure structure = sdService.getInfoStructureById(isid);
            
            if (structure==null) {
                return mapping.findForward("error");
            } else {
                request.setAttribute("structure", structure);
            }
        } else {
            return mapping.findForward("error");
        }
        
        request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
        
        return mapping.findForward("main");
    }//execute()
    
    
}//class SDConcernsAction
