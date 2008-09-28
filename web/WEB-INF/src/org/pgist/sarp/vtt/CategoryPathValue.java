package org.pgist.sarp.vtt;

import org.pgist.sarp.cht.CategoryPath;
import org.pgist.users.User;


/**
 * Category Value.
 * 
 * @author kenny
 *
 * @hibernate.class table="sarp_vtt_path_value" lazy="true"
 */
public class CategoryPathValue {
    
    
    private Long id;
    
    private CategoryPath path;
    
    private User user;
    
    // if it's a tag or a user input name
    private boolean tag;
    
    // name here now is either a tag name or a user input name
    private String name;
    
    private String criterion;
    
    
    public CategoryPathValue() {
    }
    
    
    public CategoryPathValue(CategoryPath path, User user, boolean tag) {
        this.user = user;
        this.path = path;
        this.tag = tag;
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
     * @hibernate.many-to-one column="path_id" lazy="true"
     */
    public CategoryPath getPath() {
        return path;
    }


    public void setPath(CategoryPath path) {
        this.path = path;
    }


    /**
     * @return
     * 
     * @hibernate.many-to-one column="user_id" lazy="true"
     */
    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
    }


    /**
     * @return
     * 
     * @hibernate.property not-null="true"
     */
    public boolean isTag() {
        return tag;
    }


    public void setTag(boolean tag) {
        this.tag = tag;
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
