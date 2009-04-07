package org.pgist.sarp.cht;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.pgist.sarp.bct.BCTDAO;
import org.pgist.sarp.bct.TagReference;
import org.pgist.sarp.cst.CST;
import org.pgist.sarp.cst.CSTDAO;
import org.pgist.sarp.cst.CategoryReference;
import org.pgist.sarp.drt.InfoObject;
import org.pgist.system.SystemDAO;
import org.pgist.system.YesNoVoting;
import org.pgist.tagging.Category;
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
public class CHTServiceImpl implements CHTService {
    
    
    private BCTDAO bctDAO = null;

    private CSTDAO cstDAO = null;
    
    private CHTDAO chtDAO = null;
    
    private TagDAO tagDAO = null;
    
    private SystemDAO systemDAO = null;

    private JythonAPI jythonAPI = null;
    
    
    public void setCstDAO(CSTDAO cstDAO) {
        this.cstDAO = cstDAO;
    }


    public void setBctDAO(BCTDAO bctDAO) {
        this.bctDAO = bctDAO;
    }


    public void setChtDAO(CHTDAO chtDAO) {
        this.chtDAO = chtDAO;
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


    public Category getCategoryByName(String name) throws Exception {
        return cstDAO.getCategoryByName(name);
    }//getCategoryByName()
    
    
    public Collection getRealtedTags(Long cstId, Long categoryId, PageSetting setting) throws Exception {
        return cstDAO.getRealtedTags(cstId, categoryId, setting);
    }//getRealtedTags()


    public InfoObject publish(Long chtId, String title) throws Exception {
        CHT cht = chtDAO.getCHTById(chtId);
        cht.getId();
        cht.getCategories();
        cht.getFavorites();
        cht.getInstruction();
        cht.getName();
        cht.getPurpose();
        
        InfoObject infoObject = new InfoObject();
        infoObject.setTitle(title);
        infoObject.setTarget(cht);
        
        cstDAO.save(infoObject);
        
        return infoObject;
    }//publish()


	@Override
	public CHT createCHT(Long workflowId, Long cstId, String name, String purpose, String instruction) throws Exception {
		CST cst = cstDAO.getCSTById(cstId);
		if (cst==null) throw new Exception("can not find the specified object");
		
		CHT cht = new CHT();
		cht.setCst(cst);
		cht.setClosed(false);
		cht.setName(name);
		cht.setInstruction(instruction);
		cht.setPurpose(purpose);
		
		chtDAO.save(cht);
		
		return cht;
	}//createCHT()


	@Override
	public void toggleCHT(Long chtId, boolean closed) throws Exception {
		CHT cht = chtDAO.getCHTById(chtId);
		cht.setClosed(closed);
		chtDAO.save(cht);
	}//toggleCST()


	@Override
	public CHT getCHTById(Long chtId) throws Exception {
		return (CHT) cstDAO.load(CHT.class, chtId);
	}//getCSTById()


    @Override
    public CategoryReference setRootCatReference(CHT cht, User user) throws Exception {
        CategoryReference root2 = cht.getCategories().get(user.getId());
        if (root2!=null) return root2;
        
        root2 = cht.getCats().get(user.getId());
        if (root2!=null) return root2;
        
        //copy winner
        CategoryReference root1 = cht.getCst().getWinnerCategory().getCatRef();
        
        root2 = new CategoryReference(root1);
        root2.setUser(user);
        root2.setCstId(cht.getId());
        
        chtDAO.save(root2);
        cht.getCats().put(user.getId(), root2);
        
        CategoryReference rootIgnore = new CategoryReference("root-ignore", cht.getCst().getId());
        
        Queue<CategoryReference> queue1 = new LinkedList<CategoryReference>();
        queue1.offer(root1);
        
        Queue<CategoryReference> queue2 = new LinkedList<CategoryReference>();
        queue2.offer(rootIgnore);
        
        while (!queue1.isEmpty()) {
            CategoryReference parent1 = queue1.poll();
            CategoryReference parent2 = queue2.poll();
            
            for (CategoryReference one : parent1.getChildren()) {
                CategoryReference two = new CategoryReference(one);
                two.setCstId(cht.getId());
                two.setUser(user);
                two.getParents().add(parent2);
                parent2.getChildren().add(two);
                chtDAO.save(two);
                queue1.offer(one);
                queue2.offer(two);
            }
        }
        
        cht.getIgnores().put(user.getId(), rootIgnore);
        
        chtDAO.save(cht);
        
        return root2;
    }//setRootCatReference()


    @Override
    public List<User> getOtherUsers(CHT cht) throws Exception {
        return chtDAO.getOtherUsers(cht);
    }//getOtherUsers()


    @Override
    public Collection<CHTComment> getComments(Long catRefId, PageSetting setting) throws Exception {
        return chtDAO.getComments(catRefId, setting);
    }//getComments()


    @Override
    public CHTComment createComment(Long workflowId, Long catRefId, String title, String content, boolean emailNotify) throws Exception {
        CategoryReference catRef = cstDAO.getCategoryReferenceById(catRefId);
        
        if (catRef==null) throw new Exception("can't find the specified CategoryReference with id "+catRefId);
        
        CHTComment comment = new CHTComment();
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
        
        systemDAO.setVoting(YesNoVoting.TYPE_SARP_CHT_COMMENT, comment.getId(), true);
        
        return comment;
    }//createComment()


    @Override
    public CHTComment getCommentById(Long cid) throws Exception {
        return (CHTComment) chtDAO.load(CHTComment.class, cid);
    }//getCommentById()


    @Override
    public CHTComment setVotingOnComment(Long cid, boolean agree) throws Exception {
        CHTComment comment = (CHTComment) cstDAO.load(CHTComment.class, cid);
        if (comment==null) throw new Exception("can't find the specified Comment with id "+cid);
        
        systemDAO.setVoting(YesNoVoting.TYPE_SARP_CHT_COMMENT, cid, agree);
        
        chtDAO.increaseVoting(comment, agree);
        
        return comment;
    }//setVotingOnComment()


    @Override
    public void deleteComment(CHTComment comment) throws Exception {
        comment.setDeleted(true);
        cstDAO.save(comment);
    }//deleteComment()


    @Override
    public void publish(Long chtId) throws Exception {
        CHT cht = chtDAO.getCHTById(chtId);
        CategoryReference root = cht.getCats().get(WebUtils.currentUserId());
        cht.getCats().put(WebUtils.currentUserId(), null);
        cht.getCategories().put(WebUtils.currentUserId(), root);
        chtDAO.save(cht);
    } //publish()


    @Override
    public void setClearCHTWinner(Long chtId) throws Exception {
        CHT cht = chtDAO.getCHTById(chtId);
        cht.setWinner(null);
        cht.setWinnerCategory(null);
        chtDAO.save(cht);
    } //setClearCHTWinner()


    @Override
    public CategoryReference moveCategoryReference(CHT cht, Long catRefId, int direction) throws Exception {
        CategoryReference catRef = chtDAO.getCategoryReferenceById(catRefId);
        CategoryReference parent = catRef.getParents().iterator().next();
        CategoryReference grandpa = null;
        
        if (parent.getParents().size()>0) {
            grandpa = parent.getParents().iterator().next();
        }
        
        CategoryReference root = cht.getCats().get(WebUtils.currentUserId());
        if (root==null) root = cht.getCategories().get(WebUtils.currentUserId());
        
        int index = parent.getChildren().indexOf(catRef);
        
        switch (direction) {
            case 0:
                if (index==0) throw new Exception("can't move up");
                parent.getChildren().add(index-1, parent.getChildren().remove(index));
                break;
            case 1:
                if (index>=parent.getChildren().size()-1) throw new Exception("can't move down");
                parent.getChildren().add(index+1, parent.getChildren().remove(index));
                break;
            case 2:
                if (grandpa==null) throw new Exception("can't move left");
                int n = grandpa.getChildren().indexOf(parent);
                grandpa.getChildren().add(n+1, parent.getChildren().remove(index));
                break;
            case 3:
                if (index==0) throw new Exception("can't move right");
                parent.getChildren().get(index-1).getChildren().add(parent.getChildren().remove(index));
                break;
            case 4:
                if (!"root-ignore".equals(parent.getCategory().getName())) {
                    parent.getChildren().remove(catRef);
                    cht.getIgnores().get(WebUtils.currentUserId()).getChildren().add(catRef);
                }
            case 5:
                if ("root-ignore".equals(parent.getCategory().getName())) {
                    root.getChildren().add(catRef);
                    parent.getChildren().remove(catRef);
                }
                break;
        }
        
        chtDAO.save(parent);
        chtDAO.save(cht);
        
        return root;
    } //moveCategoryReference()


    @Override
    public void setClusteredCategory(final Long chtId) throws Exception {
        final CHT cht = chtDAO.getCHTById(chtId);
        
        class CategoryFactory {
            private CategoryReference root = new CategoryReference("root", cht.getCst().getId());
            private List<CategoryPath> paths = new ArrayList<CategoryPath>();
            private Map<String, CategoryReference> catMap;
            
            public CategoryFactory(Map<String, CategoryReference> catMap) {
                this.catMap = catMap;
            }
            
            public CategoryReference createCategoryReference(String name) {
                CategoryReference catRef = new CategoryReference(name, cht.getCst().getId());
                catRef.getTags().addAll(catMap.get(name).getTags());
                catRef.setCstId(chtId);
                return catRef;
            }
            
            public TagReference createTagReference(String name) {
                TagReference tagRef = new TagReference(name, cht.getCst().getBct().getId());
                tagRef.setBctId(cht.getCst().getId());
                return tagRef;
            }
            
            public CategoryPath createCategoryPath() {
                CategoryPath path = new CategoryPath();
                path.setCht(cht);
                return path;
            }
            
            public void addCategory(CategoryReference catRef) {
                root.getChildren().add(catRef);
            }
            
            public CategoryReference getRoot() {
                return root;
            }
            
            public void addPath(CategoryPath path) throws Exception {
                paths.add(path);
            }
            
            public List<CategoryPath> getPaths() {
                return paths;
            }
        };
        
        List<Long> userIdList = new ArrayList<Long>();
        List<CategoryReference> catList = new ArrayList<CategoryReference>();
        for (Map.Entry<Long, CategoryReference> entry : cht.getCategories().entrySet()) {
            userIdList.add(entry.getKey());
            catList.add(entry.getValue());
        }
        
        Map<String, CategoryReference> catMap = new HashMap<String, CategoryReference>();
        Queue<CategoryReference> queue = new LinkedList<CategoryReference>();
        for (CategoryReference root : catList) {
            queue.addAll(root.getChildren());
        }
        while (!queue.isEmpty()) {
            CategoryReference child = queue.poll();
            catMap.put(child.getCategory().getName(), child);
            for (CategoryReference one : child.getChildren()) {
                queue.offer(one);
            }
        }
        
        CategoryFactory factory = new CategoryFactory(catMap);
        
        try {
            PythonInterpreter interpreter = jythonAPI.getInterpreter();
            interpreter.set("userIdList", userIdList);
            interpreter.set("catList", catList);
            interpreter.set("factory", factory);
            jythonAPI.run(interpreter, "CHT_Cluster.py");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        
        for (CategoryPath path : factory.getPaths()) {
            path.genTitle();
            chtDAO.save(path);
        }
        
        CategoryReference winner = factory.getRoot();
        winner.setCstId(chtId);
        chtDAO.save(winner);
        
        User moderator = systemDAO.getAdmin();
        winner.setCstId(chtId);
        
        cht.setWinner(moderator);
        cht.setWinnerCategory(winner);
        
        //persist
        chtDAO.save(winner);
        chtDAO.save(cht);
    } //setClusteredCategory()


    @Override
    public List<CategoryPath> getPathsByChtId(Long chtId, String orderby) {
        return chtDAO.getPathsByChtId(chtId, orderby);
    } //getPathsByChtId()


    @Override
    public CategoryPath getPathById(Long pathId) throws Exception {
        return (CategoryPath) chtDAO.load(CategoryPath.class, pathId);
    } //getPathById()


    @Override
    public CategoryPath setVotingOnPath(Long pathId, boolean agree) throws Exception {
        CategoryPath path = (CategoryPath) chtDAO.load(CategoryPath.class, pathId);
        if (path==null) throw new Exception("can't find the specified CategoryPath with id "+pathId);
        
        systemDAO.setVoting(YesNoVoting.TYPE_SARP_CHT_PATH, pathId, agree);
        
        chtDAO.increaseVoting(path, agree);
        
        return path;
    } //setVotingOnPath()


    @Override
    public CategoryPath createPath(Long chtId, String pathIds) throws Exception {
        CHT cht = chtDAO.getCHTById(chtId);
        CategoryPath path = new CategoryPath();
        path.setCht(cht);
        path.setFrequency(0);
        path.setNumAgree(0);
        path.setNumVote(0);
        
        String[] ids = pathIds.split(",");
        
        for (String id : ids) {
            if (id==null || id.trim().length()==0) continue;
            
            CategoryReference catRef = chtDAO.getCategoryReferenceById(new Long(id));
            path.getCategories().add(catRef);
        }
        path.genTitle();
        
        if (!chtDAO.checkPath(chtId, path.getTitle())) {
            chtDAO.save(path);
        } else {
            throw new Exception("path already exists!");
        }
        
        return path;
    } //createPath()


    @Override
    public Set<User> getThreadUsers(Long catRefId) throws Exception {
        return chtDAO.getThreadUsers(catRefId);
    }


    @Override
    public List<User> getPathUsersByPathId(Long pathId) throws Exception {
        CategoryPath path = getPathById(pathId);
        StringBuilder sb = new StringBuilder();
        
        for (Long id : path.getUsers()) {
            if (sb.length()>0) sb.append(",");
            sb.append(id);
        }
        
        List<User> users = null;
        
        if (sb.length()>0) {
            users = chtDAO.getPathUsersByPathIds(sb.toString());
        } else {
            users = new ArrayList<User>();
        }
        
        return users;
    }


}//class CHTServiceImpl
