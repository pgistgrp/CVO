package org.pgist.workflow;

import java.util.Date;

import org.pgist.users.User;
import org.pgist.wfengine.Workflow;


/**
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_wf_ds" lazy="true"
 */
public class DecisionSituation {
    
    
    private Long id;
    
    private String title;
    
    private Date createTime;
    
    private User creator;
    
    private boolean deleted;
    
    private Workflow agenda;
    
    
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
     * @hibernate.property not-null="true"
     */
    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public Date getCreateTime() {
        return createTime;
    }


    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    /**
     * @return
     * @hibernate.many-to-one column="creator_id" lazy="true" class="org.pgist.users.User" casecad="all"
     */
    public User getCreator() {
        return creator;
    }


    public void setCreator(User creator) {
        this.creator = creator;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public boolean isDeleted() {
        return deleted;
    }


    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }


    /**
     * @return
     * @hibernate.many-to-one column="agenda_id" lazy="true" class="org.pgist.wfengine.Workflow" casecad="all"
     */
    public Workflow getAgenda() {
        return agenda;
    }


    public void setAgenda(Workflow workflow) {
        this.agenda = workflow;
    }
    
    
}
