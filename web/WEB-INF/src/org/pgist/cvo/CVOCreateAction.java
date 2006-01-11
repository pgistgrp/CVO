package org.pgist.cvo;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.model.DiscourseObject;
import org.pgist.system.UserDAO;


/**
 * 
 * @author kenny
 *
 */
public class CVOCreateAction extends Action {

    
    private UserDAO userDAO;
    
    private CVODAO cvoDAO;
    
    
    public CVOCreateAction() {
    }
    
    
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
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
        
        DiscourseObject dobj = new DiscourseObject();
        CVO cvo = new CVO();
        cvo.setDeleted(false);
        cvo.setDiscourseObject(dobj);
        
        return mapping.findForward("list");
    }//execute()


}
