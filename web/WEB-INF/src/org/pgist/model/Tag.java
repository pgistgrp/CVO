package org.pgist.model;


/**
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_cst_tags" lazy="true"
 */
public class Tag {
    
    
    protected Long id;
    
    protected String name;
    
    protected Category category;
    
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
     * @hibernate.many-to-one column="category_id" lazy="true"
     */
    public Category getCategory() {
        return category;
    }


    public void setCategory(Category category) {
        this.category = category;
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
