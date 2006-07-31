package org.pgist.discussion;

import java.util.Collection;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * 
 * @author kenny
 */
public class SDListAction extends Action {
    
    
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
    
    
    public ActionForward execute(
            ActionMapping mapping,
            ActionForm form,
            javax.servlet.http.HttpServletRequest request,
            javax.servlet.http.HttpServletResponse response
    ) throws Exception {
        try {
            Collection structures = sdService.getInfoStructures();
            
            request.setAttribute("structures", structures);
            
            return mapping.findForward("list");
        } catch (Exception e) {
            e.printStackTrace();
            return mapping.findForward("error");
        }
    }//execute()
    
    
}//class SDListAction
