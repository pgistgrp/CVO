package org.pgist.sarp.bct;

import java.io.Serializable;

import org.pgist.tagging.Category;
import org.pgist.tagging.Tag;


/**
 * 
 * @author kenny
 *
 * @hibernate.class table="sarp_tag_refs" lazy="true"
 */
public class TagReference implements Serializable {
    
    
    protected Long id;
    
    protected Tag tag;
    
    protected int times;
    
    protected Long bctId;
    
    
    public TagReference() {
    }
    
    
    public TagReference(String name) {
        this.tag = new Tag(name);
    }
    
    
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
     * @hibernate.many-to-one column="tag_id" lazy="true" class="org.pgist.tagging.Tag" cascade="save-update"
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
     * @hibernate.property column="bct_id" not-null="true"
     */
    public Long getBctId() {
        return bctId;
    }


    public void setBctId(Long bctId) {
        this.bctId = bctId;
    }
    
    
    public int getFontSize() {
        int myTimes = getTimes();
        if (myTimes>=1 && myTimes<=2) {
            return 1;
        } else if (myTimes>=3 && myTimes <=5) {
            return 2;
        } else if (myTimes>=7 && myTimes <=10) {
            return 3;
        } else if (myTimes>10) {
            return 4;
        } else {
            //invalid state
            return 0;
        }
    }


    public String toString() {
        Tag one = getTag();
        if (one!=null) return one.toString();
        else return null;
    }


}//class TagReference
