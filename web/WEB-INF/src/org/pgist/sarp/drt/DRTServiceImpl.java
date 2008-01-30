package org.pgist.sarp.drt;

import java.util.Collection;
import java.util.Date;

import org.pgist.system.SystemDAO;
import org.pgist.system.YesNoVoting;
import org.pgist.util.PageSetting;
import org.pgist.util.WebUtils;


/**
 * 
 * @author kenny
 *
 */
public class DRTServiceImpl implements DRTService {
	
	
	private DRTDAO drtDAO;
	
    private SystemDAO systemDAO;
	
    
	public void setDrtDAO(DRTDAO drtDAO) {
		this.drtDAO = drtDAO;
	}
	
	
    public void setSystemDAO(SystemDAO systemDAO) {
        this.systemDAO = systemDAO;
    }
    
    
	/*
	 * -------------------------------------------------------------
	 */


	@Override
	public InfoObject getInfoObjectById(Long oid) throws Exception {
		return drtDAO.getInfoObjectById(oid);
	}//getInfoObjectById()
	
	
	@Override
	public Comment getCommentById(Long id) throws Exception {
		return null;
	}//getCommentById()


	@Override
	public void clearVote(Long oid) throws Exception {
		InfoObject infoObject = drtDAO.getInfoObjectById(oid);
		infoObject.setNumAgree(0);
		infoObject.setNumVote(0);
		
		systemDAO.deleteVote(oid);
		
		drtDAO.save(infoObject);
	}//clearVote()


	@Override
	public void toggleDRT(Long oid, boolean closed) throws Exception {
		InfoObject infoObject = drtDAO.getInfoObjectById(oid);
		
		infoObject.setClosed(closed);
		
		drtDAO.save(infoObject);
	}//toggleDRT()


	@Override
	public Collection<Comment> getComments(Long oid, PageSetting setting) throws Exception {
		return drtDAO.getComments(oid, setting);
	}//getComments()


	@Override
	public Comment createComment(Long oid, String title, String content, boolean emailNotify) throws Exception {
		InfoObject infoObject = drtDAO.getInfoObjectById(oid);
		
		if (infoObject==null) throw new Exception("can't find the specified InfoObject with id "+oid);
		
		Comment comment = new Comment();
		comment.setAuthor(drtDAO.getUserById(WebUtils.currentUserId()));
		comment.setTarget(infoObject);
		comment.setTitle(title);
		comment.setContent(content);
		comment.setCreateTime(new Date());
		comment.setNumAgree(0);
		comment.setNumVote(0);
		comment.setEmailNotify(emailNotify);
		
		drtDAO.save(comment);
		
		return comment;
	}//createComment()


	@Override
	public InfoObject setVotingOnInfoObject(Long oid, boolean agree) throws Exception {
		InfoObject infoObject = drtDAO.getInfoObjectById(oid);
		if (infoObject==null) throw new Exception("can't find the specified InfoObject with id "+oid);
		
        systemDAO.setVoting(YesNoVoting.TYPE_SART_DRT_INFOOBJ, oid, agree);
        
        drtDAO.increaseVoting(infoObject, agree);
        
        return infoObject;
	}//setVotingOnInfoObject()
	
	
}//class DRTServiceImpl
