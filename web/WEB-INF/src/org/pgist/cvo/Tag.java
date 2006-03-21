package org.pgist.cvo;

import java.util.SortedSet;


/**
 *
 * @author kenny
 *
 *
 * @hibernate.class table="pgist_cst_tags" lazy="true"
 */
public class Tag {
    public Tag() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    protected Long id;

    protected String name;

    protected SortedSet categories;

    protected boolean deleted = false;

    //Jie March 20, 2006
    private boolean candidate = false;
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
     *
     * @hibernate.set lazy="true" table="pgist_cst_cat_tag_link" sort="org.pgist.cvo.CategoryComparator"
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
    public boolean isDeleted() {
        return deleted;
    }

    //Jie March 20, 2006
    public boolean getCandidate() {
        return candidate;
    }

    //Jie March 20, 2006
    public int getCount() {
        return count;
    }


    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    //Jie March 20, 2006
    public void setCandidate(boolean candidate) {
        this.candidate = candidate;
    }

    //Jie March 20, 2006
    public void setCount(int count) {
        this.count = count;
    }

    private void jbInit() throws Exception {
    }


}
