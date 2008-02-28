package org.pgist.sarp.cst;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.pgist.model.Node;
import org.pgist.sarp.bct.TagReferenceComparator;
import org.pgist.tagging.Category;


/**
 * 
 * @author kenny
 *
 * @hibernate.class table="sarp_cst_category_refs" lazy="true"
 */
public class CategoryReference implements Serializable, Node {
    
    
	protected Long id;
	
    protected Long cstId;
    
    protected Set<CategoryReference> parents = new HashSet<CategoryReference>();
    
    protected List<CategoryReference> children = new ArrayList<CategoryReference>();
    
    protected Category category;
    
    protected String theme = "";
    
    protected SortedSet tags = new TreeSet(new TagReferenceComparator());
    
    protected int indent = 0;
    
    
    public CategoryReference() {
    }


    public CategoryReference(CategoryReference sample) {
        //shallow copy
        setCstId(sample.getCstId());
        setCategory(sample.getCategory());
        setTheme(sample.getTheme());
        setIndent(sample.getIndent());
        getTags().addAll(sample.getTags());
    }


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
     * @hibernate.property column="cst_id" not-null="true"
     */
    public Long getCstId() {
		return cstId;
	}


	public void setCstId(Long cstId) {
		this.cstId = cstId;
	}


	/**
     * @return
     * 
     * @hibernate.set inverse="true" lazy="true" table="sarp_cst_catref_catref_link"
     * @hibernate.collection-key column="parent_id"
     * @hibernate.collection-many-to-many column="child_id" class="org.pgist.sarp.cst.CategoryReference"
     */
    public Set<CategoryReference> getParents() {
        return parents;
    }
    
    
    public void setParents(Set<CategoryReference> parents) {
        this.parents = parents;
    }


    /**
     * @return
     * 
     * @hibernate.list lazy="true" table="sarp_cst_catref_catref_link"
     * @hibernate.collection-key column="child_id"
     * @hibernate.collection-index column="child_order" default="0"
     * @hibernate.collection-many-to-many column="parent_id" class="org.pgist.sarp.cst.CategoryReference"
     */
    public List<CategoryReference> getChildren() {
        return children;
    }


    public void setChildren(List<CategoryReference> children) {
        this.children = children;
    }


    /**
     * @return
     * 
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
     * @hibernate.property
     */
    public String getTheme() {
        return theme;
    }


    public void setTheme(String theme) {
        this.theme = theme;
    }


    /**
     * @return
     * 
     * @hibernate.set lazy="true" table="pgist_cst_catref_tagref_link" order-by="tagref_id" sort="org.pgist.sarp.bct.TagReferenceComparator"
     * @hibernate.collection-key column="catref_id"
     * @hibernate.collection-many-to-many column="tagref_id" class="org.pgist.sarp.bct.TagReference"
     */
    public SortedSet getTags() {
        return tags;
    }


    public void setTags(SortedSet tags) {
        this.tags = tags;
    }
    
    
    /**
     * @return
     * 
     * @hibernate.property not-null="true"
     */
    public int getIndent() {
        return indent;
    }


    public void setIndent(int indent) {
        this.indent = indent;
    }


    /*
     * ------------------------------------------------------------------------
     */
    
    
    public String toString() {
        Category one = getCategory();
        if (one!=null) return one.toString();
        else return null;
    }


    public String getCaption() {
        Category one = getCategory();
        if (one!=null) return one.toString();
        else return null;
    }
    
    
}//class CategoryReference
