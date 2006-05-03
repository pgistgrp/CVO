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
    
    
    public void save(Category category) throws Exception {
        cstDAO.save(category);
    }//save()


    public void save(CategoryReference categoryReference) throws Exception {
        cstDAO.save(categoryReference);
    }//save()
    
    
    public CategoryReference getCategoryReferenceById(Long categoryId) throws Exception {
        return cstDAO.getCategoryReferenceById(categoryId);
    }//getCategoryReferenceById()


    public Category getCategoryByName(String name) throws Exception {
        return cstDAO.getCategoryByName(name);
    }//getCategoryByName()
    
    
    public CategoryReference getCategoryReferenceByName(String name) throws Exception {
        return cstDAO.getCategoryReferenceByName(name);
    }//getCategoryReferenceByName()


}//class CSTServiceImpl
