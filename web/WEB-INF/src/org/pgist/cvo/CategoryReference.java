package org.pgist.cvo;

import java.util.SortedSet;
import java.util.TreeSet;


/**
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_cvo_category_refs" lazy="true"
 */
public class CategoryReference {
    
    
    protected Long id;
    
    protected CCT cct;
    
    protected CategoryReference parent;
    
    protected SortedSet children = new TreeSet();
    
    protected Category category;
    
    /**
     * The associated TagReference objects
     */
    protected SortedSet tags = new TreeSet();
    
    
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
     * @hibernate.many-to-one column="cct_id" lazy="true" class="org.pgist.cvo.CCT" cascade="all"
     */
    public CCT getCct() {
        return cct;
    }


    public void setCct(CCT cct) {
        this.cct = cct;
    }


    /**
     * @return
     * @hibernate.many-to-one column="parent_id" lazy="true" class="org.pgist.cvo.CategoryReference" cascade="all"
     */
    public CategoryReference getParent() {
        return parent;
    }
    
    
    public void setParent(CategoryReference parent) {
        this.parent = parent;
    }


    /**
     * @return
     * 
     * @hibernate.set lazy="true" table="pgist_cvo_catref_catref_link" order-by="tag_id" sort="org.pgist.cvo.TagComparator"
     * @hibernate.collection-key column="category_id"
     * @hibernate.collection-many-to-many column="parent_id" class="org.pgist.cvo.CategoryReference"
     */
    public SortedSet getChildren() {
        return children;
    }


    public void setChildren(SortedSet children) {
        this.children = children;
    }


    /**
     * @return
     * @hibernate.many-to-one column="category_id" lazy="true" class="org.pgist.cvo.Category" cascade="all"
     */
    public Category getCategory() {
        return category;
    }


    public void setCategory(Category category) {
        this.category = category;
    }


    /**
     * @return
     * 
     * @hibernate.set lazy="true" table="pgist_cvo_catref_tagref_link" order-by="tagref_id" sort="org.pgist.cvo.TagReferenceComparator"
     * @hibernate.collection-key column="catref_id"
     * @hibernate.collection-many-to-many column="tagref_id" class="org.pgist.cvo.TagReference"
     */
    public SortedSet getTags() {
        return tags;
    }


    public void setTags(SortedSet tags) {
        this.tags = tags;
    }
    
    
}//class CategoryReference
