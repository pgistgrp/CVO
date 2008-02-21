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
public class WorkflowPropertyTag extends SimpleTagSupport {
    
    
    private static final long serialVersionUID = 1L;
    
    private String var;
    
    private String name;
    
    
    public void setVar(String var) {
        this.var = var;
    }


    public void setName(String name) {
		this.name = name;
	}
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
	public void doTag() throws JspException, IOException {
        PageContext context = (PageContext) getJspContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        JspWriter writer = getJspContext().getOut();
        
        EnvironmentInOuts inouts = (EnvironmentInOuts) request.getAttribute("org.pgist.wfengine.INOUTS");
        
        String property = null;
        
        if (inouts!=null) {
        	property = inouts.getProperty(name);
        }
        
        if (var!=null) {
            request.setAttribute(var, property);
        } else {
            writer.write(property);
        }
    }//doTag()
    
    
}//class WorkflowPropertyTag
