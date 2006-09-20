package org.pgist.discussion;

import java.io.Serializable;


/**
 * This is a link table to speed up the searching of concerns by related tags.
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_info_tag" lazy="true"
 */
public class InfoTagLink implements Serializable {
    
    
    private Long id;
    
    private Long isid;
    
    private Long ioid;
    
    private Long cctId;
    
    private Long tagId;
    
    
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
    public Long getCctId() {
        return cctId;
    }


    public void setCctId(Long cctId) {
        this.cctId = cctId;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public Long getIoid() {
        return ioid;
    }


    public void setIoid(Long ioid) {
        this.ioid = ioid;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public Long getIsid() {
        return isid;
    }


    public void setIsid(Long isid) {
        this.isid = isid;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public Long getTagId() {
        return tagId;
    }


    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }
    
    
}//class InfoTagLink
