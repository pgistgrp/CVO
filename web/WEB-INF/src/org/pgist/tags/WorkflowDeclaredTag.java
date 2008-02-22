package org.pgist.tags;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.pgist.wfengine.RunningContext;
import org.pgist.wfengine.activity.PGameActivity;


/**
 * 
 * @author kenny
 *
 */
public class WorkflowDeclaredTag extends SimpleTagSupport {
    
    
    private static final long serialVersionUID = 1L;
    
    private RunningContext context;
    
    private PGameActivity activity;
    
    private String var;
    
    private String name;
    
    private String type = "any";
    
    
    public void setActivity(PGameActivity activity) {
        this.activity = activity;
    }


    public void setVar(String var) {
        this.var = var;
    }


    public void setName(String name) {
		this.name = name;
	}
    
    
    public void setContext(RunningContext context) {
        this.context = context;
    }


    /*
     * ------------------------------------------------------------------------
     */
    
    
	public void doTag() throws JspException, IOException {
        PageContext pageContext = (PageContext) getJspContext();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        JspWriter writer = getJspContext().getOut();
        
        if (context==null) {
            context = (RunningContext) request.getAttribute("org.pgist.wfengine.CONTEXT");
        }
        
        if (activity==null) {
            activity = (PGameActivity) request.getAttribute("org.pgist.wfengine.CURRENT");
        }
        
        boolean declared = false;
        
        if (activity!=null) {
            if (context==null) throw new JspException("can't find running context");
            
            if ("any".equalsIgnoreCase(type)) {
                if (activity.getDeclaration().getIns().get(name)!=null) declared = true;
                if (activity.getDeclaration().getOuts().get(name)!=null) declared = true;
                if (activity.getDeclaration().getProperties().get(name)!=null) declared = true;
            } else if ("in".equalsIgnoreCase(type)) {
                if (activity.getDeclaration().getIns().get(name)!=null) declared = true;
            } else if ("out".equalsIgnoreCase(type)) {
                if (activity.getDeclaration().getIns().get(name)!=null) declared = true;
            } else if ("property".equalsIgnoreCase(type)) {
                if (activity.getDeclaration().getIns().get(name)!=null) declared = true;
            } else {
                throw new JspException("unknown workflow declaration time.");
            }
        }
        
        if (var!=null) {
            request.setAttribute(var, declared);
        } else {
            writer.write(""+declared);
        }
    }//doTag()
    
    
}//class WorkflowDeclaredTag
