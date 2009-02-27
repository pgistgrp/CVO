package org.pgist.sarp.drt;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

import org.pgist.sarp.report.Report;
import org.pgist.system.SystemDAO;
import org.pgist.system.YesNoVoting;
import org.pgist.users.User;
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
		return (Comment) drtDAO.load(Comment.class, id);
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
	public Comment createComment(Long workflowId, Long oid, String title, String content, boolean emailNotify) throws Exception {
		InfoObject infoObject = drtDAO.getInfoObjectById(oid);
		
		if (infoObject==null) throw new Exception("can't find the specified InfoObject with id "+oid);
		
		Comment comment = new Comment();
		comment.setWorkflowId(workflowId);
		comment.setAuthor(drtDAO.getUserById(WebUtils.currentUserId()));
		comment.setTarget(infoObject);
		comment.setTitle(title);
		comment.setContent(content);
		comment.setCreateTime(new Date());
		comment.setNumAgree(1);
		comment.setNumVote(1);
		comment.setEmailNotify(emailNotify);
		
		drtDAO.save(comment);
		
        systemDAO.setVoting(YesNoVoting.TYPE_SARP_DRT_COMMENT, comment.getId(), true);
        
		return comment;
	}//createComment()


	@Override
	public InfoObject setVotingOnInfoObject(Long oid, boolean agree) throws Exception {
		InfoObject infoObject = drtDAO.getInfoObjectById(oid);
		if (infoObject==null) throw new Exception("can't find the specified InfoObject with id "+oid);
		
        systemDAO.setVoting(YesNoVoting.TYPE_SARP_DRT_INFOOBJ, oid, agree);
        
        drtDAO.increaseVoting(infoObject, agree);
        
        return infoObject;
	}//setVotingOnInfoObject()


	@Override
	public Comment setVotingOnComment(Long cid, boolean agree) throws Exception {
		Comment comment = (Comment) drtDAO.load(Comment.class, cid);
		if (comment==null) throw new Exception("can't find the specified Comment with id "+cid);
		
        systemDAO.setVoting(YesNoVoting.TYPE_SARP_DRT_COMMENT, cid, agree);
        
        drtDAO.increaseVoting(comment, agree);
        
        return comment;
	}//setVotingOnComment()
	
	
	@Override
	public void deleteComment(Comment comment) throws Exception {
		comment.setDeleted(true);
		
		drtDAO.save(comment);
	}//deleteComment()


    @Override
    public Collection<DRTAnnouncement> getAnnouncements(Long oid) throws Exception {
        InfoObject infoObject = drtDAO.getInfoObjectById(oid);
        if (infoObject==null) throw new Exception("can't find the specified InfoObject with id "+oid);
        
        return infoObject.getAnnouncements();
    } //getAnnouncements()


    @Override
    public Comment createAnnouncement(Long oid, String title, String description) throws Exception {
        InfoObject infoObject = drtDAO.getInfoObjectById(oid);
        if (infoObject==null) throw new Exception("can't find the specified InfoObject with id "+oid);
        
        DRTAnnouncement announcement = new DRTAnnouncement();
        announcement.setTitle(title);
        announcement.setDescription(description);
        announcement.setNumAgree(1);
        announcement.setNumVote(1);
        
        drtDAO.save(announcement);
        
        infoObject.getAnnouncements().add(announcement);
        
        drtDAO.save(infoObject);
        
        systemDAO.setVoting(YesNoVoting.TYPE_SARP_DRT_ANNOUNCEMENT, announcement.getId(), true);
        
        return null;
    } //createAnnouncement()


    @Override
    public DRTAnnouncement setVotingOnAnnouncement(Long aid, boolean agree) throws Exception {
        DRTAnnouncement announcement = (DRTAnnouncement) drtDAO.load(DRTAnnouncement.class, aid);
        if (announcement==null) throw new Exception("can't find the specified announcement with id "+aid);
        
        systemDAO.setVoting(YesNoVoting.TYPE_SARP_DRT_ANNOUNCEMENT, aid, agree);
        
        drtDAO.increaseVoting(announcement, agree);
        
        return announcement;
    } //setVotingOnAnnouncement()


    @Override
    public DRTAnnouncement getDRTAnnouncementById(Long aid) throws Exception {
        return (DRTAnnouncement) drtDAO.load(DRTAnnouncement.class, aid);
    } //getDRTAnnouncementById()


    @Override
    public void deleteVote(DRTAnnouncement announcement, YesNoVoting voting) throws Exception {
        drtDAO.deleteVote(announcement, voting);
    } //deleteVote()


    @Override
    public void setAnnouncementDone(Long aid) throws Exception {
        DRTAnnouncement announcement = (DRTAnnouncement) drtDAO.load(DRTAnnouncement.class, aid);
        announcement.setDone(true);
    } //setAnnouncementDone()


    @Override
    public Set<User> getThreadUsers(Long oid) throws Exception {
        return drtDAO.getThreadUsers(oid);
    } //getThreadUsers()


    @Override
    public InfoObject getInfoObjectByTargetId(Long oid) throws Exception {
        return drtDAO.getInfoObjectByTargetId(oid);
    } //getInfoObjectByTargetId()


    @Override
    public void save(Report report) throws Exception {
        drtDAO.save(report);
    } //save()
    
    
}//class DRTServiceImpl
