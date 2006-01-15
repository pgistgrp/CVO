package org.pgist.tags;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.pgist.model.Post;


/**
 * 
 * @author kenny
 *
 */
public class DiscourseTag extends SimpleTagSupport {

    
    private static final long serialVersionUID = -4358153734927746923L;

    private String id;
    
    private String post;
    
    private String callback;
    
    private static final String[] colors = {
        "#ccccff",
        "#ccfffe",
        "#ffcccc",
        "#ffbae7",
        "#baffbf",
        "#fbffba",
        "#ffe4ba",
        "#c9c9c9",
        "#e1a7ff",
        "#d2ffa7",
        "#ff9696",
        "#9b96ff",
        "#a7ff96",
        "#ff96ea",
        "#96d4ff",
        "#968dff",
        "#fff88d",
        "#ffd58d",
        "#ffb58d",
    };
    
    private static final int length = colors.length;
    
    
    public void setId(String condition) {
        this.id = condition;
    }
    
    
    public void setPost(String post) {
        this.post = post;
    }
    
    
    public void setCallback(String callback) {
        this.callback = callback;
    }


    public void doTag() throws JspException, IOException {
        Post thePost = (Post) getJspContext().getAttribute(post);
        JspWriter writer = getJspContext().getOut();
        writer.write("<div id=\"");
        writer.write(id);
        writer.write("\" class=\"discourse\" width=\"100%\">");
        
        writer.write("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">");
        
        int n = thePost.getChildren().size();
        writer.write("<tr><td width=\"100%\" valign=\"top\" class=\"outtop\"");
        if (n>1) {
            writer.write(" colspan=\""+n+"\"");
        }
        writer.write(">");
        String content = thePost.getContent();
        content = content.replaceAll("\n", "<br>");
        writer.write(content);
        writer.write("<span style=\"margin-left:30px;\">--- ");
        writer.write(thePost.getOwner().getLoginname());
        writer.write("</span><span style=\"margin-left:30px;\" class=\"link\" onclick=\"");
        writer.write("if ("+callback+") {"+callback+"(");
        writer.write(""+thePost.getId());
        writer.write(");}\">[Comment]</span>");
        writer.write("</td></tr>");
        
        writer.write("<tr>");
        boolean first = true;
        for (Iterator iter=thePost.getChildren().iterator(); iter.hasNext(); ) {
            if (first) writer.write("<td valign=\"top\" class=\"outleft\">");
            else writer.write("<td valign=\"top\" class=\"outright\">");
            renderNode((Post) (iter.next()), first);
            first = false;
            writer.write("</td>");
        }//for i
        writer.write("</tr>");
        
        writer.write("</table>");
        writer.write("</div>");
    }//doTag()
    
    
    public void renderNode(Post thePost, boolean first) throws IOException {
        int n = thePost.getChildren().size();
        
        JspWriter writer = getJspContext().getOut();
        if (n==0) {
            writer.write("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" height=\"5\" width=\"100%\">");
        } else {
            writer.write("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">");
        }
        
        writer.write("<tr><td width=\"100%\" valign=\"top\" class=\"hidetop\" style=\"background-color:");
        int index = (int) Math.floor(Math.random()*length);
        writer.write(colors[index]);
        writer.write(";\"");
        if (n>1) {
            writer.write("colspan=\"");
            writer.write(n);
            writer.write("\"");
        }
        writer.write(">");
        writer.write("</td></tr>");
        
        writer.write("<tr>");
        first = true;
        for (Iterator iter=thePost.getChildren().iterator(); iter.hasNext(); ) {
            writer.write("<td valign=\"top\" class=\"hidenone\">");
            renderNode((Post) (iter.next()), first);
            first = false;
            writer.write("</td>");
        }//for i
        writer.write("</tr>");
        
        writer.write("</table>");
    }//renderNode()
    
    
}//class DialogTag
