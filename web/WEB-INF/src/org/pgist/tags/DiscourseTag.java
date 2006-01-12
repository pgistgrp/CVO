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
    
    
    public void setId(String condition) {
        this.id = condition;
    }
    
    
    public void setPost(String post) {
        this.post = post;
    }
    
    
    public void doTag() throws JspException, IOException {
        Post thePost = (Post) getJspContext().getAttribute(post);
        JspWriter writer = getJspContext().getOut();
        writer.write("<div id=\"");
        writer.write(id);
        writer.write("\" width=\"100%\">");
        
        writer.write("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">");
        
        int n = thePost.getChildren().size();
        writer.write("<tr><td width=\"100%\" valign=\"top\" class=\"outtop\"");
        if (n>1) {
            writer.write(" colspan=\""+n+"\"");
        }
        writer.write(">");
        writer.write(thePost.getContent());
        writer.write("<br><span style=\"margin-left:50px;\">--- ");
        writer.write(thePost.getOwner().getLoginname());
        writer.write("</span>");
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
        JspWriter writer = getJspContext().getOut();
        writer.write("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">");
        
        int n = thePost.getChildren().size();
        writer.write("<tr><td width=\"100%\" valign=\"top\" class=\"hidetop\"");
        if (n>1) {
            writer.write("colspan=\"");
            writer.write(n);
            writer.write("\"");
        }
        writer.write(">");
        writer.write(thePost.getContent());
        writer.write("<br><span style=\"margin-left:50px;\">--- ");
        writer.write(thePost.getOwner().getLoginname());
        writer.write("</span>");
        writer.write("</td></tr>");
        
        writer.write("<tr><td valign=\"top\" class=\"hidenone\">");
        first = true;
        for (Iterator iter=thePost.getChildren().iterator(); iter.hasNext(); ) {
            renderNode((Post) (iter.next()), first);
            first = false;
        }//for i
        writer.write("</td></tr>");
        
        writer.write("</table>");
    }//renderNode()
    
    
}//class DialogTag
