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
    
    private int width;
    
    private int height;
    
    
    public void setId(String condition) {
        this.id = condition;
    }


    public void setWidth(int width) {
        this.width = width;
    }


    public void setHeight(int height) {
        this.height = height;
    }


    public int doStartTag() throws JspException {
        HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();
        String ctxPath = req.getContextPath();
        JspWriter writer = pageContext.getOut();
        try {
            writer.write("<script>");
            writer.write("var "+id+" = {");
            writer.write("popup : function() {");
            writer.write("var div = $('"+id+"');");
            writer.write("leftPos = (window.innerWidth-"+width+")/2;");
            writer.write("topPos = (window.innerHeight-"+height+")/2;");
            writer.write("div.style.left = leftPos+\"px\";");
            writer.write("div.style.top = topPos+\"px\";");
            writer.write("div.style.display = \"block\";");
            writer.write("},");
            writer.write("close : function() {");
            writer.write("$('"+id+"').style.display = \"none\";");
            writer.write("}");
            writer.write("};");
            writer.write("</script>");
            writer.write("<div id=\"");
            writer.write(id);
            writer.write("\" class=\"dialog\" ");
            writer.write("style=\"width:");
            writer.write(""+width);
            writer.write(";height:");
            writer.write(""+height);
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
            writer.write(" onclick=\"");
            writer.write(id);
            writer.write(".close();\"></td>");
            writer.write("</tr><tr><td class=\"body\" valign=\"top\">");
            writer.write("<div style=\"width:100%;overflow:auto;height:"+(height-21)+"\">");
        } catch(IOException e) {
            throw new JspException(e);
        }
        return EVAL_BODY_INCLUDE;
    }//doStartTag()
    
    
    public int doEndTag() throws JspException {
        JspWriter writer = pageContext.getOut();
        try {
            writer.write("</div></td></tr></table></div>");
        } catch(IOException e) {
            throw new JspException(e);
        }
        return SKIP_BODY;
    }//doEndTag()
    
    
}//class DialogTag
