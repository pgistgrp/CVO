package org.pgist.sarp.vtt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.pgist.sarp.bct.BCTDAO;
import org.pgist.sarp.bct.TagReference;
import org.pgist.sarp.cht.CHT;
import org.pgist.sarp.cht.CHTDAO;
import org.pgist.sarp.cht.CategoryPath;
import org.pgist.sarp.cst.CSTDAO;
import org.pgist.sarp.cst.CategoryReference;
import org.pgist.sarp.drt.InfoObject;
import org.pgist.system.SystemDAO;
import org.pgist.system.YesNoVoting;
import org.pgist.tagging.TagDAO;
import org.pgist.users.User;
import org.pgist.util.PageSetting;
import org.pgist.util.WebUtils;
import org.python.util.PythonInterpreter;


/**
 * 
 * @author kenny
 *
 */
public class VTTServiceImpl implements VTTService {
    
    
    private BCTDAO bctDAO = null;

    private CSTDAO cstDAO = null;
    
    private CHTDAO chtDAO = null;
    
    private VTTDAO vttDAO = null;
    
    private TagDAO tagDAO = null;
    
    private SystemDAO systemDAO = null;

    
    public void setCstDAO(CSTDAO cstDAO) {
        this.cstDAO = cstDAO;
    }


    public void setBctDAO(BCTDAO bctDAO) {
        this.bctDAO = bctDAO;
    }


    public void setChtDAO(CHTDAO chtDAO) {
        this.chtDAO = chtDAO;
    }


    public VTTDAO getVttDAO() {
        return vttDAO;
    }


    public void setVttDAO(VTTDAO vttDAO) {
        this.vttDAO = vttDAO;
    }


    public void setTagDAO(TagDAO tagDAO) {
        this.tagDAO = tagDAO;
    }


    public void setSystemDAO(SystemDAO systemDAO) {
        this.systemDAO = systemDAO;
    }


    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void toggleVTT(Long vttId, boolean closed) throws Exception {
        VTT vtt = vttDAO.getVTTById(vttId);
        vtt.setClosed(closed);
        vttDAO.save(vtt);
    } //toggleVTT()
    
    
    public InfoObject publish(Long vttId, String title) throws Exception {
        VTT vtt = vttDAO.getVTTById(vttId);
        vtt.getId();
        vtt.getCategories();
        vtt.getFavorites();
        vtt.getInstruction();
        vtt.getName();
        vtt.getPurpose();
        vtt.getCats();
        vtt.getWinner();
        vtt.getWinnerCategory();
        
        InfoObject infoObject = new InfoObject();
        infoObject.setTitle(title);
        infoObject.setTarget(vtt);
        
        cstDAO.save(infoObject);
        
        return infoObject;
    } //publish()
    
    
    public VTT createVTT(Long id, Long chtId, String name, String purpose, String instruction) throws Exception {
        CHT cht = chtDAO.getCHTById(chtId);
        if (cht==null) throw new Exception("can not find the specified object");
        
        VTT vtt = new VTT();
        vtt.setCht(cht);
        vtt.setClosed(false);
        vtt.setName(name);
        vtt.setInstruction(instruction);
        vtt.setPurpose(purpose);
        
        vttDAO.save(vtt);
        
        return vtt;
    } //createVTT()


    public List<User> getOtherUsers(VTT vtt) throws Exception {
        return vttDAO.getOtherUsers(vtt);
    } //getOtherUsers()


    public VTT getVTTById(Long vttId) throws Exception {
        return (VTT) vttDAO.load(VTT.class, vttId);
    } //getVTTById()


    public CategoryReference setRootCatReference(VTT vtt, User user) throws Exception {
        CategoryReference root2 = vtt.getCategories().get(user.getId());
        if (root2!=null) return root2;
        
        root2 = vtt.getCats().get(user.getId());
        if (root2!=null) return root2;
        
        //copy winner
        CategoryReference root1 = vtt.getCht().getWinnerCategory();
        
        Queue<CategoryReference> queue1 = new LinkedList<CategoryReference>();
        queue1.offer(root1);
        
        Queue<CategoryReference> queue2 = new LinkedList<CategoryReference>();
        root2 = new CategoryReference(root1);
        root2.setUser(user);
        vttDAO.save(root2);
        root2.setCstId(vtt.getId());
        queue2.offer(root2);
        
        CategoryValue catValue = new CategoryValue(root2);
        vttDAO.save(catValue);
        
        while (!queue1.isEmpty()) {
            CategoryReference parent1 = queue1.poll();
            CategoryReference parent2 = queue2.poll();
            
            for (CategoryReference one : parent1.getChildren()) {
                CategoryReference two = new CategoryReference(one);
                two.setUser(user);
                two.setCstId(vtt.getId());
                two.getParents().add(parent2);
                parent2.getChildren().add(two);
                vttDAO.save(two);
                
                catValue = new CategoryValue(two);
                vttDAO.save(catValue);
                
                queue1.offer(one);
                queue2.offer(two);
            }
        }
        
        vttDAO.save(root2);
        
        vtt.getCats().put(user.getId(), root2);
        
        vttDAO.save(vtt);
        
        return root2;
    } //setRootCatReference()


