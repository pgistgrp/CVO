package org.pgist.model;

import java.util.Set;


/**
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_cst_tags" lazy="true"
 */
public class Tag {
    
    
    protected Long id;
    
    protected String name;
    
    protected Set categories;
    
    protected boolean deleted = false;
    
    
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
    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }
    
    
    /**
     * @return
     * 
     * @hibernate.set lazy="true" table="pgist_cst_cat_tag_link" order-by="category_id"
     * @hibernate.collection-key column="tag_id"
     * @hibernate.collection-many-to-many column="category_id" class="org.pgist.model.Category"
     */
    public Set getCategories() {
        return categories;
    }


    public void setCategories(Set categories) {
        this.categories = categories;
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


}
