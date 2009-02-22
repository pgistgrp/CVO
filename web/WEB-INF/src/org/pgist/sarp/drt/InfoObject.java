package org.pgist.sarp.drt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * DRT information object.
 * 
 * @author kenny
 *
 * @hibernate.class table="sarp_drt_info_object" lazy="true"
 */
public class InfoObject implements Serializable {
	
	
	private static final long serialVersionUID = -3722333525819303168L;


	protected Long id;
	
	protected String title;
	
	protected Object target;
	
    protected int numAgree = 0;
    
    protected int numVote = 0;
    
    protected List<DRTAnnouncement> announcements = new ArrayList<DRTAnnouncement>();
    
    protected boolean closed;
    
	
	/**
     * @return
     * @hibernate.id generator-class="native"
     */
	public Long getId() {
		return id;
	}
	
	
	public void setId(Long id) {
		this.id = id;
	}
	
	
	/**
	 * @return
	 * @hibernate.property
	 */
    public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	/**
     * @return object
     * 
     * @hibernate.any id-type="long" meta-type="string"
     * @hibernate.any-column name="class_name"
     * @hibernate.any-column name="class_id"
     */
	public Object getTarget() {
		return target;
	}


	public void setTarget(Object target) {
		this.target = target;
	}


    /**
     * @return
     * @hibernate.property
     */
	public int getNumAgree() {
		return numAgree;
	}


	public void setNumAgree(int numAgree) {
		this.numAgree = numAgree;
	}


    /**
     * @return
     * @hibernate.property
     */
	public int getNumVote() {
		return numVote;
	}


	public void setNumVote(int numVote) {
		this.numVote = numVote;
	}

	
	/**
     * @return
     * 
     * @hibernate.list lazy="false" table="sarp_infoobj_announce_link" cascade="all"
     * @hibernate.collection-key column="obj_id"
     * @hibernate.collection-index column="index_order"
     * @hibernate.collection-many-to-many column="objective_id" class="org.pgist.sarp.drt.DRTAnnouncement"
	 */
    public List<DRTAnnouncement> getAnnouncements() {
        return announcements;
    }


    public void setAnnouncements(List<DRTAnnouncement> announcements) {
        this.announcements = announcements;
    }


    /**
     * @return
     * @hibernate.property
     */
	public boolean isClosed() {
		return closed;
	}


	public void setClosed(boolean closed) {
		this.closed = closed;
	}


}//class InfoObject
