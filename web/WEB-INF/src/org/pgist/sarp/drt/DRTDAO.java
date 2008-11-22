package org.pgist.sarp.drt;

import java.util.Collection;
import java.util.Set;

import org.pgist.system.BaseDAO;
import org.pgist.system.YesNoVoting;
import org.pgist.users.User;
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

    void increaseVoting(DRTAnnouncement announcement, boolean agree) throws Exception;

    void deleteVote(DRTAnnouncement announcement, YesNoVoting voting) throws Exception;

    void decreaseVoting(DRTAnnouncement announcement, boolean agree) throws Exception;

    Set<User> getThreadUsers(Long oid) throws Exception;

	
}//interface DRTDAO
