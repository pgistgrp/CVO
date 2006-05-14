package org.pgist.cvo;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.util.PageSetting;


/**
 * 
 * @author Jie Wu
 *
 */
public class StopWordAction extends Action {


    private StopWordService stopWordService = null;


    public void setStopWordService(StopWordService stopWordService) {
        this.stopWordService = stopWordService;
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
        CCTForm cctform = (CCTForm) form;
        PageSetting setting = new PageSetting(20);
        setting.setPage(1);
        cctform.setPageSetting(setting);
        cctform.setStopWords(stopWordService.getStopWords(setting));

        return mapping.findForward("list");
    }//execute()


}//class StopWordAction
