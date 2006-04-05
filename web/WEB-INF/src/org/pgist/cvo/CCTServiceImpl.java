package org.pgist.cvo;

import java.util.Collection;
import java.util.Date;
import java.util.List;

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

    private TagDAO tagDAO = null;
    
    
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }


    public void setCctDAO(CCTDAO cctDAO) {
        this.cctDAO = cctDAO;
    }


    public void setTagDAO(TagDAO tagDAO) {
        this.tagDAO = tagDAO;
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


    public Concern createConcern(CCT cct, String concern, List tags) throws Exception {
        Concern concernObj = new Concern();
        
        Long id = WebUtils.currentUserId();
        User user = userDAO.getUserById(id, true, false);
        concernObj.setAuthor(user);
        concernObj.setContent(concern);
        concernObj.setCreateTime(new Date());
        
        List tagList = tagDAO.addTags(tags);
        concernObj.getTags().addAll(tagList);
        
        cctDAO.save(cct);
        
        return null;
    }//createConcern()


    public Collection getMyConcerns(CCT cct) throws Exception {
        return cctDAO.getMyConcerns(cct.getId(), WebUtils.currentUserId());
    }//getMyConcerns()


    public Collection getOthersConcerns(CCT cct, int count) throws Exception {
        return cctDAO.getOthersConcerns(cct.getId(), WebUtils.currentUserId(), count);
    }//getOthersConcerns()
    
    
}//class CCTServiceImpl
