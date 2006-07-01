package org.pgist.cvo;

import java.util.SortedSet;
import java.util.TreeSet;


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
    
    protected SortedSet parents = new TreeSet();
    
    protected SortedSet children = new TreeSet();
    
    protected Long oid;
    
    protected int otype;
    
    protected Object obj;
    
    
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
    public SortedSet getParents() {
        return parents;
    }
    
    
    public void setParents(SortedSet parents) {
        this.parents = parents;
    }


    /**
     * @return
     * 
     * @hibernate.set lazy="true" cascade="all" table="pgist_cvo_theme_theme_link" order-by="parent_id"
     * @hibernate.collection-key column="child_id"
     * @hibernate.collection-many-to-many column="parent_id" class="org.pgist.cvo.Theme"
     */
    public SortedSet getChildren() {
        return children;
    }


    public void setChildren(SortedSet children) {
        this.children = children;
    }


    /**
     * @return
     * 
     * @hibernate.property not-null="true"
     */
    public Long getOid() {
        return oid;
    }


    public void setOid(Long oid) {
        this.oid = oid;
    }


    /**
     * @return
     * 
     * @hibernate.property not-null="true"
     */
    public int getOtype() {
        return otype;
    }


    public void setOtype(int otype) {
        this.otype = otype;
    }


    /*
     * ------------------------------------------------------------------------
     */
    
    
    public Object getObj() {
        return obj;
    }


    public void setObj(Object obj) {
        this.obj = obj;
    }


}//class Theme
