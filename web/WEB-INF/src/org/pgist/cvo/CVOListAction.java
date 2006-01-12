package org.pgist.cvo;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.system.UserDAO;


/**
 * 
 * @author kenny
 *
 */
public class CVOListAction extends Action {

    
    private UserDAO userDAO;
    
    private CVODAO cvoDAO;
    
    
    public CVOListAction() {
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
        cvoform.setCvoList(cvoDAO.getCVOList());
        
        return mapping.findForward("list");
    }//execute()


}
