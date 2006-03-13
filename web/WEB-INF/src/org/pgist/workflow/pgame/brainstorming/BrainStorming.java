package org.pgist.workflow.pgame.brainstorming;

import org.pgist.model.Discussible;


/**
 * 
 * @author kenny
 *
 * @hibernate.joined-subclass name="BrainStorming" table="pgist_brainstorming"
 * @hibernate.joined-subclass-key column="id"
 */
public class BrainStorming extends Discussible {
    
    
    private Long activityId;
    
    private int counts;
    
    
    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public Long getActivityId() {
        return activityId;
    }
    
    
    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }
    
    
    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public int getCounts() {
        return counts;
    }
    
    
    public void setCounts(int count) {
        this.counts = count;
    }


}//class BrainStorming
