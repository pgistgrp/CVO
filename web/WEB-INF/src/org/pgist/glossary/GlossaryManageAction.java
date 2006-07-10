package org.pgist.glossary;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.util.PageSetting;


/**
 * 
 * @author kenny
 *
 */
public class GlossaryManageAction extends Action {
    
    
    private GlossaryService glossaryService;
    
    private int count = 20;
    
    
    public void setGlossaryService(GlossaryService glossaryService) {
        this.glossaryService = glossaryService;
    }
    
    
    public void setCount(int count) {
        this.count = count;
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
        PageSetting setting = new PageSetting(count);
        setting.setPage(1);
        
        request.setAttribute("terms", glossaryService.getTerms(setting, true));
        request.setAttribute("setting", setting);
        
        return mapping.findForward("list");
    }//execute()


}//class GlossaryManageAction
