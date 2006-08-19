package org.pgist.cvo;

import org.pgist.tag.Tag;


/**
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_cvo_tag_refs" lazy="true"
 */
public class TagReference {
    
    
    protected Long id;
    
    protected Tag tag;
    
    protected int times;
    
    protected Long cctId;
    
    
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
     * @hibernate.many-to-one column="tag_id" lazy="true" class="org.pgist.tag.Tag" cascade="all"
     */
    public Tag getTag() {
        return tag;
    }
    
    
    public void setTag(Tag tag) {
        this.tag = tag;
    }
    
    
    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public int getTimes() {
        return times;
    }
    
    
    public void setTimes(int times) {
        this.times = times;
    }


    /**
     * @return
     * @hibernate.property column="cct_id" not-null="true"
     */
    public Long getCctId() {
        return cctId;
    }


    public void setCctId(Long cctId) {
        this.cctId = cctId;
    }
    
    
    public int getFontSize() {
        int myTimes = getTimes();
        if (myTimes>=1 && myTimes<=5) {
            return 1;
        } else if (myTimes>=6 && myTimes <=7) {
            return 2;
        } else if (myTimes>=8 && myTimes <=10) {
            return 3;
        } else if (myTimes>10) {
            return 4;
        } else {
            //invalid state
            return 0;
        }
    }


}//class TagReference
