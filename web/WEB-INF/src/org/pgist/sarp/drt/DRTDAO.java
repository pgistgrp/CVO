package org.pgist.sarp.drt;

import java.util.Collection;

import org.pgist.system.BaseDAO;
import org.pgist.util.PageSetting;


/**
 * @author kenny
 *
 */
public interface DRTDAO extends BaseDAO {
	
	
	InfoObject getInfoObjectById(Long oid) throws Exception;

	Collection<Comment> getComments(Long oid, PageSetting setting) throws Exception;

	void increaseVoting(InfoObject infoObject, boolean agree) throws Exception;

	void increaseVoting(Comment comment, boolean agree) throws Exception;

	
}//interface DRTDAO
