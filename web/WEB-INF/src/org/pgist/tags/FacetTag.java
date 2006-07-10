package org.pgist.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;


/**
 * 
 * @author kenny
 *
 */
public class FacetTag extends BodyTagSupport {
    
    
    private static final long serialVersionUID = -4266414167964467033L;
    
    private String name;
    
    
    public void setName(String name) {
        this.name = name;
    }


    public int doStartTag() throws JspException {
        return EVAL_BODY_BUFFERED;
    }//doStartTag()


    public int doEndTag() throws JspException {
        FacetAware parent = (FacetAware) findAncestorWithClass(this, FacetAware.class);
        if (parent == null) {
            throw new JspTagException("Tag 'facet' must be included in a FacetAware tag.");
        }
        if (bodyContent != null) {
            String facet = bodyContent.getString().trim();
            if (facet.length()>0) {
                parent.addFacet(name, facet);
            }
        }
        return EVAL_PAGE;
    }//doEndTag()


}//class FacetTag
