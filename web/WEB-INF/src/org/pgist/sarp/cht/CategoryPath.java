package org.pgist.sarp.cht;

import java.util.ArrayList;
import java.util.List;

import org.pgist.sarp.cst.CategoryReference;

/**
 * @author kenny
 *
 * @hibernate.class table="sarp_cht_cat_path" lazy="true"
 */
public class CategoryPath {
    
    
    private Long id;
    
    private CHT cht;
    
    private String title;
    
    private List<CategoryReference> categories = new ArrayList<CategoryReference>();
    
    private List<Long> users = new ArrayList<Long>();
    
    private int numAgree = 0;
    
    private int numVote = 0;
    
    private int frequency = 1;

    
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
     * @hibernate.many-to-one column="cht_id" lazy="true" cascade="all"
     */
    public CHT getCht() {
        return cht;
    }

    public void setCht(CHT cht) {
        this.cht = cht;
    }
    

    /**
     * @return
     * @hibernate.property
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
     * @hibernate.list lazy="false" cascade="all" table="sarp_cht_cat_path_link" cascade="all"
     * @hibernate.collection-key column="path_id"
     * @hibernate.collection-index column="index_order"
     * @hibernate.collection-many-to-many column="catref_id" class="org.pgist.sarp.cst.CategoryReference"
     */
    public List<CategoryReference> getCategories() {
        return categories;
    }
    
    public void setCategories(List<CategoryReference> categories) {
        this.categories = categories;
    }

    
    /**
     * @return
     * 
     * @hibernate.list lazy="true" cascade="all" table="sarp_cat_path_users" cascade="all"
     * @hibernate.collection-key column="path_id"
     * @hibernate.collection-index column="index_order"
     * @hibernate.collection-element type="long" column="user_id"
     */
    public List<Long> getUsers() {
        return users;
    }
    
    public void setUsers(List<Long> users) {
        this.users = users;
    }

    public void addUser(Long id) {
        users.add(id);
    }
    
    /**
     * @return
     * @hibernate.property
     */
    public int getNumAgree() {
        return numAgree;
    }
    
    public void setNumAgree(int numAgree) {
        this.numAgree = numAgree;
    }

    
    /**
     * @return
     * @hibernate.property
     */
    public int getNumVote() {
        return numVote;
    }

    public void setNumVote(int numVote) {
        this.numVote = numVote;
    }

    
    /**
     * @return
     * @hibernate.property
     */
    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void genTitle() {
        StringBuilder sb = new StringBuilder();
        for (CategoryReference catRef : getCategories()) {
            if (sb.length()>0) sb.append("|");
            sb.append(catRef.getCaption());
        }
        setTitle(sb.toString());
    }
    
    
} //class CategoryPath
