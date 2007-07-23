package org.pgist.tags;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;


/**
 * 
 * @author kenny
 *
 */
public class WorkflowInfoTag extends SimpleTagSupport {
    
    
    private static final long serialVersionUID = 1L;
    
    
    private JspFragment jspBody;


    public void setJspBody(JspFragment jspBody) {
        this.jspBody = jspBody;
    }//setJspBody()
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void doTag() throws JspException, IOException {
        PageContext context = (PageContext) getJspContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        
        String workflowId = request.getParameter("workflowId");
        String contextId = request.getParameter("contextId");
        String activityId = request.getParameter("activityId");
        
        if (workflowId==null) {
            Map wfinfo = (Map) request.getAttribute("wfinfo");
            if (wfinfo!=null) {
                workflowId = (String) wfinfo.get("workflowId");
                contextId = (String) wfinfo.get("contextId");
                activityId = (String) wfinfo.get("activityId");
            }
        }
        
        JspWriter writer = getJspContext().getOut();
        writer.write("{");
        writer.write("workflowId:");
        writer.write(workflowId);
        writer.write(", contextId:");
        writer.write(contextId);
        writer.write(", activityId:");
        writer.write(activityId);
        writer.write("}");
    }//doTag()
    
    
}//class WorkflowInfoTag
