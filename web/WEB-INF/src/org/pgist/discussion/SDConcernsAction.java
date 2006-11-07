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
        
        Long isid = new Long(isidStr);
        
        InfoStructure structure = sdService.getInfoStructureById(isid);
        if (structure==null) {
            return mapping.findForward("error");
        }
        
        request.setAttribute("structure", structure);
        
        Long ioid = null;
        try {
            ioid = new Long(ioidStr);
            
            InfoObject object = sdService.getInfoObjectById(ioid);
            request.setAttribute("object", structure);
        } catch (Exception e) {
        }
        
        return mapping.findForward("main");
    }//execute()
    
    
}//class SDConcernsAction
