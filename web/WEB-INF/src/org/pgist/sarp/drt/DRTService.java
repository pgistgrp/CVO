package org.pgist.sarp.drt;

import java.util.Collection;

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

	Comment createComment(Long oid, String title, String content, boolean emailNotify) throws Exception;

	InfoObject setVotingOnInfoObject(Long oid, boolean agree) throws Exception;

	void deleteComment(Comment comment) throws Exception;

	Comment setVotingOnComment(Long cid, boolean agree) throws Exception;

    Collection<DRTAnnouncement> getAnnouncements(Long oid) throws Exception;

    Comment createAnnouncement(Long oid, String title, String description) throws Exception;
	
	
}//interface DRTService
