package org.pgist.cvo;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.model.DiscourseObject;


/**
 * 
 * @author kenny
 *
 */
public class CVOViewAction extends Action {

    
    private CVODAO1 cvoDAO;
    
    private CCTService cvoService;
    
    
    public CVOViewAction() {
    }
    
    
    public void setCvoDAO(CVODAO1 cvoDAO) {
        this.cvoDAO = cvoDAO;
    }
    
    
    public void setCvoService(CCTService CVOService) {
        this.cvoService = CVOService;
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
        CVOForm cvoform = (CVOForm) form;
        
        CVO cvo = cvoDAO.getCVOById(cvoform.getId());
        
        DiscourseObject dobj = cvo.getDiscourseObject();
        
        cvoform.setCvo(cvo);
        
        cvoform.setDobj(dobj);
        
        cvoform.setRoot(dobj.getRoot());
        
        return mapping.findForward("view");
    }//execute()


}//class CVOViewAction
