package org.pgist.sarp.cst;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.pgist.sarp.bct.BCT;
import org.pgist.sarp.bct.BCTDAO;
import org.pgist.sarp.bct.TagReference;
import org.pgist.sarp.drt.InfoObject;
import org.pgist.system.SystemDAO;
import org.pgist.system.YesNoVoting;
import org.pgist.tagging.Category;
import org.pgist.tagging.Tag;
import org.pgist.tagging.TagDAO;
import org.pgist.users.User;
import org.pgist.util.JythonAPI;
import org.pgist.util.PageSetting;
import org.pgist.util.WebUtils;
import org.python.util.PythonInterpreter;


/**
 * 
 * @author kenny
 *
 */
public class CSTServiceImpl implements CSTService {
    
    
    private BCTDAO bctDAO = null;

    private CSTDAO cstDAO = null;
    
    private TagDAO tagDAO = null;
    
    private SystemDAO systemDAO = null;
    
    private JythonAPI jythonAPI = null;

    
    public void setCstDAO(CSTDAO cstDAO) {
        this.cstDAO = cstDAO;
    }


    public void setBctDAO(BCTDAO bctDAO) {
        this.bctDAO = bctDAO;
    }


    public void setTagDAO(TagDAO tagDAO) {
        this.tagDAO = tagDAO;
    }


    public void setSystemDAO(SystemDAO systemDAO) {
        this.systemDAO = systemDAO;
    }
    
    
    public void setJythonAPI(JythonAPI jythonAPI) {
        this.jythonAPI = jythonAPI;
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

    public CategoryReference getCategoryReferenceByName(Long cstId,String name) throws Exception {
        return cstDAO.getCategoryReferenceByName(cstId, name);
    }//getCategoryReferenceById()

    public Category getCategoryByName(String name) throws Exception {
        return cstDAO.getCategoryByName(name);
    }//getCategoryByName()
    
    
    public CategoryReference addCategoryReference(Long cstId, Long parentId, String name) throws Exception {
        CST cst = cstDAO.getCSTById(cstId);
        if (cst==null) throw new Exception("no such cst.");
        
        boolean published = false;
        
        CategoryReference parent = null;
        if (parentId==null || parentId<1) {
            /*
             * Use root category as the parent category
             */
            parent = cst.getCategories().get(WebUtils.currentUserId());
            if (parent!=null) {
                published = true;
            } else {
                parent = cst.getCats().get(WebUtils.currentUserId());
            }
        } else {
            /*
             * Get the parent category
             */
            parent = cstDAO.getCategoryReferenceById(parentId);
            
            /*
             * If parent category not exists, still use the root category
             */
            if (parent==null) parent = cst.getCategories().get(WebUtils.currentUserId());
            if (parent!=null) {
                published = true;
            } else { 
                parent = cst.getCats().get(WebUtils.currentUserId());
            }
        }
        
        /*
         * check duplication
         */
        for (CategoryReference catRef : parent.getChildren()) {
            if (name.equals(catRef.getCategory().getName())) {
                throw new Exception("category "+name+" exists.");
            }
        }
        
        /*
         * check if the parent category is in the specified bct
         */
        if (!parent.getCstId().equals(cst.getId())) throw new Exception("no such category reference in this cst.");
        
        /*
         * get the child category reference
         */
        CategoryReference categoryReference = cstDAO.getCategoryReferenceByName(cstId, name);
        CategoryReference root = categoryReference;
        while (root!=null) {
            if (root.getParents().size()>0) {
                root = (CategoryReference) root.getParents().iterator().next();
            } else {
                break;
            }
        }
        
        if (root!=null) {
            CategoryReference catRef = cst.getCategories().get(WebUtils.currentUserId());
            if (catRef==null) {
                catRef = cst.getCats().get(WebUtils.currentUserId());
            }
            if (!(root.getId().equals(catRef.getId()))) {
                categoryReference = null;
            }
        }
        
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
            categoryReference = new CategoryReference("root", cstId);
            User user = cstDAO.getUserById(WebUtils.currentUserId());
            categoryReference.setUser(user);
            categoryReference.setCategory(category);
            categoryReference.setCstId(cstId);
            
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
            //parent = bct.getRootCategory();
        } else {
            /*
             * Get the parent category
             */
            //parent = cstDAO.getCategoryReferenceById(parentId);
            
            /*
             * If parent category not exists, still use the root category
             */
            //if (parent==null) parent = bct.getRootCategory();
        }
        
        /*
         * check if the parent category is in the specified bct
         */
        //if (!parent.getBct().getId().equals(bct.getId())) throw new Exception("no such category reference in this bct.");
        
        /*
         * get the category reference
         */
        CategoryReference categoryReference = cstDAO.getCategoryReferenceById(categoryId);
        
        if (categoryReference==null) throw new Exception("no such category reference");
        //if (categoryReference.getBct().getId()!=bctId) throw new Exception("no such category reference in this bct.");
        
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
            //parent = bct.getRootCategory();
        } else {
            /*
             * Get the parent category
             */
            //parent = cstDAO.getCategoryReferenceById(parentId);
            
            /*
             * If parent category not exists, still use the root category
             */
            //if (parent==null) parent = bct.getRootCategory();
        }
        
