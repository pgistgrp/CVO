package org.pgist.sarp.vtt;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.pgist.sarp.bct.BCTDAO;
import org.pgist.sarp.cht.CHT;
import org.pgist.sarp.cht.CHTDAO;
import org.pgist.sarp.cst.CSTDAO;
import org.pgist.sarp.cst.CategoryReference;
import org.pgist.sarp.drt.InfoObject;
import org.pgist.system.SystemDAO;
import org.pgist.system.YesNoVoting;
import org.pgist.tagging.TagDAO;
import org.pgist.users.User;
import org.pgist.util.PageSetting;
import org.pgist.util.WebUtils;


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
    
    
    @Override
    public void toggleVTT(Long vttId, boolean closed) throws Exception {
        VTT vtt = vttDAO.getVTTById(vttId);
        vtt.setClosed(closed);
        vttDAO.save(vtt);
    } //toggleVTT()
    
    
    @Override
    public InfoObject publish(Long vttId, String property) throws Exception {
        // TODO Auto-generated method stub
        return null;
    } //publish()
    
    
    @Override
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


    @Override
    public List<User> getOtherUsers(VTT vtt) throws Exception {
        return vttDAO.getOtherUsers(vtt);
    } //getOtherUsers()


    @Override
    public VTT getVTTById(Long vttId) throws Exception {
        return (VTT) vttDAO.load(VTT.class, vttId);
    } //getVTTById()


    @Override
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


    @Override
    public void setClearVTTWinner(Long vttId) throws Exception {
        VTT vtt = vttDAO.getVTTById(vttId);
        vtt.setWinner(null);
        vtt.setWinnerCategory(null);
        vttDAO.save(vtt);
    } //setClearVTTWinner()


    @Override
    public Collection<VTTComment> getComments(Long catRefId, PageSetting setting) throws Exception {
        return vttDAO.getComments(catRefId, setting);
    } //getComments()


    @Override
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


    @Override
    public void deleteComment(VTTComment comment) throws Exception {
        comment.setDeleted(true);
        cstDAO.save(comment);
    } //deleteComment()


    @Override
    public VTTComment getCommentById(Long cid) throws Exception {
        return (VTTComment) vttDAO.load(VTTComment.class, cid);
    } //getCommentById()


    @Override
    public VTTComment setVotingOnComment(Long cid, boolean agree) throws Exception {
        VTTComment comment = (VTTComment) cstDAO.load(VTTComment.class, cid);
        if (comment==null) throw new Exception("can't find the specified Comment with id "+cid);
        
        systemDAO.setVoting(YesNoVoting.TYPE_SARP_VTT_COMMENT, cid, agree);
        
        vttDAO.increaseVoting(comment, agree);
        
        return comment;
    } //setVotingOnComment()
    
    
}//class VTTServiceImpl
