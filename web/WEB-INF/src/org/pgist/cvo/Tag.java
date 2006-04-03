package org.pgist.cvo;

import java.util.SortedSet;


/**
 *
 * @author kenny
 *
 * @hibernate.class table="pgist_cvo_tags" lazy="true"
 */
public class Tag {
    
    
    public static final int STATUS_OFFICIAL  = 1;

    public static final int STATUS_CANDIDATE = 2;

    public static final int STATUS_REJECTED  = 3;
    

    protected Long id;
    
    protected String name;
    
    protected String description = "";
    
    protected SortedSet categories;
    
    private int status = STATUS_CANDIDATE;
    
    private int count = 0;
    
    
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
     * @hibernate.set lazy="true" table="pgist_cvo_cat_tag_link" sort="org.pgist.cvo.CategoryComparator"
     * @hibernate.collection-key column="tag_id"
     * @hibernate.collection-many-to-many column="category_id" class="org.pgist.cvo.Category"
     */
    public SortedSet getCategories() {
        return categories;
    }


    public void setCategories(SortedSet categories) {
        this.categories = categories;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public int getStatus() {
        return status;
    }
    
    
    public void setStatus(int status) {
        this.status = status;
    }
    
    
    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public int getCount() {
        return count;
    }


    public void setCount(int count) {
        this.count = count;
    }


}//class Tag
