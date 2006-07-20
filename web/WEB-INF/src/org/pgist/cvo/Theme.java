package org.pgist.cvo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_themes" lazy="true"
 */
public class Theme {
    
    
    protected Long id;
    
    protected String name = "";
    
    protected boolean deleted = false;
    
    protected Set parents = new HashSet();
    
    protected Set children = new HashSet();
    
    protected String summary = "";
    
    protected Date createTime;
    
    
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
    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    /**
     * @return
     * 
     * @hibernate.set lazy="true" cascade="all" table="pgist_cvo_theme_theme_link" order-by="child_id"
     * @hibernate.collection-key column="parent_id"
     * @hibernate.collection-many-to-many column="child_id" class="org.pgist.cvo.Theme"
     */
    public Set getParents() {
        return parents;
    }
    
    
    public void setParents(Set parents) {
        this.parents = parents;
    }


    /**
     * @return
     * 
     * @hibernate.set lazy="true" cascade="all" table="pgist_cvo_theme_theme_link" order-by="parent_id"
     * @hibernate.collection-key column="child_id"
     * @hibernate.collection-many-to-many column="parent_id" class="org.pgist.cvo.Theme"
     */
    public Set getChildren() {
        return children;
    }


    public void setChildren(Set children) {
        this.children = children;
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


}//class Theme
