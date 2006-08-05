package org.pgist.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;


/**
 * 
 * @author kenny
 *
 */
public class FragmentTag extends TagSupport {

    
    public static final String FRAGMENT_TYPE = "page.source.type";
    
    public static final String HTML = "html";
    
    public static final String SCRIPT = "script";
    
    
    private static final long serialVersionUID = 950780053404145159L;
    
    private String type;
    

    public void setType(String type) {
        this.type = type;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    public int doStartTag() throws JspException {
        String part = (String) pageContext.getAttribute(FRAGMENT_TYPE, PageContext.REQUEST_SCOPE);
        
        if (type.equalsIgnoreCase(part)) return EVAL_BODY_INCLUDE;
        
        return SKIP_BODY;
    }//doStartTag()



}//class FragmentTag
