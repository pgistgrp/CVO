package org.pgist.sarp.cht;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.pgist.sarp.bct.BCTDAO;
import org.pgist.sarp.cst.CST;
import org.pgist.sarp.cst.CSTDAO;
import org.pgist.sarp.cst.CategoryReference;
import org.pgist.sarp.drt.InfoObject;
import org.pgist.system.SystemDAO;
import org.pgist.system.YesNoVoting;
import org.pgist.tagging.Category;
import org.pgist.tagging.TagDAO;
import org.pgist.users.User;
import org.pgist.util.PageSetting;
import org.pgist.util.WebUtils;


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
    public CategoryReference setRootCategoryReference(CHT cht, User user) throws Exception {
        CategoryReference catref = new CategoryReference();
        
        catref.setCstId(cht.getId());
        cstDAO.save(catref);
        
        cht.getCategories().put(user.getId(), catref);
        
        
        return catref;
    }//setRootCategoryReference()


    @Override
    public List<User> getOtherUsers(CHT cht) throws Exception {
        return chtDAO.getOtherUsers(cht);
    }//getOtherUsers()


    @Override
    public Collection<CHTComment> getComments(Long catRefId, PageSetting setting) throws Exception {
        return chtDAO.getComments(catRefId, setting);
    }//getComments()


    @Override
    public CHTComment createComment(Long catRefId, String title, String content, boolean emailNotify) throws Exception {
        CategoryReference catRef = cstDAO.getCategoryReferenceById(catRefId);
        
        if (catRef==null) throw new Exception("can't find the specified CategoryReference with id "+catRefId);
        
        CHTComment comment = new CHTComment();
        comment.setAuthor(cstDAO.getUserById(WebUtils.currentUserId()));
        comment.setCatRef(catRef);
        comment.setTitle(title);
        comment.setContent(content);
        comment.setCreateTime(new Date());
        comment.setNumAgree(1);
        comment.setNumVote(1);
        comment.setEmailNotify(emailNotify);
        
        cstDAO.save(comment);
        
        systemDAO.setVoting(YesNoVoting.TYPE_SART_CHT_COMMENT, comment.getId(), true);
        
        return comment;
    }//createComment()


    @Override
    public CHTComment getCommentById(Long cid) throws Exception {
        return (CHTComment) cstDAO.load(CHTComment.class, cid);
    }//getCommentById()


    @Override
    public CHTComment setVotingOnComment(Long cid, boolean agree) throws Exception {
        CHTComment comment = (CHTComment) cstDAO.load(CHTComment.class, cid);
        if (comment==null) throw new Exception("can't find the specified Comment with id "+cid);
        
        systemDAO.setVoting(YesNoVoting.TYPE_SART_CHT_COMMENT, cid, agree);
        
        chtDAO.increaseVoting(comment, agree);
        
        return comment;
    }//setVotingOnComment()


    @Override
    public void deleteComment(CHTComment comment) throws Exception {
        comment.setDeleted(true);
        cstDAO.save(comment);
    }//deleteComment()


}//class CHTServiceImpl
