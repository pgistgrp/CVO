package org.pgist.tags;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.pgist.wfengine.EnvironmentInOuts;
import org.pgist.wfengine.RunningContext;
import org.pgist.wfengine.activity.PGameActivity;


/**
 * 
 * @author kenny
 *
 */
public class WorkflowEnvVarTag extends SimpleTagSupport {
    
    
    private static final long serialVersionUID = 1L;
    
    private RunningContext context;
    
    private PGameActivity activity;
    
    private String var;
    
    private String name;
    
    
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
        
        EnvironmentInOuts inouts = null;
        
        if (context==null) {
            context = (RunningContext) request.getAttribute("org.pgist.wfengine.CONTEXT");
        }
        
        if (activity!=null) {
            if (context==null) throw new JspException("can't find running context");
            inouts = new EnvironmentInOuts(
                context,
                activity.getDeclaration()
            );
        } else {
            inouts = (EnvironmentInOuts) request.getAttribute("org.pgist.wfengine.INOUTS");
            if (inouts==null) throw new JspException("can't find environment inouts");
        }
        
        String property = null;
        
        property = inouts.getStrValue(name);
        
        if (var!=null) {
            request.setAttribute(var, property);
        } else {
            writer.write(property);
        }
    }//doTag()
    
    
}//class WorkflowEnvVarTag
