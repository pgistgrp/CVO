package org.pgist.tagging;

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
public class TaggingAction extends Action {


    private TaggingService taggingService = null;
    
    private boolean included = false;
    
    private int count = 20;


    public void setTaggingService(TaggingService taggingService) {
        this.taggingService = taggingService;
    }


    public void setIncluded(boolean included) {
        this.included = included;
    }


    public void setCount(int count) {
        this.count = count;
    }


    /*
     * ---------------------------------------------------------------------
     */
    
    
    public ActionForward execute(
            ActionMapping mapping,
            ActionForm form,
            javax.servlet.http.HttpServletRequest request,
            javax.servlet.http.HttpServletResponse response
    ) throws java.lang.Exception {
        PageSetting setting = new PageSetting(count);
        setting.setPage(1);
        
        if (included) {
            request.setAttribute("tags", taggingService.getTags(setting, included));
        } else {
            request.setAttribute("stopWords", taggingService.getTags(setting, included));
        }
        request.setAttribute("setting", setting);
        
        return mapping.findForward("list");
    }//execute()


}//class StopWordAction
