package org.pgist.cvo;


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
    
    protected CCT cct;
    
    
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
     * @hibernate.many-to-one column="tag_id" lazy="true" class="org.pgist.cvo.Tag" cascade="all"
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
     * @hibernate.many-to-one column="cct_id" lazy="true" class="org.pgist.cvo.CCT" cascade="all"
     */
    public CCT getCct() {
        return cct;
    }


    public void setCct(CCT cct) {
        this.cct = cct;
    }
    
    
    public int getFontSize() {
        if (times>=1 && times<=5) {
            return 1;
        } else if (times>=6 && times <=7) {
            return 2;
        } else if (times>=8 && times <=10) {
            return 3;
        } else if (times>10) {
            return 4;
        } else {
            //invalid state
            return 0;
        }
    }


}//class TagReference
