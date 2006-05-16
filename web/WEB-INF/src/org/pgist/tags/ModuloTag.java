package org.pgist.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;


/**
 * 
 * @author kenny
 *
 */
public class ModuloTag extends TagSupport {

    
    private static final long serialVersionUID = -4358153734927746923L;

    private String parameter;
    
    private int folding;
    
    private int value;
    
    
    public void setParameter(String parameter) {
        this.parameter = parameter;
    }


    public void setFolding(int folding) {
        this.folding = folding;
    }


    public void setValue(int value) {
        this.value = value;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */


    public int doStartTag() throws JspException {
        Integer index = (Integer) pageContext.getAttribute(parameter);
        
        if (index.intValue() % folding == value) {
            return EVAL_BODY_INCLUDE;
        } else {
            return SKIP_BODY;
        }
    }//doStartTag()
    
    
}//class ModuloTag
