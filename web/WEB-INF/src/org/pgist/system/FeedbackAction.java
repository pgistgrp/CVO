package org.pgist.system;

import java.util.Collection;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.util.PageSetting;


/**
 * An action for reviewing the feedbacks for moderator.<br>
 * 
 * This action accepts such parameters:<br>
 * <ul>
 *   <li>count - int, the max number of feedbacks to be shown in each page, default is -1, means all feedbacks.</li>
 *   <li>page - int, the current page number, default is 1.</li>
 * </ul>
 * 
 * The control will be forwarded to page with the mapping name of "main", with the following variables available:<br>
 * <ul>
 *   <li>feedbacks - a list of Feedback objects</li>
 *   <li>setting - a PageSetting object</li>
 * </ul>
 * 
 * @author kenny
 *
 */
public class FeedbackAction extends Action {
    
    
    private SystemService systemService;
    
    
    
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
    ) throws java.lang.Exception {
        PageSetting setting = new PageSetting();
        
        setting.setRowOfPage((String) request.getParameter("count"));
        setting.setPage((String) request.getParameter("page"));
        
        Collection feedbacks = systemService.getFeedbacks(setting);
        
        request.setAttribute("setting", setting);
        request.setAttribute("feedbacks", feedbacks);
        
        request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
        
        return mapping.findForward("main");
    }//execute()
    
    
}//class FeedbackAction
