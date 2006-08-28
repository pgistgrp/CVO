package org.pgist.cvo;

import java.util.Date;


/**
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_themes" lazy="true"
 */
public class Theme {
    
    
    protected Long id;
    
    protected boolean deleted = false;
    
    protected String title = "";
    
    protected String description = "";
    
    protected String summary = "";
    
    protected Date createTime = new Date();
    
    
    /**
     * @return
     * 
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
     * 
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
     * 
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
     * 
     * @hibernate.property not-null="true"
     */
    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    /**
     * @return
     * 
     * @hibernate.property not-null="true" type="text"
     */
    public String getSummary() {
        return summary;
    }


    public void setSummary(String summary) {
        this.summary = summary;
    }


    /**
     * @return
     * 
     * @hibernate.property not-null="true"
     */
    public Date getCreateTime() {
        return createTime;
    }


    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


}//class Theme
