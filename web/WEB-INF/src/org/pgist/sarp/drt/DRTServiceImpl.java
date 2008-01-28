package org.pgist.sarp.drt;

import java.util.Collection;

import org.pgist.util.PageSetting;


/**
 * 
 * @author kenny
 *
 */
public class DRTServiceImpl implements DRTService {
	
	
	private DRTDAO drtDAO;
	
	
	public void setDrtDAO(DRTDAO drtDAO) {
		this.drtDAO = drtDAO;
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
	
	
}//class DRTServiceImpl
