package org.pgist.tags;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;


/**
 * 
 * @author kenny
 *
 */
public class DialogTag extends TagSupport {

    
    private static final long serialVersionUID = -4358153734927746923L;

    private String id;
    
    private String width;
    
    private String height;
    
    
    public void setId(String condition) {
        this.id = condition;
    }


    public void setWidth(String owner) {
        this.width = owner;
    }


    public void setHeight(String roles) {
        this.height = roles;
    }


    public int doStartTag() throws JspException {
        HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();
        String ctxPath = req.getContextPath();
        JspWriter writer = pageContext.getOut();
        try {
            writer.write("<div id=\"");
            writer.write(id);
            writer.write("\" class=\"dialog\" ");
            writer.write("style=\"width:");
            writer.write(width);
            writer.write(";height:");
            writer.write(height);
            writer.write("\">");
            writer.write("<table width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
            writer.write("<tr>");
            writer.write("<td height=\"21\" style=\"background:url(");
            writer.write(ctxPath);
            writer.write("/images/titlebar.gif) repeat-x top;\" align=\"right\" nowrap><img src=\"");
            writer.write(ctxPath);
            writer.write("/images/close.gif\" onmouseover=\"");
            writer.write("this.src='");
            writer.write(ctxPath);
            writer.write("/images/close1.gif';\"");
            writer.write(" onmouseout=\"");
            writer.write("this.src='");
            writer.write(ctxPath);
            writer.write("/images/close.gif';\"");
            writer.write(" onclick=\"$('");
            writer.write(id);
            writer.write("').style.display='none';\"></td>");
            writer.write("</tr><tr><td class=\"body\" valign=\"top\">");
        } catch(IOException e) {
            throw new JspException(e);
        }
        return EVAL_BODY_INCLUDE;
    }//doStartTag()
    
    
    public int doEndTag() throws JspException {
        JspWriter writer = pageContext.getOut();
        try {
            writer.write("</td></tr></table></div>");
        } catch(IOException e) {
            throw new JspException(e);
        }
        return SKIP_BODY;
    }//doEndTag()
    
    
}//class DialogTag
