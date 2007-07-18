package org.pgist.report;

import java.io.Serializable;
import org.pgist.users.User;

/**
 * 
 * @author John
 *
 * @hibernate.class table="pgist_report_vote" lazy="true"
 */
public class ReportVote {

	private Long id;
    
    private boolean voting;
    
    private User owner;

    
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
     * @hibernate.many-to-one column="owner_id" lazy="true"
     */
	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

    /**
     * @return
     * @hibernate.property not-null="true"
     */
	public boolean isVoting() {
		return voting;
	}

	public void setVoting(boolean voting) {
		this.voting = voting;
	}
	    
}
