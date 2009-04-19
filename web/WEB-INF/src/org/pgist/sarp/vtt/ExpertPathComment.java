package org.pgist.sarp.vtt;

import org.pgist.sarp.cht.CategoryPath;
import org.pgist.users.User;

/**
 * @author kenny
 *
 * @hibernate.class table="sarp_vtt_exppath_comment" lazy="true"
 */
public class ExpertPathComment {
    
    private Long id;
    
    private CategoryPath path;
    
    private User owner;
    
    private String content;
    
    private String source;
    
    
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
     * @hibernate.many-to-one column="owner_id" lazy="true"
     */
    public User getOwner() {
        return owner;
    }
    public void setOwner(User owner) {
        this.owner = owner;
    }
    
    
    /**
     * @return
     * 
     * @hibernate.property
     */
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }


    /**
     * @return
     * @hibernate.property type="text"
     */
    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
    }
    
    
}
