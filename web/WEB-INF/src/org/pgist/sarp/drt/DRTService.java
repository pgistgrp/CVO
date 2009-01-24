package org.pgist.sarp.drt;

import java.util.Collection;
import java.util.Set;

import org.pgist.sarp.report.Report;
import org.pgist.system.YesNoVoting;
import org.pgist.users.User;
import org.pgist.util.PageSetting;


/**
 * DRT Service.
 * 
 * @author kenny
 *
 */
public interface DRTService {
	
	
	InfoObject getInfoObjectById(Long id) throws Exception;

	Comment getCommentById(Long id) throws Exception;

	void clearVote(Long oid) throws Exception;

	void toggleDRT(Long oid, boolean closed) throws Exception;

	Collection<Comment> getComments(Long oid, PageSetting setting) throws Exception;

	Comment createComment(Long workflowId, Long oid, String title, String content, boolean emailNotify) throws Exception;

	InfoObject setVotingOnInfoObject(Long oid, boolean agree) throws Exception;

	void deleteComment(Comment comment) throws Exception;

	Comment setVotingOnComment(Long cid, boolean agree) throws Exception;

    DRTAnnouncement getDRTAnnouncementById(Long aid) throws Exception;
    
    Collection<DRTAnnouncement> getAnnouncements(Long aid) throws Exception;

    Comment createAnnouncement(Long oid, String title, String description) throws Exception;
    
    DRTAnnouncement setVotingOnAnnouncement(Long aid, boolean agree) throws Exception;

    void deleteVote(DRTAnnouncement announcement, YesNoVoting voting) throws Exception;

    void setAnnouncementDone(Long aid) throws Exception;

    Set<User> getThreadUsers(Long oid) throws Exception;

    InfoObject getInfoObjectByTargetId(Long bctId) throws Exception;

    void save(Report report) throws Exception;

	
}//interface DRTService
