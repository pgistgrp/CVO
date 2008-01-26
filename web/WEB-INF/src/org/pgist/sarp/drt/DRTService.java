package org.pgist.sarp.drt;


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
	
	
}//interface DRTService
