package org.pgist.cvo;

import org.pgist.system.UserDAO;


/**
 * 
 * @author kenny
 *
 */
public class CSTServiceImpl implements CSTService {
    
    
    private CCTDAO cctDAO = null;

    private CSTDAO cstDAO;
    
    private TagDAO tagDAO = null;

    private UserDAO userDAO = null;

    
    public void setCstDAO(CSTDAO cstDAO) {
        this.cstDAO = cstDAO;
    }


    public void setCctDAO(CCTDAO cctDAO) {
        this.cctDAO = cctDAO;
    }


    public void setTagDAO(TagDAO tagDAO) {
        this.tagDAO = tagDAO;
    }


    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    
    
    
    
}//class CSTServiceImpl
