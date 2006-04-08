package org.pgist.cvo;

import java.util.ArrayList;
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
    
    private TagAnalyzer analyzer = null;
    
    
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
        cctDAO.save(cct);
    }//save()


    public CCT getCCTById(Long cctId) throws Exception {
        return cctDAO.getCCTById(cctId);
    }//getCCTById()


    public Concern createConcern(CCT cct, Concern concern, String[] tags) throws Exception {
        concern.setCct(cct);
        concern.setCreateTime(new Date());
        
        List list = new ArrayList(tags.length);
        for (int i=0; i<tags.length; i++) {
            if (tags[i]==null) continue;
            tags[i] = tags[i].trim();
            if ("".equals(tags[i])) continue;
            Tag tag = analyzer.ensureTag(tags[i]);
            list.add(tag);
        }//for i
        cct.addConcern(concern, list);
        
        List tagList = tagDAO.addTags(tags);
        concern.getTags().addAll(tagList);
        
        cctDAO.save(cct);
        
        return null;
    }//createConcern()


    public Collection getMyConcerns(CCT cct) throws Exception {
        return cctDAO.getMyConcerns(cct.getId(), WebUtils.currentUserId());
    }//getMyConcerns()


    public Collection getOthersConcerns(CCT cct, int count) throws Exception {
        return cctDAO.getOthersConcerns(cct.getId(), WebUtils.currentUserId(), count);
    }//getOthersConcerns()


    public Collection getTagsByRank(CCT cct, int count) throws Exception {
        return null;
    }//getTagsByRank()


    public Collection getTagsByThreshold(CCT cct, float threshold) throws Exception {
        return null;
    }//getTagsByThreshold()
    
    
}//class CCTServiceImpl
