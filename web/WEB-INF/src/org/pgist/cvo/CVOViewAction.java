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

    
    private CVODAO cvoDAO;
    
    
    public CVOViewAction() {
    }
    
    
    public void setCvoDAO(CVODAO cvoDAO) {
        this.cvoDAO = cvoDAO;
    }
    
    
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
    
    
}
