package org.pgist.sarp.vtt;

import org.pgist.sarp.cst.CategoryReference;


/**
 * Category Value.
 * 
 * @author kenny
 *
 * @hibernate.class table="sarp_vtt_category_value" lazy="true"
 */
public class CategoryValue {
    
    
    private Long id;
    
    private CategoryReference catRef;
    
    private boolean value;
    
    private String name;
    
    private String criterion;
    
    
    public CategoryValue() {
    }
    
    
    public CategoryValue(CategoryReference catRef) {
        this.id = catRef.getId();
        this.catRef = catRef;
    }
    
    
    /**
     * @return
     * 
     * @hibernate.id generator-class="assigned"
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
     * @hibernate.many-to-one column="catref_id" lazy="true" class="org.pgist.sarp.cst.CategoryReference"
     */
    public CategoryReference getCatRef() {
        return catRef;
    }


    public void setCatRef(CategoryReference catRef) {
        this.catRef = catRef;
    }


    /**
     * @return
     * 
     * @hibernate.property not-null="true"
     */
    public boolean isValue() {
        return value;
    }


    public void setValue(boolean value) {
        this.value = value;
    }


    /**
     * @return
     * 
     * @hibernate.property length="256"
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
     * @hibernate.property length="256"
     */
    public String getCriterion() {
        return criterion;
    }


    public void setCriterion(String criterion) {
        this.criterion = criterion;
    }
    
    
} //class CategoryValue
