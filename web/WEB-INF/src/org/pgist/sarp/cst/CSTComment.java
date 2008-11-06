package org.pgist.sarp.cst;

import java.io.Serializable;
import java.util.Set;

import org.pgist.sarp.GenericComment;


/**
 * CST Comment.
 * 
 * @author kenny
 *
 * @hibernate.joined-subclass name="VTTSpecialistComment" table="sarp_cst_comment" lazy="true"
 * @hibernate.joined-subclass-key column="id"
 */
public class CSTComment extends GenericComment implements Serializable {
    
    
	private static final long serialVersionUID = -2561599748380339322L;
	
	
	private CategoryReference catRef;
	
    private CSTComment parent;
    
    
    /**
     * @return
     * 
     * @hibernate.many-to-one column="catref_id" cascade="all" lazy="true"
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
     * @hibernate.set lazy="true" table="sarp_cst_comment_tag_link" cascade="all"
     * @hibernate.collection-key column="comment_id"
     * @hibernate.collection-many-to-many column="tag_id" class="org.pgist.tagging.Tag"
     */
    public Set getTags() {
        return tags;
    }


    public void setTags(Set tags) {
        this.tags = tags;
    }


    /**
     * @return
     * 
     * @hibernate.many-to-one column="parent_id" lazy="true"
     */
    public CSTComment getParent() {
		return parent;
	}


	public void setParent(CSTComment parent) {
		this.parent = parent;
	}


}//class CSTComment
