package org.pgist.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;


/**
 * 
 * @author kenny
 *
 */
public class FeedbackTag extends SimpleTagSupport {
    
    
    private static final long serialVersionUID = 1L;
    
    
    private String id = "feedback";
    
    private String style = "";
    
    private String styleClass = "";
    
    private String action = "";
    
    
    public void setId(String id) {
        this.id = id;
    }


    public void setStyle(String style) {
        this.style = style;
    }


    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }


    public void setAction(String action) {
        this.action = action;
    }


    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void doTag() throws JspException, IOException {
        JspWriter writer = getJspContext().getOut();
        
        //external js files
        writer.write("<script src=\"/dwr/interface/SystemAgent.js\"></script>");
        
        writer.write("<script>");
        writer.write("function createFeedback() {");
        writer.write("  s = $('feedback_input').value;");
        writer.write("  if (s.length==0) { alert('Please input your feedback.'); return; }");
        writer.write("  action = '"+action+"';");
        writer.write("  SystemAgent.createFeedback(");
        writer.write("    {feedback:s, action:action}, function(data) {");
        writer.write("      if (data.successful) { alert('You feedback is submitted. Thank you.'); $('feedback_input').value=''; }");
        writer.write("      else { alert(data.reason); }");
        writer.write("    }");
        writer.write("  );");
        writer.write("}");
        writer.write("</script>");
        
        //div head
        writer.write("<div id=\"");
        writer.write(id);
        writer.write("\" ");
        
        if (style!=null && style.trim().length()>0) {
            writer.write("style=\"");
            writer.write(style);
            writer.write("\" ");
        }
        
        if (styleClass!=null && styleClass.trim().length()>0) {
            writer.write("class=\"");
            writer.write(styleClass);
            writer.write("\" ");
        }
        
        writer.write(">");
        
        writer.write("Feedback:<br>");
        writer.write("<textarea id=\"feedback_input\"></textarea>");
        writer.write("<br><input type=\"button\" value=\"Submit\" onclick=\"createFeedback();\">");
        
        writer.write("</div>");
    }//doTag()
    
    
}//class FeedbackTag
