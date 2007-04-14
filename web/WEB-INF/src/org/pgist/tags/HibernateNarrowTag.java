package org.pgist.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.pgist.wfengine.util.Utils;


/**
 * 
 * @author kenny
 *
 */
public class HibernateNarrowTag extends SimpleTagSupport {
    
    
    private static final long serialVersionUID = 1L;
    
    
    private String name = null;
    
    
    public void setName(String name) {
        this.name = name;
    }


    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void doTag() throws JspException, IOException {
        Object object = getJspContext().getAttribute(name);
        object = Utils.narrow(object);
        getJspContext().setAttribute(name, object);
    }//doTag()
    
    
}//class HibernateNarrowTag
