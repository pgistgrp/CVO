package org.pgist.sarp.drt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.Query;
import org.pgist.system.BaseDAOImpl;
import org.pgist.system.YesNoVoting;
import org.pgist.util.PageSetting;


/**
 * @author kenny
 *
 */
public class DRTDAOImpl extends BaseDAOImpl implements DRTDAO {
	
	
	@Override
	public InfoObject getInfoObjectById(Long oid) throws Exception {
		return (InfoObject) load(InfoObject.class, oid);
	}//getInfoObjectById()
	
	
	private static final String hql_getComments1 = "select count(id) from Comment c where c.deleted=false and c.target.id=?";
	private static final String hql_getComments2 = "from Comment c where c.deleted=false and c.target.id=? order by c.id desc";
	
	
	@Override
	public Collection<Comment> getComments(Long oid, PageSetting setting) throws Exception {
        List<Comment> list = new ArrayList<Comment>();
        
        //get total rows number
        Query query = getSession().createQuery(hql_getComments1);
        query.setLong(0, oid);
        int count = ((Number) query.uniqueResult()).intValue();
        
        if (count==0) return list;
        
        if (setting.getRowOfPage()==-1) setting.setRowOfPage(count);
        setting.setRowSize(count);
        
        //get records
        query = getSession().createQuery(hql_getComments2);
        query.setLong(0, oid);
        query.setFirstResult(setting.getFirstRow());
        query.setMaxResults(setting.getRowOfPage());
        
        return query.list();
	}//getComments()


    private static final String hql_increaseVoting_11 = "update InfoObject i set i.numVote=i.numVote+1 where i.id=?";
    
    private static final String hql_increaseVoting_12 = "update InfoObject i set i.numAgree=i.numAgree+1 where i.id=?";
    
    
	@Override
    public void increaseVoting(InfoObject object, boolean agree) throws Exception {
        getSession().createQuery(hql_increaseVoting_11).setLong(0, object.getId()).executeUpdate();
        if (agree) {
            getSession().createQuery(hql_increaseVoting_12).setLong(0, object.getId()).executeUpdate();
        }
    }//increaseVoting()


    private static final String hql_increaseVoting_21 = "update Comment c set c.numVote=c.numVote+1 where c.id=?";
    
    private static final String hql_increaseVoting_22 = "update Comment c set c.numAgree=c.numAgree+1 where c.id=?";
    
    
	@Override
	public void increaseVoting(Comment comment, boolean agree) throws Exception {
        getSession().createQuery(hql_increaseVoting_21).setLong(0, comment.getId()).executeUpdate();
        if (agree) {
            getSession().createQuery(hql_increaseVoting_22).setLong(0, comment.getId()).executeUpdate();
        }
	}//increaseVoting()


    private static final String hql_increaseVoting_31 = "update DRTAnnouncement a set a.numVote=a.numVote+1 where a.id=?";
    
    private static final String hql_increaseVoting_32 = "update DRTAnnouncement a set a.numAgree=a.numAgree+1 where a.id=?";
    
    
    @Override
    public void increaseVoting(DRTAnnouncement announcement, boolean agree) throws Exception {
        getSession().createQuery(hql_increaseVoting_31).setLong(0, announcement.getId()).executeUpdate();
        if (agree) {
            getSession().createQuery(hql_increaseVoting_32).setLong(0, announcement.getId()).executeUpdate();
        }
    }//increaseVoting()


    private static final String hql_decreaseVoting_31 = "update DRTAnnouncement a set a.numVote=a.numVote-1 where a.id=?";
    
    private static final String hql_decreaseVoting_32 = "update DRTAnnouncement a set a.numAgree=a.numAgree-1 where a.id=?";
    
    
    @Override
    public void decreaseVoting(DRTAnnouncement announcement, boolean agree) throws Exception {
        getSession().createQuery(hql_decreaseVoting_31).setLong(0, announcement.getId()).executeUpdate();
        if (agree) {
            getSession().createQuery(hql_decreaseVoting_32).setLong(0, announcement.getId()).executeUpdate();
        }
    }//decreaseVoting()


    @Override
    public void deleteVote(DRTAnnouncement announcement, YesNoVoting voting) throws Exception {
        decreaseVoting(announcement, voting.isVoting());
        getSession().delete(voting);
    } //deleteVote()


}//class DRTDAOImpl
