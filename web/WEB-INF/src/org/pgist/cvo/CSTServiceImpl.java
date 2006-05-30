package org.pgist.cvo;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.pgist.system.UserDAO;
import org.pgist.util.PageSetting;


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
    
    
    public void addChildCategoryReference(Long cctId, Long parentId, String name) throws Exception {
        CCT cct = cctDAO.getCCTById(cctId);
        if (cct==null) throw new Exception("no such cct.");
        
        CategoryReference parent = null;
        if (parentId==null) {
            parent = cct.getRootCategory();
        } else {
            parent = cstDAO.getCategoryReferenceById(parentId);
            if (parent==null) parent = cct.getRootCategory();
        }
        
        if (!parent.getCct().getId().equals(cct.getId())) throw new Exception("no such category reference in this cct.");
        
        CategoryReference categoryReference = cstDAO.getCategoryReferenceByName(cctId, name);
        if (categoryReference==null) {
            Category category = cstDAO.getCategoryByName(name);
            if (category==null) {
                category = new Category();
                category.setName(name);
                cstDAO.save(category);
            }
            categoryReference = new CategoryReference();
            categoryReference.setCategory(category);
            categoryReference.setCct(cct);
        }
        categoryReference.getParents().add(parent);
        
        cstDAO.save(categoryReference);
        cstDAO.save(cct);
    }//addChildCategory()


    public void editCategoryReference(Long cctId, Long catRefId, String name) throws Exception {
        CCT cct = cctDAO.getCCTById(cctId);
        if (cct==null) throw new Exception("no such cct.");
        
        CategoryReference catRef = cstDAO.getCategoryReferenceById(catRefId);
        if (catRef==null) throw new Exception("no such category reference.");
        
        if (catRef.getCct().getId().longValue()!=cct.getId().longValue()) throw new Exception("no such category reference in this cct.");
        
        //cut off the relationships between parents and this child
        Set parents = new HashSet(catRef.getParents());
        for (Object object : parents) {
            CategoryReference one = (CategoryReference) object;
            one.getChildren().remove(catRef);
        }//for
        catRef.getParents().clear();
        
        CategoryReference categoryReference = cstDAO.getCategoryReferenceByName(cctId, name);
        if (categoryReference==null) {
            Category category = cstDAO.getCategoryByName(name);
            if (category==null) {
                category = new Category();
                category.setName(name);
                cstDAO.save(category);
            }
            categoryReference = new CategoryReference();
            categoryReference.setCct(cct);
            categoryReference.setCategory(category);
            cstDAO.save(categoryReference);
        }
        
        //create new relationships
        categoryReference.getChildren().addAll(catRef.getChildren());
        categoryReference.getParents().addAll(parents);
        for (Object object : parents) {
            CategoryReference one = (CategoryReference) object;
            one.getChildren().add(categoryReference);
        }//for
        
        cstDAO.save(categoryReference);
    }//editCategoryReference()


    public void deleteCategoryReference(Long cctId, Long parentId, Long catRefId) throws Exception {
        CCT cct = cctDAO.getCCTById(cctId);
        if (cct==null) throw new Exception("no such cct.");
        
        CategoryReference parent = null;
        if (parentId==null) {
            parent = cct.getRootCategory();
        } else {
            parent = cstDAO.getCategoryReferenceById(parentId);
        }
        
        if (parent==null) throw new Exception("no such category reference.");
        
        if (parent.getCct().getId().longValue()!=cct.getId().longValue()) throw new Exception("no such category reference in this cct.");
        
        CategoryReference catRef = cstDAO.getCategoryReferenceById(catRefId);
        if (catRef==null) throw new Exception("no such category reference.");
        
        if (catRef.getCct().getId().longValue()!=cct.getId().longValue()) throw new Exception("no such category reference in this cct.");
        
        parent.getChildren().remove(catRef);
        catRef.getParents().remove(parent);
        
        cstDAO.save(parent);
        cstDAO.save(catRef);
    }//deleteCategoryReference()


    public void relateTagToCategory(Long cctId, Long catRefId, Long tagRefId) throws Exception {
        CCT cct = cctDAO.getCCTById(cctId);
        if (cct==null) throw new Exception("no such cct.");
        
        CategoryReference catRef = cstDAO.getCategoryReferenceById(catRefId);
        if (catRef==null) throw new Exception("no such category reference.");
        
        TagReference tagRef = cstDAO.getTagReferenceById(tagRefId);
        if (tagRef==null) throw new Exception("no such tag reference.");
        
        if (catRef.getCct().getId().longValue()!=cct.getId().longValue()) throw new Exception("no such category reference in this cct.");
        
        if (tagRef.getCctId().longValue()!=cct.getId().longValue()) throw new Exception("no such tag reference in this cct.");
        
        catRef.getTags().add(tagRef);
        
        cstDAO.save(catRef);
    }//relateTagToCategory()


    public void deleteTagFromCategory(Long cctId, Long catRefId, Long tagRefId) throws Exception {
        CCT cct = cctDAO.getCCTById(cctId);
        if (cct==null) throw new Exception("no such cct.");
        
        CategoryReference catRef = cstDAO.getCategoryReferenceById(catRefId);
        if (catRef==null) throw new Exception("no such category reference.");
        
        TagReference tagRef = cstDAO.getTagReferenceById(tagRefId);
        if (tagRef==null) throw new Exception("no such tag reference.");
        
        if (catRef.getCct().getId().longValue()!=cct.getId().longValue()) throw new Exception("no such category reference in this cct.");
        
        if (tagRef.getCctId().longValue()!=cct.getId().longValue()) throw new Exception("no such tag reference in this cct.");
        
        catRef.getTags().remove(tagRef);
        
        cstDAO.save(catRef);
    }//deleteTagFromCategory()


    public Object[] getConcernsByTag(Long cctId, Long tagRefId, PageSetting setting) throws Exception {
        Object[] values = new Object[3];
        
        CCT cct = cctDAO.getCCTById(cctId);
        if (cct==null) throw new Exception("no such cct.");
        values[0] = cct;
        
        TagReference tagRef = cstDAO.getTagReferenceById(tagRefId);
        if (tagRef==null) throw new Exception("no such tag reference.");
        
        if (tagRef.getCctId().longValue()!=cct.getId().longValue()) throw new Exception("no such tag reference in this cct.");
        values[1] = tagRef;
        
        values[2] = cstDAO.getConcernsByTag(cctId, tagRefId, setting);
        
        return values;
    }//getConcernsByTag()


    public void saveSummary(Long cctId, String summary) throws Exception {
        CCT cct = cctDAO.getCCTById(cctId);
        if (cct==null) throw new Exception("no such cct.");
        
        //TODO finish saving summary
    }//saveSummary()


    public Collection getRealtedTags(Long cctId, Long categoryId, PageSetting setting) throws Exception {
        return cstDAO.getRealtedTags(cctId, categoryId, setting);
    }//getRealtedTags()


    public Collection getUnrelatedTags(Long cctId, Long categoryId, PageSetting setting) throws Exception {
        return cstDAO.getUnrelatedTags(cctId, categoryId, setting);
    }//getUnrelatedTags()


}//class CSTServiceImpl
