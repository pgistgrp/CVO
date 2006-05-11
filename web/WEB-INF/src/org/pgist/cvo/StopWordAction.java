package org.pgist.cvo;
/**
 *
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: PGIST</p>
 *
 * @author Jie
 * @version 1.0
 */
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.util.PageSetting;

public class StopWordAction extends Action {


    private CCTService cctService = null;


    public StopWordAction() {
    }


    public void setCctService(CCTService cctService) {
        this.cctService = cctService;
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
        cctform.setStopWords(cctService.getStopWords(new PageSetting(1)));

        return mapping.findForward("list");
    }//execute()


}//class StopWordAction
