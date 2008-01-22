package org.pgist.sarp.cst;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.pgist.discussion.DiscussionDAO;
import org.pgist.discussion.InfoObject;
import org.pgist.discussion.InfoStructure;
import org.pgist.sarp.bct.BCT;
import org.pgist.sarp.bct.BCTDAO;
import org.pgist.sarp.bct.CategoryReference;
import org.pgist.sarp.bct.TagReference;
import org.pgist.sarp.bct.Theme;
import org.pgist.tagging.Category;
import org.pgist.tagging.Tag;
import org.pgist.tagging.TagDAO;
import org.pgist.util.PageSetting;


/**
 * 
 * @author kenny
 *
 */
public class CSTServiceImpl implements CSTService {
    
    
    private BCTDAO bctDAO = null;

    private CSTDAO cstDAO = null;
    
    private TagDAO tagDAO = null;

    private DiscussionDAO discussionDAO = null;

    
    public void setCstDAO(CSTDAO cstDAO) {
        this.cstDAO = cstDAO;
    }


    public void setBctDAO(BCTDAO bctDAO) {
        this.bctDAO = bctDAO;
    }


    public void setTagDAO(TagDAO tagDAO) {
        this.tagDAO = tagDAO;
    }


    public void setDiscussionDAO(DiscussionDAO discussionDAO) {
        this.discussionDAO = discussionDAO;
    }


    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void save(Category category) throws Exception {
        tagDAO.save(category);
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
    
    
    public CategoryReference addCategoryReference(Long bctId, Long parentId, String name) throws Exception {
        BCT bct = bctDAO.getBCTById(bctId);
        if (bct==null) throw new Exception("no such bct.");
        
        CategoryReference parent = null;
        if (parentId==null || parentId<1) {
            /*
             * Use root category as the parent category
             */
            parent = bct.getRootCategory();
        } else {
            /*
             * Get the parent category
             */
            parent = cstDAO.getCategoryReferenceById(parentId);
            
            /*
             * If parent category not exists, still use the root category
             */
            if (parent==null) parent = bct.getRootCategory();
        }
        
        /*
         * check if the parent category is in the specified bct
         */
        if (!parent.getBct().getId().equals(bct.getId())) throw new Exception("no such category reference in this bct.");
        
        /*
         * get the child category reference
         */
        CategoryReference categoryReference = cstDAO.getCategoryReferenceByName(bctId, name);
        
        if (categoryReference==null) {
            /*
             * it's a new category reference
             */
            
            /*
             * check if a category with this tag name exists
             */
            Category category = cstDAO.getCategoryByName(name);
            if (category==null) {
                /*
                 * not exists, create a new category with this tag name
                 */
                category = new Category();
                category.setName(name);
                tagDAO.save(category);
            }
            
            /*
             * create the new category reference
             */
            categoryReference = new CategoryReference();
            categoryReference.setCategory(category);
            categoryReference.setBct(bct);
            categoryReference.getTheme().setCreateTime(new Date());
            categoryReference.getTheme().setTitle(category.getName());
            
            cstDAO.save(categoryReference);
        }
        
        /*
         * now a category reference with the given tag name is ready for use
         */
        
        /*
         * establish parent-child relationship
         */
        parent.getChildren().add(categoryReference);
        
        cstDAO.save(categoryReference);
        
        return categoryReference;
    }//addCategoryReference()


    public void copyCategoryReference(Long bctId, Long parentId, Long categoryId) throws Exception {
        BCT bct = bctDAO.getBCTById(bctId);
        if (bct==null) throw new Exception("no such bct.");
        
        CategoryReference parent = null;
        
        if (parentId==null) {
            /*
             * Use root category as the parent category
             */
            parent = bct.getRootCategory();
        } else {
            /*
             * Get the parent category
             */
            parent = cstDAO.getCategoryReferenceById(parentId);
            
            /*
             * If parent category not exists, still use the root category
             */
            if (parent==null) parent = bct.getRootCategory();
        }
        
        /*
         * check if the parent category is in the specified bct
         */
        if (!parent.getBct().getId().equals(bct.getId())) throw new Exception("no such category reference in this bct.");
        
        /*
         * get the category reference
         */
        CategoryReference categoryReference = cstDAO.getCategoryReferenceById(categoryId);
        
        if (categoryReference==null) throw new Exception("no such category reference");
        if (categoryReference.getBct().getId()!=bctId) throw new Exception("no such category reference in this bct.");
        
        /*
         * check if parent can be the parent of this category
         * RULE: child can't appear in parent's uptree route
         */
        LinkedList<CategoryReference> stack = new LinkedList<CategoryReference>();
        stack.addAll(parent.getParents());
        while (!stack.isEmpty()) {
            CategoryReference one = stack.poll();
            if (one==categoryReference) throw new Exception("A category cannot be both parent and child of another category");
            stack.addAll(one.getParents());
        }//while
        
        /*
         * establish parent-child relationship
         */
        parent.getChildren().add(categoryReference);
        
        cstDAO.save(categoryReference);
        cstDAO.save(bct);
    }//copyCategoryReference()
    
    
    public CategoryReference duplicateCategoryReference(Long bctId, Long parentId, Long categoryId, String name) throws Exception {
        BCT bct = bctDAO.getBCTById(bctId);
        if (bct==null) throw new Exception("no such bct.");
        
        CategoryReference parent = null;
        
        if (parentId==null) {
            /*
             * Use root category as the parent category
             */
            parent = bct.getRootCategory();
        } else {
            /*
             * Get the parent category
             */
            parent = cstDAO.getCategoryReferenceById(parentId);
            
            /*
             * If parent category not exists, still use the root category
             */
            if (parent==null) parent = bct.getRootCategory();
        }
        
        /*
         * check if the parent category is in the specified bct
         */
        if (!parent.getBct().getId().equals(bct.getId())) throw new Exception("no such category reference in this bct.");
        
        /*
         * get the category reference
         */
        CategoryReference categoryReference = cstDAO.getCategoryReferenceById(categoryId);
        
        if (categoryReference==null) throw new Exception("no such category reference");
        if (categoryReference.getBct().getId()!=bctId) throw new Exception("no such category reference in this bct.");
        
        CategoryReference newCat = null;
        Category category = cstDAO.getCategoryByName(name);
        if (category==null) {
            category = new Category();
            category.setName(name);
            category.setDeleted(false);
            tagDAO.save(category);
        } else {
            newCat = cstDAO.getCategoryReferenceById(category.getId());
        }
        
        if (newCat==null) {
            newCat = new CategoryReference();
            newCat.setCategory(category);
            newCat.setBct(bct);
            newCat.getTags().addAll(categoryReference.getTags());
            newCat.getTheme().setCreateTime(new Date());
            newCat.getTheme().setTitle(categoryReference.getCategory().getName());
            cstDAO.save(newCat);
        }
        
        /*
         * establish parent-child relationship
         */
        parent.getChildren().add(newCat);
        
        cstDAO.save(parent);
        
        return newCat;
    }//duplicateCategoryReference()
    
    
    public void moveCategoryReference(Long bctId, Long parent0Id, Long parent1Id, Long categoryId) throws Exception {
        BCT bct = bctDAO.getBCTById(bctId);
        if (bct==null) throw new Exception("no such bct.");
        
        CategoryReference parent0 = null;
        
        if (parent0Id==null) {
            /*
             * Use root category as the parent category
             */
            parent0 = bct.getRootCategory();
        } else {
            /*
             * Get the parent category
             */
            parent0 = cstDAO.getCategoryReferenceById(parent0Id);
            
            /*
             * If parent category not exists, still use the root category
             */
            if (parent0==null) throw new Exception("parent0 is not found");
        }
        
        CategoryReference parent1 = null;
        
        if (parent1Id==null) {
            /*
             * Use root category as the parent category
             */
            parent1 = bct.getRootCategory();
        } else {
            /*
             * Get the parent category
             */
            parent1 = cstDAO.getCategoryReferenceById(parent1Id);
            
            /*
             * If parent category not exists, still use the root category
             */
            if (parent1==null) throw new Exception("parent1 is not found");
        }
        
        /*
         * check if the parent category is in the specified bct
         */
        if (!parent0.getBct().getId().equals(bct.getId())) throw new Exception("no such category reference in this bct.");
        if (!parent1.getBct().getId().equals(bct.getId())) throw new Exception("no such category reference in this bct.");
        if (parent0==parent1) throw new Exception("no need to move");
        
        /*
         * get the category reference
         */
        CategoryReference categoryReference = cstDAO.getCategoryReferenceById(categoryId);
        
        if (categoryReference==null) throw new Exception("no such category reference");
        if (categoryReference.getBct().getId()!=bctId) throw new Exception("no such category reference in this bct.");
        
        /*
         * check if parent1 can be the parent of this category
         * RULE: child can't appear in parent's uptree route
         */
        LinkedList<CategoryReference> stack = new LinkedList<CategoryReference>();
        stack.addAll(parent1.getParents());
        while (!stack.isEmpty()) {
            CategoryReference one = stack.poll();
            if (one==categoryReference) throw new Exception("A category cannot be both parent and child of another category");
            stack.addAll(one.getParents());
        }//while
        
        /*
         * cutoff categoryReference from its parent
         */
        parent0.getChildren().remove(categoryReference);
        
        /*
         * establish parent-child relationship
         */
        parent1.getChildren().add(categoryReference);
        
        cstDAO.save(categoryReference);
        cstDAO.save(bct);
    }//moveCategoryReference()
    
    
    public void editCategoryReference(Long bctId, Long catRefId, String name) throws Exception {
        BCT bct = bctDAO.getBCTById(bctId);
        if (bct==null) throw new Exception("no such bct.");
        
        CategoryReference catRef = cstDAO.getCategoryReferenceById(catRefId);
        if (catRef==null) throw new Exception("no such category reference.");
        
        if (catRef.getBct().getId().longValue()!=bct.getId().longValue()) throw new Exception("no such category reference in this bct.");
        
        /*
         * check if category reference with the same name exists.
         */
        CategoryReference categoryReference = cstDAO.getCategoryReferenceByName(bctId, name);
        if (categoryReference!=null) throw new Exception("category with the same name already exists.");
        
        Category category = cstDAO.getCategoryByName(name);
        if (category==null) {
            category = new Category();
            category.setName(name);
            tagDAO.save(category);
        }
        
        catRef.getTheme().setTitle(name);
        
        /*
         * point catRef to the new category
         */
        catRef.setCategory(category);
        
        cstDAO.save(catRef);
    }//editCategoryReference()


    public void deleteCategoryReference(Long bctId, Long parentId, Long catRefId) throws Exception {
        BCT bct = bctDAO.getBCTById(bctId);
        if (bct==null) throw new Exception("no such bct.");
        
        CategoryReference parent = null;
        if (parentId==null) {
            parent = bct.getRootCategory();
        } else {
            parent = cstDAO.getCategoryReferenceById(parentId);
            if (parent==null) parent = bct.getRootCategory();
        }
        
        if (parent.getBct().getId().longValue()!=bct.getId().longValue()) throw new Exception("no such category reference in this bct.");
        
        CategoryReference catRef = cstDAO.getCategoryReferenceById(catRefId);
        if (catRef==null) throw new Exception("no such category reference.");
        
        if (catRef.getBct().getId().longValue()!=bct.getId().longValue()) throw new Exception("no such category reference in this bct.");
        
        parent.getChildren().remove(catRef);
        
        if (catRef.getParents().size()==0) {
            cstDAO.delete(catRef);
        }
        
        cstDAO.save(parent);
    }//deleteCategoryReference()


    public void relateTagToCategory(Long bctId, Long catRefId, Long tagRefId) throws Exception {
        BCT bct = bctDAO.getBCTById(bctId);
        if (bct==null) throw new Exception("no such bct.");
        
        CategoryReference catRef = cstDAO.getCategoryReferenceById(catRefId);
        if (catRef==null) throw new Exception("no such category reference.");
        
        TagReference tagRef = cstDAO.getTagReferenceById(tagRefId);
        if (tagRef==null) throw new Exception("no such tag reference.");
        
        if (catRef.getBct().getId().longValue()!=bct.getId().longValue()) throw new Exception("no such category reference in this bct.");
        
        if (tagRef.getBctId().longValue()!=bct.getId().longValue()) throw new Exception("no such tag reference in this bct.");
        
        catRef.getTags().add(tagRef);
        
        cstDAO.save(catRef);
    }//relateTagToCategory()


    public void deleteTagFromCategory(Long bctId, Long catRefId, Long tagRefId) throws Exception {
        BCT bct = bctDAO.getBCTById(bctId);
        if (bct==null) throw new Exception("no such bct.");
        
        CategoryReference catRef = cstDAO.getCategoryReferenceById(catRefId);
        if (catRef==null) throw new Exception("no such category reference.");
        
        TagReference tagRef = cstDAO.getTagReferenceById(tagRefId);
        if (tagRef==null) throw new Exception("no such tag reference.");
        
        if (catRef.getBct().getId().longValue()!=bct.getId().longValue()) throw new Exception("no such category reference in this bct.");
        
        if (tagRef.getBctId().longValue()!=bct.getId().longValue()) throw new Exception("no such tag reference in this bct.");
        
        catRef.getTags().remove(tagRef);
        
        cstDAO.save(catRef);
    }//deleteTagFromCategory()


    public Object[] getConcernsByTag(Long bctId, Long tagRefId, PageSetting setting) throws Exception {
        Object[] values = new Object[3];
        
        BCT bct = bctDAO.getBCTById(bctId);
        if (bct==null) throw new Exception("no such bct.");
        values[0] = bct;
        
        TagReference tagRef = cstDAO.getTagReferenceById(tagRefId);
        if (tagRef==null) throw new Exception("no such tag reference.");
        
        if (tagRef.getBctId().longValue()!=bct.getId().longValue()) throw new Exception("no such tag reference in this bct.");
        values[1] = tagRef;
        
        values[2] = cstDAO.getConcernsByTag(bctId, tagRefId, setting);
        
        return values;
    }//getConcernsByTag()


    public Object[] getConcernsByTags(Long bctId, int[] tagIds, PageSetting setting) throws Exception {
        Object[] values = new Object[3];
        
        BCT bct = bctDAO.getBCTById(bctId);
        if (bct==null) throw new Exception("no such bct.");
        values[0] = bct;
        
        List tags = new ArrayList(tagIds.length);
        for (int id : tagIds) {
            TagReference ref = cstDAO.getTagReferenceById(new Long(id));
            tags.add(ref);
        }
        values[1] = tags;
        
        values[2] = cstDAO.getConcernsByTags(bctId, tagIds, setting);
        
        return values;
    }//getConcernsByTags()


    public Collection getRealtedTags(Long bctId, Long categoryId, PageSetting setting) throws Exception {
        return cstDAO.getRealtedTags(bctId, categoryId, setting);
    }//getRealtedTags()


    public Collection getUnrelatedTags(Long bctId, Long categoryId, PageSetting setting) throws Exception {
        return cstDAO.getUnrelatedTags(bctId, categoryId, setting);
    }//getUnrelatedTags()


    public Collection getOrphanTags(Long bctId, PageSetting setting) throws Exception {
        return cstDAO.getOrphanTags(bctId, setting);
    }//getOrphanTags()


    public void saveSummary(Long bctId, Long themeId, String description, String summary, String tags) throws Exception {
        if (summary==null || "".equals(summary.trim())) throw new Exception("summary can't be empty.");
        
        BCT bct = bctDAO.getBCTById(bctId);
        if (bct==null) throw new Exception("no such bct.");
        
        Theme theme = cstDAO.getThemeById(themeId);
        if (theme==null) throw new Exception("no such theme.");
        
        theme.setDescription(description);
        theme.setSummary(summary);
        theme.setCreateTime(new Date());
        
        /*
         * clear votings if it has
         */
        Long oid = cstDAO.getInfoObjectIdByThemeId(themeId);
        if (oid!=null) {
            discussionDAO.deleteVotings(oid);
        }
        
        /*
         * clear tags if it has
         */
        theme.getTags().clear();
        
        for (String tagStr : tags.split(",")) {
            if (tagStr!=null && tagStr.trim().length()!=0) {
                tagStr = tagStr.trim();
                Tag tag = tagDAO.getTagByName(tagStr);
                if (tag==null) {
                    tag = tagDAO.addTag(tagStr, Tag.STATUS_OFFICIAL, true);
                }
                theme.getTags().add(tag);
            }
        }
        
        //cstDAO.save(theme);
    }//saveSummary()
    
    
    public List getThemes(BCT bct) throws Exception {
        List themes = new ArrayList(bct.getRootCategory().getChildren().size());
        
        for (CategoryReference ref : (Set<CategoryReference>) bct.getRootCategory().getChildren()) {
            themes.add(ref.getTheme());
        }//for
        
        return themes;
    }//getThemes()


    public InfoStructure publish(Long workflowId, Long bctId, String title) throws Exception {
        BCT bct = bctDAO.getBCTById(bctId);
        
        Date date = new Date();
        
        InfoStructure structure = new InfoStructure();
        structure.getDiscussion().setWorkflowId(workflowId);
        structure.setType(InfoStructure.TYPE_SARP_SD_CST);
        structure.setTitle(title);
        structure.setRespTime(date);
        structure.setCctId(bctId);
        discussionDAO.save(structure);
        
        for (CategoryReference ref : (Set<CategoryReference>) bct.getRootCategory().getChildren()) {
            ref.getCategory();
            ref.getBct();
            ref.getChildren();
            ref.getParents();
            ref.getTags();
            ref.getTheme();
            
            InfoObject obj = new InfoObject();
            obj.getDiscussion().setWorkflowId(workflowId);
            obj.setObject(ref);
            obj.setRespTime(date);
            discussionDAO.save(obj);
            
            structure.getInfoObjects().add(obj);
            
            cstDAO.publish(structure, obj, ref);
        }//for ref
        
        discussionDAO.save(structure);
        
        return structure;
    }//publish()


}//class CSTServiceImpl