        /*
         * check if the parent category is in the specified bct
         */
        //if (!parent.getBct().getId().equals(bct.getId())) throw new Exception("no such category reference in this bct.");
        
        /*
         * get the category reference
         */
        CategoryReference categoryReference = cstDAO.getCategoryReferenceById(categoryId);
        
        if (categoryReference==null) throw new Exception("no such category reference");
        //if (categoryReference.getBct().getId()!=bctId) throw new Exception("no such category reference in this bct.");
        
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
            newCat = new CategoryReference("root", null);
            newCat.setCategory(category);
            newCat.getTags().addAll(categoryReference.getTags());
            cstDAO.save(newCat);
        }
        
        /*
         * establish parent-child relationship
         */
        parent.getChildren().add(newCat);
        
        cstDAO.save(parent);
        
        return newCat;
    }//duplicateCategoryReference()
    
    
    public void moveCategoryReference(Long cstId, Long parent0Id, Long parent1Id, Long categoryId) throws Exception {
        CST cst = cstDAO.getCSTById(cstId);
        if (cst==null) throw new Exception("no such cst.");
        
        CategoryReference parent0 = null;
        
        if (parent0Id==null) {
            /*
             * Use root category as the parent category
             */
            parent0 = cst.getCategories().get(WebUtils.currentUserId());
            if (parent0==null) {
                parent0 = cst.getCats().get(WebUtils.currentUserId());
            }
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
            parent1 = cst.getCategories().get(WebUtils.currentUserId());
            if (parent1==null) {
                parent1 = cst.getCats().get(WebUtils.currentUserId());
            }
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
         * check if the parent category is in the specified cst
         */
        if (!(parent0.getCstId().equals(cstId))) throw new Exception("no such category reference in this cst.");
        if (!(parent1.getCstId().equals(cstId))) throw new Exception("no such category reference in this cst.");
        if (parent0==parent1) throw new Exception("no need to move");
        
        /*
         * get the category reference
         */
        CategoryReference categoryReference = cstDAO.getCategoryReferenceById(categoryId);
        
        if (categoryReference==null) throw new Exception("no such category reference");
        if (!(categoryReference.getCstId().equals(cstId))) throw new Exception("no such category reference in this cst.");
        
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
    }//moveCategoryReference()
    
    
    public void editCategoryReference(Long cstId, Long catRefId, String name) throws Exception {
        CST cst = cstDAO.getCSTById(cstId);
        if (cst==null) throw new Exception("no such cst.");
        
        CategoryReference catRef = cstDAO.getCategoryReferenceById(catRefId);
        if (catRef==null) throw new Exception("no such category reference.");
        
        if (catRef.getCstId().longValue()!=cstId.longValue()) throw new Exception("no such category reference in this cst.");
        
        /*
         * check if category reference with the same name exists.
         */
        CategoryReference categoryReference = cstDAO.getCategoryReferenceByName(cstId, name);
        if (categoryReference!=null) throw new Exception("category with the same name already exists.");
        
        Category category = cstDAO.getCategoryByName(name);
        if (category==null) {
            category = new Category();
            category.setName(name);
            tagDAO.save(category);
        }
        
        /*
         * point catRef to the new category
         */
        catRef.setCategory(category);
        
        cstDAO.save(catRef);
    }//editCategoryReference()


    public void deleteCategoryReference(Long cstId, Long parentId, Long catRefId) throws Exception {
        CST cst = cstDAO.getCSTById(cstId);
        if (cst==null) throw new Exception("no such cst.");
        
        CategoryReference parent = null;
        if (parentId==null) {
            parent = cst.getCategories().get(WebUtils.currentUserId());
            if (parent==null) parent = cst.getCats().get(WebUtils.currentUserId());
        } else {
            parent = cstDAO.getCategoryReferenceById(parentId);
            if (parent==null) parent = cst.getCategories().get(WebUtils.currentUserId());
            if (parent==null) parent = cst.getCats().get(WebUtils.currentUserId());
        }
        
        if (parent.getCstId().longValue()!=cstId.longValue()) throw new Exception("no such category reference in this cst.");
        
        CategoryReference catRef = cstDAO.getCategoryReferenceById(catRefId);
        if (catRef==null) throw new Exception("no such category reference.");
        
        if (catRef.getCstId().longValue()!=cstId.longValue()) throw new Exception("no such category reference in this cst.");
        
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
        
        //if (catRef.getBct().getId().longValue()!=bct.getId().longValue()) throw new Exception("no such category reference in this bct.");
        
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
        
        //if (catRef.getBct().getId().longValue()!=bct.getId().longValue()) throw new Exception("no such category reference in this bct.");
        
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


    public Collection getRealtedTags(Long cstId, Long categoryId, PageSetting setting) throws Exception {
        return cstDAO.getRealtedTags(cstId, categoryId, setting);
    }//getRealtedTags()


    public Collection getUnrelatedTags(Long cstId, Long categoryId, PageSetting setting) throws Exception {
        return cstDAO.getUnrelatedTags(cstId, categoryId, setting);
    }//getUnrelatedTags()


    public Collection getOrphanTags(Long cstId, PageSetting setting, boolean modtool) throws Exception {
        return cstDAO.getOrphanTags(cstId, setting, modtool);
    }//getOrphanTags()


    public Collection getOrphanTags(Long cstId, Long categoryId, PageSetting setting) throws Exception {
        return cstDAO.getOrphanTags(cstId, categoryId, setting);
    }//getOrphanTags()


    public void saveSummary(Long catRefId, String summary) throws Exception {
        CategoryReference catReference = cstDAO.getCategoryReferenceById(catRefId);
        catReference.setTheme(summary.trim());
        cstDAO.save(catReference);
    }//saveSummary()
    
    
    public List getThemes(BCT bct) throws Exception {
    	return null;
    	/*
        List themes = new ArrayList(bct.getRootCategory().getChildren().size());
        
        //for (CategoryReference ref : (Set<CategoryReference>) bct.getRootCategory().getChildren()) {
        //    themes.add(ref.getTheme());
        //}//for
        
        return themes;
        */
    }//getThemes()


    public InfoObject publish(Long cstId, String title) throws Exception {
        CST cst = cstDAO.getCSTById(cstId);
        cst.getId();
        cst.getCategories();
        cst.getFavorites();
        cst.getInstruction();
        cst.getName();
        cst.getPurpose();
        cst.getCats();
        cst.getWinner();
        cst.getWinnerCategory();
        
        InfoObject infoObject = new InfoObject();
        infoObject.setTitle(title);
        infoObject.setTarget(cst);
        
        cstDAO.save(infoObject);
        
        return infoObject;
    }//publish()


	@Override
	public CST createCST(Long workflowId, Long bctId, String name, String purpose, String instruction) throws Exception {
		BCT bct = bctDAO.getBCTById(bctId);
		if (bct==null) throw new Exception("can not find the specified object");
		
		CST cst = new CST();
		cst.setBct(bct);
		cst.setClosed(false);
		cst.setName(name);
		cst.setInstruction(instruction);
		cst.setPurpose(purpose);
		
		cstDAO.save(cst);
		
		return cst;
	}//createCST()


	@Override
	public void toggleCST(Long cstId, boolean closed) throws Exception {
		CST cst = cstDAO.getCSTById(cstId);
		cst.setClosed(closed);
		cstDAO.save(cst);
	}//toggleCST()


	@Override
	public CST getCSTById(Long cstId) throws Exception {
		return (CST) cstDAO.load(CST.class, cstId);
	}//getCSTById()


    @Override
    public CategoryReference setRootCatReference(CST cst, User user) throws Exception {
        CategoryReference catref = new CategoryReference("root", cst.getId());
        catref.setCstId(cst.getId());
        catref.setUser(user);
        cstDAO.save(catref);
        
        cst.getCats().put(user.getId(), catref);
        
        return catref;
    }//setRootCatReference()


    @Override
    public List<User> getOtherUsers(CST cst) throws Exception {
        return cstDAO.getOtherUsers(cst);
    }//getOtherUsers()


    @Override
    public Collection<CSTComment> getComments(Long catRefId, PageSetting setting) throws Exception {
        return cstDAO.getComments(catRefId, setting);
    }//getComments()


    @Override
    public CSTComment createComment(Long workflowId, Long catRefId, String title, String content, boolean emailNotify) throws Exception {
        CategoryReference catRef = cstDAO.getCategoryReferenceById(catRefId);
        
        if (catRef==null) throw new Exception("can't find the specified CategoryReference with id "+catRefId);
        
        CSTComment comment = new CSTComment();
        comment.setWorkflowId(workflowId);
        comment.setAuthor(cstDAO.getUserById(WebUtils.currentUserId()));
        comment.setCatRef(catRef);
        comment.setTitle(title);
        comment.setContent(content);
        comment.setCreateTime(new Date());
        comment.setNumAgree(1);
        comment.setNumVote(1);
        comment.setEmailNotify(emailNotify);
        
        cstDAO.save(comment);
        
        systemDAO.setVoting(YesNoVoting.TYPE_SARP_CST_COMMENT, comment.getId(), true);
        
        return comment;
    }//createComment()


    @Override
    public CSTComment getCommentById(Long cid) throws Exception {
        return (CSTComment) cstDAO.load(CSTComment.class, cid);
    }//getCommentById()


    @Override
    public CSTComment setVotingOnComment(Long cid, boolean agree) throws Exception {
        CSTComment comment = (CSTComment) cstDAO.load(CSTComment.class, cid);
        if (comment==null) throw new Exception("can't find the specified Comment with id "+cid);
        
        systemDAO.setVoting(YesNoVoting.TYPE_SARP_CST_COMMENT, cid, agree);
        
        cstDAO.increaseVoting(comment, agree);
        
        return comment;
    }//setVotingOnComment()


    @Override
    public void deleteComment(CSTComment comment) throws Exception {
        comment.setDeleted(true);
        cstDAO.save(comment);
    }//deleteComment()


    @Override
    public void setClearCSTWinner(Long cstId) throws Exception {
        CST cst = cstDAO.getCSTById(cstId);
        cst.setWinner(null);
        cst.setWinnerCategory(null);
        cstDAO.save(cst);
    }//setClearCSTWinner()


    @Override
    public void publish(Long cstId) throws Exception {
        CST cst = cstDAO.getCSTById(cstId);
        CategoryReference root = cst.getCats().get(WebUtils.currentUserId());
        cst.getCats().put(WebUtils.currentUserId(), null);
        cst.getCategories().put(WebUtils.currentUserId(), root);
        cstDAO.save(cst);
    }//publish()


    @Override
    public void setClusteredCategory(final Long cstId) throws Exception {
        final CST cst = cstDAO.getCSTById(cstId);
        final Map<String, CategoryReference> catsMap = new HashMap<String, CategoryReference>();
        final Map<String, TagReference> tagsMap = new HashMap<String, TagReference>();
        
        class CategoryFactory {
            private CategoryInfo root;
            public CategoryInfo createCategoryInfo(String name) throws Exception {
                CategoryReference catRef = catsMap.get(name);
                if (catRef==null) {
                    catRef = cstDAO.getCategoryReferenceByName(cstId, name);
                    if (catRef==null) {
                        catRef = new CategoryReference(name, cstId);
                    } else {
                        Category cat = cstDAO.getCategoryByName(name);
                        if (cat==null) {
                            catRef = new CategoryReference(name, cstId);
                        } else {
                            catRef = new CategoryReference(cat, cstId);
                        }
                    }
                    catsMap.put(name, catRef);
                }
                CategoryInfo info = new CategoryInfo();
                info.setCatRef(catRef);
                return info;
            }
            public TagReference createTagReference(String name) throws Exception {
                TagReference tagRef = tagsMap.get(name);
                if (tagRef==null) {
                    tagRef = cstDAO.getTagReferenceByName(cst.getBct().getId(), name);
                    if (tagRef==null) {
                        Tag tag = cstDAO.getTagByName(name);
                        if (tag==null) {
                            tagRef = new TagReference(name, cst.getBct().getId());
                        } else {
                            tagRef = new TagReference(tag, cst.getBct().getId());
                        }
                    }
                    tagsMap.put(name, tagRef);
                }
                return tagRef;
            }
            public void setResult(CategoryInfo root) {
                this.root = root;
            }
            public CategoryInfo getRoot() {
                return root;
            }
        };
        
        CategoryFactory factory = new CategoryFactory();
        
        List<Long> userIdList = new ArrayList<Long>();
        List<CategoryReference> catList = new ArrayList<CategoryReference>();
        for (Map.Entry<Long, CategoryReference> entry : cst.getCategories().entrySet()) {
            userIdList.add(entry.getKey());
            catList.add(entry.getValue());
        }
        
        try {
            PythonInterpreter interpreter = jythonAPI.getInterpreter();
            interpreter.set("userIdList", userIdList);
            interpreter.set("catList", catList);
            interpreter.set("factory", factory);
            jythonAPI.run(interpreter, "TCT_Cluster.py");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        
        CategoryInfo winner = factory.getRoot();
        
        User moderator = systemDAO.getAdmin();
        winner.getCatRef().setCstId(cstId);
        
        cst.setWinner(moderator);
        cst.setWinnerCategory(winner);
        
        //persist
        cstDAO.save(winner);
        cstDAO.save(cst);
    } //setClusteredCategory()


    @Override
    public CategoryInfo getCategoryInfoById(Long categoryId) throws Exception {
        return (CategoryInfo) cstDAO.load(CategoryInfo.class, categoryId);
    }


    @Override
    public Set<User> getThreadUsers(Long catRefId) throws Exception {
        return cstDAO.getThreadUsers(catRefId);
    }


} //class CSTServiceImpl
