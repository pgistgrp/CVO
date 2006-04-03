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
public class ButtonTag extends SimpleTagSupport {
    
    
    private static final long serialVersionUID = 1L;
    
    
    private String onclick = "void();";
    
    private String title = "title";


    public void setOnclick(String onclick) {
        this.onclick = onclick;
    }
    
    
    public void setTitle(String title) {
        this.title = title;
    }


    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void doTag() throws JspException, IOException {
        JspWriter writer = getJspContext().getOut();
        writer.write("<span class=\"pg_button\" onclick=\""+onclick+"\">");
        writer.write(title);
        writer.write("</span>");
    }//doTag()
    
    
}//class ButtonTag