    public void setClearVTTWinner(Long vttId) throws Exception {
        VTT vtt = vttDAO.getVTTById(vttId);
        vtt.setWinner(null);
        vtt.setWinnerCategory(null);
        vttDAO.save(vtt);
    } //setClearVTTWinner()


    public Collection<VTTComment> getComments(Long catRefId, PageSetting setting) throws Exception {
        return vttDAO.getComments(catRefId, setting);
    } //getComments()


    public VTTComment createComment(Long catRefId, String title, String content, boolean emailNotify) throws Exception {
        CategoryReference catRef = cstDAO.getCategoryReferenceById(catRefId);
        
        if (catRef==null) throw new Exception("can't find the specified CategoryReference with id "+catRefId);
        
        VTTComment comment = new VTTComment();
        comment.setAuthor(cstDAO.getUserById(WebUtils.currentUserId()));
        comment.setCatRef(catRef);
        comment.setTitle(title);
        comment.setContent(content);
        comment.setCreateTime(new Date());
        comment.setNumAgree(1);
        comment.setNumVote(1);
        comment.setEmailNotify(emailNotify);
        
        cstDAO.save(comment);
        
        systemDAO.setVoting(YesNoVoting.TYPE_SARP_VTT_COMMENT, comment.getId(), true);
        
        return comment;
    } //createComment()


    public void deleteComment(VTTComment comment) throws Exception {
        comment.setDeleted(true);
        cstDAO.save(comment);
    } //deleteComment()


    public VTTComment getCommentById(Long cid) throws Exception {
        return (VTTComment) vttDAO.load(VTTComment.class, cid);
    } //getCommentById()


    public VTTComment setVotingOnComment(Long cid, boolean agree) throws Exception {
        VTTComment comment = (VTTComment) cstDAO.load(VTTComment.class, cid);
        if (comment==null) throw new Exception("can't find the specified Comment with id "+cid);
        
        systemDAO.setVoting(YesNoVoting.TYPE_SARP_VTT_COMMENT, cid, agree);
        
        vttDAO.increaseVoting(comment, agree);
        
        return comment;
    } //setVotingOnComment()


    public CategoryValue getCategoryValueById(Long id) throws Exception {
        return vttDAO.getCategoryValueById(id);
    } //getCategoryValueById()


    public void saveCategoryValue(Long catRefId, String name, String unit) throws Exception {
        CategoryValue catValue = vttDAO.getCategoryValueById(catRefId);
        catValue.setName(name);
        catValue.setCriterion(unit);
        if (name==null || name.trim().length()==0) {
            catValue.setValue(false);
        } else {
            catValue.setValue(true);
        }
        
        vttDAO.save(catValue);
    } //saveCategoryValue()


    public void publish(Long vttId) throws Exception {
        VTT vtt = vttDAO.getVTTById(vttId);
        CategoryReference root = vtt.getCats().get(WebUtils.currentUserId());
        vtt.getCats().put(WebUtils.currentUserId(), null);
        vtt.getCategories().put(WebUtils.currentUserId(), root);
        vttDAO.save(vtt);
    } //publish()


    public CategoryReference getCategoryReferenceById(Long id) throws Exception {
        return chtDAO.getCategoryReferenceById(id);
    } //getCategoryReferenceById()


    public VTTSpecialistComment createSpecialistComment(Long vttId, String title, String content, boolean emailNotify) throws Exception {
        VTT vtt = vttDAO.getVTTById(vttId);
        
        if (vtt==null) throw new Exception("can't find the specified VTT with id "+vttId);
        
        VTTSpecialistComment comment = new VTTSpecialistComment();
        comment.setAuthor(cstDAO.getUserById(WebUtils.currentUserId()));
        comment.setVtt(vtt);
        comment.setTitle(title);
        comment.setContent(content);
        comment.setCreateTime(new Date());
        comment.setNumAgree(1);
        comment.setNumVote(1);
        comment.setEmailNotify(emailNotify);
        
        cstDAO.save(comment);
        
        systemDAO.setVoting(YesNoVoting.TYPE_SARP_VTT_SPEC_COMMENT, comment.getId(), true);
        
        return comment;
    } //createSpecialistComment()


} //class VTTServiceImpl
