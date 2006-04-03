package org.pgist.cvo;

import java.util.Collection;

import org.pgist.system.UserDAO;
import org.pgist.users.User;
import org.pgist.util.WebUtils;


/**
 * 
 * @author kenny
 *
 */
public class CCTServiceImpl implements CCTService {
    
    
    private UserDAO userDAO = null;
    
    private CCTDAO cctDAO = null;
    
    
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }


    public void setCctDAO(CCTDAO cctDAO) {
        this.cctDAO = cctDAO;
    }


    /*
     * ------------------------------------------------------------------------
     */
    
    
    public Collection getCCTs() throws Exception {
        return cctDAO.getCCTs();
    }//getCCTs()


    public void save(CCT cct) throws Exception {
        Long id = WebUtils.currentUserId();
        User user = userDAO.getUserById(id, true, false);
        cct.setCreator(user);
        
        cctDAO.save(cct);
    }//save()


    public CCT getCCTById(Long cctId) throws Exception {
        return cctDAO.getCCTById(cctId);
    }//getCCTById()
    
    
}//class CCTServiceImpl
