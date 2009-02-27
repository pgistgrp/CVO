package org.pgist.sarp.cht;

import java.io.Serializable;

import org.pgist.sarp.GenericComment;
import org.pgist.sarp.cst.CategoryReference;


/**
 * CHT Comment.
 * 
 * @author kenny
 *
 * @hibernate.joined-subclass name="CHTComment" table="sarp_cht_comment" lazy="true"
 * @hibernate.joined-subclass-key column="id"
 */
public class CHTComment extends GenericComment implements Serializable {
    
    
	private static final long serialVersionUID = -2561599748380339322L;
	
	
	private CategoryReference catRef;
	
    private CHTComment parent;
    
    
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
     * @hibernate.many-to-one column="parent_id" lazy="true"
     */
    public CHTComment getParent() {
		return parent;
	}


	public void setParent(CHTComment parent) {
		this.parent = parent;
	}


}//class CHTComment
