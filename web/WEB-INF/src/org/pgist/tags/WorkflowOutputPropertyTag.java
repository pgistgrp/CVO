package org.pgist.tags;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.pgist.wfengine.EnvironmentInOuts;


/**
 * 
 * @author kenny
 *
 */
public class WorkflowOutputPropertyTag extends SimpleTagSupport {
    
    
    private static final long serialVersionUID = 1L;
    
    private String property;
    
    
    public void setProperty(String property) {
		this.property = property;
	}


    /*
     * ------------------------------------------------------------------------
     */
    
    
	public void doTag() throws JspException, IOException {
        PageContext context = (PageContext) getJspContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        JspWriter writer = getJspContext().getOut();
        
        EnvironmentInOuts inouts = (EnvironmentInOuts) request.getAttribute("org.pgist.wfengine.INOUTS");
        
        if (inouts!=null) {
        	String value = inouts.getProperty(property);
        	if (value!=null) {
    	        writer.write(value);
        	}
        }
    }//doTag()
    
    
}//class WorkflowOutputPropertyTag
