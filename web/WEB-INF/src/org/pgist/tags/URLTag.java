package org.pgist.tags;

import java.io.IOException;

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
public class URLTag extends SimpleTagSupport {
    
    
    private static final long serialVersionUID = 1L;
    
    
    private String page = "";
    
    private String params = "";
    
    private JspFragment jspBody;


    public void setPage(String page) {
        this.page = page;
    }


    public void setParams(String params) {
        this.params = params;
    }


    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void setJspBody(JspFragment jspBody) {
        this.jspBody = jspBody;
    }//setJspBody()
    
    
    public void doTag() throws JspException, IOException {
        PageContext context = (PageContext) getJspContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        
        String workflowId = request.getParameter("workflowId");
        String contextId = request.getParameter("contextId");
        String activityId = request.getParameter("activityId");
        
        JspWriter writer = getJspContext().getOut();
        writer.write("<a href=\"");
        writer.write(page);
        writer.write("?");
        writer.write("workflowId=");
        writer.write(workflowId);
        writer.write("&contextId=");
        writer.write(contextId);
        writer.write("&activityId=");
        writer.write(activityId);
        writer.write("&");
        writer.write(params);
        writer.write("\">");
        jspBody.invoke(writer);
        writer.write("</a>");
    }//doTag()
    
    
}//class URLTag
